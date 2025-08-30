# Java17がインストール済みのOSをベースにするよ
FROM eclipse-temurin:17-jdk AS builder

# 作業するディレクトリを/appに指定する（なければこの時点で自動的に作成される）
# cdとmkdirを同時に行ったみたいな感じ
WORKDIR /app

# ./（今いる場所）に以下のファイルをコピーする
# コピーを段階に分けて行うことで処理を早くすることができる
# だから一度にコピーしない
COPY build.gradle settings.gradle gradle ./
COPY gradle ./gradle

# gradleに権限（execute）を与えてください（change mode）
RUN chmod +x .gradle
RUN ./gradle dependencies

# srcディレクトリにsrcをコピーする（なければ自動的に作成される)
COPY src ./src

# ./でgradleを実行する
RUN ./gradle build --no-daemon

# Javaの実行をするための軽量なOSをベースにするよ
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# builderでビルドしたjarファイルだけをコピーする
# アプリケーションに必要なjava、htmlファイルなどをまとめたzipファイルのこと
COPY --from=builder /app/build/libs/*.jar app.jar

# Java実行環境に対して、このapp.jarを使用してアプリを起動してください
ENTRYPOINT ["java", "-jar", "app.jar"]


