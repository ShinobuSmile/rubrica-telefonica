package ui;

import model.Persona;

import javax.swing.*;
import java.awt.*;


/*
    Finestra di dialogo per l'inserimento o la modifica di una persona.
*/
public class EditorPersonaDialog extends JDialog{
    private JTextField txtNome;
    private JTextField txtCognome;
    private JTextField txtIndirizzo;
    private JTextField txtTelefono;
    private JTextField txtEta;

    //La persona da modificare, null se stiamo creando una nuova persona
    private Persona persona;

    //true se l'utente ha premuto salva e i dati sono stati applicati
    private boolean salvato = false;

    public EditorPersonaDialog(JFrame parent, Persona persona) {
        super(parent, persona == null ? "Nuova persona" : "Modifica persona", true);
        this.persona = persona;

        costruisciUI();

        if(persona != null){
            popolaCampi(persona);
        }
        pack();                         //dimensiona la finestra al contenuto
        setLocationRelativeTo(parent);  //centra rispetto alla finestra madre
    }

    //Crea e dispone i componenti del form e i pulsanti
    private void costruisciUI() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        txtNome      = new JTextField(20);
        txtCognome   = new JTextField(20);
        txtIndirizzo = new JTextField(20);
        txtTelefono  = new JTextField(20);
        txtEta       = new JTextField(20);

        aggiungiRiga(form, gbc, 0, "Nome:", txtNome);
        aggiungiRiga(form, gbc, 1, "Cognome:", txtCognome);
        aggiungiRiga(form, gbc, 2, "Indirizzo:", txtIndirizzo);
        aggiungiRiga(form, gbc, 3, "Telefono:", txtTelefono);
        aggiungiRiga(form, gbc, 4, "Età:", txtEta);

        JButton btnSalva    = new JButton("Salva");
        JButton btnAnnulla  = new JButton("Annulla");

        btnSalva.addActionListener(e -> onSalva());
        btnAnnulla.addActionListener(e -> onAnnulla());

        getRootPane().setDefaultButton(btnSalva);

        JPanel pulsanti = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pulsanti.add(btnAnnulla);
        pulsanti.add(btnSalva);

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(pulsanti, BorderLayout.SOUTH);
    }

    /*
        Aggiunge una coppia(label,campo) a una riga del form
        Riusa lo stesso GridBagConstraints modificandone gridx/gridy
    */
    private void aggiungiRiga(JPanel panel, GridBagConstraints gbc, int riga, String etichetta, JTextField campo) {
        gbc.gridx = 0;
        gbc.gridy = riga;
        panel.add(new JLabel(etichetta), gbc);

        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    //Riempie i campi con i dati della persona da modificare
    private void popolaCampi(Persona p){
        txtNome.setText(p.getNome());
        txtCognome.setText(p.getCognome());
        txtIndirizzo.setText(p.getIndirizzo());
        txtTelefono.setText(p.getTelefono());
        txtEta.setText(String.valueOf(p.getEta()));
    }

    /*
        legge i campi, li valida tramite setter/costruttore di persona e chiude la finestra se tutto è corretto

        in caso di errore mostra JOptionPane e lascia la dialog aperta:
        l'utente può correggere senza perdere i dati inseriti
    */
    private void onSalva() {
        try {
            // Leggi i valori dai campi
            String nome      = txtNome.getText().trim();
            String cognome   = txtCognome.getText().trim();
            String indirizzo = txtIndirizzo.getText().trim();
            String telefono  = txtTelefono.getText().trim();
            int eta          = Integer.parseInt(txtEta.getText().trim());

            if (persona == null) {
                // INSERIMENTO: crea una nuova Persona
                persona = new Persona(nome, cognome, indirizzo, telefono, eta);
            } else {
                // MODIFICA: aggiorna i campi della persona esistente
                persona.setNome(nome);
                persona.setCognome(cognome);
                persona.setIndirizzo(indirizzo);
                persona.setTelefono(telefono);
                persona.setEta(eta);
            }

            salvato = true;
            dispose();   // chiude la dialog

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "L'età deve essere un numero intero.",
                "Errore di input",
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "Errore di validazione",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    //chiude la finestra senza applicare modifiche
    private void onAnnulla() {
        salvato = false;
        dispose();
    }

    public boolean isSalvato() {
        return salvato;
    }
    
    public Persona getPersona() {
        return persona;
    }
}
