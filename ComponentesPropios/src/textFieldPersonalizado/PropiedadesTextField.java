/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textFieldPersonalizado;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author aranx
 */
public class PropiedadesTextField implements Serializable {

    // ATRIBUTOS
    // Si es un patrón concreto (DNI, email,...) no se puede configurar de manera que se pueda ir comprobando
    // según se va tecleando (no podemos poner que son OBLIGATORIAS 8 letras en un DNI, porque no cumpliría
    // hasta no tenerlas todas escritas)
    // El límite de caracteres podemos marcarlo en la expresión regular
    private int codigoPatronElegido;
    private Color colorNormal;
    private Color colorAlerta;

    private final int DNI = 0;
    private final int TELEFONO = 1;
    private final int EMAIL = 2;
    private final int NUMERICO = 3;
    private final int ALFABETICO = 4;
    private final int ALFANUMERICO = 5;
    private final int[] codigosPatronesDisponibles = {DNI, TELEFONO, EMAIL, NUMERICO, ALFABETICO, ALFANUMERICO};

    // MÉTODOS
    // Getters + Setters
    public int getCodigoPatronElegido() {
        return codigoPatronElegido;
    }

    public void setCodigoPatronElegido(int codigoPatronElegido) {
        this.codigoPatronElegido = codigoPatronElegido;
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

    // Constructor CON TODOS LOS PARÁMETROS
    public PropiedadesTextField(int codigoPatronElegido, Color colorNormal, Color colorAlerta) {
        this.codigoPatronElegido = codigoPatronElegido;
        this.colorNormal = colorNormal;
        this.colorAlerta = colorAlerta;
    }


}
