package ui;

import model.Persona;
import model.Rubrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame{

    private final Rubrica rubrica;
    private final DefaultTableModel modello;
    private final JTable tabella;


    public MainFrame(Rubrica rubrica){
        super("Rubrica");
        this.rubrica = rubrica;
        this.modello = new DefaultTableModel(
            new String[]{"Nome", "Cognome", "Telefono"},0
        ){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        this.tabella = new JTable(modello);

        costruisciUI();
        sincronizzaModello();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,400);
        setLocationRelativeTo(null);
    }

    private void costruisciUI(){
        tabella.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabella);

        JButton btnNuovo    = new JButton("Nuovo");
        JButton btnModifica = new JButton("Modifica");
        JButton btnElimina  = new JButton("Elimina");

        btnNuovo.addActionListener(e -> onNuovo());
        btnModifica.addActionListener(e -> onModifica());
        btnElimina.addActionListener(e -> onElimina());

        JPanel pulsanti = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pulsanti.add(btnNuovo);
        pulsanti.add(btnModifica);
        pulsanti.add(btnElimina);

        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(pulsanti, BorderLayout.SOUTH);
    }

    private void onNuovo(){
        EditorPersonaDialog dialog = new EditorPersonaDialog(this,null);
        dialog.setVisible(true);

        if(dialog.isSalvato()){
            rubrica.aggiungiPersona(dialog.getPersona());
            sincronizzaModello();
        }
    }

    private void onModifica(){
        int riga = tabella.getSelectedRow();
        if(riga == -1){
            JOptionPane.showMessageDialog(this,
                "Per modificare è necessario prima selezionare una persona.",
                "Nessuna selezione", JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Persona selezionata = rubrica.getTutti().get(riga);
        EditorPersonaDialog dialog = new EditorPersonaDialog(this, selezionata);
        dialog.setVisible(true);

        if(dialog.isSalvato()){
            rubrica.salva();
            sincronizzaModello();
        }
    }

    private void onElimina(){
        int riga = tabella.getSelectedRow();
        if(riga == -1){
            JOptionPane.showMessageDialog(this,
                "Per eliminare è necessario prima selezionare una persona.",
                "Nessuna selezione", JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Persona selezionata = rubrica.getTutti().get(riga);

        int scelta = JOptionPane.showConfirmDialog(this,
            "Eliminare la persona " + selezionata.getNome()
                + " " + selezionata.getCognome() + "?",
            "Conferma eliminazione",
            JOptionPane.YES_NO_OPTION);

        if (scelta == JOptionPane.YES_OPTION) {
            rubrica.rimuoviPersona(selezionata);
            sincronizzaModello();   // ← aggiorna la tabella
        }
    }

    //Sincronizzazione, da chiamare dopo ogni modifica alla rubrica
    private void sincronizzaModello(){
        modello.setRowCount(0);//svuota
        for(Persona p : rubrica.getTutti()){
            modello.addRow(new Object[]{
                p.getNome(),
                p.getCognome(),
                p.getTelefono()
            });
        }
    }

}
