# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn -pl service-todo -am clean package -DskipTests

# Download the agent deterministically (fail if download fails)
RUN apt-get update && apt-get install -y --no-install-recommends curl ca-certificates \
  && curl -fL -o /app/opentelemetry-javaagent.jar \
     https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.32.0/opentelemetry-javaagent.jar \
  && test -s /app/opentelemetry-javaagent.jar

# ---- Run Stage ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/opentelemetry-javaagent.jar /app/opentelemetry-javaagent.jar
COPY --from=build /app/service-todo/target/service-todo-1.0.0-SNAPSHOT.jar /app/app.jar

RUN chown 10001:10001 /app/opentelemetry-javaagent.jar /app/app.jar \
 && chmod 644 /app/opentelemetry-javaagent.jar /app/app.jar

USER 10001:10001

EXPOSE 9080
ENTRYPOINT ["java","-javaagent:/app/opentelemetry-javaagent.jar","-jar","/app/app.jar"]
