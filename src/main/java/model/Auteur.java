package model;

/**
 * Created by jerome on 19/10/2016.
 */
public class Auteur {
    private int id;
    private String nom;
    private String prenom;

    public Auteur(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
