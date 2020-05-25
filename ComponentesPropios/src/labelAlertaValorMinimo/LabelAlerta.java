/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labelAlertaValorMinimo;

import javax.swing.JLabel;

/**
 *
 * @author aranx
 */
public class LabelAlerta extends JLabel{ // Ya implementa Serializable
    
    public ParametrosAlerta parametrosAlerta;

    public LabelAlerta() {
    }

    public ParametrosAlerta getParametrosAlerta() {
        return parametrosAlerta;
    }

    public void setParametrosAlerta(ParametrosAlerta parametrosAlerta) {
        this.parametrosAlerta = parametrosAlerta;
    }
    
    
    
}
