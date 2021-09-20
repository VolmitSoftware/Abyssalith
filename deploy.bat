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
start /wait gradlew clean shadowJar
echo F|xcopy /y /s /f /q "build/libs/Abyssalith-cloud-all.jar" "staging/Abyssalith.jar"
docker build . -t arcane/abyssalith:b%BUILD%
doctl registry login
docker tag arcane/abyssalith:b%BUILD% registry.digitalocean.com/cargo-dock/arcane/abyssalith:b%BUILD%
docker push registry.digitalocean.com/cargo-dock/arcane/abyssalith:b%BUILD%