/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.RPGMakerDev.RPGMaker.EntityData.SpawnSystem;

import com.RPGMakerDev.RPGMaker.RPGMaker;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.metadata.MetadataValue;

/**
 *
 * @author matthew
 */
public class OreListener implements Listener {
    private RPGMaker plugin;
    public OreListener(RPGMaker plugin) {
        this.plugin = plugin;
    }
    
    public void OreBreakEvent(BlockBreakEvent event) {
        Block b = event.getBlock();
        if (b.hasMetadata("oreID")) {
            List<MetadataValue> list = b.getMetadata("oreID");
            int id = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getOwningPlugin().equals(plugin)) {
                    id = list.get(i).asInt();
                    break;
                }
            }
            if (id == 0) {return;}
            OreNode node = OreNode.ores.get(id);
            node.respawnEvent();
            
            event.setCancelled(true);
        }
    }
}
