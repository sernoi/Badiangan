package fmember;
import admin.*;
import java.awt.Dialog;
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
import java.sql.ResultSet;
import java.util.Arrays;
import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
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

public class FMemberController
{
    FMemberPanel fmp;
    public FMemberController(FMemberPanel fmp)
    {
        this.fmp = fmp;
  
        this.fmp.allListener(
                //Button Classes
                new OpenAddFMDialog(), new SaveFMClass(), new EditFMClass(),
                new UpdateFMClass(), new DeleteFMClass(), new CloseDialogClass(), 
                new CloseDialogClass(), new SearchFMClass(), new MyPopUpMenu(),
                
                //PopUpMenu Classes
                new MyPopUpMenu(), new ViewFMClass(), new EditFMClass(), 
                new OpenAddFMDialog(), new DeleteFMClass(), 
                
                //Auto-generate Beneficiary in ComboBox
                new ComboKeyHandler(fmp.addBeneFMCB)
        );
        
        displayAllFM();
        initRBG();
    }
    
    class DialogDisposal extends WindowAdapter
    {
        
    }
    class ViewFMClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            int dataRow = fmp.fmTable.getSelectedRow();
            if(dataRow >= 0)
            {
                JOptionPane.showMessageDialog(fmp,
                "ID: " + (fmp.fmTable.getValueAt(dataRow,0).toString()) + "\n" +
                "Username: " + (fmp.fmTable.getValueAt(dataRow,1).toString()) + "\n" +
                "First Name: " + (fmp.fmTable.getValueAt(dataRow,2).toString()) + "\n" +
                "Middle Name: " + (fmp.fmTable.getValueAt(dataRow,3).toString()) + "\n" +
                "Last Name: " + (fmp.fmTable.getValueAt(dataRow,4).toString()) + "\n" +
                "Department: " + (fmp.fmTable.getValueAt(dataRow,5).toString()) + "\n" +
                "Position: " + (fmp.fmTable.getValueAt(dataRow,6).toString())  
                , "Family Member Info", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(fmp, "Please select Family Member to edit.");
            }
        }
    }
    
    class MyPopUpMenu extends MouseAdapter implements  PopupMenuListener
    {
        
        @Override
        public void mouseReleased(MouseEvent e) 
        {      
            int r = fmp.fmTable.rowAtPoint(e.getPoint());
            if (r >= 0 && r < fmp.fmTable.getRowCount()) {
                fmp.fmTable.setRowSelectionInterval(r, r);
            } else {
                fmp.fmTable.clearSelection();
            }

            int rowindex = fmp.fmTable.getSelectedRow();
            if (rowindex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                JPopupMenu popup = new JPopupMenu();
                popup.show(fmp, e.getX(), e.getY());
                fmp.fmTable.setComponentPopupMenu(fmp.fmPopUpMenu);
            }
        }
        
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            SwingUtilities.invokeLater(() -> {
                int rowAtPoint = fmp.fmTable.rowAtPoint(SwingUtilities.
                        convertPoint(fmp.fmPopUpMenu, new Point(0, 0), fmp.fmTable));
                if (rowAtPoint > -1) {
                    fmp.fmTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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
    
    class SearchFMClass implements KeyListener
    {
        void searchNow()
        {
            ResultSet rs = FMemberModel.searchFM(fmp.searchTF.getText());
            fmp.fmTable.setModel(DbUtils.resultSetToTableModel(rs));
            // this is to disable editing in the jtable
            for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class)) 
            {
                TableCellEditor ce = fmp.fmTable.getDefaultEditor(c);
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
    
    class OpenAddFMDialog implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            fmp.addFMDialog.setTitle("Add Family Member");
            fmp.addFMDialog.setModal(true);
            fmp.addFMDialog.pack();
            fmp.addFMDialog.setLocationRelativeTo(null);
            fmp.addFMDialog.setVisible(true);
        }
    }
    
    class SaveFMClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            JTextField text = (JTextField) fmp.addBeneFMCB.getEditor().getEditorComponent();
            String str = text.getText();
            FMemberModel.saveFM(
                    Integer.parseInt(str.substring(str.indexOf(":") + 1,str.indexOf(")"))),
                    fmp.fnameAddMemberTF.getText(),
                    fmp.mnameAddMemberTF.getText(), 
                    fmp.lnameAddMemberTF.getText(), 
                    fmp.relAddMemberCB.getSelectedItem().toString(), 
                    Integer.parseInt(fmp.ageAddMemberSpin.getValue().toString()), 
                    fmp.maleAddMemberRB.isSelected() ? "Male" : "Female",
                    fmp.heaAddMemberCB.getSelectedItem().toString(),
                    fmp.occAddMemberTF.getText(), 
                    fmp.remarksAddMemberTA.getText());
            
            fmp.addFMDialog.dispose();
            displayAllFM();
        }
    }
    
    class EditFMClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int dataRow = fmp.fmTable.getSelectedRow();
            if(dataRow >= 0)
            {
//                fmp.oldIdTF.setText(fmp.fmTable.getValueAt(dataRow,0).toString());
//                fmp.unTF1.setText(fmp.fmTable.getValueAt(dataRow,1).toString());
//                fmp.fNameTF1.setText(fmp.fmTable.getValueAt(dataRow,2).toString());
//                fmp.mNameTF1.setText(fmp.fmTable.getValueAt(dataRow,3).toString());
//                fmp.lNameTF1.setText(fmp.fmTable.getValueAt(dataRow,4).toString());
//                fmp.departmentCB1.setSelectedItem(fmp.fmTable.getValueAt(dataRow,5).toString());
//                fmp.positionCB1.setSelectedItem(fmp.fmTable.getValueAt(dataRow,6).toString());
                fmp.editFMDialog.setTitle("Edit Family Member");
                fmp.editFMDialog.setModal(true);
                fmp.editFMDialog.pack();
                fmp.editFMDialog.setLocationRelativeTo(fmp);
                fmp.editFMDialog.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(fmp, "Please select Family Member to edit.");
            }
        }
    }
    
    class UpdateFMClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    
    class DeleteFMClass implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int dataRow = fmp.fmTable.getSelectedRow();
            if(dataRow >= 0)
            {
                String fmID = fmp.fmTable.getValueAt(dataRow,0).toString();
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (fmp, "Would You Like to "
                                   + "Delete Family Member: " + fmID + "?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION)
                {
                    FMemberModel.deleteFM(fmID);
                    displayAllFM();
                }  
            }
            else
            {
                JOptionPane.showMessageDialog(fmp, "Please select Family Member to delete.");
            }
        }
    }
    
    class CloseDialogClass extends WindowAdapter implements ActionListener
    {
        @Override 
            public void windowClosing(WindowEvent e){
            clearFields();
            fmp.addFMDialog.dispose();
            fmp.editFMDialog.dispose();
        }
            
        @Override
        public void actionPerformed(ActionEvent e) {
            clearFields();
            fmp.addFMDialog.dispose();
            fmp.editFMDialog.dispose();
        }
    }

    void displayAllFM()
    {
        //this is to load all the schedules in the database upon selecting the Event Scheduler in the menu bar
        ResultSet rs = FMemberModel.getAllFM();
        fmp.fmTable.setModel(DbUtils.resultSetToTableModel(rs));
        // this is to disable editing in the jtable
        for (Class c: Arrays.asList(Object.class, Number.class, Boolean.class)) 
        {
            TableCellEditor ce = fmp.fmTable.getDefaultEditor(c);
            if (ce instanceof DefaultCellEditor) 
            {
                ((DefaultCellEditor) ce).setClickCountToStart(Integer.MAX_VALUE);
            }
        }
        fmp.fmTable.getColumnModel().getColumn(0).setMinWidth(0);
        fmp.fmTable.getColumnModel().getColumn(0).setMaxWidth(50);
        fmp.fmTable.getColumnModel().getColumn(0).setPreferredWidth(25);
    }
    
    /**
     * Clears the fields in the jDialog
     * Invoked by reset button and close operation
     */
    void clearFields()
    {
        //clears all fields in addFMDialog
        JTextField text = (JTextField) fmp.addBeneFMCB.getEditor().getEditorComponent();
        text.setText("");
        fmp.fnameAddMemberTF.setText("");
        fmp.mnameAddMemberTF.setText("");
        fmp.lnameAddMemberTF.setText("");
        fmp.relAddMemberCB.setSelectedIndex(0);
        fmp.ageAddMemberSpin.setValue(0);
        fmp.maleAddMemberRB.setSelected(true);
        fmp.heaAddMemberCB.setSelectedIndex(0);
        fmp.occAddMemberTF.setText("");
        fmp.remarksAddMemberTA.setText("");
        
        //clears all fields in editFMDialog
        JTextField text1 = (JTextField) fmp.addBeneFMCB.getEditor().getEditorComponent();
        text1.setText("");
        fmp.fnameEditMemberTF.setText("");
        fmp.mnameEditMemberTF.setText("");
        fmp.lnameEditMemberTF.setText("");
        fmp.relEditMemberCB.setSelectedIndex(0);
        fmp.ageEditMemberSpin.setValue(0);
        fmp.maleEditMemberRB.setSelected(true);
        fmp.heaEditMemberCB.setSelectedIndex(0);
        fmp.occEditMemberTF.setText("");
        fmp.remarksEditMemberTA.setText("");
    }
    
    void initRBG()
    {
        fmp.sexAddFMRB.add(fmp.maleAddMemberRB);
        fmp.sexAddFMRB.add(fmp.femaleAddMemberRB);
        fmp.maleAddMemberRB.setSelected(true);
    }
    
}
