package far;
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
import util.BeneCBBHandler;
import util.DisasterCBBHandler;
import util.SearchModel;

public class FarController
{
    FarPanel fp;
    public FarController(FarPanel fp)
    {
        this.fp = fp;
        this.fp.allListener(new Action(), new PopUp(), new Mouse(), 
                new BeneCBBHandler(fp.beneCBB), 
                new DisasterCBBHandler(fp.disCBB),
                new BeneCBBHandler(fp.beneCBB1), 
                new DisasterCBBHandler(fp.disCBB1));
        
        displayAllFar();
    }
    void addFar()
    {
        fp.addDialog.setTitle("Add FAR");
        fp.addDialog.setModal(true);
        fp.addDialog.pack();
        fp.addDialog.setLocationRelativeTo(null);
        fp.addDialog.setVisible(true);
        clearFields();
    }
    void clearFields()
    {
        JTextField text = (JTextField) fp.beneCBB.getEditor().getEditorComponent();
        text.setText("");
        JTextField text1 = (JTextField) fp.disCBB.getEditor().getEditorComponent();
        text1.setText("");
        fp.providerTF.setText("");
        fp.qtySpin.setValue(0);
        fp.costSpin.setValue(0);
        fp.dateDC.setDate(new Date());
        
        JTextField text2 = (JTextField) fp.beneCBB1.getEditor().getEditorComponent();
        text2.setText("");
        JTextField text3 = (JTextField) fp.disCBB1.getEditor().getEditorComponent();
        text3.setText("");
        fp.providerTF1.setText("");
        fp.qtySpin1.setValue(0);
        fp.costSpin1.setValue(0);
        fp.dateDC1.setDate(new Date());
    }
    void deleteFar()
    {
        int dataRow = fp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            String id = fp.table.getValueAt(dataRow,0).toString();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (fp, "Would You Like to "
                    + "delete FAR: " + id + "?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                FarModel.deleteFar(id);
                displayAllFar();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(fp, "Please select a FAR to delete.");
        }
    }  
    void displayAllFar()
    {
        ResultSet rs = FarModel.getAllFar();
        fp.table.setModel(DbUtils.resultSetToTableModel(rs));
        // this is to disable editing in the jtable
        for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class))
        {
            TableCellEditor ce = fp.table.getDefaultEditor(c);
            if (ce instanceof DefaultCellEditor)
            {
                ((DefaultCellEditor) ce).setClickCountToStart(Integer.MAX_VALUE);
            }
        }
        fp.table.getColumnModel().getColumn(0).setMinWidth(0);
        fp.table.getColumnModel().getColumn(0).setMaxWidth(50);
        fp.table.getColumnModel().getColumn(0).setPreferredWidth(25);
        new SearchModel(fp, fp.table, fp.searchTF, rs);
    }
    void editFar()
    {
        clearFields();
        int dataRow = fp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            fp.idLbl.setText(fp.table.getValueAt(dataRow,0).toString());
            
            try 
            {
                Date date;
                date = new SimpleDateFormat("yyyy-MM-dd").parse(fp.table.getValueAt(dataRow,4).toString());
                fp.dateDC1.setDate(date); //dob
            } catch (ParseException ex) {
                Logger.getLogger(FarController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            fp.typeCBB1.setSelectedItem(fp.table.getValueAt(dataRow,5).toString());
            fp.qtySpin1.setValue(Integer.parseInt(fp.table.getValueAt(dataRow,6).toString()));
            fp.costSpin1.setValue(Double.parseDouble(fp.table.getValueAt(dataRow,7).toString()));
            fp.providerTF1.setText(fp.table.getValueAt(dataRow,8).toString());
            fp.editDialog.setTitle("Edit FAR");
            fp.editDialog.setModal(true);
            fp.editDialog.pack();
            fp.editDialog.setLocationRelativeTo(null);
            fp.editDialog.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(fp, "Please select FAR to edit.");
        }
    }
    void saveFar()
    {
        Object dis_id = null;
        JTextField text = (JTextField) fp.beneCBB.getEditor().getEditorComponent();
        String str = text.getText();
        JTextField text1 = (JTextField) fp.disCBB.getEditor().getEditorComponent();
        String str1 = text1.getText();
        if(str1.contains(":"))
        {
            dis_id = Integer.parseInt(str1.substring(str1.indexOf(":") + 1,str1.indexOf(")")));
            str1 = str1.substring(str1.indexOf(")") + 2);
        }
        FarModel.saveFar(
        Integer.parseInt(str.substring(str.indexOf(":") + 1,str.indexOf(")"))),
        dis_id,
        str1, Alter.gatDate(fp.dateDC), 
        Alter.getString(fp.typeCBB), Alter.getInt(fp.qtySpin),
        Alter.getDouble(fp.costSpin), fp.providerTF.getText());
        fp.addDialog.dispose();
        displayAllFar();
    }
    void updateFar()
    {

    }
    void viewFar()
    {
        int dataRow = fp.table.getSelectedRow();
        if(dataRow >= 0)
        {
            JOptionPane.showMessageDialog(fp,
                    "ID: " + (fp.table.getValueAt(dataRow,0).toString()) + "\n"
                    + "Beneficiary: " + (fp.table.getValueAt(dataRow,1).toString()) + "\n"
                    + "Disaster ID: " + (fp.table.getValueAt(dataRow,2) == null ? "" : fp.table.getValueAt(dataRow,2).toString()) + "\n"
                    + "During: " + (fp.table.getValueAt(dataRow,3).toString()) + "\n"
                    + "Date: " + (fp.table.getValueAt(dataRow,4).toString()) + "\n"
                    + "type: " + (fp.table.getValueAt(dataRow,5).toString()) + "\n"
                    + "Quantity: " + (fp.table.getValueAt(dataRow,6).toString()) + "\n"
                    + "Cost: " + (fp.table.getValueAt(dataRow,7).toString()) + "\n"
                    + "Provider: " + (fp.table.getValueAt(dataRow,8).toString()),
                    "FAR Info", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(fp, "Please select FAR to edit.");
        }
    }

    class Action implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == fp.addBtn)
            {
                addFar();
            }
            if(e.getSource() == fp.okBtn)
            {
                saveFar();
            }
            if(e.getSource() == fp.editBtn)
            {
                editFar();
            }
            if(e.getSource() == fp.deleteBtn)
            {
                deleteFar();
            }
            if(e.getSource() == fp.cancelBtn)
            {
                fp.addDialog.dispose();
            }
            if(e.getSource() == fp.cancelBtn1)
            {
                fp.editDialog.dispose();
            }
            if(e.getSource() == fp.viewMenuItem)
            {
                viewFar();
            }
            if(e.getSource() == fp.editMenuItem)
            {
                editFar();
            }
            if(e.getSource() == fp.addMenuItem)
            {
                addFar();
            }
            if(e.getSource() == fp.deleteMenuItem)
            {
                deleteFar();
            }
        }
    }
    
    class Mouse extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent e) 
        {      
            int r = fp.table.rowAtPoint(e.getPoint());
            if (r >= 0 && r < fp.table.getRowCount()) {
                fp.table.setRowSelectionInterval(r, r);
            } else {
                fp.table.clearSelection();
            }

            int rowindex = fp.table.getSelectedRow();
            if (rowindex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(fp, e.getX(), e.getY());
                fp.table.setComponentPopupMenu(fp.popUpMenu);
            }
        }
    }
    
    class PopUp implements PopupMenuListener
    {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = fp.table.rowAtPoint(SwingUtilities.
                        convertPoint(fp.popUpMenu, new Point(0, 0), fp.table));
                if (rowAtPoint > -1) {
                    fp.table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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
