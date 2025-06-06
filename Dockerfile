# ----------------------------
# Build Stage
# ----------------------------
FROM gradle:8.11.1-jdk17 AS build
WORKDIR /app

# 의존성 캐싱을 위해 먼저 build.gradle* 만 복사하는 최적화도 가능
COPY build.gradle.kts settings.gradle.kts ./
RUN gradle build --no-daemon || true
COPY . .
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
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]