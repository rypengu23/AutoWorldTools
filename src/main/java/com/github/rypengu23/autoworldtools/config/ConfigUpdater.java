package com.github.rypengu23.autoworldtools.config;

import com.github.rypengu23.autoworldtools.AutoWorldTools;
import com.github.rypengu23.autoworldtools.util.CheckUtil;
import jp.jyn.jbukkitlib.config.YamlLoader;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConfigUpdater {

    private Plugin plugin;

    private ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    public ConfigUpdater() {
        this.plugin = AutoWorldTools.getInstance();
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    public YamlLoader getMessageLoader(String fileName){
        return new YamlLoader(plugin, fileName);
    }

    public boolean configUpdateCheck(){

        boolean checkFlag = false;

        Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.ConfigUpdater_CheckUpdateConfig);
        if(mainConfig.getVersion().compareTo(AutoWorldTools.pluginVersion) == -1){
            Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.ConfigUpdater_UpdateConfig);
            migrateNewConfig(1);
            checkFlag = true;
        }
        if(messageConfig.getVersion().compareTo(AutoWorldTools.pluginVersion) == -1) {
            Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.ConfigUpdater_NoConfigUpdates);
            migrateNewConfig(2);
            checkFlag = true;
        }
        return checkFlag;
    }


    /**
     * Configを新しいバージョンに移行。
     * type=1 : MainConfig
     * type=2 : MessageConfig
     * @param type
     */
    public void migrateNewConfig(int type) {

        CheckUtil checkUtil = new CheckUtil();
        Configuration configuration;

        YamlLoader mainLoader = new YamlLoader(plugin, "config.yml");
        YamlLoader messageLoader = new YamlLoader(plugin, "message_"+ mainConfig.getLanguage() +".yml");

        //現在のConfigを読み込み
        ArrayList<String> configFileName = new ArrayList<>();
        if(type == 1) {
            configuration = mainLoader.getConfig();
            configFileName.add("config.yml");
        }else{
            configuration = messageLoader.getConfig();
            configFileName.add("message_en.yml");
            configFileName.add("message_ja.yml");
        }

        for(String fileName:configFileName) {

            if(type == 2){
                configuration = getMessageLoader(fileName).getConfig();
            }

            //ファイルを指定
            File configFile = new File(plugin.getDataFolder(), fileName);

            //古いConfigをバックアップ
            File oldConfigFile = configFile;
            try {
                oldConfigFile.createNewFile();
            } catch (IOException e) {

            }
            //ファイル名用に現在日時取得
            //ファイル名用日付取得
            Date nowDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmm");

            Double version = 1.0;
            if(type == 1){
                version = mainConfig.getVersion();
            }else{
                version = messageConfig.getVersion();
            }

            //古いConfigを別名で保存
            oldConfigFile.renameTo(new File(plugin.getDataFolder(), fileName.split("\\.")[0] + "_Ver" + version + "-" + format.format(nowDate) + ".yml.old"));

            //古いConfigを削除
            configFile.delete();

            //新しいConfigの配置
            configLoader.reloadConfig();

            //Configを全行取得
            List<String> ymlLines = new ArrayList<>();
            try {
                ymlLines = Files.readAllLines(configFile.toPath(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                Bukkit.getLogger().warning("[AutoWorldTools] New config file read failure.");
            }

            //Configを削除
            configFile.delete();

            //新しいymlファイルの作成
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().warning("[AutoWorldTools] New config file create failure.");
            }

            //書き出し
            try {
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(osw);

                String sectionName = "";

                for (String line : ymlLines) {

                    //空白の場合、スキップ
                    if (line.trim().length() == 0) {
                        bufferedWriter.append("");
                        bufferedWriter.newLine();
                        continue;
                    }
                    //コメント行の場合、スキップ
                    if ("#".equals(String.valueOf(line.trim().charAt(0)))) {
                        bufferedWriter.append(line);
                        bufferedWriter.newLine();
                        continue;
                    }
                    //パラメーター名の右の値を削除して置き換え
                    //左右の値を取得
                    String[] lineContent = line.split(":", 2);

                    //セクション外の値である場合、記憶したセクション名をリセット
                    if (!checkUtil.checkNullOrBlank(sectionName) && !String.valueOf(lineContent[0].charAt(0)).equals(" ")) {
                        sectionName = "";
                    }

                    //:より右に何も無いor空白の場合、セクション名を記憶し、スキップ
                    if (lineContent[1].trim().length() == 0) {
                        sectionName = lineContent[0];
                        bufferedWriter.append(line);
                        bufferedWriter.newLine();
                        continue;
                    }

                    //古いConfigに値が無い項目の場合
                    try {
                        configuration.get(lineContent[0].trim());
                    } catch (NullPointerException e) {
                        bufferedWriter.append(line);
                        bufferedWriter.newLine();
                        continue;
                    }

                    String sectionWork = "";
                    if (checkUtil.checkNullOrBlank(sectionName)) {
                        sectionWork = sectionName;
                    } else {
                        sectionWork = sectionName + ".";
                    }

                    Map<String, String> typeList = mainConfig.getConfigTypeList();
                    //型を識別
                    if(type == 1) {
                        //config.yml
                        if ((sectionWork + lineContent[0]).equalsIgnoreCase("version")) {
                            //バージョン：更新
                            bufferedWriter.append(lineContent[0] + ": " + AutoWorldTools.pluginVersion);
                        } else if (typeList.get(sectionWork + lineContent[0].trim()).equals("string")) {
                            //String:文字列を""で囲んで出力
                            bufferedWriter.append(lineContent[0] + ": \"" + configuration.getString(sectionWork + lineContent[0].trim()) + "\"");
                        } else if (typeList.get(sectionWork + lineContent[0].trim()).equals("int")) {
                            //int:
                            bufferedWriter.append(lineContent[0] + ": " + configuration.getInt(sectionWork + lineContent[0].trim()));
                        } else if (typeList.get(sectionWork + lineContent[0].trim()).equals("double")) {
                            //double:
                            bufferedWriter.append(lineContent[0] + ": " + configuration.getDouble(sectionWork + lineContent[0].trim()));
                        } else if (typeList.get(sectionWork + lineContent[0].trim()).equals("boolean")) {
                            //boolean
                            bufferedWriter.append(lineContent[0] + ": " + configuration.getBoolean(sectionWork + lineContent[0].trim()));
                        }
                    }else{
                        //message_xx.yml
                        if ((sectionWork + lineContent[0]).equalsIgnoreCase("version")) {
                            //バージョン：更新
                            bufferedWriter.append(lineContent[0] + ": " + AutoWorldTools.pluginVersion);
                        } else {
                            //String:文字列を""で囲んで出力
                            bufferedWriter.append(lineContent[0] + ": \"" + configuration.getString(sectionWork + lineContent[0].trim()) + "\"");
                        }
                    }
                    bufferedWriter.newLine();

                }

                //クローズ
                bufferedWriter.close();
            } catch (IOException e) {
                Bukkit.getLogger().warning("[AutoWorldTools] New config file get failure.");
            }
        }

    }

}
