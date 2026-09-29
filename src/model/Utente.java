package model;


/*
    Rappresenta un utente del software con credenziali di accesso
*/
public class Utente {
    private String username;
    private String password;

    public Utente(String username, String password){
        setUsername(username);
        setPassword(password);
    }

    public void setUsername(String username){
        if(username == null || username.trim().isEmpty()){
            throw new IllegalArgumentException("Username non può essere vuoto");
        }
        this.username = username.trim();
    }

    public void setPassword(String password){
        if(password == null || username.isEmpty()){
            throw new IllegalArgumentException("Password non può essere vuota");
        }
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    @Override 
    public String toString(){
        return username;
    }
}
