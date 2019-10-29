/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.MessageFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author John Rey Alipe
 */
public class MyPrinter 
{
    public static void printNow(JTable tbl, String title)
    {
        MessageFormat header = new MessageFormat(title);
        MessageFormat footer = new MessageFormat("Page{0,number,integer}");

        try
        {
            tbl.print(JTable.PrintMode.FIT_WIDTH,header,footer);
        }catch (java.awt.print.PrinterException p)
        {
            JOptionPane.showMessageDialog(null, p.getMessage());
        } 
    }
}
