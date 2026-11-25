@echo off
REM Inicia el servidor MongoDB local de DroneMission Pro en localhost:27017.
REM Los datos se guardan en la carpeta "data" (no se borran al apagar).
cd /d "%~dp0"
start "MongoDB local (no cerrar)" bin\mongod.exe --dbpath data --port 27017 --bind_ip 127.0.0.1 --logpath log\mongod.log --logappend
echo MongoDB local iniciado en mongodb://localhost:27017
