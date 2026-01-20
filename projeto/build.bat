@echo off
REM Script para compilar e executar o Sistema de Gestão da Manutenção
REM Escola Naval - POO 2025/2026

setlocal enabledelayedexpansion

echo.
echo =========================================
echo Sistema de Gestão da Manutenção
echo Escola Naval - POO 2025/2026
echo =========================================
echo.

if "%1"=="clean" (
    echo Limpando o projeto...
    if exist target rmdir /s /q target
    echo Limpeza concluída!
    goto end
)

if "%1"=="compile" (
    echo Compilando o projeto...
    mkdir target\classes 2>nul
    javac -d target/classes -sourcepath src/main/java ^
        src/main/java/pt/escnaval/manutencao/dominio/*.java ^
        src/main/java/pt/escnaval/manutencao/infraestrutura/*.java ^
        src/main/java/pt/escnaval/manutencao/aplicacao/*.java ^
        src/main/java/pt/escnaval/manutencao/ui/*.java ^
        src/main/java/pt/escnaval/manutencao/*.java
    if errorlevel 1 (
        echo Erro na compilação!
        goto end
    )
    echo Compilação concluída com sucesso!
    goto end
)

if "%1"=="run" (
    if not exist target\classes (
        echo Compilando primeiro...
        mkdir target\classes 2>nul
        javac -d target/classes -sourcepath src/main/java ^
            src/main/java/pt/escnaval/manutencao/dominio/*.java ^
            src/main/java/pt/escnaval/manutencao/infraestrutura/*.java ^
            src/main/java/pt/escnaval/manutencao/aplicacao/*.java ^
            src/main/java/pt/escnaval/manutencao/ui/*.java ^
            src/main/java/pt/escnaval/manutencao/*.java
        if errorlevel 1 (
            echo Erro na compilação!
            goto end
        )
    )
    echo Iniciando o sistema...
    java -cp target/classes pt.escnaval.manutencao.MaintenanceSystemMain
    goto end
)

if "%1"=="test" (
    echo Compilando testes...
    mkdir target\test-classes 2>nul
    javac -d target/test-classes -cp target/classes;lib/* ^
        -sourcepath src/test/java ^
        src/test/java/pt/escnaval/manutencao/*.java
    echo Testes compilados!
    goto end
)

REM Mostrar ajuda se nenhum argumento for fornecido
echo Uso: %0 [comando]
echo.
echo Comandos disponíveis:
echo   compile    - Compilar o projeto
echo   run        - Compilar e executar o sistema
echo   clean      - Limpar os ficheiros compilados
echo   test       - Compilar os testes unitários
echo.
echo Exemplo: %0 run

:end
endlocal
