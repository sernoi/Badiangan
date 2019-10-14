package admin;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class AdminModel 
{
    public static void deleteAdmin(String adminID)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Delete from admin where admin_id = '"+adminID+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            //JOptionPane.showMessageDialog(null,"Subscriber Deleted!");
        } catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static ResultSet getAllAdmins()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "SELECT admin_id as 'ID',username as 'Username', fname as 'First Name', "
                         + "mname as 'Middle Name', lname as 'Last Name', department as 'Department',"
                    + " position as 'Position' from admin";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    public static void saveAdmin(String username, String password,
            String department, String position, String fName, String lName, String mName)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into admin values (0,?,sha1 (?),?,?,?,?,?)";
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
        }catch(MySQLIntegrityConstraintViolationException ex)
        {
            JOptionPane.showMessageDialog(null, "Username already taken", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Please check inputs" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void updateAdmin(String adminID, String username, String password, 
            String fname, String mname, String lname, String department, String position)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Update admin set username = ? , password = sha1(?) , "
                    + "fname = ? , mname = ? , lname = ?, department = ?, "
                    + "position = ? where admin_id = '"+adminID+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, fname);
            stmt.setString(4, mname);
            stmt.setString(5, lname);
            stmt.setString(6, department);
            stmt.setString(7, position);
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Admin Info Updated!");
        } catch(MySQLIntegrityConstraintViolationException ex)
        {
            JOptionPane.showMessageDialog(null, "Username already taken", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection of DB!");
                Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
