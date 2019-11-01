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
            String date, double lt, double lg, double rad, String remarks)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into disaster values (0,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, type);
            stmt.setString(2, name);
            stmt.setString(3, date);
            stmt.setDouble(4, lt);
            stmt.setDouble(5, lg);
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
    
    public static void updateDisaster(int id, String type, String name, 
            String date, double lt, double lg, double rad, String remarks)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Update disaster set disaster.type = ? , disaster.name = ? , "
                    + "disaster.date = ? , disaster.lat = ? , disaster.long = ?, disaster.radius = ?, "
                    + "disaster.remarks = ? where disaster.dis_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, type);
            stmt.setString(2, name);
            stmt.setString(3, date);
            stmt.setDouble(4, lt);
            stmt.setDouble(5, lg);
            stmt.setDouble(6, rad);
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
