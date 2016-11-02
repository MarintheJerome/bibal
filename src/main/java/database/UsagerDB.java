package database;

import model.Oeuvre;
import model.Usager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by jerome on 19/10/2016.
 */
public class UsagerDB {

    private Connection connection;

    public UsagerDB(){
        this.connection = Connexion.getStaticConnection();
    }

    public Usager findById(int idUsager) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM usager WHERE idUsager= ?");
        preparedStatement.setInt(1, idUsager);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            String nom = rs.getString("nomUsager");
            String prenom = rs.getString("prenomUsager");
            String mail = rs.getString("Mail");
            String adresse = rs.getString("Adresse");
            return new Usager(idUsager, nom, prenom, mail, adresse);
        }else{
            return null;
        }
    }

    public int getIdFromUsager(String nomUsager, String prenomUsager, String mail, String adresse) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT idUsager FROM usager WHERE nomUsager = ? AND prenomUsager = ? AND mail = ? and adresse = ?");
        preparedStatement.setString(1, nomUsager);
        preparedStatement.setString(2, prenomUsager);
        preparedStatement.setString(3, mail);
        preparedStatement.setString(4, adresse);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        return rs.getInt("idUsager");
    }

    public void insert(String nomUsager, String prenomUsager, String mail, String adresse) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO usager(nomUsager, prenomUsager, Mail, Adresse) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, nomUsager);
        preparedStatement.setString(2, prenomUsager);
        preparedStatement.setString(3, mail);
        preparedStatement.setString(4, adresse);
        preparedStatement.executeUpdate();
    }

    public void update(int id, String nomUsager, String prenomUsager, String mail, String adresse) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE usager SET nomUsager = ?, prenomUsager = ?, Mail = ?, Adresse = ? WHERE idUsager=?");
        preparedStatement.setString(1, nomUsager);
        preparedStatement.setString(2, prenomUsager);
        preparedStatement.setString(3, mail);
        preparedStatement.setString(4, adresse);
        preparedStatement.setInt(5, id);
        preparedStatement.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM usager WHERE idUsager = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public ArrayList<Usager> selectAll() throws SQLException{
        ArrayList<Usager> toReturn = new ArrayList<Usager>();
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM usager");
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            toReturn.add(this.findById(rs.getInt("idUsager")));
        }
        return toReturn;
    }
}