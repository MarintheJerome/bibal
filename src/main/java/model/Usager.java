package model;

/**
 * Created by jerome on 19/10/2016.
 */
public class Usager {
    private int idUsager;
    private String nom;
    private String prenom;
    private String mail;
    private String adresse;

    public Usager(){}

    public Usager(int idUsager, String nom, String prenom, String mail, String adresse) {
        this.idUsager = idUsager;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.adresse = adresse;
    }

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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    // Changer les params ???
    public Usager identification(String nom, String prenom){
        return null;
    }
}
