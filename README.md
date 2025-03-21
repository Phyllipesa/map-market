# Map Market

  O **MapMarket** é uma aplicação para facilitar compras em supermercados, permitindo que os usuários encontrem a 
  localização exata de um produto nas prateleiras. O projeto foi desenvolvido aplicando os conceitos de 
  **Arquitetura Hexagonal**, com o intuito de colocar em prática os conhecimentos 
  adquiridos, priorizando a qualidade do código e a organização.

## Tecnologias Utilizadas

- **Backend:** Java, Spring Boot, Spring Security, Spring HATEOAS, ModelMapper
- **Banco de Dados:** MySQL, Flyway
- **Testes:** JUnit, Mockito, Rest Assured, TestContainers
- **Autenticação:** JWT (accessToken e refreshToken)
- **Containerização:** Docker para o backend e `docker-compose` para suportar tanto o banco de dados quanto o backend.

## Controle de Acesso

O sistema tem três tipos de usuários:

1. **Common User**: Apenas pesquisa a localização dos itens.
2. **Manager**: Pode criar e atualizar produtos e unidades de prateleira, além de atualizar a localização dos produtos.
3. **Admin**: Responsável por criar usuários, excluir produtos e unidades de prateleira, além de gerenciar localizações.

## Endpoints

<details>
<summary>Os principais endpoints da aplicação incluem:</summary>

- ### **Autenticação**
  - `POST /auth/sign-in` - Login e geração de tokens.
  - `PUT /auth/refresh/{username}` - Atualização do accessToken usando refreshToken.

- ### **Localizações**
  - `GET /api/v1/location/{id}` - Busca uma localização específica.
  - `GET /api/v1/location/product/{id}` - Busca a localização de um produto.
  - `PUT /api/v1/location/{locationId}/{productId}` - Atualiza a localização de um produto.
  - `PUT /api/v1/location/{id}` - Atualiza informações da localização.

- ### **Produtos**
  - `GET /api/v1/product` - Retorna todos os produtos.
  - `GET /api/v1/product/{id}` - Retorna um produto específico.
  - `POST /api/v1/product` - Adiciona um novo produto.
  - `PUT /api/v1/product/{id}` - Atualiza um produto existente.
  - `DELETE /api/v1/product/{id}` - Remove um produto.

- ### **Unidades de Prateleira**
  - `GET /api/v1/shelvingUnit` - Retorna todas as unidades de prateleira.
  - `GET /api/v1/shelvingUnit/{id}` - Retorna uma unidade específica.
  - `POST /api/v1/shelvingUnit` - Adiciona uma nova unidade de prateleira.
  - `PUT /api/v1/shelvingUnit/{id}` - Atualiza uma unidade existente.
  - `DELETE /api/v1/shelvingUnit/{id}` - Remove uma unidade de prateleira.

</details>

## Requisitos
Certifique-se de ter o Java, Maven e Docker instalados em sua máquina.

## Executando a Aplicação

1. Clone o repositório:
   ```bash
   git clone git@github.com:Phyllipesa/map-market.git
   ```

2. Navegue até o diretório do projeto:
   ```bash
   cd map-market
   ```

3. Execute o docker-compose:
   ```bash
   docker compose up -d --build
   ```

4. A aplicação será iniciada em 'http://localhost:80'.


5. Utilize as configurações de ENV e Collections fornecidas na pasta "documents" com o Postman ou outra ferramenta similar para realizar requisições e testar a API.

## Tecnologias
![YAML](https://img.shields.io/badge/yaml-%23ffffff.svg?style=for-the-badge&logo=yaml&logoColor=151515)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![JUnit5rest](https://img.shields.io/badge/JUnit5-25A162.svg?style=for-the-badge&logo=JUnit5&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

## Autor
- [@phyllipesa](https://github.com/phyllipesa) - Desenvolvimento do projeto