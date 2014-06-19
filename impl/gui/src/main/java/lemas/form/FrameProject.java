/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lemas.form;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import lemas.model.LemasLog;
import lemas.model.LesmaReflection;
import lemas.model.Project;
import lemas.model.Runner;
import lemas.model.TrustModelBean;
import lemas.model.Workspace;
import lemas.util.Data;
import lemas.util.Message;

@SuppressWarnings("all")
public class FrameProject extends JDialog {

    private static final long serialVersionUID = 1L;
    private static FrameProject instance;
    private Project project = null;

    public static FrameProject getInstance() {
        if (instance == null) {
            instance = new FrameProject();
        }
        return instance;
    }

    void load() {
        project = new Project();
        List<TrustModelBean> list = LesmaReflection.getTrustModels();
        cbTrustModelList.removeAllItems();
        for (TrustModelBean item : list) {
            cbTrustModelList.addItem(item);
        }
    }

    private FrameProject() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(false);
    }    

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbTrustModelList = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        btRun = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        checkMonitor = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIp = new javax.swing.JTextField();
        txtContainer = new javax.swing.JTextField();
        btSave = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelARRF = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panelLoad = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtLoad = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Project");
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);

        cbTrustModelList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
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

        checkMonitor.setText("Monitor");

        jLabel2.setText("Host:");

        jLabel3.setText("Container name:");

        txtIp.setText("127.0.0.1");
        txtIp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIpKeyReleased(evt);
            }
        });

        txtContainer.setText("Agents-Container");

        btSave.setText("Save Project");
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout panelARRFLayout = new javax.swing.GroupLayout(panelARRF);
        panelARRF.setLayout(panelARRFLayout);
        panelARRFLayout.setHorizontalGroup(
            panelARRFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
        );
        panelARRFLayout.setVerticalGroup(
            panelARRFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Properties", panelARRF);

        txtLoad.setColumns(20);
        txtLoad.setRows(5);
        jScrollPane2.setViewportView(txtLoad);

        javax.swing.GroupLayout panelLoadLayout = new javax.swing.GroupLayout(panelLoad);
        panelLoad.setLayout(panelLoadLayout);
        panelLoadLayout.setHorizontalGroup(
            panelLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
        );
        panelLoadLayout.setVerticalGroup(
            panelLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("File", panelLoad);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtIp, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtContainer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkMonitor))
                    .addComponent(cbTrustModelList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(95, 95, 95))
            .addGroup(layout.createSequentialGroup()
                .addComponent(btSave, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btRun, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(txtContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkMonitor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbTrustModelList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSave)
                    .addComponent(btCancel)
                    .addComponent(btRun))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbTrustModelListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTrustModelListActionPerformed

    }//GEN-LAST:event_cbTrustModelListActionPerformed

    private void btRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRunActionPerformed
        try {
            updateProject();
            Data.projectToFile(project, project.getSaveIn());
            this.setVisible(false);
            FrameMain.getInstance().visibleWindows(true);
            Runner.run(project);
            LemasLog.clean();
        } catch (Exception ex) {
            Message.error(ex.getMessage(), this);
            Logger.getLogger(FrameProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btRunActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        FrameMain.getInstance().getWindow().setVisible(false);
    }//GEN-LAST:event_btCancelActionPerformed


    private void txtIpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIpKeyReleased
        
    }//GEN-LAST:event_txtIpKeyReleased

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
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
    }//GEN-LAST:event_btSaveActionPerformed
   
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
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
        //</editor-fold>

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
    private javax.swing.JComboBox cbTrustModelList;
    private javax.swing.JCheckBox checkMonitor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel panelARRF;
    private javax.swing.JPanel panelLoad;
    private javax.swing.JTextField txtContainer;
    private javax.swing.JTextField txtIp;
    private javax.swing.JTextArea txtLoad;
    // End of variables declaration//GEN-END:variables

    void clean() {
        project = new Project();
        updateScreen(project);
    }

    private void updateProject() {
        project.setHost(txtIp.getText());
        TrustModelBean tmb = (TrustModelBean) cbTrustModelList.getSelectedItem();
        project.setTrustmodel(tmb.getName());
        project.setClazz(tmb.getClazz().getName());
        project.setConteiner(txtContainer.getText());
        project.setMonitor(checkMonitor.isSelected());
        project.setLoading(txtLoad.getText());
    }

    public void updateScreen(Project project) {
        load();
        this.project = project;
        txtIp.setText(project.getHost());        
        txtContainer.setText(this.project.getConteiner());
        checkMonitor.setSelected(project.isMonitor());
        for (int index = 0; index < cbTrustModelList.getItemCount(); index++) {
            TrustModelBean item = (TrustModelBean) cbTrustModelList.getItemAt(index);
            if (project.getTrustmodel() != null && project.getTrustmodel().equals(item.getName())) {
                this.cbTrustModelList.setSelectedItem(item);
            }
        }        
        txtLoad.setText(project.getLoading());
        txtLoad.setCaretPosition(0);
    }
}
