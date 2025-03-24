:: Name this script start_emulator.bat

:: Set the AVD name
set AVD_NAME=intellier-tab

:: Set the path to the emulator executable
set EMULATOR_PATH=C:\Users\1600000273\AppData\Local\Android\Sdk\emulator\emulator.exe

:: Start the emulator
%EMULATOR_PATH% -avd %AVD_NAME% -no-snapshot-load
