package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import settings.Settings;

public class DB {
    public static Connection getConnection()
    {
        Connection con = null;
        try 
        {
            //String userName = "root";  //user of the database
            String userName = Settings.userName;  //user of the database
            String password = Settings.password;  //password of the database
            //String url = "jdbc:mysql://localhost/badiangan";  //the database that will be used
            String url = Settings.url;
            Class.forName ("com.mysql.jdbc.Driver"); //the driver of the database
            con = DriverManager.getConnection (url, userName, password);
        } 
        catch (ClassNotFoundException| SQLException ex) 
        {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            System.err.print(ex);
            JOptionPane.showMessageDialog(null, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return con;
    }
}
