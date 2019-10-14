package livestock.disposal;

import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DisposalModel 
{
    public static void deleteDisposal(String id)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Delete from ls_season where ls_season_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            //JOptionPane.showMessageDialog(null,"Subscriber Deleted!");
        } catch (SQLException ex) {
            Logger.getLogger(DisposalModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(DisposalModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static ResultSet getAllDisposal()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "SELECT ls_season_id as 'ID', "
                    + "fk_ls_id_ls_season as 'Livestock ID', "
                    + "date as 'Date', "
                    + "profit as 'Profit', "
                    + "remarks as 'Remarks' from "
                    + "ls_season";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DisposalModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static void saveDisposal(int lsId, double profit, String date ,String remarks)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into ls_season values (0,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lsId);
            stmt.setDouble(2, profit);
            stmt.setString(3, date);
            stmt.setString(4, remarks);
            stmt.execute();
        }catch (SQLException ex) {
            Logger.getLogger(DisposalModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Please check inputs" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(DisposalModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void updateDisposal(int id, int lsId, double profit, String date, String remarks)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Update ls_season set fk_ls_id_ls_season = ?,"
                    + " profit = ? , date = ?, "
                    + "remarks = ? where ls_season_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lsId);
            stmt.setDouble(2, profit);
            stmt.setString(3, date);
            stmt.setString(4, remarks);
            stmt.execute();
        }
        catch (SQLException ex) {
            Logger.getLogger(DisposalModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection of DB!");
                Logger.getLogger(DisposalModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static ArrayList<Integer> getLSID()
    {
        ArrayList<Integer> ls = new ArrayList<Integer>();
        Connection conn = DB.getConnection();
        ResultSet rs;
        try 
        {
            String sql = "SELECT ls_id as 'ID' from livestock";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) 
            {
                ls.add(rs.getInt("ID"));
            }
        } catch (SQLException err) 
        {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        return ls;
    }
}
