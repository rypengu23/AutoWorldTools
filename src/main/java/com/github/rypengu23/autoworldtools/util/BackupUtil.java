package com.github.rypengu23.autoworldtools.util;

import com.github.rypengu23.autoworldtools.AutoWorldTools;
import com.github.rypengu23.autoworldtools.config.ConfigLoader;
import com.github.rypengu23.autoworldtools.config.ConsoleMessage;
import com.github.rypengu23.autoworldtools.config.MainConfig;
import com.github.rypengu23.autoworldtools.config.MessageConfig;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BackupUtil {

    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;
    //private String worldName;

    public BackupUtil() {
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    /**
     * 現在時刻がバックアップ実行時刻か判定
     *
     * @param nowCalendar
     * @return
     */
    public boolean checkBackupTime(Calendar nowCalendar) {

        CheckUtil checkUtil = new CheckUtil();
        ConvertUtil convertUtil = new ConvertUtil();

        //バックアップ時刻リストを取得
        ArrayList<Calendar> backupTimeList = convertUtil.convertCalendar(mainConfig.getBackupDayOfTheWeekList(), mainConfig.getBackupTimeList());

        //比較
        if (backupTimeList != null) {
            for (Calendar backupTime : backupTimeList) {
                if (checkUtil.checkComparisonTime(nowCalendar, backupTime)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 現在時刻がバックアップ前アナウンス時刻か判定。
     * 戻り地が-1の場合、アナウンス時刻ではない。
     *
     * @param nowCalendar
     * @return
     */
    public int checkAnnounceBeforeBackupTime(Calendar nowCalendar) {

        CheckUtil checkUtil = new CheckUtil();
        ConvertUtil convertUtil = new ConvertUtil();

        //リセット時刻リストを取得
        ArrayList<Calendar> backupTimeList = convertUtil.convertCalendar(mainConfig.getBackupDayOfTheWeekList(), mainConfig.getBackupTimeList());

        //比較
        if (backupTimeList != null) {
            for (Calendar backupTime : backupTimeList) {
                int result = checkUtil.checkComparisonTimeOfList(nowCalendar, backupTime, mainConfig.getBackupNotifyTimeList());
                if (result != -1) {
                    return result;
                }
            }
        }
        return -1;
    }

    /**
     * Configに記載された全ワールドをバックアップ
     * メッセージ等も送信
     */
    public void autoBackup() {

        CheckUtil checkUtil = new CheckUtil();



        Runnable runnable = new BukkitRunnable() {
            @Override
            public void run() {

                //メッセージが空白で無ければ送信
                //バックアップ開始メッセージ(Discord)
                if (mainConfig.isUseDiscordSRV() && !checkUtil.checkNullOrBlank(messageConfig.getBackupStartOfDiscord())) {
                    DiscordUtil discordUtil = new DiscordUtil();
                    discordUtil.sendMessageMainChannel(messageConfig.getBackupStartOfDiscord());
                }

                //全ワールドバックアップ
                for (String worldName : mainConfig.getBackupWorldName()) {
                    createWorldFileZip(worldName);
                    deleteOldFile(worldName);
                }

                //メッセージが空白で無ければ送信
                //バックアップ完了メッセージ
                if (!checkUtil.checkNullOrBlank(messageConfig.getBackupComplete())) {
                    Bukkit.getServer().broadcastMessage("§a" + messageConfig.getPrefix() + " §f" + messageConfig.getBackupComplete());
                }

                //メッセージが空白で無ければ送信
                //バックアップ完了メッセージ(Discord)
                if (mainConfig.isUseDiscordSRV() && !checkUtil.checkNullOrBlank(messageConfig.getBackupCompleteOfDiscord())) {
                    DiscordUtil discordUtil = new DiscordUtil();
                    discordUtil.sendMessageMainChannel(messageConfig.getBackupCompleteOfDiscord());
                }
                AutoWorldTools.backupTask = null;

            }
        };

        AutoWorldTools.backupTask = Bukkit.getServer().getScheduler().runTaskAsynchronously(AutoWorldTools.getInstance(), runnable);

    }

    /**
     * 引数のカウントダウン秒数をもとに、メッセージを送信。
     *
     * @param second
     */
    public void sendNotify(int second) {

        CheckUtil checkUtil = new CheckUtil();
        ConvertUtil convertUtil = new ConvertUtil();

        if (second <= 0) {
            return;
        }

        String countdownStr = convertUtil.createCountdown(second, messageConfig);

        //メッセージが空白で無ければ送信
        //カウントダウンメッセージ
        if (!checkUtil.checkNullOrBlank(messageConfig.getResetCountdown())) {
            Bukkit.getServer().broadcastMessage("§a" + messageConfig.getPrefix() + " §f" + convertUtil.placeholderUtil("{countdown}", countdownStr, messageConfig.getBackupCountdown()));
        }
    }

    /**
     * 引数のワールド名のデータをZIPに圧縮後、保存する。
     *
     * @param worldName
     */
    public void createWorldFileZip(String worldName) {


        Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.BackupUtil_startZip + worldName);

        //ファイル名用日付取得
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String zipFileName = format.format(nowDate);

        //バックアップ保存先フォルダを作成
        File saveDirectory = new File(mainConfig.getBackupLocation() + worldName);
        saveDirectory.mkdir();

        //一時ファイル保存先フォルダを作成
        File workDirectory = new File(saveDirectory.toString() + "/work" + zipFileName + "/" + worldName);
        //workDirectory.mkdir();

        //バックアップするワールドをセーブ
        World world = Bukkit.getWorld(worldName);
        world.save();

        //バックアップするワールド取得
        File worldFile = world.getWorldFolder();

        //バックアップするファイルをworkにコピー
        try {
            FileUtils.copyDirectory(worldFile, workDirectory);
        } catch (FileSystemException e){
            if(!e.getFile().equalsIgnoreCase("session.lock")) {
                Bukkit.getLogger().warning("[AutoWorldTools] " + ConsoleMessage.BackupUtil_backupFailure);
            }
        } catch (IOException e) {
            Bukkit.getLogger().warning("[AutoWorldTools] " + ConsoleMessage.BackupUtil_backupFailure);
        }

        //ZIP化設定
        ZipParameters params = new ZipParameters();
        params.setCompressionMethod(CompressionMethod.DEFLATE);
        params.setCompressionLevel(CompressionLevel.FAST);

        //ZIP化
        try {
            new ZipFile(mainConfig.getBackupLocation() + worldName + "/" + worldName + zipFileName + ".zip").addFolder(workDirectory, params);
        } catch (ZipException e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("[AutoWorldTools] " + ConsoleMessage.BackupUtil_backupFailure + worldName);
        }


        //一時ファイルを削除
        try {
            FileUtils.deleteDirectory(new File(saveDirectory.toString() + "/work" + zipFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getLogger().info("[AutoWorldTools] " + ConsoleMessage.BackupUtil_compZip + worldName);


    }

    /**
     * Configで設定されている保存上限を超えた場合に古いファイルを削除する。
     *
     * @param worldName
     */
    public void deleteOldFile(String worldName) {

        //バックアップロケーション内のファイル一覧取得
        FilenameFilter filter = new FilenameFilter() {

            public boolean accept(File file, String str) {

                // 拡張子を指定する
                if (str.endsWith("zip")) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        //ZIPファイルリスト作成
        File[] fileList = new File(mainConfig.getBackupLocation() + worldName).listFiles(filter);

        if (fileList == null) {
            return;
        }
        List<File> backupFileList = Arrays.asList(fileList);

        //ソート
        Collections.sort(backupFileList);

        //上限を超えたファイル削除
        for (int i = 0; i < backupFileList.size() - mainConfig.getBackupLimit(); i++) {
            backupFileList.get(i).delete();
        }

    }

}
