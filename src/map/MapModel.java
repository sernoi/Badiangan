package map;

import db.DB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class MapModel 
{
    public static ArrayList<Integer> getAllBene()
    { 
        ArrayList<Integer> list = new ArrayList<Integer>();
        Connection conn = DB.getConnection();
        ResultSet rs;
        String sql = "Select * from beneficiary ORDER BY bene_id";
        try
        {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                list.add(rs.getInt("bene_id"));
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }
    
    public static ArrayList<String> getAllBeneGeo()
    { 
        ArrayList<String> list = new ArrayList<String>();
        Connection conn = DB.getConnection();
        ResultSet rs;
        String sql = "Select * from beneficiary ORDER BY bene_id";
        try
        {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                list.add("" + rs.getDouble("loc_lat") + "," + rs.getDouble("loc_long"));
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }
    public static ArrayList<Integer> getAllEvac()
    { 
        ArrayList<Integer> list = new ArrayList<Integer>();
        Connection conn = DB.getConnection();
        ResultSet rs;
        String sql = "Select * from evac ORDER BY evac_id";
        try
        {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                list.add(rs.getInt("evac_id"));
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }
    
    public static ArrayList<String> getAllEvacGeo()
    { 
        ArrayList<String> list = new ArrayList<String>();
        Connection conn = DB.getConnection();
        ResultSet rs;
        String sql = "Select * from evac ORDER BY evac_id";
        try
        {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                list.add("" + rs.getDouble("lat") + "," + rs.getDouble("long"));
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }
    
    public static ArrayList<Integer> getAllHazard()
    { 
        ArrayList<Integer> list = new ArrayList<Integer>();
        Connection conn = DB.getConnection();
        ResultSet rs;
        String sql = "Select * from hazard ORDER BY haz_id";
        try
        {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                list.add(rs.getInt("haz_id"));
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }
    
    public static ArrayList<String> getAllHazardGeo()
    { 
        ArrayList<String> list = new ArrayList<String>();
        Connection conn = DB.getConnection();
        ResultSet rs;
        String sql = "Select * from hazard ORDER BY haz_id";
        try
        {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                list.add("" + rs.getDouble("lt") + "," + rs.getDouble("lg"));
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }
    
    public static ArrayList<String> getDisaster(int id)
    { 
        ArrayList<String> list = new ArrayList<String>();
        Connection conn = DB.getConnection();
        ResultSet rs;
        String sql = "Select * from disaster where dis_id = "+id+"";
        try
        {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                list.add(""+rs.getInt("dis_id"));
                list.add(""+rs.getString("type"));
                list.add(""+rs.getString("name"));
                list.add(""+rs.getString("date"));
                list.add(""+rs.getDouble("lat"));
                list.add(""+rs.getDouble("long"));
                list.add(""+rs.getDouble("radius"));
                list.add(""+rs.getString("remarks"));
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }
}
