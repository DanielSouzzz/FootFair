# Footfair ⚽

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

---

# 🚀 Como usar Footfair

# 1. Clone o repositório
git clone https://github.com/seu-usuario/footfair.git

# 2. Configure o banco de dados e variáveis de ambiente
# Exemplo (.env ou variáveis do sistema):
DB_HOST=jdbc:mysql://localhost:3306/footfair
DB_USER=seu_user_db
DB_PASS=sua_senha_db
API_SECURITY_TOKEN_SECRET=sua_chave_jwt

# 3. Execute o projeto
mvn spring-boot:run

# 4. Teste os endpoints via Postman ou Swagger:
# Abra no navegador:
http://localhost:8080/swagger-ui/index.html
