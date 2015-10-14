/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lemas.form;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import openjade.trust.ITrustModel;
import openjade.trust.TrustModelFactory;
import lemas.Lemas;
import lemas.db.LemasDB;
import lemas.model.LemasLog;
import lemas.model.LemasReflection;
import lemas.model.Project;
import lemas.model.Runner;
import lemas.model.ClassBean;
import lemas.model.Workspace;
import lemas.trust.Constants;
import lemas.util.Data;
import lemas.util.Message;

@SuppressWarnings("all")
public class FrameProject extends JDialog {

    private static final long serialVersionUID = 1L;
    private static FrameProject instance;
    private Project project = null;
    private boolean bVerLog = true;
    private boolean bVerResult = true;
    private boolean bSimulated = false;
    private boolean bSaveDB = false;

    public static FrameProject getInstance() {
        if (instance == null) {
            instance = new FrameProject();
        }
        return instance;
    }

    void load() {
        project = new Project();
        List<ClassBean> list = LemasReflection.getTrustModels();
        cbTrustModelList.removeAllItems();
        for (ClassBean item : list) {
            cbTrustModelList.addItem(item);
        }
        cbMetrics.removeAllItems();
        list = LemasReflection.getMetrics();
        for (ClassBean item : list) {
            cbMetrics.addItem(item);
        }
    }

