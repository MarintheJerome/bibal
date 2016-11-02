import database.AuteurDB;
import database.Connexion;
import database.OeuvreDB;
import model.Oeuvre;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by jerome on 25/10/2016.
 */
public class TestOeuvreDB {

    private static Connection connection;
    private static OeuvreDB oeuvreDB;
    private static AuteurDB auteurDB;
    private int idAuteur;

    private static void delete() throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Oeuvre");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("DELETE FROM Auteur");
        preparedStatement.executeUpdate();
    }

    @BeforeClass
    public static void init(){
        connection = Connexion.getStaticConnection();
        oeuvreDB = new OeuvreDB();
        auteurDB = new AuteurDB();
    }

    @Before
    public void cleanData() throws SQLException {
        delete();

        auteurDB.insert("Rowling", "JK");
        idAuteur = auteurDB.getIdFromAuteur("Rowling", "JK");
        oeuvreDB.insertLivre("978-1-2345-6789-7", "Harry Potter", "Harry Potter et la chambre des secrets", new Date(12000), 1, new Date(13000), "Harry va dans la chambre des secrets oO", idAuteur);
        oeuvreDB.insertMagasine("978-1-2345-6789-8", "Nous deux", "Nous deux numéro 3", new Date(2006, 11, 17), 3, 3, 7);
    }

    @AfterClass()
    public static void deleteAfterTest() throws SQLException {
        delete();
    }

    @Test
    public void testInsert() throws SQLException {
        oeuvreDB.insertLivre("978-1-2345-6789-9", "Harry Potter", "Harry Potter et la coupe de feu", new Date(180000), 1000, new Date(190000), "Ah!! Ca brûle", idAuteur);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT ISBN FROM Oeuvre " +
                "WHERE nomOeuvre = ? AND titre = ? AND dateParution = ? AND nbReservation = ? AND dateEdition = ? AND resume = ? and idAuteur = ?");
        preparedStatement.setString(1, "Harry Potter");
        preparedStatement.setString(2, "Harry Potter et la coupe de feu");
        preparedStatement.setDate(3, new Date(180000));
        preparedStatement.setInt(4, 1000);
        preparedStatement.setDate(5, new Date(190000));
        preparedStatement.setString(6, "Ah!! Ca brûle");
        preparedStatement.setInt(7, idAuteur);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        assertNotNull(rs.getString("ISBN"));
    }

    @Test
    public void testFindById() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT ISBN FROM Oeuvre " +
                "WHERE nomOeuvre = ? AND titre = ? AND dateParution = ? AND nbReservation = ? AND dateEdition = ? AND resume = ? and idAuteur = ?");
        preparedStatement.setString(1, "Harry Potter");
        preparedStatement.setString(2, "Harry Potter et la chambre des secrets");
        preparedStatement.setDate(3, new Date(12000));
        preparedStatement.setInt(4, 1);
        preparedStatement.setDate(5, new Date(13000));
        preparedStatement.setString(6, "Harry va dans la chambre des secrets oO");
        preparedStatement.setInt(7, idAuteur);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();

        Oeuvre oeuvre = oeuvreDB.findByISBN(rs.getString("ISBN"));
        assertNotNull(oeuvre);
    }

    @Test
    public void testISBNFromOeuvre() throws SQLException {
        String ISBN = oeuvreDB.getISBNFromOeuvre("Harry Potter", "Harry Potter et la chambre des secrets", new Date(12000), 1, new Date(13000), "Harry va dans la chambre des secrets oO", idAuteur, null, null);
        assertNotNull(oeuvreDB.findByISBN(ISBN));
    }

    @Test
    public void testUpdate() throws SQLException{
        String ISBN = oeuvreDB.getISBNFromOeuvre("Harry Potter", "Harry Potter et la chambre des secrets", new Date(12000), 1, new Date(13000), "Harry va dans la chambre des secrets oO", idAuteur, null, null);

        oeuvreDB.update(ISBN, "Harry Potter", "Harry Potter et la chambre des secrets", new Date(12000), 10, new Date(13000), "Harry va dans la chambre des secrets oO", idAuteur, null, null);
        Oeuvre oeuvre = oeuvreDB.findByISBN(ISBN);
        assertEquals(oeuvre.getNbReservation(), 10);
    }

   @Test
    public void testDelete() throws SQLException{
        String ISBN = oeuvreDB.getISBNFromOeuvre("Harry Potter", "Harry Potter et la chambre des secrets", new Date(12000), 1, new Date(13000), "Harry va dans la chambre des secrets oO", idAuteur, null, null);
        oeuvreDB.delete(ISBN);
        assertNull(oeuvreDB.findByISBN(ISBN));
    }

    @Test
    public void testSelectAll() throws SQLException{
        ArrayList<Oeuvre> oeuvres = oeuvreDB.selectAll();
        assertTrue(oeuvres.size() > 0);
        assertTrue(oeuvres.size() == 2);
        assertEquals(oeuvres.get(0).getAuteur().getNomAuteur(), "Rowling");
    }
}
