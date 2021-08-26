package com.github.rypengu23.autoworldtools.watch;

import com.github.rypengu23.autoworldtools.AutoWorldTools;
import com.github.rypengu23.autoworldtools.util.BackupUtil;
import com.github.rypengu23.autoworldtools.util.ResetUtil;
import com.github.rypengu23.autoworldtools.util.RestartUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Calendar;


public class TimeSurveillance {

    private Plugin plugin;
    private boolean waitRestart;
    private boolean waitReset;

    public TimeSurveillance() {
        this.plugin = AutoWorldTools.getInstance();
    }

    public void timeSurveillance(){

        Runnable timeMonitor = new Runnable() {
            @Override
            public void run() {
                //現在の時刻を取得
                Calendar nowCalendar = Calendar.getInstance();

                ResetUtil resetUtil = new ResetUtil();
                BackupUtil backupUtil = new BackupUtil();
                RestartUtil restartUtil = new RestartUtil();

                //バックアップ前カウントダウン
                backupUtil.sendNotify(backupUtil.checkAnnounceBeforeBackupTime(nowCalendar));
                //バックアップ可否
                if(backupUtil.checkBackupTime(nowCalendar)){
                    //バックアップが実行中の場合、キャンセル
                    if(AutoWorldTools.backupTask != null){
                        AutoWorldTools.backupTask.cancel();
                    }
                    backupUtil.autoBackup();
                }

                //リセット前カウントダウン
                resetUtil.sendNotify(resetUtil.checkAnnounceBeforeResetTime(nowCalendar));
                //リセット可否
                if(resetUtil.checkResetTime(nowCalendar) || waitReset) {

                    if(AutoWorldTools.backupTask != null){
                        //バックアップが実行中の場合、待機
                        waitReset = true;
                    }else {
                        resetUtil.autoReset();
                        waitReset = false;
                    }
                }

                //再起動前カウントダウン
                restartUtil.sendNotify(restartUtil.checkAnnounceBeforeRestartTime(nowCalendar));
                //再起動可否
                if(restartUtil.checkRestartTime(nowCalendar) || waitRestart){

                    if(AutoWorldTools.backupTask != null){
                        //バックアップが実行中の場合、待機
                        waitRestart = true;
                    }else{
                        restartUtil.autoRestart();
                        waitRestart = false;
                    }
                }

            }
        };

        AutoWorldTools.timeSurveillance = Bukkit.getServer().getScheduler().runTaskTimer(plugin, timeMonitor, 0, 20L);

    }
}