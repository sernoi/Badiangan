package crop;

import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class CropModel 
{
    public static void deleteCrop(String id)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Delete from crop where crop_id = '"+id+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            //JOptionPane.showMessageDialog(null,"Subscriber Deleted!");
        } catch (SQLException ex) {
            Logger.getLogger(CropModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(CropModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static ResultSet getAllCrop()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "SELECT crop.crop_id as 'ID', "
                    + "CONCAT_WS(' ', beneficiary.fname, beneficiary.mname, beneficiary.lname) as 'Beneficiary', "
                    + "crop.crop as 'Crop', crop.area as 'Area', crop.variety as 'Variety', "
                    + "crop.classification as 'Classification', crop.exp as 'Exp Harvest Date', "
                    + "crop.remarks as 'Remarks', crop.status as 'Status' "
                    + "from crop, beneficiary where "
                    + "crop.fk_bene_id_crop = beneficiary.bene_id";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CropModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static void saveCrop(int beneId, String crop, String area,
            String variety, String classification, String exp, String remarks)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into crop values (0,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, beneId);
            stmt.setString(2, crop);
            stmt.setString(3, area);
            stmt.setString(4, variety);
            stmt.setString(5, classification);
            stmt.setString(6, exp);
            stmt.setString(7, remarks);
            stmt.execute();
        }catch (SQLException ex) {
            Logger.getLogger(CropModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Please check inputs" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(CropModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void updateCrop(int id, int bene_id, String crop, 
            String area, String variety, String classification, 
            String exp, String remarks)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Update crop set fk_bene_id_crop = ? , crop = ? , "
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
}
