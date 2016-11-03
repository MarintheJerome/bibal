import database.*;
import model.Emprunt;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by jerome on 26/10/2016.
 */
public class TestEmpruntDB {
    private static Connection connection;
    private static OeuvreDB oeuvreDB;
    private static AuteurDB auteurDB;
    private static UsagerDB usagerDB;
    private static EmpruntDB empruntDB;
    private static ExemplaireDB exemplaireDB;

    private int idAuteur;
    private String ISBN;
    private int idExemplaire;
    private int idUsager;

    private static void delete() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Emprunt");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("DELETE FROM Usager");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("DELETE FROM Exemplaire");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("DELETE FROM Oeuvre");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("DELETE FROM Auteur");
        preparedStatement.executeUpdate();
    }

    public int getMaxIDExemplaire() throws SQLException {
        return exemplaireDB.getMaxId();
    }

    @BeforeClass
    public static void init(){
        connection = Connexion.getStaticConnection();
        oeuvreDB = new OeuvreDB();
        auteurDB = new AuteurDB();
        usagerDB = new UsagerDB();
        exemplaireDB = new ExemplaireDB();
        empruntDB = new EmpruntDB();
    }

    @Before
    public void cleanData() throws SQLException {
        delete();

        auteurDB.insert("Rowling", "JK");
        idAuteur = auteurDB.getIdFromAuteur("Rowling", "JK");

        oeuvreDB.insertLivre("978-1-2345-6789-7", "Harry Potter", "Harry Potter et la chambre des secrets", new Date(12000), 1, new Date(13000), "Harry va dans la chambre des secrets oO", idAuteur);
        ISBN = oeuvreDB.getISBNFromOeuvre("Harry Potter", "Harry Potter et la chambre des secrets", new Date(12000), 1, new Date(13000), "Harry va dans la chambre des secrets oO", idAuteur, null, null);

        exemplaireDB.insert("Bon", ISBN);
        idExemplaire = getMaxIDExemplaire();

        usagerDB.insert("Pierson", "Guillaume", "guillaume.pierson@gmail.com", "9 rue du rosier");
        idUsager = usagerDB.getIdFromUsager("Pierson", "Guillaume", "guillaume.pierson@gmail.com", "9 rue du rosier");
    }

    @AfterClass()
    public static void deleteAfterTest() throws SQLException {
        delete();
    }

    @Test
    public void testInsert() throws SQLException{
        empruntDB.insert(idUsager, idExemplaire, new Date(12000), 7, new Date(250000), new Date(250000));

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Emprunt WHERE idUsager = ? AND idExemplaire = ?");
        preparedStatement.setInt(1, idUsager);
        preparedStatement.setInt(2, idExemplaire);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        assertNotNull(rs.getInt("idUsager"));
        assertNotNull(rs.getInt("idExemplaire"));
    }

    @Test
    public void testFindByIds() throws SQLException{
        empruntDB.insert(idUsager, idExemplaire, new Date(12000), 7, new Date(250000), new Date(250000));

        Emprunt emprunt = empruntDB.findByIds(idUsager, idExemplaire);
        assertNotNull(emprunt);
    }

    @Test
    public void update() throws SQLException{
        empruntDB.insert(idUsager, idExemplaire, new Date(12000), 7, new Date(250000), new Date(250000));

        empruntDB.update(idUsager, idExemplaire, new Date(12000), 15, new Date(250000), new Date(250000));
        Emprunt emprunt = empruntDB.findByIds(idUsager, idExemplaire);
        assertNotNull(emprunt);
        assertEquals(15, emprunt.getDuree());
    }

    @Test
    public void testDelete() throws SQLException{
        empruntDB.insert(idUsager, idExemplaire, new Date(12000), 7, new Date(250000), new Date(250000));

        empruntDB.delete(idUsager, idExemplaire);
        assertNull(empruntDB.findByIds(idUsager, idExemplaire));
    }

    @Test
    public void testSelectAll() throws SQLException{
        empruntDB.insert(idUsager, idExemplaire, new Date(12000), 7, new Date(250000), new Date(250000));
        ArrayList<Emprunt> emprunts = empruntDB.selectAll();
        assertEquals(1, emprunts.size());
    }
}
