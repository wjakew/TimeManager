/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator_gui;

import generator.Raport_Generator;
import timemanager.TimeManager_Container;

/**
 *
 * @author kubaw
 */
public class calculation_window extends javax.swing.JDialog {

    TimeManager_Container session_tmc;
    int hours;
    double money;
    int check;
    /**
     * Creates new form calculation_window
     */
    public calculation_window(java.awt.Frame parent, boolean modal,TimeManager_Container session_tmc,int hours,double money) {
        super(parent, modal);
        this.session_tmc = session_tmc;
        this.hours = hours;
        this.money = money;
        check = -1;
        initComponents();
        this.setLocationRelativeTo(null);
        load_window();
        setVisible(true);
    }
    
    /**
     * Function for loading content to window
     */
    void load_window(){
        Raport_Generator rg = new Raport_Generator(session_tmc,0);
        field_raport.setText(rg.generate_raw());
        label_hoursamount.setText("Amount of work hours (minimal): "+Long.toString(rg.converted_hours)+"/"+Integer.toString(hours));
        label_paycheck.setText("Estimated paycheck: "+ rg.converted_hours*money);
        field_raport.setEditable(false);
        label_rawhours.setText("Raw hours calculation: "+rg.converted_hours);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        field_raport = new javax.swing.JTextArea();
        label_hoursamount = new javax.swing.JLabel();
        label_paycheck = new javax.swing.JLabel();
        check_savefile = new javax.swing.JCheckBox();
        button_close = new javax.swing.JButton();
        label_rawhours = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Summary");

        field_raport.setColumns(20);
        field_raport.setRows(5);
        jScrollPane1.setViewportView(field_raport);

        label_hoursamount.setText("Amount of work hours (minimal):");

        label_paycheck.setText("Estimated paycheck:");

        check_savefile.setText("Save file after");
        check_savefile.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                check_savefileItemStateChanged(evt);
            }
        });

        button_close.setText("Close");
        button_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_closeActionPerformed(evt);
            }
        });

        label_rawhours.setText("Raw hours calculation:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_close, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label_hoursamount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(check_savefile))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_paycheck)
                            .addComponent(label_rawhours))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_hoursamount)
                    .addComponent(check_savefile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_rawhours)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(label_paycheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_close, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void check_savefileItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_check_savefileItemStateChanged
        if ( evt.getStateChange() == 1 ){
            check = 1;
        }
    }//GEN-LAST:event_check_savefileItemStateChanged

    private void button_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_closeActionPerformed
        System.out.println("CHECK DATA: "+check);
        if (check == 1){
            session_tmc.dump_container();
        }
        System.exit(0);
    }//GEN-LAST:event_button_closeActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_close;
    private javax.swing.JCheckBox check_savefile;
    private javax.swing.JTextArea field_raport;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_hoursamount;
    private javax.swing.JLabel label_paycheck;
    private javax.swing.JLabel label_rawhours;
    // End of variables declaration//GEN-END:variables
}
