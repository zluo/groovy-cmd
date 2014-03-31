@echo off
m .stocklist %1 %2
IF ERRORLEVEL 1 GOTO EXIT
SET /p ENTRY= <.entry
start "" C:\Users\zluo\AppData\Local\Google\Chrome\Application\chrome.exe http://finance.yahoo.com/q?s=%ENTRY%
:EXIT

