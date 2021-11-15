package io.denery.entityregistry.spawn;

import io.denery.entityregistry.entity.AbstractCustomizableEntityType;
import io.denery.entityregistry.entity.CustomizableEntity;
import io.denery.entityregistry.entity.CustomizableEntityBuilder;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

/**
 * Class where you can customize spawning of your CustomizableEntityTypes.
 */
public class CustomizableSpawn implements BiFunction<Server, Location, Flux<Optional<CustomizableEntity<?>>>> {
    private CustomizableSpawn() {}
    private static final Logger logger = LoggerFactory.getLogger("EntityRegistryLib");

    /**
     * means delay between 2 spawn actions, in milliseconds, by default is 100.
     */
    public int delay = 100;
    public int maxPerChunk = 20;
    public int maxLightLevel = 15;
    private List<AbstractCustomizableEntityType> types;


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
    public Flux<Optional<CustomizableEntity<?>>> apply(Server server, Location location) {
        return Flux.create(sink -> {
            if (types.size() < 1) {
                logger.error("There is no entity types registered! please, register it!");
            }
            Random random = new Random();
            int randindex = random.nextInt(Math.abs(types.size()));
            AbstractCustomizableEntityType type = types.get(randindex);
            if (type.getOriginType().isEmpty()) throw new NullPointerException("CustomizableEntityType's origin type is null! Please set it!");
            int range = server.getViewDistance() * 2 * 16;

            // I guess everything in these methods needs to be called in async,
            // because async methods must be thread safe and all the work in this sequence goes parallel.

            try {
                // Spawn entity on the first not solid block from height 1.
                sink.next(Optional.of(CustomizableEntityBuilder.newBuilder()
                        .applyType(type)
                        .applyOriginEntity(doSpawnFromZeroLevel(type, location, range))
                        .build()));

                // Spawn entity under location's Y coordinate
                sink.next(Optional.of(CustomizableEntityBuilder.newBuilder()
                        .applyType(type)
                        .applyOriginEntity(doAtHighestBlockInLocation(type, location, range))
                        .build()));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean checkMaxLightLevel(World world, Location location) {
        return world.getBlockAt(location).getLightLevel() <= maxLightLevel;
    }

    private boolean checkMaxPerChunk(World world, Location location) {
        int valueOfEntitiesInChunk;
        try {
            valueOfEntitiesInChunk = world.getChunkAtAsync(location).get().getEntities().length;
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Cannot check amount of entities in chunk!");
        }
        return valueOfEntitiesInChunk < maxPerChunk;
    }

    private LivingEntity doSpawnFromZeroLevel(AbstractCustomizableEntityType type, Location location, int range) throws ExecutionException, InterruptedException {
        LivingEntity entity = null;
        Random random = new Random();
        final int xCord = location.getWorld().getChunkAtAsync(location).get().getX() + random.ints(-1 * range, range).findFirst().orElseThrow();
        final int zCord = location.getWorld().getChunkAtAsync(location).get().getX() + random.ints(-1 * range, range).findFirst().orElseThrow();
        Block chosenBlock = null;

        CompletableFuture<Block> asyncBlockIteration = CompletableFuture.supplyAsync(() -> {
            Block tmpChosenBlock = null;
            int ycord = 1;
            try {
                for (Block block = location.getWorld().getChunkAtAsync(location).get().getBlock(xCord, ycord, zCord);
                     !block.isSolid() && !block.isLiquid(); ycord++) {
                    tmpChosenBlock = block;
                    if (ycord > 254) break;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return tmpChosenBlock;
        });
        try {
            chosenBlock = asyncBlockIteration.get();
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Cannot invoke block iterating asynchronously!");
        }
        if (chosenBlock != null &&
                !new Location(
                        location.getWorld(), xCord, chosenBlock.getY() - 1, zCord
                ).getBlock().isLiquid() &&
                        checkMaxLightLevel(location.getWorld(), chosenBlock.getLocation()) &&
                        checkMaxPerChunk(location.getWorld(), chosenBlock.getLocation()
                )) {
            Optional<Class<? extends Entity>> entityClass = Optional.ofNullable(type.getOriginType().orElseThrow().getEntityClass());
            entity = (LivingEntity) location.getWorld().spawn(new Location(location.getWorld(), xCord, chosenBlock.getY(), zCord), entityClass.orElseThrow());
        }
        return entity;
    }

    private LivingEntity doAtHighestBlockInLocation(AbstractCustomizableEntityType type, Location location, int range) throws ExecutionException, InterruptedException {
        LivingEntity entity = null;
        Random random = new Random();
        final int xCord = location.getWorld().getChunkAtAsync(location).get().getX() + random.ints(-1 * range, range).findFirst().orElseThrow();
        final int zCord = location.getWorld().getChunkAtAsync(location).get().getX() + random.ints(-1 * range, range).findFirst().orElseThrow();
        Block chosenBlock = location.getWorld().getHighestBlockAt(xCord, zCord);
        if (new Location(
                location.getWorld(), xCord, chosenBlock.getY() - 1, zCord
        ).getBlock().isLiquid() &&
                checkMaxLightLevel(location.getWorld(), chosenBlock.getLocation()) &&
                checkMaxPerChunk(location.getWorld(), chosenBlock.getLocation()
                )) {
            Optional<Class<? extends Entity>> entityClass = Optional.ofNullable(type.getOriginType().orElseThrow().getEntityClass());
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
