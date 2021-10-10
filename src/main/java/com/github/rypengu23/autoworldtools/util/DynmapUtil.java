package com.github.rypengu23.autoworldtools.util;

import com.github.rypengu23.autoworldtools.config.ConfigLoader;
import com.github.rypengu23.autoworldtools.config.ConsoleMessage;
import com.github.rypengu23.autoworldtools.config.MainConfig;
import com.github.rypengu23.autoworldtools.config.MessageConfig;
import com.github.rypengu23.autoworldtools.model.ResetWorldModel;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class DynmapUtil {

    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public DynmapUtil() {
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    public void purgeMapOfWorldType(int worldType){

        //削除可否の確認
        if(!checkMapResetForWorldType(worldType)){
            return;
        }

        //ワールド名リストの取得
        ArrayList<ResetWorldModel> worldList = new ArrayList<>();
        if (worldType == 0) {
            worldList = mainConfig.getResetWorldNameOfNormal();
        } else if (worldType == 1) {
            worldList = mainConfig.getResetWorldNameOfNether();
        } else {
            worldList = mainConfig.getResetWorldNameOfEnd();
        }

        for (ResetWorldModel worldInfo : worldList) {
            deleteMapDataOfWorldName(worldInfo.getWorldName());
        }
    }

    public void deleteMapDataOfWorldName(String worldName){

        ConvertUtil convertUtil = new ConvertUtil();
        if(Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "dynmap purgeworld "+ worldName)){
            Bukkit.getLogger().info("[AutoWorldTools] " + convertUtil.placeholderUtil("{worldname}", worldName, ConsoleMessage.DynmapUtil_PurgeMapComplete));
        }else{
            Bukkit.getLogger().warning("[AutoWorldTools] " + convertUtil.placeholderUtil("{worldname}", worldName, ConsoleMessage.DynmapUtil_PurgeMapFailure));
        }
    }

    public boolean checkMapResetForWorldType(int worldType){

        if(worldType == 0 && mainConfig.isMapPurgeAtResetOfNormal()){
            return true;
        }else if(worldType == 1 && mainConfig.isMapPurgeAtResetOfNether()){
            return true;
        }else if(worldType == 2 && mainConfig.isMapPurgeAtResetOfEnd()){
            return true;
        }
        return false;
    }

}
