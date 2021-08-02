package com.github.rypengu23.autoresetmaterialworld.util;

import org.bukkit.command.CommandSender;

public class CheckUtil {

    public boolean checkPermissionUse(CommandSender sender){

        if(sender.hasPermission("autoResetWorld.use")){
            return true;
        }

        sender.sendMessage("§c[ARW]§f権限を所持していません。");
        return false;
    }

    public boolean checkPermissionInfo(CommandSender sender){

        if(sender.hasPermission("autoResetWorld.info")){
            return true;
        }

        sender.sendMessage("§c[ARW]§f権限を所持していません。");
        return false;
    }
}
