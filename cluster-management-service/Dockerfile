# Multi-stage build
FROM openjdk:21-slim AS builder

WORKDIR /app
COPY . .

# Gradle 실행 권한 부여
RUN chmod +x ./gradlew

# 애플리케이션 빌드
RUN ./gradlew build -x test

# 실행 이미지
FROM openjdk:21-slim

WORKDIR /app

ADD https://dtdg.co/latest-java-tracer /app/dd-java-agent.jar

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8081

# 애플리케이션 실행
ENTRYPOINT ["java", "-javaagent:/app/dd-java-agent.jar", "-jar", "app.jar"]