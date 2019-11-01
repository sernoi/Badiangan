package hazard;

import disaster.*;
import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class HazardModel 
{
    public static void deleteHazard(String id)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Delete from hazard where haz_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(HazardModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(HazardModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static ResultSet getAllHazard()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Select haz_id as 'ID', description as 'Description', "
                    + "lt as 'Latitude', lg as 'Longitude' from hazard";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(HazardModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static void saveHazard(String desc,
            double lt, double lg)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into hazard values (0,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, desc);
            stmt.setDouble(2, lt);
            stmt.setDouble(3, lg);
            stmt.execute();
        }catch (SQLException ex) {
            Logger.getLogger(HazardModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Please check inputs" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(HazardModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void updateHazard(int id, String desc, double lt, double lg)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Update hazard set desc = ? , "
                    + "lt = ? , lg = ? where haz_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, desc);
            stmt.setDouble(2, lt);
            stmt.setDouble(3, lg);
            stmt.execute();
        }
        catch (SQLException ex) {
            Logger.getLogger(HazardModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection of DB!");
                Logger.getLogger(HazardModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
