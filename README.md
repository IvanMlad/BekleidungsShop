# Bekleidungs-Shop (Demo-Projekt)

Kleines Fullstack-Demo-Projekt für ein Praktikum (Fachinformatiker AE).
Frontend: HTML/Bootstrap/JavaScript (Fetch API)
Backend: Spring Boot (REST API) + JPA/Hibernate
Datenbank: PostgreSQL in Docker

## Features
- Registrierung & Login (Demo)
- Rollen: CUSTOMER / EMPLOYEE / ADMIN
- Produkte anzeigen + suchen + sortieren
- Warenkorb (Frontend) + Bestellung absenden
- Admin: Produkte verwalten (CRUD)
- Mitarbeiter: Bestellungen ansehen (inkl. Details)

## Tech-Stack
- Java (Spring Boot)
- Spring Web (REST Controller)
- Spring Data JPA (Repositories)
- Hibernate (JPA-Implementierung)
- PostgreSQL (Datenbank)
- Docker (Datenbank-Container)
- HTML + Bootstrap 5 + JavaScript

## Projektstruktur (wichtigste Ordner)
- `src/main/java/com/shop/backend/controller`  -> REST Controller (Products, Orders, Auth)
- `src/main/java/com/shop/backend/model`       -> Entities (Product, ShopOrder, OrderItem, User)
- `src/main/java/com/shop/backend/repository`  -> JPA Repositories
- `src/main/resources/static`                  -> Frontend (shop.html, products.html, orders.html, login.html, register.html)
- `src/main/resources/application.properties`  -> DB-Verbindung + JPA Einstellungen

## Starten (lokal)
### 1) PostgreSQL mit Docker starten
Falls Container schon existiert:
```bash
docker start shop-postgres
```
### Falls der Container noch nicht existiert:
```bash
docker run --name shop-postgres \
  -e POSTGRES_USER=shop \
  -e POSTGRES_PASSWORD=shop \
  -e POSTGRES_DB=shopdb \
  -p 5432:5432 \
  -d postgres:15
```
### 2) Backend starten
- Projekt in IntelliJ öffnen
- Klasse `BackendApplication` starten
- Spring Boot startet auf Port **8080**

### 3) Anwendung im Browser öffnen
Alle HTML-Seiten werden direkt von Spring Boot ausgeliefert:

- http://localhost:8080/login.html
- http://localhost:8080/register.html
- http://localhost:8080/shop.html
- http://localhost:8080/products.html (Admin)
- http://localhost:8080/orders.html (Mitarbeiter)

## 🔐 Hinweis zu Zugangsdaten

Die in der `application.properties` enthaltenen
Datenbank-Zugangsdaten sind Demo-Werte für
lokale Entwicklungszwecke.

In einem Produktivsystem würden sensible Daten
über Umgebungsvariablen oder ein sicheres
Konfigurationsmanagement gesetzt werden.
```bash
git status
git add .
git commit -m "Initial commit – Spring Boot Webshop"
git push
```

### Rollen / Zugriff
- **CUSTOMER**: Shop nutzen, Bestellungen absenden
- **EMPLOYEE**: Bestellungen ansehen
- **ADMIN**: Produkte verwalten

### Hinweis zur Authentifizierung
Login und Registrierung sind als **Demo-Lösung** umgesetzt  
(Session über `localStorage`).  
In echten Projekten würde man **Spring Security, Passwort-Hashing und JWT** verwenden.

### Datenbank / JPA
- Tabellen werden automatisch aus den JPA-Entities erstellt:
  `spring.jpa.hibernate.ddl-auto=update`
- Generierte SQL-Statements sind im Log sichtbar:
  `spring.jpa.show-sql=true`
- Daten können über **DBeaver** eingesehen werden




