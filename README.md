# Rubrica Telefonica

Applicazione desktop in Java per la gestione di una rubrica di contatti, con interfaccia grafica Swing e persistenza su file.

## Funzionalità

- Aggiunta, modifica ed eliminazione di contatti
- Visualizzazione di tutti i contatti in una tabella
- Validazione dei campi (nome, cognome e telefono obbligatori; età tra 0 e 150)
- Persistenza automatica su file di testo
- Interfaccia grafica con finestra principale e finestra di editing

## Requisiti

- Java 17 o superiore (testato con JDK 26)
- Nessuna libreria esterna

## Persistenza

I contatti vengono salvati automaticamente nel file:

```
<home utente>/Rubrica/informazioni.txt
```

Formato di ogni riga:

```
nome;cognome;indirizzo;telefono;eta
```

Esempio:

```
Mario;Rossi;Via Roma 1;3331234567;30
Lucia;Bianchi;Via Verdi 2;3339999999;25
```