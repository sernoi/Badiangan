package evac;

import evac.*;
import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class EvacModel 
{
    public static void deleteEvac(String id)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Delete from evac where evac_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EvacModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(EvacModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static ResultSet getAllEvac()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Select evac.evac_id as 'ID', name as 'Name', "
                    + "evac.lat as 'Latitude', evac.long as 'Longitude' from evac";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(EvacModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static void saveEvac(String name, double lt, double lg)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into evac values (0,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setDouble(2, lt);
            stmt.setDouble(3, lg);
            stmt.execute();
        }catch (SQLException ex) {
            Logger.getLogger(EvacModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Please check inputs" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(EvacModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void updateEvac(int id, String name, double lt, double lg)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Update evac set evac.name = ? , evac.lat = ?, "
                    + "evac.long = ? where evac.evac_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setDouble(2, lt);
            stmt.setDouble(3, lg);
            stmt.execute();
        }
        catch (SQLException ex) {
            Logger.getLogger(EvacModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection of DB!");
                Logger.getLogger(EvacModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
