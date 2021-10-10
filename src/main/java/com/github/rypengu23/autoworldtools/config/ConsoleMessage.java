package com.github.rypengu23.autoworldtools.config;

public class ConsoleMessage {

    public static String AutoWorldTools_startupPlugin;
    public static String AutoWorldTools_startupCompPlugin;

    public static String AutoWorldTools_loadMultiverseCore;
    public static String AutoWorldTools_loadCompMultiverseCore;
    public static String AutoWorldTools_loadFailureMultiverseCore;

    public static String AutoWorldTools_loadMultiversePortals;
    public static String AutoWorldTools_loadCompMultiversePortals;
    public static String AutoWorldTools_loadFailureMultiversePortals;

    public static String AutoWorldTools_loadDiscordSRV;
    public static String AutoWorldTools_loadCompDiscordSRV;
    public static String AutoWorldTools_loadFailureDiscordSRV;

    public static String AutoWorldTools_loadDynmap;
    public static String AutoWorldTools_loadCompDynmap;
    public static String AutoWorldTools_loadFailureDynmap;

    public static String AutoWorldTools_startupScheduler;


    public static String ResetUtil_resetStart;
    public static String ResetUtil_resetComp;
    public static String ResetUtil_resetFailure;
    public static String ResetUtil_resetFailureNotConnectedMultiverseCore;

    public static String BackupUtil_backupStart;
    public static String BackupUtil_startZip;
    public static String BackupUtil_compZip;
    public static String BackupUtil_backupComp;
    public static String BackupUtil_backupFailure;

    public static String MultiversePortalsUtil_RestartMultiversePortals;
    public static String MultiversePortalsUtil_RestartCompMultiversePortals;
    public static String MultiversePortalsUtil_RestartFailureMultiversePortals;
    public static String MultiversePortalsUtil_WorldNameGateNameAmountMismatch;
    public static String MultiversePortalsUtil_GateGenerateInfo;
    public static String MultiversePortalsUtil_PortalNotFound;
    public static String MultiversePortalsUtil_PortalNotGenerateInfo;
    public static String MultiversePortalsUtil_GateGenerateStart;
    public static String MultiversePortalsUtil_GateGenerateComp;

    public static String DynmapUtil_PurgeMapComplete;
    public static String DynmapUtil_PurgeMapFailure;

    public static String RestartUtil_RestartStart;

    public static String ConfigUpdater_CheckUpdateConfig;
    public static String ConfigUpdater_UpdateConfig;
    public static String ConfigUpdater_NoConfigUpdates;

    public static String DiscordUtil_FailureSendMessage;


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

            AutoWorldTools_loadDiscordSRV = "DiscordSRVの読み込みを行います。";
            AutoWorldTools_loadCompDiscordSRV = "DiscordSRVの読み込みが完了しました。";
            AutoWorldTools_loadFailureDiscordSRV = "DiscordSRVの読み込みに失敗しました。";

            AutoWorldTools_loadDynmap = "Dynmapの読み込みを行います。";
            AutoWorldTools_loadCompDynmap = "Dynmapの読み込みが完了しました。";
            AutoWorldTools_loadFailureDynmap = "Dynmapの読み込みに失敗しました。";

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


            MultiversePortalsUtil_RestartMultiversePortals = "Multiverse-Portalsを再起動します。";
            MultiversePortalsUtil_RestartCompMultiversePortals = "Multiverse-Portalsの再起動が完了しました。";
            MultiversePortalsUtil_RestartFailureMultiversePortals = "Multiverse-Portalsの再起動に失敗しました。";
            MultiversePortalsUtil_WorldNameGateNameAmountMismatch = "Configに記載されている、ワールド名とポータル名の数量が一致しないためゲート生成処理を行いません。";
            MultiversePortalsUtil_GateGenerateInfo = "ワールド名:{worldname} ポータル名:{portalname} にゲートを生成します。";
            MultiversePortalsUtil_PortalNotFound = "ポータル名:{portalname} の情報取得に失敗しました。";
            MultiversePortalsUtil_PortalNotGenerateInfo = "ワールド名:{worldname} ポータル名:{portalname} の生成は行いません。";
            MultiversePortalsUtil_GateGenerateStart = "ゲート生成処理を開始します。";
            MultiversePortalsUtil_GateGenerateComp = "ゲート生成処理が完了しました。";

