package model;

import java.util.Date;

/**
 * Created by jerome on 19/10/2016.
 */
public class Magazine extends Oeuvre{

    private int numero;
    private int periodicite;


    public Magazine() {
        super();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getPeriodicite() {
        return periodicite;
    }

    public void setPeriodicite(int periodicite) {
        this.periodicite = periodicite;
    }
}
