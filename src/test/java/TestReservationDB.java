import database.*;
import model.Emprunt;
import model.Reservation;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by jerome on 26/10/2016.
 */
public class TestReservationDB {
    private static Connection connection;
    private static OeuvreDB oeuvreDB;
    private static AuteurDB auteurDB;
    private static UsagerDB usagerDB;
    private static ReservationDB reservationDB;

    private int idAuteur;
    private String ISBN;
    private int idUsager;

    private static void delete() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Reservation");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("DELETE FROM Usager");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("DELETE FROM Oeuvre");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("DELETE FROM Auteur");
        preparedStatement.executeUpdate();
    }

    @BeforeClass
    public static void init(){
        connection = Connexion.getStaticConnection();
        oeuvreDB = new OeuvreDB();
        auteurDB = new AuteurDB();
        usagerDB = new UsagerDB();
        reservationDB = new ReservationDB();
    }

    @Before
    public void cleanData() throws SQLException {
        delete();

        auteurDB.insert("Rowling", "JK");
        idAuteur = auteurDB.getIdFromAuteur("Rowling", "JK");

        oeuvreDB.insertLivre("978-1-2345-6789-7", "Harry Potter", "Harry Potter et la chambre des secrets", new Date(12000), 1, new Date(13000), "Harry va dans la chambre des secrets oO", idAuteur);
        ISBN = oeuvreDB.getISBNFromOeuvre("Harry Potter", "Harry Potter et la chambre des secrets", new Date(12000), 1, new Date(13000), "Harry va dans la chambre des secrets oO", idAuteur, null, null);

        usagerDB.insert("Pierson", "Guillaume", "guillaume.pierson@gmail.com", "9 rue du rosier");
        idUsager = usagerDB.getIdFromUsager("Pierson", "Guillaume", "guillaume.pierson@gmail.com", "9 rue du rosier");
    }

    @AfterClass()
    public static void deleteAfterTest() throws SQLException {
        delete();
    }

    @Test
    public void testInsert() throws SQLException{
        reservationDB.insert(idUsager, ISBN, new Date(12000), "en attente");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Reservation WHERE idUsager = ? AND ISBN = ?");
        preparedStatement.setInt(1, idUsager);
        preparedStatement.setString(2, ISBN);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        assertNotNull(rs.getInt("idUsager"));
        assertNotNull(rs.getString("ISBN"));
    }

    @Test
    public void testFindByIds() throws SQLException{
        reservationDB.insert(idUsager, ISBN, new Date(12000), "en attente");

        Reservation reservation = reservationDB.findByIds(idUsager, ISBN);
        assertNotNull(reservation);
    }

    @Test
    public void update() throws SQLException{
        reservationDB.insert(idUsager, ISBN, new Date(12000), "en attente");;

        reservationDB.update(idUsager, ISBN, new Date(12000), "terminée");
        Reservation reservation = reservationDB.findByIds(idUsager, ISBN);
        assertNotNull(reservation);
        assertEquals("terminée", reservation.getEtat());
    }

    @Test
    public void testDelete() throws SQLException{
        reservationDB.insert(idUsager, ISBN, new Date(12000), "en attente");

        reservationDB.delete(idUsager, ISBN);
        assertNull(reservationDB.findByIds(idUsager, ISBN));
    }
}
