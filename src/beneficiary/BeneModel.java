package beneficiary;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class BeneModel 
{
    public static void deleteBene(String beneId)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Delete from beneficiary where bene_id = '"+beneId+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            //JOptionPane.showMessageDialog(null,"Subscriber Deleted!");
        } catch (SQLException ex) {
            Logger.getLogger(BeneModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(BeneModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static ResultSet getAllBene()
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "SELECT bene_id as '#',"
                    + "fname as 'First Name', mname as 'Middle Name', "
                    + "lname as 'Last Name', sex as 'Sex', dob as 'Date of Birth', "
                    + "(select name from brgy where brgy_id = fk_brgy_id_beneficiary) as 'Brgy', "
                    + "code as 'Code', fourps as '4Ps', ip as 'Indigent', hea as 'Highest Educ Att', "
                    + "ethnicity as 'Ethnicity', net_income as 'Net Income', occ as 'Occupation', "
                    + "health_condition as 'Health Condition', house_status as 'House Status',"
                    + "house_condition as 'House Condition', contact_num as 'Contact #',"
                    + "loc_long as 'Longitude', loc_lat as 'Latitude' "
                    + "from beneficiary";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(BeneModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    /**
     * Gets the latest bene_id from the beneficiary table to be
     * saved in the registration table
     * @return bene_id
     */
    public static int getIdOfLatestBene()
    {
        int bene_id = 0;
        Connection conn = DB.getConnection();
        ResultSet rs;
        String sql = "Select * from beneficiary ORDER BY bene_id DESC LIMIT 1";
        try
        {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next())
            {
                bene_id = rs.getInt("bene_id");
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return bene_id;
    }
    
    public static void saveBene(int beneId, String fName, String mName,
            String lName, String sex, String dob, String brgy, String code,
            String fourPs, String indigency, String hea, String ethnicity,
            double netIncome, String occ, String healthCond, String houseStat,
            String houseCond, String contactNum, double locLong, double locLat)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Insert into beneficiary values (?,?,?,?,?,?,"
                    + "(Select brgy_id from brgy where name = '"+brgy+"'),"
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, beneId);
            stmt.setString(2, fName);
            stmt.setString(3, mName);
            stmt.setString(4, lName);
            stmt.setString(5, sex);
            stmt.setString(6, dob);
            stmt.setString(7, code);
            stmt.setString(8, fourPs);
            stmt.setString(9, indigency);
            stmt.setString(10, hea);
            stmt.setString(11, ethnicity);
            stmt.setDouble(12, netIncome);
            stmt.setString(13, occ);
            stmt.setString(14, healthCond);
            stmt.setString(15, houseStat);
            stmt.setString(16, houseCond);
            stmt.setString(17, contactNum);
            stmt.setDouble(18, locLong);
            stmt.setDouble(19, locLat);
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Beneficiary Added!");
        }catch (SQLException ex) {
            Logger.getLogger(BeneModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Please check inputs" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(BeneModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static ResultSet searchBene(String str)
    {
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "SELECT bene_id as '#', "
                    + "fname as 'First Name', mname as 'Middle Name', "
                    + "lname as 'Last Name', sex as 'Sex', dob as 'Date of Birth', "
                    + "(select name from brgy where brgy_id = fk_brgy_id_beneficiary) as 'Brgy', "
                    + "code as 'Code', fourps as '4Ps', ip as 'Indigent', hea as 'Highest Educ Att', "
                    + "ethnicity as 'Ethnicity', net_income as 'Net Income', occ as 'Occupation', "
                    + "health_condition as 'Health Condition', house_status as 'House Status', "
                    + "house_condition as 'House Condition', contact_num as 'Contact #', "
                    + "loc_long as 'Longitude', loc_lat as 'Latitude' "
                    + "from beneficiary where "
                    
                    + "bene_id LIKE '%" + str + "%' or fname LIKE '%" + str + "%' or "
                    + "mname LIKE '%" + str + "%' or lname LIKE '%" + str + "%' or "
                    + "sex LIKE '%" + str + "%' or dob LIKE '%" + str + "%' or "
                    + "(select name from brgy where brgy_id = fk_brgy_id_beneficiary) LIKE '%" + str + "%' or "
                    + "code LIKE '%" + str + "%' or fourps LIKE '%" + str + "%' or "
                    + "ip LIKE '%" + str + "%' or hea LIKE '%" + str + "%' or "
                    + "ethnicity LIKE '%" + str + "%' or net_income LIKE '%" + str + "%' or "
                    + "occ LIKE '%" + str + "%' or health_condition LIKE '%" + str + "%' or "
                    + "house_status LIKE '%" + str + "%' or house_condition LIKE '%" + str + "%' or "
                    + "loc_long LIKE '%" + str + "%' or loc_lat LIKE '%" + str + "%' or "
                    + "contact_num LIKE '%" + str + "%'";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            //Logger.getLogger(AddSubscriberModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return rs;
    }
    
    public static void updateBene(int beneId, String fName, String mName,
            String lName, String sex, String dob, String brgy, String code,
            String fourPs, String indigency, String hea, String ethnicity,
            double netIncome, String occ, String healthCond, String houseStat,
            String houseCond, String contactNum, double locLong, double locLat)
    {
        Connection conn = null;
        try {
            conn = DB.getConnection();
            String sql = "Update beneficiary set "
                    + "fname = ?, mname = ?, lname = ?, "
                    + "sex = ?, dob = ?, "
                    + "fk_brgy_id_beneficiary = (Select brgy_id from brgy where name = '"+brgy+"'), "
                    + "code = ?, fourps = ?, ip = ?, hea = ?, "
                    + "ethnicity = ?, net_income = ?, occ = ?, health_condition = ?, "
                    + "house_status = ?, house_condition = ?, contact_num = ?, "
                    + "loc_long = ?, loc_lat = ?"
                    + " where bene_id = "+beneId+"";
//            String sql = "Insert into beneficiary values (?,?,?,?,?,?,"
//                    + "(Select brgy_id from brgy where name = '"+brgy+"'),"
//                    + "?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fName);
            stmt.setString(2, mName);
            stmt.setString(3, lName);
            stmt.setString(4, sex);
            stmt.setString(5, dob);
            stmt.setString(6, code);
            stmt.setString(7, fourPs);
            stmt.setString(8, indigency);
            stmt.setString(9, hea);
            stmt.setString(10, ethnicity);
            stmt.setDouble(11, netIncome);
            stmt.setString(12, occ);
            stmt.setString(13, healthCond);
            stmt.setString(14, houseStat);
            stmt.setString(15, houseCond);
            stmt.setString(16, contactNum);
            stmt.setDouble(17, locLong);
            stmt.setDouble(18, locLat);
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Beneficiary Updated!");
        }catch (SQLException ex) {
            Logger.getLogger(BeneModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Please check inputs" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, "Cannot close connection to DB!");
                Logger.getLogger(BeneModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
