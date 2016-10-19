package model;

import java.util.Date;

/**
 * Created by jerome on 19/10/2016.
 */
public class Livre extends Oeuvre{

    private Date dateEdition;
    private String resume;

    private Auteur auteur;

    public Livre() {
        super();
    }

    public Date getDateEdition() {
        return dateEdition;
    }

    public void setDateEdition(Date dateEdition) {
        this.dateEdition = dateEdition;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Auteur getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }
}
