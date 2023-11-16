# CSV Test
- Projeto Spring Boot para ler arquivo .csv de funcionários e salvá-los em um banco de dados PostgreSQL

## Como rodar no IntelliJ?
- Crie um banco de dados PostgreSQL chamado "csvtest".
- Clique no botão de start da IDE.

## Detalhes sobre as api's externas
- A api pública usada para buscar os dados da empresa de um funcionário, só aceita 3 requests por minutos. Então se for enviado um .csv com mais de 3 funcionários, a aplicação dá erro.
