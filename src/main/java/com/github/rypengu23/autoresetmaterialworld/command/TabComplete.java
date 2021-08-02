package com.github.rypengu23.autoresetmaterialworld.command;

import com.github.rypengu23.autoresetmaterialworld.util.CheckUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> onCompList = new ArrayList<>();

        CheckUtil checkUtil = new CheckUtil();

        if(args.length == 1) {

            if(sender.hasPermission("autoResetWorld.use")) {
                onCompList.add("normal");
                onCompList.add("nether");
                onCompList.add("end");
                onCompList.add("all");
                onCompList.add("reload");
            }
            if(sender.hasPermission("autoResetWorld.info")) {
                onCompList.add("info");
            }
        }

        if((command.getName().equalsIgnoreCase("autoreset") || command.getName().equalsIgnoreCase("ar")) && onCompList.size() > 0){
            return onCompList;
        }
        return null;
    }


}

