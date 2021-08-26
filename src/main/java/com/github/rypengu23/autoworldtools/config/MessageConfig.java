package com.github.rypengu23.autoworldtools.config;

import com.github.rypengu23.autoworldtools.util.ConvertUtil;
import org.bukkit.configuration.Configuration;

public class MessageConfig {

    //バージョン
    private Double version;

    //////////////////////////////
    //単語設定
    //////////////////////////////
    //接頭辞
    private String prefix;

    //曜日
    private String sunday;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;

    //時
    private String hour;
    private String hours;

    //分
    private String minute;
    private String minutes;

    //秒
    private String second;
    private String seconds;

    //////////////////////////////
    //リセット関連
    //////////////////////////////

    //リセット時間情報
    private String resetTimeInfo;
    //リセット前カウントダウン
    private String resetCountdown;
    //リセット開始
    private String resetStart;
    //リセット開始(Discord)
    private String resetStartOfDiscord;
    //リセット完了
    private String resetComplete;
    //リセット完了(Discord)
    private String resetCompleteOfDiscord;

    //////////////////////////////
    //バックアップ関連
    //////////////////////////////

    //バックアップ時間情報
    private String backupTimeInfo;
    //バックアップ前カウントダウン
    private String backupCountdown;
    //バックアップ開始
    private String backupStart;
    //バックアップ開始(Discord)
    private String backupStartOfDiscord;
    //バックアップ完了
    private String backupComplete;
    //バックアップ完了(Discord)
    private String backupCompleteOfDiscord;

    //////////////////////////////
    //再起動関連
    //////////////////////////////

    //再起動時間情報
    private String restartTimeInfo;
    //再起動前カウントダウン
    private String restartCountdown;
    //再起動開始
    private String restartStart;
    //再起動開始(Discord)
    private String restartStartOfDiscord;

    public MessageConfig(Configuration config) {

        ConvertUtil convertUtil = new ConvertUtil();

        version = config.getDouble("version");

        prefix = convertUtil.convertColorCode(config.getString("prefix"));
        sunday = config.getString("sunday");
        monday = config.getString("monday");
        tuesday = config.getString("tuesday");
        wednesday = config.getString("wednesday");
        thursday = config.getString("thursday");
        friday = config.getString("friday");
        saturday = config.getString("saturday");
        hour = config.getString("hour");
        hours = config.getString("hours");
        minute = config.getString("minute");
        minutes = config.getString("minutes");
        second = config.getString("second");
        seconds = config.getString("seconds");

        resetTimeInfo = convertUtil.convertColorCode(config.getString("resetTimeInfo"));
        resetCountdown = convertUtil.convertColorCode(config.getString("resetCountdown"));
        resetStart = convertUtil.convertColorCode(config.getString("resetStart"));
        resetStartOfDiscord = config.getString("resetStartOfDiscord");
        resetComplete = convertUtil.convertColorCode(config.getString("resetComplete"));
        resetCompleteOfDiscord = config.getString("resetCompleteOfDiscord");

        backupTimeInfo = convertUtil.convertColorCode(config.getString("backupTimeInfo"));
        backupCountdown = convertUtil.convertColorCode(config.getString("backupCountdown"));
        backupStart = convertUtil.convertColorCode(config.getString("backupStart"));
        backupStartOfDiscord = config.getString("backupStartOfDiscord");
        backupComplete = convertUtil.convertColorCode(config.getString("backupComplete"));
        backupCompleteOfDiscord = config.getString("backupCompleteOfDiscord");

        restartTimeInfo = convertUtil.convertColorCode(config.getString("restartTimeInfo"));
        restartCountdown = convertUtil.convertColorCode(config.getString("restartCountdown"));
        restartStart = convertUtil.convertColorCode(config.getString("restartStart"));
        restartStartOfDiscord = config.getString("restartStartOfDiscord");
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String[] getDayOfWeekList(){
        return new String[]{getSunday(),getMonday(),getTuesday(),getWednesday(),getThursday(),getFriday(),getSaturday()};
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }


    public String getResetTimeInfo() {
        return resetTimeInfo;
    }

    public void setResetTimeInfo(String resetTimeInfo) {
        this.resetTimeInfo = resetTimeInfo;
    }

    public String getResetCountdown() {
        return resetCountdown;
    }

    public void setResetCountdown(String resetCountdown) {
        this.resetCountdown = resetCountdown;
    }

    public String getResetStart() {
        return resetStart;
    }

    public void setResetStart(String resetStart) {
        this.resetStart = resetStart;
    }

    public String getResetStartOfDiscord() {
        return resetStartOfDiscord;
    }

    public String getResetComplete() {
        return resetComplete;
    }

    public void setResetComplete(String resetComplete) {
        this.resetComplete = resetComplete;
    }

    public String getResetCompleteOfDiscord() {
        return resetCompleteOfDiscord;
    }

    public String getBackupTimeInfo() {
        return backupTimeInfo;
    }

    public void setBackupTimeInfo(String backupTimeInfo) {
        this.backupTimeInfo = backupTimeInfo;
    }

    public String getBackupCountdown() {
        return backupCountdown;
    }

    public void setBackupCountdown(String backupCountdown) {
        this.backupCountdown = backupCountdown;
    }

    public String getBackupStart() {
        return backupStart;
    }

    public String getBackupStartOfDiscord() {
        return backupStartOfDiscord;
    }

    public String getBackupComplete() {
        return backupComplete;
    }

    public String getBackupCompleteOfDiscord() {
        return backupCompleteOfDiscord;
    }

    public String getRestartTimeInfo() {
        return restartTimeInfo;
    }


    public String getRestartCountdown() {
        return restartCountdown;
    }


    public String getRestartStart() {
        return restartStart;
    }

    public String getRestartStartOfDiscord() {
        return restartStartOfDiscord;
    }
}
