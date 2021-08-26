package com.github.rypengu23.autoworldtools.config;

public class CommandMessage {

    public static String AutoWorldTools_ConfigReload = "Config reloaded.";
    public static String AutoWorldTools_CommandFailure = "Command failure.";
    public static String AutoWorldTools_DoNotHavePermission = "You do not have permission.";
    public static String AutoWorldTools_CommandResetStart = "Reset of worlds listed in Config. type:";
    public static String AutoWorldTools_CommandResetComp = "Reset of worlds listed in Config is complete. type:";
    public static String AutoWorldTools_CommandAllResetStart = "Resets all worlds listed in Config.";
    public static String AutoWorldTools_CommandAllResetComp = "Resets all worlds listed in Config is completed.";
    public static String AutoWorldTools_CommandBackupStart = "Start backup. World:";
    public static String AutoWorldTools_CommandBackupComp = "Backup is complete. World:";
    public static String AutoWorldTools_CommandBackupNotFoundWorld = "That world cannot be found. World:";

    public static String Command_Help_Line1 = "§b――――― §fAutoWorldTools Help §b―――――";
    public static String Command_Help_Reset = "§e/awt reset [world type] §f:Regenerate the world written in Config for each type.";
    public static String Command_Help_Backup = "§e/awt backup [world name] §f:Backs up the specified world.";
    public static String Command_Help_Resetinfo = "§e/awt reset info §f:Displays the automatic reset time.";
    public static String Command_Help_Backupinfo = "§e/awt backup info §f:Displays the automatic backup time.";
    public static String Command_Help_Restartinfo = "§e/awt restart info §f:Displays the automatic restart time.";
    public static String Command_Help_reload = "§e/awt reload §f:Reload Config.";
    public static String Command_Help_LineLast = "§b――――――――――――――――――――――――――――――――";


    private MainConfig mainConfig;

    public CommandMessage(MainConfig mainConfig){
        this.mainConfig = mainConfig;
    }

    public void changeLanguageCommandMessages(){
        if(mainConfig.getLanguage().equals("ja")){
            AutoWorldTools_ConfigReload = "Configをリロードしました。";
            AutoWorldTools_CommandFailure = "不正なコマンドです。";
            AutoWorldTools_DoNotHavePermission = "権限を所有していません。";
            AutoWorldTools_CommandResetStart = "Configに登録されたワールドのリセットを開始します。 種別:";
            AutoWorldTools_CommandResetComp = "Configに登録されたワールドのリセットが完了しました。 種別:";
            AutoWorldTools_CommandAllResetStart = "Configに登録された全ワールドのリセットを開始します。";
            AutoWorldTools_CommandAllResetComp = "Configに登録された全ワールドのリセットが完了しました。。";
            AutoWorldTools_CommandBackupStart = "バックアップを開始します。 ワールド名:";
            AutoWorldTools_CommandBackupComp = "バックアップが完了しました。 ワールド名:";
            AutoWorldTools_CommandBackupNotFoundWorld = "ワールドが見つかりません。 ワールド名:";

            Command_Help_Line1 = "§b――――― §fAutoWorldTools コマンドガイド §b―――――";
            Command_Help_Reset = "§e/awt reset [ﾜｰﾙﾄﾞ種別] §f: 種別ごとにConfigに記載されたﾜｰﾙﾄﾞを再生成。";
            Command_Help_Backup = "§e/awt backup [ﾜｰﾙﾄﾞ名] §f: 指定したﾜｰﾙﾄﾞをバックアップ。";
            Command_Help_Resetinfo = "§e/awt reset info §f: 自動リセット時刻を表示。";
            Command_Help_Backupinfo = "§e/awt backup info §f: 自動バックアップ時刻を表示。";
            Command_Help_Restartinfo = "§e/awt restart info §f:自動再起動時刻を表示。";
            Command_Help_reload = "§e/awt reload §f: Configをリロード。";
            Command_Help_LineLast = "§b――――――――――――――――――――――――――――――――――――――――";
        } else if(mainConfig.getLanguage().equals("en")){
            AutoWorldTools_ConfigReload = "Config reloaded.";
            AutoWorldTools_CommandFailure = "Command failure.";
            AutoWorldTools_DoNotHavePermission = "You do not have permission.";
            AutoWorldTools_CommandResetStart = "Reset of worlds listed in Config. type:";
            AutoWorldTools_CommandResetComp = "Reset of worlds listed in Config is complete. type:";
            AutoWorldTools_CommandAllResetStart = "Resets all worlds listed in Config.";
            AutoWorldTools_CommandAllResetComp = "Resets all worlds listed in Config is completed.";
            AutoWorldTools_CommandBackupStart = "Start backup. World:";
            AutoWorldTools_CommandBackupComp = "Backup is complete. World:";
            AutoWorldTools_CommandBackupNotFoundWorld = "That world cannot be found. World:";

            Command_Help_Line1 = "§b――――― §fAutoWorldTools Help §b―――――";
            Command_Help_Reset = "§e/awt reset [world type] §f:Regenerate the world written in Config for each type.";
            Command_Help_Backup = "§e/awt backup [world name] §f:Backs up the specified world.";
            Command_Help_Resetinfo = "§e/awt reset info §f:Displays the automatic reset time.";
            Command_Help_Backupinfo = "§e/awt backup info §f:Displays the automatic backup time.";
            Command_Help_Restartinfo = "§e/awt restart info §f:Displays the automatic restart time.";
            Command_Help_reload = "§e/awt reload §f:Reload Config.";
            Command_Help_LineLast = "§b――――――――――――――――――――――――――――――――";
        }
    }
}
