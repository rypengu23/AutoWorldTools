package com.github.rypengu23.autoworldtools.command;

import com.github.rypengu23.autoworldtools.util.CheckUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
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

            if(sender.hasPermission("autoWorldTools.reset") || sender.hasPermission("autoWorldTools.resetInfo")) {
                onCompList.add("reset");
            }
            if(sender.hasPermission("autoWorldTools.backup") || sender.hasPermission("autoWorldTools.backupInfo")) {
                onCompList.add("backup");
            }
            if(sender.hasPermission("autoWorldTools.reload")) {
                onCompList.add("reload");
            }
            onCompList.add("help");
        }

        if(args.length == 2) {

            if(args[0].equalsIgnoreCase("reset")){
                if(sender.hasPermission("autoWorldTools.reset")) {
                    onCompList.add("normal");
                    onCompList.add("nether");
                    onCompList.add("end");
                    onCompList.add("all");
                }
                if(sender.hasPermission("autoWorldTools.resetInfo")){
                    onCompList.add("info");
                }
            } else if(args[0].equalsIgnoreCase("backup")){
                if(sender.hasPermission("autoWorldTools.backup")) {
                    List<World> worldList = Bukkit.getWorlds();
                    List<String> worldNameList = new ArrayList<>();
                    for(World world:worldList){
                        onCompList.add(world.getName());
                    }
                }
                if(sender.hasPermission("autoWorldTools.backupInfo")){
                    onCompList.add("info");
                }
            }

        }

        if((command.getName().equalsIgnoreCase("autoworldtools") || command.getName().equalsIgnoreCase("awt")) && onCompList.size() > 0){
            return onCompList;
        }
        return null;
    }


}

