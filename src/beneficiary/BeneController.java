package beneficiary;
import beneficiary.map.MapController;
import beneficiary.map.MapPanel;
import crop.CropModel;
import fmember.FMemberModel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import livestock.LSModel;
import main.MainFrame;
import net.proteanit.sql.DbUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import registration.RegModel;
import util.Alter;
import util.BrgyModel;
import util.SearchModel;
public class BeneController 
{
    BenePanel bp;
    MainFrame mf;
    public BeneController(BenePanel bp, MainFrame mf)
    {
        this.bp = bp;
        this.mf = mf;
        
        this.bp.allListener(
                new Action(),
                //Bene Classes
                new BenePopUpMenu(), 
                new BenePopUpMenu(),
                
                //Members Classes
                new MemberPopUpMenu(), 
                new MemberPopUpMenu(), 
                
                //Crop Classes
                new CropPopUpMenu(), 
                new CropPopUpMenu(), 
                
                //Livestock Classes
                new LivestockPopUpMenu(), 
                new LivestockPopUpMenu(),
                
                //farmer Class
                new FarmerCHBClass()
        );

        initRBG();
        initJDateChooser();
        displayAllBene();
    }
    void clearAddBeneFields()
    {
        //bp.beneIdLbl.setText("0"); //beneID
        bp.fNameTF.setText(""); //fname
        bp.mNameTF.setText("");//mname
        bp.lNameTF.setText(""); //lname
        bp.sexMaleRB.setSelected(true); //sex
        bp.codeCB.setSelectedIndex(0); //code
        bp.fourpsYesRB.setSelected(true);//fourps
        bp.indigentYesRB.setSelected(true); //indigent
        bp.heaCB.setSelectedIndex(0); //hea
        bp.ethnicityTF.setText(""); //ehtnicity
        bp.netIncomeSpin.setValue(0); //income
        bp.occTF.setText(""); //occ
        bp.farmerCB.setSelected(false);
        bp.healthCondCB.setSelectedIndex(0); //healthCond
        bp.houseStatCB.setSelectedIndex(0); //houseStat
        bp.houseCondCB.setSelectedIndex(0); //houseCond
        bp.contactNumTF.setText("");
        DefaultTableModel model1 = (DefaultTableModel) bp.membersTable.getModel();
        model1.setRowCount(0);
        DefaultTableModel model2 = (DefaultTableModel) bp.cropTable.getModel();
        model2.setRowCount(0);
        DefaultTableModel model3 = (DefaultTableModel) bp.livestockTable.getModel();
        model3.setRowCount(0);
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
    void clearAddLSFields()
    {
        bp.livestockAddLSTF.setText("");
        bp.classificationAddLSTF.setText("");
        bp.headsAddLSSpin.setValue(0);
        bp.ageAddLSSpin.setValue(0);
        bp.expDisposalAddLSDC.setDate(new Date());
        bp.remarksAddLSTA.setText("");
    }
    void clearAddMemberFields()
    {
        bp.fnameAddMemberTF.setText("");
        bp.mnameAddMemberTF.setText("");
        bp.lnameAddMemberTF.setText("");
        bp.relAddMemberCB.setSelectedIndex(0);
        bp.ageAddMemberSpin.setValue(0);
        bp.maleAddMemberRB.setSelected(true);
        bp.heaAddMemberCB.setSelectedIndex(0);
        bp.occAddMemberTF.setText("");
        bp.remarksAddMemberTA.setText("");
    }
    void clearEditBeneFields()
    {
        bp.beneIdLbl1.setText("0"); //beneID
        bp.fNameTF1.setText(""); //fname
        bp.mNameTF1.setText("");//mname
        bp.lNameTF1.setText(""); //lname
        bp.sexMaleRB1.setSelected(true); //sex
        bp.codeCB1.setSelectedIndex(0); //code
        bp.fourpsYesRB1.setSelected(true);//fourps
        bp.indigentYesRB1.setSelected(true); //indigent
        bp.heaCB1.setSelectedIndex(0); //hea
        bp.ethnicityTF1.setText(""); //ehtnicity
        bp.netIncomeSpin1.setValue(0); //income
        bp.occTF1.setText(""); //occ
        bp.healthCondCB1.setSelectedIndex(0); //healthCond
        bp.houseStatCB1.setSelectedIndex(0); //houseStat
        bp.houseCondCB1.setSelectedIndex(0); //houseCond
        bp.contactNumTF1.setText("");
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
    void clearEditLSFields()
    {
        bp.livestockEditLSTF.setText("");
        bp.classificationEditLSTF.setText("");
        bp.headsEditLSSpin.setValue(0);
        bp.ageEditLSSpin.setValue(0);
        bp.expDisposalEditLSDC.setDate(new Date());
        bp.remarksEditLSTA.setText("");
    }
    void clearEditMemberFields()
    {
        bp.fnameEditMemberTF.setText("");
        bp.mnameEditMemberTF.setText("");
        bp.lnameEditMemberTF.setText("");
        bp.relEditMemberCB.setSelectedIndex(0);
        bp.ageEditMemberSpin.setValue(0);
        bp.maleEditMemberRB.setSelected(true);
        bp.heaEditMemberCB.setSelectedIndex(0);
        bp.occEditMemberTF.setText("");
        bp.remarksEditMemberTA.setText("");
    }
    void deleteBene()
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
    void deleteCrop()
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
    void deleteLS()
    {
        int dataRow = bp.livestockTable.getSelectedRow();
        if(dataRow >= 0)
        {
            String livestockID = bp.livestockTable.getValueAt(dataRow,0).toString();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to "
                    + "Delete livestock: " + livestockID + "?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                int row = bp.livestockTable.getSelectedRows()[0];
                DefaultTableModel model = (DefaultTableModel) bp.livestockTable.getModel();
                model.removeRow(row);
                for(int index = row ;index<model.getRowCount();index++){
                    model.setValueAt(index+1, index, 0); //setValueAt(data,row,column)
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(bp.addBeneDialog,
                    "Please select a livestock to delete.", "No Item Selected",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    void deleteMember()
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
    void displayAllBene()
    {
        ResultSet rs = BeneModel.getAllBene();
        bp.beneTable.setModel(DbUtils.resultSetToTableModel(rs));
        new SearchModel(bp, bp.beneTable, bp.searchTF, rs);
    }
    void editBene()
    {
        int dataRow = bp.beneTable.getSelectedRow();
        if(dataRow >= 0)
        {
            bp.beneIdLbl1.setText(bp.beneTable.getValueAt(dataRow,0).toString());
            bp.fNameTF1.setText(bp.beneTable.getValueAt(dataRow,1).toString()); //fname
            bp.mNameTF1.setText(bp.beneTable.getValueAt(dataRow,2).toString()); //mname
            bp.lNameTF1.setText(bp.beneTable.getValueAt(dataRow,3).toString()); //lname
            bp.sexMaleRB1.setSelected(true);
            
            try {
                Date date;
                date = new SimpleDateFormat("yyyy-MM-dd").parse(bp.beneTable.getValueAt(dataRow,5).toString());
                bp.dobDC1.setDate(date); //dob
            } catch (ParseException ex) {
                Logger.getLogger(BeneController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            bp.brgyCB1.setModel(new DefaultComboBoxModel(BrgyModel.getBrgy().toArray()));
            bp.brgyCB1.setSelectedItem(bp.beneTable.getValueAt(dataRow,6).toString());
            bp.codeCB1.setSelectedItem(bp.beneTable.getValueAt(dataRow,7).toString());//code
            bp.fourpsYesRB1.setSelected(true);
            bp.indigentYesRB1.setSelected(true);
            bp.heaCB1.setSelectedItem(bp.beneTable.getValueAt(dataRow,10).toString()); //hea
            bp.ethnicityTF1.setText(bp.beneTable.getValueAt(dataRow,11).toString());//ehtnicity
            bp.netIncomeSpin1.setValue(Double.parseDouble(bp.beneTable.getValueAt(dataRow,12).toString())); //income
            bp.occTF1.setText(bp.beneTable.getValueAt(dataRow,13).toString()); //occ
            bp.healthCondCB1.setSelectedItem(bp.beneTable.getValueAt(dataRow,14).toString()); //healthCond
            bp.houseStatCB1.setSelectedItem(bp.beneTable.getValueAt(dataRow,15).toString());//houseStat
            bp.houseCondCB1.setSelectedItem(bp.beneTable.getValueAt(dataRow,16).toString());//houseCond
            bp.contactNumTF1.setText(bp.beneTable.getValueAt(dataRow,17).toString());
            bp.longLatLbl1.setText(
                    bp.beneTable.getValueAt(dataRow,18).toString() + "," +
                            bp.beneTable.getValueAt(dataRow,19).toString()
            );
            
            bp.editBeneDialog.setTitle("Edit Beneficiary");
            bp.editBeneDialog.setModal(true);
            bp.editBeneDialog.pack();
            bp.editBeneDialog.setLocationRelativeTo(null);
            bp.editBeneDialog.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(bp,
                    "Please select a beneficiary to edit.", "No Item Selected",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    void editCrop()
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
            bp.editCropDialog.setLocationRelativeTo(null);
            bp.editCropDialog.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(bp.addBeneDialog,
                    "Please select a crop to edit.", "No Item Selected",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    void editLS()
    {
        int dataRow = bp.livestockTable.getSelectedRow();
        if(dataRow >= 0)
        {
            bp.numberEditLSLbl.setText(bp.livestockTable.getValueAt(dataRow,0).toString());
            bp.livestockEditLSTF.setText(bp.livestockTable.getValueAt(dataRow,1).toString());
            bp.classificationEditLSTF.setText(bp.livestockTable.getValueAt(dataRow,2).toString());
            bp.headsEditLSSpin.setValue(Integer.parseInt(bp.livestockTable.getValueAt(dataRow,3).toString()));
            bp.ageEditLSSpin.setValue(Integer.parseInt(bp.livestockTable.getValueAt(dataRow,4).toString()));
            bp.remarksEditLSTA.setText(bp.livestockTable.getValueAt(dataRow,6).toString());
            bp.editLSDialog.setTitle("Edit Livestock");
            bp.editLSDialog.setModal(true);
            bp.editLSDialog.pack();
            bp.editLSDialog.setLocationRelativeTo(null);
            bp.editLSDialog.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(bp.addBeneDialog,
                    "Please select a livestock to edit.", "No Item Selected",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    void editMember()
    {
        int dataRow = bp.membersTable.getSelectedRow();
        if(dataRow >= 0)
        {
            bp.numEditMemberTF.setText(bp.membersTable.getValueAt(dataRow,0).toString());
            bp.fnameEditMemberTF.setText(bp.membersTable.getValueAt(dataRow,1).toString());
            bp.mnameEditMemberTF.setText(bp.membersTable.getValueAt(dataRow,2).toString());
            bp.lnameEditMemberTF.setText(bp.membersTable.getValueAt(dataRow,3).toString());
            bp.relEditMemberCB.setSelectedItem(bp.membersTable.getValueAt(dataRow,4).toString());
            bp.ageEditMemberSpin.setValue(Integer.valueOf(bp.membersTable.getValueAt(dataRow,5).toString()));
            bp.maleEditMemberRB.setSelected(true);
            bp.heaEditMemberCB.setSelectedItem(bp.membersTable.getValueAt(dataRow,7).toString());
            bp.occEditMemberTF.setText(bp.membersTable.getValueAt(dataRow,8).toString());
            bp.remarksEditMemberTA.setText(bp.membersTable.getValueAt(dataRow,9).toString());
            bp.editMemberDialog.setTitle("Edit Family Member");
            bp.editMemberDialog.setModal(true);
            bp.editMemberDialog.pack();
            bp.editMemberDialog.setLocationRelativeTo(null);
            bp.editMemberDialog.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(bp.addBeneDialog,
                    "Please select a family member to edit.", "No Item Selected",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    void initJDateChooser()
    {
        bp.dobDC.getDateEditor().addPropertyChangeListener("date", (PropertyChangeEvent e) -> {
            DateTime birthDate = new DateTime(((JTextField)bp.dobDC.getDateEditor().getUiComponent()).getText());
            DateTime now = new DateTime();
            Period period = new Period(birthDate, now);
            int years = period.getYears();
            bp.ageLbl.setText("" + years);
        });
        
        bp.dobDC1.getDateEditor().addPropertyChangeListener("date", (PropertyChangeEvent e) -> {
            DateTime birthDate = new DateTime(((JTextField)bp.dobDC1.getDateEditor().getUiComponent()).getText());
            DateTime now = new DateTime();
            Period period = new Period(birthDate, now);
            int years = period.getYears();
            bp.ageLbl1.setText("" + years);
        });
    }
    void initRBG()
    {
        //for addBeneDialog
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
        
        //for editBeneDialog
        bp.sexRBGAddBene1.add(bp.sexMaleRB1);
        bp.sexRBGAddBene1.add(bp.sexFemaleRB1);
        bp.sexMaleRB1.setSelected(true);
        
        bp.fourpsRBG1.add(bp.fourpsYesRB1);
        bp.fourpsRBG1.add(bp.fourpsNoRB1);
        bp.fourpsYesRB1.setSelected(true);
        
        bp.indigentRBG1.add(bp.indigentYesRB1);
        bp.indigentRBG1.add(bp.indigentNoRB1);
        bp.indigentYesRB1.setSelected(true);
        
        //for fmemberdialogs
        bp.sexRBGAddMember.add(bp.maleAddMemberRB);
        bp.sexRBGAddMember.add(bp.femaleAddMemberRB);
        bp.maleAddMemberRB.setSelected(true);
        
        bp.sexRBGEditMember.add(bp.maleEditMemberRB);
        bp.sexRBGEditMember.add(bp.femaleEditMemberRB);
        bp.maleEditMemberRB.setSelected(true);
    }
    void okAddCrop()
    {
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
    void okAddLS()
    {
        DefaultTableModel model = (DefaultTableModel) bp.livestockTable.getModel();
        model.addRow(new Object[]{
            bp.livestockTable.getRowCount() + 1, bp.livestockAddLSTF.getText(),
            bp.classificationAddLSTF.getText(), bp.headsAddLSSpin.getValue().toString(),
            bp.ageAddLSSpin.getValue().toString(),
            ((JTextField)bp.expDisposalAddLSDC.getDateEditor().getUiComponent()).getText(),
            bp.remarksAddLSTA.getText()});
        clearAddLSFields();
        bp.addLSDialog.dispose();
    }
    void okAddMember()
    {
        DefaultTableModel model = (DefaultTableModel) bp.membersTable.getModel();
        model.addRow(new Object[]{
            bp.membersTable.getRowCount() + 1, bp.fnameAddMemberTF.getText(),
            bp.mnameAddMemberTF.getText(), bp.lnameAddMemberTF.getText(),
            bp.relAddMemberCB.getSelectedItem().toString(), bp.ageAddMemberSpin.getValue().toString(),
            bp.maleAddMemberRB.isSelected() ? "Male" : "Female",
            bp.heaAddMemberCB.getSelectedItem().toString(), bp.occAddMemberTF.getText(),
            bp.remarksAddMemberTA.getText()});
        clearAddMemberFields();
        bp.addMemberDialog.dispose();
    }
    void okEditCrop()
    {
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
    void okEditLS()
    {
        DefaultTableModel model = (DefaultTableModel) bp.livestockTable.getModel();
        model.removeRow(Integer.parseInt(bp.numberEditLSLbl.getText()) - 1);
        model.insertRow(Integer.parseInt(bp.numberEditLSLbl.getText()) - 1, new Object[]{
            bp.numberEditLSLbl.getText(), bp.livestockEditLSTF.getText(),
            bp.classificationEditLSTF.getText(), Alter.getInt(bp.headsEditLSSpin),
            bp.ageEditLSSpin.getValue().toString(),
            ((JTextField)bp.expDisposalAddLSDC.getDateEditor().getUiComponent()).getText(),
            bp.remarksEditLSTA.getText()});
        clearEditLSFields();
        bp.editLSDialog.dispose();
    }
    void okEditMember()
    {
        DefaultTableModel model = (DefaultTableModel) bp.membersTable.getModel();
        model.removeRow(Integer.parseInt(bp.numEditMemberTF.getText()) - 1);
        model.insertRow(Integer.parseInt(bp.numEditMemberTF.getText()) - 1, new Object[]{
            bp.numEditMemberTF.getText(), bp.fnameEditMemberTF.getText(),
            bp.mnameEditMemberTF.getText(), bp.lnameEditMemberTF.getText(),
            bp.relEditMemberCB.getSelectedItem().toString(),
            bp.ageEditMemberSpin.getValue().toString(),
            bp.maleEditMemberRB.isSelected() ? "Male" : "Female",
            bp.heaEditMemberCB.getSelectedItem().toString(), bp.occEditMemberTF.getText(),
            bp.remarksEditMemberTA.getText()});
        clearEditMemberFields();
        bp.editMemberDialog.dispose();
    }
    void openAddBeneDialog()
    {
        Date date = new Date();
        bp.dobDC.setDate(date);
        bp.brgyCB.setModel(new DefaultComboBoxModel(BrgyModel.getBrgy().toArray()));
        //bp.beneIdLbl.setText("" + (BeneModel.getIdOfLatestBene() + 1));
        bp.addBeneDialog.setTitle("Add Beneficiary:" + (BeneModel.getIdOfLatestBene() + 1));
        bp.addBeneDialog.setModal(true);
        bp.addBeneDialog.pack();
        bp.addBeneDialog.setLocationRelativeTo(null);
        bp.addBeneDialog.setVisible(true);
        clearAddBeneFields();
        clearAddMemberFields();
        clearAddCropFields();
        clearAddLSFields();
    }
    void openAddCropDialog()
    {
        bp.addCropDialog.setTitle("Add Crop/Tree");
        bp.addCropDialog.setModal(true);
        bp.addCropDialog.pack();
        bp.addCropDialog.setLocationRelativeTo(null);
        bp.addCropDialog.setVisible(true);
    }
    void openAddLSDialog()
    {
        bp.addLSDialog.setTitle("Add Livestock");
        bp.addLSDialog.setModal(true);
        bp.addLSDialog.pack();
        bp.addLSDialog.setLocationRelativeTo(null);
        bp.addLSDialog.setVisible(true);
    }
    void openAddMemberDialog()
    {
        bp.addMemberDialog.setTitle("Add Family Member");
        bp.addMemberDialog.setModal(true);
        bp.addMemberDialog.pack();
        bp.addMemberDialog.setLocationRelativeTo(null);
        bp.addMemberDialog.setVisible(true);
    }
    void openMapToGetLoc()
    {
        MapPanel mpp = new MapPanel();
        new MapController(mpp,bp);
    }
    void saveBeneToDB()
    {
        try
        {
            if(bp.fNameTF.getText().trim().isEmpty() || bp.lNameTF.getText().trim().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "First Name and Last Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String brgyStr = bp.brgyCB.getSelectedItem().toString();
                String locStr = bp.longLatLbl.getText();
                String title = bp.addBeneDialog.getTitle();
                
                BeneModel.saveBene(
                        Integer.parseInt(title.substring(title.indexOf(":") + 1)), //beneID
                        bp.fNameTF.getText(), //fname
                        bp.mNameTF.getText(), //mname
                        bp.lNameTF.getText(), //lname
                        bp.sexMaleRB.isSelected() ? "Male" : "Female", //sex
                        ((JTextField)bp.dobDC.getDateEditor().getUiComponent()).getText(), //dob
                        brgyStr,
                        //Integer.parseInt(brgyStr.substring(0,brgyStr.indexOf(" "))), //brgy
                        bp.codeCB.getSelectedItem().toString(), //code
                        bp.fourpsYesRB.isSelected() ? "Yes" : "No", //fourps
                        bp.indigentYesRB.isSelected() ? "Yes" : "No", //indigent
                        bp.heaCB.getSelectedItem().toString(), //hea
                        bp.ethnicityTF.getText(), //ehtnicity
                        Double.parseDouble(bp.netIncomeSpin.getValue().toString()), //income
                        bp.occTF.getText(), //occ
                        bp.healthCondCB.getSelectedItem().toString(), //healthCond
                        bp.houseStatCB.getSelectedItem().toString(), //houseStat
                        bp.houseCondCB.getSelectedItem().toString(), //houseCond
                        bp.contactNumTF.getText(),
                        Double.parseDouble(locStr.substring(0,locStr.indexOf(","))), //loc_long
                        Double.parseDouble(locStr.substring(locStr.indexOf(",") + 1, locStr.length()))); //loc_lat
                
                saveFMemberToDB();
                saveCropsToDB();
                saveLSToDB();
                
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date dateobj = new Date();
                
                RegModel.saveReg(
                        Integer.parseInt(mf.adminIDTF.getText()),
                        Integer.parseInt(title.substring(title.indexOf(":") + 1)),
                        bp.walkinYesRB.isSelected() ? "Yes" : "No",
                        Alter.getString(bp.caseCBB),
                        df.format(dateobj));
                JOptionPane.showMessageDialog(null, "Beneficiary Added" , "Success", JOptionPane.INFORMATION_MESSAGE);
                bp.addBeneDialog.dispose();
                displayAllBene();
            }
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    void saveCropsToDB()
    {
        int dataRow = bp.cropTable.getRowCount();
        if(dataRow > 0)
        {
            for(int x = 0 ; x < bp.cropTable.getRowCount() ; x++)
            {
                String title = bp.addBeneDialog.getTitle();
                CropModel.saveCrop(
                        Integer.parseInt(title.substring(title.indexOf(":") + 1)),
                        bp.cropTable.getValueAt(x,1).toString(),
                        bp.cropTable.getValueAt(x,2).toString(),
                        bp.cropTable.getValueAt(x,3).toString(),
                        bp.cropTable.getValueAt(x,4).toString(),
                        bp.cropTable.getValueAt(x,5).toString(),
                        bp.cropTable.getValueAt(x,6).toString()
                );
            }
        }
    }
    void saveFMemberToDB()
    {
        int dataRow = bp.membersTable.getRowCount();
        if(dataRow > 0)
        {
            for(int x = 0 ; x < bp.membersTable.getRowCount() ; x++)
            {
                String title = bp.addBeneDialog.getTitle();
                FMemberModel.saveFM(
                        Integer.parseInt(title.substring(title.indexOf(":") + 1)), //beneID
                        bp.membersTable.getValueAt(x,1).toString(),
                        bp.membersTable.getValueAt(x,2).toString(),
                        bp.membersTable.getValueAt(x,3).toString(),
                        bp.membersTable.getValueAt(x,4).toString(),
                        Integer.parseInt(bp.membersTable.getValueAt(x,5).toString()),
                        bp.membersTable.getValueAt(x,6).toString(),
                        bp.membersTable.getValueAt(x,7).toString(),
                        bp.membersTable.getValueAt(x,8).toString(),
                        bp.membersTable.getValueAt(x,9).toString()
                );
            }
        }
    }
    void saveLSToDB()
    {
        int dataRow = bp.livestockTable.getRowCount();
        if(dataRow > 0)
        {
            for(int x = 0 ; x < bp.livestockTable.getRowCount() ; x++)
            {
                String title = bp.addBeneDialog.getTitle();
                LSModel.saveLS(
                        Integer.parseInt(title.substring(title.indexOf(":") + 1)), //beneID
                        bp.livestockTable.getValueAt(x,1).toString(), //ls
                        bp.livestockTable.getValueAt(x,2).toString(), //cl
                        Integer.parseInt(bp.livestockTable.getValueAt(x,3).toString()), //heads
                        Integer.parseInt(bp.livestockTable.getValueAt(x,4).toString()), //age
                        bp.livestockTable.getValueAt(x,5).toString(), //exp
                        bp.livestockTable.getValueAt(x,6).toString() //rem
                );
            }
        }
    }
    void viewBeneInMap()
    {
        int dataRow = bp.beneTable.getSelectedRow();
        if(dataRow >= 0)
        {
            MapPanel mpp = new MapPanel();
            new MapController(mpp, bp,
                    Double.parseDouble(bp.beneTable.getValueAt(dataRow,18).toString()),
                    Double.parseDouble(bp.beneTable.getValueAt(dataRow,19).toString())
            );
        }
        else
        {
            JOptionPane.showMessageDialog(bp.addBeneDialog,
                    "Please select a beneficiary to view.", "No Item Selected",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    void editBeneLoc()
    {
        MapPanel mpp = new MapPanel();
        new MapController(mpp, bp 
        );
    }
    void updateBene()
    {
        String brgyStr = bp.brgyCB1.getSelectedItem().toString();
        String locStr = bp.longLatLbl1.getText();
        
        BeneModel.updateBene(
                Integer.parseInt(bp.beneIdLbl1.getText()), //beneID
                bp.fNameTF1.getText(), //fname
                bp.mNameTF1.getText(), //mname
                bp.lNameTF1.getText(), //lname
                bp.sexMaleRB1.isSelected() ? "Male" : "Female", //sex
                ((JTextField)bp.dobDC1.getDateEditor().getUiComponent()).getText(), //dob
                brgyStr,
                //Integer.parseInt(brgyStr.substring(0,brgyStr.indexOf(" "))), //brgy
                bp.codeCB1.getSelectedItem().toString(), //code
                bp.fourpsYesRB1.isSelected() ? "Yes" : "No", //fourps
                bp.indigentYesRB1.isSelected() ? "Yes" : "No", //indigent
                bp.heaCB1.getSelectedItem().toString(), //hea
                bp.ethnicityTF1.getText(), //ehtnicity
                Double.parseDouble(bp.netIncomeSpin1.getValue().toString()), //income
                bp.occTF1.getText(), //occ
                bp.healthCondCB1.getSelectedItem().toString(), //healthCond
                bp.houseStatCB1.getSelectedItem().toString(), //houseStat
                bp.houseCondCB1.getSelectedItem().toString(), //houseCond
                bp.contactNumTF1.getText(),
                Double.parseDouble(locStr.substring(0,locStr.indexOf(","))), //loc_long
                Double.parseDouble(locStr.substring(locStr.indexOf(",") + 1, locStr.length()))); //loc_lat
        
        bp.editBeneDialog.dispose();
        displayAllBene();
    }
    void viewBene()
    {
        int dataRow = bp.beneTable.getSelectedRow();
        if(dataRow >= 0)
        {
            JOptionPane.showMessageDialog(bp,
                    "#: " + (bp.beneTable.getValueAt(dataRow,0).toString()) + "\n" +
                            "First Name: " + (bp.beneTable.getValueAt(dataRow,1).toString()) + "\n" +
                                    "Middle Name: " + (bp.beneTable.getValueAt(dataRow,2).toString()) + "\n" +
                                            "Last Name: " + (bp.beneTable.getValueAt(dataRow,3).toString()) + "\n" +
                                                    "Sex: " + (bp.beneTable.getValueAt(dataRow,4).toString()) + "\n" +
                                                            "Birth Date: " + (bp.beneTable.getValueAt(dataRow,5).toString()) + "\n" +
                                                                    "Brgy: " + (bp.beneTable.getValueAt(dataRow,6).toString()) + "\n" +
                                                                            "Code: " + (bp.beneTable.getValueAt(dataRow,7).toString()) + "\n" +
                                                                                    "4Ps: " + (bp.beneTable.getValueAt(dataRow,8).toString()) + "\n" +
                                                                                            "Indigent: " + (bp.beneTable.getValueAt(dataRow,9).toString()) + "\n" +
                                                                                                    "Highest Educ Att: " + (bp.beneTable.getValueAt(dataRow,10).toString()) + "\n" +
                                                                                                            "Ethnicity: " + (bp.beneTable.getValueAt(dataRow,11).toString()) + "\n" +
                                                                                                                    "Net Income: " + (bp.beneTable.getValueAt(dataRow,12).toString()) + "\n" +
                                                                                                                            "Occupation: " + (bp.beneTable.getValueAt(dataRow,13).toString()) + "\n" +
                                                                                                                                    "Health Condition: " + (bp.beneTable.getValueAt(dataRow,14).toString()) + "\n" +
                                                                                                                                            "House Status: " + (bp.beneTable.getValueAt(dataRow,15).toString()) + "\n" +
                                                                                                                                                    "House Condition: " + (bp.beneTable.getValueAt(dataRow,16).toString()) + "\n" +
                                                                                                                                                            "Contact Number: " + (bp.beneTable.getValueAt(dataRow,17).toString()) + "\n" +
                                                                                                                                                                    "Longitude: " + (bp.beneTable.getValueAt(dataRow,18).toString()) + "\n" +
                                                                                                                                                                            "Latitude: " + (bp.beneTable.getValueAt(dataRow,19).toString()),
                    "Beneficiary Info", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(bp,
                    "Please select a beneficiary to view.", "No Item Selected",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    void viewCrop()
    {
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
    void viewLS()
    {
        int dataRow = bp.livestockTable.getSelectedRow();
        if(dataRow >= 0)
        {
            JOptionPane.showMessageDialog(bp.addBeneDialog,
                    "#: " + (bp.livestockTable.getValueAt(dataRow,0).toString()) + "\n" +
                            "Livestock Raised: " + (bp.livestockTable.getValueAt(dataRow,1).toString()) + "\n" +
                                    "Classification: " + (bp.livestockTable.getValueAt(dataRow,2).toString()) + "\n" +
                                            "Number of Heads: " + (bp.livestockTable.getValueAt(dataRow,3).toString()) + "\n" +
                                                    "Age in Months: " + (bp.livestockTable.getValueAt(dataRow,4).toString()) + "\n" +
                                                            "Exp Date of Disposal: " + (bp.livestockTable.getValueAt(dataRow,5).toString()) + "\n" +
                                                                    "Remarks: " + (bp.livestockTable.getValueAt(dataRow,6).toString()),
                    "Livestock Info", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(bp.addBeneDialog,
                    "Please select a livestock to view.", "No Item Selected",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    void viewMember()
    {
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
    class Action implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //Bene
            if(e.getSource() == bp.viewBeneMenuItem)
            {
                viewBene();
            }
            if(e.getSource() == bp.editBeneMenuItem)
            {
                clearEditBeneFields();
                editBene();
            }
            if(e.getSource() == bp.addBeneMenuItem)
            {
                clearAddBeneFields();
                openAddBeneDialog();
            }
            if(e.getSource() == bp.deleteBeneMenuItem)
            {
                deleteBene();
            }
            if(e.getSource() == bp.viewMapMenuItem)
            {
                viewBeneInMap();
            }
            if(e.getSource() == bp.addBeneBtn)
            {
                openAddBeneDialog();
            }
            if(e.getSource() == bp.saveBeneBtn)
            {
                saveBeneToDB();
            }
            if(e.getSource() == bp.cancelBeneBtn)
            {
                bp.addBeneDialog.dispose();
            }
            if(e.getSource() == bp.editBeneBtn)
            {
                editBene();
            }
            if(e.getSource() == bp.updateBeneBtn)
            {
                updateBene();
            }
            if(e.getSource() == bp.cancelBeneBtn1)
            {
                bp.editBeneDialog.dispose();
            }
            if(e.getSource() == bp.deleteBeneBtn)
            {
                deleteBene();
            }
            if(e.getSource() == bp.locBtn)
            {
                openMapToGetLoc();
            }
            if(e.getSource() == bp.locBtn1)
            {
                openMapToGetLoc();
            }

            
            //Members
            if(e.getSource() == bp.viewMemberMenuItem)
            {
                viewMember();
            }
            if(e.getSource() == bp.editMemberMenuItem)
            {
                clearEditMemberFields();
                editMember();
            }
            if(e.getSource() == bp.addMemberMenuItem)
            {
                clearAddMemberFields();
                openAddMemberDialog();
            }
            if(e.getSource() == bp.deleteMemberMenuItem)
            {
                deleteMember();
            }
            if(e.getSource() == bp.okAddMemberBtn)
            {
                okAddMember();
            }
            if(e.getSource() == bp.cancelAddMemberBtn)
            {
                bp.addMemberDialog.dispose();
            }
            if(e.getSource() == bp.okEditMemberBtn)
            {
                okEditMember();
            }
            if(e.getSource() == bp.cancelEditMemberBtn)
            {
                bp.editMemberDialog.dispose();
            }
            
            //Crops
            if(e.getSource() == bp.viewCropMenuItem)
            {
                viewCrop();
            }
            if(e.getSource() == bp.editCropMenuItem)
            {
                clearEditCropFields();
                editCrop();
            }
            if(e.getSource() == bp.addCropMenuItem)
            {
                clearAddCropFields();
                openAddCropDialog();
            }
            if(e.getSource() == bp.deleteCropMenuItem)
            {
                deleteCrop();
            }
            if(e.getSource() == bp.okAddCropBtn)
            {
                okAddCrop();
            }
            if(e.getSource() == bp.cancelAddCropBtn)
            {
                bp.addCropDialog.dispose();
            }
            if(e.getSource() == bp.okEditCropBtn)
            {
                okEditCrop();
            }
            if(e.getSource() == bp.cancelEditCropBtn)
            {
                bp.editCropDialog.dispose();
            }
            
            //Livestock
            if(e.getSource() == bp.viewLSMenuItem)
            {
                viewLS();
            }
            if(e.getSource() == bp.editLSMenuItem)
            {
                clearEditLSFields();
                editLS();
            }
            if(e.getSource() == bp.addLSMenuItem)
            {
                clearAddLSFields();
                openAddLSDialog();
            }
            if(e.getSource() == bp.deleteLSMenuItem)
            {
                deleteLS();
            }
            if(e.getSource() == bp.okAddLSBtn)
            {
                okAddLS();
            }
            if(e.getSource() == bp.cancelAddLSBtn)
            {
                bp.addLSDialog.dispose();
            }
            if(e.getSource() == bp.okEditLSBtn)
            {
                okEditLS();
            }
            if(e.getSource() == bp.cancelEditLSBtn)
            {
                bp.editLSDialog.dispose();
            }
        }
    }
    class BenePopUpMenu extends MouseAdapter implements  PopupMenuListener
    {
        
        @Override
        public void mouseReleased(MouseEvent e)
        {
            int r = bp.beneTable.rowAtPoint(e.getPoint());
            if (r >= 0 && r < bp.beneTable.getRowCount()) {
                bp.beneTable.setRowSelectionInterval(r, r);
            } else {
                bp.beneTable.clearSelection();
            }
            
            int rowindex = bp.beneTable.getSelectedRow();
            if (rowindex < 0){
                JPopupMenu popup = new JPopupMenu();
                popup.show(bp, e.getX(), e.getY());
                bp.beneTable.setComponentPopupMenu(bp.benePopMenu);
                //return;
            }
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(bp, e.getX(), e.getY());
                bp.beneTable.setComponentPopupMenu(bp.benePopMenu);
            }
        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = bp.beneTable.rowAtPoint(SwingUtilities.
                        convertPoint(bp.benePopMenu, new Point(0, 0), bp.beneTable));
                if (rowAtPoint > -1) {
                    bp.beneTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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
    class FarmerCHBClass implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(bp.farmerCB.isSelected())
            {
                bp.occTF.setEnabled(false);
                bp.occTF.setText("Farmer");
                
                bp.cropTable.setFillsViewportHeight(true);
                bp.livestockTable.setFillsViewportHeight(true);
            }
            else
            {
                bp.occTF.setEnabled(true);
                bp.occTF.setText("");
                
                bp.cropTable.setFillsViewportHeight(false);
                bp.livestockTable.setFillsViewportHeight(false);
                
                DefaultTableModel cropTable = (DefaultTableModel) bp.cropTable.getModel();
                cropTable.setRowCount(0);
                
                DefaultTableModel livestockTable = (DefaultTableModel) bp.livestockTable.getModel();
                livestockTable.setRowCount(0);
            }
        }
    }
    class LivestockPopUpMenu extends MouseAdapter implements  PopupMenuListener
    {
        
        @Override
        public void mouseReleased(MouseEvent e) 
        {
            int r = bp.livestockTable.rowAtPoint(e.getPoint());
            if (r >= 0 && r < bp.livestockTable.getRowCount()) {
                bp.livestockTable.setRowSelectionInterval(r, r);
            } else {
                bp.livestockTable.clearSelection();  
            }
            
            int rowindex = bp.livestockTable.getSelectedRow();
            if (rowindex < 0){
                JPopupMenu popup = new JPopupMenu();
                popup.show(bp.addBeneDialog, e.getX(), e.getY());
                bp.livestockTable.setComponentPopupMenu(bp.livestockPopMenu);
                //return;
            }
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(bp.addBeneDialog, e.getX(), e.getY());
                bp.livestockTable.setComponentPopupMenu(bp.livestockPopMenu);
            }
        }
        
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = bp.livestockTable.rowAtPoint(SwingUtilities.
                        convertPoint(bp.livestockPopMenu, new Point(0, 0), bp.livestockTable));
                if (rowAtPoint > -1) {
                    bp.livestockTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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
}
