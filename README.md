# Pro Musculi System API

A Pro Musculi System API é um sistema desenvolvido em Java utilizando tecnologias como Spring, JPA, Spring Security e Docker, com o objetivo de administrar uma academia. Este projeto oferece controle sobre instrutores, clientes, aulas de dança e musculação, além de fornecer diferentes níveis de acesso para usuários administradores e comuns.

## Funcionalidades Principais

- Gerenciamento de instrutores
- Gerenciamento de clientes
- Agendamento de aulas de dança e musculação
- Controle de acesso com diferentes níveis de permissão

## Repositório de Artefatos

Os artefatos do projeto relacionados à engenharia de software, estão disponíveis no repositório: [Artefatos-Pro-Musculi-System](https://github.com/CleytonSM/Artefatos-Pro-Musculi-System)

## Tecnologias Utilizadas

- Java
- Spring Boot
- JPA (Java Persistence API)
- Docker
- MySQL
- Spring Security

## Instalação e Uso

1. Clone o repositório:
`git clone https://github.com/CleytonSM/pro-musculi-system.git`
2. Configure o ambiente Docker para o MySQL e a aplicação Spring.

3. Execute a aplicação Spring.

## Segurança

A segurança da API é garantida através de tokens que previnem ataques CSRF (Cross-Site Request Forgery) e roles que determinam o nível de acesso dos usuários na plataforma.

## Próximos Passos

- Implementação de JWT Security para melhorar a autenticação e autorização.
- Integração de filas de mensagens usando RabbitMQ para processamento assíncrono.
- Aprimoramento das funcionalidades existentes.
- Implementação de testes unitários utilizando JUnit 5 a fim de trazer mais confiabilidade no projeto

## Versão
1.0.0
