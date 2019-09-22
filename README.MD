# はじめに

こちらのソースコードは「技術書典 7」で頒布した「Ktor による REST API の開発」に出てくるソースコードになります。本がなくても内容は理解できるかと思いますが、本があるとより一層ソースを理解できると思います笑

## とりあえずざっくりソースを読みたい人へ

まずは Application.kt, controller, route, repository あたりのソースを読めば Ktor + Koin + Exposed を網羅できると思いますので、よくわからなかったら twitter などでお問い合わせください。それか、技術書典 7 で頒布した本を買っていただけると解説が載っています笑

## ディレクトリ構成

```
└── src
    ├── main
    │   ├── kotlin
    │   │   └── example
    │   │       ├── common
    │   │       ├── controller
    │   │       ├── domain
    │   │       ├── exception
    │   │       ├── repository
    │   │       │   └── impl
    │   │       ├── request
    │   │       ├── response
    │   │       ├── route
    │   │       ├── service
    │   │       └── table
    │   └── resources
    │       └── sql
    └── test
```

レイヤードアーキテクチャに domain と repository がある感じです。  
レイヤの定義は terasoluna のドキュメントに詳細が記載されておりますので、以下をご参照ください。  
https://terasolunaorg.github.io/guideline/public_review/Overview/ApplicationLayering.html#id1

## Ktor

アプリケーションサーバ全般の機能を提供するライブラリです。  
Ktor server が対象となります。  
https://ktor.io/

## Koin

DI を行うライブラリです。  
Ktor と組み合わせて使用します。  
https://github.com/InsertKoinIO/koin

## Exposed

Kotlin 製の O/R Mapper です。  
https://github.com/JetBrains/Exposed

## Ktor の追加予定ソースコード

Ktor 自体機能が豊富なので他にも色々ありますが、このソースコードに以下は含まれません。
が、追加される予定です。

- session
- cookie
- 認証 (JWT)
- coroutines
- その他諸々

## Exposed の追加予定ソースコード

- DAO スタイルによる記述
- count, group by, left join, などの select
- java.time の LocalDate 使用方法(Exposed のバージョンアップ待ち)
- その他

## Koin の追加予定ソースコード

- 解説していない factory などの機能
- その他

特に Ktor は機能が豊富なので、Ktor Server の機能を重点的に追加していきたいと思っています。

## その他追加予定なもの

- JUnit5 の parameterized test
- MockK を使用した Ktor のテスト
- クラウドサービスへのデプロイ(GAE など)

GAE (Google App Engine) がお手軽に試すにはちょうどいいと思うので、gradle の gae plugin を使用して deploy する方法などを追記する予定です。
