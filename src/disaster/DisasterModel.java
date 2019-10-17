package disaster;

import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DisasterModel 
{
    public static void deleteDisaster(String id)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Delete from disaster where dis_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DisasterModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(DisasterModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static ResultSet getAllDisasters()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Select * from disaster";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DisasterModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static void saveDisaster(String type, String name,
            String date, double lg, double lt, double rad, String remarks)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into disaster values (0,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, type);
            stmt.setString(2, name);
            stmt.setString(3, date);
            stmt.setDouble(4, lg);
            stmt.setDouble(5, lt);
            stmt.setDouble(6, rad);
            stmt.setString(7, remarks);
            stmt.execute();
        }catch (SQLException ex) {
            Logger.getLogger(DisasterModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Please check inputs" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(DisasterModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void updateDisaster(int id, int bene_id, String crop, 
            String area, String variety, String classification, 
            String exp, String remarks)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Update disaster set fk_bene_id_crop = ? , crop = ? , "
                    + "area = ? , variety = ? , classification = ?, exp = ?, "
                    + "remarks = ? where crop_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bene_id);
            stmt.setString(2, crop);
            stmt.setString(3, area);
            stmt.setString(4, variety);
            stmt.setString(5, classification);
            stmt.setString(6, exp);
            stmt.setString(7, remarks);
            stmt.execute();
        }
        catch (SQLException ex) {
            Logger.getLogger(DisasterModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection of DB!");
                Logger.getLogger(DisasterModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
