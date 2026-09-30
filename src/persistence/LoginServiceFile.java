package persistence;

import model.Utente;
import ui.LoginService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintStream;


/*
    verifica le credenziali leggendo da un file di testo
    formato del file: "username;password"
    il file si trova in Il file si trova in <home utente>/Rubrica/utenti.txt.

    al primo avvio se il file non esiste viene creato automaticamente con utente di default admin/admin
*/
public class LoginServiceFile implements LoginService{
    
    private static final String CARTELLA = System.getProperty("user.home")+ File.separator + "Rubrica";

    private static final String NOME_FILE = CARTELLA + File.separator + "utenti.txt";
    
    //crea file utente di default se non esiste
    public LoginServiceFile(){
        File file = new File(NOME_FILE);
        if(file.exists()) return;

        File cartella = file.getParentFile();
        if (cartella != null && !cartella.exists()) {
            cartella.mkdirs();
        }

        // Crea il file con l'utente di default
        try (PrintStream out = new PrintStream(file)) {
            out.println("admin" + ";" + "admin");
            System.out.println("Creato file utenti di default: " + file.getAbsolutePath());
            System.out.println("Utente di default: " + "admin"
                + " / " + "admin");
        } catch (FileNotFoundException e) {
            System.err.println("Impossibile creare " + NOME_FILE + ": " + e.getMessage());
        }
    }
    
    @Override 
    public Utente verifica(String username, String password){
        File file = new File(NOME_FILE);

        if(!file.exists()){
            System.err.println("File utenti non trovato: " + NOME_FILE);
            return null;
        }

        try(Scanner sc = new Scanner(file)){
            while(sc.hasNextLine()){
                String riga = sc.nextLine().trim();
                if(riga.isEmpty()) continue;

                String[] campi = riga.split(";");
                if(campi.length != 2){
                    System.err.println("Riga utente malformata ignorata: " + riga);
                    continue;
                }
                
                if(campi[0].equals(username) && campi[1].equals(password)){
                    return new Utente(campi[0], campi[1]);
                }
            }
        }catch(FileNotFoundException e){
            System.err.println("Errore lettura utenti: " + e.getMessage());
        }
        return null;
    }
}
