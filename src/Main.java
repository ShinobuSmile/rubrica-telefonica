import model.Rubrica;
import persistence.DatabaseManager;
import persistence.LoginServiceDB;
import ui.MainFrame;
import ui.LoginDialog;
import ui.LoginService;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(() -> {

            DatabaseManager db = new DatabaseManager();

            // 1. Login
            LoginService loginService = new LoginServiceDB(db);
            LoginDialog loginDialog = new LoginDialog(loginService);
            loginDialog.setVisible(true);   // BLOCCA finché non viene chiusa

            // 2. Se autenticato → MainFrame
            if (loginDialog.isAutenticato()) {
                Rubrica rubrica = new Rubrica(db);
                MainFrame frame = new MainFrame(rubrica);
                frame.setVisible(true);
            } else {
                System.exit(0);
            }
        });
        
    }
}
