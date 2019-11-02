/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.evacuate;

import db.DB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
}
