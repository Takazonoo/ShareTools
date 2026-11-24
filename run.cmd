@echo off
setlocal
pushd "%~dp0" || exit /b 1
if not exist "build\classes" mkdir "build\classes" || goto falha
if not exist "dist\lib" mkdir "dist\lib" || goto falha

(for %%D in (DAO Model Principal View) do for %%F in (src\%%D\*.java) do echo src/%%D/%%~nxF) > "build\fontes.txt"
javac --release 17 -encoding UTF-8 -d "build\classes" @build/fontes.txt || goto falha
if not exist "build\classes\Imagens" mkdir "build\classes\Imagens" || goto falha
copy /y "src\Imagens\*.png" "build\classes\Imagens\" >nul || goto falha
copy /y "lib\mysql-connector-j-8.4.0.jar" "dist\lib\" >nul || goto falha
jar --create --file "dist\ShareTools.jar" --manifest "manifest.mf" -C "build\classes" . || goto falha

if /i "%~1"=="--build-only" goto sucesso
java -jar "dist\ShareTools.jar" || goto falha

:sucesso
popd
exit /b 0

:falha
echo Falha ao compilar, empacotar ou executar o ShareTools. 1>&2
popd
exit /b 1
