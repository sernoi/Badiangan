package admin;
import beneficiary.BeneModel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.util.Arrays;
import javax.swing.DefaultCellEditor;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableCellEditor;
import net.proteanit.sql.DbUtils;
public class AdminController 
{
    AdminPanel ap;
    public AdminController(AdminPanel ap)
    {
        this.ap = ap;
  
        this.ap.allListener(new OpenAddAdminDialog(),
                new SaveAdminClass(), new EditAdminClass(), new UpdateAdminClass(),
                new DeleteAdminClass(), new ResetFieldsClass(), new CloseDialogClass(),
                new SearchAdminClass(), new MyPopUpMenu(), new MyPopUpMenu(),
                new ViewAdminClass(), new EditAdminClass(), new OpenAddAdminDialog(),
                new DeleteAdminClass());
        
        displayAllAdmin();
        
    }
    
    class ViewAdminClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            int dataRow = ap.adminTable.getSelectedRow();
            if(dataRow >= 0)
            {
                JOptionPane.showMessageDialog(ap,
                "Admin ID: " + (ap.adminTable.getValueAt(dataRow,0).toString()) + "\n" +
                "Username: " + (ap.adminTable.getValueAt(dataRow,1).toString()) + "\n" +
                "First Name: " + (ap.adminTable.getValueAt(dataRow,2).toString()) + "\n" +
                "Middle Name: " + (ap.adminTable.getValueAt(dataRow,3).toString()) + "\n" +
                "Last Name: " + (ap.adminTable.getValueAt(dataRow,4).toString()) + "\n" +
                "Department: " + (ap.adminTable.getValueAt(dataRow,5).toString()) + "\n" +
                "Position: " + (ap.adminTable.getValueAt(dataRow,6).toString())  
                , "Admin Info", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(ap, "Please select admin to edit.");
            }
        }
    }
    
    class MyPopUpMenu extends MouseAdapter implements  PopupMenuListener
    {
        
        @Override
        public void mouseReleased(MouseEvent e) 
        {      
            int r = ap.adminTable.rowAtPoint(e.getPoint());
            if (r >= 0 && r < ap.adminTable.getRowCount()) {
                ap.adminTable.setRowSelectionInterval(r, r);
            } else {
                ap.adminTable.clearSelection();
            }

            int rowindex = ap.adminTable.getSelectedRow();
            if (rowindex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(ap, e.getX(), e.getY());
                ap.adminTable.setComponentPopupMenu(ap.popupmenu);
            }
        }
        
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = ap.adminTable.rowAtPoint(SwingUtilities.
                        convertPoint(ap.popupmenu, new Point(0, 0), ap.adminTable));
                if (rowAtPoint > -1) {
                    ap.adminTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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
    
    class SearchAdminClass implements KeyListener
    {
        void searchNow()
        {
            ResultSet rs = AdminModel.searchAdmin(ap.searchTF.getText());
            ap.adminTable.setModel(DbUtils.resultSetToTableModel(rs));
            // this is to disable editing in the jtable
            for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class)) 
            {
                TableCellEditor ce = ap.adminTable.getDefaultEditor(c);
                if (ce instanceof DefaultCellEditor) 
                {
                    ((DefaultCellEditor) ce).setClickCountToStart(Integer.MAX_VALUE);
                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            searchNow();
        }

        @Override
        public void keyPressed(KeyEvent e){
            searchNow();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            searchNow();
        }
    }
    
    /**
     * opens the addAdminDialog to add new admin
     */
    class OpenAddAdminDialog implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            ap.addAdminDialog.setTitle("Add Admin");
            ap.addAdminDialog.setModal(true);
            ap.addAdminDialog.pack();
            ap.addAdminDialog.setLocationRelativeTo(ap);
            ap.addAdminDialog.setVisible(true);
        }
    }
    
    class SaveAdminClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(ap.jPasswordField1.getText().equals(ap.jPasswordField2.getText()))
            {
                AdminModel.saveAdmin(ap.unTF.getText(),
                        ap.jPasswordField1.getText(), 
                        ap.departmentCB.getSelectedItem().toString(), 
                        ap.positionCB.getSelectedItem().toString(), 
                        ap.fNameTF.getText(), 
                        ap.lNameTF.getText(),
                        ap.mNameTF.getText());
                displayAllAdmin();
            }
            else
            {
                JOptionPane.showMessageDialog(ap, "Password Mismatch", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    class EditAdminClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int dataRow = ap.adminTable.getSelectedRow();
            if(dataRow >= 0)
            {
                ap.oldIdTF.setText(ap.adminTable.getValueAt(dataRow,0).toString());
                ap.unTF1.setText(ap.adminTable.getValueAt(dataRow,1).toString());
                ap.fNameTF1.setText(ap.adminTable.getValueAt(dataRow,2).toString());
                ap.mNameTF1.setText(ap.adminTable.getValueAt(dataRow,3).toString());
                ap.lNameTF1.setText(ap.adminTable.getValueAt(dataRow,4).toString());
                ap.departmentCB1.setSelectedItem(ap.adminTable.getValueAt(dataRow,5).toString());
                ap.positionCB1.setSelectedItem(ap.adminTable.getValueAt(dataRow,6).toString());
                ap.editAdminDialog.setTitle("Edit Admin");
                ap.editAdminDialog.setModal(true);
                ap.editAdminDialog.pack();
                ap.editAdminDialog.setLocationRelativeTo(ap);
                ap.editAdminDialog.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(ap, "Please select admin to edit.");
            }
        }
    }
    
    class UpdateAdminClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(ap.jPasswordField3.getText().equals(ap.jPasswordField4.getText()))
            {
                AdminModel.updateAdmin(ap.oldIdTF.getText(), ap.unTF1.getText(),
                   ap.jPasswordField3.getText(), ap.fNameTF1.getText(), ap.mNameTF1.getText(),
                   ap.lNameTF1.getText(), ap.departmentCB1.getSelectedItem().toString(),
                   ap.positionCB1.getSelectedItem().toString());
                displayAllAdmin();
            }
            else
            {
                JOptionPane.showMessageDialog(ap, "Password Mismatch", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    class DeleteAdminClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int dataRow = ap.adminTable.getSelectedRow();
            if(dataRow >= 0)
            {
                String adminID = ap.adminTable.getValueAt(dataRow,0).toString();
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (ap, "Would You Like to "
                                   + "Delete Admin: " + adminID + "?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    AdminModel.deleteAdmin(adminID);
                    displayAllAdmin();
                }  
            }
            else
            {
                JOptionPane.showMessageDialog(ap, "Please select admin to delete.");
            }
        }
    }
    
    class CloseDialogClass extends WindowAdapter
    {
        @Override 
            public void windowClosing(WindowEvent e){
            clearFields();
        }
    }
    
    class ResetFieldsClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearFields();
        }
    }
    
    /**
     * Gets all the rows in admin table, put in the table of AdminPanel
     */
    void displayAllAdmin()
    {
        //this is to load all the schedules in the database upon selecting the Event Scheduler in the menu bar
        ResultSet rs = AdminModel.getAllAdmins();
        ap.adminTable.setModel(DbUtils.resultSetToTableModel(rs));
        // this is to disable editing in the jtable
        for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class)) 
        {
            TableCellEditor ce = ap.adminTable.getDefaultEditor(c);
            if (ce instanceof DefaultCellEditor) 
            {
                ((DefaultCellEditor) ce).setClickCountToStart(Integer.MAX_VALUE);
            }
        }
    }
    
    /**
     * Clears the fields in the jDialog
     * Invoked by reset button and close operation
     */
    void clearFields()
    {
        //clears all fields in addAdminDialog
        ap.unTF.setText("");
        ap.fNameTF.setText("");
        ap.mNameTF.setText("");
        ap.lNameTF.setText("");
        ap.departmentCB.setSelectedIndex(0);
        ap.positionCB.setSelectedIndex(0);
        ap.jPasswordField1.setText("");
        ap.jPasswordField2.setText("");
        
        //clears all fields in editAdminDialog
        ap.unTF1.setText("");
        ap.fNameTF1.setText("");
        ap.mNameTF1.setText("");
        ap.lNameTF1.setText("");
        ap.departmentCB1.setSelectedIndex(0);
        ap.positionCB1.setSelectedIndex(0);
        ap.jPasswordField3.setText("");
        ap.jPasswordField4.setText("");
    }
}
