# CSV Test
- Projeto Spring Boot para ler arquivo .csv de funcionários e salvá-los em um banco de dados PostgreSQL

## Link do projeto no GitHub
- https://github.com/Xaakla/csv-test.git

## Arquivo .csv
- Na raiz do projeto tem um arquivo .csv para testar a aplicação

## Como rodar no IntelliJ?
- O sistema está rodando na porta 8070.
- Crie um banco de dados PostgreSQL chamado "csvtest".
- Clique no botão de start da IDE.

## Detalhes sobre as api's externas
- A api pública usada para buscar os dados da empresa de um funcionário, só aceita 3 requests por minutos. Então se for enviado um .csv com mais de 3 funcionários, a aplicação dá erro.

## Endpoints
BASE_URL: "http://localhost:8070"
- (POST) BASE_URL + "/employees" -> Cria um funcionário
- (POST) BASE_URL + "/employees/csv/upload" -> Upload um arquivo .csv para criar mais de 1 funcionário
- (GET) BASE_URL + "/employees" -> Lista todos os funcionários
- (GET) BASE_URL + "/employees/{document}" -> Lista um funcionário pelo CPF
- (PATCH) BASE_URL + "/employees/{document}/edit/name" -> Edita o nome de um funcionário pelo CPF
- (PATCH) BASE_URL + "/employees/{document}/edit/postalCode" -> Edita o CEP de um funcionário pelo CPF
- (PATCH) BASE_URL + "/employees/{document}/edit/companyDocument" -> Edita o CNPJ de um funcionário pelo CPF
- (DELETE) BASE_URL + "/employees/{document}" -> Deleta um funcionário pelo CPF
