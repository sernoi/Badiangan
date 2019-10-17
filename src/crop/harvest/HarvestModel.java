package crop.harvest;

import crop.CropModel;
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

public class HarvestModel 
{
    public static void deleteHarvest(String id)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Delete from crop_season where cs_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            //JOptionPane.showMessageDialog(null,"Subscriber Deleted!");
        } catch (SQLException ex) {
            Logger.getLogger(HarvestModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(HarvestModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static ResultSet getAllHarvest()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "SELECT crop_season.cs_id as 'ID', "
                    + "crop_season.fk_crop_id_cs as 'Crop ID', crop_season.form as 'Container', "
                    + "crop_season.num as 'Quantity', "
                    + "crop_season.profit as 'Profit', "
                    + "crop_season.date as 'Harvest Date', "
                    + "crop_season.remarks as 'Remarks' "
                    + "from crop_season";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(HarvestModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static void saveHarvest(int id, String crop, int qty,
            double profit, String date ,String remarks)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into crop_season values (0,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, crop);
            stmt.setInt(3, qty);
            stmt.setDouble(4, profit);
            stmt.setString(5, date);
            stmt.setString(6, remarks);
            stmt.execute();
        }catch (SQLException ex) {
            Logger.getLogger(HarvestModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Please check inputs" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(HarvestModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void updateHarvest(int id, int cropId, String container, 
            int qty, double profit, String date, String remarks)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Update crop_season set fk_crop_id_cs = ? , form = ? , "
                    + "num = ? , profit = ? , date = ?, "
                    + "remarks = ? where cs_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cropId);
            stmt.setString(2, container);
            stmt.setInt(3, qty);
            stmt.setDouble(4, profit);
            stmt.setString(5, date);
            stmt.setString(6, remarks);
            stmt.execute();
        }
        catch (SQLException ex) {
            Logger.getLogger(HarvestModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection of DB!");
                Logger.getLogger(HarvestModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void updateCropHarvested(int id)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Update crop set status = 'Harvested' "
                    + "where crop_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        }
        catch (SQLException ex) {
            Logger.getLogger(CropModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection of DB!");
                Logger.getLogger(CropModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static ArrayList<Integer> getCropsID()
    {
        ArrayList<Integer> crop = new ArrayList<Integer>();
        Connection conn = DB.getConnection();
        ResultSet rs;
        try 
        {
            String sql = "SELECT crop_id as 'ID' from crop";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) 
            {
                crop.add(rs.getInt("ID"));
            }
        } catch (SQLException err) 
        {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        return crop;
    }
}
