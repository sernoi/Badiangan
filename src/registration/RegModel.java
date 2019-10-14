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
    public static void deleteLS(String id)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Delete from livestock where ls_id = '"+id+"'";
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
        public static ResultSet getAllLS()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            //String sql = "Select * from livestock";
            String sql = "SELECT livestock.ls_id as 'ID', "
                    + "CONCAT_WS(' ', beneficiary.fname, beneficiary.mname, beneficiary.lname) as 'Beneficiary', "
                    + "livestock.livestock_raised as 'Livestock Raised', "
                    + "livestock.classification as 'Classification', livestock.heads as 'No. of Heads', "
                    + "livestock.age as 'Age in Months', livestock.exp as 'Exp Disposal Date', "
                    + "livestock.remarks as 'Remarks' "
                    + "from livestock, beneficiary where "
                    + "livestock.fk_bene_id_livestock = beneficiary.bene_id";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(RegModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static void saveLS(int id, String ls, String classification,
            int heads, int age, String exp, String remarks)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into livestock values (0,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, ls);
            stmt.setString(3, classification);
            stmt.setInt(4, heads);
            stmt.setInt(5, age);
            stmt.setString(6, exp);
            stmt.setString(7, remarks);
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
    
    public static void updateLS(int id, int bene_id, String ls, 
            String cl, int heads, int age, String exp, String remarks)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Update livestock set fk_bene_id_livestock = ? , livestock_raised = ? , "
                    + "classification = ? , heads = ? , age = ?, exp = ?, "
                    + "remarks = ? where ls_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bene_id);
            stmt.setString(2, ls);
            stmt.setString(3, cl);
            stmt.setInt(4, heads);
            stmt.setInt(5, age);
            stmt.setString(6, exp);
            stmt.setString(7, remarks);
            stmt.execute();
        }
        catch (SQLException ex) {
            Logger.getLogger(RegModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection of DB!");
                Logger.getLogger(RegModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
