@echo off
echo ============================================
echo   Hospital Management System - BUILD
echo ============================================

REM Use Android Studio bundled JDK (detected on this PC)
set JAVAC="C:\Program Files\Android\Android Studio\jbr\bin\javac.exe"
set LIB=lib\mysql-connector-j.jar
set SRC=src
set OUT=out

if not exist %LIB% (
    echo.
    echo ERROR: MySQL JDBC driver not found at: %LIB%
    echo.
    echo Please:
    echo  1. Download from: https://dev.mysql.com/downloads/connector/j/
    echo  2. Extract the .zip
    echo  3. Rename the .jar file to: mysql-connector-j.jar
    echo  4. Copy it into the lib\ folder of this project
    echo.
    pause
    exit /b 1
)

if not exist %OUT% mkdir %OUT%

echo Compiling Java source files...
%JAVAC% -cp "%LIB%" -d %OUT% -sourcepath %SRC% ^
  src\hospital\Main.java ^
  src\hospital\db\DBConnection.java ^
  src\hospital\models\Patient.java ^
  src\hospital\models\Doctor.java ^
  src\hospital\models\Nurse.java ^
  src\hospital\models\Employee.java ^
  src\hospital\models\Room.java ^
  src\hospital\models\Appointment.java ^
  src\hospital\models\Medicine.java ^
  src\hospital\models\MedicalRecord.java ^
  src\hospital\models\Bill.java ^
  src\hospital\dao\PatientDAO.java ^
  src\hospital\dao\DoctorDAO.java ^
  src\hospital\dao\NurseDAO.java ^
  src\hospital\dao\EmployeeDAO.java ^
  src\hospital\dao\RoomDAO.java ^
  src\hospital\dao\AppointmentDAO.java ^
  src\hospital\dao\MedicineDAO.java ^
  src\hospital\dao\MedicalRecordDAO.java ^
  src\hospital\dao\BillingDAO.java ^
  src\hospital\gui\LoginFrame.java ^
  src\hospital\gui\MainFrame.java ^
  src\hospital\gui\PatientPanel.java ^
  src\hospital\gui\DoctorPanel.java ^
  src\hospital\gui\NursePanel.java ^
  src\hospital\gui\EmployeePanel.java ^
  src\hospital\gui\RoomPanel.java ^
  src\hospital\gui\AppointmentPanel.java ^
  src\hospital\gui\MedicinePanel.java ^
  src\hospital\gui\MedicalRecordPanel.java ^
  src\hospital\gui\BillingPanel.java

if %ERRORLEVEL%==0 (
    echo.
    echo BUILD SUCCESSFUL!
    echo Run run.bat to start the application.
) else (
    echo.
    echo BUILD FAILED. Check errors above.
)
pause
