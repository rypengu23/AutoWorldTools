package com.github.rypengu23.autoresetmaterialworld;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.stream.Stream;

public class Config {

    private final Plugin plugin;
    private FileConfiguration config = null;

    //バージョン
    private String version;

    //指定時刻自動リセット機能の利用可否
    private boolean autoReset;

    //リセット時間
    private String[] resetTimeList;
    //リセット曜日
    private String[] resetDayOfTheWeekList;
    //リセット通知時間
    private int[] resetNotifyTimeList;

    //ワールド名
    private String[] materialWorldNameOfNormal;
    private String[] materialWorldNameOfNether;
    private String[] materialWorldNameOfEnd;

    //ワールドサイズ
    private int materialWorldOfNormalSize;
    private int materialWorldOfNetherSize;
    private int materialWorldOfEndSize;

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

    public Config(Plugin plugin) {
        this.plugin = plugin;
        // ロードする
        load();
    }

    /**
     * 設定をロードします
     */
    public void load() {
        // 設定ファイルを保存
        plugin.saveDefaultConfig();
        config = plugin.getConfig();

        //バージョン
        version = config.getString("version");

        //自動リセット利用可否
        autoReset = config.getBoolean("setting.autoReset");

        //時間
        resetTimeList = config.getString("time.resetTime").split(",");
        resetDayOfTheWeekList = config.getString("time.resetDayOfTheWeek").split(",");
        resetNotifyTimeList = Stream.of(config.getString("time.resetNotifyTime").split(",")).mapToInt(Integer::parseInt).toArray();

        //MultiversePortals利用可否
        useMultiversePortals = config.getBoolean("gateInfo.useMultiversePortals");

        //素材世界(ノーマル)
        materialWorldNameOfNormal = config.getString("worldInfo.materialWorldNameOfNormal").split(",");
        materialWorldOfNormalSize = config.getInt("border.materialWorldOfNormalSize");
        portalNameOfNormal = config.getString("gateInfo.portalNameOfNormal").split(",");
        gateAutoBuildOfNormal = config.getBoolean("gateInfo.gateAutoBuildOfNormal");

        //素材世界(ネザー)
        materialWorldNameOfNether = config.getString("worldInfo.materialWorldNameOfNether").split(",");
        materialWorldOfNetherSize = config.getInt("border.materialWorldOfNetherSize");
        portalNameOfNether = config.getString("gateInfo.portalNameOfNether").split(",");
        gateAutoBuildOfNether = config.getBoolean("gateInfo.gateAutoBuildOfNether");

        //素材世界(エンド)
        materialWorldNameOfEnd = config.getString("worldInfo.materialWorldNameOfEnd").split(",");
        materialWorldOfEndSize = config.getInt("border.materialWorldOfEndSize");
        portalNameOfEnd = config.getString("gateInfo.portalNameOfEnd").split(",");
        gateAutoBuildOfEnd = config.getBoolean("gateInfo.gateAutoBuildOfEnd");

    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isAutoReset() {
        return autoReset;
    }

    public void setAutoReset(boolean autoReset) {
        this.autoReset = autoReset;
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

    public String[] getMaterialWorldNameOfNormal() {
        return materialWorldNameOfNormal;
    }

    public void setMaterialWorldNameOfNormal(String[] materialWorldNameOfNormal) {
        this.materialWorldNameOfNormal = materialWorldNameOfNormal;
    }

    public String[] getMaterialWorldNameOfNether() {
        return materialWorldNameOfNether;
    }

    public void setMaterialWorldNameOfNether(String[] materialWorldNameOfNether) {
        this.materialWorldNameOfNether = materialWorldNameOfNether;
    }

    public String[] getMaterialWorldNameOfEnd() {
        return materialWorldNameOfEnd;
    }

    public void setMaterialWorldNameOfEnd(String[] materialWorldNameOfEnd) {
        this.materialWorldNameOfEnd = materialWorldNameOfEnd;
    }

    public int getMaterialWorldOfNormalSize() {
        return materialWorldOfNormalSize;
    }

    public void setMaterialWorldOfNormalSize(int materialWorldOfNormalSize) {
        this.materialWorldOfNormalSize = materialWorldOfNormalSize;
    }

    public int getMaterialWorldOfNetherSize() {
        return materialWorldOfNetherSize;
    }

    public void setMaterialWorldOfNetherSize(int materialWorldOfNetherSize) {
        this.materialWorldOfNetherSize = materialWorldOfNetherSize;
    }

    public int getMaterialWorldOfEndSize() {
        return materialWorldOfEndSize;
    }

    public void setMaterialWorldOfEndSize(int materialWorldOfEndSize) {
        this.materialWorldOfEndSize = materialWorldOfEndSize;
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
}
