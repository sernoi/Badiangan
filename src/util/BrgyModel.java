package util;
import db.DB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class BrgyModel 
{
    public static ArrayList<String> getBrgy()
    {
        ArrayList<String> brgy = new ArrayList<String>();
        Connection conn = DB.getConnection();
        ResultSet rs;
        try 
        {
            String sql = "SELECT * from brgy";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) 
            {
                //brgy.add(rs.getInt("brgy_id")+ " " + rs.getString("name"));
                brgy.add(rs.getString("name"));
            }
        } catch (SQLException err) 
        {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        return brgy;
    }
}
