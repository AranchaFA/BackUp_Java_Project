/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textFieldLimitado;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

/**
 *
 * @author aranx
 */
public class PropiedadesLimitacionPanel extends javax.swing.JPanel {

    /**
     * Creates new form PropiedadesLimitacionPanel
     */
    public PropiedadesLimitacionPanel() {
        initComponents();

        // Listener para impedir que se introduzcan caracteres que no sean dígitos en el jTextField del límite de caracteres
        jTF_limiteCaracteres.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                String patron = "[0-9]{1}";
                char caracterPulsado = ke.getKeyChar();
                // Si no es ni dígito ni la tecla de borrar
                if (!Pattern.matches(patron, String.valueOf(caracterPulsado)) && caracterPulsado != KeyEvent.VK_BACK_SPACE) {
                    ke.consume();
                }
            }
        });
    }

    public PropiedadesLimitacion getPropiedadesLimitacion() {
        Integer limiteCaracteres = Integer.valueOf(jTF_limiteCaracteres.getText());
        // Los colores elegidos serán los colores de letra (foreground) de los jTextFields
        Color colorNormal = jTF_colorNormal.getForeground();
        Color colorAlerta = jTF_colorAlerta.getForeground();
        return new PropiedadesLimitacion(limiteCaracteres, colorNormal, colorAlerta);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTF_colorNormal = new javax.swing.JTextField();
        jTF_colorAlerta = new javax.swing.JTextField();
        jTF_limiteCaracteres = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jColorChooser1 = new javax.swing.JColorChooser();
        jB_colorNormal = new javax.swing.JButton();
        jB_colorAlerta = new javax.swing.JButton();

        jTF_colorNormal.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTF_colorNormal.setText("000000");

        jTF_colorAlerta.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTF_colorAlerta.setText("000000");

        jTF_limiteCaracteres.setText("100");

        jLabel3.setText("Límite de caracteres");

        jColorChooser1.setColor(new java.awt.Color(0, 0, 0));

        jB_colorNormal.setText("Color por defecto");
        jB_colorNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_colorNormalActionPerformed(evt);
            }
        });

        jB_colorAlerta.setText("Color de alerta");
        jB_colorAlerta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_colorAlertaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jB_colorNormal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTF_colorNormal, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jB_colorAlerta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTF_colorAlerta, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jTF_limiteCaracteres, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jColorChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTF_colorNormal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTF_colorAlerta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jTF_limiteCaracteres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jB_colorAlerta)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jB_colorNormal)))
                .addGap(18, 18, 18)
                .addComponent(jColorChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jB_colorNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_colorNormalActionPerformed
        // Ponemos el color de letra y el valor RGB seleccionados en el jColorChooser al jTextField
        jTF_colorNormal.setForeground(jColorChooser1.getColor());
        jTF_colorNormal.setText(String.valueOf(jColorChooser1.getColor().getRGB()));
    }//GEN-LAST:event_jB_colorNormalActionPerformed

    private void jB_colorAlertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_colorAlertaActionPerformed
        // Ponemos el color de letra y el valor RGB seleccionados en el jColorChooser al jTextField
        jTF_colorAlerta.setForeground(jColorChooser1.getColor());
        jTF_colorAlerta.setText(String.valueOf(jColorChooser1.getColor().getRGB()));
    }//GEN-LAST:event_jB_colorAlertaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_colorAlerta;
    private javax.swing.JButton jB_colorNormal;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTF_colorAlerta;
    private javax.swing.JTextField jTF_colorNormal;
    private javax.swing.JTextField jTF_limiteCaracteres;
    // End of variables declaration//GEN-END:variables
}
