import model.Rubrica;
import persistence.LoginServiceFile;
import ui.MainFrame;
import ui.LoginDialog;
import ui.LoginService;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(() -> {

            LoginService loginService = new LoginServiceFile();
            LoginDialog loginDialog = new LoginDialog(loginService);
            loginDialog.setVisible(true);

            //una volta autenticato apre il mainframe
            if(loginDialog.isAutenticato()){
                Rubrica rubrica = new Rubrica();
                MainFrame frame = new MainFrame(rubrica);
                frame.setVisible(true);
            }else{
                System.exit(0);
            }
        });
        
    }
}
