package beneficiary;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.ButtonModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import net.proteanit.sql.DbUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import util.BrgyModel;
public class BeneController 
{
    BenePanel bp;
    public BeneController(BenePanel ap)
    {
        this.bp = ap;
  
        this.bp.allListener(
                //Bene Classes
                new OpenAddBeneClass(), new SaveBeneClass(),
                new EditBeneClass(), new UpdateBeneClass(), new DeleteBeneClass(),
                new SearchBeneClass(), new SearchBeneClass(),
                
                //Members Classes
                new MemberPopUpMenu(), new MemberPopUpMenu(), new ViewMemberClass(), 
                new EditMemberClass(), new OpenAddMemberClass(), new DeleteMemberClass(),
                new OkAddMemberClass(), new CloseAddMemberDialogClass(),
                new CloseAddMemberDialogClass(),
                new OkEditMemberClass(), new CloseEditMemberDialogClass(),
                new CloseEditMemberDialogClass(),
                
                //Crop Classes
                new CropPopUpMenu(), new CropPopUpMenu(), new ViewCropClass(), 
                new EditCropClass(), new OpenAddCropClass(), new DeleteCropClass(),
                new OkAddCropClass(), new CloseAddCropDialogClass(),
                new CloseAddCropDialogClass(),
                new OkEditCropClass(), new CloseEditCropDialogClass(),
                new CloseEditCropDialogClass()
        
        );
        
        // TODO ubra add member dialog 
        
        initRGB();
        displayAllBene();
        JDateChooserInit();
        
    }
    
    
    class OkAddMemberClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel) bp.membersTable.getModel();
            model.addRow(new Object[]{
                bp.membersTable.getRowCount() + 1, bp.fnameAddMemberTF.getText(),
                bp.mnameAddMemberTF.getText(), bp.lnameAddMemberTF.getText(),
                bp.relAddMemberCB.getSelectedItem().toString(), bp.ageAddMemberTF.getText(),
                bp.maleAddMemberRB.isSelected() ? "Male" : "Female",
                bp.heaAddMemberCB.getSelectedItem().toString(), bp.occAddMemberTF.getText(),
                bp.remarksAddMemberTA.getText()});
            clearAddMemberFields();
            bp.addMemberDialog.dispose();
        }
    }
    
    class OkEditMemberClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel) bp.membersTable.getModel();
            model.removeRow(Integer.parseInt(bp.numEditMemberTF.getText()) - 1);
            model.insertRow(Integer.parseInt(bp.numEditMemberTF.getText()) - 1, new Object[]{
                bp.numEditMemberTF.getText(), bp.fnameEditMemberTF.getText(),
                bp.mnameEditMemberTF.getText(), bp.lnameEditMemberTF.getText(),
                bp.relEditMemberCB.getSelectedItem().toString(), bp.ageEditMemberTF.getText(),
                bp.maleEditMemberRB.isSelected() ? "Male" : "Female",
                bp.heaEditMemberCB.getSelectedItem().toString(), bp.occEditMemberTF.getText(),
                bp.remarksEditMemberTA.getText()});
            clearEditMemberFields();
            bp.editMemberDialog.dispose();
        }
    }
    
    class OkEditCropClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel) bp.cropTable.getModel();
            model.removeRow(Integer.parseInt(bp.numberEditCropLbl.getText()) - 1);
            model.insertRow(Integer.parseInt(bp.numberEditCropLbl.getText()) - 1, new Object[]{
                bp.numberEditCropLbl.getText(), bp.cropEditCropTF.getText(),
                bp.areaEditCropTF.getText(), bp.varietyEditCropTF.getText(),
                bp.classificationEditCropTF.getText(),
                ((JTextField)bp.expHarvestAddCropDC.getDateEditor().getUiComponent()).getText(),
                bp.remarksEditCropTA.getText()});
            clearEditCropFields();
            bp.editCropDialog.dispose();
        }
    }
    
    class OkAddCropClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel) bp.cropTable.getModel();
            model.addRow(new Object[]{
                bp.cropTable.getRowCount() + 1, bp.cropAddCropTF.getText(),
                bp.areaAddCropTF.getText(), bp.varietyAddCropTF.getText(),
                bp.classificationAddCropTF.getText(), 
                ((JTextField)bp.expHarvestAddCropDC.getDateEditor().getUiComponent()).getText(),
                bp.remarksAddCropTA.getText()});
            clearAddCropFields();
            bp.addCropDialog.dispose();
        }
    }
    
    
    class CropPopUpMenu extends MouseAdapter implements  PopupMenuListener
    {
        
        @Override
        public void mouseReleased(MouseEvent e) 
        {      
            int r = bp.cropTable.rowAtPoint(e.getPoint());
            if (r >= 0 && r < bp.cropTable.getRowCount()) {
                bp.cropTable.setRowSelectionInterval(r, r);
            } else {
                bp.cropTable.clearSelection();
            }

            int rowindex = bp.cropTable.getSelectedRow();
            if (rowindex < 0){
                JPopupMenu popup = new JPopupMenu();
                popup.show(bp.addBeneDialog, e.getX(), e.getY());
                bp.cropTable.setComponentPopupMenu(bp.cropPopMenu);
                //return;
            }
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(bp.addBeneDialog, e.getX(), e.getY());
                bp.cropTable.setComponentPopupMenu(bp.cropPopMenu);
            }
        }
        
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = bp.cropTable.rowAtPoint(SwingUtilities.
                        convertPoint(bp.cropPopMenu, new Point(0, 0), bp.cropTable));
                if (rowAtPoint > -1) {
                    bp.cropTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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
    
    
    class MemberPopUpMenu extends MouseAdapter implements  PopupMenuListener
    {
        
        @Override
        public void mouseReleased(MouseEvent e) 
        {      
            int r = bp.membersTable.rowAtPoint(e.getPoint());
            if (r >= 0 && r < bp.membersTable.getRowCount()) {
                bp.membersTable.setRowSelectionInterval(r, r);
            } else {
                bp.membersTable.clearSelection();
            }

            int rowindex = bp.membersTable.getSelectedRow();
            if (rowindex < 0){
                JPopupMenu popup = new JPopupMenu();
                popup.show(bp.addBeneDialog, e.getX(), e.getY());
                bp.membersTable.setComponentPopupMenu(bp.membersPopMenu);
                //return;
            }
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(bp.addBeneDialog, e.getX(), e.getY());
                bp.membersTable.setComponentPopupMenu(bp.membersPopMenu);
            }
        }
        
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = bp.membersTable.rowAtPoint(SwingUtilities.
                        convertPoint(bp.membersPopMenu, new Point(0, 0), bp.membersTable));
                if (rowAtPoint > -1) {
                    bp.membersTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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
    
    class SearchBeneClass implements ActionListener, KeyListener
    {
        void searchNow()
        {
            ResultSet rs = BeneModel.searchBene(bp.searchTF.getText());
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
        
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            searchNow();
        }

        @Override
        public void keyTyped(KeyEvent e) {
            searchNow();
        }

        @Override
        public void keyPressed(KeyEvent e){
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
    
    class ViewMemberClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            int dataRow = bp.membersTable.getSelectedRow();
            if(dataRow >= 0)
            {
                JOptionPane.showMessageDialog(bp.addBeneDialog,    
                "#: " + (bp.membersTable.getValueAt(dataRow,0).toString()) + "\n" +
                "First Name: " + (bp.membersTable.getValueAt(dataRow,1).toString()) + "\n" +
                "Middle Name: " + (bp.membersTable.getValueAt(dataRow,2).toString()) + "\n" +
                "Last Name: " + (bp.membersTable.getValueAt(dataRow,3).toString()) + "\n" +
                "Rel to HOD: " + (bp.membersTable.getValueAt(dataRow,4).toString()) + "\n" +
                "Age: " + (bp.membersTable.getValueAt(dataRow,5).toString()) + "\n" +
                "Sex: " + (bp.membersTable.getValueAt(dataRow,6).toString()) + "\n" +
                "Educ: " + (bp.membersTable.getValueAt(dataRow,7).toString()) + "\n" +
                "Occ Skills: " + (bp.membersTable.getValueAt(dataRow,8).toString()) + "\n" +        
                "Remarks: " + (bp.membersTable.getValueAt(dataRow,9).toString()),
                "Family Member Info", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(bp.addBeneDialog, 
                        "Please select a family member to view.", "No Item Selected",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    class ViewCropClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            int dataRow = bp.cropTable.getSelectedRow();
            if(dataRow >= 0)
            {
                JOptionPane.showMessageDialog(bp.addBeneDialog,    
                "#: " + (bp.cropTable.getValueAt(dataRow,0).toString()) + "\n" +
                "Crop/Tree Planted: " + (bp.cropTable.getValueAt(dataRow,1).toString()) + "\n" +
                "Area: " + (bp.cropTable.getValueAt(dataRow,2).toString()) + "\n" +
                "Variety: " + (bp.cropTable.getValueAt(dataRow,3).toString()) + "\n" +
                "Classification: " + (bp.cropTable.getValueAt(dataRow,4).toString()) + "\n" +
                "Exp Date of Harvest: " + (bp.cropTable.getValueAt(dataRow,5).toString()) + "\n" +
                "Remarks: " + (bp.cropTable.getValueAt(dataRow,6).toString()),
                "Crop/Tree Info", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(bp.addBeneDialog, 
                        "Please select a crop to view.", "No Item Selected",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * opens the addAdminDialog to add new Beneficiary
     */
    class OpenAddBeneClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            Date date = new Date(); 
            bp.dobDC.setDate(date);
            bp.brgyCB.setModel(new DefaultComboBoxModel(BrgyModel.getBrgy().toArray()));
            bp.beneIdLbl.setText("" + (BeneModel.getIdOfLatestBene() + 1));
            bp.addBeneDialog.setTitle("Add Beneficiary");
            bp.addBeneDialog.setModal(true);
            bp.addBeneDialog.pack();
            bp.addBeneDialog.setLocationRelativeTo(bp);
            bp.addBeneDialog.setVisible(true);
        }
    }
    
    class SaveBeneClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String brgyStr = bp.brgyCB.getSelectedItem().toString();
            String locStr = bp.longLatLbl.getText();
            
            BeneModel.saveBene(
                    Integer.parseInt(bp.beneIdLbl.getText()), //beneID
                    bp.fNameTF.getText(), //fname
                    bp.mNameTF.getText(), //mname
                    bp.lNameTF.getText(), //lname
                    bp.sexMaleRB.isSelected() ? "Male" : "Female", //sex
                    ((JTextField)bp.dobDC.getDateEditor().getUiComponent()).getText(), //dob
                    Integer.parseInt(brgyStr.substring(0,brgyStr.indexOf(" "))), //brgy
                    bp.codeCB.getSelectedItem().toString(), //code
                    bp.fourpsYesRB.isSelected() ? "Yes" : "No", //fourps
                    bp.indigentYesRB.isSelected() ? "Yes" : "No", //indigent
                    bp.heaCB.getSelectedItem().toString(), //hea
                    bp.ethnicityTF.getText(), //ehtnicity
                    Double.parseDouble(bp.netIncomeTF.getText()), //income
                    bp.occTF.getText(), //occ
                    bp.healthCondCB.getSelectedItem().toString(), //healthCond
                    bp.houseStatCB.getSelectedItem().toString(), //houseStat
                    bp.houseCondCB.getSelectedItem().toString(), //houseCond
                    bp.contactNumTF.getText(),
                    Double.parseDouble(locStr.substring(0,locStr.indexOf(","))), //loc_long
                    Double.parseDouble(locStr.substring(locStr.indexOf(",") + 1, locStr.length()))); //loc_lat
            displayAllBene();
        }
    }
    
    class EditBeneClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {

        }
    }
    
    class EditMemberClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int dataRow = bp.membersTable.getSelectedRow();
            if(dataRow >= 0)
            {
                bp.numEditMemberTF.setText(bp.membersTable.getValueAt(dataRow,0).toString());
                bp.fnameEditMemberTF.setText(bp.membersTable.getValueAt(dataRow,1).toString());
                bp.mnameEditMemberTF.setText(bp.membersTable.getValueAt(dataRow,2).toString());
                bp.lnameEditMemberTF.setText(bp.membersTable.getValueAt(dataRow,3).toString());
                bp.relEditMemberCB.setSelectedItem(bp.membersTable.getValueAt(dataRow,4).toString());
                bp.ageEditMemberTF.setText(bp.membersTable.getValueAt(dataRow,5).toString());
                bp.maleEditMemberRB.setSelected(true);
                bp.heaEditMemberCB.setSelectedItem(bp.membersTable.getValueAt(dataRow,7).toString());
                bp.occEditMemberTF.setText(bp.membersTable.getValueAt(dataRow,8).toString());
                bp.remarksEditMemberTA.setText(bp.membersTable.getValueAt(dataRow,9).toString());
                bp.editMemberDialog.setTitle("Edit Family Member");
                bp.editMemberDialog.setModal(true);
                bp.editMemberDialog.pack();
                bp.editMemberDialog.setLocationRelativeTo(bp.addBeneDialog);
                bp.editMemberDialog.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(bp.addBeneDialog, 
                        "Please select a family member to edit.", "No Item Selected",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    class EditCropClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int dataRow = bp.cropTable.getSelectedRow();
            if(dataRow >= 0)
            {
                bp.numberEditCropLbl.setText(bp.cropTable.getValueAt(dataRow,0).toString());
                bp.cropEditCropTF.setText(bp.cropTable.getValueAt(dataRow,1).toString());
                bp.areaEditCropTF.setText(bp.cropTable.getValueAt(dataRow,2).toString());
                bp.varietyEditCropTF.setText(bp.cropTable.getValueAt(dataRow,3).toString());
                bp.classificationEditCropTF.setText(bp.cropTable.getValueAt(dataRow,4).toString());
                bp.remarksEditCropTA.setText(bp.cropTable.getValueAt(dataRow,6).toString());
                bp.editCropDialog.setTitle("Edit Crop/Tree");
                bp.editCropDialog.setModal(true);
                bp.editCropDialog.pack();
                bp.editCropDialog.setLocationRelativeTo(bp.addBeneDialog);
                bp.editCropDialog.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(bp.addBeneDialog, 
                        "Please select a crop to edit.", "No Item Selected",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
   
    class UpdateBeneClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }
    
    class DeleteBeneClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int dataRow = bp.beneTable.getSelectedRow();
            if(dataRow >= 0)
            {
                String beneID = bp.beneTable.getValueAt(dataRow,0).toString();
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to "
                                   + "Delete beneficiary: " + beneID + "?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    BeneModel.deleteBene(beneID);
                    displayAllBene();
                }  
            }
            else
            {
                JOptionPane.showMessageDialog(bp, 
                        "Please select a beneficiary to delete.", "No Item Selected",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    class DeleteMemberClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int dataRow = bp.membersTable.getSelectedRow();
            if(dataRow >= 0)
            {
                String memberID = bp.membersTable.getValueAt(dataRow,0).toString();
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to "
                                   + "Delete family member: " + memberID + "?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    int row = bp.membersTable.getSelectedRows()[0];
                    DefaultTableModel model = (DefaultTableModel) bp.membersTable.getModel();
                    model.removeRow(row);
                    for(int index = row ;index<model.getRowCount();index++){
                        model.setValueAt(index+1, index, 0); //setValueAt(data,row,column)
                    }
                }  
            }
            else
            {
                JOptionPane.showMessageDialog(bp.addBeneDialog, 
                        "Please select a family member to delete.", "No Item Selected",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    class DeleteCropClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int dataRow = bp.cropTable.getSelectedRow();
            if(dataRow >= 0)
            {
                String cropID = bp.cropTable.getValueAt(dataRow,0).toString();
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to "
                                   + "Delete crop: " + cropID + "?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    int row = bp.cropTable.getSelectedRows()[0];
                    DefaultTableModel model = (DefaultTableModel) bp.cropTable.getModel();
                    model.removeRow(row);
                    for(int index = row ;index<model.getRowCount();index++){
                        model.setValueAt(index+1, index, 0); //setValueAt(data,row,column)
                    }
                }  
            }
            else
            {
                JOptionPane.showMessageDialog(bp.addBeneDialog, 
                        "Please select a crop to delete.", "No Item Selected",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    class OpenAddMemberClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            bp.addMemberDialog.setTitle("Add Family Member");
            bp.addMemberDialog.setModal(true);
            bp.addMemberDialog.pack();
            bp.addMemberDialog.setLocationRelativeTo(bp);
            bp.addMemberDialog.setVisible(true);
        }
    }
    
    class OpenAddCropClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            bp.addCropDialog.setTitle("Add Crop/Tree");
            bp.addCropDialog.setModal(true);
            bp.addCropDialog.pack();
            bp.addCropDialog.setLocationRelativeTo(bp.addBeneDialog);
            bp.addCropDialog.setVisible(true);
        }
    }
    
    class CloseAddMemberDialogClass extends WindowAdapter implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearAddMemberFields();
            bp.addMemberDialog.dispose();
        }
        
        @Override 
        public void windowClosing(WindowEvent e){
            clearAddMemberFields();
            bp.addMemberDialog.dispose();
        }
    }
    
    class CloseEditMemberDialogClass extends WindowAdapter implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearEditMemberFields();
            bp.editMemberDialog.dispose();
        }
        
        @Override 
        public void windowClosing(WindowEvent e){
            clearEditMemberFields();
            bp.editMemberDialog.dispose();
        }
    }
    
    class CloseEditCropDialogClass extends WindowAdapter implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearEditCropFields();
            bp.editCropDialog.dispose();
        }
        
        @Override 
        public void windowClosing(WindowEvent e){
            clearEditCropFields();
            bp.editCropDialog.dispose();
        }
    }
    
    class CloseAddCropDialogClass extends WindowAdapter implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearAddCropFields();
            bp.addCropDialog.dispose();
        }
        
        @Override 
        public void windowClosing(WindowEvent e){
            clearAddCropFields();
            bp.addCropDialog.dispose();
        }
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
    
    void initRGB()
    {
        bp.walkinRBG.add(bp.walkinYesRB);
        bp.walkinRBG.add(bp.walkinNoRB);
        bp.walkinYesRB.setSelected(true);
        
        bp.sexRBGAddBene.add(bp.sexMaleRB);
        bp.sexRBGAddBene.add(bp.sexFemaleRB);
        bp.sexMaleRB.setSelected(true);
        
        bp.fourpsRBG.add(bp.fourpsYesRB);
        bp.fourpsRBG.add(bp.fourpsNoRB);
        bp.fourpsYesRB.setSelected(true);
        
        bp.indigentRBG.add(bp.indigentYesRB);
        bp.indigentRBG.add(bp.indigentNoRB);
        bp.indigentYesRB.setSelected(true);
        
        bp.sexRBGAddMember.add(bp.maleAddMemberRB);
        bp.sexRBGAddMember.add(bp.femaleAddMemberRB);
        bp.maleAddMemberRB.setSelected(true);
        
        bp.sexRBGEditMember.add(bp.maleEditMemberRB);
        bp.sexRBGEditMember.add(bp.femaleEditMemberRB);
        bp.maleEditMemberRB.setSelected(true);
    }
    
    void JDateChooserInit()
    {
        bp.dobDC.getDateEditor().addPropertyChangeListener("date", (PropertyChangeEvent e) -> {
            DateTime birthDate = new DateTime(((JTextField)bp.dobDC.getDateEditor().getUiComponent()).getText());
            DateTime now = new DateTime();
            Period period = new Period(birthDate, now);
            int years = period.getYears();
            bp.ageLbl.setText("" + years);
        });
    }
    
    void clearAddMemberFields()
    {
        bp.fnameAddMemberTF.setText("");
        bp.mnameAddMemberTF.setText("");
        bp.lnameAddMemberTF.setText("");
        bp.relAddMemberCB.setSelectedIndex(0);
        bp.ageAddMemberTF.setText("");
        bp.maleAddMemberRB.setSelected(true);
        bp.heaAddMemberCB.setSelectedIndex(0);
        bp.occAddMemberTF.setText("");
        bp.remarksAddMemberTA.setText("");
    }
    
    void clearEditMemberFields()
    {
        bp.fnameEditMemberTF.setText("");
        bp.mnameEditMemberTF.setText("");
        bp.lnameEditMemberTF.setText("");
        bp.relEditMemberCB.setSelectedIndex(0);
        bp.ageEditMemberTF.setText("");
        bp.maleEditMemberRB.setSelected(true);
        bp.heaEditMemberCB.setSelectedIndex(0);
        bp.occEditMemberTF.setText("");
        bp.remarksEditMemberTA.setText("");
    }
    
    void clearAddCropFields()
    {
        bp.cropAddCropTF.setText("");
        bp.areaAddCropTF.setText("");
        bp.varietyAddCropTF.setText("");
        bp.classificationAddCropTF.setText(""); 
        bp.expHarvestAddCropDC.setDate(new Date());
        bp.remarksAddCropTA.setText("");
    }
    
    void clearEditCropFields()
    {
        bp.cropEditCropTF.setText("");
        bp.areaEditCropTF.setText("");
        bp.varietyEditCropTF.setText("");
        bp.classificationEditCropTF.setText(""); 
        bp.expHarvestEditCropDC.setDate(new Date());
        bp.remarksEditCropTA.setText("");
    }
}
