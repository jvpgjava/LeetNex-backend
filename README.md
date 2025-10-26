# LeetNex Backend

Sistema backend da plataforma LeetNex - Uma plataforma de preparação para entrevistas técnicas para desenvolvedores e estudantes.

## Visão Geral

LeetNex é uma plataforma abrangente projetada para ajudar desenvolvedores e estudantes a se prepararem para entrevistas técnicas por meio de problemas de prática, desafios de programação e rastreamento de sessões. Este backend fornece APIs RESTful para suportar a aplicação frontend com gerenciamento de usuários, gerenciamento de problemas, execução de código e análise.

## Stack Tecnológica

- **Java** 17
- **Spring Boot** 3.2.0
- **Spring Security** - Autenticação e autorização
- **JPA/Hibernate** - Mapeamento objeto-relacional
- **PostgreSQL** - Banco de dados
- **Redis** - Cache
- **JWT** - Autenticação baseada em token
- **MapStruct** - Mapeamento de DTOs
- **Maven** - Gerenciamento de dependências
- **WebRTC** - Suporte para comunicação em tempo real

## Funcionalidades

### Gerenciamento de Usuários
- Registro e autenticação de usuários
- Autenticação segura baseada em JWT
- Controle de acesso baseado em funções (Admin, Usuário)
- Perfis de usuário e rastreamento de progresso

### Gerenciamento de Problemas
- Operações CRUD para problemas de codificação
- Suporte para múltiplas linguagens de programação
- Níveis de dificuldade dos problemas (Fácil, Médio, Difícil)
- Categorias de problemas (Arrays, Strings, Programação Dinâmica, etc.)
- Casos de teste e restrições de exemplo

### Execução de Código
- Suporte para execução de código em múltiplas linguagens
- Ambiente de execução de código em sandbox
- Validação de casos de teste
- Rastreamento de uso de runtime e memória

### Sessões de Prática
- Funcionalidade de cronômetro para sessões de prática
- Suporte para integração de câmera para monitoramento
- Gravação e histórico de sessões
- Rastreamento de submissões

### Análise
- Estatísticas e rastreamento de progresso do usuário
- Histórico e análise de submissões
- Métricas de desempenho
- Suporte para leaderboard

## Estrutura do Projeto

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/leetnex/
│   │   │   ├── config/              # Classes de configuração
│   │   │   ├── controller/          # Controladores REST
│   │   │   ├── dto/                 # Objetos de transferência de dados
│   │   │   ├── mapper/              # Mapeadores MapStruct
│   │   │   ├── model/               # Modelos de entidade
│   │   │   ├── repository/          # Camada de acesso a dados
│   │   │   ├── security/            # Configuração de segurança
│   │   │   ├── service/             # Lógica de negócios
│   │   │   └── util/                # Classes utilitárias
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/        # Migrações de banco de dados
│   └── test/
│       └── java/com/leetnex/
└── pom.xml
```

## Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- PostgreSQL 12+
- Redis (opcional, para cache)

## Configuração

### Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL:
```sql
CREATE DATABASE leetnex;
```

2. Atualize o `application.properties` com suas credenciais de banco de dados:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/leetnex
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

### Configuração JWT

Configure o segredo JWT em `application.properties`:
```properties
jwt.secret=sua-chave-secreta-aqui
jwt.expiration=86400000
```

## Executando a Aplicação

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd backend
```

2. Compile o projeto:
```bash
mvn clean install
```

3. Execute a aplicação:
```bash
mvn spring-boot:run
```

A aplicação será iniciada em `http://localhost:8080`

## Documentação da API

### Autenticação
- `POST /api/auth/register` - Registrar um novo usuário
- `POST /api/auth/login` - Login do usuário
- `POST /api/auth/refresh` - Atualizar token de acesso

### Usuários
- `GET /api/users/me` - Obter perfil do usuário atual
- `PUT /api/users/me` - Atualizar perfil do usuário
- `GET /api/users/{id}` - Obter usuário por ID (Admin)

### Problemas
- `GET /api/problems` - Obter todos os problemas
- `GET /api/problems/{id}` - Obter problema por ID
- `POST /api/problems` - Criar problema (Admin)
- `PUT /api/problems/{id}` - Atualizar problema (Admin)
- `DELETE /api/problems/{id}` - Excluir problema (Admin)

### Submissões
- `POST /api/submissions` - Submeter solução de código
- `GET /api/submissions` - Obter submissões do usuário
- `GET /api/submissions/{id}` - Obter submissão por ID

### Sessões
- `GET /api/sessions` - Obter sessões de prática do usuário
- `POST /api/sessions` - Iniciar uma nova sessão de prática
- `PUT /api/sessions/{id}` - Atualizar sessão
- `DELETE /api/sessions/{id}` - Encerrar sessão

## Testes

Execute todos os testes:
```bash
mvn test
```

Execute uma classe de teste específica:
```bash
mvn test -Dtest=SuaClasseDeTeste
```

## Migrações de Banco de Dados

O projeto usa Flyway para migrações de banco de dados. As migrações estão localizadas em `src/main/resources/db/migration/`

## Segurança

- Autenticação baseada em JWT
- Criptografia de senha usando BCrypt
- Controle de acesso baseado em funções
- Configuração CORS
- Prevenção de injeção SQL através de JPA

## Diretrizes de Desenvolvimento

### Estilo de Código
- Siga as convenções de nomenclatura Java
- Use nomes de variáveis e métodos significativos
- Adicione comentários JavaDoc para métodos públicos
- Mantenha métodos focados e com propósito único

### Banco de Dados
- Use entidades JPA para mapeamento de banco de dados
- Sempre use DTOs para respostas de API
- Evite expor objetos de entidade diretamente
- Use transações adequadamente

### Tratamento de Erros
- Implemente tratamento global de exceções
- Retorne mensagens de erro significativas
- Registre erros adequadamente
- Use códigos de status HTTP apropriados

## Contribuindo

Ao contribuir para este projeto:

1. Crie um branch de funcionalidade a partir de `main`
2. Faça suas alterações
3. Escreva ou atualize testes conforme necessário
4. Certifique-se de que todos os testes passem
5. Envie uma pull request

## Licença

Consulte o arquivo LICENSE para detalhes.

## Contato

Para dúvidas ou suporte, entre em contato com a equipe de desenvolvimento.

## Histórico de Versões

- **1.0.0** - Versão inicial com funcionalidades principais

