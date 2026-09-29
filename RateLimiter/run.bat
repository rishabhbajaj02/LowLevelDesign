@echo off
setlocal EnableExtensions EnableDelayedExpansion

cd /d "%~dp0"

if not exist out mkdir out

set "SOURCES="
for /r %%F in (*.java) do (
    set "SOURCES=!SOURCES! "%%~fF""
)

echo Compiling Java sources...
javac -d out %SOURCES%
if errorlevel 1 (
    echo Compilation failed.
    exit /b 1
)

if /i "%~1"=="test" (
    echo Running tests...
    java -cp out ratelimiter.RateLimiterTest
    exit /b %errorlevel%
)

echo Starting RateLimiter...
java -cp out Main
