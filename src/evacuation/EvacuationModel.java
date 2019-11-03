/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evacuation;

import db.DB;
import disaster.DisasterModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.distance.GeoUtils;

/**
 *
 * @author John Rey Alipe
 */
public class EvacuationModel 
{
    
    public static int getNearestSite(int id, ArrayList<Integer> availableSites)
    { 
        int idtoSend = 0;
        Connection conn = DB.getConnection();
        ResultSet rs;
        double beneLt = 0, beneLg = 0, evacLt = 0, evacLg = 0;
        double[] distanceDbl = new double[availableSites.size()];
        try
        {
            String sql = "Select loc_lat, loc_long from beneficiary where bene_id = "+id+"";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                beneLt = rs.getDouble("loc_lat");
                beneLg = rs.getDouble("loc_long");
            }
            
            for(int x = 0 ; x < availableSites.size() ; x++)
            {
                String sql2 = "Select evac.lat, evac.long from evac where evac_id = "+availableSites.get(x)+"";
                Statement stmt2 = conn.createStatement();
                rs = stmt2.executeQuery(sql2);
                if(rs.next())
                {
                    evacLt = rs.getDouble("lat");
                    evacLg = rs.getDouble("long");
                }
                distanceDbl[x] = GeoUtils.computeDistance(beneLt, beneLg, evacLt, evacLg);
            }
            int evacId = 0;
            double lowestDistance = distanceDbl[0];
            for(int i = 1; i < distanceDbl.length; i++)
            {
                if(distanceDbl[i] < lowestDistance)
                {
                    lowestDistance = distanceDbl[i];
                    evacId = i;
                }
            }
            idtoSend = availableSites.get(evacId);
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return idtoSend;
    }
    
    public static ResultSet getAllEvacuations()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Select "
                    + "fk_dis_id_evac_event as 'ID', "
                    + "(select name from disaster where dis_id = fk_dis_id_evac_event) as 'Disaster', "
                    + "(select CONCAT_WS(' ', beneficiary.fname, beneficiary.mname, beneficiary.lname) "
                    + "from beneficiary where bene_id = fk_bene_id_evac_event) as 'Beneficiary', "
                    + "(select name from evac where evac_id = fk_evac_id_evac_event) as 'Site Name' "
                    + "from evac_event";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(EvacuationModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static void saveEvacuation(int evacId, int disasterId, int beneId)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into evac_event values (0,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, evacId);
            stmt.setInt(2, disasterId);
            stmt.setInt(3, beneId);
            stmt.execute();
        }catch (SQLException ex) {
            Logger.getLogger(EvacuationModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Please check inputs" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(EvacuationModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static boolean isDisasterExist(int dis_id)
    {
        ResultSet rs = null;
        Connection conn = null;
        boolean flag = false;
        try {
            conn = DB.getConnection();
            String sql = "Select * from evac_event where fk_dis_id_evac_event = "+dis_id+"";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                flag = true;
            }
            else
            {
                flag = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvacuationModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        return flag;
    }
    
    public static void deleteExistingEvac(int dis_id)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Delete from evac_event where fk_dis_id_evac_event = '"+dis_id+"'";
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
    
    public static ArrayList<String> getEvacuationInfo(int dis_id)
    {
        ArrayList<String> list = new ArrayList<String>();
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Select "
                    + "evac_event_id as 'ID', "
                    + "(select name from disaster where dis_id = fk_dis_id_evac_event) as 'Disaster', "
                    + "(select CONCAT_WS(' ', beneficiary.fname, beneficiary.mname, beneficiary.lname) "
                    + "from beneficiary where bene_id = fk_bene_id_evac_event) as 'Beneficiary', "
                    + "(select count(*) from fmember where fk_bene_id_member = fk_bene_id_evac_event ) as 'count', "
                    + "(select name from evac where evac_id = fk_evac_id_evac_event) as 'Site Name' "
                    + "from evac_event";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                list.add(rs.getString("Beneficiary"));
                list.add(rs.getString("count"));
                list.add(rs.getString("Site Name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvacuationModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return list;
    }
}