    private FrameProject() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(false);
        String[] col = new String[]{"Properties", "Value"};
        DefaultTableModel dtm = new DefaultTableModel(col, 0);
        jTableProperties.setModel(dtm);
    }

    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbTrustModelList = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        btRun = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIp = new javax.swing.JTextField();
        txtContainer = new javax.swing.JTextField();
        btSave = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelProperties = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableProperties = new javax.swing.JTable();
        cbMetrics = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        checkLog = new javax.swing.JCheckBox();
        checkResult = new javax.swing.JCheckBox();
        checkSimulated = new javax.swing.JCheckBox();
        checkSaveBD = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Project");

        cbTrustModelList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbTrustModelList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTrustModelListItemStateChanged(evt);
            }
        });
        cbTrustModelList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTrustModelListActionPerformed(evt);
            }
        });

        jLabel1.setText("Trust Model:");

        btRun.setText("Run");
        btRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRunActionPerformed(evt);
            }
        });

        btCancel.setText("Close");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        jLabel2.setText("Host:");

        jLabel3.setText("Container name:");

        txtIp.setText("127.0.0.1");
        txtIp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIpKeyReleased(evt);
            }
        });

        txtContainer.setText("Agents-Container");

        btSave.setText("Save");
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });

        jTableProperties.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTableProperties);

        javax.swing.GroupLayout panelPropertiesLayout = new javax.swing.GroupLayout(panelProperties);
        panelProperties.setLayout(panelPropertiesLayout);
        panelPropertiesLayout.setHorizontalGroup(
            panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropertiesLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelPropertiesLayout.setVerticalGroup(
            panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Properties", panelProperties);

        cbMetrics.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbMetrics.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMetricsItemStateChanged(evt);
            }
        });
        cbMetrics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMetricsActionPerformed(evt);
            }
        });

        jLabel4.setText("Metrics:");

        checkLog.setText("Log");
        checkLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkLogActionPerformed(evt);
            }
        });

        checkResult.setText("Result");
        checkResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkResultActionPerformed(evt);
            }
        });

        checkSimulated.setText("Simulated");
        checkSimulated.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkSimulatedActionPerformed(evt);
            }
        });

        checkSaveBD.setText("Salvar em DB");
        checkSaveBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkSaveBDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btSave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIp)
                                    .addComponent(txtContainer, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cbTrustModelList, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbMetrics, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkLog)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(checkResult)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkSimulated)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkSaveBD)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btRun, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbTrustModelList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbMetrics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkLog)
                    .addComponent(checkResult)
                    .addComponent(checkSimulated)
                    .addComponent(checkSaveBD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSave)
                    .addComponent(btCancel)
                    .addComponent(btRun)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbTrustModelListItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTrustModelListItemStateChanged
        if (evt.getStateChange() == 1) {
            changeItemTrustModel((ClassBean) evt.getItem());
        }
    }//GEN-LAST:event_cbTrustModelListItemStateChanged

    private void cbMetricsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMetricsItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMetricsItemStateChanged

    private void cbMetricsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMetricsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMetricsActionPerformed

    private void checkLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkLogActionPerformed
        bVerLog = checkLog.isSelected();        
    }//GEN-LAST:event_checkLogActionPerformed

    private void checkResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkResultActionPerformed
        bVerResult = checkResult.isSelected();
    }//GEN-LAST:event_checkResultActionPerformed

    private void checkSimulatedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkSimulatedActionPerformed
        bSimulated = checkSimulated.isSelected();
    }//GEN-LAST:event_checkSimulatedActionPerformed

    private void checkSaveBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkSaveBDActionPerformed
        bSaveDB = checkSaveBD.isSelected();
    }//GEN-LAST:event_checkSaveBDActionPerformed

    private void cbTrustModelListActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbTrustModelListActionPerformed
    }// GEN-LAST:event_cbTrustModelListActionPerformed

    private void btRunActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btRunActionPerformed
        try {
            cleanAll();
            updateProject();
            Data.projectToFile(project, project.getSaveIn());
            //this.setVisible(false);
            FrameMain.getInstance().visibleWindows(true);
//          Runner.run(project);
            Runner.run();
            LemasLog.clean();
            LemasLog.setEnable(bVerLog);
        } catch (Exception ex) {
            Message.error(ex.getMessage(), this);
            Logger.getLogger(FrameProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_btRunActionPerformed
    
    
    public void cleanAll() {
        LemasDB db = new LemasDB(bSaveDB);
        db.connect();
        db.cleanAll();
        db.close();
    }

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btCancelActionPerformed
    	this.setVisible(false);
        DialogResult.getInstance().setVisible(false);        
    }// GEN-LAST:event_btCancelActionPerformed

    private void txtIpKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtIpKeyReleased

    }// GEN-LAST:event_txtIpKeyReleased

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btSaveActionPerformed
        String ext = ".lma";
        updateProject();
        if (project.getSaveIn() == null) {
            FileNameExtensionFilter filterExt = new FileNameExtensionFilter("Project Lesma file (.lma)", "lma");
            JFileChooser chooser = new JFileChooser();
            chooser.addChoosableFileFilter(filterExt);
            chooser.setCurrentDirectory(new File(Workspace.FOLDER_PROJECT));
            int retrival = chooser.showSaveDialog(this);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                try {
                    if (chooser.getSelectedFile().getName().endsWith(ext)) {
                        Data.projectToFile(project, chooser.getSelectedFile() + "");
                    } else {
                        Data.projectToFile(project, chooser.getSelectedFile() + ext);
                    }
                } catch (Exception ex) {
                    Message.error(ex.getMessage(), this);
                }
            }
        } else {
            Data.projectToFile(project, project.getSaveIn());
            Message.info("project saved", this);
        }
    }// GEN-LAST:event_btSaveActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
        // desc=" Look and feel setting code (optional) ">
		/*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase
         * /tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		// </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameProject().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btRun;
    private javax.swing.JButton btSave;
    private javax.swing.JComboBox cbMetrics;
    private javax.swing.JComboBox cbTrustModelList;
    private javax.swing.JCheckBox checkLog;
    private javax.swing.JCheckBox checkResult;
    private javax.swing.JCheckBox checkSaveBD;
    private javax.swing.JCheckBox checkSimulated;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableProperties;
    private javax.swing.JPanel panelProperties;
    private javax.swing.JTextField txtContainer;
    private javax.swing.JTextField txtIp;
    // End of variables declaration//GEN-END:variables

    void clean() {
        project = new Project();
        updateScreen(project);
    }

    private void updateProject() {
        project.setHost(txtIp.getText());
        ClassBean tmb = (ClassBean) cbTrustModelList.getSelectedItem();
        ClassBean tmb2 = (ClassBean) cbMetrics.getSelectedItem();
        project.setTrustmodel(tmb.getName());
        project.setMetricsClass(tmb2.getClazz().getName());
        project.setClazz(tmb.getClazz().getName());
        project.setConteiner(txtContainer.getText());
        project.setVerLog(bVerLog);
        project.setVerResult(bVerResult);
        project.setSimulated(bSimulated);
        project.setSaveDB(bSaveDB);
        project.getProperties().clear();        
        DefaultTableModel t = (DefaultTableModel) jTableProperties.getModel();
        for (int row = 0; row < t.getRowCount(); row++) {
            String key = (String) t.getValueAt(row, 0);
            String value = (String) t.getValueAt(row, 1);            
            project.getProperties().put(key, value);
        }
    }

    public void updateScreen(Project project) {
        load();
        this.project = project;
        txtIp.setText(project.getHost());
        txtContainer.setText(this.project.getConteiner());
        Properties pTM = new Properties();

        bVerLog = project.getVerLog();
        checkLog.setSelected(bVerLog);
        
        bVerResult = project.getVerResult();
        checkResult.setSelected(bVerResult);     
        
        bSimulated = project.getSimulated();
        checkSimulated.setSelected(bSimulated);

        bSaveDB = project.getSaveDB();
        checkSaveBD.setSelected(bSaveDB);

        for (int index = 0; index < cbMetrics.getItemCount(); index++) {
            ClassBean item = (ClassBean) cbMetrics.getItemAt(index);
            if (project.getMetricsClass() != null && item.getClazz().getName().equals(project.getMetricsClass())) {
                this.cbMetrics.setSelectedItem(item);
            }
        }

        for (int index = 0; index < cbTrustModelList.getItemCount(); index++) {
            ClassBean item = (ClassBean) cbTrustModelList.getItemAt(index);
            if (project.getTrustmodel() != null && project.getTrustmodel().equals(item.getName())) {
                this.cbTrustModelList.setSelectedItem(item);

                ITrustModel tm = (ITrustModel) TrustModelFactory.create(item.getClazz().getName());
                pTM = tm.getProperties();
                break;
            }
        }
        if (pTM != null) {
            pTM.putAll(project.getProperties());
            project.setProperties(pTM);
        }
        updateTable(project.getProperties());
    }

    private void changeItemTrustModel(ClassBean tmb) {
        ITrustModel tm = (ITrustModel) TrustModelFactory.create(tmb.getClazz().getName());
        updateTable(tm.getProperties());
    }

    private void updateTable(Properties properties) {
        DefaultTableModel t = (DefaultTableModel) jTableProperties.getModel();
        while (t.getRowCount() > 0) {
            t.removeRow(0);
        }
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String[] registro = {key, (String) properties.get(key)};
            t.insertRow(t.getRowCount(), registro);
        }
    }

    public Project getCurrentProject() {
        return project;
    }

    public boolean getVerLog() {
        return bVerLog;
    }
    
    public boolean getVerResult() {
        return bVerResult;
    }
    
    
    public boolean getSimulated() {
        return bSimulated;
    }

    public boolean getSaveDB() {
        return bSaveDB;
    }
}
