package registration;

import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class RegModel 
{
    public static void deleteReg(String id)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Delete from registration where reg_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            //JOptionPane.showMessageDialog(null,"Subscriber Deleted!");
        } catch (SQLException ex) {
            Logger.getLogger(RegModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(RegModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        public static ResultSet getAllReg()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            //String sql = "Select * from registration";
            String sql = "SELECT registration.reg_id as 'ID', "
                    + "CONCAT_WS(' ', admin.fname, admin.mname, admin.lname) as 'Admin', "
                    + "CONCAT_WS(' ', beneficiary.fname, beneficiary.mname, beneficiary.lname) as 'Beneficiary', "
                    + "registration.walkin_status as 'Walk in Stat', "
                    + "registration.case as 'Case', "
                    + "registration.date as 'Reg Date' "
                    + "from registration, beneficiary, admin where "
                    + "registration.fk_bene_id_registration = beneficiary.bene_id "
                    + "and registration.fk_admin_id_registration = admin.admin_id";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(RegModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static void saveReg(int adminID, int beneID, String stat,
            String caseReg, String date)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into registration values (0,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, adminID);
            stmt.setInt(2, beneID);
            stmt.setString(3, stat);
            stmt.setString(4, caseReg);
            stmt.setString(5, date);
            stmt.execute();
        }catch (SQLException ex) {
            Logger.getLogger(RegModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Please check inputs" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(RegModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
