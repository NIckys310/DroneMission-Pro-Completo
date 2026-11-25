@echo off
REM Inicia el backend de DroneMission Pro conectado al MongoDB LOCAL (localhost:27017).
REM Antes ejecuta ..\mongodb-local\iniciar-mongodb.bat (o deja abierta su ventana).
set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.10"
set "SPRING_PROFILES_ACTIVE=mongo"
set "MONGODB_URI=mongodb://localhost:27017/dronemission"
cd /d "%~dp0backend"
call mvnw.cmd spring-boot:run
