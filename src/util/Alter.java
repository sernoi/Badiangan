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
    
    public static int getVal(JSpinner spin)
    {
        return toInt(spin.getValue().toString());
    }
    
    public static String gatVal(JDateChooser dc)
    {
        return ((JTextField)dc.getDateEditor().getUiComponent()).getText();
    }
    
    public static String getVal(JComboBox box)
    {
        return box.getSelectedItem().toString();
    }
}
