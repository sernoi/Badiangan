package hazard;
import hazard.map.MapController;
import hazard.map.MapPanel;
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

public class HazardController
{
    HazardPanel hazp;
    public HazardController(HazardPanel hazp)
    {
        this.hazp = hazp;
        this.hazp.allListener(new Action(), new PopUp(), new Mouse());
        
        displayAllHazard();
    }
    void addDisaster()
    {
        hazp.addDialog.setTitle("Add Hazard");
        hazp.addDialog.setModal(true);
        hazp.addDialog.pack();
        hazp.addDialog.setLocationRelativeTo(null);
        hazp.addDialog.setVisible(true);
        clearFields();
    }
    void clearFields()
    {
        hazp.descTF.setText("");
        hazp.latSpin.setValue(0);
        hazp.longSpin.setValue(0);
    }
    void deleteHazard()
    {
        int dataRow = hazp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            String id = hazp.table.getValueAt(dataRow,0).toString();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (hazp, "Would You Like to "
                    + "delete hazard: " + id + "?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                HazardModel.deleteHazard(id);
                displayAllHazard();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(hazp, "Please select a hazard to delete.");
        }
    }  
    void displayAllHazard()
    {
        ResultSet rs = HazardModel.getAllHazard();
        hazp.table.setModel(DbUtils.resultSetToTableModel(rs));
        new SearchModel(hazp, hazp.table, hazp.searchTF, rs);
    }
    void openMapToGetLoc()
    {
        MapPanel mpp = new MapPanel();
        new MapController(mpp,hazp);
    }
    void editHazard()
    {
        int dataRow = hazp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            hazp.idLbl.setText(hazp.table.getValueAt(dataRow,0).toString());
            hazp.descTF1.setText(hazp.table.getValueAt(dataRow,1).toString());
            hazp.latSpin1.setValue(Double.parseDouble(hazp.table.getValueAt(dataRow,2).toString()));
            hazp.longSpin1.setValue(Double.parseDouble(hazp.table.getValueAt(dataRow,3).toString()));
            hazp.editDialog.setTitle("Edit Hazard");
            hazp.editDialog.setModal(true);
            hazp.editDialog.pack();
            hazp.editDialog.setLocationRelativeTo(hazp);
            hazp.editDialog.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(hazp, "Please select a hazard to edit.");
        }
    }
    void saveHazard()
    {
        HazardModel.saveHazard(
                hazp.descTF.getText(),
                Alter.getDouble(hazp.latSpin),
                Alter.getDouble(hazp.longSpin));
        hazp.addDialog.dispose();
        displayAllHazard();
    }
    void updateHazard()
    {
        HazardModel.updateHazard(Integer.parseInt(hazp.idLbl.getText()), 
                hazp.descTF1.getText(),
                Alter.getDouble(hazp.latSpin1),
                Alter.getDouble(hazp.longSpin1));
        hazp.editDialog.dispose();
        displayAllHazard();
    }
    void viewHazard()
    {
        int dataRow = hazp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            JOptionPane.showMessageDialog(hazp,
                    "ID: " + (hazp.table.getValueAt(dataRow,0).toString()) + "\n"
                            + "Description: " + (hazp.table.getValueAt(dataRow,1).toString()) + "\n"
                                    + "Latitude: " + (hazp.table.getValueAt(dataRow,2).toString()) + "\n"
                                            + "Longitude: " + (hazp.table.getValueAt(dataRow,3).toString()),
                    "Hazard Info", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(hazp, "Please select a Hazard to view.");
        }
    }
    void viewInMap()
    {
        int dataRow = hazp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            double lt = (Double.parseDouble(hazp.table.getValueAt(dataRow,2).toString()));
            double lg = (Double.parseDouble(hazp.table.getValueAt(dataRow,3).toString()));
            MapPanel mpp = new MapPanel();
            new MapController(mpp, hazp, lt, lg);
        }
        else
        {
            JOptionPane.showMessageDialog(hazp, "Please select a hazard to view in map.");
        }
    }
    class Action implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == hazp.addBtn)
            {
                addDisaster();
            }
            if(e.getSource() == hazp.okBtn)
            {
                saveHazard();
            }
            if(e.getSource() == hazp.editBtn)
            {
                editHazard();
            }
            if(e.getSource() == hazp.deleteBtn)
            {
                deleteHazard();
            }
            if(e.getSource() == hazp.cancelBtn)
            {
                hazp.addDialog.dispose();
            }
            if(e.getSource() == hazp.cancelBtn1)
            {
                hazp.editDialog.dispose();
            }
            if(e.getSource() == hazp.viewMenuItem)
            {
                viewHazard();
            }
            if(e.getSource() == hazp.editMenuItem)
            {
                editHazard();
            }
            if(e.getSource() == hazp.addMenuItem)
            {
                addDisaster();
            }
            if(e.getSource() == hazp.deleteMenuItem)
            {
                deleteHazard();
            }
            if(e.getSource() == hazp.getLocBtn)
            {
                openMapToGetLoc();
            }
            if(e.getSource() == hazp.getLocBtn1)
            {
                openMapToGetLoc();
            }
            if(e.getSource() == hazp.okBtn1)
            {
                updateHazard();
            }
            if(e.getSource() == hazp.viewInMapMenuItem)
            {
                viewInMap();
            }
        }
    }
    
    class Mouse extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent e) 
        {      
            int r = hazp.table.rowAtPoint(e.getPoint());
            if (r >= 0 && r < hazp.table.getRowCount()) {
                hazp.table.setRowSelectionInterval(r, r);
            } else {
                hazp.table.clearSelection();
            }

            int rowindex = hazp.table.getSelectedRow();
            if (rowindex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(hazp, e.getX(), e.getY());
                hazp.table.setComponentPopupMenu(hazp.popUpMenu);
            }
        }
    }
    
    class PopUp implements PopupMenuListener
    {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = hazp.table.rowAtPoint(SwingUtilities.
                        convertPoint(hazp.popUpMenu, new Point(0, 0), hazp.table));
                if (rowAtPoint > -1) {
                    hazp.table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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
