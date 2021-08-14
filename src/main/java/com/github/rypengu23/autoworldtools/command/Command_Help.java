package com.github.rypengu23.autoworldtools.command;

import com.github.rypengu23.autoworldtools.config.CommandMessage;
import com.github.rypengu23.autoworldtools.config.ConfigLoader;
import com.github.rypengu23.autoworldtools.config.MainConfig;
import com.github.rypengu23.autoworldtools.config.MessageConfig;
import org.bukkit.command.CommandSender;
import sun.tools.jar.Main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Command_Help {

    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public Command_Help(){
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    public void showHelp(CommandSender sender, String page){

        int pageNumber = 0;
        //引数が数字かチェック
        try {
            pageNumber = Integer.parseInt(page);
        } catch(NumberFormatException e){
            sender.sendMessage("§c" + messageConfig.getPrefix() +"§f "+ CommandMessage.AutoWorldTools_CommandFailure);
            return;
        }

        List<String> showList = new ArrayList<>();
        showList.add(CommandMessage.Command_Help_Line1);
        //表示させるコマンドリストを取得
        if(sender.hasPermission("autoWorldTools.reset")){
            showList.add(CommandMessage.Command_Help_Reset);
        }
        if(sender.hasPermission("autoWorldTools.backup")){
            showList.add(CommandMessage.Command_Help_Backup);
        }
        if(sender.hasPermission("autoWorldTools.resetInfo")){
            showList.add(CommandMessage.Command_Help_Resetinfo);
        }
        if(sender.hasPermission("autoWorldTools.backupInfo")){
            showList.add(CommandMessage.Command_Help_Backupinfo);
        }
        if(sender.hasPermission("autoWorldTools.admin")){
            showList.add(CommandMessage.Command_Help_reload);
        }

        showList.add(CommandMessage.Command_Help_LineLast);

        for(String message:showList){
            sender.sendMessage(message);
        }
    }
}
