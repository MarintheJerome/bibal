import database.AuteurDB;
import database.Connexion;
import model.Auteur;
import model.Usager;
import org.junit.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by jerome on 19/10/2016.
 */
public class TestAuteurDB {

    private static Connection connection;
    private static AuteurDB auteurDB;

    private static void delete() throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM auteur");
        preparedStatement.executeUpdate();
    }

    @BeforeClass
    public static void init(){
        connection = Connexion.getStaticConnection();
        auteurDB = new AuteurDB();
    }

    @Before
    public void cleanData() throws SQLException {
        delete();
        auteurDB.insert("Hugo", "Victor");
    }

    @AfterClass()
    public static void deleteAfterTest() throws SQLException {
        delete();
    }

    @Test
    public void testInsert() throws SQLException {
        auteurDB.insert("Hugo", "Jean-Claude");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT idAuteur FROM auteur WHERE nomAuteur = ? AND prenomAuteur = ?");
        preparedStatement.setString(1, "Hugo");
        preparedStatement.setString(2, "Jean-Claude");
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        assertNotNull(rs.getInt("idAuteur"));
    }

    @Test
    public void testFindById() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT idAuteur FROM auteur WHERE nomAuteur = ? AND prenomAuteur = ?");
        preparedStatement.setString(1, "Hugo");
        preparedStatement.setString(2, "Victor");
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();

        Auteur auteur = auteurDB.findById(rs.getInt("idAuteur"));
        assertNotNull(auteur);
    }

    @Test
    public void testIdFromUsager() throws SQLException {
        int id = auteurDB.getIdFromAuteur("Hugo", "Victor");
        assertNotNull(auteurDB.findById(id));
    }

    @Test
    public void testUpdate() throws SQLException{
        int id = auteurDB.getIdFromAuteur("Hugo", "Victor");
        auteurDB.update(id, "Hugo", "Victor");
        Auteur auteur = auteurDB.findById(id);
        assertEquals(auteur.getPrenomAuteur(), ("Victor"));
    }

    @Test
    public void testDelete() throws SQLException{
        int id = auteurDB.getIdFromAuteur("Hugo", "Victor");
        auteurDB.delete(id);
        assertNull(auteurDB.findById(id));
    }
}
