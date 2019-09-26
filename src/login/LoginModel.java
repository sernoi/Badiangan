package login;

import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class LoginModel 
{
    /**
     * This method is used to validate user and password from the database
     * it will search for a row which matches the username and password
     * @param un
     * @param pw
     * @return name array which includes the position, department, and full
     * name of the user
     */
    public static String[] loginAdmin(String un, String pw)
    {
        String[] name = new String[3];
        Connection conn = DB.getConnection();
        ResultSet rs;
        try 
        {
            if (un != null && pw != null) 
            {
                String sql = "SELECT position, department, CONCAT_WS(' ', fname, mname, lname) AS 'name' FROM admin "
                            + "where username='" + un + "' and password = sha1('"+pw+"')";
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                if (rs.next()) 
                {
                    //in this case enter when at least one result comes it means user is valid
                    name[0] = rs.getString("position");
                    name[1] = rs.getString("department");
                    name[2] = rs.getString("name");
                }
                else
                {
                    name[0] = null;
                    name[1] = null;
                    name[2] = null;
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Fields cannot be empty!","Error",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException err) 
        {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        return name;
    }
    
    public static void saveAdmin(String username, String password, String department, 
            String position, String fName, String lName, String mName)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into admin values ('',?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, department);
            stmt.setString(4, position);
            stmt.setString(5, fName);
            stmt.setString(6, lName);
            stmt.setString(7, mName);
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Admin Added!");
        } catch (SQLException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
