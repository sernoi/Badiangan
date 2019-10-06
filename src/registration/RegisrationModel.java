package registration;

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

public class RegisrationModel 
{
    public static ResultSet getAllBene()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "SELECT bene_id as 'Beneficiary ID',"
                    + "fname as 'First Name', mname as 'Middle Name', fk_brgy_id_beneficiary as 'Brgy', "
                    + "lname as 'Last Name', sex as 'Sex', dob as 'Date of Birth', "
                    + "occ as Occupation, code as 'Code', fourps as '4Ps', ip as 'Indigent',"
                    + "ethnicity as 'Ethnicity', net_income as 'Net Income', "
                    + "house_status as 'House Status', house_condition as 'House Condition',"
                    + "health_condition as 'Health Condition', loc_long as 'Location Long',"
                    + "loc_lat as 'Location Lat' "
                    + "from beneficiary";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(RegisrationModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static ResultSet searchAdmin(String str)
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "SELECT admin_id as 'Admin ID',username as 'Username', fname as 'First Name', "
                         + "mname as 'Middle Name', lname as 'Last Name', department as 'Department',"
                         + "position as 'Position' from admin where "
                         + "admin_id LIKE '%" + str + "%' or fname LIKE '%" + str + "%' or "
                         + "mname LIKE '%" + str + "%' or lname LIKE '%" + str + "%' or "
                         + "department LIKE '%" + str + "%' or username LIKE '%" + str + "%' or "
                         + "position LIKE '%" + str + "%'";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            //Logger.getLogger(AddSubscriberModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static void saveBene(String fName, String mName, String lName,
                    String sex, String dob, String code, String fourPs,
                    String indigency, String ethnicity, String occ,
                    String houseStat, String houseCond)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into beneficiary values (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fName);
            stmt.setString(2, mName);
            stmt.setString(3, lName);
            stmt.setString(4, sex);
            stmt.setString(5, dob);
            stmt.setString(6, code);
            stmt.setString(7, fourPs);
            stmt.setString(8, indigency);
            stmt.setString(9, ethnicity);
            stmt.setString(10, occ);
            stmt.setString(11, houseStat);
            stmt.setString(12, houseCond);
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Beneficiary Added!");
        }catch (SQLException ex) {
            Logger.getLogger(RegisrationModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Please check inputs" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(RegisrationModel.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RegisrationModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection of DB!");
                Logger.getLogger(RegisrationModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
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
            Logger.getLogger(RegisrationModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(RegisrationModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Gets the latest bene_id from the beneficiary table to be 
     * saved in the registration table
     * @return bene_id
     */
    public static int getIdOfLatestBene()
    {
        int bene_id = 0;
        Connection conn = DB.getConnection();
        ResultSet rs;
        String sql = "Select * from beneficiary ORDER BY bene_id DESC LIMIT 1";
            try
            {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                if (rs.next()) 
                {
                    bene_id = rs.getInt("bene_id");
                }
            }catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        return bene_id;
    }
}
