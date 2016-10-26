import database.AuteurDB;
import database.Connexion;
import database.ExemplaireDB;
import database.OeuvreDB;
import model.Exemplaire;
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
public class TestExemplaireDB {
    private static Connection connection;
    private static AuteurDB auteurDB;
    private static OeuvreDB oeuvreDB;
    private static ExemplaireDB exemplaireDB;
    private int idAuteur;
    private String ISBN;

    private static void delete() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Exemplaire");
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
        exemplaireDB = new ExemplaireDB();
        auteurDB = new AuteurDB();
    }

    @Before
    public void cleanData() throws SQLException {
        delete();
        auteurDB.insert("Rowling", "JK");

        idAuteur = auteurDB.getIdFromAuteur("Rowling", "JK");
        oeuvreDB.insertLivre("978-1-2345-6789-7", "Harry Potter", "Harry Potter et la chambre des secrets", new Date(12000), 1, new Date(13000), "Harry va dans la chambre des secrets oO", idAuteur);

        ISBN = oeuvreDB.getISBNFromOeuvre("Harry Potter", "Harry Potter et la chambre des secrets", new Date(12000), 1, new Date(13000), "Harry va dans la chambre des secrets oO", idAuteur, null, null);
    }

    public int getMaxID() throws SQLException {
        return exemplaireDB.getMaxId();
    }

    @AfterClass()
    public static void deleteAfterTest() throws SQLException {
        delete();
    }

    @Test
    public void testInsert() throws SQLException{
        exemplaireDB.insert("Abimé", ISBN);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Exemplaire WHERE idExemplaire in (SELECT MAX(idExemplaire) FROM exemplaire GROUP by idExemplaire)");
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        assertEquals(ISBN, rs.getString("ISBN"));
        assertEquals("Abimé", rs.getString("etatExemplaire"));
    }

    @Test
    public void findById() throws SQLException{
        exemplaireDB.insert("Abimé", ISBN);
        int maxId = getMaxID();

        Exemplaire exemplaire = exemplaireDB.findById(maxId);
        assertNotNull(exemplaire);
        assertEquals("Abimé", exemplaire.getEtatExemplaire());
        assertEquals(ISBN, exemplaire.getOeuvre().getISBN());
    }

    @Test
    public void update() throws SQLException{
        exemplaireDB.insert("Abimé", ISBN);
        int maxId = getMaxID();

        exemplaireDB.update(maxId, "Neuf", ISBN);
        Exemplaire exemplaire = exemplaireDB.findById(maxId);
        assertEquals("Neuf", exemplaire.getEtatExemplaire());
    }

    @Test
    public void testDelete() throws SQLException{
        exemplaireDB.insert("Trop Abimé", ISBN);
        int maxId = getMaxID();

        exemplaireDB.delete(maxId);
        assertNull(exemplaireDB.findById(maxId));
    }
}
