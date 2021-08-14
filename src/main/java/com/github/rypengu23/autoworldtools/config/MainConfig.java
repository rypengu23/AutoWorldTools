package com.github.rypengu23.autoworldtools.config;

import org.bukkit.configuration.Configuration;

import java.util.stream.Stream;

public class MainConfig {

    //バージョン
    private String version;

    //言語
    private String language;
    private String consoleLanguage;

    //指定時刻自動リセット機能の利用可否
    private boolean autoReset;
    //自動バックアップ利用可否
    private boolean autoBackup;
    //バックアップ保存上限
    private int backupLimit;

    //リセット時間
    private String[] resetTimeList;
    //リセット曜日
    private String[] resetDayOfTheWeekList;
    //リセット通知時間
    private int[] resetNotifyTimeList;

    //バックアップ時間
    private String[] backupTimeList;
    //バックアップ曜日
    private String[] backupDayOfTheWeekList;
    //バックアップ通知時間
    private int[] backupNotifyTimeList;

    //リセットワールド名
    private String[] resetWorldNameOfNormal;
    private String[] resetWorldNameOfNether;
    private String[] resetWorldNameOfEnd;

    //ワールドサイズ
    private int worldOfNormalSize;
    private int worldOfNetherSize;
    private int worldOfEndSize;

    //バックアップワールド名
    private String[] backupWorldName;

    //MultiversePortals利用可否
    private boolean useMultiversePortals;

    //ゲート自動生成可否
    private boolean gateAutoBuildOfNormal;
    private boolean gateAutoBuildOfNether;
    private boolean gateAutoBuildOfEnd;

    //ワープゲート名
    private String[] portalNameOfNormal;
    private String[] portalNameOfNether;
    private String[] portalNameOfEnd;

    //バックアップ保存先
    private String backupLocation;

