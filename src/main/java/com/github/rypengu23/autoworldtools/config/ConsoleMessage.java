package com.github.rypengu23.autoworldtools.config;

public class ConsoleMessage {

    public static String AutoWorldTools_startupPlugin = "Plugin startup.";
    public static String AutoWorldTools_startupCompPlugin = "Plugin startup complete.";

    public static String AutoWorldTools_loadMultiverseCore = "Load:Multiverse-Core";
    public static String AutoWorldTools_loadCompMultiverseCore = "Load complete:Multiverse-Core";
    public static String AutoWorldTools_loadFailureMultiverseCore = "Load failure:Multiverse-Core";

    public static String AutoWorldTools_loadMultiversePortals = "Load:Multiverse-Portals";
    public static String AutoWorldTools_loadCompMultiversePortals = "Load complete:Multiverse-Portals";
    public static String AutoWorldTools_loadFailureMultiversePortals = "Load failure:Multiverse-Portals";

    public static String AutoWorldTools_startupScheduler = "Scheduler startup.";


    public static String ResetUtil_resetStart = "Reset start. World:";
    public static String ResetUtil_resetComp = "Reset complete. World:";
    public static String ResetUtil_resetFailure = "Reset failure. Not Found world. World:";
    public static String ResetUtil_resetFailureNotConnectedMultiverseCore = "Reset failure. Not connected Multiverse-Core.";

    public static String BackupUtil_backupStart = "Backup start. World:";
    public static String BackupUtil_startZip = "Compressing world file...  World:";
    public static String BackupUtil_compZip = "Compression complete. World:";
    public static String BackupUtil_backupComp = "Backup complete. World:";
    public static String BackupUtil_backupFailure = "Backup failure. Not Found world. World:";

    public static String CreateWarpGateUtil_RestartMultiversePortals = "Restart Multiverse-Portals.";
    public static String CreateWarpGateUtil_RestartCompMultiversePortals = "Restarted Multiverse-Portals.";
    public static String CreateWarpGateUtil_RestartFailureMultiversePortals = "Restart failure Multiverse-Portals.";
    public static String CreateWarpGateUtil_WorldNameGateNameAmountMismatch = "The amount of world name and portal name do not match. Not generated gate.";
    public static String CreateWarpGateUtil_GateGenerateInfo = "Create a gate on World name:{worldname} Portal name:{portalname}";
    public static String CreateWarpGateUtil_PortalNotFound = "Failed to get portal information　of {portalname}.";
    public static String CreateWarpGateUtil_PortalNotGenerateInfo = "Does not generate World name:{worldname} Portal name:{portalname}";
    public static String CreateWarpGateUtil_GateGenerateStart = "Start gate generation";
    public static String CreateWarpGateUtil_GateGenerateComp = "Complete gate generation";


    private MainConfig mainConfig;

    public ConsoleMessage(MainConfig mainConfig){
        this.mainConfig = mainConfig;
    }

