package model;

import java.util.Date;

/**
 * Created by jerome on 19/10/2016.
 */
public class Reservation {
    private Date dateReservation;
    private String etat;
    private Usager usager;
    private Oeuvre oeuvre;

    public Reservation(){}

    public Reservation(Date dateReservation, String etat, Usager usager, Oeuvre oeuvre) {
        this.dateReservation = dateReservation;
        this.etat = etat;
        this.oeuvre = oeuvre;
        this.usager = usager;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Oeuvre getOeuvre() {
        return oeuvre;
    }

    public void setOeuvre(Oeuvre oeuvre) {
        this.oeuvre = oeuvre;
    }

    public Usager getUsager() {
        return usager;
    }

    public void setUsager(Usager usager) {
        this.usager = usager;
    }
}
