#!/usr/bin/env pwsh
<#
.SYNOPSIS
    Script para compilar e executar o Sistema de Gestão da Manutenção
.DESCRIPTION
    Facilita a compilação, limpeza e execução do projeto
.EXAMPLE
    .\build.ps1 compile
    .\build.ps1 run
    .\build.ps1 clean
#>

param(
    [Parameter(Position = 0)]
    [ValidateSet("compile", "run", "clean", "test", "help")]
    [string]$Command = "help"
)

$projectRoot = Get-Location
$targetDir = Join-Path $projectRoot "target"
$classesDir = Join-Path $targetDir "classes"
$srcMainDir = Join-Path $projectRoot "src/main/java"

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Sistema de Gestão da Manutenção" -ForegroundColor Cyan
Write-Host "Escola Naval - POO 2025/2026" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

function Compile {
    Write-Host "Compilando o projeto..." -ForegroundColor Yellow
    
    if (-not (Test-Path $classesDir)) {
        New-Item -ItemType Directory -Path $classesDir -Force | Out-Null
    }
    
    $javaFiles = @(
        (Get-ChildItem -Path "$srcMainDir/pt/escnaval/manutencao/dominio" -Filter "*.java" | ForEach-Object { $_.FullName }),
        (Get-ChildItem -Path "$srcMainDir/pt/escnaval/manutencao/infraestrutura" -Filter "*.java" | ForEach-Object { $_.FullName }),
        (Get-ChildItem -Path "$srcMainDir/pt/escnaval/manutencao/aplicacao" -Filter "*.java" | ForEach-Object { $_.FullName }),
        (Get-ChildItem -Path "$srcMainDir/pt/escnaval/manutencao/ui" -Filter "*.java" | ForEach-Object { $_.FullName }),
        (Get-ChildItem -Path "$srcMainDir/pt/escnaval/manutencao" -Filter "*.java" | ForEach-Object { $_.FullName })
    ) | Where-Object { $_ }
    
    & javac -d $classesDir -sourcepath $srcMainDir @javaFiles
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ Compilação concluída com sucesso!" -ForegroundColor Green
    } else {
        Write-Host "✗ Erro na compilação!" -ForegroundColor Red
        exit 1
    }
}

function Run {
    if (-not (Test-Path $classesDir)) {
        Write-Host "Classes não encontradas. Compilando..." -ForegroundColor Yellow
        Compile
    }
    
    Write-Host "Iniciando o sistema..." -ForegroundColor Yellow
    & java -cp $classesDir pt.escnaval.manutencao.MaintenanceSystemMain
}

function Clean {
    Write-Host "Limpando o projeto..." -ForegroundColor Yellow
    
    if (Test-Path $targetDir) {
        Remove-Item -Path $targetDir -Recurse -Force
        Write-Host "✓ Limpeza concluída!" -ForegroundColor Green
    } else {
        Write-Host "Nada para limpar." -ForegroundColor Gray
    }
}

function Test {
    Write-Host "Compilando testes..." -ForegroundColor Yellow
    Write-Host "Nota: Esta funcionalidade requer mais configuração." -ForegroundColor Yellow
    Write-Host "Use 'mvn test' se Maven estiver instalado." -ForegroundColor Gray
}

function Help {
    Write-Host "Uso: .\build.ps1 [comando]" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Comandos disponíveis:" -ForegroundColor Cyan
    Write-Host "  compile    - Compilar o projeto"
    Write-Host "  run        - Compilar e executar o sistema (default)"
    Write-Host "  clean      - Limpar os ficheiros compilados"
    Write-Host "  test       - Compilar os testes unitários"
    Write-Host "  help       - Mostrar esta ajuda"
    Write-Host ""
    Write-Host "Exemplos:" -ForegroundColor Cyan
    Write-Host "  .\build.ps1 run"
    Write-Host "  .\build.ps1 compile"
    Write-Host "  .\build.ps1 clean"
}

# Executar o comando
switch ($Command) {
    "compile" { Compile }
    "run" { Run }
    "clean" { Clean }
    "test" { Test }
    "help" { Help }
    default { Help }
}

Write-Host ""
