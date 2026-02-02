package com.dfsek.terra.bukkit.nms.v1_21_8;

import com.dfsek.terra.bukkit.TerraBukkitPlugin;
import com.dfsek.terra.bukkit.nms.v1_21_8.processors.NetherProcess;
import com.dfsek.terra.bukkit.nms.v1_21_8.processors.OverworldProcess;
import com.dfsek.terra.bukkit.nms.v1_21_8.processors.TheEndProcess;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.Listener;


public class NMSChankLoadListener implements Listener {
    private final TerraBukkitPlugin plugin;

    public NMSChankLoadListener(TerraBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChunkGenerate(ChunkLoadEvent event) {
        if(!event.isNewChunk()) {
            return;
        }

        processChunk(event.getChunk());
    }

    private void processChunk(Chunk chunk) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            try {
                OverworldProcess.process(chunk);
                NetherProcess.process(chunk);
                TheEndProcess.process(chunk);
            } catch(Exception e) {
                plugin.getLogger().severe("Error fixing loot tables in chunk: " + e);
            }
        }, 5L);
    }
}
