package model;

import java.util.Date;

/**
 * Created by jerome on 19/10/2016.
 */
public class Emprunt {
    private Date dateDebut;
    private int duree;
    private Date dateRetourPrevue;
    private Date dateRetourEffective;
    private Exemplaire exemplaire;
    private Usager usager;

    public Emprunt(){}

    public Emprunt(Date dateDebut, int duree, Date dateRetourPrevue, Date dateRetourEffective, Exemplaire exemplaire, Usager usager) {
        this.dateDebut = dateDebut;
        this.duree = duree;
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourEffective = dateRetourEffective;
        this.exemplaire = exemplaire;
        this.usager = usager;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Date getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(Date dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public Date getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setDateRetourEffective(Date dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }

    public Usager getUsager() {
        return usager;
    }

    public void setUsager(Usager usager) {
        this.usager = usager;
    }
}
