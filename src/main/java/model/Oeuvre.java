package model;

import java.sql.Date;

/**
 * Created by jerome on 19/10/2016.
 */
public class Oeuvre {

    private String ISBN;
    private String nomOeuvre;
    private String titre;
    private Date dateParution;
    private Date dateEdition;
    private int nbReservation;
    private String resume;
    private int idAuteur;
    private int numero;
    private int periodicite;

    public Oeuvre(){

    }

    public Oeuvre(String ISBN, String nomOeuvre, String titre, Date dateParution, int nbReservation){
        this.ISBN = ISBN;
        this.nomOeuvre = nomOeuvre;
        this.titre = titre;
        this.dateParution = dateParution;
        this.nbReservation = nbReservation;
    }

    // Livre
    public Oeuvre(String ISBN, String nomOeuvre, String titre, Date dateParution, int nbReservation, Date dateEdition, String resume, int idAuteur){
        this.ISBN = ISBN;
        this.nomOeuvre = nomOeuvre;
        this.titre = titre;
        this.dateParution = dateParution;
        this.nbReservation = nbReservation;
        this.dateEdition = dateEdition;
        this.resume = resume;
        this.idAuteur = idAuteur;
    }

    // Magazine
    public Oeuvre(String ISBN, String nomOeuvre, String titre, Date dateParution, int nbReservation, int numero, int periodicite){
        this.ISBN = ISBN;
        this.nomOeuvre = nomOeuvre;
        this.titre = titre;
        this.dateParution = dateParution;
        this.nbReservation = nbReservation;
        this.numero = numero;
        this.periodicite = periodicite;
    }

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

    public int getIdAuteur() {
        return idAuteur;
    }

    public void setIdAuteur(int idAuteur) {
        this.idAuteur = idAuteur;
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

    public Oeuvre identification(String titre){
        return null;
    }
}
