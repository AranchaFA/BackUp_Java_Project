/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customBackground;

import java.awt.Component;
import java.beans.PropertyEditorSupport;
import java.io.File;

/**
 *
 * @author aranx
 */
public class CustomBackgroundPropertyEditorSupport extends PropertyEditorSupport{
    
    private CustomBackgroundPanel parametrosPanel=new CustomBackgroundPanel();

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    @Override
    public Component getCustomEditor() {
        return parametrosPanel;
    }

    @Override
    public String getJavaInitializationString() {
        if (parametrosPanel.getParametros().getColorFondo()!=null) { 
            return "new customBackground.CustomBackgroundParameters(new java.awt.Color("+parametrosPanel.getParametros().getColorFondo().getRGB()+"))";
        } else {            
            return "new customBackground.CustomBackgroundParameters(new java.io.File(\"images"+File.separator+"fondo.jpg\"))";
        } 
    }

    @Override
    public Object getValue() {
        return parametrosPanel.getParametros();
    }
    

    
}
