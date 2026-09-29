package ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/*
    Metodi di utilità per costruire toolbar e pulsanti con icone.
 
    Le icone vengono caricate dal classpath, dalla cartella "resources".
    Se un'icona non esiste, il pulsante mostra il testo come fallback.
*/
public final class ToolbarHelper {

    private ToolbarHelper() {
        // classe di sole utilità: non deve essere istanziata
    }

    // Crea una toolbar standard (fissa, senza bordo).
    public static JToolBar creaToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        return toolbar;
    }

    //Crea un pulsante per la toolbar.
    public static JButton creaPulsante(String testo, String nomeIcona, String tooltip) {
        ImageIcon icona = caricaIcona(nomeIcona);

        JButton btn = (icona != null) ? new JButton(icona) : new JButton(testo);

        btn.setToolTipText(tooltip);
        btn.setFocusable(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setMargin(new Insets(5, 8, 5, 8));
        return btn;
    }

    //Carica un'icona dal classpath. Restituisce null se non esiste.
    public static ImageIcon caricaIcona(String nomeFile) {
        URL url = ToolbarHelper.class.getResource("/resources/" + nomeFile);
        if (url == null) {
            System.err.println("Icona non trovata: " + nomeFile);
            return null;
        }
        return new ImageIcon(url);
    }
}