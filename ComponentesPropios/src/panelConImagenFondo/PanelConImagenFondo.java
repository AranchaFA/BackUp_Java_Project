/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panelConImagenFondo;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author aranx
 */
public class PanelConImagenFondo extends JPanel{ // Ya implementa Serializable
    
    private PropiedadesImagen propiedadesImagen;

    public PanelConImagenFondo() {
    }

    public PropiedadesImagen getPropiedadesImagen() {
        return propiedadesImagen;
    }

    public void setPropiedadesImagen(PropiedadesImagen propiedadesImagen) {
        this.propiedadesImagen = propiedadesImagen;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); 
        
    }
    
    
    
}
