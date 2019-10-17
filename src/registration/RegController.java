package registration;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import net.proteanit.sql.DbUtils;
import util.Alter;
import util.SearchModel;

public class RegController
{
    RegPanel rp;
    public RegController(RegPanel rp)
    {
        this.rp = rp;
        this.rp.allListener(new Action(), new PopUp(), new Mouse());
        
        displayAllReg();
    }
    void deleteReg()
    {
        int dataRow = rp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            String id = rp.table.getValueAt(dataRow,0).toString();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (rp, "Would You Like to "
                    + "delete registration: " + id + "?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                RegModel.deleteReg(id);
                displayAllReg();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(rp, "Please select a registration to delete.");
        }
    }  
    void displayAllReg()
    {
        ResultSet rs = RegModel.getAllReg();
        rp.table.setModel(DbUtils.resultSetToTableModel(rs));
        new SearchModel(rp, rp.table, rp.searchTF, rs);
    }
    void viewReg()
    {
        int dataRow = rp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            JOptionPane.showMessageDialog(rp,
                    "ID: " + (rp.table.getValueAt(dataRow,0).toString()) + "\n"
                            + "Admin: " + (rp.table.getValueAt(dataRow,1).toString()) + "\n"
                                    + "Beneficiary: " + (rp.table.getValueAt(dataRow,2).toString()) + "\n"
                                            + "Walkin Status: " + (rp.table.getValueAt(dataRow,3).toString()) + "\n"
                                                    + "Case: " + (rp.table.getValueAt(dataRow,4).toString()) + "\n"
                                                            + "Date: " + (rp.table.getValueAt(dataRow,5).toString()) + "\n",
                    "Registration Info", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(rp, "Please select a registration to view.");
        }
    }

    class Action implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == rp.viewMenuItem)
            {
                viewReg();
            }
            if(e.getSource() == rp.deleteMenuItem)
            {
                deleteReg();
            }
            if(e.getSource() == rp.deleteBtn)
            {
                deleteReg();
            }
        }
    }
    
    class Mouse extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent e) 
        {      
            int r = rp.table.rowAtPoint(e.getPoint());
            if (r >= 0 && r < rp.table.getRowCount()) {
                rp.table.setRowSelectionInterval(r, r);
            } else {
                rp.table.clearSelection();
            }

            int rowindex = rp.table.getSelectedRow();
            if (rowindex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(rp, e.getX(), e.getY());
                rp.table.setComponentPopupMenu(rp.popUpMenu);
            }
        }
    }
    
    class PopUp implements PopupMenuListener
    {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = rp.table.rowAtPoint(SwingUtilities.
                        convertPoint(rp.popUpMenu, new Point(0, 0), rp.table));
                if (rowAtPoint > -1) {
                    rp.table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                }
            });
        }
        
        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            
        }
        
        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            
        }
    }
}
