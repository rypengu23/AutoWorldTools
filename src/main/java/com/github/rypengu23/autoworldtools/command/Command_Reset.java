package com.github.rypengu23.autoworldtools.command;

import com.github.rypengu23.autoworldtools.config.CommandMessage;
import com.github.rypengu23.autoworldtools.config.ConfigLoader;
import com.github.rypengu23.autoworldtools.config.MainConfig;
import com.github.rypengu23.autoworldtools.config.MessageConfig;
import com.github.rypengu23.autoworldtools.util.ConvertUtil;
import com.github.rypengu23.autoworldtools.util.CreateWarpGateUtil;
import com.github.rypengu23.autoworldtools.util.DiscordUtil;
import com.github.rypengu23.autoworldtools.util.ResetUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class Command_Reset {

    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public Command_Reset() {
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    public void resetWorld(CommandSender sender, int worldType) {

        //権限所持チェック
        if (!sender.hasPermission("autoWorldTools.reset")) {
            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_DoNotHavePermission);
            return;
        }

        //メッセージ用にワールドタイプをセット
        String type = "";
        if (worldType == 0) {
            type = "NORMAL";
        } else if (worldType == 1) {
            type = "NETHER";
        } else if (worldType == 2) {
            type = "THE END";
        }

        //リセット開始メッセージ
        if (worldType != 3) {
            sender.sendMessage("§b" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_CommandResetStart + type);
        } else {
            sender.sendMessage("§b" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_CommandAllResetStart);
        }

        //ワールド再生成
        ResetUtil resetUtil = new ResetUtil();
        if (worldType != 3) {
            resetUtil.regenerateWorld(worldType);
        } else {
            for (int i = 0; i < 3; i++) {
                resetUtil.regenerateWorld(i);
            }
        }

        //ゲート生成
        if (mainConfig.isUseMultiversePortals()) {
            CreateWarpGateUtil createWarpGateUtil = new CreateWarpGateUtil();
            if (worldType != 3) {
                if ((worldType == 0 && mainConfig.isGateAutoBuildOfNormal()) || (worldType == 1 && mainConfig.isGateAutoBuildOfNether()) || (worldType == 2 && mainConfig.isGateAutoBuildOfEnd())) {
                    createWarpGateUtil.createWarpGateUtil(worldType);
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    if ((i == 0 && mainConfig.isGateAutoBuildOfNormal()) || (i == 1 && mainConfig.isGateAutoBuildOfNether()) || (i == 2 && mainConfig.isGateAutoBuildOfEnd())) {
                        createWarpGateUtil.createWarpGateUtil(i);
                    }
                }
            }
        }

        //リセット完了メッセージ
        if (worldType != 3) {
            sender.sendMessage("§a" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_CommandResetComp + type);
        } else {
            sender.sendMessage("§a" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_CommandAllResetComp);
        }
        //リセット完了メッセージ(Discord)
        DiscordUtil discordUtil = new DiscordUtil();
        discordUtil.sendMessageMainChannel("");
    }

    public void showResetInfo(CommandSender sender) {

        //権限所持チェック
        if (!sender.hasPermission("autoWorldTools.resetInfo")) {
            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_DoNotHavePermission);
            return;
        }

        ConvertUtil convertUtil = new ConvertUtil();

        //リセット曜日を取得
        ArrayList<Integer> resetDayOfWeekNumberList = new ArrayList<>();
        StringBuilder weekStr = new StringBuilder();
        String[] weekNameJP = {"日", "月", "火", "水", "木", "金", "土"};
        String[] weekNameEN = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};
        for (int i = 0; i < 7; i++) {
            for (String resetDay : mainConfig.getResetDayOfTheWeekList()) {
                if (resetDay.equals(weekNameJP[i]) || resetDay.equals(weekNameEN[i])) {
                    resetDayOfWeekNumberList.add(i);
                }
            }
        }

        //メッセージ用に曜日をセット
        for (int i = 0; i < resetDayOfWeekNumberList.size(); i++) {
            if (i != 0) {
                weekStr.append("/");
            }
            weekStr.append(messageConfig.getDayOfWeekList()[resetDayOfWeekNumberList.get(i)]);
        }

        //メッセージ用に時間をセット
        StringBuilder timeStr = new StringBuilder();
        for (int i = 0; i < mainConfig.getResetTimeList().length; i++) {
            if (i != 0) {
                timeStr.append("/");
            }
            timeStr.append(mainConfig.getResetTimeList()[i]);
        }

        //リセット情報送信
        sender.sendMessage("§a" + messageConfig.getPrefix() + " §f" + convertUtil.placeholderUtil("{dayofweek}", weekStr.toString(), "{time}", timeStr.toString(), messageConfig.getResetTimeInfo()));

    }
}
