# ----------------------------
# Build Stage
# ----------------------------
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . /app

# 의존성 캐싱을 위해 먼저 build.gradle* 만 복사하는 최적화도 가능
# COPY build.gradle.kts settings.gradle.kts ./
# RUN gradle build --no-daemon || return 0
# COPY . .
RUN gradle build --no-daemon

# ----------------------------
# Runtime Stage
# ----------------------------
FROM eclipse-temurin:17-jdk-jammy AS run
WORKDIR /app

# build/libs 디렉토리에 생성된 JAR 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 환경변수 (선택)
ENV JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]