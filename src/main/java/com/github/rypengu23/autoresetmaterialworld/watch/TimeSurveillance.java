package com.github.rypengu23.autoresetmaterialworld.watch;

import com.github.rypengu23.autoresetmaterialworld.Config;
import com.github.rypengu23.autoresetmaterialworld.util.CreateWarpGateUtil;
import com.github.rypengu23.autoresetmaterialworld.util.ResetUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Calendar;

public class TimeSurveillance {

    private final Plugin plugin;
    private static Config config;

    public TimeSurveillance(Plugin plugin) {
        this.plugin = plugin;
    }

    public void timeSurveillance() {

        config = new Config(plugin);
        ResetUtil resetUtil = new ResetUtil(plugin);
        CreateWarpGateUtil createWarpGateUtil = new CreateWarpGateUtil(plugin);

        Runnable timeMonitor = new Runnable() {
            @Override
            public void run() {
                config = new Config(plugin);

                String[] weekNameJP = {"日", "月", "火", "水", "木", "金", "土"};

                //現在の時刻を取得
                Calendar nowCalendar = Calendar.getInstance();
                String nowDayOfWeek = weekNameJP[nowCalendar.get(Calendar.DAY_OF_WEEK) - 1];

                //リセット曜日（数値）を取得
                ArrayList<Integer> resetDayOfWeekNumberList = new ArrayList<>();
                for (int i = 0; i < config.getResetTimeList().length; i++) {
                    if (nowDayOfWeek.equals(config.getResetDayOfTheWeekList()[i])) {
                        resetDayOfWeekNumberList.add(i+1);
                    }
                }

                //リセット曜日・時間をリスト化
                ArrayList<Calendar> resetTimeList = new ArrayList<>();

                for(int dayOfWeek:resetDayOfWeekNumberList) {
                    Calendar work = (Calendar) nowCalendar.clone();
                    for (String time : config.getResetTimeList()) {
                        work.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                        work.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));
                        work.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1]));
                        work.set(Calendar.SECOND, 0);
                    }
                    resetTimeList.add(work);
                }

                //現在時刻とリセット前通知時刻が同一か
                for(Calendar resetTime:resetTimeList){

                    //現在時刻とリセット前通知時刻が同一か
                    for(int notifyTime:config.getResetNotifyTimeList()) {
                        Calendar work = (Calendar) resetTime.clone();
                        work.add(Calendar.SECOND, -notifyTime);

                        //曜日・時・分・秒が同一かどうか
                        if(nowCalendar.get(Calendar.DAY_OF_WEEK) == work.get(Calendar.DAY_OF_WEEK) && nowCalendar.get(Calendar.HOUR_OF_DAY) == work.get(Calendar.HOUR_OF_DAY) && nowCalendar.get(Calendar.MINUTE) == work.get(Calendar.MINUTE) && nowCalendar.get(Calendar.SECOND) == work.get(Calendar.SECOND)){
                            //同一な場合
                            //残り時間を計算
                            int hour = notifyTime / 3600;
                            int minute = (notifyTime % 3600) / 60;
                            int second = notifyTime - (hour * 3600) - (minute * 60);
                            StringBuilder countdownStr = new StringBuilder();
                            if(hour!=0) {
                                countdownStr.append(hour + "時間");
                            }
                            if(minute!=0) {
                                countdownStr.append(minute + "分");
                            }
                            if(second!=0) {
                                countdownStr.append(second + "秒");
                            }
                            Bukkit.getServer().broadcastMessage("§a[ARW] §f"+ countdownStr +"後に素材世界のリセットを行います。");

                            //処理終了
                            return;
                        }
                    }

                    //現在時刻とリセット時刻が同一か
                    if(resetTime.get(Calendar.DAY_OF_WEEK) == nowCalendar.get(Calendar.DAY_OF_WEEK) && resetTime.get(Calendar.HOUR_OF_DAY) == nowCalendar.get(Calendar.HOUR_OF_DAY) && resetTime.get(Calendar.MINUTE) == nowCalendar.get(Calendar.MINUTE) && resetTime.get(Calendar.SECOND) == nowCalendar.get(Calendar.SECOND)){

                        //リセット実行
                        Bukkit.getServer().broadcastMessage("§a[ARW] §f素材世界のリセットを開始します。");

                        //全ての素材世界のリセット
                        String[] worldName = {"通常世界", "ネザー", "エンド"};
                        for (int i = 0; i <= 2; i++) {
                            Bukkit.getLogger().info("[ARW] 次のワールドを再生成します："+ worldName[i]);
                            resetUtil.regenerateWorld(i);
                        }

                        //全ての素材世界へゲートを再生成
                        if(config.isUseMultiversePortals()) {
                            for (int i = 0; i <= 2; i++) {
                                if ((i == 0 && config.isGateAutoBuildOfNormal()) || (i == 1 && config.isGateAutoBuildOfNether()) || (i == 2 && config.isGateAutoBuildOfEnd())) {
                                    Bukkit.getLogger().info("[ARW] 次のワールドにゲートを生成します：" + worldName[i]);
                                    if(createWarpGateUtil.createWarpGateUtil(i)){
                                        Bukkit.getLogger().info("[ARW] ゲートの生成が完了しました。");
                                    }
                                }
                            }
                        }

                        Bukkit.getServer().broadcastMessage("§a[ARW] §f素材世界のリセットが完了しました。");
                    }
                }
            }
        };

        Bukkit.getServer().getScheduler().runTaskTimer(plugin, timeMonitor, 0, 20L);
    }
}