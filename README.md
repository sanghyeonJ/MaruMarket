🛒 MaruMarket (マルマーケット)
  JSP/Servlet ベースの中古取引プラットフォーム・プロジェクト ユーザーが安心して物品を売買できる、信頼性の高いコミュニティ空間です。


🛠 Tech Stack (技術スタック)
  Language: Java 11 (JDK 8+)
  Server: Apache Tomcat 9.0
  Database: Oracle DB (XE)
  Frontend: JSP, HTML5, CSS3, JavaScript, jQuery
  Library & API: OJDBC, JSON, JSTL
  Version Control: Git, SourceTree, GitHub


📋 Key Features (主な機能)
  商品管理: 中古商品の登録、修正、削除、およびステータス管理（販売中/予約中/売却済み）
  画像アップロード: 商品詳細画像およびメインサムネイルの複数アップロード機能
  マイページ:
    登録済み商品リストの照会（無限スクロールおよび「もっと見る」非同期処理）
    お気に入り（いいね）商品のリスト確認および管理
  お気に入り (Like): AJAXを活用したリアルタイムなお気に入り登録・解除機能
  カテゴリ分類: 電子機器、衣類、本など、カテゴリ別の商品フィルタリング


🗄️ Database Schema (DB 構造)
プロジェクト実行のために、以下のスクリプトを順に実行してください。
  ファイルパス: /sql/schema.sql
  主要テーブル:
    member: ユーザー情報および権限管理
    product: 商品情報（タイトル、内容、価格、ステータスなど）
    file_info: アップロードされたファイルのメタデータ
    category: 商品カテゴリ管理
    product_like: ユーザー別のお気に入り登録履歴


📂 Project Structure (ディレクトリ構成)
MaruMarket/
├── src/
│   ├── controller/ (Servlet クラス)
│   ├── model/ (DTO, DAO)
│   ├── service/ (ビジネスロジック)
│   └── util/ (DB 接続および共通ユーティリティ)
├── WebContent/
│   ├── common/ (ヘッダー、フッター共通 JSP)
│   ├── css/ & js/ (静的リソース)
│   └── product/ (商品関連ページ)
└── sql/
    └── schema.sql (DB 初期化スクリプト)


🚀 Getting Started (始め方)
1. このリポジトリをクローン(Clone)します。
2. sql/schema.sql スクリプトを Oracle DB で実行します。
3. src/util/DBUtil.java ファイル内の DB 接続情報(ID, PW)を環境に合わせて修正します。
4. Tomcat サーバーを接続して実行します。
