# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn -pl service-timeauthority/service-timeauthority-impl -am clean package -DskipTests

# ---- Run Stage ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/service-timeauthority/service-timeauthority-impl/target/service-timeauthority-impl-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 9081
ENTRYPOINT ["java","-jar","/app/app.jar"]
