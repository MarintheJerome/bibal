package database;

import java.sql.*;

/**
 * Created by jerome on 19/10/2016.
 */
public class Connexion {
    private final static String USER = "root";
    private final static String PASSWORD = "root";

    /**
     * Instance de la classe
     */
    private static Connexion INSTANCE = null;

    private Connection connection;

    private Connexion(){
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
    public static Connexion getInstance(){
        if (INSTANCE == null){
            INSTANCE = new Connexion();
        }
        return INSTANCE;
    }

    public Connection getConnection(){
        return this.connection;
    }

    public static Connection getStaticConnection(){
        return Connexion.getInstance().getConnection();
    }
}
