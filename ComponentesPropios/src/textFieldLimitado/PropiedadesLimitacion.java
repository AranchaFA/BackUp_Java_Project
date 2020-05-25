/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textFieldLimitado;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author aranx
 */
public class PropiedadesLimitacion implements Serializable {

    private int limiteCaracteres;
    private Color colorNormal;
    private Color colorAlerta;

    public PropiedadesLimitacion(int limiteCaracteres, Color colorNormal, Color colorAlerta) {
        this.limiteCaracteres = limiteCaracteres;
        this.colorNormal = colorNormal;
        this.colorAlerta = colorAlerta;
    }

    public int getLimiteCaracteres() {
        return limiteCaracteres;
    }

    public void setLimiteCaracteres(int limiteCaracteres) {
        this.limiteCaracteres = limiteCaracteres;
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
