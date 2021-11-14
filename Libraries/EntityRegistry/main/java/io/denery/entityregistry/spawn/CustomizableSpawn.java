package io.denery.entityregistry.spawn;

import io.denery.entityregistry.entity.AbstractCustomizableEntityType;
import io.denery.entityregistry.entity.CustomizableEntity;
import io.denery.entityregistry.entity.CustomizableEntityBuilder;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

/**
 * Class where you can customize spawning of your CustomizableEntities.
 */
public class CustomizableSpawn implements BiFunction<Server, Location, Optional<CustomizableEntity<?>>> {
    private CustomizableSpawn() {}
    private static final Logger logger = LoggerFactory.getLogger("EntityRegistryLib");

    /**
     * greater == less chance of spawning.
     */
    public int chance = 1;
    public int maxPerChunk = 20;
    public int maxLightLevel = 15;
    private List<AbstractCustomizableEntityType> types;


    /**
     * (pls don't look at this code if you don't wanna break your psychic)
     * Spawning Mechanism for Customizable Entities.
     *
     * @param server server where spawn is happening.
     * @param location location nearby which you need to spawn a mob.
     *
     * @return Spawned CustomizableEntity instance, but will be null if some values isn't match,
     * so you MUST add your custom null handler, otherwise you'll have NullPointerExceptions thrown.
     */
    @Override
    public Optional<CustomizableEntity<?>> apply(Server server, Location location) {
        // TODO: Performance and bug fixes, work asynchronously.
        Random random = new Random();
        if (types.size() < 1) {
            logger.info("Size of types: " + types.size());
        }
        int randindex = random.nextInt(Math.abs(types.size()));
        AbstractCustomizableEntityType type = types.get(randindex);
        if (type.getOriginType().isEmpty()) throw new NullPointerException("CustomizableEntityType's origin type is null!");
        int range = server.getViewDistance() * 2 * 16;
        LivingEntity spawnedEntity;
        // Random spawn option;
        if (randomizedBoolean(chance)) {
            // Randomly Asynchronously Spawn entity on the first air block from height 1.
            //spawnedEntity = doSpawnFromZeroLevel(type, location, range);
            spawnedEntity = doSpawnUnderPlayerLevel(type, location, range);
        } else {
            spawnedEntity = doSpawnAbovePlayerLevel(type, location, range);
            /*
            if (random.nextBoolean()) {
                // Randomly Asynchronously Spawn entity on the first solid block under players Y coord.
                spawnedEntity = doSpawnUnderPlayerLevel(type, location, range);
            } else {
                // Randomly Asynchronously Spawn entity on the first solid block above players Y coord.
                spawnedEntity = doSpawnAbovePlayerLevel(type, location, range);
            }

             */
        }
        return Optional.of(CustomizableEntityBuilder.newBuilder().applyType(type).applyOriginEntity(spawnedEntity).build());
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

    private boolean randomizedBoolean(int randomness) {
        Random random = new Random();
        return random.ints(0, randomness).findFirst().getAsInt() == 0;
    }

    private LivingEntity completeAsyncSpawn(CompletableFuture<LivingEntity> asyncLivingEntity) {
        try {
            return asyncLivingEntity.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace(System.err);
            logger.error("Cannot compete asynchronous spawning operation!");
            throw new RuntimeException("Something went wrong in asynchronous spawning entity!", e);
        }
    }

    private LivingEntity doSpawnFromZeroLevel(AbstractCustomizableEntityType type, Location location, int range) {
        Random random = new Random();
        final int xcord = location.getBlockX() + random.ints(-1 * range, range).findFirst().getAsInt();
        final int zcord = location.getBlockZ() + random.ints(-1 * range, range).findFirst().getAsInt();
        Block choosenBlock = null;
        CompletableFuture<Block> asyncBlockIter = CompletableFuture.supplyAsync(() -> {
            Block tmpChoosenBlock = null;
            int ycord = 1;
            for (Block block = location.getWorld().getBlockAt(new Location(location.getWorld(), xcord, ycord, zcord));
                 !block.isSolid(); ycord++) {
                tmpChoosenBlock = block;
                if (ycord > 254) break;
            }
            return tmpChoosenBlock;
        });
        try {
            choosenBlock = asyncBlockIter.get();
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Cannot invoke block iterating asynchronously!");
        }

        if (choosenBlock != null &&
                !new Location(location.getWorld(), xcord, choosenBlock.getY() - 1, zcord).getBlock().isLiquid() &&
                checkMaxLightLevel(location.getWorld(), choosenBlock.getLocation()) &&
                checkMaxPerChunk(location.getWorld(), choosenBlock.getLocation())) {
            return (LivingEntity) location.getWorld().spawn(new Location(location.getWorld(), xcord, choosenBlock.getY(), zcord), type.getOriginType().get().getEntityClass());
        }
        return null;
    }

    private LivingEntity doSpawnAbovePlayerLevel(AbstractCustomizableEntityType type, Location location, int range) {
        Random random = new Random();
        final int xcord = location.getBlockX() + random.ints(-1 * range, range).findFirst().getAsInt();
        final int zcord = location.getBlockZ() + random.ints(-1 * range, range).findFirst().getAsInt();
        Block choosenBlock = null;
        CompletableFuture<Block> asyncBlockIter = CompletableFuture.supplyAsync(() -> {
            Block tmpChoosenBlock = null;
            int ycord = location.getBlockY();
            for (Block block = location.getWorld().getBlockAt(new Location(location.getWorld(), xcord, ycord, zcord));
                 block.isSolid(); ycord++) {
                tmpChoosenBlock = block;
                if (ycord > 254) break;
                if (ycord < 1) break;
            }
            return tmpChoosenBlock;
        });
        try {
            choosenBlock = asyncBlockIter.get();
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Cannot invoke block iterating asynchronously!");
        }
        if (choosenBlock != null ||
                !new Location(location.getWorld(), xcord, choosenBlock.getY() + 1, zcord).getBlock().isLiquid() &&
                        checkMaxLightLevel(location.getWorld(), choosenBlock.getLocation()) &&
                        checkMaxPerChunk(location.getWorld(), choosenBlock.getLocation())) {
            return (LivingEntity) location.getWorld().spawn(new Location(location.getWorld(), xcord, choosenBlock.getY() + 1, zcord), type.getOriginType().get().getEntityClass());
        }
        return null;
    }

    private LivingEntity doSpawnUnderPlayerLevel(AbstractCustomizableEntityType type, Location location, int range) {
        Random random = new Random();
        final int xcord = location.getBlockX() + random.ints(-1 * range, range).findFirst().getAsInt();
        final int zcord = location.getBlockZ() + random.ints(-1 * range, range).findFirst().getAsInt();
        Block choosenBlock = null;
        CompletableFuture<Block> asyncBlockIter = CompletableFuture.supplyAsync(() -> {
            Block tmpChoosenBlock = null;
            int ycord = location.getBlockY();
            for (Block block = location.getWorld().getBlockAt(new Location(location.getWorld(), xcord, ycord, zcord));
                 block.isSolid(); ycord--) {
                tmpChoosenBlock = block;
                if (ycord > 254) break;
                if (ycord < 1) break;
            }
            return tmpChoosenBlock;
        });
        try {
            choosenBlock = asyncBlockIter.get();
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Cannot invoke block iterating asynchronously!");
        }
        if (choosenBlock != null ||
                !new Location(location.getWorld(), xcord, choosenBlock.getY() + 1, zcord).getBlock().isLiquid() &&
                        checkMaxLightLevel(location.getWorld(), choosenBlock.getLocation()) &&
                        checkMaxPerChunk(location.getWorld(), choosenBlock.getLocation())) {
            location.getWorld().spawn(new Location(location.getWorld(), xcord, choosenBlock.getY() + 1, zcord), type.getOriginType().get().getEntityClass());
        }
        return null;
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

        public CustomizableSpawnBuilder setChance(int chance) {
            CustomizableSpawn.this.chance = chance;
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
