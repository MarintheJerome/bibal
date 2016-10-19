package database;

import model.Auteur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jerome on 19/10/2016.
 */
public class AuteurDB {

    private Connection connection;

    public void setConnection(){
        this.connection = (Connection) Connexion.getInstance();
    }

    public Auteur findById(int idAuteur) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM auteur WHERE idAuteur= ?");
        preparedStatement.setInt(1, idAuteur);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        String nomAuteur = rs.getString("nomAuteur");
        String prenomAuteur = rs.getString("prenomAuteur");
        return new Auteur(idAuteur, nomAuteur, prenomAuteur);
    }

    public void insert(int idAuteur, String nomAuteur, String prenomAuteur) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO auteur(idAuteur, nomAuteur, prenomAuteur) VALUES(?, ?, ?)");
        preparedStatement.setInt(1, idAuteur);
        preparedStatement.setString(2, nomAuteur);
        preparedStatement.setString(3, prenomAuteur);
        preparedStatement.executeUpdate();
    }

    public void update(int idAuteur, String nomAuteur, String prenomAuteur) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE auteur SET nomAuteur = ?, prenomAuteur = ? WHERE idAuteur = ?");
        preparedStatement.setString(1, nomAuteur);
        preparedStatement.setString(2, prenomAuteur);
        preparedStatement.setInt(3, idAuteur);
        preparedStatement.executeUpdate();
    }

    public void delete(int idAuteur) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE from auteur WHERE idAuteur = ?");
        preparedStatement.setInt(1, idAuteur);
        preparedStatement.executeUpdate();
    }
}
