# Rubrica Telefonica — versione con database MySQL

Applicazione desktop in Java per la gestione di una rubrica di contatti, con login utente, interfaccia grafica Swing e persistenza su database MySQL.

> ⚠️ **Nota**: questo è il branch `feature/persistenza-sql`. Il branch `main` contiene la versione con persistenza su file di testo.

## Funzionalità

- **Login utente** con credenziali salvate su database
- **Gestione contatti**: aggiunta, modifica, eliminazione
- **Visualizzazione** di tutti i contatti in una tabella
- **Barra degli strumenti** con icone per le azioni principali
- **Validazione** dei campi (nome, cognome e telefono obbligatori; età tra 0 e 150)
- **Persistenza** su database MySQL tramite JDBC

## Requisiti

- **Java 17** o superiore (testato con JDK 26)
- **MySQL Server 8.0** o superiore
- **MySQL Connector/J** (driver JDBC) — incluso nella cartella `lib/`

## Configurazione del database

### 1. Installare MySQL

Scaricare e installare **MySQL Community Server 8.0** da:
https://dev.mysql.com/downloads/installer/

Durante l'installazione:
- Scegliere **"Developer Default"** o **"Custom"** (includendo Server + Workbench + Connector/J)
- Impostare una password per l'utente `root` (annotarla)
- Lasciare la porta di default `3306`
- Configurare MySQL come servizio Windows

### 2. Creare il database

Aprire **MySQL Workbench** e connettersi al server locale. Poi:

- `File → Open SQL Script...`
- Selezionare `schema_database.sql`
- Cliccare il fulmine ⚡ per eseguire

Oppure da riga di comando:

```bash
mysql -u root -p < schema_database.sql
```

Lo script crea:
- Il database `rubrica`
- La tabella `persone` (contatti)
- La tabella `utenti` (credenziali di login)
- L'utente di default `admin` / `admin`

### 3. Configurare le credenziali

Aprire `credenziali_database.properties` e sostituire i valori con le proprie credenziali MySQL:

```properties
db.url=jdbc:mysql://localhost:3306/rubrica
db.user=root
db.password=lamiapassword
```

**Nota**: questo file **non** deve essere committato su Git (è nel `.gitignore`).