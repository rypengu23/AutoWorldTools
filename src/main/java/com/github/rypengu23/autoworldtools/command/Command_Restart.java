package com.github.rypengu23.autoworldtools.command;

import com.github.rypengu23.autoworldtools.config.CommandMessage;
import com.github.rypengu23.autoworldtools.config.ConfigLoader;
import com.github.rypengu23.autoworldtools.config.MainConfig;
import com.github.rypengu23.autoworldtools.config.MessageConfig;
import com.github.rypengu23.autoworldtools.util.*;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class Command_Restart {

    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public Command_Restart() {
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    public void restartServer(CommandSender sender) {

        //権限所持チェック
        if (!sender.hasPermission("autoWorldTools.restart")) {
            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_DoNotHavePermission);
            return;
        }

        RestartUtil restartUtil = new RestartUtil();
        restartUtil.autoRestart();
    }

    public void showRestartInfo(CommandSender sender) {

        //権限所持チェック
        if (!sender.hasPermission("autoWorldTools.restartInfo")) {
            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.AutoWorldTools_DoNotHavePermission);
            return;
        }

        ConvertUtil convertUtil = new ConvertUtil();

        //再起動曜日を取得
        ArrayList<Integer> restartDayOfWeekNumberList = new ArrayList<>();
        StringBuilder weekStr = new StringBuilder();
        String[] weekNameJP = {"日", "月", "火", "水", "木", "金", "土"};
        String[] weekNameEN = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};
        for (int i = 0; i < 7; i++) {
            for (String resetDay : mainConfig.getRestartDayOfTheWeekList()) {
                if (resetDay.equals(weekNameJP[i]) || resetDay.equals(weekNameEN[i])) {
                    restartDayOfWeekNumberList.add(i);
                }
            }
        }

        //メッセージ用に曜日をセット
        for (int i = 0; i < restartDayOfWeekNumberList.size(); i++) {
            if (i != 0) {
                weekStr.append("/");
            }
            weekStr.append(messageConfig.getDayOfWeekList()[restartDayOfWeekNumberList.get(i)]);
        }

        //メッセージ用に時間をセット
        StringBuilder timeStr = new StringBuilder();
        for (int i = 0; i < mainConfig.getRestartTimeList().length; i++) {
            if (i != 0) {
                timeStr.append("/");
            }
            timeStr.append(mainConfig.getRestartTimeList()[i]);
        }

        //再起動情報送信
        sender.sendMessage("§a" + messageConfig.getPrefix() + " §f" + convertUtil.placeholderUtil("{dayofweek}", weekStr.toString(), "{time}", timeStr.toString(), messageConfig.getRestartTimeInfo()));

    }
}
