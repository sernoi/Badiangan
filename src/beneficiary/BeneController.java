package beneficiary;
import admin.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Arrays;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellEditor;
import net.proteanit.sql.DbUtils;
public class BeneController 
{
    BenePanel bp;
    public BeneController(BenePanel ap)
    {
        this.bp = ap;
  
        this.bp.addAdminListener(new openAddAdminDialog());
        this.bp.saveAdminListener(new SaveAdminClass());
        this.bp.searchAdminListener(new SearchAdminClass());
        this.bp.editAdminListener(new EditAdminClass());
        this.bp.updateAdminListener(new UpdateAdminClass());
        this.bp.deleteAdminListener(new DeleteAdminClass());
        
        displayAllBene();
    }
    
    /**
     * Gets all the rows in beneficiary table, put in the table of BenePanel
     */
    void displayAllBene()
    {
        //this is to load all the schedules in the database upon selecting the Event Scheduler in the menu bar
        ResultSet rs = BeneModel.getAllBene();
        bp.beneTable.setModel(DbUtils.resultSetToTableModel(rs));
        // this is to disable editing in the jtable
        for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class)) 
        {
            TableCellEditor ce = bp.beneTable.getDefaultEditor(c);
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
            ResultSet rs = BeneModel.searchAdmin(bp.searchTF.getText());
            bp.beneTable.setModel(DbUtils.resultSetToTableModel(rs));
            // this is to disable editing in the jtable
            for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class)) 
            {
                TableCellEditor ce = bp.beneTable.getDefaultEditor(c);
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
            bp.addAdminDialog.setTitle("Add Admin");
            bp.addAdminDialog.setModal(true);
            bp.addAdminDialog.pack();
            bp.addAdminDialog.setLocationRelativeTo(bp);
            bp.addAdminDialog.setVisible(true);
        }
    }
    
    class SaveAdminClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(bp.jPasswordField1.getText().equals(bp.jPasswordField2.getText()))
            {
                BeneModel.saveAdmin(bp.unTF.getText(),
                        bp.jPasswordField1.getText(), 
                        bp.departmentCB.getSelectedItem().toString(), 
                        bp.positionCB.getSelectedItem().toString(), 
                        bp.fNameTF.getText(), 
                        bp.lNameTF.getText(),
                        bp.mNameTF.getText());
                displayAllBene();
            }
            else
            {
                JOptionPane.showMessageDialog(bp, "Password Mismatch", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    class EditAdminClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int dataRow = bp.beneTable.getSelectedRow();
            if(dataRow >= 0)
            {
                bp.oldIdTF.setText(bp.beneTable.getValueAt(dataRow,0).toString());
                bp.unTF1.setText(bp.beneTable.getValueAt(dataRow,1).toString());
                bp.fNameTF1.setText(bp.beneTable.getValueAt(dataRow,2).toString());
                bp.mNameTF1.setText(bp.beneTable.getValueAt(dataRow,3).toString());
                bp.lNameTF1.setText(bp.beneTable.getValueAt(dataRow,4).toString());
                bp.departmentCB1.setSelectedItem(bp.beneTable.getValueAt(dataRow,5).toString());
                bp.positionCB1.setSelectedItem(bp.beneTable.getValueAt(dataRow,6).toString());
                bp.editAdminDialog.setTitle("Edit Admin");
                bp.editAdminDialog.setModal(true);
                bp.editAdminDialog.pack();
                bp.editAdminDialog.setLocationRelativeTo(bp);
                bp.editAdminDialog.setVisible(true);
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
            if(bp.jPasswordField3.getText().equals(bp.jPasswordField4.getText()))
            {
                BeneModel.updateAdmin(bp.oldIdTF.getText(), bp.unTF1.getText(),
                   bp.jPasswordField3.getText(), bp.fNameTF1.getText(), bp.mNameTF1.getText(),
                   bp.lNameTF1.getText(), bp.departmentCB1.getSelectedItem().toString(),
                   bp.positionCB1.getSelectedItem().toString());
                displayAllBene();
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
            int dataRow = bp.beneTable.getSelectedRow();
            if(dataRow >= 0)
            {
                String adminID = bp.beneTable.getValueAt(dataRow,0).toString();
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to "
                                   + "Delete Admin: " + adminID + "?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    BeneModel.deleteAdmin(adminID);
                    displayAllBene();
                }  
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please select admin to delete.");
            }
        }
    }
}
