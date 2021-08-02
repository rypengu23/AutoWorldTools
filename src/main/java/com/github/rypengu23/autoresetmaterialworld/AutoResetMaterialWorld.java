package com.github.rypengu23.autoresetmaterialworld;

import com.github.rypengu23.autoresetmaterialworld.command.Command_Reset;
import com.github.rypengu23.autoresetmaterialworld.command.TabComplete;
import com.github.rypengu23.autoresetmaterialworld.util.CheckUtil;
import com.github.rypengu23.autoresetmaterialworld.watch.TimeSurveillance;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiversePortals.MultiversePortals;
import com.onarandombox.MultiversePortals.utils.PortalManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class AutoResetMaterialWorld extends JavaPlugin {

    private Config config;
    static MultiverseCore core;
    public static MVWorldManager worldManager;
    public static MultiversePortals portals;
    public static PortalManager portalManager;
    public static boolean resetStatus;

    @Override
    public void onEnable() {
        // Plugin startup logic
        config = new Config(this);
        Bukkit.getLogger().info("[ARW] == AutoResetWorld Ver"+ config.getVersion() +" ==");
        Bukkit.getLogger().info("[ARW] プラグインを起動します。");
        try {
            Bukkit.getLogger().info("[ARW] Multiverse-Coreの読み込みを行います。");
            core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
            worldManager = core.getMVWorldManager();
            Bukkit.getLogger().info("[ARW] Multiverse-Coreの読み込みが完了しました。");
        }catch(NoClassDefFoundError e){
            Bukkit.getLogger().warning("[ARW] Multiverse-Coreの読み込みに失敗しました。");
        }
        if(config.isUseMultiversePortals()) {
            try {
                Bukkit.getLogger().info("[ARW] Multiverse-Portalsの読み込みを行います。");
                portals = (MultiversePortals) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Portals");
                portalManager = portals.getPortalManager();
                Bukkit.getLogger().info("[ARW] Multiverse-Portalsの読み込みが完了しました。");
            }catch(NoClassDefFoundError e){
                Bukkit.getLogger().warning("[ARW] Multiverse-Portalsの読み込みに失敗しました。");
            }
        }

        //コマンド入力時の入力補助
        TabComplete tabComplete = new TabComplete();
        getCommand("autoreset").setTabCompleter(tabComplete);
        TabComplete tabComplete2 = new TabComplete();
        getCommand("ar").setTabCompleter(tabComplete2);

        //リセット時刻監視
        if (config.isAutoReset()) {
            Bukkit.getLogger().info("[ARW] スケジューラを起動。");
            TimeSurveillance timeSurveillance = new TimeSurveillance(this);
            timeSurveillance.timeSurveillance();
        }

        Bukkit.getLogger().info("[ARW] プラグインが起動しました。");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("[ARW] プラグインを終了しました。");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("autoreset") || label.equalsIgnoreCase("ar")) {
            //引数があるかどうか
            if (args.length != 0) {

                CheckUtil checkUtil = new CheckUtil();

                //権限が必要なコマンド

                //素材世界(通常)をリセット
                if (args[0].equalsIgnoreCase("normal")) {
                    if(!checkUtil.checkPermissionUse(sender)) {
                        return false;
                    }
                    sender.sendMessage("§b[ARW] §f素材世界(通常)のリセットを開始します。");
                    Command_Reset reset = new Command_Reset(this);
                    reset.resetWorld(0);
                    sender.sendMessage("§a[ARW] §f素材世界(通常)のリセットが完了しました。");

                } else if (args[0].equalsIgnoreCase("nether")) {
                    if(!checkUtil.checkPermissionUse(sender)) {
                        return false;
                    }
                    sender.sendMessage("§b[ARW] §f素材世界(ネザー)のリセットを開始します。");
                    Command_Reset reset = new Command_Reset(this);
                    reset.resetWorld(1);
                    sender.sendMessage("§a[ARW] §f素材世界(ネザー)のリセットが完了しました。");

                } else if (args[0].equalsIgnoreCase("end")) {
                    if(!checkUtil.checkPermissionUse(sender)) {
                        return false;
                    }
                    sender.sendMessage("§b[ARW] §f素材世界(エンド)のリセットを開始します。");
                    Command_Reset reset = new Command_Reset(this);
                    reset.resetWorld(2);
                    sender.sendMessage("§a[ARW] §f素材世界(エンド)のリセットが完了しました。");

                } else if (args[0].equalsIgnoreCase("all")) {
                    if(!checkUtil.checkPermissionUse(sender)) {
                        return false;
                    }
                    sender.sendMessage("§b[ARW] §f全ての素材世界のリセットを開始します。");
                    Command_Reset reset = new Command_Reset(this);
                    for(int i = 0; i<3; i++) {
                        reset.resetWorld(i);
                    }
                    sender.sendMessage("§b[ARW] §f全ての素材世界のリセットが完了しました。");


                } else if (args[0].equalsIgnoreCase("reload")) {
                    if(!checkUtil.checkPermissionUse(sender)) {
                        return false;
                    }
                    this.reloadConfig();
                    config = new Config(this);
                    try {
                        Bukkit.getLogger().info("[ARW] Multiverse-Coreの読み込みを行います。");
                        core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
                        worldManager = core.getMVWorldManager();
                        Bukkit.getLogger().info("[ARW] Multiverse-Coreの読み込みが完了しました。");
                    }catch(NoClassDefFoundError e){
                        Bukkit.getLogger().warning("[ARW] Multiverse-Coreの読み込みに失敗しました。");
                    }
                    if(config.isUseMultiversePortals()){
                        try {
                            Bukkit.getLogger().info("[ARW] Multiverse-Portalsの読み込みを行います。");
                            portals = (MultiversePortals) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Portals");
                            portalManager = portals.getPortalManager();
                            Bukkit.getLogger().info("[ARW] Multiverse-Portalsの読み込みが完了しました。");
                        }catch(NoClassDefFoundError e){
                            Bukkit.getLogger().warning("[ARW] Multiverse-Portalsの読み込みに失敗しました。");
                        }
                    }
                    sender.sendMessage("§a[ARW] §fConfigをリロードしました。");

                } else if (args[0].equalsIgnoreCase("info")) {
                    if(!checkUtil.checkPermissionInfo(sender)) {
                        return false;
                    }
                    StringBuilder weekStr = new StringBuilder();
                    for(int i = 0; i < config.getResetDayOfTheWeekList().length; i++) {
                        if(i!=0){
                            weekStr.append("･");
                        }
                        weekStr.append(config.getResetDayOfTheWeekList()[i]);
                    }

                    StringBuilder timeStr = new StringBuilder();
                    for(int i = 0; i < config.getResetTimeList().length; i++) {
                        if(i!=0){
                            timeStr.append("･");
                        }
                        timeStr.append(config.getResetTimeList()[i]);
                    }

                    sender.sendMessage("§a[ARW] §f素材世界のリセットは毎週§a"+ weekStr.toString() +"§f曜日の§a"+ timeStr.toString() +"§fに行われます。");
                }else{
                    sender.sendMessage("§a[ARW] §fコマンドが不正です。");
                }

             //権限が不要なコマンド

            } else {
                //無ければバージョン情報
                sender.sendMessage("§a[ARW] §fAutoResetMaterialWorld Ver1.0");
            }
        }
        return false;
    }

}
