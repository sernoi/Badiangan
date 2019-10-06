package fmember;

import beneficiary.*;
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

public class FMemberModel 
{
    public static ResultSet getAllFMember()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "SELECT bene_id as 'Beneficiary ID',"
                    + "fname as 'First Name', mname as 'Middle Name', "
                    + "lname as 'Last Name', sex as 'Sex', dob as 'Date of Birth', "
                    + "(select name from brgy where brgy_id = fk_brgy_id_beneficiary) as 'Brgy', "
                    + "code as 'Code', fourps as '4Ps', ip as 'Indigent', hea as 'Highest Educ Att', "
                    + "ethnicity as 'Ethnicity', net_income as 'Net Income', occ as 'Occupation', "
                    + "health_condition as 'Health Condition', house_status as 'House Status',"
                    + "house_condition as 'House Condition', contact_num as 'Contact #' "
                    + "from beneficiary";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FMemberModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static ResultSet searchFMember(String str)
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "SELECT bene_id as 'Beneficiary ID', "
                    + "fname as 'First Name', mname as 'Middle Name', "
                    + "lname as 'Last Name', sex as 'Sex', dob as 'Date of Birth', "
                    + "(select name from brgy where brgy_id = fk_brgy_id_beneficiary) as 'Brgy', "
                    + "code as 'Code', fourps as '4Ps', ip as 'Indigent', hea as 'Highest Educ Att', "
                    + "ethnicity as 'Ethnicity', net_income as 'Net Income', occ as 'Occupation', "
                    + "health_condition as 'Health Condition', house_status as 'House Status', "
                    + "house_condition as 'House Condition', contact_num as 'Contact #' "
                    + "from beneficiary where "
                    
                    + "bene_id LIKE '%" + str + "%' or fname LIKE '%" + str + "%' or "
                    + "mname LIKE '%" + str + "%' or lname LIKE '%" + str + "%' or "
                    + "sex LIKE '%" + str + "%' or dob LIKE '%" + str + "%' or "
                    + "(select name from brgy where brgy_id = fk_brgy_id_beneficiary) LIKE '%" + str + "%' or "
                    + "code LIKE '%" + str + "%' or fourps LIKE '%" + str + "%' or "
                    + "ip LIKE '%" + str + "%' or hea LIKE '%" + str + "%' or "
                    + "ethnicity LIKE '%" + str + "%' or net_income LIKE '%" + str + "%' or "
                    + "occ LIKE '%" + str + "%' or health_condition LIKE '%" + str + "%' or "
                    + "house_status LIKE '%" + str + "%' or house_condition LIKE '%" + str + "%' or "
                    + "contact_num LIKE '%" + str + "%'";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            //Logger.getLogger(AddSubscriberModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static void saveFMember(int beneId, String fName, String mName,
            String lName, String rel, int age, String sex, String educ,
            String occ, String remarks)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into fmember values (0,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, beneId);
            stmt.setString(2, fName);
            stmt.setString(3, mName);
            stmt.setString(4, lName);
            stmt.setString(5, rel);
            stmt.setInt(6, age);
            stmt.setString(7, sex);
            stmt.setString(8, educ);
            stmt.setString(9, occ);
            stmt.setString(10, remarks);
            stmt.execute();
        }catch (SQLException ex) {
            Logger.getLogger(FMemberModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Please check inputs" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(FMemberModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void updateFMember(String beneID, String username, String password, 
            String fname, String mname, String lname, String department, String position)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Update beneficiary set username = ? , password = sha1(?) , "
                    + "fname = ? , mname = ? , lname = ?, department = ?, "
                    + "position = ? where bene_id = '"+beneID+"'";
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
            Logger.getLogger(FMemberModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection of DB!");
                Logger.getLogger(FMemberModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void deleteFMember(String beneId)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Delete from beneficiary where bene_id = '"+beneId+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            //JOptionPane.showMessageDialog(null,"Subscriber Deleted!");
        } catch (SQLException ex) {
            Logger.getLogger(FMemberModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(FMemberModel.class.getName()).log(Level.SEVERE, null, ex);
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
