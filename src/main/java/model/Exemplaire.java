package model;

/**
 * Created by jerome on 19/10/2016.
 */
public class Exemplaire {
    private int idExemplaire;
    private String etatExemplaire;
    private String ISBN;

    public Exemplaire(){}

    public Exemplaire(int idExemplaire, String etatExemplaire, String ISBN){
        this.idExemplaire = idExemplaire;
        this.etatExemplaire = etatExemplaire;
        this.ISBN = ISBN;
    }

    public int getIdExemplaire() {
        return idExemplaire;
    }

    public void setIdExemplaire(int idExemplaire) {
        this.idExemplaire = idExemplaire;
    }

    public String getEtatExemplaire() {
        return etatExemplaire;
    }

    public void setEtatExemplaire(String etatExemplaire) {
        this.etatExemplaire = etatExemplaire;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
