package com.github.rypengu23.autoworldtools.command;

import com.github.rypengu23.autoworldtools.AutoWorldTools;
import com.github.rypengu23.autoworldtools.config.CommandMessage;
import com.github.rypengu23.autoworldtools.config.ConfigLoader;
import com.github.rypengu23.autoworldtools.config.MainConfig;
import com.github.rypengu23.autoworldtools.config.MessageConfig;
import com.github.rypengu23.autoworldtools.util.BackupUtil;
import com.github.rypengu23.autoworldtools.util.ConvertUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Command_Backup {

    private final Plugin plugin;
    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public Command_Backup() {
        this.plugin = AutoWorldTools.getInstance();
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    public void backupWorld(CommandSender sender, String worldName) {

        //権限所持チェック
        if (!sender.hasPermission("autoWorldTools.backup")) {
            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_DoNotHavePermission);
            return;
        }

        //ワールド存在チェック
        if (Bukkit.getWorld(worldName) == null) {
            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_CommandBackupNotFoundWorld + worldName);
            return;
        }

        Runnable runnable = new BukkitRunnable() {
            @Override
            public void run() {

                //バックアップ開始メッセージ
                sender.sendMessage("§b" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_CommandBackupStart + worldName);

                BackupUtil backupUtil = new BackupUtil();

                //バックアップ実行
                backupUtil.createWorldFileZip(worldName);
                backupUtil.deleteOldFile(worldName);

                //バックアップ完了メッセージ
                sender.sendMessage("§a" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_CommandBackupComp + worldName);

            }
        };

        Bukkit.getServer().getScheduler().runTaskAsynchronously(AutoWorldTools.getInstance(), runnable);

    }

    public void showBackupInfo(CommandSender sender) {

        //権限所持チェック
        if (!sender.hasPermission("autoWorldTools.backupInfo")) {
            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_DoNotHavePermission);
            return;
        }

        ConvertUtil convertUtil = new ConvertUtil();

        //バックアップ曜日を取得
        ArrayList<Integer> backupDayOfWeekNumberList = new ArrayList<>();
        StringBuilder weekStr = new StringBuilder();
        String[] weekNameJP = {"日", "月", "火", "水", "木", "金", "土"};
        String[] weekNameEN = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};
        for (int i = 0; i < 7; i++) {
            for (String backupDay : mainConfig.getBackupDayOfTheWeekList()) {
                if (backupDay.equals(weekNameJP[i]) || backupDay.equals(weekNameEN[i])) {
                    backupDayOfWeekNumberList.add(i);
                }
            }
        }

        //メッセージ用に曜日をセット
        for (int i = 0; i < backupDayOfWeekNumberList.size(); i++) {
            if (i != 0) {
                weekStr.append("/");
            }
            weekStr.append(messageConfig.getDayOfWeekList()[backupDayOfWeekNumberList.get(i)]);
        }

        //メッセージ用に時間をセット
        StringBuilder timeStr = new StringBuilder();
        for (int i = 0; i < mainConfig.getBackupTimeList().length; i++) {
            if (i != 0) {
                timeStr.append("/");
            }
            timeStr.append(mainConfig.getBackupTimeList()[i]);
        }

        //リセット情報送信
        sender.sendMessage("§a" + messageConfig.getPrefix() + " §f" + convertUtil.placeholderUtil("{dayofweek}", weekStr.toString(), "{time}", timeStr.toString(), messageConfig.getBackupTimeInfo()));

    }
}
