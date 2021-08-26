package com.github.rypengu23.autoworldtools.command;

import com.github.rypengu23.autoworldtools.AutoWorldTools;
import com.github.rypengu23.autoworldtools.config.*;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiversePortals.MultiversePortals;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Command_Config {

    private final ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    public Command_Config(){
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    public void reloadConfig(CommandSender sender){

        //権限チェック
        if(!sender.hasPermission("autoWorldTools.reload")){
            sender.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.AutoWorldTools_DoNotHavePermission);
            return;
        }

        //Config再読み込み
        configLoader.reloadConfig();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();

        //Multiverse-Core再読み込み
        try {
            Bukkit.getLogger().info("[AutoWorldTools] "+ ConsoleMessage.AutoWorldTools_loadMultiverseCore);
            AutoWorldTools.core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
            Bukkit.getLogger().info("[AutoWorldTools] "+ ConsoleMessage.AutoWorldTools_loadCompMultiverseCore);
        }catch(NoClassDefFoundError e){
            Bukkit.getLogger().warning("[AutoWorldTools] "+ ConsoleMessage.AutoWorldTools_loadFailureMultiverseCore);
        }

        //Multiverse-Portals再読み込み
        if(mainConfig.isUseMultiversePortals()){
            try {
                Bukkit.getLogger().info("[AutoWorldTools] "+ ConsoleMessage.AutoWorldTools_loadMultiversePortals);
                AutoWorldTools.portals = (MultiversePortals) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Portals");
                Bukkit.getLogger().info("[AutoWorldTools] "+ ConsoleMessage.AutoWorldTools_loadCompMultiversePortals);
            }catch(NoClassDefFoundError e){
                Bukkit.getLogger().warning("[AutoWorldTools] "+ ConsoleMessage.AutoWorldTools_loadFailureMultiversePortals);
            }
        }

        //DiscordSRV再読み込み
        if(mainConfig.isUseDiscordSRV()){
            try {
                Bukkit.getLogger().info("[AutoWorldTools] "+ ConsoleMessage.AutoWorldTools_loadDiscordSRV);
                AutoWorldTools.discordSRV = (DiscordSRV) Bukkit.getServer().getPluginManager().getPlugin("DiscordSRV");
                Bukkit.getLogger().info("[AutoWorldTools] "+ ConsoleMessage.AutoWorldTools_loadCompDiscordSRV);
            }catch(NoClassDefFoundError e){
                Bukkit.getLogger().warning("[AutoWorldTools] "+ ConsoleMessage.AutoWorldTools_loadFailureDiscordSRV);
            }
        }

        //完了メッセージ
        sender.sendMessage("§a"+ messageConfig.getPrefix() +" §f"+ CommandMessage.AutoWorldTools_ConfigReload);
    }
}
