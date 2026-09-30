package persistence;

import model.Utente;
import ui.LoginService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/*
    verifica le credenziali tramite la tabella utenti del db 
*/
public class LoginServiceDB implements LoginService{

    private final DatabaseManager db;

    public LoginServiceDB(DatabaseManager db){
        this.db = db;
    }

    @Override
    public Utente verifica(String username, String password) {
        String sql = "SELECT username FROM utenti WHERE username = ? AND password = ?";

        try (Connection conn = db.apriConnessione();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Utente(rs.getString("username"), password);
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore verifica login: " + e.getMessage());
        }

        return null;   //credenziali errate o errore di connessione
    }
}
