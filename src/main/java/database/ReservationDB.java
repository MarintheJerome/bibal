package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by jerome on 26/10/2016.
 */
public class ReservationDB {
    private Connection connection;
    private OeuvreDB oeuvreDB;
    private UsagerDB usagerDB;

    public ReservationDB(){
        connection = Connexion.getStaticConnection();
        oeuvreDB = new OeuvreDB();
        usagerDB = new UsagerDB();
    }

    public Reservation findByIds(int idUsager, String ISBN) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM Reservation WHERE idUsager = ? AND ISBN = ?");
        preparedStatement.setInt(1, idUsager);
        preparedStatement.setString(2, ISBN);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            Date dateReservation = rs.getDate("dateReservation");
            String etatReservation = rs.getString("etatReservation");
            Usager usager = usagerDB.findById(idUsager);
            Oeuvre oeuvre = oeuvreDB.findByISBN(ISBN);

            return new Reservation(dateReservation, etatReservation, usager, oeuvre);
        }else{
            return null;
        }
    }

    public void insert(int idUsager, String ISBN, Date dateReservation, String etatReservation) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO Reservation(idUsager, ISBN, dateReservation, etatReservation) " +
                "VALUES(?, ?, ?, ?)");
        preparedStatement.setInt(1, idUsager);
        preparedStatement.setString(2, ISBN);
        preparedStatement.setDate(3, dateReservation);
        preparedStatement.setString(4, etatReservation);
        preparedStatement.executeUpdate();
    }

    public void update(int idUsager, String ISBN, Date dateReservation, String etatReservation) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE Reservation SET dateReservation = ?, etatReservation = ?" +
                " WHERE idUsager = ? AND ISBN = ?");
        preparedStatement.setDate(1, dateReservation);
        preparedStatement.setString(2, etatReservation);
        preparedStatement.setInt(3, idUsager);
        preparedStatement.setString(4, ISBN);
        preparedStatement.executeUpdate();
    }

    public void delete(int idUsager, String ISBN) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE from Reservation WHERE idUsager = ? AND ISBN = ?");
        preparedStatement.setInt(1, idUsager);
        preparedStatement.setString(2, ISBN);
        preparedStatement.executeUpdate();
    }

    public ArrayList<Reservation> selectAll() throws SQLException {
        ArrayList<Reservation> toReturn = new ArrayList<Reservation>();
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM Reservation");
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            toReturn.add(this.findByIds(rs.getInt("idUsager"), rs.getString("ISBN")));
        }
        return toReturn;
    }
}
