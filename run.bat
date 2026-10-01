@echo off
REM Use Android Studio bundled JDK (detected on this PC)
set JAVA="C:\Program Files\Android\Android Studio\jbr\bin\java.exe"
echo Starting Hospital Management System...
%JAVA% -cp "out;lib\mysql-connector-j.jar" hospital.Main
pause
