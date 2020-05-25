/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textFieldLimitado;

import java.awt.Color;
import java.io.Serializable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author aranx
 */
public class TextFieldLimitado extends JTextField implements Serializable { // Ya implementa Serializable

    private PropiedadesLimitacion propiedadesLimitacion;

    public TextFieldLimitado() {
        // Lo creamos "programado" para detectar cuándo se excede el límite de caracteres
        // Sacamos el "documento" (que hereda de JTextField)
        // Le implementamos un Listener que detecte cada vez que se esrcibe un caracter
        // y compruebe la longitud del texto
        getDocument().addDocumentListener(new DocumentListener() { // Se haría más corto con un adaptador :)
            @Override
            public void insertUpdate(DocumentEvent de) {
                if (propiedadesLimitacion != null) {
                    Color colorTexto = getText().length() > propiedadesLimitacion.getLimiteCaracteres()
                    ? propiedadesLimitacion.getColorAlerta() : propiedadesLimitacion.getColorNormal();
                    setForeground(colorTexto);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                if (propiedadesLimitacion != null) {
                    Color colorTexto = getText().length() > propiedadesLimitacion.getLimiteCaracteres()
                    ? propiedadesLimitacion.getColorAlerta() : propiedadesLimitacion.getColorNormal();
                    setForeground(colorTexto);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                // Vacío porque no lo usaremos
            }
        });
    }

    public PropiedadesLimitacion getPropiedadesLimitacion() {
        return propiedadesLimitacion;
    }

    public void setPropiedadesLimitacion(PropiedadesLimitacion propiedadesLimitacion) {
        this.propiedadesLimitacion = propiedadesLimitacion;
    }

}
