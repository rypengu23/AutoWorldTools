package com.github.rypengu23.autoworldtools;

import com.github.rypengu23.autoworldtools.command.*;
import com.github.rypengu23.autoworldtools.config.*;
import com.github.rypengu23.autoworldtools.watch.TimeSurveillance;
import com.onarandombox.MultiversePortals.MultiversePortals;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class AutoWorldTools extends JavaPlugin {

    //バージョン
    public static double pluginVersion = 1.7;

    //インスタンス
    private static AutoWorldTools instance = null;

    //タスク保管
    public static BukkitTask timeSurveillance;
    public static BukkitTask backupTask;

    //Config
    private ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    //Multiverse
    public static MultiversePortals portals;

    //DiscordSRV
    public static DiscordSRV discordSRV;

    //Dynmap
    public static Plugin dynmap;

    @Override
    public void onEnable() {

        instance = this;
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        configLoader.reloadConfig();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();

        //起動メッセージ
        //Startup message
        Bukkit.getLogger().info("[AutoWorldTools] == AutoWorldTools Ver"+ pluginVersion +" ==");
        Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.AutoWorldTools_startupPlugin);

        //Configの更新確認
        ConfigUpdater configUpdater = new ConfigUpdater();
        if(configUpdater.configUpdateCheck() == true){
            configLoader = new ConfigLoader();
            configLoader.reloadConfig();
            mainConfig = configLoader.getMainConfig();
            messageConfig = configLoader.getMessageConfig();
        }

        if (mainConfig.isUseMultiversePortals()) {
            //Multiverse-Portals接続
            //Connect Multiverse-Portals
            try {
                Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.AutoWorldTools_loadMultiversePortals);
                portals = (MultiversePortals) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Portals");
                Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.AutoWorldTools_loadCompMultiversePortals);
            } catch (NoClassDefFoundError e) {
                Bukkit.getLogger().warning("[AutoWorldTools] " + ConsoleMessage.AutoWorldTools_loadFailureMultiversePortals);
            }
        }
        if (mainConfig.isUseDiscordSRV()) {
            //DiscordSRV接続
            try {
                Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.AutoWorldTools_loadDiscordSRV);
                discordSRV = (DiscordSRV) Bukkit.getServer().getPluginManager().getPlugin("DiscordSRV");
                Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.AutoWorldTools_loadCompDiscordSRV);
            } catch (NoClassDefFoundError e) {
                Bukkit.getLogger().warning("[AutoWorldTools] " + ConsoleMessage.AutoWorldTools_loadFailureDiscordSRV);
            }
        }
        if (mainConfig.isUseDynmap()) {
            //Dynmap接続
            try {
                Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.AutoWorldTools_loadDynmap);
                dynmap = Bukkit.getServer().getPluginManager().getPlugin("dynmap");
                Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.AutoWorldTools_loadCompDynmap);
            } catch (NoClassDefFoundError e) {
                Bukkit.getLogger().warning("[AutoWorldTools] " + ConsoleMessage.AutoWorldTools_loadFailureDynmap);
            }
        }

        //コマンド入力時の入力補助
        //Command tab complete
        TabComplete tabComplete = new TabComplete();
        getCommand("autoworldtools").setTabCompleter(tabComplete);
        TabComplete tabComplete2 = new TabComplete();
        getCommand("awt").setTabCompleter(tabComplete2);

        //時刻監視
        //time surveillance
        if (mainConfig.isAutoReset()) {
            Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.AutoWorldTools_startupScheduler);
            TimeSurveillance timeSurveillance = new TimeSurveillance();
            timeSurveillance.timeSurveillance();
        }

        Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.AutoWorldTools_startupCompPlugin);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static AutoWorldTools getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("autoworldtools") || label.equalsIgnoreCase("awt")) {
            configLoader = new ConfigLoader();
            //引数があるかどうか
            if (args.length != 0) {

                if (args.length == 1) {
                    //Configリロード
                    if (args[0].equalsIgnoreCase("reload")) {
                        Command_Config command_config = new Command_Config();
                        command_config.reloadConfig(sender);

                    } else if (args[0].equalsIgnoreCase("help")) {
                        //helpコマンド ページ1
                        Command_Help command_help = new Command_Help();
                        command_help.showHelp(sender, "0");
                    } else {
                        //コマンドの形式が不正な場合
                        sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_CommandFailure);
                    }

                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("reset")) {
                        //リセット
                        Command_Reset reset = new Command_Reset();
                        if (args[1].equalsIgnoreCase("normal")) {
                            reset.resetWorld(sender, 0);

                        } else if (args[1].equalsIgnoreCase("nether")) {
                            reset.resetWorld(sender, 1);

                        } else if (args[1].equalsIgnoreCase("end")) {
                            reset.resetWorld(sender, 2);

                        } else if (args[1].equalsIgnoreCase("all")) {
                            reset.resetWorld(sender, 3);

                        } else if (args[1].equalsIgnoreCase("info")) {
                            reset.showResetInfo(sender);
                        } else {
                            //コマンドの形式が不正な場合
                            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_CommandFailure);
                        }

                    } else if (args[0].equalsIgnoreCase("backup")) {
                        //バックアップ
                        Command_Backup backup = new Command_Backup();
                        if (args[1].equalsIgnoreCase("info")) {
                            backup.showBackupInfo(sender);
                        } else {
                            backup.backupWorld(sender, args[1]);
                        }

                    } else if (args[0].equalsIgnoreCase("restart")) {
                        //再起動
                        Command_Restart restart = new Command_Restart();
                        if (args[1].equalsIgnoreCase("info")) {
                            restart.showRestartInfo(sender);
                        } else {
                            //コマンドの形式が不正な場合
                            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_CommandFailure);
                        }

                    } else if (args[0].equalsIgnoreCase("help")) {
                        //helpコマンド
                        Command_Help command_help = new Command_Help();
                        command_help.showHelp(sender, args[1]);
                    } else {
                        //コマンドの形式が不正な場合
                        sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_CommandFailure);
                    }
                } else {
                    //コマンドの形式が不正な場合
                    sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_CommandFailure);
                }

            } else {
                //引数が無ければバージョン情報
                sender.sendMessage("§a" + messageConfig.getPrefix() + " §fAutoWorldTools Ver1.1");
                sender.sendMessage("§a" + messageConfig.getPrefix() + " §fDeveloper: rypengu23");
            }

        }
        return false;
    }
}