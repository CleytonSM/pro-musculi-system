# 💪 Pro Musculi System API 💪

A Pro Musculi System API é um sistema desenvolvido em Java utilizando tecnologias como Spring, JPA, Spring Security e Docker, com o objetivo de administrar uma academia. Este projeto oferece controle sobre usuários e seus diferentes níveis de acesso, instrutores, clientes, aulas de dança e musculação.

## Funcionalidades Principais ⚙

- Gerenciamento de usuários
- Gerenciamento de instrutores
- Gerenciamento de clientes
- Agendamento de aulas de dança e musculação
- Controle de acesso com diferentes níveis de permissão

## Repositório de Artefatos 👷‍♂️

Os artefatos do projeto relacionados à engenharia de software, estão disponíveis no repositório: [Artefatos-Pro-Musculi-System](https://github.com/CleytonSM/Artefatos-Pro-Musculi-System)

## Tecnologias Utilizadas 🤓

- Java
- Spring Boot
- JPA (Java Persistence API)
- Docker
- MySQL
- Spring Security

## Instalação e Uso 💻

1. Clone o repositório:
`git clone https://github.com/CleytonSM/pro-musculi-system.git`
2. Configure o ambiente Docker para o banco de dados que desejar.

3. Configure as propriedades no application.properties da aplicação Spring baseado o que foi definido na dockerfile. 
- `spring.datasource.url=`
- `spring.datasource.username=`
- `spring.datasource.password=`
- `spring.main.allow-circular-references=true` (Necessário para o funcionamento correto da aplicação)

4. Ao preparar o Docker, rode os script SQL presente em /src/main/resources/scripts.sql para criar as entidades.

5. Rode a aplicação. 

## Segurança 🔐

A segurança da API é garantida através de tokens que previnem ataques CSRF (Cross-Site Request Forgery) e roles que determinam o nível de acesso dos usuários na plataforma, variando entre administrador e usuário.

## Próximos Passos 👨‍💻

- Implementação de JWT Security para melhorar a autenticação e autorização.
- Integração de filas de mensagens usando RabbitMQ para processamento assíncrono.
- Aprimoramento das funcionalidades existentes.
- Implementação de testes unitários utilizando JUnit 5 a fim de trazer mais confiabilidade no projeto

## Versão 💪
- 1.0.0

## Tem alguma sugestão? 🙂
Se tiver alguma sugestão de melhora no código, caso haja uma má prática ou até uma questão de performace, ou se quiser contribuir para o projeto, entre em contato comigo pelo [linkedin](https://www.linkedin.com/in/cleyton-souza-martins/)!

Fique a vontade em me corrigir 😀, todo aprendizado e reaprendizado é sempre positivo.
