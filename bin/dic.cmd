@echo off
m .diclist %1 %2
IF ERRORLEVEL 1 GOTO EXIT
SET /p ENTRY= <.entry
start "" C:\Users\zluo\AppData\Local\Google\Chrome\Application\chrome.exe http://translate.google.com/#en/zh-CN/%ENTRY%
:EXIT
