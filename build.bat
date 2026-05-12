@echo off
REM RestAssured Project CI/CD Build Script
REM This script runs Maven tests and generates reports

setlocal enabledelayedexpansion

echo ========================================
echo RestAssured Project Build Started
echo ========================================
echo.

REM Step 1: Clean and build project
echo [INFO] Running Maven clean test...
call mvn clean test

if %errorlevel% neq 0 (
    echo [ERROR] Maven build failed with exit code %errorlevel%
    exit /b %errorlevel%
)

echo.
echo ========================================
echo Build completed successfully!
echo ========================================
echo Test results are available at: target\surefire-reports\
echo.

exit /b 0

