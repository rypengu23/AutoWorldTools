package com.github.rypengu23.autoworldtools.util;

import com.github.rypengu23.autoworldtools.config.CommandMessage;
import com.github.rypengu23.autoworldtools.config.ConfigLoader;
import com.github.rypengu23.autoworldtools.config.MainConfig;
import com.github.rypengu23.autoworldtools.config.MessageConfig;
import org.bukkit.command.CommandSender;

public class CheckUtil {

    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public CheckUtil(){
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    public boolean checkPermissionReset(CommandSender sender){

        if(sender.hasPermission("autoWorldTools.reset")){
            return true;
        }

        sender.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.AutoWorldTools_CommandFailure);
        return false;
    }

    public boolean checkPermissionInfo(CommandSender sender){

        if(sender.hasPermission("autoResetWorld.info")){
            return true;
        }

        sender.sendMessage("§c[ARW]§f権限を所持していません。");
        return false;
    }

    /**
     * 引数がnullまたは空白か判定
     * @param str
     * @return
     */
    public boolean checkNullOrBlank(String str){
        if(str == null || str.equals("")){
            return true;
        }
        return false;
    }
}
