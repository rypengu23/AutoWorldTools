# AutoWorldTools
指定曜日の指定時刻にワールドリセットやバックアップを行うプラグインです。
Multiverse-Portalsと連携してポータルの再設定や構造物の設置も出来ます。
It is a plug-in that performs world reset and backup at the specified time on the specified day of the week.
You can also reconfigure portals and install structures in cooperation with Multiverse-Portals.

# 機能詳細 | Features

* 自動リセット | Auto Reset

設定した時刻になると、Configに記載した全ワールドが再生成されます。
At the set time, all the worlds listed in Config will be regenerated.

* ワープゲート再生成 | Warp gate regeneration

Multiverse-Portalsを導入している場合、再生性後もワープゲートを有効化するためにMultiverse-Portalsの再起動を行います。
※Multiverse-Portalsを利用している場合はConfigの「useMultiversePortals」をtrueにして下さい。
If you have Multiverse-Portals installed, restart Multiverse-Portals to enable WarpGate even after reproducibility.
* If you are using Multiverse-Portals, set "useMultiversePortals" in Config to true.

ポータルにゲート(構造物)を自動生成する場合、ポータルの大きさは 1×1×2に再設定され岩盤・ガラスを用いたゲートがポータルに自動生成されます。
When automatically generating a gate (structure) on the portal, the size of the portal is reset to 1x1x2, and a gate using rock and glass is automatically generated on the portal.

* 自動バックアップ | Auto backup

設定した時刻になると、Configに記載した全ワールドがバックアップされます。古いファイルを自動で削除することも可能です。
At the set time, all the worlds listed in Config will be backed up. It is also possible to delete old files automatically.

# 導入必須プラグイン | Required plugin

* Multiverse-Core

# 任意プラグイン | Optional plugin

* Multiverse-Portals
ポータルの再設定や、ワープゲート(構造物)の設置を行えます。
You can reconfigure the portal and generate warp gates (structures).

# コマンド

* /awt reset [world type(normal/nether/end)]

種別ごとにConfigに記載されたワールドを再生成します。種別がallの場合、全種別(ノーマル・ネザー・ジエンド)を再生成します。Regenerate the world described in Config for each type. If the type is all, all types (normal, nether, and the end) will be regenerated.
* /awt backup [world name]

指定したワールドをバックアップします。
Backs up the specified world.
* /awt reset info

自動リセット時刻の情報を表示します。
Displays information on the automatic reset time.
* /awt backup info

自動バックアップ時刻の情報を表示します。
Displays information on the automatic backup time.
* /awt reload

Configをリロードします。
Reload Config.
* /awt help

コマンドガイドを表示します。
Display the command guide.

# Permission

* autoWorldTools.admin

手動リセット・バックアップ・Configのリロードを行えます。
You can perform manual reset, backup, and reload of Config.
* autoWorldTools.reset

手動リセットを行えます。
You can perform a manual reset.
* autoWorldTools.backup

手動バックアップを行えます。
You can perform a manual backup.
* autoWorldTools.resetInfo

リセット時刻情報を表示出来ます。
The reset time information can be displayed.
* autoWorldTools.backupInfo

バックアップ時刻情報を表示出来ます。
You can display the backup time information.
