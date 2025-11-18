@echo off
REM Development start script for service-todo (Windows)
REM Starts the Spring Boot app with OpenTelemetry Java Agent instrumentation.

setlocal ENABLEDELAYEDEXPANSION
cd /d "%~dp0" || (
  echo Failed to change to script directory & exit /b 1
  exit /b 1
)

REM Define JVM arguments for OpenTelemetry instrumentation.
set JVM_ARGS=-javaagent:bin/opentelemetry-javaagent.jar ^
 -Dotel.service.name=service-todo ^
 -Dotel.traces.exporter=otlp ^
 -Dotel.metrics.exporter=otlp ^
 -Dotel.logs.exporter=otlp ^
 -Dotel.exporter.otlp.endpoint=http://localhost:4318 ^
 -Dotel.resource.attributes.process.command_args= ^
 -Dotel.instrumentation.http.client.exclude-urls=\"http://localhost:4318/v1/metrics\"

REM Run the application with the defined JVM arguments.
mvn spring-boot:run -Dspring-boot.run.jvmArguments="%JVM_ARGS%"

endlocal
