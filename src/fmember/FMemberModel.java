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
    public static ResultSet getAllFM()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "SELECT fmember.fmem_id as 'ID', CONCAT_WS(' ', beneficiary.fname, "
                    + "beneficiary.mname, beneficiary.lname ) as 'Beneficiary', " 
                    + "fmember.fname as 'First Name', fmember.mname as 'Middle Name', "
                    + "fmember.lname as 'Last Name', fmember.rel_to_hod as 'Rel to HOD', "
                    + "fmember.age as 'Age', fmember.sex as 'Sex', fmember.educ as 'HIghest Educ Att', "
                    + "fmember.occ_skills as 'Occ Skills', " 
                    + "fmember.remarks as 'Remarks' from beneficiary , fmember where "
                    + "fmember.fk_bene_id_member = beneficiary.bene_id";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FMemberModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static void saveFM(int beneId, String fName, String mName,
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
    
    public static void updateFM(int id, int bene_id, String fname, 
            String mname, String lname, String rel, int age, String sex,
            String educ, String occ, String remarks)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Update fmember set fk_bene_id_member = ?, "
                    + "fname = ? , mname = ? , lname = ?, rel_to_hod = ?, "
                    + "age = ? , sex = ? , educ = ?, occ_skills = ?, "
                    + "remarks = ? where fmem_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bene_id);
            stmt.setString(2, fname);
            stmt.setString(3, mname);
            stmt.setString(4, lname);
            stmt.setString(5, rel);
            stmt.setInt(6, age);
            stmt.setString(7, sex);
            stmt.setString(8, educ);
            stmt.setString(9, occ);
            stmt.setString(10, remarks);
            stmt.execute();
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
    
    public static void deleteFM(String beneId)
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
}
