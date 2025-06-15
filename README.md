# Trello API 

Este é um projeto de API RESTful construído com Spring Boot para gerenciar usuários e tarefas, simulando funcionalidades básicas de um aplicativo como o Trello. O foco foi na implementação de operações CRUD completas e um sistema de autenticação JWT.

## Funcionalidades

*   **Gerenciamento de Usuários (CRUD completo):**
    *   `POST /api/usuarios`: Criar um novo usuário.
    *   `POST /api/auth/login`: Cria o token. 
    *   `GET /api/usuarios`: Listar todos os usuários.
    *   `GET /api/usuarios/{id}`: Buscar usuário por ID.
    *   `PUT /api/usuarios/{id}`: Atualizar usuário.
    *   `DELETE /api/usuarios/{id}`: Deletar usuário.

*   **Gerenciamento de Tarefas (CRUD completo):**
    *   `POST /api/tarefas/usuario/{usuarioId}`: Criar uma nova tarefa associada a um usuário.
    *   `GET /api/tarefas`: Listar todas as tarefas (geral).
    *   `GET /api/tarefas/usuario/{usuarioId}`: Listar tarefas de um usuário específico.
    *   `GET /api/tarefas/usuario/{usuarioId}/status/{status}`: Listar tarefas de um usuário por status.
    *   `GET /api/tarefas/{id}`: Buscar tarefa por ID.
    *   `PUT /api/tarefas/{id}`: Atualizar tarefa.
    *   `DELETE /api/tarefas/{id}`: Deletar tarefa.
    *   **Importante:** Todas as operações de tarefas (exceto a criação de usuário e login) exigem autenticação via token JWT.

*   **Autenticação JWT:**
    *   Sistema de login baseado em token JWT para acesso a endpoints protegidos.

## Tecnologias Utilizadas

*   **Spring Boot:** Framework para construção de aplicações Java.
*   **Spring Data JPA:** Para persistência de dados com H2 Database.
*   **Spring Security:** Para segurança da aplicação (com configuração simplificada).
*   **H2 Database:** Banco de dados em memória para desenvolvimento e testes.
*   **JJWT:** Biblioteca para JSON Web Tokens.
*   **JUnit 5:** Para testes unitários da camada de serviço.

## Como Rodar a Aplicação

1.  **Pré-requisitos:** Certifique-se de ter o Java Development Kit (JDK) 17 ou superior e o Maven instalados.
2.  **Compilar e Rodar:** Abra o terminal na raiz do projeto e execute:
    ```bash
    mvn clean install -DskipTests
    mvn spring-boot:run -DskipTests
    ```
    A aplicação será iniciada e estará disponível em `http://localhost:8080`.

## Fluxo de Autenticação e Uso da API

Para interagir com a API, especialmente os endpoints protegidos, siga este fluxo:

1.  **Criar Usuário (público):**
    *   **Endpoint:** `POST http://localhost:8080/api/usuarios`
    *   **Body:**
        ```json
        {
            "nome": "Seu Nome",
            "email": "seu@email.com",
            "senha": "suasenha"
        }
        ```

2.  **Fazer Login (público) e Obter Token:**
    *   Este é o primeiro passo para obter o token JWT necessário para acessar os demais endpoints.
    *   **Endpoint:** `POST http://localhost:8080/api/auth/login`
    *   **Body:**
        ```json
        {
            "email": "seu@email.com",
            "senha": "suasenha"
        }
        ```
    *   **Resposta:** Retornará um JSON contendo o `token` JWT.
        ```json
        {
            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        }
        ```

3.  **Acessar Endpoints Protegidos com o Token:**
    *   Para todas as requisições que acessam métodos de tarefa (e outros futuros endpoints protegidos), você **deve** incluir o token obtido no login no cabeçalho `Authorization`.
    *   **Header:** `Authorization: Bearer {seu_token_aqui}`
        *   **Importante:** Substitua `{seu_token_aqui}` pelo token completo obtido no login, sem as aspas se o Insomnia ou ferramenta de teste as adicionar.

### **Exemplo de  Uso do Token (Criar Tarefa)**

*   **Endpoint:** `POST http://localhost:8080/api/tarefas/usuario/{usuarioId}`
    *   Substitua `{usuarioId}` pelo ID do usuário (obtido ao criar o usuário ou listar usuários).
*   **Headers:**
    *   `Content-Type: application/json`
    *   `Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`
*   **Body:**
    ```json
    {
        "titulo": "Tarefa de Exemplo",
        "descricao": "Esta é uma tarefa de exemplo.",
        "prazo": "2024-07-01T10:00:00",
        "status": "PENDENTE"
    }
    ```

## Testes Unitários

Testes simples foram implementados para as camadas de serviço (`UsuarioServiceTest.java` e `TarefaServiceTest.java`) utilizando JUnit e Mockito para garantir a lógica de negócios.

Para rodar os teste:
mvn test