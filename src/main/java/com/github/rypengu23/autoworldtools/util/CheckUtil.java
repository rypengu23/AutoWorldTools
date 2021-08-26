package com.github.rypengu23.autoworldtools.util;

import com.github.rypengu23.autoworldtools.config.CommandMessage;
import com.github.rypengu23.autoworldtools.config.MessageConfig;
import org.bukkit.command.CommandSender;

import java.util.Calendar;

public class CheckUtil {

    public boolean checkPermissionReset(CommandSender sender, MessageConfig messageConfig){

        if(sender.hasPermission("autoWorldTools.reset")){
            return true;
        }

        sender.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.AutoWorldTools_CommandFailure);
        return false;
    }

    public boolean checkPermissionInfo(CommandSender sender){

        if(sender.hasPermission("autoResetWorld.info")){
            return true;
        }

        sender.sendMessage("§c[ARW]§f権限を所持していません。");
        return false;
    }

    /**
     * 引数がnullまたは空白か判定
     * @param str
     * @return
     */
    public boolean checkNullOrBlank(String str){
        if(str == null || str.equals("")){
            return true;
        }
        return false;
    }

    /**
     * 2つの引数の時刻(曜日・時間)が同一は判定
     * @param nowTime
     * @param comparisonTime
     * @return
     */
    public boolean checkComparisonTime(Calendar nowTime, Calendar comparisonTime){

        //現在時刻とリセット時刻が同一か
        if (comparisonTime.get(Calendar.DAY_OF_WEEK) == nowTime.get(Calendar.DAY_OF_WEEK) && comparisonTime.get(Calendar.HOUR_OF_DAY) == nowTime.get(Calendar.HOUR_OF_DAY) && comparisonTime.get(Calendar.MINUTE) == nowTime.get(Calendar.MINUTE) && comparisonTime.get(Calendar.SECOND) == nowTime.get(Calendar.SECOND)) {
            return true;
        }
        return false;
    }

    /**
     * 引数の時刻のbeforeSecond秒前と、現在時刻が同一か判定
     * @param nowTime
     * @param comparisonTime
     * @param beforeSecond
     * @return
     */
    public boolean checkComparisonTime(Calendar nowTime, Calendar comparisonTime, int beforeSecond){

        Calendar work = (Calendar) comparisonTime.clone();
        work.add(Calendar.SECOND, -beforeSecond);

        //曜日・時・分・秒が同一かどうか
        if (nowTime.get(Calendar.DAY_OF_WEEK) == work.get(Calendar.DAY_OF_WEEK) && nowTime.get(Calendar.HOUR_OF_DAY) == work.get(Calendar.HOUR_OF_DAY) && nowTime.get(Calendar.MINUTE) == work.get(Calendar.MINUTE) && nowTime.get(Calendar.SECOND) == work.get(Calendar.SECOND)) {
            //同一な場合
            return true;
        }
        return false;
    }

    /**
     * 比較時刻のbeforeSecondList秒前と、引数の現在時刻が同一か判定。
     * 同一の場合、リスト内の同一であった秒数を返す。同一な値が無い場合は-1を返す。
     * @param nowTime
     * @param comparisonTime
     * @param beforeSecondList
     * @return
     */
    public int checkComparisonTimeOfList(Calendar nowTime, Calendar comparisonTime, int[] beforeSecondList){

        if(beforeSecondList != null) {
            for (int beforeSecond : beforeSecondList) {
                Calendar work = (Calendar) comparisonTime.clone();
                work.add(Calendar.SECOND, -beforeSecond);

                //曜日・時・分・秒が同一かどうか
                if (nowTime.get(Calendar.DAY_OF_WEEK) == work.get(Calendar.DAY_OF_WEEK) && nowTime.get(Calendar.HOUR_OF_DAY) == work.get(Calendar.HOUR_OF_DAY) && nowTime.get(Calendar.MINUTE) == work.get(Calendar.MINUTE) && nowTime.get(Calendar.SECOND) == work.get(Calendar.SECOND)) {
                    return beforeSecond;
                }
            }
        }
        return -1;
    }
}