    public void changeLanguageConsoleMessages(){
        if(mainConfig.getConsoleLanguage().equals("ja")){

            AutoWorldTools_startupPlugin = "プラグインを起動します。";
            AutoWorldTools_startupCompPlugin = "プラグインが起動しました。";

            AutoWorldTools_loadMultiverseCore = "Multiverse-Coreの読み込みを行います。";
            AutoWorldTools_loadCompMultiverseCore = "Multiverse-Coreの読み込みが完了しました。";
            AutoWorldTools_loadFailureMultiverseCore = "Multiverse-Coreの読み込みに失敗しました。";

            AutoWorldTools_loadMultiversePortals = "Multiverse-Portalsの読み込みを行います。";
            AutoWorldTools_loadCompMultiversePortals = "Multiverse-Portalsの読み込みが完了しました。";
            AutoWorldTools_loadFailureMultiversePortals = "Multiverse-Portalsの読み込みに失敗しました。";

            AutoWorldTools_startupScheduler = "スケジューラを起動。";

            ResetUtil_resetStart = "リセット開始。 ワールド名:";
            ResetUtil_resetComp = "リセット完了。 ワールド名:";
            ResetUtil_resetFailure = "ワールドが見つからないため、リセットに失敗しました。 ワールド名:";
            ResetUtil_resetFailureNotConnectedMultiverseCore = "Multiverse-Coreの読み込みに失敗したため、リセットに失敗しました。";

            BackupUtil_backupStart = "バックアップを開始します。 ワールド名:";
            BackupUtil_startZip = "ワールドファイルの圧縮中...";
            BackupUtil_compZip = "ワールドファイルの圧縮が完了しました。";
            BackupUtil_backupComp = "バックアップが完了しました。 ワールド名:";
            BackupUtil_backupFailure = "ワールドが見つからないため、バックアップに失敗しました。 ワールド名:";


            CreateWarpGateUtil_RestartMultiversePortals = "Multiverse-Portalsを再起動します。";
            CreateWarpGateUtil_RestartCompMultiversePortals = "Multiverse-Portalsの再起動が完了しました。";
            CreateWarpGateUtil_RestartFailureMultiversePortals = "Multiverse-Portalsの再起動に失敗しました。";
            CreateWarpGateUtil_WorldNameGateNameAmountMismatch = "Configに記載されている、ワールド名とポータル名の数量が一致しないためゲート生成処理を行いません。";
            CreateWarpGateUtil_GateGenerateInfo = "ワールド名:{worldname} ポータル名:{portalname} にゲートを生成します。";
            CreateWarpGateUtil_PortalNotFound = "ポータル名:{portalname} の情報取得に失敗しました。";
            CreateWarpGateUtil_PortalNotGenerateInfo = "ワールド名:{worldname} ポータル名:{portalname} の生成は行いません。";
            CreateWarpGateUtil_GateGenerateStart = "ゲート生成処理を開始します。";
            CreateWarpGateUtil_GateGenerateComp = "ゲート生成処理が完了しました。";

        } else if(mainConfig.getConsoleLanguage().equals("en")){

            AutoWorldTools_startupPlugin = "Plugin startup.";
            AutoWorldTools_startupCompPlugin = "Plugin startup complete.";

            AutoWorldTools_loadMultiverseCore = "Load:Multiverse-Core";
            AutoWorldTools_loadCompMultiverseCore = "Load complete:Multiverse-Core";
            AutoWorldTools_loadFailureMultiverseCore = "Load failure:Multiverse-Core";

            AutoWorldTools_loadMultiversePortals = "Load:Multiverse-Portals";
            AutoWorldTools_loadCompMultiversePortals = "Load complete:Multiverse-Portals";
            AutoWorldTools_loadFailureMultiversePortals = "Load failure:Multiverse-Portals";

            AutoWorldTools_startupScheduler = "Scheduler startup.";

            ResetUtil_resetStart = "Reset start. World:";
            ResetUtil_resetComp = "Reset complete. World:";
            ResetUtil_resetFailure = "Reset failure. Not Found world. World:";
            ResetUtil_resetFailureNotConnectedMultiverseCore = "Reset failure. Not connected Multiverse-Core.";

            BackupUtil_backupStart = "Backup start. World:";
            BackupUtil_startZip = "Compressing world file...  World:";
            BackupUtil_compZip = "Compression complete. World:";
            BackupUtil_backupComp = "Backup complete. World:";
            BackupUtil_backupFailure = "Backup failure. Not Found world. World:";

            CreateWarpGateUtil_RestartMultiversePortals = "Restart Multiverse-Portals.";
            CreateWarpGateUtil_RestartCompMultiversePortals = "Restarted Multiverse-Portals.";
            CreateWarpGateUtil_RestartFailureMultiversePortals = "Restart failure Multiverse-Portals.";
            CreateWarpGateUtil_WorldNameGateNameAmountMismatch = "The amount of world name and portal name do not match. Not generated gate.";
            CreateWarpGateUtil_GateGenerateInfo = "Create a gate on World name:{worldname} Portal name:{portalname}";
            CreateWarpGateUtil_PortalNotFound = "Failed to get portal information　of {portalname}.";
            CreateWarpGateUtil_PortalNotGenerateInfo = "Does not generate World name:{worldname} Portal name:{portalname}";
            CreateWarpGateUtil_GateGenerateStart = "Start gate generation";
            CreateWarpGateUtil_GateGenerateComp = "Complete gate generation";
        }
    }
}
