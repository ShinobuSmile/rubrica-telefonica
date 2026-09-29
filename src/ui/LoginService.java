package ui;

import model.Utente;

public interface LoginService {
    Utente verifica(String username, String password);
}
