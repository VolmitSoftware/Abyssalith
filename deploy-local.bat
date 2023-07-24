@echo off
set BUILD=0

(
set /p BUILD=
)<build.number

set /A BUILD=%BUILD%+1

echo Build #%BUILD%

(
echo %build%
)>build.number
call gradlew clean shadowJar --info
echo F|xcopy /y /s /f /q "build/libs/Abyssalith-cloud-all.jar" "staging/Abyssalith.jar"
doctl registry login
docker build . -t registry.digitalocean.com/cargo-dock/arcane/abyssalith:b%BUILD%