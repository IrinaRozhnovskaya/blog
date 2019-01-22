@echo off
rem Read1: https://github.com/daggerok/streaming-file-server/blob/master/scripts/application.cmd
rem Read2: https://ab57.ru/cmdlist/taskkill.html
FOR /f "tokens=1" %%A IN ('tasklist ^| find /I "node"') DO (taskkill /F /PID %%A)
