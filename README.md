# AutoWorldTools
指定曜日の指定時刻にワールドリセットやバックアップを行うプラグインです。
Multiverse-Portalsと連携してポータルの再設定や構造物の設置も出来ます。

# 機能詳細

* 自動リセット

設定した時刻になると、Configに記載した全ワールドが再生成されます。

* ワープゲート再生成

Multiverse-Portalsを導入している場合、再生性後もワープゲートを有効化するためにMultiverse-Portalsの再起動を行います。
※Multiverse-Portalsを利用している場合はConfigの「useMultiversePortals」をtrueにして下さい。

ポータルにゲート(構造物)を自動生成する場合、ポータルの大きさは 1×1×2に再設定され岩盤・ガラスを用いたゲートがポータルに自動生成されます。

* 自動バックアップ

設定した時刻になると、Configに記載した全ワールドがバックアップされます。古いファイルを自動で削除することも可能です。

# 導入必須プラグイン

* Multiverse-Core

# 任意プラグイン

* Multiverse-Portals
ポータルの再設定や、ワープゲート(構造物)の設置を行えます。

# コマンド

* /awt reset [ワールド種別(normal/nether/end)]

種別ごとにConfigに記載されたワールドを再生成します。種別がallの場合、全種別(ノーマル・ネザー・ジエンド)を再生成します・
* /awt backup [ワールド名]

指定したワールドをバックアップします。
* /awt reset info

自動リセット時刻の情報を表示します。
* /awt backup info

自動バックアップ時刻の情報を表示します。
* /awt reload

Configをリロードします。
* /awt help

コマンドガイドを表示します。

# Permission
詳細は後々記載

* autoWorldTools.admin

手動リセット・バックアップ・Configのリロードを行えます。
* autoWorldTools.reset

手動リセットを行えます。
* autoWorldTools.backup

手動バックアップを行えます。
* autoWorldTools.resetInfo

リセット時刻情報を表示出来ます。
* autoWorldTools.backupInfo

バックアップ時刻情報を表示出来ます。
