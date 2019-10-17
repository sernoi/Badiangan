package crop.harvest;
import crop.CropController;
import crop.CropPanel;
import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableCellEditor;
import net.proteanit.sql.DbUtils;
import util.Alter;
import util.SearchModel;

public class HarvestController
{
    HarvestPanel hp;
    JPanel mp;
    public HarvestController(HarvestPanel hp, JPanel mp)
    {
        this.hp = hp;
        this.mp = mp;
        this.hp.allListener(new HAction(), new HPopUp(), new HMouse());
        
        
        displayAllHarvest();
    }
    void addHarvest()
    {
        JOptionPane.showMessageDialog(null, "Please add a harvest in Crop/Tree Panel", 
                "Add Harvest", JOptionPane.INFORMATION_MESSAGE);
        HarvestPanel hp = new HarvestPanel();
        CropPanel cp = new CropPanel();
        new CropController(cp);
        CardLayout cl = (CardLayout) (mp.getLayout());
        mp.add(cp,"CropPanel");
        cl.show(mp, "CropPanel");  
    }
    void clearFields()
    {
        hp.formTF.setText("");
        hp.qtySpin.setValue(0);
        hp.profitSpin.setValue(0);
        hp.dateDC.setDate(new Date());
        hp.remarksTA.setText("");
    }
    void deleteHarvest()
    {
        int dataRow = hp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            String id = hp.table.getValueAt(dataRow,0).toString();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (hp, "Would You Like to "
                    + "delete harvest: " + id + "?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                HarvestModel.deleteHarvest(id);
                displayAllHarvest();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(hp, "Please select a harvest to delete.");
        }
    }  
    void displayAllHarvest()
    {
        ResultSet rs = HarvestModel.getAllHarvest();
        hp.table.setModel(DbUtils.resultSetToTableModel(rs));
        // this is to disable editing in the jtable
        for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class))
        {
            TableCellEditor ce = hp.table.getDefaultEditor(c);
            if (ce instanceof DefaultCellEditor)
            {
                ((DefaultCellEditor) ce).setClickCountToStart(Integer.MAX_VALUE);
            }
        }
        new SearchModel(hp, hp.table, hp.searchTF, rs);
    }
    void editHarvest()
    {
        int dataRow = hp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            hp.idHLbl.setText(hp.table.getValueAt(dataRow,0).toString());
            hp.idCropCB.setModel(new DefaultComboBoxModel(HarvestModel.getCropsID().toArray()));
            hp.idCropCB.setSelectedItem(Integer.parseInt(hp.table.getValueAt(dataRow,1).toString()));
            hp.formTF.setText(hp.table.getValueAt(dataRow,2).toString());
            hp.qtySpin.setValue(Integer.parseInt(hp.table.getValueAt(dataRow,3).toString()));
            hp.profitSpin.setValue(Double.parseDouble(hp.table.getValueAt(dataRow,4).toString()));
            
            try 
            {
                Date date;
                date = new SimpleDateFormat("yyyy-MM-dd").parse(hp.table.getValueAt(dataRow,5).toString());
                hp.dateDC.setDate(date); //dob
            } catch (ParseException ex) {
                Logger.getLogger(HarvestController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            hp.remarksTA.setText(hp.table.getValueAt(dataRow,6).toString());
            hp.editDialog.setTitle("Edit Harvest");
            hp.editDialog.setModal(true);
            hp.editDialog.pack();
            hp.editDialog.setLocationRelativeTo(hp);
            hp.editDialog.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(hp, "Please select harvest to edit.");
        }
    }
    void viewHarvest()
    {
        int dataRow = hp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            JOptionPane.showMessageDialog(hp,
            "Harvest ID: " + (hp.table.getValueAt(dataRow,0).toString()) + "\n" 
            + "Crop ID: " + (hp.table.getValueAt(dataRow,1).toString()) + "\n" 
            + "Container: " + (hp.table.getValueAt(dataRow,2).toString()) + "\n" 
            + "Qty: " + (hp.table.getValueAt(dataRow,3).toString()) + "\n" 
            + "Profit: " + (hp.table.getValueAt(dataRow,4).toString()) + "\n" 
            + "Harvest Date: " + (hp.table.getValueAt(dataRow,5).toString()) + "\n" 
            + "Remarks: " + (hp.table.getValueAt(dataRow,6).toString()),
            "Harvest Info", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(hp, "Please select harvest to view.");
        }
    }
    void updateHarvest()
    {
        HarvestModel.updateHarvest(
                Integer.parseInt(hp.idHLbl.getText()),
                Alter.getInt(hp.idCropCB),
                hp.formTF.getText(),
                Alter.getInt(hp.qtySpin),
                Alter.getDouble(hp.profitSpin),
                ((JTextField)hp.dateDC.getDateEditor().getUiComponent()).getText(),
                hp.remarksTA.getText());
        hp.editDialog.dispose();
        displayAllHarvest();
    }

    class HAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == hp.addBtn)
            {
                addHarvest();
            }
            if(e.getSource() == hp.editBtn)
            {
                editHarvest();
            }
            if(e.getSource() == hp.okBtn)
            {
                updateHarvest();
            }
            if(e.getSource() == hp.deleteBtn)
            {
                deleteHarvest();
            }
            if(e.getSource() == hp.cancelBtn)
            {
                hp.editDialog.dispose();
            }
            if(e.getSource() == hp.viewMenuItem)
            {
                viewHarvest();
            }
            if(e.getSource() == hp.editMenuItem)
            {
                editHarvest();
            }
            if(e.getSource() == hp.addMenuItem)
            {
                addHarvest();
            }
            if(e.getSource() == hp.deleteMenuItem)
            {
                deleteHarvest();
            }
        }
    }
    
    class HMouse extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent e) 
        {      
            int r = hp.table.rowAtPoint(e.getPoint());
            if (r >= 0 && r < hp.table.getRowCount()) {
                hp.table.setRowSelectionInterval(r, r);
            } else {
                hp.table.clearSelection();
            }

            int rowindex = hp.table.getSelectedRow();
            if (rowindex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(hp, e.getX(), e.getY());
                hp.table.setComponentPopupMenu(hp.popUpMenu);
            }
        }
    }
    
    class HPopUp implements PopupMenuListener
    {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = hp.table.rowAtPoint(SwingUtilities.
                        convertPoint(hp.popUpMenu, new Point(0, 0), hp.table));
                if (rowAtPoint > -1) {
                    hp.table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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
