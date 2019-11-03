package evacsite;
import evacsite.map.MapController;
import evacsite.map.MapPanel;
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

public class EvacController
{
    EvacPanel ep;
    public EvacController(EvacPanel ep)
    {
        this.ep = ep;
        this.ep.allListener(new Action(), new PopUp(), new Mouse());
        
        displayAllEvac();
    }
    void addEvac()
    {
        ep.addDialog.setTitle("Add Evacuation Site");
        ep.addDialog.setModal(true);
        ep.addDialog.pack();
        ep.addDialog.setLocationRelativeTo(null);
        ep.addDialog.setVisible(true);
        clearFields();
    }
    void clearFields()
    {
        ep.nameTF.setText("");
        ep.longSpin.setValue(0);
        ep.latSpin.setValue(0);
    }
    void deleteEvac()
    {
        int dataRow = ep.table.getSelectedRow();
        if(dataRow >= 0)
        {
            String id = ep.table.getValueAt(dataRow,0).toString();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (ep, "Would You Like to "
                    + "delete evacuation site: " + id + "?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                EvacModel.deleteEvac(id);
                displayAllEvac();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(ep, "Please select a disaster to delete.");
        }
    }  
    void displayAllEvac()
    {
        ResultSet rs = EvacModel.getAllEvac();
        ep.table.setModel(DbUtils.resultSetToTableModel(rs));
        new SearchModel(ep, ep.table, ep.searchTF, rs);
    }
    void openMapToGetLoc()
    {
        MapPanel mpp = new MapPanel();
        new MapController(mpp,ep);
    }
    void openMapToEditLoc()
    {
        MapPanel mpp = new MapPanel();
        new MapController(mpp,ep);
    }
    void editEvac()
    {
        int dataRow = ep.table.getSelectedRow();
        if(dataRow >= 0)
        {
            ep.idLbl.setText(ep.table.getValueAt(dataRow,0).toString());
            ep.nameTF1.setText(ep.table.getValueAt(dataRow,1).toString());
            ep.longSpin1.setValue(Double.parseDouble(ep.table.getValueAt(dataRow,2).toString()));
            ep.latSpin1.setValue(Double.parseDouble(ep.table.getValueAt(dataRow,3).toString()));
            ep.editDialog.setTitle("Edit Evacuation Site");
            ep.editDialog.setModal(true);
            ep.editDialog.pack();
            ep.editDialog.setLocationRelativeTo(ep);
            ep.editDialog.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(ep, "Please select a site to edit.");
        }
    }
    void saveEvac()
    {
        EvacModel.saveEvac(
                ep.nameTF.getText(),
                Alter.getDouble(ep.latSpin),
                Alter.getDouble(ep.longSpin));
        ep.addDialog.dispose();
        displayAllEvac();
    }
    void updateEvac()
    {
        EvacModel.updateEvac(Alter.toInt(ep.idLbl.getText()),
                ep.nameTF1.getText(), Alter.getDouble(ep.latSpin1), 
                Alter.getDouble(ep.longSpin1));
        ep.editDialog.dispose();
        displayAllEvac();
    }
    void viewEvac()
    {
        int dataRow = ep.table.getSelectedRow();
        if(dataRow >= 0)
        {
            JOptionPane.showMessageDialog(ep,
                    "ID: " + (ep.table.getValueAt(dataRow,0).toString()) + "\n"
                            + "Name: " + (ep.table.getValueAt(dataRow,1).toString()) + "\n"
                                    + "Latitude: " + (ep.table.getValueAt(dataRow,2).toString()) + "\n"
                                            + "Longitude: " + (ep.table.getValueAt(dataRow,3).toString()),
                    "Disaster Info", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(ep, "Please select a site to edit.");
        }
    }

    class Action implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == ep.addBtn)
            {
                addEvac();
            }
            if(e.getSource() == ep.okBtn)
            {
                saveEvac();
            }
            if(e.getSource() == ep.okBtn1)
            {
                updateEvac();
            }
            if(e.getSource() == ep.editBtn)
            {
                editEvac();
            }
            if(e.getSource() == ep.deleteBtn)
            {
                deleteEvac();
            }
            if(e.getSource() == ep.cancelBtn)
            {
                ep.addDialog.dispose();
            }
            if(e.getSource() == ep.cancelBtn1)
            {
                ep.editDialog.dispose();
            }
            if(e.getSource() == ep.viewMenuItem)
            {
                viewEvac();
            }
            if(e.getSource() == ep.editMenuItem)
            {
                editEvac();
            }
            if(e.getSource() == ep.addMenuItem)
            {
                addEvac();
            }
            if(e.getSource() == ep.deleteMenuItem)
            {
                deleteEvac();
            }
            if(e.getSource() == ep.getLocBtn)
            {
                openMapToGetLoc();
            }
            if(e.getSource() == ep.getLocBtn1)
            {
                openMapToEditLoc();
            }
        }
    }
    
    class Mouse extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent e) 
        {      
            int r = ep.table.rowAtPoint(e.getPoint());
            if (r >= 0 && r < ep.table.getRowCount()) {
                ep.table.setRowSelectionInterval(r, r);
            } else {
                ep.table.clearSelection();
            }

            int rowindex = ep.table.getSelectedRow();
            if (rowindex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(ep, e.getX(), e.getY());
                ep.table.setComponentPopupMenu(ep.popUpMenu);
            }
        }
    }
    
    class PopUp implements PopupMenuListener
    {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = ep.table.rowAtPoint(SwingUtilities.
                        convertPoint(ep.popUpMenu, new Point(0, 0), ep.table));
                if (rowAtPoint > -1) {
                    ep.table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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
