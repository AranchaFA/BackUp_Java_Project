/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panelConImagenFondo;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author aranx
 */
class PropiedadesImagen implements Serializable{
    
    private File ficheroImagen;
    private float opacidad;

    public PropiedadesImagen(File ficheroImagen, float opacidad) {
        this.ficheroImagen = ficheroImagen;
        this.opacidad = opacidad;
    }

    public File getFicheroImagen() {
        return ficheroImagen;
    }

    public void setFicheroImagen(File ficheroImagen) {
        this.ficheroImagen = ficheroImagen;
    }

    public float getOpacidad() {
        return opacidad;
    }

    public void setOpacidad(float opacidad) {
        this.opacidad = opacidad;
    }
    
    
    
}
