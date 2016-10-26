package model;

/**
 * Created by jerome on 19/10/2016.
 */
public class Exemplaire {
    private int idExemplaire;
    private String etatExemplaire;
    private Oeuvre oeuvre;

    public Exemplaire(){}

    public Exemplaire(int idExemplaire, String etatExemplaire, Oeuvre oeuvre){
        this.idExemplaire = idExemplaire;
        this.etatExemplaire = etatExemplaire;
        this.oeuvre = oeuvre;
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

    public Oeuvre getOeuvre() {
        return oeuvre;
    }

    public void setOeuvre(Oeuvre oeuvre) {
        this.oeuvre = oeuvre;
    }
}
