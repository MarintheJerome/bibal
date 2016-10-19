package model;

/**
 * Created by jerome on 19/10/2016.
 */
public class Usager {
    private int idUsager;
    private String nom;
    private String prenom;
    private String mail;
    private Adresse adresse;

    public Usager(){}

    public int getIdUsager() {
        return idUsager;
    }

    public void setIdUsager(int idUsager) {
        this.idUsager = idUsager;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    // Changer les params ???
    public Usager identification(String nom, String prenom){
        return null;
    }
}
