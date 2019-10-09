package crop;
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
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableCellEditor;
import net.proteanit.sql.DbUtils;
import util.ComboKeyHandler;
import util.SearchModel;

public class CropController
{
    CropPanel cp;
    public CropController(CropPanel cp)
    {
        this.cp = cp;
        this.cp.allListener(new CCAction(), new CCPopUp(), new CCMouse(), 
                new ComboKeyHandler(cp.beneCB), new ComboKeyHandler(cp.beneCB1));
        
        displayAllCrops();
    }
    void addCrop()
    {
        cp.addDialog.setTitle("Add Crop");
        cp.addDialog.setModal(true);
        cp.addDialog.pack();
        cp.addDialog.setLocationRelativeTo(null);
        cp.addDialog.setVisible(true);
        clearFields();
    }
    void clearFields()
    {
        JTextField text = (JTextField) cp.beneCB.getEditor().getEditorComponent();
        text.setText("");
        cp.cropTF.setText("");
        cp.areaTF.setText("");
        cp.varietyTF.setText("");
        cp.classificationTF.setText("");
        cp.remarksTA.setText("");
        
        JTextField text1 = (JTextField) cp.beneCB1.getEditor().getEditorComponent();
        text1.setText("");
        cp.cropTF1.setText("");
        cp.areaTF1.setText("");
        cp.varietyTF1.setText("");
        cp.classificationTF1.setText("");
        cp.remarksTA1.setText("");
    }
    void deleteCrop()
    {
        int dataRow = cp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            String id = cp.table.getValueAt(dataRow,0).toString();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (cp, "Would You Like to "
                    + "delete crop: " + id + "?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                CropModel.deleteCrop(id);
                displayAllCrops();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(cp, "Please select a crop to delete.");
        }
    }  
    void displayAllCrops()
    {
        ResultSet rs = CropModel.getAllCrop();
        cp.table.setModel(DbUtils.resultSetToTableModel(rs));
        // this is to disable editing in the jtable
        for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class))
        {
            TableCellEditor ce = cp.table.getDefaultEditor(c);
            if (ce instanceof DefaultCellEditor)
            {
                ((DefaultCellEditor) ce).setClickCountToStart(Integer.MAX_VALUE);
            }
        }
        cp.table.getColumnModel().getColumn(0).setMinWidth(0);
        cp.table.getColumnModel().getColumn(0).setMaxWidth(50);
        cp.table.getColumnModel().getColumn(0).setPreferredWidth(25);
        new SearchModel(cp, cp.table, cp.searchTF, rs);
    }
    void editCrop()
    {
        int dataRow = cp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            cp.idLbl.setText(cp.table.getValueAt(dataRow,0).toString());
            cp.cropTF1.setText(cp.table.getValueAt(dataRow,2).toString());
            cp.areaTF1.setText(cp.table.getValueAt(dataRow,3).toString());
            cp.varietyTF1.setText(cp.table.getValueAt(dataRow,4).toString());
            cp.classificationTF1.setText(cp.table.getValueAt(dataRow,5).toString());
            
            try 
            {
                Date date;
                date = new SimpleDateFormat("yyyy-MM-dd").parse(cp.table.getValueAt(dataRow,6).toString());
                cp.expDC1.setDate(date); //dob
            } catch (ParseException ex) {
                Logger.getLogger(CropController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            cp.remarksTA1.setText(cp.table.getValueAt(dataRow,7).toString());
            cp.editDialog.setTitle("Edit Crop");
            cp.editDialog.setModal(true);
            cp.editDialog.pack();
            cp.editDialog.setLocationRelativeTo(cp);
            cp.editDialog.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(cp, "Please select crop to edit.");
        }
    }
    void saveCrop()
    {
        JTextField text = (JTextField) cp.beneCB.getEditor().getEditorComponent();
        String str = text.getText();
        CropModel.saveCrop(
                Integer.parseInt(str.substring(str.indexOf(":") + 1,str.indexOf(")"))),
                cp.cropTF.getText(),
                cp.areaTF.getText(),
                cp.varietyTF.getText(),
                cp.classificationTF.getText(),
                ((JTextField)cp.expDC.getDateEditor().getUiComponent()).getText(),
                cp.remarksTA.getText());
        cp.addDialog.dispose();
        displayAllCrops();
    }
    void viewCrop()
    {
        int dataRow = cp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            JOptionPane.showMessageDialog(cp,
            "ID: " + (cp.table.getValueAt(dataRow,0).toString()) + "\n" 
            + "Beneficiary: " + (cp.table.getValueAt(dataRow,1).toString()) + "\n" 
            + "Crop/Tree: " + (cp.table.getValueAt(dataRow,2).toString()) + "\n" 
            + "Area: " + (cp.table.getValueAt(dataRow,3).toString()) + "\n" 
            + "Variety: " + (cp.table.getValueAt(dataRow,4).toString()) + "\n" 
            + "Classification: " + (cp.table.getValueAt(dataRow,5).toString()) + "\n" 
            + "Exp Harvest Date: " + (cp.table.getValueAt(dataRow,6).toString()) + "\n" 
            + "Remarks: " + (cp.table.getValueAt(dataRow,7).toString()),
            "Crop Info", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(cp, "Please select crop to edit.");
        }
    }
    void updateCrop()
    {
        JTextField text = (JTextField) cp.beneCB1.getEditor().getEditorComponent();
        String str = text.getText();
        CropModel.updateCrop(
                Integer.parseInt(cp.idLbl.getText()),
                Integer.parseInt(str.substring(str.indexOf(":") + 1,str.indexOf(")"))),
                cp.cropTF1.getText(),
                cp.areaTF1.getText(),
                cp.varietyTF1.getText(),
                cp.classificationTF1.getText(),
                ((JTextField)cp.expDC1.getDateEditor().getUiComponent()).getText(),
                cp.remarksTA1.getText());
        cp.editDialog.dispose();
        displayAllCrops();
    }

    class CCAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == cp.addBtn)
            {
                addCrop();
            }
            if(e.getSource() == cp.saveBtn)
            {
                saveCrop();
            }
            if(e.getSource() == cp.editBtn)
            {
                editCrop();
            }
            if(e.getSource() == cp.updateBtn)
            {
                updateCrop();
            }
            if(e.getSource() == cp.deleteBtn)
            {
                deleteCrop();
            }
            if(e.getSource() == cp.cancelBtn)
            {
                cp.addDialog.dispose();
            }
            if(e.getSource() == cp.cancelBtn1)
            {
                cp.editDialog.dispose();
            }
            if(e.getSource() == cp.viewMenuItem)
            {
                viewCrop();
            }
            if(e.getSource() == cp.editMenuItem)
            {
                editCrop();
            }
            if(e.getSource() == cp.addMenuItem)
            {
                addCrop();
            }
            if(e.getSource() == cp.deleteMenuItem)
            {
                deleteCrop();
            }
        }
    }
    
    class CCMouse extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent e) 
        {      
            int r = cp.table.rowAtPoint(e.getPoint());
            if (r >= 0 && r < cp.table.getRowCount()) {
                cp.table.setRowSelectionInterval(r, r);
            } else {
                cp.table.clearSelection();
            }

            int rowindex = cp.table.getSelectedRow();
            if (rowindex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(cp, e.getX(), e.getY());
                cp.table.setComponentPopupMenu(cp.popUpMenu);
            }
        }
    }
    
    class CCPopUp implements PopupMenuListener
    {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = cp.table.rowAtPoint(SwingUtilities.
                        convertPoint(cp.popUpMenu, new Point(0, 0), cp.table));
                if (rowAtPoint > -1) {
                    cp.table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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