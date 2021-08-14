package com.github.rypengu23.autoworldtools.util;

import com.github.rypengu23.autoworldtools.AutoWorldTools;
import com.github.rypengu23.autoworldtools.config.ConfigLoader;
import com.github.rypengu23.autoworldtools.config.ConsoleMessage;
import com.github.rypengu23.autoworldtools.config.MainConfig;
import com.github.rypengu23.autoworldtools.config.MessageConfig;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ResetUtil {

    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public ResetUtil() {
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    /**
     * 指定されたタイプの素材世界を再生成
     * 0:ノーマル 1:ネザー 2:ジエンド
     *
     * @param worldType
     */
    public void regenerateWorld(int worldType) {

        try {
            MVWorldManager worldManager = AutoWorldTools.core.getMVWorldManager();

            //ワールド名リストの取得
            ArrayList<String> worldNameList = new ArrayList<>();
            if (worldType == 0) {
                worldNameList = new ArrayList<String>(Arrays.asList(mainConfig.getResetWorldNameOfNormal()));
            } else if (worldType == 1) {
                worldNameList = new ArrayList<String>(Arrays.asList(mainConfig.getResetWorldNameOfNether()));
            } else {
                worldNameList = new ArrayList<String>(Arrays.asList(mainConfig.getResetWorldNameOfEnd()));
            }

            //ワールド再生成
            for (String worldName : worldNameList) {
                Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.ResetUtil_resetStart + worldName);
                if (worldManager.regenWorld(worldName, true, true, "")) {

                    //ワールドボーダーをセット
                    int worldSize = 0;
                    if (worldType == 0) {
                        worldSize = mainConfig.getWorldOfNormalSize();
                    } else if (worldType == 1) {
                        worldSize = mainConfig.getWorldOfNetherSize();
                    } else {
                        worldSize = mainConfig.getWorldOfEndSize();
                    }
                    WorldBorder worldBorder = Bukkit.getWorld(worldName).getWorldBorder();
                    worldBorder.setCenter(0.0, 0.0);
                    worldBorder.setSize(worldSize);

                    Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.ResetUtil_resetComp + worldName);

                } else {
                    Bukkit.getLogger().warning("[AutoWorldTools] " + ConsoleMessage.ResetUtil_resetFailure + worldName);
                }
            }

        } catch (NoClassDefFoundError e) {
            Bukkit.getLogger().warning("[AutoWorldTools] " + ConsoleMessage.ResetUtil_resetFailureNotConnectedMultiverseCore);
        }
    }

    /**
     * リセット時刻をチェックし、リセット・通知を実行
     */
    public void resetTimeCheck() {

        ResetUtil resetUtil = new ResetUtil();
        CreateWarpGateUtil createWarpGateUtil = new CreateWarpGateUtil();
        CheckUtil checkUtil = new CheckUtil();
        ConvertUtil convertUtil = new ConvertUtil();

        String[] weekNameJP = {"日", "月", "火", "水", "木", "金", "土"};
        String[] weekNameEN = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

        //現在の時刻を取得
        Calendar nowCalendar = Calendar.getInstance();

        //リセット曜日を取得
        ArrayList<Integer> resetDayOfWeekNumberList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (String resetDay : mainConfig.getResetDayOfTheWeekList()) {
                if (resetDay.equals(weekNameJP[i]) || resetDay.equals(weekNameEN[i])) {
                    resetDayOfWeekNumberList.add(i + 1);
                }
            }
        }

        //リセット曜日・時間をリスト化
        ArrayList<Calendar> resetTimeList = new ArrayList<>();

        for (int dayOfWeek : resetDayOfWeekNumberList) {

            for (String time : mainConfig.getResetTimeList()) {
                Calendar work = (Calendar) nowCalendar.clone();
                work.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                work.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));
                work.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1]));
                work.set(Calendar.SECOND, 0);
                resetTimeList.add(work);
            }

        }

        //現在時刻とリセット前通知時刻が同一か
        for (Calendar resetTime : resetTimeList) {

            //現在時刻とリセット前通知時刻が同一か
            for (int notifyTime : mainConfig.getResetNotifyTimeList()) {
                Calendar work = (Calendar) resetTime.clone();
                work.add(Calendar.SECOND, -notifyTime);

                //曜日・時・分・秒が同一かどうか
                if (nowCalendar.get(Calendar.DAY_OF_WEEK) == work.get(Calendar.DAY_OF_WEEK) && nowCalendar.get(Calendar.HOUR_OF_DAY) == work.get(Calendar.HOUR_OF_DAY) && nowCalendar.get(Calendar.MINUTE) == work.get(Calendar.MINUTE) && nowCalendar.get(Calendar.SECOND) == work.get(Calendar.SECOND)) {
                    //同一な場合
                    //残り時間を計算
                    int hour = notifyTime / 3600;
                    int minute = (notifyTime % 3600) / 60;
                    int second = notifyTime - (hour * 3600) - (minute * 60);
                    StringBuilder countdownStr = new StringBuilder();
                    if (hour != 0) {
                        countdownStr.append(hour);
                        if (hour == 1) {
                            countdownStr.append(messageConfig.getHour());
                        } else {
                            countdownStr.append(messageConfig.getHours());
                        }
                    }
                    if (minute != 0) {
                        countdownStr.append(minute);
                        if (minute == 1) {
                            countdownStr.append(messageConfig.getMinute());
                        } else {
                            countdownStr.append(messageConfig.getMinutes());
                        }
                    }
                    if (second != 0) {
                        countdownStr.append(second);
                        if (second == 1) {
                            countdownStr.append(messageConfig.getSecond());
                        } else {
                            countdownStr.append(messageConfig.getSeconds());
                        }
                    }

                    //メッセージが空白で無ければ送信
                    //カウントダウンメッセージ
                    if (!checkUtil.checkNullOrBlank(messageConfig.getResetCountdown())) {
                        Bukkit.getServer().broadcastMessage("§a" + messageConfig.getPrefix() + " §f" + convertUtil.placeholderUtil("{countdown}", countdownStr.toString(), messageConfig.getResetCountdown()));
                    }

                }
            }

            //現在時刻とリセット時刻が同一か
            if (resetTime.get(Calendar.DAY_OF_WEEK) == nowCalendar.get(Calendar.DAY_OF_WEEK) && resetTime.get(Calendar.HOUR_OF_DAY) == nowCalendar.get(Calendar.HOUR_OF_DAY) && resetTime.get(Calendar.MINUTE) == nowCalendar.get(Calendar.MINUTE) && resetTime.get(Calendar.SECOND) == nowCalendar.get(Calendar.SECOND)) {

                //メッセージが空白で無ければ送信
                //リセット開始メッセージ
                if (!checkUtil.checkNullOrBlank(messageConfig.getResetStart())) {
                    Bukkit.getServer().broadcastMessage("§a" + messageConfig.getPrefix() + " §f" + messageConfig.getResetStart());
                }

                //全ての素材世界のリセット
                for (int i = 0; i <= 2; i++) {
                    resetUtil.regenerateWorld(i);
                }

                //全ての素材世界へゲートを再生成
                if (mainConfig.isUseMultiversePortals()) {
                    for (int i = 0; i <= 2; i++) {
                        if ((i == 0 && mainConfig.isGateAutoBuildOfNormal()) || (i == 1 && mainConfig.isGateAutoBuildOfNether()) || (i == 2 && mainConfig.isGateAutoBuildOfEnd())) {
                            createWarpGateUtil.createWarpGateUtil(i);
                        }
                    }
                }

                //メッセージが空白で無ければ送信
                //リセット完了メッセージ
                if (!checkUtil.checkNullOrBlank(messageConfig.getResetComplete())) {
                    Bukkit.getServer().broadcastMessage("§a" + messageConfig.getPrefix() + " §f" + messageConfig.getResetComplete());
                }

            }
        }
    }
}
