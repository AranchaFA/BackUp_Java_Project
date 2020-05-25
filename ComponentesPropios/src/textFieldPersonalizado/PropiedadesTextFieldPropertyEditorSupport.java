/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textFieldPersonalizado;

import java.awt.Component;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author aranx
 */
public class PropiedadesTextFieldPropertyEditorSupport extends PropertyEditorSupport{

    private PropiedadesTextFieldPanel propiedadesTextFieldPanel=new PropiedadesTextFieldPanel();
    
    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    @Override
    public Component getCustomEditor() {
        return propiedadesTextFieldPanel;
    }

    @Override
    public String getJavaInitializationString() {
        return "new textFieldPersonalizado.PropiedadesTextField("
                +propiedadesTextFieldPanel.getPropiedadesTextField().getCodigoPatronElegido()+","
                +"new java.awt.Color("+propiedadesTextFieldPanel.getPropiedadesTextField().getColorNormal().getRGB()+"),"
                +"new java.awt.Color("+propiedadesTextFieldPanel.getPropiedadesTextField().getColorAlerta().getRGB()+"))"; 
    }

    @Override
    public Object getValue() {
        return propiedadesTextFieldPanel.getPropiedadesTextField(); 
    }
    
    
    
}
