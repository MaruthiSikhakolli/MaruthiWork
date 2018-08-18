d:
cd %~dp0/Sikuli SCript/
set CLASSPATH=%~dp0Sikuli Lib/*;
set PATH=%PATH%;%~dp0Sikuli Lib\libs32;%~dp0Sikuli Lib\libs64;
javac Sikuli.java
java Sikuli %1 %2
#pause
exit