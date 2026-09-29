package ui;

import model.Utente;

import javax.swing.*;
import java.awt.*;

/*
    Finestra di login mostrata all'avvio dell'applicazione.
    è un JDialog modale: quando viene mostrato l'esecuzione del chiamante si blocca finchè non viene chiuso

    al termine:
    isAutenticato() -> true se il login è andato a buon fine
    getUtente() -> utente autenticato o null
 */
public class LoginDialog extends JDialog{

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private boolean autenticato = false;
    private Utente utente;

    private final LoginService loginService;

    public LoginDialog(LoginService loginService){
        super((JFrame) null, "Login",true);
        this.loginService = loginService;

        costruisciUI();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void costruisciUI() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Utente:"), gbc);
        gbc.gridx = 1;
        form.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        form.add(txtPassword, gbc);

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.addActionListener(e -> onLogin());
        getRootPane().setDefaultButton(btnLogin);

        JPanel pulsante = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pulsante.add(btnLogin);

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(pulsante, BorderLayout.SOUTH);
    }

    private void onLogin(){
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if(username.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this,
                "Inserisci username e password",
                "Campi vuoti",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Utente u = loginService.verifica(username, password);
        if(u==null){
            JOptionPane.showMessageDialog(this,
                "Login errato.",
                "Errore di autenticazione",
                JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            return;
        }

        this.utente = u;
        this.autenticato = true;
        dispose();
    }

    public boolean isAutenticato(){
        return autenticato;
    }

    public Utente getUtente(){
        return utente;
    }

}
