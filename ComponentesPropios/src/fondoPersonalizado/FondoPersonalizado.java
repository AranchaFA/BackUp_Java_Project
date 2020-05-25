/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fondoPersonalizado;

import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author aranx
 */
public class FondoPersonalizado extends JPanel implements Serializable {

    private PropiedadesFondo propiedadesFondo;

    public FondoPersonalizado() {
    }

    public PropiedadesFondo getPropiedadesFondo() {
        return propiedadesFondo;
    }

    public void setPropiedadesFondo(PropiedadesFondo propiedadesFondo) {
        this.propiedadesFondo = propiedadesFondo;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        // Antes de elegir en el editor las propiedadesFondo es null, y daría nullPointerExcptn el if interno
        if (propiedadesFondo != null) {
            if (propiedadesFondo.getFicheroImagen() != null && propiedadesFondo.getFicheroImagen().exists()) {
                ImageIcon imagen = new ImageIcon(propiedadesFondo.getFicheroImagen().getAbsolutePath());
                grphcs.drawImage(imagen.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
            } else if (propiedadesFondo.getColorFondo() != null) {
                setBackground(propiedadesFondo.getColorFondo());
            }
        }
    }

}
