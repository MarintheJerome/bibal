package database;

import model.Auteur;
import model.Oeuvre;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by jerome on 21/10/2016.
 */
public class OeuvreDB {

    private Connection connection;
    private AuteurDB auteurDB;

    public OeuvreDB(){
        connection = Connexion.getStaticConnection();
        auteurDB = new AuteurDB();
    }

    public Oeuvre findByISBN(String ISBN) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM Oeuvre WHERE ISBN = ?");
        preparedStatement.setString(1, ISBN);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            String nomOeuvre = rs.getString("nomOeuvre");
            String titre = rs.getString("titre");
            Date  dateParution = rs.getDate("dateParution");
            int nbReservation = rs.getInt("nbReservation");

            // check si c'est un livre
            Date dateEdition = rs.getDate("dateEdition");
            String resume = rs.getString("resume");
            Integer idAuteurInteger = (Integer) rs.getObject("idAuteur");
            int idAuteur = 0;
            if(idAuteurInteger != null){
                idAuteur = idAuteurInteger;
            }

            // check si c'est un magazine
            Integer numeroInteger = (Integer) rs.getObject("numero");
            int numero = 0;
            if(numeroInteger != null){
                numero = numeroInteger;
            }
            String periodicite = rs.getString("periodicite");
            // c'est un livre
            if((dateEdition != null || resume != null) && idAuteur != 0){
                Auteur auteur = auteurDB.findById(idAuteur);
                return new Oeuvre(ISBN, nomOeuvre, titre, dateParution, nbReservation, dateEdition, resume, auteur);
            }

            // c'est un magazine
            if(numero !=  0 || periodicite != null){
                return new Oeuvre(ISBN, nomOeuvre, titre, dateParution, nbReservation, numero, periodicite);
            }

