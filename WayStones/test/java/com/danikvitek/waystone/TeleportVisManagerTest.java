package com.danikvitek.waystone;

import com.destroystokyo.paper.HeightmapType;
import com.destroystokyo.paper.block.BlockSoundGroup;
import io.papermc.paper.world.MoonPhase;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.*;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class TeleportVisManagerTest {
    private World dummyWorld = new World() {
        @Override
        public int getEntityCount() {
            return 0;
        }

        @Override
        public int getTileEntityCount() {
            return 0;
        }

        @Override
        public int getTickableTileEntityCount() {
            return 0;
        }

        @Override
        public int getChunkCount() {
            return 0;
        }

        @Override
        public int getPlayerCount() {
            return 0;
        }

        @Override
        public @NotNull MoonPhase getMoonPhase() {
            return null;
        }

        @Override
        public boolean lineOfSightExists(@NotNull Location location, @NotNull Location location2) {
            return false;
        }

        @Override
        public @NotNull Block getBlockAt(int i, int j, int k) {
            return new Block() {
                @Override
                public byte getData() {
                    return 0;
                }

                @Override
                public @NotNull BlockData getBlockData() {
                    return null;
                }

                @Override
                public @NotNull Block getRelative(int modX, int modY, int modZ) {
                    return null;
                }

                @Override
                public @NotNull Block getRelative(@NotNull BlockFace face) {
                    return null;
                }

                @Override
                public @NotNull Block getRelative(@NotNull BlockFace face, int distance) {
                    return null;
                }

                @Override
                public @NotNull Material getType() {
                    return null;
                }

                @Override
                public byte getLightLevel() {
                    return 0;
                }

                @Override
                public byte getLightFromSky() {
                    return 0;
                }

                @Override
                public byte getLightFromBlocks() {
                    return 0;
                }

                @Override
                public @NotNull World getWorld() {
                    return null;
                }

                @Override
                public int getX() {
                    return 0;
                }

                @Override
                public int getY() {
                    return 0;
                }

                @Override
                public int getZ() {
                    return 0;
                }

                @Override
                public boolean isValidTool(@NotNull ItemStack itemStack) {
                    return false;
                }

                @Override
                public @NotNull Location getLocation() {
                    return null;
                }

                @Override
                public @Nullable Location getLocation(@Nullable Location loc) {
                    return null;
                }

                @Override
                public @NotNull Chunk getChunk() {
                    return null;
                }

                @Override
                public void setBlockData(@NotNull BlockData data) {

                }

                @Override
                public void setBlockData(@NotNull BlockData data, boolean applyPhysics) {

                }

                @Override
                public void setType(@NotNull Material type) {

                }

                @Override
                public void setType(@NotNull Material type, boolean applyPhysics) {

                }

                @Override
                public @Nullable BlockFace getFace(@NotNull Block block) {
                    return null;
                }

                @Override
                public @NotNull BlockState getState() {
                    return null;
                }

                @Override
                public @NotNull BlockState getState(boolean useSnapshot) {
                    return null;
                }

                @Override
                public @NotNull Biome getBiome() {
                    return null;
                }

                @Override
                public void setBiome(@NotNull Biome bio) {

                }

                @Override
                public boolean isBlockPowered() {
                    return false;
                }

                @Override
                public boolean isBlockIndirectlyPowered() {
                    return false;
                }

                @Override
                public boolean isBlockFacePowered(@NotNull BlockFace face) {
                    return false;
                }

                @Override
                public boolean isBlockFaceIndirectlyPowered(@NotNull BlockFace face) {
                    return false;
                }

                @Override
                public int getBlockPower(@NotNull BlockFace face) {
                    return 0;
                }

                @Override
                public int getBlockPower() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public boolean isLiquid() {
                    return false;
                }

                @Override
                public boolean isBuildable() {
                    return false;
                }

                @Override
                public boolean isBurnable() {
                    return false;
                }

                @Override
                public boolean isReplaceable() {
                    return false;
                }

                @Override
                public boolean isSolid() {
                    return false;
                }

                @Override
                public boolean isCollidable() {
                    return false;
                }

                @Override
                public double getTemperature() {
                    return 0;
                }

                @Override
                public double getHumidity() {
                    return 0;
                }

                @Override
                public @NotNull PistonMoveReaction getPistonMoveReaction() {
                    return null;
                }

                @Override
                public boolean breakNaturally() {
                    return false;
                }

                @Override
                public boolean breakNaturally(@Nullable ItemStack tool) {
                    return false;
                }

                @Override
                public boolean breakNaturally(boolean triggerEffect) {
                    return false;
                }

                @Override
                public boolean breakNaturally(@NotNull ItemStack tool, boolean triggerEffect) {
                    return false;
                }

                @Override
                public boolean applyBoneMeal(@NotNull BlockFace face) {
                    return false;
                }

                @Override
                public @NotNull Collection<ItemStack> getDrops() {
                    return null;
                }

                @Override
                public @NotNull Collection<ItemStack> getDrops(@Nullable ItemStack tool) {
                    return null;
                }

                @Override
                public @NotNull Collection<ItemStack> getDrops(@NotNull ItemStack tool, @Nullable Entity entity) {
                    return null;
                }

                @Override
                public boolean isPreferredTool(@NotNull ItemStack tool) {
                    return false;
                }

                @Override
                public float getBreakSpeed(@NotNull Player player) {
                    return 0;
                }

                @Override
                public boolean isPassable() {
                    return false;
                }

                @Override
                public @Nullable RayTraceResult rayTrace(@NotNull Location start, @NotNull Vector direction, double maxDistance, @NotNull FluidCollisionMode fluidCollisionMode) {
                    return null;
                }

                @Override
                public @NotNull BoundingBox getBoundingBox() {
                    return null;
                }

                @Override
                public @NotNull VoxelShape getCollisionShape() {
                    return null;
                }

                @Override
                public @NotNull BlockSoundGroup getSoundGroup() {
                    return null;
                }

                @Override
                public @NotNull String getTranslationKey() {
                    return null;
                }

                @Override
                public float getDestroySpeed(@NotNull ItemStack itemStack, boolean considerEnchants) {
                    return 0;
                }

                @Override
                public @NotNull String translationKey() {
                    return null;
                }

                @Override
                public void setMetadata(@NotNull String metadataKey, @NotNull MetadataValue newMetadataValue) {

                }

                @Override
                public @NotNull List<MetadataValue> getMetadata(@NotNull String metadataKey) {
                    return null;
                }

                @Override
                public boolean hasMetadata(@NotNull String metadataKey) {
                    return false;
                }

                @Override
                public void removeMetadata(@NotNull String metadataKey, @NotNull Plugin owningPlugin) {

                }
            };
        }

        @Override
        public @NotNull Block getBlockAt(@NotNull Location location) {
            return null;
        }

        @Override
        public int getHighestBlockYAt(int i, int j) {
            return 0;
        }

        @Override
        public int getHighestBlockYAt(@NotNull Location location) {
            return 0;
        }

        @Override
        public @NotNull Block getHighestBlockAt(int i, int j) {
            return null;
        }

        @Override
        public @NotNull Block getHighestBlockAt(@NotNull Location location) {
            return null;
        }

        @Override
        public int getHighestBlockYAt(int i, int j, @NotNull HeightmapType heightmapType) throws UnsupportedOperationException {
            return 0;
        }

        @Override
        public int getHighestBlockYAt(int i, int j, @NotNull HeightMap heightMap) {
            return 0;
        }

        @Override
        public int getHighestBlockYAt(@NotNull Location location, @NotNull HeightMap heightMap) {
            return 0;
        }

        @Override
        public @NotNull Block getHighestBlockAt(int i, int j, @NotNull HeightMap heightMap) {
            return null;
        }

        @Override
        public @NotNull Block getHighestBlockAt(@NotNull Location location, @NotNull HeightMap heightMap) {
            return null;
        }

        @Override
        public @NotNull Chunk getChunkAt(int i, int j) {
            return null;
        }

        @Override
        public @NotNull Chunk getChunkAt(@NotNull Location location) {
            return null;
        }

        @Override
        public @NotNull Chunk getChunkAt(@NotNull Block block) {
            return null;
        }

        @Override
        public boolean isChunkLoaded(@NotNull Chunk chunk) {
            return false;
        }

        @Override
        public @NotNull Chunk[] getLoadedChunks() {
            return new Chunk[0];
        }

        @Override
        public void loadChunk(@NotNull Chunk chunk) {

        }

        @Override
        public boolean isChunkLoaded(int i, int j) {
            return false;
        }

        @Override
        public boolean isChunkGenerated(int i, int j) {
            return false;
        }

        @Override
        public boolean isChunkInUse(int i, int j) {
            return false;
        }

        @Override
        public void loadChunk(int i, int j) {

        }

        @Override
        public boolean loadChunk(int i, int j, boolean bl) {
            return false;
        }

        @Override
        public boolean unloadChunk(@NotNull Chunk chunk) {
            return false;
        }

        @Override
        public boolean unloadChunk(int i, int j) {
            return false;
        }

        @Override
        public boolean unloadChunk(int i, int j, boolean bl) {
            return false;
        }

        @Override
        public boolean unloadChunkRequest(int i, int j) {
            return false;
        }

        @Override
        public boolean regenerateChunk(int i, int j) {
            return false;
        }

        @Override
        public boolean refreshChunk(int i, int j) {
            return false;
        }

        @Override
        public boolean isChunkForceLoaded(int i, int j) {
            return false;
        }

        @Override
        public void setChunkForceLoaded(int i, int j, boolean bl) {

        }

        @Override
        public @NotNull Collection<Chunk> getForceLoadedChunks() {
            return null;
        }

        @Override
        public boolean addPluginChunkTicket(int i, int j, @NotNull Plugin plugin) {
            return false;
        }

        @Override
        public boolean removePluginChunkTicket(int i, int j, @NotNull Plugin plugin) {
            return false;
        }

        @Override
        public void removePluginChunkTickets(@NotNull Plugin plugin) {

        }

        @Override
        public @NotNull Collection<Plugin> getPluginChunkTickets(int i, int j) {
            return null;
        }

        @Override
        public @NotNull Map<Plugin, Collection<Chunk>> getPluginChunkTickets() {
            return null;
        }

        @Override
        public @NotNull Item dropItem(@NotNull Location location, @NotNull ItemStack itemStack) {
            return null;
        }

        @Override
        public @NotNull Item dropItem(@NotNull Location location, @NotNull ItemStack itemStack, @Nullable Consumer<Item> consumer) {
            return null;
        }

        @Override
        public @NotNull Item dropItemNaturally(@NotNull Location location, @NotNull ItemStack itemStack) {
            return null;
        }

        @Override
        public @NotNull Item dropItemNaturally(@NotNull Location location, @NotNull ItemStack itemStack, @Nullable Consumer<Item> consumer) {
            return null;
        }

        @Override
        public @NotNull Arrow spawnArrow(@NotNull Location location, @NotNull Vector vector, float f, float g) {
            return null;
        }

        @Override
        public <T extends AbstractArrow> @NotNull T spawnArrow(@NotNull Location location, @NotNull Vector vector, float f, float g, @NotNull Class<T> class_) {
            return null;
        }

        @Override
        public boolean generateTree(@NotNull Location location, @NotNull TreeType treeType) {
            return false;
        }

        @Override
        public boolean generateTree(@NotNull Location location, @NotNull TreeType treeType, @NotNull BlockChangeDelegate blockChangeDelegate) {
            return false;
        }

        @Override
        public @NotNull LightningStrike strikeLightning(@NotNull Location location) {
            return null;
        }

        @Override
        public @NotNull LightningStrike strikeLightningEffect(@NotNull Location location) {
            return null;
        }

        @Override
        public @Nullable Location findLightningRod(@NotNull Location location) {
            return null;
        }

        @Override
        public @Nullable Location findLightningTarget(@NotNull Location location) {
            return null;
        }

        @Override
        public @NotNull List<Entity> getEntities() {
            return null;
        }

        @Override
        public @NotNull List<LivingEntity> getLivingEntities() {
            return null;
        }

        @Override
        public @NotNull <T extends Entity> Collection<T> getEntitiesByClass(@NotNull Class<T>... classs) {
            return null;
        }

        @Override
        public @NotNull <T extends Entity> Collection<T> getEntitiesByClass(@NotNull Class<T> class_) {
            return null;
        }

        @Override
        public @NotNull Collection<Entity> getEntitiesByClasses(@NotNull Class<?>... classs) {
            return null;
        }

        @Override
        public @NotNull CompletableFuture<Chunk> getChunkAtAsync(int i, int j, boolean bl, boolean bl2) {
            return null;
        }

        @Override
        public @NotNull NamespacedKey getKey() {
            return null;
        }

        @Override
        public @NotNull List<Player> getPlayers() {
            return null;
        }

        @Override
        public @NotNull Collection<Entity> getNearbyEntities(@NotNull Location location, double d, double e, double f) {
            return null;
        }

        @Override
        public @Nullable Entity getEntity(@NotNull UUID uUID) {
            return null;
        }

        @Override
        public @NotNull Collection<Entity> getNearbyEntities(@NotNull Location location, double d, double e, double f, @Nullable Predicate<Entity> predicate) {
            return null;
        }

        @Override
        public @NotNull Collection<Entity> getNearbyEntities(@NotNull BoundingBox boundingBox) {
            return null;
        }

        @Override
        public @NotNull Collection<Entity> getNearbyEntities(@NotNull BoundingBox boundingBox, @Nullable Predicate<Entity> predicate) {
            return null;
        }

        @Override
        public @Nullable RayTraceResult rayTraceEntities(@NotNull Location location, @NotNull Vector vector, double d) {
            return null;
        }

        @Override
        public @Nullable RayTraceResult rayTraceEntities(@NotNull Location location, @NotNull Vector vector, double d, double e) {
            return null;
        }

        @Override
        public @Nullable RayTraceResult rayTraceEntities(@NotNull Location location, @NotNull Vector vector, double d, @Nullable Predicate<Entity> predicate) {
            return null;
        }

        @Override
        public @Nullable RayTraceResult rayTraceEntities(@NotNull Location location, @NotNull Vector vector, double d, double e, @Nullable Predicate<Entity> predicate) {
            return null;
        }

        @Override
        public @Nullable RayTraceResult rayTraceBlocks(@NotNull Location location, @NotNull Vector vector, double d) {
            return null;
        }

        @Override
        public @Nullable RayTraceResult rayTraceBlocks(@NotNull Location location, @NotNull Vector vector, double d, @NotNull FluidCollisionMode fluidCollisionMode) {
            return null;
        }

        @Override
        public @Nullable RayTraceResult rayTraceBlocks(@NotNull Location location, @NotNull Vector vector, double d, @NotNull FluidCollisionMode fluidCollisionMode, boolean bl) {
            return null;
        }

        @Override
        public @Nullable RayTraceResult rayTrace(@NotNull Location location, @NotNull Vector vector, double d, @NotNull FluidCollisionMode fluidCollisionMode, boolean bl, double e, @Nullable Predicate<Entity> predicate) {
            return null;
        }

        @Override
        public @NotNull Location getSpawnLocation() {
            return null;
        }

        @Override
        public boolean setSpawnLocation(@NotNull Location location) {
            return false;
        }

        @Override
        public boolean setSpawnLocation(int i, int j, int k, float f) {
            return false;
        }

        @Override
        public boolean setSpawnLocation(int i, int j, int k) {
            return false;
        }

        @Override
        public long getTime() {
            return 0;
        }

        @Override
        public void setTime(long l) {

        }

        @Override
        public long getFullTime() {
            return 0;
        }

        @Override
        public void setFullTime(long l) {

        }

        @Override
        public boolean isDayTime() {
            return false;
        }

        @Override
        public long getGameTime() {
            return 0;
        }

        @Override
        public boolean hasStorm() {
            return false;
        }

        @Override
        public void setStorm(boolean bl) {

        }

        @Override
        public int getWeatherDuration() {
            return 0;
        }

        @Override
        public void setWeatherDuration(int i) {

        }

        @Override
        public boolean isThundering() {
            return false;
        }

        @Override
        public void setThundering(boolean bl) {

        }

        @Override
        public int getThunderDuration() {
            return 0;
        }

        @Override
        public void setThunderDuration(int i) {

        }

        @Override
        public boolean isClearWeather() {
            return false;
        }

        @Override
        public void setClearWeatherDuration(int i) {

        }

        @Override
        public int getClearWeatherDuration() {
            return 0;
        }

        @Override
        public boolean createExplosion(double d, double e, double f, float g) {
            return false;
        }

        @Override
        public boolean createExplosion(double d, double e, double f, float g, boolean bl) {
            return false;
        }

        @Override
        public boolean createExplosion(double d, double e, double f, float g, boolean bl, boolean bl2) {
            return false;
        }

        @Override
        public boolean createExplosion(double d, double e, double f, float g, boolean bl, boolean bl2, @Nullable Entity entity) {
            return false;
        }

        @Override
        public boolean createExplosion(@NotNull Location location, float f) {
            return false;
        }

        @Override
        public boolean createExplosion(@NotNull Location location, float f, boolean bl) {
            return false;
        }

        @Override
        public boolean createExplosion(@Nullable Entity entity, @NotNull Location location, float f, boolean bl, boolean bl2) {
            return false;
        }

        @Override
        public boolean createExplosion(@NotNull Location location, float f, boolean bl, boolean bl2) {
            return false;
        }

        @Override
        public boolean createExplosion(@NotNull Location location, float f, boolean bl, boolean bl2, @Nullable Entity entity) {
            return false;
        }

        @Override
        public boolean getPVP() {
            return false;
        }

        @Override
        public void setPVP(boolean bl) {

        }

        @Override
        public @Nullable ChunkGenerator getGenerator() {
            return null;
        }

        @Override
        public @Nullable BiomeProvider getBiomeProvider() {
            return null;
        }

        @Override
        public void save() {

        }

        @Override
        public @NotNull List<BlockPopulator> getPopulators() {
            return null;
        }

        @Override
        public @NotNull FallingBlock spawnFallingBlock(@NotNull Location location, @NotNull MaterialData materialData) throws IllegalArgumentException {
            return null;
        }

        @Override
        public @NotNull FallingBlock spawnFallingBlock(@NotNull Location location, @NotNull BlockData blockData) throws IllegalArgumentException {
            return null;
        }

        @Override
        public @NotNull FallingBlock spawnFallingBlock(@NotNull Location location, @NotNull Material material, byte b) throws IllegalArgumentException {
            return null;
        }

        @Override
        public void playEffect(@NotNull Location location, @NotNull Effect effect, int i) {

        }

        @Override
        public void playEffect(@NotNull Location location, @NotNull Effect effect, int i, int j) {

        }

        @Override
        public <T> void playEffect(@NotNull Location location, @NotNull Effect effect, @Nullable T object) {

        }

        @Override
        public <T> void playEffect(@NotNull Location location, @NotNull Effect effect, @Nullable T object, int i) {

        }

        @Override
        public @NotNull ChunkSnapshot getEmptyChunkSnapshot(int i, int j, boolean bl, boolean bl2) {
            return null;
        }

        @Override
        public void setSpawnFlags(boolean bl, boolean bl2) {

        }

        @Override
        public boolean getAllowAnimals() {
            return false;
        }

        @Override
        public boolean getAllowMonsters() {
            return false;
        }

        @Override
        public @NotNull Biome getBiome(int i, int j) {
            return null;
        }

        @Override
        public void setBiome(int i, int j, @NotNull Biome biome) {

        }

        @Override
        public double getTemperature(int i, int j) {
            return 0;
        }

        @Override
        public double getTemperature(int i, int j, int k) {
            return 0;
        }

        @Override
        public double getHumidity(int i, int j) {
            return 0;
        }

        @Override
        public double getHumidity(int i, int j, int k) {
            return 0;
        }

        @Override
        public int getLogicalHeight() {
            return 0;
        }

        @Override
        public boolean isNatural() {
            return false;
        }

        @Override
        public boolean isBedWorks() {
            return false;
        }

        @Override
        public boolean hasSkyLight() {
            return false;
        }

        @Override
        public boolean hasCeiling() {
            return false;
        }

        @Override
        public boolean isPiglinSafe() {
            return false;
        }

        @Override
        public boolean isRespawnAnchorWorks() {
            return false;
        }

        @Override
        public boolean hasRaids() {
            return false;
        }

        @Override
        public boolean isUltraWarm() {
            return false;
        }

        @Override
        public int getSeaLevel() {
            return 0;
        }

        @Override
        public boolean getKeepSpawnInMemory() {
            return false;
        }

        @Override
        public void setKeepSpawnInMemory(boolean bl) {

        }

        @Override
        public boolean isAutoSave() {
            return false;
        }

        @Override
        public void setAutoSave(boolean bl) {

        }

        @Override
        public void setDifficulty(@NotNull Difficulty difficulty) {

        }

        @Override
        public @NotNull Difficulty getDifficulty() {
            return null;
        }

        @Override
        public @NotNull File getWorldFolder() {
            return null;
        }

        @Override
        public @Nullable WorldType getWorldType() {
            return null;
        }

        @Override
        public boolean canGenerateStructures() {
            return false;
        }

        @Override
        public boolean isHardcore() {
            return false;
        }

        @Override
        public void setHardcore(boolean bl) {

        }

        @Override
        public long getTicksPerAnimalSpawns() {
            return 0;
        }

        @Override
        public void setTicksPerAnimalSpawns(int i) {

        }

        @Override
        public long getTicksPerMonsterSpawns() {
            return 0;
        }

        @Override
        public void setTicksPerMonsterSpawns(int i) {

        }

        @Override
        public long getTicksPerWaterSpawns() {
            return 0;
        }

        @Override
        public void setTicksPerWaterSpawns(int i) {

        }

        @Override
        public long getTicksPerWaterAmbientSpawns() {
            return 0;
        }

        @Override
        public void setTicksPerWaterAmbientSpawns(int i) {

        }

        @Override
        public long getTicksPerWaterUndergroundCreatureSpawns() {
            return 0;
        }

        @Override
        public void setTicksPerWaterUndergroundCreatureSpawns(int i) {

        }

        @Override
        public long getTicksPerAmbientSpawns() {
            return 0;
        }

        @Override
        public void setTicksPerAmbientSpawns(int i) {

        }

        @Override
        public int getMonsterSpawnLimit() {
            return 0;
        }

        @Override
        public void setMonsterSpawnLimit(int i) {

        }

        @Override
        public int getAnimalSpawnLimit() {
            return 0;
        }

        @Override
        public void setAnimalSpawnLimit(int i) {

        }

        @Override
        public int getWaterAnimalSpawnLimit() {
            return 0;
        }

        @Override
        public void setWaterAnimalSpawnLimit(int i) {

        }

        @Override
        public int getWaterUndergroundCreatureSpawnLimit() {
            return 0;
        }

        @Override
        public void setWaterUndergroundCreatureSpawnLimit(int i) {

        }

        @Override
        public int getWaterAmbientSpawnLimit() {
            return 0;
        }

        @Override
        public void setWaterAmbientSpawnLimit(int i) {

        }

        @Override
        public int getAmbientSpawnLimit() {
            return 0;
        }

        @Override
        public void setAmbientSpawnLimit(int i) {

        }

        @Override
        public void playSound(@NotNull Location location, @NotNull Sound sound, float f, float g) {

        }

        @Override
        public void playSound(@NotNull Location location, @NotNull String string, float f, float g) {

        }

        @Override
        public void playSound(@NotNull Location location, @NotNull Sound sound, @NotNull SoundCategory soundCategory, float f, float g) {

        }

        @Override
        public void playSound(@NotNull Location location, @NotNull String string, @NotNull SoundCategory soundCategory, float f, float g) {

        }

        @Override
        public @NotNull String[] getGameRules() {
            return new String[0];
        }

        @Override
        public @Nullable String getGameRuleValue(@Nullable String string) {
            return null;
        }

        @Override
        public boolean setGameRuleValue(@NotNull String string, @NotNull String string2) {
            return false;
        }

        @Override
        public boolean isGameRule(@NotNull String string) {
            return false;
        }

        @Override
        public <T> @Nullable T getGameRuleValue(@NotNull GameRule<T> gameRule) {
            return null;
        }

        @Override
        public <T> @Nullable T getGameRuleDefault(@NotNull GameRule<T> gameRule) {
            return null;
        }

        @Override
        public <T> boolean setGameRule(@NotNull GameRule<T> gameRule, @NotNull T object) {
            return false;
        }

        @Override
        public @NotNull WorldBorder getWorldBorder() {
            return null;
        }

        @Override
        public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i) {

        }

        @Override
        public void spawnParticle(@NotNull Particle particle, double d, double e, double f, int i) {

        }

        @Override
        public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, @Nullable T object) {

        }

        @Override
        public <T> void spawnParticle(@NotNull Particle particle, double d, double e, double f, int i, @Nullable T object) {

        }

        @Override
        public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double d, double e, double f) {

        }

        @Override
        public void spawnParticle(@NotNull Particle particle, double d, double e, double f, int i, double g, double h, double j) {

        }

        @Override
        public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double d, double e, double f, @Nullable T object) {

        }

        @Override
        public <T> void spawnParticle(@NotNull Particle particle, double d, double e, double f, int i, double g, double h, double j, @Nullable T object) {

        }

        @Override
        public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double d, double e, double f, double g) {

        }

        @Override
        public void spawnParticle(@NotNull Particle particle, double d, double e, double f, int i, double g, double h, double j, double k) {

        }

        @Override
        public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double d, double e, double f, double g, @Nullable T object) {

        }

        @Override
        public <T> void spawnParticle(@NotNull Particle particle, @Nullable List<Player> list, @Nullable Player player, double d, double e, double f, int i, double g, double h, double j, double k, @Nullable T object, boolean bl) {

        }

        @Override
        public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double d, double e, double f, double g, @Nullable T object, boolean bl) {

        }

        @Override
        public <T> void spawnParticle(@NotNull Particle particle, double d, double e, double f, int i, double g, double h, double j, double k, @Nullable T object, boolean bl) {

        }

        @Override
        public @Nullable Location locateNearestStructure(@NotNull Location location, @NotNull StructureType structureType, int i, boolean bl) {
            return null;
        }

        @Override
        public @Nullable Location locateNearestBiome(@NotNull Location location, @NotNull Biome biome, int i) {
            return null;
        }

        @Override
        public @Nullable Location locateNearestBiome(@NotNull Location location, @NotNull Biome biome, int i, int j) {
            return null;
        }

        @Override
        public boolean isUltrawarm() {
            return false;
        }

        @Override
        public double getCoordinateScale() {
            return 0;
        }

        @Override
        public boolean hasSkylight() {
            return false;
        }

        @Override
        public boolean hasBedrockCeiling() {
            return false;
        }

        @Override
        public boolean doesBedWork() {
            return false;
        }

        @Override
        public boolean doesRespawnAnchorWork() {
            return false;
        }

        @Override
        public boolean isFixedTime() {
            return false;
        }

        @Override
        public @NotNull Collection<Material> getInfiniburn() {
            return null;
        }

        @Override
        public void sendGameEvent(@Nullable Entity entity, @NotNull GameEvent gameEvent, @NotNull Vector vector) {

        }

        @Override
        public int getViewDistance() {
            return 0;
        }

        @Override
        public void setViewDistance(int i) {

        }

        @Override
        public int getNoTickViewDistance() {
            return 0;
        }

        @Override
        public void setNoTickViewDistance(int i) {

        }

        @Override
        public int getSendViewDistance() {
            return 0;
        }

        @Override
        public void setSendViewDistance(int i) {

        }

        @Override
        public @NotNull Spigot spigot() {
            return null;
        }

        @Override
        public @Nullable Raid locateNearestRaid(@NotNull Location location, int i) {
            return null;
        }

        @Override
        public @NotNull List<Raid> getRaids() {
            return null;
        }

        @Override
        public @Nullable DragonBattle getEnderDragonBattle() {
            return null;
        }

        @Override
        public @NotNull Biome getBiome(@NotNull Location location) {
            return null;
        }

        @Override
        public @NotNull Biome getBiome(int i, int j, int k) {
            return null;
        }

        @Override
        public void setBiome(@NotNull Location location, @NotNull Biome biome) {

        }

        @Override
        public void setBiome(int i, int j, int k, @NotNull Biome biome) {

        }

        @Override
        public @NotNull BlockState getBlockState(@NotNull Location location) {
            return null;
        }

        @Override
        public @NotNull BlockState getBlockState(int i, int j, int k) {
            return null;
        }

        @Override
        public @NotNull BlockData getBlockData(@NotNull Location location) {
            return null;
        }

        @Override
        public @NotNull BlockData getBlockData(int i, int j, int k) {
            return null;
        }

        @Override
        public @NotNull Material getType(@NotNull Location location) {
            return null;
        }

        @Override
        public @NotNull Material getType(int i, int j, int k) {
            return null;
        }

        @Override
        public void setBlockData(@NotNull Location location, @NotNull BlockData blockData) {

        }

        @Override
        public void setBlockData(int i, int j, int k, @NotNull BlockData blockData) {

        }

        @Override
        public void setType(@NotNull Location location, @NotNull Material material) {

        }

        @Override
        public void setType(int i, int j, int k, @NotNull Material material) {

        }

        @Override
        public boolean generateTree(@NotNull Location location, @NotNull Random random, @NotNull TreeType treeType) {
            return false;
        }

        @Override
        public boolean generateTree(@NotNull Location location, @NotNull Random random, @NotNull TreeType treeType, @Nullable Consumer<BlockState> consumer) {
            return false;
        }

        @Override
        public @NotNull Entity spawnEntity(@NotNull Location location, @NotNull EntityType entityType) {
            return null;
        }

        @Override
        public @NotNull Entity spawnEntity(@NotNull Location location, @NotNull EntityType entityType, boolean bl) {
            return null;
        }

        @Override
        public <T extends Entity> @NotNull T spawn(@NotNull Location location, @NotNull Class<T> class_) throws IllegalArgumentException {
            return null;
        }

        @Override
        public <T extends Entity> @NotNull T spawn(@NotNull Location location, @NotNull Class<T> class_, @Nullable Consumer<T> consumer, CreatureSpawnEvent.@NotNull SpawnReason spawnReason) throws IllegalArgumentException {
            return null;
        }

        @Override
        public <T extends Entity> @NotNull T spawn(@NotNull Location location, @NotNull Class<T> class_, boolean bl, @Nullable Consumer<T> consumer) throws IllegalArgumentException {
            return null;
        }

        @Override
        public @NotNull String getName() {
            return null;
        }

        @Override
        public @NotNull UUID getUID() {
            return null;
        }

        @Override
        public @NotNull Environment getEnvironment() {
            return null;
        }

        @Override
        public long getSeed() {
            return 0;
        }

        @Override
        public int getMinHeight() {
            return 0;
        }

        @Override
        public int getMaxHeight() {
            return 0;
        }

        @Override
        public void setMetadata(@NotNull String string, @NotNull MetadataValue metadataValue) {

        }

        @Override
        public @NotNull List<MetadataValue> getMetadata(@NotNull String string) {
            return null;
        }

        @Override
        public boolean hasMetadata(@NotNull String string) {
            return false;
        }

        @Override
        public void removeMetadata(@NotNull String string, @NotNull Plugin plugin) {

        }

        @Override
        public void sendPluginMessage(@NotNull Plugin plugin, @NotNull String string, @NotNull byte[] bs) {

        }

        @Override
        public @NotNull Set<String> getListeningPluginChannels() {
            return null;
        }
    };

//    @Test
//    public void sendVisualisationTest() {
//        TeleportVisManager.init(WayStonesPlugin.getPlugin(WayStonesPlugin.class));
//        TeleportVisManager.sendVisualisation(null, null, new Location(dummyWorld, 0, 60, 0));
//    }
}
