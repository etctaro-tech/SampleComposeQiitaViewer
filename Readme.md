# Jetpack Composeサンプル：Qiita Viewer
[Qiitaの記事「Jetpack Composeがalpha版になったので改めてQiitaビューアを作ってみた」](https://qiita.com/etctaro/items/a17681019c747f5e8b12)で作成したサンプルコードです。

## 概要
- QiitaAPIに通信を行い、その結果を画面にリスト表示するアプリです。
- Jetpack Compose(1.0.0-beta06)を使って実装しました。
- 通信とデシリアライズにはKotlinのKtor、kotlinx.serializationを使っています。
- UIテストでは、アプリを起動しFABをタップして画面の表示を確認するサンプルコードとなっています。

## 使い方(端末上での実行)
- 1.Androidエミュレータまたは端末上でアプリを起動します。
- 2.FABをタップします。
- 3.記事の一覧が取得され、画面に記事のタイトルとURLが表示されます。
- 4.各記事をタップするとブラウザ上でその記事が表示されます。

## 制約事項と注意点
- Android Studio 2020.3.1 Arctic Fox(Canary15)にて実装、動作確認
- Android 11のエミュレータにて動作確認をしました。
- インターネットに接続されていることが前提です。
- QiitaAPIについては最新20件の記事を取得するのみです。無限スクロールなどの対応はしていません。
- エラー処理はしていません。
- ユニットテストのカバレッジは考慮していません。

## 旧版のリリースについて
以前のリリースについては[Releases](https://github.com/etctaro-tech/SampleComposeQiitaViewer/releases)または以下を参照してください。

- [v0.1-compose-alpha07](https://github.com/etctaro-tech/SampleComposeQiitaViewer/releases/tag/v0.1-compose-alpha07)
    - Jetpack Compose 1.0.0-alpha07への対応版。Android Studioは4.2にて動作確認を実施。