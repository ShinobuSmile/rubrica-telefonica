package model;


/*
    Rappresenta un contatto nella rubrica.
    ogni persona è identificata univocamente dal proprio riferimento in memoria
*/
public class Persona {
    private String nome;
    private String cognome;
    private String indirizzo;
    private String telefono;
    private int eta;

    /*
        Crea una persona valida. Ogni campo passa attraverso il proprio setter
        cha applica le regole di validazione
    */
    public Persona(String nome, String cognome, String indirizzo, String telefono, int eta){
        setNome(nome);
        setCognome(cognome);
        setIndirizzo(indirizzo);
        setTelefono(telefono);
        setEta(eta);
    }

    //Getter & Setter:
    public String getNome(){return nome;}

    public String getCognome(){return cognome;}

    public String getIndirizzo(){return indirizzo;}

    public String getTelefono(){return telefono;}

    public int getEta(){return eta;}

    public void setNome(String nome){
        this.nome = validaStringa(nome, "Nome");
    }

    public void setCognome(String cognome){
        this.cognome = validaStringa(cognome, "Cognome");
    }

    /*
        l'indirizzo è opzionale, se null diventa stringa vuota
    */
    public void setIndirizzo(String indirizzo){
        this.indirizzo = (indirizzo == null) ? "" : indirizzo.trim();
    }

    /*
        ammessi: cifre, '+', spazi e trattini, lunghezza 5-20 caratteri.
    */
    public void setTelefono(String telefono){
        String t = validaStringa(telefono, "Telefono");
        if (!t.matches("[+0-9\\-\\s]{5,20}")){
            throw new IllegalArgumentException("Telefono non valido, ammessi solo cifre, numeri, spazi e trattini, 5-20 caratteri");
        }
        this.telefono = t;
    }

    public void setEta(int eta){
        if (eta < 0 || eta > 150){
            throw new IllegalArgumentException("Età deve essere compresa tra 0 e 150");
        }
        this.eta = eta;
    }

    //Verifica che una stringa non sia null o vuota (dopo trim) e la restituisce ripulita.
    private static String validaStringa(String valore, String campo){
        if (valore == null || valore.trim().isEmpty()){
            throw new IllegalArgumentException(campo + " non può essere vuoto!");
        }
        return valore.trim();
    }

    //override del toString
    @Override
    public String toString(){
        return "Nome: " + nome + ", Cognome: " + cognome + ", Indirizzo: " + indirizzo + ", Telefono: " + telefono + ", Età: " + eta;
    }
}
