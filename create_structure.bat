@echo off
set /p projectName="Digite o nome do projeto: "

echo Criando estrutura de diretórios para %projectName%...

mkdir %projectName%
mkdir %projectName%\.vscode
mkdir %projectName%\src
mkdir %projectName%\src\main
mkdir %projectName%\src\main\java
mkdir %projectName%\src\main\java\%projectName%
mkdir %projectName%\src\main\resources
mkdir %projectName%\src\test
mkdir %projectName%\src\test\java
mkdir %projectName%\out
mkdir %projectName%\docs

echo. > %projectName%\README.md
echo. > %projectName%\.gitignore
echo. > %projectName%\.vscode\launch.json
echo. > %projectName%\.vscode\tasks.json
echo. > %projectName%\src\main\java\%projectName%\HelloWorld.java

echo Estrutura criada com sucesso!
pause