            // ni livre ni magazine
            return new Oeuvre(ISBN, nomOeuvre, titre, dateParution, nbReservation, "Non précisé");
        }else{
            return null;
        }
    }

    public String getISBNFromOeuvre(String nomOeuvre, String titre, Date dateParution, int nbReservation, Date dateEdition, String resume, Integer idAuteur, Integer numero, Integer periodicite) throws SQLException {
        String requete = "SELECT ISBN FROM Oeuvre " + "WHERE nomOeuvre = ? AND titre = ? AND dateParution = ? AND nbReservation = ?";
        if(dateEdition != null){
            requete += " AND dateEdition = ?";
        }
        if(resume != null){
            requete += " AND resume = ?";
        }
        if(idAuteur != null){
            requete += " AND idAuteur = ?";
        }
        if(numero != null){
            requete += " AND numero = ?";
        }
        if(periodicite != null){
            requete += " AND periodicite = ?";
        }
        PreparedStatement preparedStatement = this.connection.prepareStatement(requete);
        preparedStatement.setString(1, nomOeuvre);
        preparedStatement.setString(2, titre);
        preparedStatement.setDate(3, dateParution);
        preparedStatement.setInt(4, nbReservation);
        if(dateEdition != null){
            preparedStatement.setDate(5, dateEdition);
        }
        if(resume != null){
            preparedStatement.setString(6, resume);
        }
        if(idAuteur != null){
            preparedStatement.setInt(7, idAuteur);
        }
        if(numero != null){
            preparedStatement.setInt(8, numero);
        }
        if(periodicite != null){
            preparedStatement.setInt(9, periodicite);
        }
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        return rs.getString("ISBN");
    }

    public void insert(String ISBN, String nomOeuvre, String titre, Date dateParution, int nbReservation) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO Oeuvre(ISBN, nomOeuvre, titre, dateParution, nbReservation) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, ISBN);
        preparedStatement.setString(2, nomOeuvre);
        preparedStatement.setString(3, titre);
        preparedStatement.setDate(4, dateParution);
        preparedStatement.setInt(5, nbReservation);
        preparedStatement.executeUpdate();
    }

    public void insertLivre(String ISBN, String nomOeuvre, String titre, Date dateParution, int nbReservation, Date dateEdition, String resume, Integer idAuteur) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO Oeuvre(ISBN, nomOeuvre, titre, dateParution, nbReservation, dateEdition, resume, idAuteur) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, ISBN);
        preparedStatement.setString(2, nomOeuvre);
        preparedStatement.setString(3, titre);
        preparedStatement.setDate(4, dateParution);
        preparedStatement.setInt(5, nbReservation);
        preparedStatement.setDate(6, dateEdition);
        preparedStatement.setString(7, resume);
        preparedStatement.setInt(8, idAuteur);
        preparedStatement.executeUpdate();
    }

    public void insertMagasine(String ISBN, String nomOeuvre, String titre, Date dateParution, int nbReservation, Integer numero, String periodicite) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO Oeuvre(ISBN, nomOeuvre, titre, dateParution, nbReservation, numero, periodicite) VALUES (?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, ISBN);
        preparedStatement.setString(2, nomOeuvre);
        preparedStatement.setString(3, titre);
        preparedStatement.setDate(4, dateParution);
        preparedStatement.setInt(5, nbReservation);
        preparedStatement.setInt(6, numero);
        preparedStatement.setString(7, periodicite);
        preparedStatement.executeUpdate();
    }

    public void insertAll(String ISBN, String nomOeuvre, String titre, Date dateParution, int nbReservation, Date dateEdition, String resume, Integer idAuteur, Integer numero, String periodicite) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO Oeuvre(ISBN, nomOeuvre, titre, dateParution, nbReservation, dateEdition, resume, numero, periodicite, idAuteur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, ISBN);
        preparedStatement.setString(2, nomOeuvre);
        preparedStatement.setString(3, titre);
        preparedStatement.setDate(4, dateParution);
        preparedStatement.setInt(5, nbReservation);
        preparedStatement.setDate(6, dateEdition);
        preparedStatement.setString(7, resume);
        preparedStatement.setInt(8, idAuteur);
        preparedStatement.setInt(9, numero);
        preparedStatement.setString(10, periodicite);
        preparedStatement.executeUpdate();
    }

    public void update(String ISBN, String nomOeuvre, String titre, Date dateParution, int nbReservation, Date dateEdition, String resume, Integer idAuteur, Integer numero, String periodicite) throws SQLException{
        String requete = "UPDATE Oeuvre SET nomOeuvre = ?, titre = ?, dateParution = ?, nbReservation = ?";
        if(dateEdition != null){
            requete += ", dateEdition = ?";
        }
        if(resume != null){
            requete += ", resume = ?";
        }
        if(idAuteur != null){
            requete += ", idAuteur = ?";
        }
        if(numero != null){
            requete += ", numero = ?";
        }
        if(periodicite != null){
            requete += ", periodicite = ?";
        }
        requete += " WHERE ISBN = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(requete);
        preparedStatement.setString(1, nomOeuvre);
        preparedStatement.setString(2, titre);
        preparedStatement.setDate(3, dateParution);
        preparedStatement.setInt(4, nbReservation);
        int compteur = 0;
        if(dateEdition != null){
            compteur++;
            preparedStatement.setDate(5, dateEdition);
        }
        if(resume != null){
            compteur++;
            preparedStatement.setString(6, resume);
        }
        if(idAuteur != null){
            compteur++;
            preparedStatement.setInt(7, idAuteur);
        }
        if(numero != null){
            compteur++;
            preparedStatement.setInt(8, numero);
        }
        if(periodicite != null){
            compteur++;
            preparedStatement.setString(9, periodicite);
        }
        preparedStatement.setString(5+compteur, ISBN);
        preparedStatement.executeUpdate();
    }

    public void delete(String ISBN) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM Oeuvre WHERE ISBN = ?");
        preparedStatement.setString(1, ISBN);
        preparedStatement.executeUpdate();
    }

    public ArrayList<Oeuvre> selectAll() throws SQLException {
        ArrayList<Oeuvre> toReturn = new ArrayList<Oeuvre>();
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM oeuvre");
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            toReturn.add(this.findByISBN(rs.getString("ISBN")));
        }
        return toReturn;
    }
}
