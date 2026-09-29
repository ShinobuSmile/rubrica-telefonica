import model.Rubrica;
import ui.MainFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(() -> {
            Rubrica rubrica = new Rubrica();
            MainFrame frame = new MainFrame(rubrica);
            frame.setVisible(true);
        });
        
    }
}
