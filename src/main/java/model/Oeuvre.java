package model;

import java.util.Date;

/**
 * Created by jerome on 19/10/2016.
 */
public class Oeuvre {

    private String ISBN;
    private String nomOeuvre;
    private String titre;
    private Date dateParution;

    private int nbReservation;

    public Oeuvre(){}

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getNomOeuvre() {
        return nomOeuvre;
    }

    public void setNomOeuvre(String nomOeuvre) {
        this.nomOeuvre = nomOeuvre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDateParution() {
        return dateParution;
    }

    public void setDateParution(Date dateParution) {
        this.dateParution = dateParution;
    }

    public int getNbReservation() {
        return nbReservation;
    }

    public void setNbReservation(int nbReservation) {
        this.nbReservation = nbReservation;
    }

    public Oeuvre identification(String titre){
        return null;
    }
}
