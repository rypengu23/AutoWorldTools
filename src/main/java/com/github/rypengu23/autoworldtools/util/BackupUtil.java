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
     * Configに記載された全ワールドのバックアップ・古いファイルの削除を行う。
     */
    public void worldsBackup() {

        for (String worldName : mainConfig.getBackupWorldName()) {
            createWorldFileZip(worldName);
            deleteOldFile(worldName);
        }

    }

    /**
     * 引数のワールド名のデータをZIPに圧縮後、保存する。
     *
     * @param worldName
     */
    public void createWorldFileZip(String worldName) {


        Bukkit.getLogger().info("[AutoWorldTools] "+ ConsoleMessage.BackupUtil_startZip + worldName);

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
        //world.save();

        //バックアップするワールド取得
        File worldFile = world.getWorldFolder();

        //バックアップするファイルをworkにコピー
        try {
            FileUtils.copyDirectory(worldFile, workDirectory);
        } catch (IOException e) {
            e.printStackTrace();
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
            Bukkit.getLogger().warning("[AutoWorldTools] "+ ConsoleMessage.BackupUtil_backupFailure + worldName);
        }


        //一時ファイルを削除
        try {
            FileUtils.deleteDirectory(new File(saveDirectory.toString() + "/work" + zipFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getLogger().info("[AutoWorldTools] "+ ConsoleMessage.BackupUtil_compZip + worldName);


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

    /**
     * バックアップ時刻をチェックし、バックアップ・通知を実行
     */
    public void backupTimeCheck() {

        BackupUtil backupUtil = new BackupUtil();
        CheckUtil checkUtil = new CheckUtil();
        ConvertUtil convertUtil = new ConvertUtil();

        String[] weekNameJP = {"日", "月", "火", "水", "木", "金", "土"};
        String[] weekNameEN = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

        //現在の時刻を取得
        Calendar nowCalendar = Calendar.getInstance();

        //バックアップ曜日を取得
        ArrayList<Integer> backupDayOfWeekNumberList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (String backupDay : mainConfig.getBackupDayOfTheWeekList()) {
                if (backupDay.equals(weekNameJP[i]) || backupDay.equals(weekNameEN[i])) {
                    backupDayOfWeekNumberList.add(i + 1);
                }
            }
        }

        //バックアップ曜日・時間をリスト化
        ArrayList<Calendar> backupTimeList = new ArrayList<>();

        for (int dayOfWeek : backupDayOfWeekNumberList) {
            for (String time : mainConfig.getBackupTimeList()) {
                Calendar work = (Calendar) nowCalendar.clone();
                work.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                work.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));
                work.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1]));
                work.set(Calendar.SECOND, 0);
                backupTimeList.add(work);
            }
        }

        //現在時刻とリセット前通知時刻が同一か
        for (Calendar backupTime : backupTimeList) {

            //現在時刻とリセット前通知時刻が同一か
            for (int notifyTime : mainConfig.getBackupNotifyTimeList()) {
                Calendar work = (Calendar) backupTime.clone();
                work.add(Calendar.SECOND, -notifyTime);

                //曜日・時・分・秒が同一かどうか
                if (nowCalendar.get(Calendar.DAY_OF_WEEK) == work.get(Calendar.DAY_OF_WEEK) && nowCalendar.get(Calendar.HOUR_OF_DAY) == work.get(Calendar.HOUR_OF_DAY) && nowCalendar.get(Calendar.MINUTE) == work.get(Calendar.MINUTE) && nowCalendar.get(Calendar.SECOND) == work.get(Calendar.SECOND)) {
                    //同一な場合
                    //残り時間を計算
                    int hour = notifyTime / 3600;
                    int minute = (notifyTime % 3600) / 60;
                    int second = notifyTime - (hour * 3600) - (minute * 60);
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
                    Bukkit.getServer().broadcastMessage("§a" + messageConfig.getPrefix() + " §f" + convertUtil.placeholderUtil("{countdown}", countdownStr.toString(), messageConfig.getBackupCountdown()));

                    //処理終了
                    return;
                }
            }

            //現在時刻とバックアップ時刻が同一か
            if (backupTime.get(Calendar.DAY_OF_WEEK) == nowCalendar.get(Calendar.DAY_OF_WEEK) && backupTime.get(Calendar.HOUR_OF_DAY) == nowCalendar.get(Calendar.HOUR_OF_DAY) && backupTime.get(Calendar.MINUTE) == nowCalendar.get(Calendar.MINUTE) && backupTime.get(Calendar.SECOND) == nowCalendar.get(Calendar.SECOND)) {

                //バックアップ実行
                Runnable runnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        //メッセージが空白で無ければ送信
                        if (!checkUtil.checkNullOrBlank(messageConfig.getBackupStart())) {
                            Bukkit.getServer().broadcastMessage("§a" + messageConfig.getPrefix() + " §f" + messageConfig.getBackupStart());
                        }

                        //バックアップ処理
                        backupUtil.worldsBackup();

                        //メッセージが空白で無ければ送信
                        if (!checkUtil.checkNullOrBlank(messageConfig.getBackupComplete())) {
                            Bukkit.getServer().broadcastMessage("§a" + messageConfig.getPrefix() + " §f" + messageConfig.getBackupComplete());
                        }
                    }
                };

                Bukkit.getServer().getScheduler().runTaskAsynchronously(AutoWorldTools.getInstance(), runnable);
            }
        }
    }


}
