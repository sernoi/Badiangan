package livestock;
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
import util.Alter;
import util.ComboKeyHandler;
import util.SearchModel;

public class LSController
{
    LSPanel lsp;
    public LSController(LSPanel lsp)
    {
        this.lsp = lsp;
        this.lsp.allListener(new LSAction(), new LSPopUp(), new LSMouse(), 
                new ComboKeyHandler(lsp.beneCB), new ComboKeyHandler(lsp.beneCB1));
        
        displayAllLS();
    }
    void addLS()
    {
        lsp.addDialog.setTitle("Add LS");
        lsp.addDialog.setModal(true);
        lsp.addDialog.pack();
        lsp.addDialog.setLocationRelativeTo(null);
        lsp.addDialog.setVisible(true);
        clearFields();
    }
    void clearFields()
    {
        JTextField text = (JTextField) lsp.beneCB.getEditor().getEditorComponent();
        text.setText("");
        lsp.lsTF.setText("");
        lsp.classificationTF.setText("");
        lsp.headsSpin.setValue(0);
        lsp.ageSpin.setValue(0);
        lsp.remarksTA.setText("");
    }
    void deleteLS()
    {
        int dataRow = lsp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            String id = lsp.table.getValueAt(dataRow,0).toString();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (lsp, "Would You Like to "
                    + "delete livestock: " + id + "?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                LSModel.deleteLS(id);
                displayAllLS();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(lsp, "Please select a ls to delete.");
        }
    }  
    void displayAllLS()
    {
        ResultSet rs = LSModel.getAllLS();
        lsp.table.setModel(DbUtils.resultSetToTableModel(rs));
        // this is to disable editing in the jtable
        for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class))
        {
            TableCellEditor ce = lsp.table.getDefaultEditor(c);
            if (ce instanceof DefaultCellEditor)
            {
                ((DefaultCellEditor) ce).setClickCountToStart(Integer.MAX_VALUE);
            }
        }
        lsp.table.getColumnModel().getColumn(0).setMinWidth(0);
        lsp.table.getColumnModel().getColumn(0).setMaxWidth(50);
        lsp.table.getColumnModel().getColumn(0).setPreferredWidth(25);
        new SearchModel(lsp, lsp.table, lsp.searchTF, rs);
    }
    void editLS()
    {
        int dataRow = lsp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            lsp.idLbl.setText(lsp.table.getValueAt(dataRow,0).toString());
            lsp.lsTF1.setText(lsp.table.getValueAt(dataRow,2).toString());
            lsp.classificationTF1.setText(lsp.table.getValueAt(dataRow,3).toString());
            lsp.headsSpin1.setValue(Alter.toInt(lsp.table.getValueAt(dataRow,4).toString()));
            lsp.ageSpin1.setValue(Alter.toInt(lsp.table.getValueAt(dataRow,5).toString()));
            
            try 
            {
                Date date;
                date = new SimpleDateFormat("yyyy-MM-dd").parse(lsp.table.getValueAt(dataRow,6).toString());
                lsp.expDC1.setDate(date); //dob
            } catch (ParseException ex) {
                Logger.getLogger(LSController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            lsp.remarksTA1.setText(lsp.table.getValueAt(dataRow,7).toString());
            lsp.editDialog.setTitle("Edit Livestock");
            lsp.editDialog.setModal(true);
            lsp.editDialog.pack();
            lsp.editDialog.setLocationRelativeTo(lsp);
            lsp.editDialog.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(lsp, "Please select ls to edit.");
        }
    }
    void saveLS()
    {
        JTextField text = (JTextField) lsp.beneCB.getEditor().getEditorComponent();
        String str = text.getText();
        LSModel.saveLS(
                Integer.parseInt(str.substring(str.indexOf(":") + 1,str.indexOf(")"))),
                lsp.lsTF.getText(),
                lsp.classificationTF.getText(),
                Alter.getVal(lsp.headsSpin),
                Alter.getVal(lsp.ageSpin),
                ((JTextField)lsp.expDC.getDateEditor().getUiComponent()).getText(),
                lsp.remarksTA.getText());
        lsp.addDialog.dispose();
        displayAllLS();
    }
    void viewLS()
    {
        int dataRow = lsp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            JOptionPane.showMessageDialog(lsp,
            "ID: " + (lsp.table.getValueAt(dataRow,0).toString()) + "\n" 
            + "Beneficiary: " + (lsp.table.getValueAt(dataRow,1).toString()) + "\n" 
            + "Livestock Raised: " + (lsp.table.getValueAt(dataRow,2).toString()) + "\n" 
            + "Area: " + (lsp.table.getValueAt(dataRow,3).toString()) + "\n" 
            + "Variety: " + (lsp.table.getValueAt(dataRow,4).toString()) + "\n" 
            + "Classification: " + (lsp.table.getValueAt(dataRow,5).toString()) + "\n" 
            + "Exp Harvest Date: " + (lsp.table.getValueAt(dataRow,6).toString()) + "\n" 
            + "Remarks: " + (lsp.table.getValueAt(dataRow,7).toString()),
            "LS Info", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(lsp, "Please select ls to edit.");
        }
    }
    void updateLS()
    {
        JTextField text = (JTextField) lsp.beneCB1.getEditor().getEditorComponent();
        String str = text.getText();
        LSModel.updateLS(
                Integer.parseInt(lsp.idLbl.getText()),
                Integer.parseInt(str.substring(str.indexOf(":") + 1,str.indexOf(")"))),
                lsp.lsTF1.getText(),
                lsp.classificationTF1.getText(),
                Integer.parseInt(lsp.headsSpin1.getValue().toString()),
                Integer.parseInt(lsp.ageSpin1.getValue().toString()),
                ((JTextField)lsp.expDC1.getDateEditor().getUiComponent()).getText(),
                lsp.remarksTA1.getText());
        lsp.editDialog.dispose();
        displayAllLS();
    }

    class LSAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == lsp.addBtn)
            {
                addLS();
            }
            if(e.getSource() == lsp.saveBtn)
            {
                saveLS();
            }
            if(e.getSource() == lsp.editBtn)
            {
                editLS();
            }
            if(e.getSource() == lsp.updateBtn)
            {
                updateLS();
            }
            if(e.getSource() == lsp.deleteBtn)
            {
                deleteLS();
            }
            if(e.getSource() == lsp.cancelBtn)
            {
                lsp.addDialog.dispose();
            }
            if(e.getSource() == lsp.cancelBtn1)
            {
                lsp.editDialog.dispose();
            }
            if(e.getSource() == lsp.viewMenuItem)
            {
                viewLS();
            }
            if(e.getSource() == lsp.editMenuItem)
            {
                editLS();
            }
            if(e.getSource() == lsp.addMenuItem)
            {
                addLS();
            }
            if(e.getSource() == lsp.deleteMenuItem)
            {
                deleteLS();
            }
        }
    }
    
    class LSMouse extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent e) 
        {      
            int r = lsp.table.rowAtPoint(e.getPoint());
            if (r >= 0 && r < lsp.table.getRowCount()) {
                lsp.table.setRowSelectionInterval(r, r);
            } else {
                lsp.table.clearSelection();
            }

            int rowindex = lsp.table.getSelectedRow();
            if (rowindex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(lsp, e.getX(), e.getY());
                lsp.table.setComponentPopupMenu(lsp.popUpMenu);
            }
        }
    }
    
    class LSPopUp implements PopupMenuListener
    {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = lsp.table.rowAtPoint(SwingUtilities.
                        convertPoint(lsp.popUpMenu, new Point(0, 0), lsp.table));
                if (rowAtPoint > -1) {
                    lsp.table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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
