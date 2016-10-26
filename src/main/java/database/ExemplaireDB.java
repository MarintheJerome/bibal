package database;

import model.Exemplaire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jerome on 26/10/2016.
 */
public class ExemplaireDB {
    private Connection connection;

    public ExemplaireDB(){
        connection = Connexion.getStaticConnection();
    }

    public Exemplaire findById(int idExemplaire) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM exemplaire WHERE idExemplaire = ?");
        preparedStatement.setInt(1, idExemplaire);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            String etatExemplaire = rs.getString("etatExemplaire");
            String ISBN = rs.getString("ISBN");
            return new Exemplaire(idExemplaire, etatExemplaire, ISBN);
        }else{
            return null;
        }
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
}
