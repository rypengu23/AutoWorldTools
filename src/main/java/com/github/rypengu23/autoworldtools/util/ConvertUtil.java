package com.github.rypengu23.autoworldtools.util;

import com.github.rypengu23.autoworldtools.config.MessageConfig;

import java.util.ArrayList;
import java.util.Calendar;

public class ConvertUtil {

    public String placeholderUtil(String beforeReplaceWord, String afterReplaceWord, String message){

        if(beforeReplaceWord != null && afterReplaceWord != null && message != null) {
            return message.replace(beforeReplaceWord, afterReplaceWord);
        }else{
            return null;
        }
    }

    public String placeholderUtil(String beforeReplaceWord1, String afterReplaceWord1, String beforeReplaceWord2, String afterReplaceWord2, String message){

        message = placeholderUtil(beforeReplaceWord1, afterReplaceWord1, message);
        return placeholderUtil(beforeReplaceWord2, afterReplaceWord2, message);
    }

    public String placeholderUtil(String beforeReplaceWord1, String afterReplaceWord1, String beforeReplaceWord2, String afterReplaceWord2, String beforeReplaceWord3, String afterReplaceWord3, String message){

        message = placeholderUtil(beforeReplaceWord1, afterReplaceWord1, message);
        message = placeholderUtil(beforeReplaceWord2, afterReplaceWord2, message);
        return placeholderUtil(beforeReplaceWord3, afterReplaceWord3, message);
    }

    /**
     * カラーコードの文字を置き換える
     * @param word
     * @return
     */
    public String convertColorCode(String word){

        String[] codeListBefore = {"&0","&1","&2","&3","&4","&5","&6","&7","&8","&9","&a","&b","&c","&d","&e","&f","&k","&l","&m","&n","&o","&r"};
        String[] codeListAfter = {"§0","§1","§2","§3","§4","§5","§6","§7","§8","§9","§a","§b","§c","§d","§e","§f","§k","§l","§m","§n","§o","§r"};

        for(int i=0; i<codeListBefore.length; i++){
            word = placeholderUtil(codeListBefore[i], codeListAfter[i], word);
        }
        return word;
    }

    /**
     * 引数の秒数を XX時間XX分XX秒 に変換。
     * @param seconds
     * @param messageConfig
     * @return
     */
    public String createCountdown(int seconds, MessageConfig messageConfig){

        if(seconds <= 0){
            return  "";
        }

        int hour = seconds / 3600;
        int minute = (seconds % 3600) / 60;
        int second = seconds - (hour * 3600) - (minute * 60);

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

        return countdownStr.toString();
    }

    /**
     * 曜日リスト・時刻リストをCalendar型のリストに変換
     * @param dayOfTheWeekList
     * @param timeList
     */
    public ArrayList<Calendar> convertCalendar(String[] dayOfTheWeekList, String[] timeList){

        if(dayOfTheWeekList == null || timeList == null){
            return null;
        }

        Calendar calendar = Calendar.getInstance();

        String[] weekNameJP = {"日", "月", "火", "水", "木", "金", "土"};
        String[] weekNameEN = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

        //リセット曜日を取得
        ArrayList<Integer> dayOfWeekNumberList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (String dayOfTheWeek : dayOfTheWeekList) {
                if (dayOfTheWeek.equals(weekNameJP[i]) || dayOfTheWeek.equals(weekNameEN[i])) {
                    dayOfWeekNumberList.add(i + 1);
                }
            }
        }

        //リセット曜日・時間をリスト化
        ArrayList<Calendar> doTimeList = new ArrayList<>();

        for (int dayOfWeek : dayOfWeekNumberList) {

            for (String time : timeList) {
                Calendar work = (Calendar) calendar.clone();
                work.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                work.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));
                work.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1]));
                work.set(Calendar.SECOND, 0);
                doTimeList.add(work);
            }

        }

        return doTimeList;
    }
}
