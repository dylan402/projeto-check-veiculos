# CheckVeículos



Pré-requisitos:

Eclipse IDE for Java Developers (includes Incubating components)
Version: 2021-03 (4.19.0)
Build id: 20210312-0638

Java:
Plataforma: 1.8 
Produto: 1.8.0_291
Arquitetura: x86




1=> Criar o arquivo de acesso ao Dynamo DB da Amazon.

O mesmo deve conter o conteúdo:

amazon.aws.accesskey={ACCESS_KEY}
amazon.aws.secretkey={SECRET_KEY}


projeto-check-veiculos\backend-cliente\src\main\resources
projeto-check-veiculos\backend-cliente\src\test\resources

projeto-check-veiculos\backend-veiculo\src\main\resources
projeto-check-veiculos\backend-veiculo\src\test\resources


2=> 

Primeiramente é necessário realizar a configuração do Maven.
Criaremos duas configurações de builds para criamos binários dos dois serviços.