            DynmapUtil_PurgeMapComplete = "ワールド名:{worldname}のマップを削除しました。";
            DynmapUtil_PurgeMapFailure = "ワールド名:{worldname}のマップを削除に失敗しました。";

            RestartUtil_RestartStart = "サーバーを再起動します。";

            ConfigUpdater_CheckUpdateConfig = "Configの更新確認を行います。";
            ConfigUpdater_UpdateConfig = "古いバージョンのConfigです。アップデートを行います。";
            ConfigUpdater_NoConfigUpdates = "Configは最新バージョンです。";

            DiscordUtil_FailureSendMessage = "DiscordSRVへのメッセージ送信に失敗しました。";

        } else if(mainConfig.getConsoleLanguage().equals("en")){

            AutoWorldTools_startupPlugin = "Plugin startup.";
            AutoWorldTools_startupCompPlugin = "Plugin startup complete.";

            AutoWorldTools_loadMultiverseCore = "Load:Multiverse-Core";
            AutoWorldTools_loadCompMultiverseCore = "Load complete:Multiverse-Core";
            AutoWorldTools_loadFailureMultiverseCore = "Load failure:Multiverse-Core";

            AutoWorldTools_loadMultiversePortals = "Load:Multiverse-Portals";
            AutoWorldTools_loadCompMultiversePortals = "Load complete:Multiverse-Portals";
            AutoWorldTools_loadFailureMultiversePortals = "Load failure:Multiverse-Portals";

            AutoWorldTools_loadDiscordSRV = "Load:DiscordSRV";
            AutoWorldTools_loadCompDiscordSRV = "Load complete:DiscordSRV";
            AutoWorldTools_loadFailureDiscordSRV = "Load failure:DiscordSRV";

            AutoWorldTools_loadDynmap = "Load:Dynmap";
            AutoWorldTools_loadCompDynmap = "Load complete:Dynmap";
            AutoWorldTools_loadFailureDynmap = "Load failure:Dynmap";

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

            MultiversePortalsUtil_RestartMultiversePortals = "Restart Multiverse-Portals.";
            MultiversePortalsUtil_RestartCompMultiversePortals = "Restarted Multiverse-Portals.";
            MultiversePortalsUtil_RestartFailureMultiversePortals = "Restart failure Multiverse-Portals.";
            MultiversePortalsUtil_WorldNameGateNameAmountMismatch = "The amount of world name and portal name do not match. Not generated gate.";
            MultiversePortalsUtil_GateGenerateInfo = "Create a gate on World name:{worldname} Portal name:{portalname}";
            MultiversePortalsUtil_PortalNotFound = "Failed to get portal information　of {portalname}.";
            MultiversePortalsUtil_PortalNotGenerateInfo = "Does not generate World name:{worldname} Portal name:{portalname}";
            MultiversePortalsUtil_GateGenerateStart = "Start gate generation";
            MultiversePortalsUtil_GateGenerateComp = "Complete gate generation";

            DynmapUtil_PurgeMapComplete = "Map deletion successful:{worldname}.";
            DynmapUtil_PurgeMapFailure = "Map deletion failure:{worldname}.";


            RestartUtil_RestartStart = "Restart the server...";

            ConfigUpdater_CheckUpdateConfig = "Check for Config updates.";
            ConfigUpdater_UpdateConfig = "Update Config.";
            ConfigUpdater_NoConfigUpdates = "No Config updates.";

            DiscordUtil_FailureSendMessage = "Failed to send a message to DiscordSRV.";
        }
    }
}
