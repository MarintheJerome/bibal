package database;

import model.Emprunt;
import model.Exemplaire;
import model.Oeuvre;
import model.Usager;

import java.sql.*;

/**
 * Created by jerome on 26/10/2016.
 */
public class EmpruntDB {
    private Connection connection;
    private ExemplaireDB exemplaireDB;
    private UsagerDB usagerDB;

    public EmpruntDB(){
        connection = Connexion.getStaticConnection();
        exemplaireDB = new ExemplaireDB();
        usagerDB = new UsagerDB();
    }

    public Emprunt findByIds(int idUsager, int idExemplaire) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM Emprunt WHERE idExemplaire = ? AND idUsager = ?");
        preparedStatement.setInt(1, idExemplaire);
        preparedStatement.setInt(2, idUsager);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            Date dateDebut = rs.getDate("dateDebut");
            int duree = rs.getInt("durée");
            Date dateRetourEffective = rs.getDate("dateRetourEffective");
            Exemplaire exemplaire = exemplaireDB.findById(idExemplaire);
            Usager usager =usagerDB.findById(idUsager);

            return new Emprunt(dateDebut, duree, dateRetourEffective, exemplaire, usager);
        }else{
            return null;
        }
    }

    public void insert(int idUsager, int idExemplaire, Date dateDebut, int duree, Date dateRetourEffective) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO Emprunt(idUsager, idExemplaire, dateDebut, durée, dateRetourEffective) " +
                "VALUES(?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, idUsager);
        preparedStatement.setInt(2, idExemplaire);
        preparedStatement.setDate(3, dateDebut);
        preparedStatement.setInt(4, duree);
        preparedStatement.setDate(5, dateRetourEffective);
        preparedStatement.executeUpdate();
    }

    public void update(int idUsager, int idExemplaire, Date dateDebut, int duree, Date dateRetourEffective) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE emprunt SET dateDebut = ?, durée = ?, dateRetourEffective = ? WHERE idUsager = ? AND idExemplaire = ?");
        preparedStatement.setDate(1, dateDebut);
        preparedStatement.setInt(2, duree);
        preparedStatement.setDate(3, dateRetourEffective);
        preparedStatement.setInt(4, idUsager);
        preparedStatement.setInt(5, idExemplaire);
        preparedStatement.executeUpdate();
    }

    public void delete(int idUsager, int idExemplaire) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE from emprunt WHERE idUsager = ? AND idExemplaire = ?");
        preparedStatement.setInt(1, idUsager);
        preparedStatement.setInt(2, idExemplaire);
        preparedStatement.executeUpdate();
    }
}
