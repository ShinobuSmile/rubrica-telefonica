# Rubrica Telefonica

Applicazione desktop in Java per la gestione di una rubrica di contatti, con interfaccia grafica Swing e persistenza su file.

## Funzionalità

- **Login utente** con credenziali salvate su file
- **Gestione contatti**: aggiunta, modifica, eliminazione
- **Visualizzazione** di tutti i contatti in una tabella
- **Barra degli strumenti** con icone per le azioni principali
- **Validazione** dei campi (nome, cognome e telefono obbligatori; età tra 0 e 150)
- **Persistenza automatica** su file separati, uno per contatto
- **Utente di default** creato al primo avvio (credenziali admin/admin)

## Requisiti

- Java 17 o superiore (testato con JDK 26)
- Nessuna libreria esterna

## Dati di un contatto

| Campo      | Tipo   | Obbligatorio |
|------------|--------|--------------|
| Nome       | String | Sì           |
| Cognome    | String | Sì           |
| Indirizzo  | String | No           |
| Telefono   | String | Sì           |
| Età        | int    | Sì (0-150)   |

## Login

Al primo avvio, il programma crea automaticamente il file delle credenziali con un utente di default:

```
username: admin
password: admin
```

**Si consiglia di modificare la password** aprendo il file `utenti.txt` (vedi sezione Persistenza) e sostituendo la riga con le proprie credenziali.

### Aggiungere altri utenti

Aprire il file `utenti.txt` e aggiungere una riga per ogni utente, nel formato:

```
username;password
```

## Persistenza

L'applicazione salva i dati in due punti distinti, dentro la cartella home dell'utente:

```
<home utente>/Rubrica/
├── informazioni/                    ← cartella dei contatti
│   ├── Persona0001.txt
│   ├── Persona0002.txt
│   └── ...
└── utenti.txt                       ← credenziali di login
```

Su Windows: `C:\Users\<nome>\Rubrica\`  
Su Linux/Mac: `/home/<nome>/Rubrica/`

## Compilazione ed esecuzione

### Da IDE (VS Code, Eclipse, IntelliJ)

Aprire il progetto ed eseguire `Main.java`.

### Da terminale

```bash
# 1. Compila i sorgenti
javac -d out -encoding UTF-8 src/Main.java src/model/*.java src/ui/*.java src/persistence/*.java

# 2. Copia le risorse (icone) nella cartella di build
cp -r src/resources out/resources
# Su Windows PowerShell:
# Copy-Item -Recurse -Force src/resources out/resources

# 3. Crea il JAR eseguibile
jar cfm Rubrica.jar manifest.txt -C out .

# 4. Esegui
java -jar Rubrica.jar
```

Il file `manifest.txt` deve contenere:

```
Main-Class: Main
```