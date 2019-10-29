package brgy;
import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class BrgyModel 
{
    public static void deleteBrgy(String id)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Delete from brgy where brgy_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(BrgyModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(BrgyModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static ResultSet getAllBrgy()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Select brgy.brgy_id as 'ID', brgy.name as 'Name' from brgy";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(BrgyModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static void saveBrgy(String name)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into brgy values (0,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.execute();
        }catch (SQLException ex) {
            Logger.getLogger(BrgyModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Please check inputs" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(BrgyModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void updateBrgy(int id, String name)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Update brgy set brgy.name = ? where brgy.brgy_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.execute();
        }
        catch (SQLException ex) {
            Logger.getLogger(BrgyModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection of DB!");
                Logger.getLogger(BrgyModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
