# Lab01

Este laboratório tem como finalidade iniciar o espaço onde iremos trabalhar ao longo do semestre.

## Estrutura do Projeto

```
Lab01/
	src/
		main/
			java/
				escnaval/
					HelloWorld.java
					ArgsEcho.java
		test/
			java/
	docs/
	README.md
```

## Como compilar e executar

### Compilar

```sh
cd Lab01/src/main/java
javac escnaval/HelloWorld.java escnaval/ArgsEcho.java
```

### Executar HelloWorld

```sh
java escnaval.HelloWorld
```

### Executar ArgsEcho

```sh
java escnaval.ArgsEcho argumento1 argumento2
```

## Descrição dos arquivos

- `HelloWorld.java`: Exemplo simples que imprime uma mensagem de boas-vindas.
- `ArgsEcho.java`: Exibe os argumentos recebidos pela linha de comando.

## Exportar para o GitHub

1. Inicializa o repositório:
	 ```sh
	 git init
	 git add .
	 git commit -m "Lab01 pronto para exportar"
	 ```
2. Cria o repositório no GitHub e faz push:
	 ```sh
	 git remote add origin <URL-do-repo>
	 git push -u origin master
	 ```

