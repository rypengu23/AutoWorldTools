package com.github.rypengu23.autoworldtools.config;

import com.github.rypengu23.autoworldtools.AutoWorldTools;
import com.github.rypengu23.autoworldtools.model.ResetWorldModel;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MainConfig {

    private Plugin plugin;
    private FileConfiguration config = null;

    //バージョン
    private Double version;

    //言語
    private String language;
    private String consoleLanguage;

    //DiscordSRV利用可否
    private boolean useDiscordSRV;

    //指定時刻自動リセット機能の利用可否
    private boolean autoReset;
    //自動バックアップ利用可否
    private boolean autoBackup;
    //自動再起動利用可否
    private boolean autoRestart;
    //バックアップ保存上限
    private int backupLimit;

    //リセット時間
    private String[] resetTimeList;
    //リセット曜日
    private String[] resetDayOfTheWeekList;
    //リセット通知時間
    private int[] resetNotifyTimeList;

    //リセットワールド名
    private ArrayList<ResetWorldModel> resetWorldNameOfNormal = new ArrayList<>();
    private ArrayList<ResetWorldModel> resetWorldNameOfNether = new ArrayList<>();
    private ArrayList<ResetWorldModel> resetWorldNameOfEnd = new ArrayList<>();

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

    //Dynmap利用可否
    private boolean useDynmap;

    //リセット時マップ削除可否
    private boolean mapPurgeAtResetOfNormal;
    private boolean mapPurgeAtResetOfNether;
    private boolean mapPurgeAtResetOfEnd;

    //個別に削除するマップ
    private String[] mapPurgeAtResetWorldName;

    //バックアップ時間
    private String[] backupTimeList;
    //バックアップ曜日
    private String[] backupDayOfTheWeekList;
    //バックアップ通知時間
    private int[] backupNotifyTimeList;

    //バックアップ保存先
    private String backupLocation;

    //再起動時間
    private String[] restartTimeList;
    //再起動曜日
    private String[] restartDayOfTheWeekList;
    //再起動通知時間
    private int[] restartNotifyTimeList;

    public MainConfig(Configuration config) {

        plugin = AutoWorldTools.getInstance();

        this.config = (FileConfiguration) config;

        //バージョン
        version = config.getDouble("version");

        //言語
        language = config.getString("setting.language");
        consoleLanguage = config.getString("setting.consoleLanguage");

        //自動リセット利用可否
        autoReset = config.getBoolean("setting.autoReset");
        //自動バックアップ利用可否
        autoBackup = config.getBoolean("setting.autoBackup");
        //自動再起動利用可否
        autoRestart = config.getBoolean("setting.autoRestart");

        //DiscordSRV利用可否
        useDiscordSRV = config.getBoolean("setting.useDiscordSRV");

        //バックアップ保存上限
        backupLimit = config.getInt("setting.backupLimit");


        //リセット時間
        resetTimeList = config.getString("resetTime.resetTime").split(",");
        resetDayOfTheWeekList = config.getString("resetTime.resetDayOfTheWeek").split(",");
        if(!config.getString("resetTime.resetNotifyTime").equals("")) {
            resetNotifyTimeList = Stream.of(config.getString("resetTime.resetNotifyTime").split(",")).mapToInt(Integer::parseInt).toArray();
        }

        //リセットワールド情報
        //ノーマル
        String worldNameWorkOfNormal[] = config.getString("resetWorldInfo.worldNameOfNormal").split(",");
        for(String worldName:worldNameWorkOfNormal){
            if(worldName.contains(":")){
                String[] worldInfoWork = worldName.split(":");
                ResetWorldModel worldModelWork = new ResetWorldModel();
                worldModelWork.setWorldName(worldInfoWork[0]);
                try {
                    worldModelWork.setSeed(Long.parseLong(worldInfoWork[1]));
                }catch(Exception e){
                    worldModelWork.setSeed(-1);
                }
                resetWorldNameOfNormal.add(worldModelWork);
            }else{
                resetWorldNameOfNormal.add(new ResetWorldModel(worldName, (long) -1));
            }
        }
        //ネザー
        String worldNameWorkOfNether[] = config.getString("resetWorldInfo.worldNameOfNether").split(",");
        for(String worldName:worldNameWorkOfNether){
            if(worldName.contains(":")){
                String[] worldInfoWork = worldName.split(":");
                ResetWorldModel worldModelWork = new ResetWorldModel();
                worldModelWork.setWorldName(worldInfoWork[0]);
                try {
                    worldModelWork.setSeed(Long.parseLong(worldInfoWork[1]));
                }catch(Exception e){
                    worldModelWork.setSeed(-1);
                }
                resetWorldNameOfNether.add(worldModelWork);
            }else{
                resetWorldNameOfNether.add(new ResetWorldModel(worldName, (long) -1));
            }
        }
        //エンド
        String worldNameWorkOfEnd[] = config.getString("resetWorldInfo.worldNameOfEnd").split(",");
        for(String worldName:worldNameWorkOfEnd){
            if(worldName.contains(":")){
                String[] worldInfoWork = worldName.split(":");
                ResetWorldModel worldModelWork = new ResetWorldModel();
                worldModelWork.setWorldName(worldInfoWork[0]);
                try {
                    worldModelWork.setSeed(Long.parseLong(worldInfoWork[1]));
                }catch(Exception e){
                    worldModelWork.setSeed(-1);
                }
                resetWorldNameOfEnd.add(worldModelWork);
            }else{
                resetWorldNameOfEnd.add(new ResetWorldModel(worldName, (long) -1));
            }
        }

        //ワールドボーダー
        worldOfNormalSize = config.getInt("border.worldOfNormalSize");
        worldOfNetherSize = config.getInt("border.worldOfNetherSize");
        worldOfEndSize = config.getInt("border.worldOfEndSize");

        //MultiversePortals利用可否
        useMultiversePortals = config.getBoolean("gateInfo.useMultiversePortals");

        //ゲート情報
        portalNameOfNormal = config.getString("gateInfo.portalNameOfNormal").split(",");
        portalNameOfNether = config.getString("gateInfo.portalNameOfNether").split(",");
        portalNameOfEnd = config.getString("gateInfo.portalNameOfEnd").split(",");

        //ゲート自動生成
        gateAutoBuildOfNormal = config.getBoolean("gateInfo.gateAutoBuildOfNormal");
        gateAutoBuildOfNether = config.getBoolean("gateInfo.gateAutoBuildOfNether");
        gateAutoBuildOfEnd = config.getBoolean("gateInfo.gateAutoBuildOfEnd");

        //Dynmap利用可否
        useDynmap = config.getBoolean("dynmap.useDynmap");

        //リセット時マップ削除可否
        mapPurgeAtResetOfNormal = config.getBoolean("dynmap.mapPurgeAtResetOfNormal");
        mapPurgeAtResetOfNether = config.getBoolean("dynmap.mapPurgeAtResetOfNether");
        mapPurgeAtResetOfEnd = config.getBoolean("dynmap.mapPurgeAtResetOfEnd");

        //個別に削除するマップ
        mapPurgeAtResetWorldName = config.getString("dynmap.mapPurgeAtResetWorldName").split(",");;

        //バックアップワールドリスト
        backupWorldName = config.getString("backupWorldInfo.backupWorldName").split(",");

        //バックアップ時間
        backupTimeList = config.getString("backupTime.backupTime").split(",");
        backupDayOfTheWeekList = config.getString("backupTime.backupDayOfTheWeek").split(",");
        if(!config.getString("backupTime.backupNotifyTime").equals("")) {
            backupNotifyTimeList = Stream.of(config.getString("backupTime.backupNotifyTime").split(",")).mapToInt(Integer::parseInt).toArray();
        }

        //バックアップ保存先
        backupLocation = config.getString("backupLocation.fileLocation");


        //再起動時間
        restartTimeList = config.getString("restartTime.restartTime").split(",");
        restartDayOfTheWeekList = config.getString("restartTime.restartDayOfTheWeek").split(",");
        if(!config.getString("restartTime.restartNotifyTime").equals("")) {
            restartNotifyTimeList = Stream.of(config.getString("restartTime.restartNotifyTime").split(",")).mapToInt(Integer::parseInt).toArray();
        }
    }

    public Map getConfigTypeList() {
        //バージョン
        Map<String, String> map = new HashMap<>();
        map.put("version", "double");
        map.put("setting.language", "string");
        map.put("setting.consoleLanguage", "string");
        map.put("setting.autoReset", "boolean");
        map.put("setting.autoBackup", "boolean");
        map.put("setting.autoRestart", "boolean");
        map.put("setting.useDiscordSRV", "boolean");
        map.put("setting.backupLimit", "int");

        map.put("resetTime.resetDayOfTheWeek", "string");
        map.put("resetTime.resetTime", "string");
        map.put("resetTime.resetNotifyTime", "string");

        map.put("resetWorldInfo.worldNameOfNormal", "string");
        map.put("resetWorldInfo.worldNameOfNether", "string");
        map.put("resetWorldInfo.worldNameOfEnd", "string");

        map.put("border.worldOfNormalSize", "int");
        map.put("border.worldOfNetherSize", "int");
        map.put("border.worldOfEndSize", "int");

        map.put("gateInfo.useMultiversePortals", "boolean");
        map.put("gateInfo.portalNameOfNormal", "string");
        map.put("gateInfo.portalNameOfNether", "string");
        map.put("gateInfo.portalNameOfEnd", "string");
        map.put("gateInfo.gateAutoBuildOfNormal", "boolean");
        map.put("gateInfo.gateAutoBuildOfNether", "boolean");
        map.put("gateInfo.gateAutoBuildOfEnd", "boolean");

        map.put("dynmap.useDynmap", "boolean");
        map.put("dynmap.mapPurgeAtResetOfNormal", "boolean");
        map.put("dynmap.mapPurgeAtResetOfNether", "boolean");
        map.put("dynmap.mapPurgeAtResetOfEnd", "boolean");
        map.put("dynmap.mapPurgeAtResetWorldName", "string");

        map.put("backupWorldInfo.backupWorldName", "string");

        map.put("backupTime.backupDayOfTheWeek", "string");
        map.put("backupTime.backupTime", "string");
        map.put("backupTime.backupNotifyTime", "string");

        map.put("backupLocation.fileLocation", "string");

        map.put("restartTime.restartDayOfTheWeek", "string");
        map.put("restartTime.restartTime", "string");
        map.put("restartTime.restartNotifyTime", "string");

        return map;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
        config.set("version", version);
        plugin.saveConfig();
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

    public boolean isUseDiscordSRV() {
        return useDiscordSRV;
    }

    public void setUseDiscordSRV(boolean useDiscordSRV) {
        this.useDiscordSRV = useDiscordSRV;
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

    public boolean isAutoRestart() {
        return autoRestart;
    }

    public void setAutoRestart(boolean autoRestart) {
        this.autoRestart = autoRestart;
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

    public ArrayList<ResetWorldModel> getResetWorldNameOfNormal() {
        return resetWorldNameOfNormal;
    }

    public void setResetWorldNameOfNormal(ArrayList<ResetWorldModel> resetWorldNameOfNormal) {
        this.resetWorldNameOfNormal = resetWorldNameOfNormal;
    }

    public ArrayList<ResetWorldModel> getResetWorldNameOfNether() {
        return resetWorldNameOfNether;
    }

    public void setResetWorldNameOfNether(ArrayList<ResetWorldModel> resetWorldNameOfNether) {
        this.resetWorldNameOfNether = resetWorldNameOfNether;
    }

    public ArrayList<ResetWorldModel> getResetWorldNameOfEnd() {
        return resetWorldNameOfEnd;
    }

    public void setResetWorldNameOfEnd(ArrayList<ResetWorldModel> resetWorldNameOfEnd) {
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

    public boolean isUseDynmap() {
        return useDynmap;
    }

    public void setUseDynmap(boolean useDynmap) {
        this.useDynmap = useDynmap;
    }

    public boolean isMapPurgeAtResetOfNormal() {
        return mapPurgeAtResetOfNormal;
    }

    public void setMapPurgeAtResetOfNormal(boolean mapPurgeAtResetOfNormal) {
        this.mapPurgeAtResetOfNormal = mapPurgeAtResetOfNormal;
    }

    public boolean isMapPurgeAtResetOfNether() {
        return mapPurgeAtResetOfNether;
    }

    public void setMapPurgeAtResetOfNether(boolean mapPurgeAtResetOfNether) {
        this.mapPurgeAtResetOfNether = mapPurgeAtResetOfNether;
    }

    public boolean isMapPurgeAtResetOfEnd() {
        return mapPurgeAtResetOfEnd;
    }

    public void setMapPurgeAtResetOfEnd(boolean mapPurgeAtResetOfEnd) {
        this.mapPurgeAtResetOfEnd = mapPurgeAtResetOfEnd;
    }

    public String[] getMapPurgeAtResetWorldName() {
        return mapPurgeAtResetWorldName;
    }

    public void setMapPurgeAtResetWorldName(String[] mapPurgeAtResetWorldName) {
        this.mapPurgeAtResetWorldName = mapPurgeAtResetWorldName;
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

    public String getBackupLocation() {
        return backupLocation;
    }

    public void setBackupLocation(String backupLocation) {
        this.backupLocation = backupLocation;
    }

    public String[] getRestartTimeList() {
        return restartTimeList;
    }

    public void setRestartTimeList(String[] restartTimeList) {
        this.restartTimeList = restartTimeList;
    }

    public String[] getRestartDayOfTheWeekList() {
        return restartDayOfTheWeekList;
    }

    public void setRestartDayOfTheWeekList(String[] restartDayOfTheWeekList) {
        this.restartDayOfTheWeekList = restartDayOfTheWeekList;
    }

    public int[] getRestartNotifyTimeList() {
        return restartNotifyTimeList;
    }

    public void setRestartNotifyTimeList(int[] restartNotifyTimeList) {
        this.restartNotifyTimeList = restartNotifyTimeList;
    }
}