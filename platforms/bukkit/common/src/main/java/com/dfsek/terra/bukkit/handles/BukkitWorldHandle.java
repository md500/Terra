package com.dfsek.terra.bukkit.handles;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dfsek.terra.api.block.state.BlockState;
import com.dfsek.terra.api.entity.EntityType;
import com.dfsek.terra.api.handle.WorldHandle;
import com.dfsek.terra.bukkit.util.BukkitUtils;
import com.dfsek.terra.bukkit.world.block.data.BukkitBlockState;

public class BukkitWorldHandle implements WorldHandle {
    private static final Logger logger = LoggerFactory.getLogger(BukkitWorldHandle.class);
    private final BlockState air;

    public BukkitWorldHandle() {
        this.air = BukkitBlockState.newInstance(Material.AIR.createBlockData());
    }

    @Override
    public synchronized @NotNull BlockState createBlockState(@NotNull String data) {
        if(data.equals("minecraft:grass")) { //TODO: remove in 7.0
            data = "minecraft:short_grass";
            logger.warn(
                "Translating minecraft:grass to minecraft:short_grass. In 1.20.3 minecraft:grass was renamed to minecraft:short_grass" +
                ". You are advised to perform this rename in your config backs as this translation will be removed in the next major " +
                "version of Terra.");
        }
        BlockData bukkitData = Bukkit.createBlockData(extractId(data));
        return BukkitBlockState.newInstance(bukkitData);
    }

    @Override
    public @NotNull BlockState air() {
        return air;
    }

    @Override
    public @NotNull EntityType getEntity(@NotNull String id) {
        return BukkitUtils.getEntityType(extractId(id));
    }

    private static @NotNull String extractId(@NotNull String input) {
        int brace = input.indexOf('{');
        return brace == -1
               ? input
               : input.substring(0, brace).trim();
    }
}
