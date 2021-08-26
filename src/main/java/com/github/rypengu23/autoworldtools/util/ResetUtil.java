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
     * 現在時刻がリセット実行時刻か判定
     * @param nowCalendar
     * @return
     */
    public boolean checkResetTime(Calendar nowCalendar){

        CheckUtil checkUtil = new CheckUtil();
        ConvertUtil convertUtil = new ConvertUtil();

        //リセット時刻リストを取得
        ArrayList<Calendar> resetTimeList = convertUtil.convertCalendar(mainConfig.getResetDayOfTheWeekList(), mainConfig.getResetTimeList());

        //比較
        if(resetTimeList != null) {
            for (Calendar resetTime : resetTimeList) {
                if (checkUtil.checkComparisonTime(nowCalendar, resetTime)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 現在時刻がリセット前アナウンス時刻か判定。
     * 戻り地が-1の場合、アナウンス時刻ではない。
     * @param nowCalendar
     * @return
     */
    public int checkAnnounceBeforeResetTime(Calendar nowCalendar){

        CheckUtil checkUtil = new CheckUtil();
        ConvertUtil convertUtil = new ConvertUtil();

        //リセット時刻リストを取得
        ArrayList<Calendar> resetTimeList = convertUtil.convertCalendar(mainConfig.getResetDayOfTheWeekList(), mainConfig.getResetTimeList());

        //比較
        if(resetTimeList != null) {
            for (Calendar resetTime : resetTimeList) {
                int result = checkUtil.checkComparisonTimeOfList(nowCalendar, resetTime, mainConfig.getResetNotifyTimeList());
                if (result != -1) {
                    return result;
                }
            }
        }
        return -1;
    }

    /**
     * Configに登録された全ワールドをリセット・ゲート生成する。
     * メッセージ等も送信
     */
    public void autoReset(){

        CheckUtil checkUtil = new CheckUtil();
        CreateWarpGateUtil createWarpGateUtil = new CreateWarpGateUtil();

        //メッセージが空白で無ければ送信
        //リセット開始メッセージ
        if (!checkUtil.checkNullOrBlank(messageConfig.getResetStart())) {
            Bukkit.getServer().broadcastMessage("§a" + messageConfig.getPrefix() + " §f" + messageConfig.getResetStart());
        }

        //メッセージが空白で無ければ送信
        //リセット開始メッセージ(Discord)
        if (mainConfig.isUseDiscordSRV() && !checkUtil.checkNullOrBlank(messageConfig.getResetStartOfDiscord())) {
            DiscordUtil discordUtil = new DiscordUtil();
            discordUtil.sendMessageMainChannel(messageConfig.getResetStartOfDiscord());
        }

        //全てのワールドのリセット
        for (int i = 0; i <= 2; i++) {
            regenerateWorld(i);
        }

        //全てのワールドへゲートを再生成
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

        //メッセージが空白で無ければ送信
        //リセット完了メッセージ(Discord)
        if (mainConfig.isUseDiscordSRV() && !checkUtil.checkNullOrBlank(messageConfig.getResetCompleteOfDiscord())) {
            DiscordUtil discordUtil = new DiscordUtil();
            discordUtil.sendMessageMainChannel(messageConfig.getResetCompleteOfDiscord());
        }
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
        if (!checkUtil.checkNullOrBlank(messageConfig.getResetCountdown())) {
            Bukkit.getServer().broadcastMessage("§a" + messageConfig.getPrefix() + " §f" + convertUtil.placeholderUtil("{countdown}", countdownStr, messageConfig.getResetCountdown()));
        }
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
}
