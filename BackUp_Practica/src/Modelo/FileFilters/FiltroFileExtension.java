/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.FileFilters;

import Modelo.OperacionesFicheros;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Filtra los archivos cuya extensión se encuentre en la lista de extensiones pasada por parámetro 
 * (con o sin punto ".", p.ej. "txt" o ".txt")
 *
 * @author aranx
 */
public class FiltroFileExtension implements FileFilter {

    private List<String> listaExtensiones;

    public FiltroFileExtension(String extension) {
        listaExtensiones = new ArrayList<String>();
        // Si introducimos la extension con "." delante lo eliminamos, para que no provoque errores al comparar cadenas en el accept
        if (extension.charAt(0) == '.') {
            extension = extension.substring(1);
        }
        listaExtensiones.add(extension.toUpperCase());
    }

    public FiltroFileExtension(List<String> listaExtensiones) {
        this.listaExtensiones = new ArrayList<String>();
        // Si introducimos la extension con "." delante lo eliminamos, para que no provoque errores al comparar cadenas en el accept
        for (String extension : listaExtensiones) {
            if (extension.charAt(0) == '.') {
                extension = extension.substring(1).toUpperCase();
            }
            this.listaExtensiones.add(extension);
        }
    }

    @Override
    public boolean accept(File file) {
        String extensionFile = OperacionesFicheros.getExtension(file); // si es directorio extension=null
        if (extensionFile != null) {
            if (extensionFile.charAt(0) == '.') {
                extensionFile = extensionFile.substring(1).toUpperCase();
            }
            if (listaExtensiones.contains(extensionFile.toUpperCase())) {
                return true;
            }
        }

        return false;
    }

}
