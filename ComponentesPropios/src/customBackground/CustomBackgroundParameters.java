/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customBackground;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;

/**
 *
 * @author aranx
 */
public class CustomBackgroundParameters implements Serializable {

    // ATRIBUTOS
    private Color colorFondo;
    private File ficheroImagen;

    // MÉTODOS
    // Getters + Setters
    public Color getColorFondo() {
        return colorFondo;
    }

    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
    }

    public File getFicheroImagen() {
        return ficheroImagen;
    }

    public void setFicheroImagen(File ficheroImagen) {
        this.ficheroImagen = ficheroImagen;
    }

    // Constructores
    public CustomBackgroundParameters() {
    }

    public CustomBackgroundParameters(Color colorFondo) {
        this.ficheroImagen=null;
        this.colorFondo = colorFondo;
    }

    public CustomBackgroundParameters(File ficheroImagen) {
        this.colorFondo=null;
        this.ficheroImagen=ficheroImagen;
    }


}
