package com.github.rypengu23.autoworldtools.util;

import com.github.rypengu23.autoworldtools.config.ConfigLoader;
import com.github.rypengu23.autoworldtools.config.ConsoleMessage;
import com.github.rypengu23.autoworldtools.config.MainConfig;
import com.github.rypengu23.autoworldtools.config.MessageConfig;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Calendar;

public class RestartUtil {

    private ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    public RestartUtil() {
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    /**
     * 現在時刻が再起動実行時刻か判定
     * @param nowCalendar
     * @return
     */
    public boolean checkRestartTime(Calendar nowCalendar){

        CheckUtil checkUtil = new CheckUtil();
        ConvertUtil convertUtil = new ConvertUtil();

        //再起動時刻リストを取得
        ArrayList<Calendar> restartTimeList = convertUtil.convertCalendar(mainConfig.getRestartDayOfTheWeekList(), mainConfig.getRestartTimeList());

        //比較
        if(restartTimeList != null) {
            for (Calendar restartTime : restartTimeList) {
                if (checkUtil.checkComparisonTime(nowCalendar, restartTime)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 現在時刻が再起動前アナウンス時刻か判定。
     * 戻り地が-1の場合、アナウンス時刻ではない。
     * @param nowCalendar
     * @return
     */
    public int checkAnnounceBeforeRestartTime(Calendar nowCalendar){

        CheckUtil checkUtil = new CheckUtil();
        ConvertUtil convertUtil = new ConvertUtil();

        //リセット時刻リストを取得
        ArrayList<Calendar> restartTimeList = convertUtil.convertCalendar(mainConfig.getRestartDayOfTheWeekList(), mainConfig.getRestartTimeList());

        //比較
        if(restartTimeList != null) {
            for (Calendar restartTime : restartTimeList) {
                int result = checkUtil.checkComparisonTimeOfList(nowCalendar, restartTime, mainConfig.getRestartNotifyTimeList());
                if (result != -1) {
                    return result;
                }
            }
        }
        return -1;
    }

    /**
     * サーバーを再起動する。
     * メッセージ等も送信
     */
    public void autoRestart(){

        CheckUtil checkUtil = new CheckUtil();

        //メッセージが空白で無ければ送信
        //再起動開始メッセージ
        if (!checkUtil.checkNullOrBlank(messageConfig.getRestartStart())) {
            Bukkit.getServer().broadcastMessage("§b" + messageConfig.getPrefix() + " §f" + messageConfig.getRestartStart());
        }

        //メッセージが空白で無ければ送信
        //再起動開始メッセージ(Discord)
        if (mainConfig.isUseDiscordSRV() && !checkUtil.checkNullOrBlank(messageConfig.getRestartStartOfDiscord())) {
            DiscordUtil discordUtil = new DiscordUtil();
            discordUtil.sendMessageMainChannel(messageConfig.getRestartStartOfDiscord());
        }

        Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.RestartUtil_RestartStart);
        Bukkit.getServer().spigot().restart();
    }

    /**
     * 引数のカウントダウン秒数をもとに、メッセージを送信。
     * @param second
     */
    public void sendNotify(int second){

        CheckUtil checkUtil = new CheckUtil();
        ConvertUtil convertUtil = new ConvertUtil();

        if(second <= 0){
            return;
        }

        String countdownStr = convertUtil.createCountdown(second, messageConfig);

        //メッセージが空白で無ければ送信
        //カウントダウンメッセージ
        if (!checkUtil.checkNullOrBlank(messageConfig.getRestartCountdown())) {
            Bukkit.getServer().broadcastMessage("§a" + messageConfig.getPrefix() + " §f" + convertUtil.placeholderUtil("{countdown}", countdownStr, messageConfig.getRestartCountdown()));
        }
    }


}
