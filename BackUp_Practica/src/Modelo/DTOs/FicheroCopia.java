package Modelo.DTOs;

import java.io.File;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author aranx
 */
public class FicheroCopia extends File implements Serializable {

    // ATRIBUTOS
    private String rutaOrigen;

    // MÉTODOS
    // Getters + Setters
    public String getRutaOrigen() {
        return rutaOrigen;
    }

    public void setRutaOrigen(String rutaOrigen) {
        this.rutaOrigen = rutaOrigen;
    }

    // Constructor
    public FicheroCopia(String rutaDestino, String rutaOrigen) {
        super(rutaDestino);
        this.rutaOrigen = rutaOrigen;
    }

    public FicheroCopia(String ruta) {
        super(ruta);
    }

    // Equals y HashCode para saber si un fichero es duplicado sin necesidad del FileFilter
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.rutaOrigen);
        return hash;
    }

    /**
     * La comparación de nombres NO ES KEYSENSITIVE (en SO Keysensitive como Linux podría dar como iguales ficheros que no lo son).
     * @param obj
     * @return true si el fichero no es directorio y el fichero pasado por parámetro tiene igual nombre y tamaño
     * false en cualquier otro caso.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        final FicheroCopia other = (FicheroCopia) obj;
        //  No debe ser directorio, si es DIRECTORIO DEVUELVE SIEMPRE LENGTH()=0
        // Directorios con igual nombre pero distinto contenido darían true en el equals entonces
        // Los directorios nos los listará como no duplicados, en el método de borrar duplicados como
        // primero borramos todos los ficheros duplicados y luego los directorios vacíos, ya nos lo borraría si
        // su contenido es duplicado de otro porque quedará vacío
        if (!this.isDirectory()&&this.getName().equalsIgnoreCase(other.getName()) && this.length() == other.length()) {
            return true;
        }
        return false;
    }

}
