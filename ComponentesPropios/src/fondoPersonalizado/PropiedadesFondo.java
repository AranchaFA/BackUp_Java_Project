/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fondoPersonalizado;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;

/**
 *
 * @author aranx
 */
public class PropiedadesFondo implements Serializable {

    private File ficheroImagen;
    private Color colorFondo;

    public PropiedadesFondo(File ficheroImagen) {
        this.ficheroImagen = ficheroImagen;
    }

    public PropiedadesFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
    }
    

    public File getFicheroImagen() {
        return ficheroImagen;
    }

    public void setFicheroImagen(File ficheroImagen) {
        this.ficheroImagen = ficheroImagen;
    }

    public Color getColorFondo() {
        return colorFondo;
    }

    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
    }

}
