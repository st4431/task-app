# GradleとJava17の機能を借りてきて、それをbuilderと名付ける
FROM eclipse-temurin:17-jdk-jammy AS builder

# /appという名前の作業スペースを用意する
WORKDIR /app

# 自分のSpringBootの設計図や設定ファイルをコピーする
# 「あなたのPCになるbuild.gradleとsettings.gradleという2つの設定ファイルを、コンテナ内の/appという場所にコピーしてください。」
# ソースコードに必要なライブラリをダウンロードする
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# gradlewファイルに実行権限を与える ★★★
RUN chmod +x ./gradlew

RUN ./gradlew dependencies

# 次に、アプリケーションのソースコードを全てコピーします。
COPY src ./src

# gradlewファイルに実行権限を与えます。
RUN chmod +x ./gradlew

# ビルドを行う
RUN ./gradlew build --no-daemon

# Java17のアプリケーションを実行するためだけの、最小限の道具（JRE）が入った、小さいコンテナを持ってくる
FROM eclipse-temurin:17-jre-jammy

# /appという名前の作業スペースを用意する
WORKDIR /app

# /appにapp.jarという名前で.jarファイルをコピーする
# コピー元は最初にbuilderと名付けた部分
COPY --from=builder /app/build/libs/*.jar app.jar

# コンテナを起動するときに必ず実行して欲しい命令文を指定する
ENTRYPOINT ["java", "-jar", "app.jar"]