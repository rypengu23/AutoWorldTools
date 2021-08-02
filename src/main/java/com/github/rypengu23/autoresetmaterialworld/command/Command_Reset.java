package com.github.rypengu23.autoresetmaterialworld.command;

import com.github.rypengu23.autoresetmaterialworld.Config;
import com.github.rypengu23.autoresetmaterialworld.util.CreateWarpGateUtil;
import com.github.rypengu23.autoresetmaterialworld.util.ResetUtil;
import org.bukkit.plugin.Plugin;

public class Command_Reset {

    private Config config;
    private final Plugin plugin;

    public Command_Reset(Plugin plugin){
        this.plugin = plugin;
    }

    public void resetWorld(int worldType){

        config = new Config(plugin);

        //ワールド再生成
        ResetUtil resetUtil = new ResetUtil(plugin);
        resetUtil.regenerateWorld(worldType);

        //ゲート生成
        if(config.isUseMultiversePortals()) {
            if ((worldType == 0 && config.isGateAutoBuildOfNormal()) || (worldType == 1 && config.isGateAutoBuildOfNether()) || (worldType == 2 && config.isGateAutoBuildOfEnd())) {
                CreateWarpGateUtil createWarpGateUtil = new CreateWarpGateUtil(plugin);
                createWarpGateUtil.createWarpGateUtil(worldType);
            }
        }
    }
}
