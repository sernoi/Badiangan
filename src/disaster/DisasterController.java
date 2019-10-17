package disaster;
import crop.*;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import net.proteanit.sql.DbUtils;
import util.Alter;
import util.SearchModel;

public class DisasterController
{
    DisasterPanel disp;
    public DisasterController(DisasterPanel disp)
    {
        this.disp = disp;
        this.disp.allListener(new Action(), new PopUp(), new Mouse());
        
        displayAllDisasters();
    }
    void addDisaster()
    {
        disp.addDialog.setTitle("Add Disaster");
        disp.addDialog.setModal(true);
        disp.addDialog.pack();
        disp.addDialog.setLocationRelativeTo(null);
        disp.addDialog.setVisible(true);
        clearFields();
    }
    void clearFields()
    {
        disp.typeCBB.setSelectedIndex(0);
        disp.nameTF.setText("");
        disp.dateDC.setDate(new Date());
        disp.longSpin.setValue(0);
        disp.latSpin.setValue(0);
        disp.radSpin.setValue(0);
        disp.remarksTA.setText("");
    }
    void deleteDisaster()
    {
        int dataRow = disp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            String id = disp.table.getValueAt(dataRow,0).toString();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (disp, "Would You Like to "
                    + "delete disaster: " + id + "?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                DisasterModel.deleteDisaster(id);
                displayAllDisasters();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(disp, "Please select a disaster to delete.");
        }
    }  
    void displayAllDisasters()
    {
        ResultSet rs = DisasterModel.getAllDisasters();
        disp.table.setModel(DbUtils.resultSetToTableModel(rs));
        new SearchModel(disp, disp.table, disp.searchTF, rs);
    }
    void editDisaster()
    {
        int dataRow = disp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            disp.idLbl.setText(disp.table.getValueAt(dataRow,0).toString());
            disp.typeCBB1.setSelectedItem((disp.table.getValueAt(dataRow,1).toString()));
            disp.nameTF1.setText(disp.table.getValueAt(dataRow,2).toString());
        
            try 
            {
                Date date;
                date = new SimpleDateFormat("yyyy-MM-dd").parse(disp.table.getValueAt(dataRow,3).toString());
                disp.dateDC1.setDate(date);
            } catch (ParseException ex) {
                Logger.getLogger(DisasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            disp.longSpin1.setValue(Double.parseDouble(disp.table.getValueAt(dataRow,4).toString()));
            disp.latSpin1.setValue(Double.parseDouble(disp.table.getValueAt(dataRow,5).toString()));
            disp.radSpin1.setValue(Double.parseDouble(disp.table.getValueAt(dataRow,6).toString()));
            disp.remarksTA1.setText(disp.table.getValueAt(dataRow,7).toString());
            disp.editDialog.setTitle("Edit Disaster");
            disp.editDialog.setModal(true);
            disp.editDialog.pack();
            disp.editDialog.setLocationRelativeTo(disp);
            disp.editDialog.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(disp, "Please select a disaster to edit.");
        }
    }
    void saveDisaster()
    {
        DisasterModel.saveDisaster(
                Alter.getString(disp.typeCBB),
                disp.nameTF.getText(),
                Alter.gatVal(disp.dateDC),
                Alter.getDouble(disp.longSpin),
                Alter.getDouble(disp.latSpin),
                Alter.getDouble(disp.radSpin),
                disp.remarksTA.getText());
        disp.addDialog.dispose();
        displayAllDisasters();
    }
    void updateDisaster()
    {
        
    }
    void viewDisaster()
    {
        int dataRow = disp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            JOptionPane.showMessageDialog(disp,
                    "ID: " + (disp.table.getValueAt(dataRow,0).toString()) + "\n"
                            + "Beneficiary: " + (disp.table.getValueAt(dataRow,1).toString()) + "\n"
                                    + "Disaster: " + (disp.table.getValueAt(dataRow,2).toString()) + "\n"
                                            + "Area: " + (disp.table.getValueAt(dataRow,3).toString()) + "\n"
                                                    + "Variety: " + (disp.table.getValueAt(dataRow,4).toString()) + "\n"
                                                            + "Classification: " + (disp.table.getValueAt(dataRow,5).toString()) + "\n"
                                                                    + "Exp Harvest Date: " + (disp.table.getValueAt(dataRow,6).toString()) + "\n"
                                                                            + "Remarks: " + (disp.table.getValueAt(dataRow,7).toString()),
                    "Disaster Info", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(disp, "Please select a disaster to edit.");
        }
    }

    class Action implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == disp.addBtn)
            {
                addDisaster();
            }
            if(e.getSource() == disp.okBtn)
            {
                saveDisaster();
            }
            if(e.getSource() == disp.editBtn)
            {
                editDisaster();
            }
            if(e.getSource() == disp.deleteBtn)
            {
                deleteDisaster();
            }
            if(e.getSource() == disp.cancelBtn)
            {
                disp.addDialog.dispose();
            }
            if(e.getSource() == disp.cancelBtn1)
            {
                disp.editDialog.dispose();
            }
            if(e.getSource() == disp.viewMenuItem)
            {
                viewDisaster();
            }
            if(e.getSource() == disp.editMenuItem)
            {
                editDisaster();
            }
            if(e.getSource() == disp.addMenuItem)
            {
                addDisaster();
            }
            if(e.getSource() == disp.deleteMenuItem)
            {
                deleteDisaster();
            }
        }
    }
    
    class Mouse extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent e) 
        {      
            int r = disp.table.rowAtPoint(e.getPoint());
            if (r >= 0 && r < disp.table.getRowCount()) {
                disp.table.setRowSelectionInterval(r, r);
            } else {
                disp.table.clearSelection();
            }

            int rowindex = disp.table.getSelectedRow();
            if (rowindex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(disp, e.getX(), e.getY());
                disp.table.setComponentPopupMenu(disp.popUpMenu);
            }
        }
    }
    
    class PopUp implements PopupMenuListener
    {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = disp.table.rowAtPoint(SwingUtilities.
                        convertPoint(disp.popUpMenu, new Point(0, 0), disp.table));
                if (rowAtPoint > -1) {
                    disp.table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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
