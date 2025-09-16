# Footfair ⚽

[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-brightgreen)](https://spring.io/projects/spring-boot)
[![Build](https://img.shields.io/badge/Build-Passing-brightgreen)](#)

**Footfair** é uma plataforma para gestão de times de futebol amador.  
O sistema organiza jogadores, monta times equilibrados automaticamente, gera rankings e facilita a comunicação.

---

## 🔹 Funcionalidades principais
- Cadastro de jogadores: nome, idade, posição e habilidades.
- Montagem automática de times semanais, balanceando habilidades.
- Ranking de jogadores e times, com estatísticas de vitórias e gols.
- Buscador de partidas: encontre ou desafie outros times.
- Histórico de jogos e estatísticas individuais.

---

## 💻 Tecnologias
- **Backend:** Java 17 + Spring Boot
- **Banco de dados:** MySQL
- **Arquitetura:** MVC com Controllers, Services, Repositories e DTOs
- **Docker-compose:** Para o banco de dados MySQL 

---

# 🚀 Como usar Footfair

## 1. Clone o repositório
git clone https://github.com/danielsouzzz/footfair.git
cd footfair

## 2. Configure o banco de dados e variáveis de ambiente
### Exemplo (.env ou variáveis do sistema):
DB_HOST=jdbc:mysql://localhost:3306/footfair
DB_USER=seu_user_db
DB_PASS=sua_senha_db
API_SECURITY_TOKEN_SECRET=sua_chave_jwt

### ⚠️ Adicione o .env no .gitignore para não subir para o GitHub

## 3. Suba o MySQL via Docker Compose
docker-compose up -d

## 4. Execute o projeto Spring Boot
mvn spring-boot:run

## 5. Teste os endpoints via Postman ou Swagger:
http://localhost:8080/swagger-ui/index.html
