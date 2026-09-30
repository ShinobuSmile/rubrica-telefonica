package model;

import persistence.DatabaseManager;

import java.util.Vector;


/*
    Gestisce la collezione dei contatti e la loro persistenza
    la loro persistenza è delegata a DatabaseManager
*/
public class Rubrica {

    private Vector<Persona> contatti;
    private final DatabaseManager db;

    //inizializza la lista vuota e tenta di caricare i file dal disco
    public Rubrica(DatabaseManager db){
        this.db = db;
        this.contatti = db.carica();
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
        db.salva(this);
    }
}
