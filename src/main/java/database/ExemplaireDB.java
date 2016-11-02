package database;

import model.Exemplaire;
import model.Oeuvre;
import model.Usager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by jerome on 26/10/2016.
 */
public class ExemplaireDB {
    private Connection connection;
    private OeuvreDB oeuvreDB;

    public ExemplaireDB(){
        connection = Connexion.getStaticConnection();
        oeuvreDB = new OeuvreDB();
    }

    public Exemplaire findById(int idExemplaire) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM exemplaire WHERE idExemplaire = ?");
        preparedStatement.setInt(1, idExemplaire);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            String etatExemplaire = rs.getString("etatExemplaire");
            String ISBN = rs.getString("ISBN");
            Oeuvre oeuvre = oeuvreDB.findByISBN(ISBN);
            return new Exemplaire(idExemplaire, etatExemplaire, oeuvre);
        }else{
            return null;
        }
    }

    public int getMaxId() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Exemplaire WHERE idExemplaire in (SELECT MAX(idExemplaire) FROM exemplaire GROUP by idExemplaire)");
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        return rs.getInt("idExemplaire");
    }

    public void insert(String etatExemplaire, String ISBN) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO Exemplaire(etatExemplaire, ISBN) VALUES(?, ?)");
        preparedStatement.setString(1, etatExemplaire);
        preparedStatement.setString(2, ISBN);
        preparedStatement.executeUpdate();
    }

    public void update(int idExemplaire, String etatExemplaire, String ISBN) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE Exemplaire SET etatExemplaire = ?, ISBN = ? WHERE idExemplaire = ?");
        preparedStatement.setString(1, etatExemplaire);
        preparedStatement.setString(2, ISBN);
        preparedStatement.setInt(3, idExemplaire);
        preparedStatement.executeUpdate();
    }

    public void delete(int idExemplaire) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE from Exemplaire WHERE idExemplaire = ?");
        preparedStatement.setInt(1, idExemplaire);
        preparedStatement.executeUpdate();
    }

    public void delete(String ISBN) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE from Exemplaire WHERE ISBN = ?");
        preparedStatement.setString(1, ISBN);
        preparedStatement.executeUpdate();
    }

    public ArrayList<Exemplaire> selectAll(String ISBN) throws SQLException {
        ArrayList<Exemplaire> toReturn = new ArrayList<Exemplaire>();
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM exemplaire WHERE ISBN = ?");
        preparedStatement.setString(1, ISBN);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            toReturn.add(this.findById(rs.getInt("idExemplaire")));
        }
        return toReturn;
    }
}
