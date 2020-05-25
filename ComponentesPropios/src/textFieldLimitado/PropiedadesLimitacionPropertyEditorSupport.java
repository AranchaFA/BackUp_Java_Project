/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textFieldLimitado;

import java.awt.Component;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author aranx
 */
public class PropiedadesLimitacionPropertyEditorSupport extends PropertyEditorSupport{
    
    private PropiedadesLimitacionPanel propiedadesLimitacionPanel=new PropiedadesLimitacionPanel();

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    @Override
    public Component getCustomEditor() {
        return propiedadesLimitacionPanel;
    }

    @Override
    public String getJavaInitializationString() {
        return "new textFieldLimitado.PropiedadesLimitacion("
                +propiedadesLimitacionPanel.getPropiedadesLimitacion().getLimiteCaracteres()
                +",new java.awt.Color("+propiedadesLimitacionPanel.getPropiedadesLimitacion().getColorNormal().getRGB()
                +"),new java.awt.Color("+propiedadesLimitacionPanel.getPropiedadesLimitacion().getColorAlerta().getRGB()
                +"))";
    }

    @Override
    public Object getValue() {
        return propiedadesLimitacionPanel.getPropiedadesLimitacion();
    }
    
    
    
}
