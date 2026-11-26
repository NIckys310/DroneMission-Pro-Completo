@echo off
title Abrir los diagramas en Visual Paradigm (una pestana cada uno)
:espera
tasklist /FI "IMAGENAME eq Visual Paradigm.exe" | find /I "Visual Paradigm.exe" >nul
if not errorlevel 1 (
  echo Visual Paradigm esta abierto. Guarda (Ctrl+S) y cierralo, luego presiona una tecla...
  pause >nul
  goto espera
)
copy /Y "%~dp0pestanas-vp.xml" "%APPDATA%\VisualParadigm\ws\.vpprefdata\projectsViewInfo\V7z6qEWGAqACBgBZ" >nul
start "" "C:\Program Files\Visual Paradigm CE 18.1\bin\Visual Paradigm.exe" "%~dp0DroneMission-Pro-Vistas.vpp"
