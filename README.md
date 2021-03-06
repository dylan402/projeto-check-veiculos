# CheckVeículos

# Pré-requisitos: 

No ambiente de teste foram utilizadas as versões dos softwares:

- Eclipse IDE for Java Developers (includes Incubating components) <br>
Version: 2021-03 (4.19.0) <br>
Build id: 20210312-0638 <br>

-   Java <br>
Plataforma: 1.8 <br>
Produto: 1.8.0_291 <br>
Arquitetura: x86 <br>
<br>

# Importação de arquivos

Realize o download do projeto e abertura no eclipse.

# Preparando ambiente

Para perfeito funcionamento do **Dynamo DB** é necessário configuração da **região us-east-2    Leste dos EUA (Ohio) na Amazon**.
<br>

## Criação de tabelas no Dynamo DB

Após efetuar login na plataforma Amazon, acessar o Dashboard do Dynamo DB.

Deve-se realizar a **criação de duas tabelas**. No Dashboard acesse a aba tabelas do lado esquerdo.

Através do botão criar tabela e insira:

### **Tabela de Clientes**
- Nome da tabela: **cliente**
- Chave primária: **id**
- Definir a chave primária como **tipo String**
- Para as demais opções, manter a configuração padrão
- Clicar sobre o botão Criar para concluir

### **Tabela de Veículos**
- Nome da tabela: **veiculo**
- Chave primária: **id**
- Definir a chave primária como **tipo String**
- Para as demais opções, manter a configuração padrão
- Clicar sobre o botão Criar para concluir

## Criar o arquivo de acesso ao Dynamo DB da Amazon.
<br>

O mesmo deve conter o conteúdo abaixo substituindo **ACCESS_KEY** e **SECRET_KEY** pelas credenciais de acesso do Dynamo DB:

amazon.aws.accesskey=ACCESS_KEY <br>
amazon.aws.secretkey=SECRET_KEY

O arquivo deverá ser nomeado como: **application.properties**

Uma cópia do arquivo deverá ser armazenada nos **diretórios**:

- projeto-check-veiculos\backend-cliente\src\main\resources
- projeto-check-veiculos\backend-cliente\src\test\resources
- projeto-check-veiculos\backend-veiculo\src\main\resources
- projeto-check-veiculos\backend-veiculo\src\test\resources
<br><br>


## Criando builds do **Maven**:
<br>

**Duas** configurações de builds precisam ser criadas para gerar os binários dos dois serviços.

No **IDE Eclipse** siga os passos abaixo: <br><br>


### **Compilando serviço cliente:**
1. Acesse a aba **Run**
2. Selecione opção **Run Configurations**
3. Na opção **Maven Build** clique com **botão direito** para adicionar uma nova configuração selecionando **New Configuration**
   1. Alterar a propriedade name para **backend_cliente**
   2. Clique sobre o **botão Workspace...** disponível na aba **Main**, abaixo da opção **Base Directory**
   3. Selecionar a pasta do **backend-cliente** e clicar no botão **ok**
4. Na opção **Goals**, inserir o comando **clean install**
5. Clique em **Run** para que seja criado o binário do **backend_cliente**
6. Ao **termino** da execução será exibida a mensagem **BUILD SUCCESS** presseguida do tempo total e data de finalização caso as instruções tenham sido seguidas
<br><br>


### **Compilando serviço veículo:**
1. Acesse a aba **Run**
2. Selecione opção **Run Configurations**
3. Na opção **Maven Build** clique com **botão direito** para adicionar uma nova configuração selecionando **New Configuration**
   1. Alterar a propriedade name para **backend_veiculo**
   2. Clique sobre o **botão Workspace...** disponível na aba **Main**, abaixo da opção **Base Directory**
   3. Selecionar a pasta do **backend-veiculo** e clicar no botão **ok**
4. Na opção **Goals**, inserir o comando **clean install**
5. Clique em **Run** para que seja criado o binário do **backend_veiculo**
6. Ao **termino** da execução será exibida a mensagem **BUILD SUCCESS** presseguida do tempo total e data de finalização caso as instruções tenham sido seguidas

Ambos os binários serão armazenados nos respectivos diretórios:

- projeto-check-veiculos\backend-cliente\target
- projeto-check-veiculos\backend-veiculo\target
<br><br>

# Executando a aplicação através dos binários

Para iniciar o serviço deve-se via terminal, navegar até o diretório dos respectivos arquivos:

### **Backend-cliente**
- projeto-check-veiculos\backend-cliente\target
- Inserir comando **java -jar backend-cliente-0.0.1-SNAPSHOT.jar**
- Ao **termino** da execução do comando, será exibida a mensagem: **Started Application**
- Utiliza **porta 8081**

<br>

### **Backend-veiculo**
- projeto-check-veiculos\backend-veiculo\target
- Inserir comando **java -jar backend-veiculo-0.0.1-SNAPSHOT.jar**
- Ao termino da execução do comando, será exibida a mensagem: **Started Application**
- Utiliza **porta 8080**

<br>

# Criando as imagens no Docker

Criaremos duas imagens, sendo uma referente ao **serviço dos clientes** e outra para **veículos**. 

### **Cliente:**
1. Através do **cmd** acesse o diretório: **projeto-check-veiculos\backend-cliente**
2. Insira o comando: **docker build --build-arg JAR_FILE=./target/backend-cliente-0.0.1-SNAPSHOT.jar -t backend-cliente .**
3. Para visualizar a imagem criada, digite o comando: **docker images**
4. Observe que a imagem foi criada com nome: **backend-cliente**

### **Veículos:**
1. Através do **cmd** acesse o diretório: **projeto-check-veiculos\backend-veiculo**
2. Insira o comando: **docker build --build-arg JAR_FILE=./target/backend-veiculo-0.0.1-SNAPSHOT.jar -t backend-veiculo .**
3. Para visualizar a imagem criada, digite o comando: **docker images**
4. Observe que a imagem foi criada com nome: **backend-veiculo**

<br>

# Executando a aplicação através do Docker

Para iniciar o serviço deve-se via terminal:

### **Backend-cliente**
- **docker run -it -p 8081:8081 backend-cliente**
- Ao **termino** da execução do comando, será exibida a mensagem: **Started Application**
- Observar que o serviço utiliza a **porta 8081**

<br>

### **Backend-veiculo**
- **docker run -it -p 8080:8080 backend-veiculo**
- Ao **termino** da execução do comando, será exibida a mensagem: **Started Application**
- Observar que o serviço utiliza a **porta 8080**
