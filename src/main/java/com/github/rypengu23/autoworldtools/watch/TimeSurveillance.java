package com.github.rypengu23.autoworldtools.watch;

import com.github.rypengu23.autoworldtools.AutoWorldTools;
import com.github.rypengu23.autoworldtools.util.BackupUtil;
import com.github.rypengu23.autoworldtools.util.ResetUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;


public class TimeSurveillance {

    private Plugin plugin;

    public TimeSurveillance() {
        this.plugin = AutoWorldTools.getInstance();
    }

    public void resetTimeSurveillance() {

        Runnable timeMonitor = new Runnable() {
            @Override
            public void run() {
                ResetUtil resetUtil = new ResetUtil();

                //リセット時刻をチェック
                resetUtil.resetTimeCheck();
            }
        };

        AutoWorldTools.resetTimeSurveillance = Bukkit.getServer().getScheduler().runTaskTimer(plugin, timeMonitor, 0, 20L);
    }

    public void backupTimeSurveillance() {

        Runnable timeMonitor = new Runnable() {
            @Override
            public void run() {
                BackupUtil backupUtil = new BackupUtil();

                //バックアップ時刻をチェック
                backupUtil.backupTimeCheck();
            }
        };

        AutoWorldTools.backupTimeSurveillance = Bukkit.getServer().getScheduler().runTaskTimer(plugin, timeMonitor, 0, 20L);
    }
}