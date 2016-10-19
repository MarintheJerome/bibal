import database.Connexion;
import database.UsagerDB;
import model.Usager;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yhugo on 19/10/2016.
 */
public class TestUsagerDB {

    private static Connection connection;
    private static UsagerDB usagerDB;

    @BeforeClass
    public static void init(){
        connection = Connexion.getStaticConnection();
        usagerDB = new UsagerDB();
    }

    @Test
    public void testInsert() throws SQLException{
        usagerDB.insert("Pierson", "Guillaume", "guillaume.pierson@gmail.com", "5 rue du rosier");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT idUsager FROM usager WHERE nomUsager = ? AND prenomUsager = ? AND mail = ? and adresse = ?");
        preparedStatement.setString(1, "Pierson");
        preparedStatement.setString(2, "Guillaume");
        preparedStatement.setString(3, "guillaume.pierson@gmail.com");
        preparedStatement.setString(4, "5 rue du rosier");
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        assertNotNull(rs.getInt("idUsager"));
    }

    @Test
    public void testFindById() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT idUsager FROM usager WHERE nomUsager = ? AND prenomUsager = ? AND mail = ? and adresse = ?");
        preparedStatement.setString(1, "Pierson");
        preparedStatement.setString(2, "Guillaume");
        preparedStatement.setString(3, "guillaume.pierson@gmail.com");
        preparedStatement.setString(4, "5 rue du rosier");
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();

        Usager usager = usagerDB.findById(rs.getInt("idUsager"));
        assertNotNull(usager);
    }

    @Test
    public void testIdFromUsager() throws SQLException {
        int id = usagerDB.getIdFromUsager("Pierson", "Guillaume", "guillaume.pierson@gmail.com", "5 rue du rosier");
        assertNotNull(usagerDB.findById(id));
    }

    @Test
    public void testUpdate() throws SQLException{
        int id = usagerDB.getIdFromUsager("Pierson", "Guillaume", "guillaume.pierson@gmail.com", "5 rue du rosier");
        usagerDB.update(id, "Pierson", "Guillaume", "guillaume.pierson@gmail.com", "7 rue du rosier");
        Usager usager = usagerDB.findById(id);
        assertEquals(usager.getAdresse(), ("7 rue du rosier"));
    }

    @Test
    public void testDelete() throws SQLException{
        int id = usagerDB.getIdFromUsager("Pierson", "Guillaume", "guillaume.pierson@gmail.com", "7 rue du rosier");
        usagerDB.delete(id);
        assertNull(usagerDB.findById(id));
    }
}
