/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textFieldPersonalizado;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;
import javax.swing.JTextField;

/**
 *
 * @author aranx
 */
public class TextFieldPersonalizado extends JTextField {

    // ATRIBUTOS
    private PropiedadesTextField propiedadesTextField;

    // MÉTODOS
    // Getters + Setters
    public PropiedadesTextField getPropiedadesTextField() {
        return propiedadesTextField;
    }

    public void setPropiedadesTextField(PropiedadesTextField propiedadesTextField) {
        this.propiedadesTextField = propiedadesTextField;
    }

    // Constructor SIN PARÁMETROS
    public TextFieldPersonalizado() {
        // Implementamos un listener que gestionaráque se cumpla el patrón
        // Usamos un Adaptador porque no queremos implementar todos los métodos del KeyListener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                validarPatron(ke, propiedadesTextField.getCodigoPatronElegido());
            }
        });
    }

    // MÉTODO PARA COMPROBAR EN TIEMPO REAL PARA CADA PATRÓN CONCRETO
    // En función del tipo de dato que hayamos dicho que queramos introducir en nuEstro TextField
    public void validarPatron(KeyEvent ke, int codigoPatron) {
        switch (codigoPatron) {
            case 0:
                validarDNI(ke);
                break;
            case 1:
                validarTelefono(ke);
                break;
            case 2:
                validarEmail(ke);
                break;
            case 3:
                validarNumerico(ke);
                break;
            case 4:
                validarAlfabetico(ke);
                break;
            case 5:
                validarAlfanumerico(ke);
                break;
        }
    }

    public void validarDNI(KeyEvent ke) {
        setForeground(propiedadesTextField.getColorNormal());
        String patron;
        char caracterPulsado = ke.getKeyChar();
        if (getText().length() < 8) {
            // Si no es ni dígito ni la tecla de borrar
            patron = "[0-9]{1}";
            if (!Pattern.matches(patron, String.valueOf(caracterPulsado)) && caracterPulsado != KeyEvent.VK_BACK_SPACE) {
                setForeground(propiedadesTextField.getColorAlerta());
                ke.consume();
            }
        } else if (getText().length() == 8) {
            // Si no es ni letra ni la tecla de borrar
            patron = "[A-Za-z]{1}";
            if (!Pattern.matches(patron, String.valueOf(caracterPulsado)) && caracterPulsado != KeyEvent.VK_BACK_SPACE) {
                setForeground(propiedadesTextField.getColorAlerta());
                ke.consume();
            }
        } else {
            // Nunca debe exceder de 9 caracteres
            setForeground(propiedadesTextField.getColorAlerta());
            ke.consume();
        }
    }

    // Para tenéfonos que no empiecen por +34.....
    public void validarTelefono(KeyEvent ke) {
        setForeground(propiedadesTextField.getColorNormal());
        String patron;
        char caracterPulsado = ke.getKeyChar();
        if (getText().length() <= 8) { // Devuelve la longitud sin contar la tecla recién presionada!!
            // Si no es ni dígito ni la tecla de borrar
            patron = "[0-9]{1}";
            if (!Pattern.matches(patron, String.valueOf(caracterPulsado)) && caracterPulsado != KeyEvent.VK_BACK_SPACE) {
                setForeground(propiedadesTextField.getColorAlerta());
                ke.consume();
            }
        } else {
            // Nunca debe exceder de 9 caracteres
            setForeground(propiedadesTextField.getColorAlerta());
            ke.consume();
        }
    }

    // Suponemos que sólo acepta [A-Za-z0-9]+[@]{1}[A-Za-z]+[.]{1}[A-Za-z]+ alfanumérico@alfabético.alfabético
    public void validarEmail(KeyEvent ke) {
        setForeground(propiedadesTextField.getColorNormal());
        String patron;
        char caracterPulsado = ke.getKeyChar();
        if (!getText().contains("@")) {
            // antes de haber puesto la @
            patron = "[A-Za-z0-9]+[@]?";
            if (!Pattern.matches(patron, getText() + caracterPulsado) && getText().length() > 0) {
                setForeground(propiedadesTextField.getColorAlerta());
                ke.consume();
            }
        } else if (getText().contains("@") && !getText().contains(".")) {
            // después de haber puesto la @ y antes del .
            patron = "[A-Za-z0-9]+[@]{1}[A-Za-z]+[.]?";
            if (!Pattern.matches(patron, getText()+caracterPulsado)) {
                setForeground(propiedadesTextField.getColorAlerta());
                ke.consume();
            }
        } else if (getText().contains("@") && getText().contains(".")) {
            // después de haber puesto la @ y después del .
            patron = "[A-Za-z0-9]+[@]{1}[A-Za-z]+[.]{1}[A-Za-z]+";
            if (!Pattern.matches(patron, getText()+caracterPulsado)) {
                setForeground(propiedadesTextField.getColorAlerta());
                ke.consume();
            }
        } else {
            setForeground(propiedadesTextField.getColorAlerta());
            ke.consume();
        }
    }

    public void validarNumerico(KeyEvent ke) {
        setForeground(propiedadesTextField.getColorNormal());
        String patron = "[0-9]";
        char caracterPulsado = ke.getKeyChar();
        // Si no es ni dígito ni la tecla de borrar
        if (!Pattern.matches(patron, String.valueOf(caracterPulsado)) && caracterPulsado != KeyEvent.VK_BACK_SPACE) {
            setForeground(propiedadesTextField.getColorAlerta());
            ke.consume();
        }
    }

    public void validarAlfabetico(KeyEvent ke) {
        setForeground(propiedadesTextField.getColorNormal());
        String patron = "[A-Za-z]";
        char caracterPulsado = ke.getKeyChar();
        // Si no es ni dígito ni la tecla de borrar
        if (!Pattern.matches(patron, String.valueOf(caracterPulsado)) && caracterPulsado != KeyEvent.VK_BACK_SPACE) {
            setForeground(propiedadesTextField.getColorAlerta());
            ke.consume();
        }
    }

    public void validarAlfanumerico(KeyEvent ke) {
        setForeground(propiedadesTextField.getColorNormal());
        String patron = "[A-Za-z0-9]";
        char caracterPulsado = ke.getKeyChar();
        // Si no es ni dígito ni la tecla de borrar
        if (!Pattern.matches(patron, String.valueOf(caracterPulsado)) && caracterPulsado != KeyEvent.VK_BACK_SPACE) {
            setForeground(propiedadesTextField.getColorAlerta());
            ke.consume();
        }
    }

}
