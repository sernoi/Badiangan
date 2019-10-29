package main;

import admin.AdminController;
import admin.AdminPanel;
import beneficiary.BeneController;
import beneficiary.BenePanel;
import brgy.BrgyController;
import brgy.BrgyPanel;
import crop.CropController;
import crop.CropPanel;
import crop.harvest.HarvestController;
import crop.harvest.HarvestPanel;
import disaster.DisasterController;
import disaster.DisasterPanel;
import evac.EvacController;
import evac.EvacPanel;
import far.FarController;
import far.FarPanel;
import fmember.FMemberController;
import fmember.FMemberPanel;
import java.awt.CardLayout;
import java.awt.Toolkit;
import livestock.LSController;
import livestock.LSPanel;
import livestock.disposal.DisposalController;
import livestock.disposal.DisposalPanel;
import login.LoginController;
import map.MapController;
import map.MapPanel;
import registration.RegController;
import registration.RegPanel;

public class MainFrame extends javax.swing.JFrame {

    AdminPanel ap = new AdminPanel();
    BenePanel bp = new BenePanel();
    RegPanel rp = new RegPanel();
    FMemberPanel fmp = new FMemberPanel();
    CropPanel cp = new CropPanel();
    LSPanel lsp = new LSPanel();
    HarvestPanel hp = new HarvestPanel();
    DisposalPanel dp = new DisposalPanel();
    DisasterPanel disp = new DisasterPanel();
    FarPanel fp = new FarPanel();
    EvacPanel ep = new EvacPanel();
    MapPanel mp = new MapPanel();
    BrgyPanel brgyp = new BrgyPanel();
    
    public MainFrame() 
    {
        initComponents();
        

        mp = new MapPanel();
        new MapController(mp);
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        mainPanel.add(mp,"MapPanel");
        cl.show(mainPanel, "MapPanel");  
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/title.png")));

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DateTimePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        realTimeLabel = new javax.swing.JLabel();
        fnameLbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        adminIDTF = new javax.swing.JLabel();
        deptLbl = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        MenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        mapMenuItem = new javax.swing.JMenuItem();
        logoutMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        beneMenu = new javax.swing.JMenu();
        beneMenuItem = new javax.swing.JMenuItem();
        fmMenuItem = new javax.swing.JMenuItem();
        cropsMenuItem = new javax.swing.JMenuItem();
        lsMenuItem = new javax.swing.JMenuItem();
        disasterMenu = new javax.swing.JMenu();
        evacSitesMenuItem = new javax.swing.JMenuItem();
        disasterMenuItem = new javax.swing.JMenuItem();
        manageMenu = new javax.swing.JMenu();
        adminMenuItem = new javax.swing.JMenuItem();
        brgyMenuItem = new javax.swing.JMenuItem();
        reportMenu = new javax.swing.JMenu();
        regMenuItem = new javax.swing.JMenuItem();
        farMenuItem = new javax.swing.JMenuItem();
        harvestMenuItem = new javax.swing.JMenuItem();
        disposalMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Badiangan");

        DateTimePanel.setBackground(new java.awt.Color(51, 51, 51));
        DateTimePanel.setForeground(new java.awt.Color(153, 153, 153));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Date");

        dateLabel.setForeground(new java.awt.Color(255, 255, 255));

        timeLabel.setForeground(new java.awt.Color(255, 255, 255));
        timeLabel.setText("Time");

        realTimeLabel.setForeground(new java.awt.Color(255, 255, 255));

        fnameLbl.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Logged in:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Admin ID:");

        adminIDTF.setForeground(new java.awt.Color(255, 255, 255));
        adminIDTF.setText("0");

        deptLbl.setForeground(new java.awt.Color(255, 255, 255));
        deptLbl.setText("admin");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Dept:");

        javax.swing.GroupLayout DateTimePanelLayout = new javax.swing.GroupLayout(DateTimePanel);
        DateTimePanel.setLayout(DateTimePanelLayout);
        DateTimePanelLayout.setHorizontalGroup(
            DateTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DateTimePanelLayout.createSequentialGroup()
                .addContainerGap(109, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deptLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminIDTF, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fnameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(timeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(realTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        DateTimePanelLayout.setVerticalGroup(
            DateTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(DateTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fnameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(realTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DateTimePanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(DateTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(adminIDTF)
                    .addComponent(deptLbl)
                    .addComponent(jLabel5)))
        );

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setLayout(new java.awt.CardLayout());

        fileMenu.setForeground(new java.awt.Color(0, 0, 0));
        fileMenu.setText("File");

        mapMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        mapMenuItem.setText("Map");
        mapMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mapMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(mapMenuItem);

        logoutMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        logoutMenuItem.setText("Logout");
        logoutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(logoutMenuItem);

        exitMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        MenuBar.add(fileMenu);

        beneMenu.setForeground(new java.awt.Color(0, 0, 0));
        beneMenu.setText("Beneficiary");

        beneMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        beneMenuItem.setText("Beneficiaries");
        beneMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beneMenuItemActionPerformed(evt);
            }
        });
        beneMenu.add(beneMenuItem);

        fmMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        fmMenuItem.setText("Family Members");
        fmMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fmMenuItemActionPerformed(evt);
            }
        });
        beneMenu.add(fmMenuItem);

        cropsMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        cropsMenuItem.setText("Crops/Trees");
        cropsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cropsMenuItemActionPerformed(evt);
            }
        });
        beneMenu.add(cropsMenuItem);

        lsMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        lsMenuItem.setText("Livestocks");
        lsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lsMenuItemActionPerformed(evt);
            }
        });
        beneMenu.add(lsMenuItem);

        MenuBar.add(beneMenu);

        disasterMenu.setForeground(new java.awt.Color(0, 0, 0));
        disasterMenu.setText("Disaster");

        evacSitesMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        evacSitesMenuItem.setText("Evacuation Sites");
        evacSitesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                evacSitesMenuItemActionPerformed(evt);
            }
        });
        disasterMenu.add(evacSitesMenuItem);

        disasterMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        disasterMenuItem.setText("Disasters");
        disasterMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disasterMenuItemActionPerformed(evt);
            }
        });
        disasterMenu.add(disasterMenuItem);

        MenuBar.add(disasterMenu);

        manageMenu.setForeground(new java.awt.Color(0, 0, 0));
        manageMenu.setText("Manage");

        adminMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        adminMenuItem.setText("Admin");
        adminMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminMenuItemActionPerformed(evt);
            }
        });
        manageMenu.add(adminMenuItem);

        brgyMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        brgyMenuItem.setText("Brgy");
        brgyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brgyMenuItemActionPerformed(evt);
            }
        });
        manageMenu.add(brgyMenuItem);

        MenuBar.add(manageMenu);

        reportMenu.setForeground(new java.awt.Color(0, 0, 0));
        reportMenu.setText("Report");

        regMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        regMenuItem.setText("Registration");
        regMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regMenuItemActionPerformed(evt);
            }
        });
        reportMenu.add(regMenuItem);

        farMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        farMenuItem.setText("Family Assistance Record");
        farMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                farMenuItemActionPerformed(evt);
            }
        });
        reportMenu.add(farMenuItem);

        harvestMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        harvestMenuItem.setText("Crops Harvest");
        harvestMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                harvestMenuItemActionPerformed(evt);
            }
        });
        reportMenu.add(harvestMenuItem);

        disposalMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        disposalMenuItem.setText("Livestocks Disposal");
        disposalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disposalMenuItemActionPerformed(evt);
            }
        });
        reportMenu.add(disposalMenuItem);

        MenuBar.add(reportMenu);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DateTimePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(DateTimePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void adminMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminMenuItemActionPerformed
        ap = new AdminPanel();
        new AdminController(ap);
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        mainPanel.add(ap,"AdminPanel");
        cl.show(mainPanel, "AdminPanel");  
    }//GEN-LAST:event_adminMenuItemActionPerformed

    private void beneMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beneMenuItemActionPerformed
        bp = new BenePanel();
        new BeneController(bp,this);
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        mainPanel.add(bp,"BenePanel");
        cl.show(mainPanel, "BenePanel");   
    }//GEN-LAST:event_beneMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void mapMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mapMenuItemActionPerformed
        mp = new MapPanel();
        new MapController(mp);
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        mainPanel.add(mp,"MapPanel");
        cl.show(mainPanel, "MapPanel");  
    }//GEN-LAST:event_mapMenuItemActionPerformed

    private void regMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regMenuItemActionPerformed
        rp = new RegPanel();
        new RegController(rp);
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        mainPanel.add(rp,"RegPanel");
        cl.show(mainPanel, "RegPanel");  
    }//GEN-LAST:event_regMenuItemActionPerformed

    private void fmMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fmMenuItemActionPerformed
        fmp = new FMemberPanel();
        new FMemberController(fmp);
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        mainPanel.add(fmp,"FMemberPanel");
        cl.show(mainPanel, "FMemberPanel");  
    }//GEN-LAST:event_fmMenuItemActionPerformed

    private void cropsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cropsMenuItemActionPerformed
        cp = new CropPanel();
        new CropController(cp);
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        mainPanel.add(cp,"CropPanel");
        cl.show(mainPanel, "CropPanel");   
    }//GEN-LAST:event_cropsMenuItemActionPerformed

    private void lsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lsMenuItemActionPerformed
        lsp = new LSPanel();
        new LSController(lsp);
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        mainPanel.add(lsp,"LSPanel");
        cl.show(mainPanel, "LSPanel");  
    }//GEN-LAST:event_lsMenuItemActionPerformed

    private void harvestMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_harvestMenuItemActionPerformed
        hp = new HarvestPanel();
        new HarvestController(hp,mainPanel);
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        mainPanel.add(hp,"HarvestPanel");
        cl.show(mainPanel, "HarvestPanel"); 
    }//GEN-LAST:event_harvestMenuItemActionPerformed

    private void disposalMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disposalMenuItemActionPerformed
        dp = new DisposalPanel();
        new DisposalController(dp,mainPanel);
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        mainPanel.add(dp,"HarvestPanel");
        cl.show(mainPanel, "HarvestPanel"); 
    }//GEN-LAST:event_disposalMenuItemActionPerformed

    private void farMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_farMenuItemActionPerformed
        fp = new FarPanel();
        new FarController(fp);
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        mainPanel.add(fp,"FarPanel");
        cl.show(mainPanel, "FarPanel"); 
    }//GEN-LAST:event_farMenuItemActionPerformed

    private void disasterMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disasterMenuItemActionPerformed
        disp = new DisasterPanel();
        new DisasterController(disp);
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        mainPanel.add(disp,"DisasterPanel");
        cl.show(mainPanel, "DisasterPanel"); 
    }//GEN-LAST:event_disasterMenuItemActionPerformed

    private void logoutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutMenuItemActionPerformed
        setVisible(false);
        dispose();
        Start.main(null);
    }//GEN-LAST:event_logoutMenuItemActionPerformed

    private void evacSitesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evacSitesMenuItemActionPerformed
        ep = new EvacPanel();
        new EvacController(ep);
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        mainPanel.add(ep,"EvacPanel");
        cl.show(mainPanel, "EvacPanel"); 
    }//GEN-LAST:event_evacSitesMenuItemActionPerformed

    private void brgyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brgyMenuItemActionPerformed
        brgyp = new BrgyPanel();
        new BrgyController(brgyp);
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        mainPanel.add(brgyp,"BrgyPanel");
        cl.show(mainPanel, "BrgyPanel"); 
    }//GEN-LAST:event_brgyMenuItemActionPerformed

    public int getToken(String dept)
    {
        if(dept.equals("MSWDO"))
        {
            return 1;
        }
        else if(dept.equals("MAO"))
        {
            return 2;
        }
        else if(dept.equals("MDRRMO"))
        {
            return 3;
        }
        else if(dept.equals("MPDO"))
        {
            return 4;
        }
        else if(dept.equals("VIEWER"))
        {
            return 5;
        }
        else
        {
            return 0;
        }
    }
    
    public void setConfig()
    {
        switch(getToken(deptLbl.getText()))
        {
            //MSWDO
            case 1:
                removeAllDeleteBtn();
                cropsMenuItem.setVisible(false);
                lsMenuItem.setVisible(false);
                disasterMenu.setVisible(false);
                adminMenuItem.setVisible(false);
                harvestMenuItem.setVisible(false);
                disposalMenuItem.setVisible(false);
                break; 
            //MAO
            case 2:
                removeAllDeleteBtn();
                fmMenuItem.setVisible(false);
                disasterMenu.setVisible(false);
                manageMenu.setVisible(false);
                regMenuItem.setVisible(false);
                farMenuItem.setVisible(false);
                break; 
            //MDRRMO
            case 3:
                removeAllDeleteBtn();
                beneMenu.setVisible(false);
                manageMenu.setVisible(false);
                reportMenu.setVisible(false);
                break;
            //MPDO
            case 4:
                removeAllDeleteBtn();
                beneMenu.setVisible(false);
                manageMenu.setVisible(false);
                disasterMenu.setVisible(false);
                break; 
            //MPDO
            case 5:
                removeAllDeleteBtn();
                beneMenu.setVisible(false);
                disasterMenu.setVisible(false);
                manageMenu.setVisible(false);
                reportMenu.setVisible(false);
                break; 
        }
    }
    
    void removeAllDeleteBtn()
    {
        ap.deleteAdminBtn.setVisible(false);
        ap.deleteAdminMenuItem.setVisible(false);
        bp.deleteBeneBtn.setVisible(false);
        bp.deleteBeneMenuItem.setVisible(false);
        bp.deleteCropMenuItem.setVisible(false);
        bp.deleteLSMenuItem.setVisible(false);
        bp.deleteMemberMenuItem.setVisible(false);
        rp.deleteBtn.setVisible(false);
        rp.deleteMenuItem.setVisible(false);
        fmp.deleteFMBtn.setVisible(false);
        fmp.deleteFMMenuItem.setVisible(false);
        cp.deleteBtn.setVisible(false);
        cp.deleteMenuItem.setVisible(false);
        lsp.deleteBtn.setVisible(false);
        lsp.deleteMenuItem.setVisible(false);
        hp.deleteBtn.setVisible(false);
        hp.deleteMenuItem.setVisible(false);
        dp.deleteBtn.setVisible(false);
        dp.deleteMenuItem.setVisible(false);
        fp.deleteBtn.setVisible(false);
        fp.deleteMenuItem.setVisible(false);
        disp.deleteBtn.setVisible(false);
        disp.deleteMenuItem.setVisible(false);
        ep.deleteBtn.setVisible(false);
        ep.deleteMenuItem.setVisible(false);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DateTimePanel;
    private javax.swing.JMenuBar MenuBar;
    public javax.swing.JLabel adminIDTF;
    public javax.swing.JMenuItem adminMenuItem;
    public javax.swing.JMenu beneMenu;
    public javax.swing.JMenuItem beneMenuItem;
    public javax.swing.JMenuItem brgyMenuItem;
    public javax.swing.JMenuItem cropsMenuItem;
    public javax.swing.JLabel dateLabel;
    public javax.swing.JLabel deptLbl;
    public javax.swing.JMenu disasterMenu;
    public javax.swing.JMenuItem disasterMenuItem;
    public javax.swing.JMenuItem disposalMenuItem;
    public javax.swing.JMenuItem evacSitesMenuItem;
    public javax.swing.JMenuItem exitMenuItem;
    public javax.swing.JMenuItem farMenuItem;
    public javax.swing.JMenu fileMenu;
    public javax.swing.JMenuItem fmMenuItem;
    public javax.swing.JLabel fnameLbl;
    public javax.swing.JMenuItem harvestMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    public javax.swing.JMenuItem logoutMenuItem;
    public javax.swing.JMenuItem lsMenuItem;
    private javax.swing.JPanel mainPanel;
    public javax.swing.JMenu manageMenu;
    public javax.swing.JMenuItem mapMenuItem;
    public javax.swing.JLabel realTimeLabel;
    public javax.swing.JMenuItem regMenuItem;
    public javax.swing.JMenu reportMenu;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
}
