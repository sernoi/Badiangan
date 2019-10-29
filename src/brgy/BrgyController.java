package brgy;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import net.proteanit.sql.DbUtils;
import util.Alter;
import util.SearchModel;

public class BrgyController
{
    BrgyPanel brgyp;
    public BrgyController(BrgyPanel brgyp)
    {
        this.brgyp = brgyp;
        this.brgyp.allListener(new Action(), new PopUp(), new Mouse());
        
        displayAllBrgy();
    }
    void addBrgy()
    {
        brgyp.addDialog.setTitle("Add Brgy");
        brgyp.addDialog.setModal(true);
        brgyp.addDialog.pack();
        brgyp.addDialog.setLocationRelativeTo(null);
        brgyp.addDialog.setVisible(true);
        clearFields();
    }
    void clearFields()
    {
        brgyp.nameTF.setText("");
    }
    void deleteBrgy()
    {
        int dataRow = brgyp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            String id = brgyp.table.getValueAt(dataRow,0).toString();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (brgyp, "Would You Like to "
                    + "delete brgy: " + id + "?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                BrgyModel.deleteBrgy(id);
                displayAllBrgy();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(brgyp, "Please select a disaster to delete.");
        }
    }  
    void displayAllBrgy()
    {
        ResultSet rs = BrgyModel.getAllBrgy();
        brgyp.table.setModel(DbUtils.resultSetToTableModel(rs));
        new SearchModel(brgyp, brgyp.table, brgyp.searchTF, rs);
    }

    void editBrgy()
    {
        int dataRow = brgyp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            brgyp.idLbl.setText(brgyp.table.getValueAt(dataRow,0).toString());
            brgyp.nameTF1.setText(brgyp.table.getValueAt(dataRow,1).toString());
            brgyp.editDialog.setTitle("Edit Brgy");
            brgyp.editDialog.setModal(true);
            brgyp.editDialog.pack();
            brgyp.editDialog.setLocationRelativeTo(brgyp);
            brgyp.editDialog.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(brgyp, "Please select a site to edit.");
        }
    }
    void saveBrgy()
    {
        BrgyModel.saveBrgy(brgyp.nameTF.getText());
        brgyp.addDialog.dispose();
        displayAllBrgy();
    }
    void updateBrgy()
    {
        BrgyModel.updateBrgy(Alter.toInt(brgyp.idLbl.getText()),
                brgyp.nameTF1.getText());
        brgyp.editDialog.dispose();
        displayAllBrgy();
    }
    void viewBrgy()
    {
        int dataRow = brgyp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            JOptionPane.showMessageDialog(brgyp,
                    "ID: " + (brgyp.table.getValueAt(dataRow,0).toString()) + "\n"
                            + "Name: " + (brgyp.table.getValueAt(dataRow,1).toString()),
                    "Disaster Info", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(brgyp, "Please select a brgy to edit.");
        }
    }

    class Action implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == brgyp.addBtn)
            {
                addBrgy();
            }
            if(e.getSource() == brgyp.okBtn)
            {
                saveBrgy();
            }
            if(e.getSource() == brgyp.okBtn1)
            {
                updateBrgy();
            }
            if(e.getSource() == brgyp.editBtn)
            {
                editBrgy();
            }
            if(e.getSource() == brgyp.deleteBtn)
            {
                deleteBrgy();
            }
            if(e.getSource() == brgyp.cancelBtn)
            {
                brgyp.addDialog.dispose();
            }
            if(e.getSource() == brgyp.cancelBtn1)
            {
                brgyp.editDialog.dispose();
            }
            if(e.getSource() == brgyp.viewMenuItem)
            {
                viewBrgy();
            }
            if(e.getSource() == brgyp.editMenuItem)
            {
                editBrgy();
            }
            if(e.getSource() == brgyp.addMenuItem)
            {
                addBrgy();
            }
            if(e.getSource() == brgyp.deleteMenuItem)
            {
                deleteBrgy();
            }
        }
    }
    
    class Mouse extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent e) 
        {      
            int r = brgyp.table.rowAtPoint(e.getPoint());
            if (r >= 0 && r < brgyp.table.getRowCount()) {
                brgyp.table.setRowSelectionInterval(r, r);
            } else {
                brgyp.table.clearSelection();
            }

            int rowindex = brgyp.table.getSelectedRow();
            if (rowindex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(brgyp, e.getX(), e.getY());
                brgyp.table.setComponentPopupMenu(brgyp.popUpMenu);
            }
        }
    }
    
    class PopUp implements PopupMenuListener
    {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = brgyp.table.rowAtPoint(SwingUtilities.
                        convertPoint(brgyp.popUpMenu, new Point(0, 0), brgyp.table));
                if (rowAtPoint > -1) {
                    brgyp.table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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
