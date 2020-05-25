/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fondoPersonalizado;

import java.awt.Component;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author aranx
 */
public class FondoPersonalizadoPropertyEditorSupport extends PropertyEditorSupport {

    private FondoPersonalizadoPanel fondoPersonalizadoPanel = new FondoPersonalizadoPanel();

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    @Override
    public Component getCustomEditor() {
        return fondoPersonalizadoPanel;
    }

    @Override
    public String getJavaInitializationString() {
        if (fondoPersonalizadoPanel.getPropiedadesFondo().getColorFondo() != null) {
            return "new fondoPersonalizado.PropiedadesFondo(new java.awt.Color("
                    + fondoPersonalizadoPanel.getPropiedadesFondo().getColorFondo().getRGB() + "))";
        }
        if (fondoPersonalizadoPanel.getPropiedadesFondo().getFicheroImagen() != null) {
            return "new fondoPersonalizado.PropiedadesFondo(new java.io.File(\""
                    + fondoPersonalizadoPanel.getPropiedadesFondo().getFicheroImagen().getAbsolutePath().replace("\\", "\\\\") + "\"))";
        }
        return "error";
    }

    @Override
    public Object getValue() {
        return fondoPersonalizadoPanel.getPropiedadesFondo();
    }

}
