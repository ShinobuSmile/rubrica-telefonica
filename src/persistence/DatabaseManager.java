package persistence;

import model.Persona;
import model.Rubrica;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;


/*
    Gestisce la persistenza della rubrica su db MySql tramite JDBC.
    le credenziali sono lette dal file .properties
*/
public class DatabaseManager {
    private static final String FILE_CREDENZIALI = "credenziali_database.properties";

    private String url;
    private String user;
    private String password;

    public DatabaseManager(){
        Properties props = new Properties();
        File file = new File(FILE_CREDENZIALI);

        if (!file.exists()) {
            System.err.println("File credenziali non trovato: " + file.getAbsolutePath());
            return;
        }

        try (FileInputStream in = new FileInputStream(file)) {
            props.load(in);
        } catch (IOException e) {
            System.err.println("Impossibile leggere " + FILE_CREDENZIALI + ": " + e.getMessage());
            return;
        }

        this.url = props.getProperty("db.url");
        this.user = props.getProperty("db.user");
        this.password = props.getProperty("db.password");

        if (url == null || user == null || password == null) {
            System.err.println("Credenziali incomplete in " + FILE_CREDENZIALI);
        }
    }

    //apre una connessione al db
    public Connection apriConnessione() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    //salva tutti i contatti della rubrica sul db
    public void salva(Rubrica rubrica){
        try(Connection conn = apriConnessione()){
            conn.setAutoCommit(false);

            try(Statement st = conn.createStatement()){
                st.executeUpdate("DELETE FROM persone");
            }

            String sql = "INSERT INTO persone (nome, cognome, indirizzo, telefono, eta)" + "VALUES (?,?,?,?,?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)){
                for(Persona p : rubrica.getTutti()){
                    ps.setString(1, p.getNome());
                    ps.setString(2, p.getCognome());
                    ps.setString(3, p.getIndirizzo());
                    ps.setString(4, p.getTelefono());
                    ps.setInt(5, p.getEta());
                    ps.executeUpdate();
                }
            }
            conn.commit();
        }catch(SQLException e){
            System.err.println("Errore salvataggio DB: " + e.getMessage());
        }
    }

    //carica tutte le persone dal db, oridnate per id
    public Vector<Persona> carica(){
        Vector<Persona> contatti = new Vector<>();

        String sql = "SELECT nome, cognome, indirizzo, telefono, eta FROM persone ORDER BY id";
        try(Connection conn = apriConnessione();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql)){
                while(rs.next()){
                    Persona p = new Persona(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("indirizzo"),
                        rs.getString("telefono"),
                        rs.getInt("eta")
                    );
                    contatti.add(p);
                }
            }catch(SQLException e){
                System.err.println("Errore caricamento DB:" + e.getMessage());
            }
            return contatti;
    }    
}
