/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labelAlertaValorMinimo;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author aranx
 */
class ParametrosAlerta implements Serializable{
    
    private double valorMinimo;
    private Color colorNormal;
    private Color colorAlerta;

    public ParametrosAlerta(double valorMinimo, Color colorNormal, Color colorAlerta) {
        this.valorMinimo = valorMinimo;
        this.colorNormal = colorNormal;
        this.colorAlerta = colorAlerta;
    }

    public double getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public Color getColorNormal() {
        return colorNormal;
    }

    public void setColorNormal(Color colorNormal) {
        this.colorNormal = colorNormal;
    }

    public Color getColorAlerta() {
        return colorAlerta;
    }

    public void setColorAlerta(Color colorAlerta) {
        this.colorAlerta = colorAlerta;
    }
    
    
    
}
