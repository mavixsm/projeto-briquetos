# 🏅 Esporte Kids – Loja de Brinquedos Esportivos

Projeto da **AC4 de Programação Orientada a Objetos (Ibmec)**: uma aplicação **Spring Boot** para gerenciar o catálogo de uma loja de brinquedos esportivos para crianças de até 15 anos.

A aplicação tem duas formas de acesso:

* **API REST** em `/brinquedos`, testada pelo Postman, com CRUD completo.
* **Telas web (Thymeleaf)**, com landing page pública, login e áreas separadas para os perfis **USER** e **ADMIN**.

**Integrantes:** Mari e Maria

---

## 📑 Sumário

1. [Tecnologias](#-tecnologias)
2. [Spring Initializr](#-spring-initializr)
3. [Arquitetura](#-arquitetura)
4. [Estrutura de pastas](#-estrutura-de-pastas)
5. [Como rodar](#-como-rodar)
6. [Usuários de teste](#-usuários-de-teste)
7. [Entidade e validações](#-entidade-e-validações)
8. [JpaRepository](#-jparepository)
9. [Segurança (Spring Security)](#-segurança-spring-security)
10. [API REST – testes no Postman](#-api-rest--testes-no-postman)
11. [Telas](#-telas)
12. [Banco H2](#-banco-h2)

---

## 🛠 Tecnologias

| Tecnologia | Para que usamos |
|---|---|
| **Java 21** | Linguagem do projeto |
| **Spring Boot 4.1.2** | Base da aplicação, com o servidor Tomcat embutido na porta 8080 |
| **Spring Web MVC** | Controllers da API REST e das telas |
| **Spring Data JPA + Hibernate** | Persistência dos dados com o `JpaRepository` |
| **H2 Database** | Banco de dados salvo em arquivo (`./data/brinquedosdb`) |
| **Bean Validation** | Validação dos campos da entidade |
| **Spring Security** | Login e controle de acesso por perfil (USER e ADMIN) |
| **Thymeleaf + thymeleaf-extras-springsecurity6** | Telas HTML geradas no servidor |
| **Spring Boot DevTools** | Reinício automático durante o desenvolvimento |
| **Maven** | Gerenciamento de dependências e build |
| **IntelliJ IDEA** | IDE usada no desenvolvimento |
| **Postman** | Testes da API |

---

## ⚙ Spring Initializr

O projeto foi gerado em [start.spring.io](https://start.spring.io) com esta configuração:

* **Project:** Maven
* **Language:** Java
* **Spring Boot:** 4.1.2
* **Group:** `br.edu.ibmec`
* **Artifact:** `ProjetoBrinquedos`
* **Java:** 21
* **Dependências:**
  * Spring Web
  * Spring Data JPA
  * H2 Database
  * Validation
  * Spring Security
  * Thymeleaf
  * Spring Boot DevTools

![Configuração no Spring Initializr](docs/img/spring-initializr.png)

---

## 🏛 Arquitetura

O projeto segue a arquitetura em camadas:

```
            Postman (JSON)                       Navegador (HTML)
                 │                                      │
                 ▼                                      ▼
   ┌──────────────────────────────────────────────────────────────┐
   │                Spring Security (SecurityConfig)               │
   │      login · perfis USER/ADMIN · Basic Auth · form login      │
   └──────────────────────────────────────────────────────────────┘
                 │                                      │
                 ▼                                      ▼
      BrinquedoController                         WebController
      (@RestController /brinquedos)               (@Controller → Thymeleaf)
                 │                                      │
                 ▼                                      │
         BrinquedoService                               │
       (regras de negócio)                              │
                 │                                      │
                 └──────────────┬───────────────────────┘
                                ▼
                       BrinquedoRepository
                (extends JpaRepository<Brinquedo, Long>)
                                │
                                ▼
                     Hibernate / JPA  (@Entity Brinquedo)
                                │
                                ▼
                   Banco H2 → tabela TDS_TB_Brinquedos
```

| Camada | Classe | Responsabilidade |
|---|---|---|
| **Model** | `Brinquedo` | Entidade JPA mapeada para a tabela `TDS_TB_Brinquedos`, com as validações |
| **Repository** | `BrinquedoRepository` | Acesso ao banco, herdando todos os métodos do `JpaRepository` |
| **Service** | `BrinquedoService` | Regras de negócio, como buscar, atualizar e excluir, e o tratamento de "não encontrado" (404) |
| **Controller REST** | `BrinquedoController` | Endpoints JSON em `/brinquedos` |
| **Controller Web** | `WebController` | Rotas das telas: `/`, `/login`, `/painel` e `/admin` |
| **Config** | `SecurityConfig` | Usuários, perfis e regras de acesso |

---

## 📁 Estrutura de pastas

```
ProjetoBrinquedos/
├── pom.xml
└── src/main/
    ├── java/br/edu/ibmec/ProjetoBrinquedos/
    │   ├── ProjetoBrinquedosApplication.java
    │   ├── config/
    │   │   └── SecurityConfig.java
    │   ├── controller/
    │   │   ├── BrinquedoController.java   ← API REST
    │   │   └── WebController.java         ← telas
    │   ├── exception/
    │   │   └── GlobalExceptionHandler.java
    │   ├── model/
    │   │   └── Brinquedo.java
    │   ├── repository/
    │   │   └── BrinquedoRepository.java
    │   └── service/
    │       └── BrinquedoService.java
    └── resources/
        ├── application.properties
        ├── static/css/
        │   └── style.css
        └── templates/
            ├── index.html        ← landing page pública
            ├── login.html        ← tela de login
            ├── brinquedos.html   ← catálogo (USER e ADMIN)
            └── admin.html        ← cadastro e exclusão (só ADMIN)
```

---

## ▶ Como rodar

**Pré-requisito:** Java 21 ou mais recente. Não precisa instalar o Maven, porque o projeto já vem com o Maven Wrapper (`mvnw`).

```bash
git clone https://github.com/mavixsm/projeto-briquetos.git
cd projeto-briquetos/ProjetoBrinquedos
./mvnw spring-boot:run
```

No Windows, use `mvnw.cmd spring-boot:run`.

Quando aparecer `Started ProjetoBrinquedosApplication` no console, a aplicação está pronta:

| Endereço | O que é |
|---|---|
| http://localhost:8080 | Landing page |
| http://localhost:8080/login | Login |
| http://localhost:8080/brinquedos | API REST |
| http://localhost:8080/h2-console | Console do banco H2 |

Também dá para rodar direto pelo IntelliJ: abra a classe `ProjetoBrinquedosApplication` e clique em ▶ Run.

---

## 👤 Usuários de teste

Os usuários ficam em memória (`InMemoryUserDetailsManager`), e as senhas são criptografadas com **BCrypt**.

| Usuário | Senha | Perfil | Pode fazer |
|---|---|---|---|
| `user` | `123` | USER | Ver o catálogo e fazer GET na API |
| `admin` | `123` | ADMIN | Tudo: cadastrar, editar, excluir e acessar a área `/admin` |

---

## 🧸 Entidade e validações

A classe `Brinquedo` é uma entidade JPA (`@Entity`), mapeada para a tabela **`TDS_TB_Brinquedos`** com `@Table(name = "TDS_TB_Brinquedos")`.

| Campo | Tipo | Validação | Regra |
|---|---|---|---|
| `id` | `long` | `@Id @GeneratedValue` | Gerado automaticamente pelo banco |
| `nome` | `String` | `@NotBlank @Size(min = 2, max = 100)` | Obrigatório, entre 2 e 100 caracteres |
| `tipo` | `String` | `@NotBlank` | Obrigatório (bola, raquete, patins…) |
| `classificacao` | `int` | `@Min(0) @Max(15)` | Idade indicada, de 0 a 15 anos (a loja atende crianças de até 15 anos) |
| `tamanho` | `String` | `@NotBlank @Pattern(regexp = "P\|M\|G")` | Só aceita **P**, **M** ou **G** |
| `preco` | `double` | `@NotNull @Positive` | Tem que ser maior que zero |

Quando um dado viola alguma dessas regras, o Bean Validation impede que ele seja salvo no banco. O Hibernate também usa as anotações para criar as restrições da tabela. Por exemplo, a coluna `classificacao` recebe a restrição `check ((classificacao<=15) and (classificacao>=0))`.

Tabela criada automaticamente pelo Hibernate (`ddl-auto=update`):

```sql
create table tds_tb_brinquedos (
    id bigint not null,
    classificacao integer not null check ((classificacao<=15) and (classificacao>=0)),
    nome varchar(100) not null,
    preco float(53) not null,
    tamanho varchar(255) not null,
    tipo varchar(255) not null,
    primary key (id)
)
```

---

## 🗄 JpaRepository

```java
@Repository
public interface BrinquedoRepository extends JpaRepository<Brinquedo, Long> {
}
```

### O que é

O `JpaRepository` é uma **interface do Spring Data JPA** que já traz prontos todos os métodos de acesso ao banco. Basta criar uma interface que estenda `JpaRepository<Entidade, TipoDoId>`. No nosso caso, `Brinquedo` é a entidade e `Long` é o tipo do `id`.

**Não precisamos escrever nenhuma implementação nem nenhum SQL.** Quando a aplicação sobe, o Spring cria sozinho uma classe que implementa essa interface. No log aparece a mensagem `Found 1 JPA repository interface`. Depois, o Spring injeta essa classe onde ela for usada, como no `BrinquedoService` e no `WebController`.

### Por que é uma `interface` e não uma `class`

Porque quem escreve a implementação é o Spring. Se fosse uma `class`, o Java exigiria que nós mesmos implementássemos todos os métodos herdados (`save`, `findAll`, `flush`…).

### Hierarquia

```
Repository
  └── CrudRepository            → save, findById, findAll, deleteById, count, existsById...
        └── PagingAndSortingRepository  → findAll(Sort), findAll(Pageable)
              └── JpaRepository         → saveAndFlush, flush, deleteAllInBatch, findAll(Example)...
                    └── BrinquedoRepository (nosso)
```

### Métodos que usamos e o SQL que o Hibernate gera

| Método | Onde usamos | SQL gerado |
|---|---|---|
| `save(brinquedo)` | Cadastrar e atualizar | `INSERT` se o brinquedo é novo, `UPDATE` se já existe |
| `findAll()` | Listar | `SELECT * FROM tds_tb_brinquedos` |
| `findById(id)` | Buscar por ID | `SELECT ... WHERE id = ?`, que retorna um `Optional` |
| `delete(brinquedo)` / `deleteById(id)` | Excluir | `DELETE FROM tds_tb_brinquedos WHERE id = ?` |

Como `spring.jpa.show-sql=true` está ativado, esses SQLs aparecem no console do IntelliJ toda vez que um método é chamado.

### Métodos personalizados (query methods)

O Spring Data também cria consultas **só pelo nome do método**. Por exemplo, se adicionarmos isto na interface:

```java
List<Brinquedo> findByTipo(String tipo);
List<Brinquedo> findByClassificacaoLessThanEqual(int idade);
```

o Spring gera sozinho `SELECT ... WHERE tipo = ?` e `SELECT ... WHERE classificacao <= ?`.

### Vantagens

* Menos código: não existe DAO, JDBC ou SQL escrito à mão.
* Os métodos padrão já são testados e confiáveis.
* Troca de banco fácil: mudando só o `application.properties`, sai o H2 e entra o PostgreSQL ou o MySQL.
* Já vem com paginação, ordenação e transações.

---

## 🔒 Segurança (Spring Security)

Configurada na classe `SecurityConfig`:

```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/", "/login", "/css/**", "/error", "/h2-console/**").permitAll()
    .requestMatchers(HttpMethod.GET, "/brinquedos/**", "/painel").hasAnyRole("USER", "ADMIN")
    .anyRequest().hasRole("ADMIN")
)
.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/painel", true).permitAll())
.httpBasic(Customizer.withDefaults())
```

| Endereço | Visitante | USER | ADMIN |
|---|:---:|:---:|:---:|
| `/`, `/login`, `/css/**`, `/h2-console` | ✅ | ✅ | ✅ |
| `GET /brinquedos` e `GET /painel` | ❌ | ✅ | ✅ |
| `POST`, `PUT`, `DELETE /brinquedos` | ❌ | ❌ 403 | ✅ |
| `/admin` | ❌ | ❌ 403 | ✅ |

* **`formLogin`:** login pela tela `login.html`, usado no navegador.
* **`httpBasic`:** login enviado em cada requisição, usado no Postman (aba *Authorization → Basic Auth*).
* **`BCryptPasswordEncoder`:** as senhas nunca ficam guardadas em texto puro.
* **`sec:authorize`:** nas telas, mostra ou esconde botões conforme o perfil. Por exemplo, o link "Admin" só aparece para o ADMIN.

---

## 📮 API REST – testes no Postman

**Base URL:** `http://localhost:8080/brinquedos`
**Autenticação:** aba *Authorization → Basic Auth*, com `admin` / `123` (ou `user` / `123` para os testes de leitura).

| Método | Endpoint | Descrição | Perfil |
|---|---|---|---|
| `GET` | `/brinquedos` | Lista todos | USER, ADMIN |
| `GET` | `/brinquedos/{id}` | Busca pelo ID | USER, ADMIN |
| `POST` | `/brinquedos` | Cadastra | ADMIN |
| `PUT` | `/brinquedos/{id}` | Atualiza | ADMIN |
| `DELETE` | `/brinquedos/{id}` | Exclui pelo ID | ADMIN |

### 1. Cadastrar – `POST /brinquedos`

```json
{
  "nome": "Bola de Futebol Infantil",
  "tipo": "Bola",
  "classificacao": 6,
  "tamanho": "M",
  "preco": 79.90
}
```

Resposta: **200 OK**, com o brinquedo salvo e o `id` gerado.

![POST](docs/img/postman-post.png)

### 2. Listar – `GET /brinquedos`

Resposta: **200 OK**, com a lista de brinquedos.

![GET todos](docs/img/postman-get.png)

### 3. Buscar por ID – `GET /brinquedos/1`

Resposta: **200 OK**, com o brinquedo. Se o ID não existir, a resposta é **404 Not Found**.

![GET por id](docs/img/postman-get-id.png)

### 4. Atualizar – `PUT /brinquedos/1`

```json
{
  "nome": "Bola de Futebol Profissional Kids",
  "tipo": "Bola",
  "classificacao": 8,
  "tamanho": "G",
  "preco": 99.90
}
```

Resposta: **200 OK**, com o brinquedo atualizado.

![PUT](docs/img/postman-put.png)

### 5. Excluir – `DELETE /brinquedos/1`

Resposta: **204 No Content**.

![DELETE](docs/img/postman-delete.png)

### 6. Erro de validação

```json
{
  "nome": "A",
  "tipo": "",
  "classificacao": 20,
  "tamanho": "XG",
  "preco": -5
}
```

O brinquedo **não é salvo**, porque os dados violam as validações da entidade.

![Erro de validação](docs/img/postman-validacao.png)

### 7. Acesso negado

`POST /brinquedos` feito com o usuário `user` resulta em **403 Forbidden**, porque só o ADMIN pode cadastrar.

![Acesso negado](docs/img/postman-403.png)

---

## 🖥 Telas

| Tela | Rota | Acesso |
|---|---|---|
| Landing page | `/` | Pública |
| Login | `/login` | Pública |
| Catálogo | `/painel` | USER e ADMIN |
| Administração | `/admin` | Só ADMIN |

### Landing page
![Landing page](docs/img/landing.png)

### Login
![Login](docs/img/login.png)

### Catálogo (logado como USER)
O USER só consulta. Aparece um aviso de que cadastro, edição e exclusão são exclusivos do ADMIN.

![Painel USER](docs/img/painel-user.png)

### USER tentando acessar /admin → 403
![403](docs/img/403-user-admin.png)

### Área do administrador
Formulário de cadastro e lista com o botão de exclusão.

![Admin](docs/img/admin.png)

---

## 💾 Banco H2

Configuração no `application.properties`:

```properties
server.port=8080
spring.datasource.url=jdbc:h2:file:./data/brinquedosdb
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

O banco é salvo **em arquivo** (`./data/brinquedosdb`), então os dados continuam lá mesmo depois de reiniciar a aplicação.

**Para acessar o console:**

1. Abra http://localhost:8080/h2-console.
2. Em **JDBC URL**, coloque `jdbc:h2:file:./data/brinquedosdb`.
3. Em **User**, coloque `sa` e deixe a senha vazia. Clique em **Connect**.
4. Rode `SELECT * FROM TDS_TB_BRINQUEDOS;`.

![H2 Console](docs/img/h2-console.png)

---

📚 *AC4 – Programação Orientada a Objetos · Ibmec · 2026*
