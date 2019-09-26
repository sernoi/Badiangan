package admin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Arrays;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellEditor;
import net.proteanit.sql.DbUtils;
public class AdminController 
{
    AdminPanel ap;
    public AdminController(AdminPanel ap)
    {
        this.ap = ap;
  
        this.ap.addAdminListener(new openAddAdminDialog());
        this.ap.saveAdminListener(new SaveAdminClass());
        this.ap.searchAdminListener(new SearchAdminClass());
        this.ap.editAdminListener(new EditAdminClass());
        this.ap.updateAdminListener(new UpdateAdminClass());
        this.ap.deleteAdminListener(new DeleteAdminClass());
        
        displayAllAdmin();
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
    
    class SearchAdminClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
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
    }
    
    /**
     * opens the addAdminDialog to add new admin
     */
    class openAddAdminDialog implements ActionListener
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
                JOptionPane.showMessageDialog(null, "Please select admin to edit.");
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
                JOptionPane.showMessageDialog(null, "Password Mismatch", "Error", JOptionPane.ERROR_MESSAGE);
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
                int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to "
                                   + "Delete Admin: " + adminID + "?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    AdminModel.deleteAdmin(adminID);
                    displayAllAdmin();
                }  
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please select admin to delete.");
            }
        }
    }
}
