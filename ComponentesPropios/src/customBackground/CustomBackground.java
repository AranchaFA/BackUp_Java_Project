/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customBackground;

import java.awt.Graphics;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author aranx
 */
public class CustomBackground extends JPanel implements Serializable {

    private CustomBackgroundParameters parametros;

    public CustomBackgroundParameters getParametros() {
        return parametros;
    }

    public void setParametros(CustomBackgroundParameters parametros) {
        this.parametros = parametros;
    }
    
    public CustomBackground() {
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        //antes de que se entre en el editor de propiedades propiedadesImagen es null debemos tenerlo en cuenta y no pintar la imagen
        if (this.parametros != null) {
            if ((this.parametros.getFicheroImagen() != null) && (this.parametros.getFicheroImagen().exists())) {
                asignarImagenFondo();
            }
            if ((this.parametros.getColorFondo() != null)) {
               this.setBackground(this.parametros.getColorFondo());
            }
        }
    }

    public void asignarImagenFondo() {
        try {
            ImageIcon imagen = new ImageIcon(this.parametros.getFicheroImagen().getCanonicalPath());
            Graphics graphics = this.getGraphics();
            graphics.drawImage(imagen.getImage(), 0, 0, this.getSize().height, this.getSize().width, null);
            super.paintComponents(graphics);
        } catch (IOException ex) {
            Logger.getLogger(CustomBackground.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
