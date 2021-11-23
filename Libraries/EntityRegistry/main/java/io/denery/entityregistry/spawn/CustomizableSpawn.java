package io.denery.entityregistry.spawn;

import io.denery.entityregistry.entity.AbstractCustomizableEntityType;
import io.denery.entityregistry.entity.CustomizableEntity;
import io.denery.entityregistry.entity.CustomizableEntityBuilder;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiFunction;

/**
 * Class where you can customize spawning of your CustomizableEntityTypes.
 */
public class CustomizableSpawn implements BiFunction<Server, Location, List<Optional<CustomizableEntity<?>>>> {
    private CustomizableSpawn() {}
    private static final Logger logger = LoggerFactory.getLogger("EntityRegistryLib");

    /**
     * means delay between 2 spawn actions, in milliseconds, by default is 100.
     */
    public int delay = 100;
    public int maxPerChunk = 20;
    public int maxLightLevel = 15;
    private List<AbstractCustomizableEntityType> types;
    private final int cores = Runtime.getRuntime().availableProcessors();


    /**
     * (pls don't look at this code if you don't wanna break your psychic)
     * Spawning reactive Mechanism for Customizable Entities.
     *
     * @param server server where spawn is happening.
     * @param location location nearby which you need to spawn a mob.
     *
     * @return Spawned CustomizableEntity instance, but will be null if some values isn't match,
     * so you MUST add your custom null handler, otherwise you'll have NullPointerExceptions thrown.
     */
    @Override
    public List<Optional<CustomizableEntity<?>>> apply(Server server, Location location) {
        if (types.size() < 1) {
            logger.error("There is no entity types registered! please, register it!");
        }
        Random random = new Random();
        int randindex = random.nextInt(Math.abs(types.size()));
        AbstractCustomizableEntityType type = types.get(randindex);
        if (type.getOriginType().isEmpty()) throw new NullPointerException("CustomizableEntityType's origin type is null! Please set it!");
        int range = server.getViewDistance() * 2 * 16;
        
        List<Optional<CustomizableEntity<?>>> optionalCustomizableEntities = new ArrayList<>();
            List<CompletableFuture<Optional<CustomizableEntity<?>>>> optionalCustomizableEntityFutures = new ArrayList<>();

            // Computing in parallel these spawn actions
            for (int i = 0; i < cores; i++) {
                optionalCustomizableEntityFutures.add(CompletableFuture.supplyAsync(() -> {
                    Optional<CustomizableEntity<?>> optionalCustomizableEntity = null;
                    /*
                    try {

                        if (random.nextBoolean()) {
                            // Spawn entity on the first not solid block from height 1.
                            optionalCustomizableEntity = Optional.of(CustomizableEntityBuilder.newBuilder()
                                    .applyType(type)
                                    .applyOriginEntity(doSpawnFromZeroLevel(type, location, range))
                                    .build());
                        } else {
                            // Spawn entity under location's Y coordinate
                            optionalCustomizableEntity = Optional.of(CustomizableEntityBuilder.newBuilder()
                                    .applyType(type)
                                    .applyOriginEntity(doAtHighestBlockInLocation(type, location, range))
                                    .build());
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }

                     */
                    return optionalCustomizableEntity;
                }));
            }
        try {
            for (CompletableFuture<Optional<CustomizableEntity<?>>> optionalCustomizableEntityFuture : optionalCustomizableEntityFutures) {
                optionalCustomizableEntities.add(optionalCustomizableEntityFuture.get());
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return optionalCustomizableEntities;
    }

    // I guess everything in these methods needs to be called in async,
    // because async methods must be thread safe and all the work in this sequence goes parallel.

    // Really weird method but sometimes it really helps.
    private Location getBlockLocalChunkLocationThreadSafe(World world, int x, int z) {
        final Chunk locTargetChunk;
        try {
            locTargetChunk = world.getChunkAtAsync(x, z).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Cannot get block in thread-safety mode!", e);
        }
        int xCordInChunk = 0;
        int zCordInChunk = 0;
        if (x > 0) {
            xCordInChunk = x - (locTargetChunk.getX() * 16 - 15);
        }
        if (z > 0) {
            zCordInChunk = z- (locTargetChunk.getZ() * 16 - 15);
        }
        if (x < 0) {
            xCordInChunk = x + (locTargetChunk.getX() * 16 + 15);
        }
        if (z < 0) {
            zCordInChunk = z + (locTargetChunk.getZ() * 16 + 15);
        }
        return new Location(world, xCordInChunk, 0, zCordInChunk);
    }

    private boolean checkMaxLightLevel(World world, Location location) {
        try {
            return world.getChunkAtAsync(location).get().getBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ()).getLightLevel() <= maxLightLevel;
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Cannot check block's light level in chunk!");
        }
    }

    private boolean checkMaxPerChunk(World world, Location location) {
        try {
            return world.getChunkAtAsync(location).get().getEntities().length < maxPerChunk;
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Cannot check amount of entities in chunk!");
        }
    }

    private LivingEntity doSpawnFromZeroLevel(AbstractCustomizableEntityType type, Location location, int range) throws ExecutionException, InterruptedException {
        LivingEntity entity = null;
        Random random = new Random();
        final Chunk locChunk = location.getWorld().getChunkAtAsync(location).get();
        final int xCord = locChunk.getX() + random.ints(-1 * range, range).findFirst().orElseThrow();
        final int zCord = locChunk.getZ() + random.ints(-1 * range, range).findFirst().orElseThrow();
        final Chunk targetChunk = location.getWorld().getChunkAtAsync(xCord, zCord).get();
        final Location inChunkLocation = getBlockLocalChunkLocationThreadSafe(location.getWorld(), xCord, zCord);
        Block chosenBlock = null;
        int ycord = 1;
        for (Block block = targetChunk.getBlock(inChunkLocation.getBlockX(), ycord, inChunkLocation.getBlockZ());
             !block.isSolid() && !block.isLiquid(); ycord++) {
            chosenBlock = block;
            if (ycord > 254) break;
        }
        if (chosenBlock == null) return null;
        if (!new Location(
                        location.getWorld(), xCord, chosenBlock.getY() - 1, zCord
                ).getBlock().isLiquid() &&
                        checkMaxLightLevel(location.getWorld(), chosenBlock.getLocation()) &&
                        checkMaxPerChunk(location.getWorld(), chosenBlock.getLocation()
                )) {
            Optional<Class<? extends Entity>> entityClass = Optional.ofNullable(type.getOriginType().get().getEntityClass());
            entity = (LivingEntity) location.getWorld().spawn(new Location(location.getWorld(), xCord, chosenBlock.getY(), zCord), entityClass.orElseThrow());
        }
        return entity;
    }

    private LivingEntity doAtHighestBlockInLocation(AbstractCustomizableEntityType type, Location location, int range) throws ExecutionException, InterruptedException {
        LivingEntity entity = null;
        Random random = new Random();
        final Chunk locChunk = location.getWorld().getChunkAtAsync(location).get();
        final int xCord = locChunk.getX() + random.ints(-1 * range, range).findFirst().orElseThrow();
        final int zCord = locChunk.getZ() + random.ints(-1 * range, range).findFirst().orElseThrow();
        final Chunk locTargetChunk = location.getWorld().getChunkAtAsync(xCord, zCord).get();
        Block chosenBlock = locTargetChunk.getWorld().getHighestBlockAt(xCord, zCord);
        if (!new Location(
                location.getWorld(), xCord, chosenBlock.getY() - 1, zCord
        ).getBlock().isLiquid() &&
                checkMaxLightLevel(location.getWorld(), chosenBlock.getLocation()) &&
                checkMaxPerChunk(location.getWorld(), chosenBlock.getLocation()
                )) {
            Optional<Class<? extends Entity>> entityClass = Optional.ofNullable(type.getOriginType().get().getEntityClass());
            entity = (LivingEntity) location.getWorld().spawn(new Location(location.getWorld(), xCord, chosenBlock.getY(), zCord), entityClass.orElseThrow());
        }
        return entity;
    }

    public static CustomizableSpawnBuilder newBuilder() {
        return new CustomizableSpawn().new CustomizableSpawnBuilder();
    }

    public class CustomizableSpawnBuilder {
        private CustomizableSpawnBuilder() {}

        public CustomizableSpawnBuilder setEntities(List<AbstractCustomizableEntityType> types) {
            CustomizableSpawn.this.types = types;
            return this;
        }

        public CustomizableSpawnBuilder setDelay(int delay) {
            CustomizableSpawn.this.delay = delay;
            return this;
        }

        public CustomizableSpawnBuilder setMaxPerChunk(int maxPerChunk) {
            CustomizableSpawn.this.maxPerChunk = maxPerChunk;
            return this;
        }

        public CustomizableSpawnBuilder setMaxLightLevel(int maxLightLevel) {
            CustomizableSpawn.this.maxLightLevel = maxLightLevel;
            return this;
        }

        public CustomizableSpawn build() {
            return CustomizableSpawn.this;
        }
    }
}
