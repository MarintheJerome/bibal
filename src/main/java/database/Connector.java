package database;

import java.sql.*;

/**
 * Created by jerome on 19/10/2016.
 */
public class Connector {
    private final static String USER = "root";
    private final static String PASSWORD = "root";

    /**
     * Instance de la classe
     */
    private static Connector INSTANCE = null;

    private Connection connection;
    private Connector(){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Bibal", USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de récupérer l'instance unique
     * @return l'instance unique
     */
    public static Connector getInstance(){
        if (INSTANCE == null){
            INSTANCE = new Connector();
        }
        return INSTANCE;
    }

    public Connection getConnection(){
        return this.connection;
    }

    public static Connection getStaticConnection(){
        return Connector.getInstance().getConnection();
    }
}