    public MainConfig(Configuration config) {
        //バージョン
        version = config.getString("version");

        //言語
        language = config.getString("setting.language");
        consoleLanguage = config.getString("setting.consoleLanguage");

        //自動リセット利用可否
        autoReset = config.getBoolean("setting.autoReset");
        //自動バックアップ利用可否
        autoBackup = config.getBoolean("setting.autoBackup");
        //バックアップ保存上限
        backupLimit = config.getInt("setting.backupLimit");


        //リセット時間
        resetTimeList = config.getString("resetTime.resetTime").split(",");
        resetDayOfTheWeekList = config.getString("resetTime.resetDayOfTheWeek").split(",");
        resetNotifyTimeList = Stream.of(config.getString("resetTime.resetNotifyTime").split(",")).mapToInt(Integer::parseInt).toArray();

        //バックアップ時間
        backupTimeList = config.getString("backupTime.backupTime").split(",");
        backupDayOfTheWeekList = config.getString("backupTime.backupDayOfTheWeek").split(",");
        backupNotifyTimeList = Stream.of(config.getString("backupTime.backupNotifyTime").split(",")).mapToInt(Integer::parseInt).toArray();

        //MultiversePortals利用可否
        useMultiversePortals = config.getBoolean("gateInfo.useMultiversePortals");

        //ワールド種別：ノーマル
        resetWorldNameOfNormal = config.getString("resetWorldInfo.worldNameOfNormal").split(",");
        worldOfNormalSize = config.getInt("border.worldOfNormalSize");
        portalNameOfNormal = config.getString("gateInfo.portalNameOfNormal").split(",");
        gateAutoBuildOfNormal = config.getBoolean("gateInfo.gateAutoBuildOfNormal");

        //ワールド種別：ネザー
        resetWorldNameOfNether = config.getString("resetWorldInfo.worldNameOfNether").split(",");
        worldOfNetherSize = config.getInt("border.worldOfNetherSize");
        portalNameOfNether = config.getString("gateInfo.portalNameOfNether").split(",");
        gateAutoBuildOfNether = config.getBoolean("gateInfo.gateAutoBuildOfNether");

        //ワールド種別：エンド
        resetWorldNameOfEnd = config.getString("resetWorldInfo.worldNameOfEnd").split(",");
        worldOfEndSize = config.getInt("border.worldOfEndSize");
        portalNameOfEnd = config.getString("gateInfo.portalNameOfEnd").split(",");
        gateAutoBuildOfEnd = config.getBoolean("gateInfo.gateAutoBuildOfEnd");

        //バックアップワールドリスト
        backupWorldName = config.getString("backupWorldInfo.backupWorldName").split(",");

        //バックアップ保存先
        backupLocation = config.getString("backupLocation.fileLocation");

    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getConsoleLanguage() {
        return consoleLanguage;
    }

    public void setConsoleLanguage(String consoleLanguage) {
        this.consoleLanguage = consoleLanguage;
    }

    public boolean isAutoReset() {
        return autoReset;
    }

    public void setAutoReset(boolean autoReset) {
        this.autoReset = autoReset;
    }

    public boolean isAutoBackup() {
        return autoBackup;
    }

    public void setAutoBackup(boolean autoBackup) {
        this.autoBackup = autoBackup;
    }

    public int getBackupLimit() {
        return backupLimit;
    }

    public void setBackupLimit(int backupLimit) {
        this.backupLimit = backupLimit;
    }

    public String[] getResetTimeList() {
        return resetTimeList;
    }

    public void setResetTimeList(String[] resetTimeList) {
        this.resetTimeList = resetTimeList;
    }

    public String[] getResetDayOfTheWeekList() {
        return resetDayOfTheWeekList;
    }

    public void setResetDayOfTheWeekList(String[] resetDayOfTheWeekList) {
        this.resetDayOfTheWeekList = resetDayOfTheWeekList;
    }

    public int[] getResetNotifyTimeList() {
        return resetNotifyTimeList;
    }

    public void setResetNotifyTimeList(int[] resetNotifyTimeList) {
        this.resetNotifyTimeList = resetNotifyTimeList;
    }

    public String[] getBackupTimeList() {
        return backupTimeList;
    }

    public void setBackupTimeList(String[] backupTimeList) {
        this.backupTimeList = backupTimeList;
    }

    public String[] getBackupDayOfTheWeekList() {
        return backupDayOfTheWeekList;
    }

    public void setBackupDayOfTheWeekList(String[] backupDayOfTheWeekList) {
        this.backupDayOfTheWeekList = backupDayOfTheWeekList;
    }

    public int[] getBackupNotifyTimeList() {
        return backupNotifyTimeList;
    }

    public void setBackupNotifyTimeList(int[] backupNotifyTimeList) {
        this.backupNotifyTimeList = backupNotifyTimeList;
    }

    public String[] getResetWorldNameOfNormal() {
        return resetWorldNameOfNormal;
    }

    public void setResetWorldNameOfNormal(String[] resetWorldNameOfNormal) {
        this.resetWorldNameOfNormal = resetWorldNameOfNormal;
    }

    public String[] getResetWorldNameOfNether() {
        return resetWorldNameOfNether;
    }

    public void setResetWorldNameOfNether(String[] resetWorldNameOfNether) {
        this.resetWorldNameOfNether = resetWorldNameOfNether;
    }

    public String[] getResetWorldNameOfEnd() {
        return resetWorldNameOfEnd;
    }

    public void setResetWorldNameOfEnd(String[] resetWorldNameOfEnd) {
        this.resetWorldNameOfEnd = resetWorldNameOfEnd;
    }

    public int getWorldOfNormalSize() {
        return worldOfNormalSize;
    }

    public void setWorldOfNormalSize(int worldOfNormalSize) {
        this.worldOfNormalSize = worldOfNormalSize;
    }

    public int getWorldOfNetherSize() {
        return worldOfNetherSize;
    }

    public void setWorldOfNetherSize(int worldOfNetherSize) {
        this.worldOfNetherSize = worldOfNetherSize;
    }

    public int getWorldOfEndSize() {
        return worldOfEndSize;
    }

    public void setWorldOfEndSize(int worldOfEndSize) {
        this.worldOfEndSize = worldOfEndSize;
    }

    public String[] getBackupWorldName() {
        return backupWorldName;
    }

    public void setBackupWorldName(String[] backupWorldName) {
        this.backupWorldName = backupWorldName;
    }

    public boolean isUseMultiversePortals() {
        return useMultiversePortals;
    }

    public void setUseMultiversePortals(boolean useMultiversePortals) {
        this.useMultiversePortals = useMultiversePortals;
    }

    public boolean isGateAutoBuildOfNormal() {
        return gateAutoBuildOfNormal;
    }

    public void setGateAutoBuildOfNormal(boolean gateAutoBuildOfNormal) {
        this.gateAutoBuildOfNormal = gateAutoBuildOfNormal;
    }

    public boolean isGateAutoBuildOfNether() {
        return gateAutoBuildOfNether;
    }

    public void setGateAutoBuildOfNether(boolean gateAutoBuildOfNether) {
        this.gateAutoBuildOfNether = gateAutoBuildOfNether;
    }

    public boolean isGateAutoBuildOfEnd() {
        return gateAutoBuildOfEnd;
    }

    public void setGateAutoBuildOfEnd(boolean gateAutoBuildOfEnd) {
        this.gateAutoBuildOfEnd = gateAutoBuildOfEnd;
    }

    public String[] getPortalNameOfNormal() {
        return portalNameOfNormal;
    }

    public void setPortalNameOfNormal(String[] portalNameOfNormal) {
        this.portalNameOfNormal = portalNameOfNormal;
    }

    public String[] getPortalNameOfNether() {
        return portalNameOfNether;
    }

    public void setPortalNameOfNether(String[] portalNameOfNether) {
        this.portalNameOfNether = portalNameOfNether;
    }

    public String[] getPortalNameOfEnd() {
        return portalNameOfEnd;
    }

    public void setPortalNameOfEnd(String[] portalNameOfEnd) {
        this.portalNameOfEnd = portalNameOfEnd;
    }

    public String getBackupLocation() {
        return backupLocation;
    }

    public void setBackupLocation(String backupLocation) {
        this.backupLocation = backupLocation;
    }
}