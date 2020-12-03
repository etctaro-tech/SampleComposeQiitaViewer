# Jetpack Composeサンプル：Qiita Viewer
[Qiitaの記事「Jetpack Composeがalpha版になったので改めてQiitaビューアを作ってみた」](https://qiita.com/etctaro/items/a17681019c747f5e8b12)で作成したサンプルコードです。

## 概要
- QiitaAPIに通信を行い、その結果を画面にリスト表示するアプリです。
- Jetpack Compose(1.0.0-alpha07)を使って実装しました。
- 通信とシリアライズにはKotlinのKtor、kotlinx.serializationを使っています。
- UIテストでは、アプリを起動しFABをタップして画面の表示を確認するサンプルコードとなっています。

## 使い方(端末上での実行)
- 1.Androidエミュレータまたは端末上でアプリを起動します。
- 2.FABをタップします。
- 3.記事の一覧が取得され、画面に記事のタイトルとURLが表示されます。
- 4.各記事をタップするとブラウザ上でその記事が表示されます。

## 制約事項と注意点
- Android Studio 4.2(Canary16)にて実装、動作確認
- Android 11のエミュレータにて動作確認をしました。
- インターネットに接続されていることが前提です。
- QiitaAPIについては最新20件の記事を取得するのみです。無限スクロールなどの対応はしていません。
- エラー処理はしていません。
- ユニットテストのカバレッジは考慮していません。

## Android Studioのバージョン更新について
記事を執筆している時に、Android Studio 4.2 Beta1とArctic Fox Canary 1 (2020.3.1.1)がリリースされました。
それらのバージョンに対する動作保証は致しかねますが、それらで動かす場合は以下を留意してください。

なお、以下で記したバージョンの更新は必要に応じてAndroid Studioで更新を促すダイアログが表示されますので、それに従うとよいです。

### 4.2 Beta1
- AGPのバージョンを4.2.0-beta01に更新。rootのbuild.gradleの設定を以下の通り変更してください。
```groovy
    dependencies {
        classpath 'com.android.tools.build:gradle:4.2.0-beta01'
```
- 4.2 Beta1ではJetpack Composeの実装は行えません。(現時点ではCanaryのみの機能であるため)
    - ※ビルドは可能です。

### Arctic Fox Canary 1
- AGPのバージョンを7.0.0-alpha01に更新。rootのbuild.gradleの設定を以下の通り変更してください。

```groovy
    dependencies {
        classpath 'com.android.tools.build:gradle:7.0.0-alpha01'
```

- Gradleバージョンを6.7.1に更新。gradle/gradle-wrapper.propertiesの設定を以下の通り変更してください。

```
distributionUrl=https://services.gradle.org/distributions/gradle-6.7.1-bin.zip
```

- AGP 7.0.0-alpha01はJDK11以降でのみ動きますので、必要に応じてProject StructureからJDKの設定を変更してください。
