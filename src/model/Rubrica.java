package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;


/*
    Gestisce la collezione dei contatti e la loro persistenza
    Ogni persona è salvata in un file separato all'interno della cartella
    <home utente>/Rubrica/informazioni/, con nome Persona0001.txt, Persona0002.txt etc
*/
public class Rubrica {

    
    //cartella in cui vengono salvati i contatti
    private static final String CARTELLA = 
        System.getProperty("user.home") + File.separator + "Rubrica" + File.separator + "informazioni";
    
    private static final String FORMATO_FILE = "Persona%04d.txt";

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


    public boolean rimuoviPersona(Persona p){
        boolean rimosso = contatti.remove(p);
        if(rimosso) salva();
        return rimosso;
    }

    //Persistenza
    public void salva(){
        File cartella = new File(CARTELLA);
        if (!cartella.exists()) {
            if(!cartella.mkdirs()){
                System.err.println("Impossibile creare la cartella: " + CARTELLA);
                return;
            }
        }

        //elimina i file precedenti
        File[] esistenti = cartella.listFiles();
        if(esistenti != null){
            for(File f : esistenti){
                if(f.isFile()) f.delete();
            }
        }

        //crea un file per ogni persona
        int n=1;
        for(Persona p : contatti){
            File file = new File(cartella,String.format(FORMATO_FILE, n));
            scriviPersonaSuFile(p, file);
            n++;
        }
    }

    //scrive una sola persona nel file
    private void scriviPersonaSuFile(Persona p, File file){
        try(PrintStream out = new PrintStream(file)){
            out.println(
                p.getNome()+";"+
                p.getCognome()+";"+
                p.getIndirizzo()+";"+
                p.getTelefono()+";"+
                p.getEta());
            }catch(FileNotFoundException e){
                System.err.println("Impossibile salvare " + file.getAbsolutePath() + " : " + e.getMessage());
            }
    }

    //legge tutti file dalla cartella e li ordina per nome
    private void carica(){
        File cartella = new File(CARTELLA);
        if(!cartella.exists()){
            return;
        }

        File[] files = cartella.listFiles();
        if (files == null) return;
        Arrays.sort(files);
        for(File f : files){
            if(!f.isFile() || !f.getName().endsWith(".txt"))continue;
            Persona p = leggiPersonaDaFile(f);
            if(p != null) contatti.add(p);
        }
    }

    //legge una persona da un file
    private Persona leggiPersonaDaFile(File file){

        try(Scanner sc = new Scanner(file)){
            if(!sc.hasNextLine()) return null;

            String riga = sc.nextLine().trim();
            if(riga.isEmpty()) return null;

            String[] campi = riga.split(";");
            if(campi.length != 5){
                System.err.println("File malformato: " + file.getName());
                return null;
            }

            return new Persona(campi[0],campi[1],campi[2],campi[3],Integer.parseInt(campi[4]));
            
        }catch(NumberFormatException e){
            System.err.println("Età non numerica in " + file.getName());
        }catch(IllegalArgumentException e){
            System.err.println("Dati non validi in " + file.getName() + ": " + e.getMessage());
        }catch(FileNotFoundException e){
            System.err.println("File non trovato: " + file.getName());
        }
        return null;
    }
}
