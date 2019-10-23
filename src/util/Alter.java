package util;

import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class Alter 
{
    public static int toInt(String str)
    {
        return Integer.parseInt(str);
    }
    
    public static int getInt(JSpinner spin)
    {
        return toInt(spin.getValue().toString());
    }
    
    public static double getDouble(JSpinner spin)
    {
        return Double.parseDouble(spin.getValue().toString());
    }
    
    public static String gatDate(JDateChooser dc)
    {
        return ((JTextField)dc.getDateEditor().getUiComponent()).getText();
    }
    
    public static String getString(JComboBox box)
    {
        return box.getSelectedItem().toString();
    }
    
    public static int getInt(JComboBox box)
    {
        return Integer.parseInt(box.getSelectedItem().toString());
    }
}
