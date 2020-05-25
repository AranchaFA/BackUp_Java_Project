/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.FileFilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Acepta los ficheros que no son directorio
 * @author aranx
 */
public class FiltroFileNoDirectorio implements FileFilter {

    @Override
    public boolean accept(File file) {
        return !file.isDirectory();
    }

}
