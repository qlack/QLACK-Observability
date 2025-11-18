@echo off
REM Development start script for service-timeauthority (Windows)
REM Navigates to the implementation module and starts the Spring Boot app.

setlocal ENABLEDELAYEDEXPANSION
cd /d "%~dp0service-timeauthority-impl" || (
  echo Failed to change directory to service-timeauthority-impl & exit /b 1
  exit /b 1
)

mvn spring-boot:run

endlocal
