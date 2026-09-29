package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Vector;


/*
    Gestisce la collezione dei contatti e la loro persistenza
    I contatti vengono salvati automaticamente nel file:<home utente>/Rubrica/informazioni.txt
*/
public class Rubrica {

    //dove vengono salvati i contatti
    private static final String NOME_FILE =
    System.getProperty("user.home") + File.separator + "Rubrica" + File.separator + "informazioni.txt";

    private Vector<Persona> contatti;

    //inizializza la lista vuota e tenta di caricare i file dal disco
    public Rubrica(){
        this.contatti = new Vector<>();
        carica();
    }

    //Create
    public void aggiungiPersona(Persona p){
        if(p == null) throw new IllegalArgumentException("Persona nulla!");
        contatti.add(p);
        salva();
    }

    //Read
    public Vector<Persona> getTutti(){
        return contatti;
    }

    public int size() {
        return contatti.size();
    }

    //Update
    public void modificaPersona(Persona p){
        if (p == null || !contatti.contains(p)){
            throw new IllegalArgumentException("Persona non presente in rubrica");
        }
    }

    public boolean rimuoviPersona(Persona p){
        boolean rimosso = contatti.remove(p);
        if(rimosso) salva();
        return rimosso;
    }

    //Persistenza
    public void salva(){
        File file = new File(NOME_FILE);
        File cartella = file.getParentFile();
        if (cartella != null && !cartella.exists()) {
            cartella.mkdirs();
        }
        //formato con cui vengono salvate le persone
        try(PrintStream out = new PrintStream(NOME_FILE)){
            for(Persona p : contatti){
                out.println(
                    p.getNome()+";"+
                    p.getCognome()+";"+
                    p.getIndirizzo()+";"+
                    p.getTelefono()+";"+
                    p.getEta()
                );
            }
        }catch(FileNotFoundException e){
            System.err.println("Impossibile salvare la rubrica: " + e.getMessage());
        }
    }

    //legge tutte le persone nel file
    public void carica(){
        File file = new File(NOME_FILE);
        if(!file.exists()){
            return;
        }

        try(Scanner sc = new Scanner(file)){
            while(sc.hasNextLine()){
                String riga = sc.nextLine().trim();
                if(riga.isEmpty()) continue;

                String[] campi = riga.split(";");
                if(campi.length != 5){
                    System.err.println("Riga di dimensione sbagliata" + riga);
                    continue;
                }

                try{
                    String nome= campi[0];
                    String cognome= campi[1];
                    String indirizzo= campi[2];
                    String telefono= campi[3];
                    int eta = Integer.parseInt(campi[4]);

                    Persona p = new Persona(nome, cognome, indirizzo, telefono, eta);
                    contatti.add(p);
                }catch(IllegalArgumentException e){
                    System.err.println("Riga non valida ignorata" + riga + " ("+ e.getMessage()+")");
                }
            }
        }catch(FileNotFoundException e){
            System.err.println("File non trovato: " + e.getMessage());
        }
    }

}
