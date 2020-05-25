/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.TableModels;

import Modelo.DTOs.FicheroCopia;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author aranx
 */
public class TableModelFicheros extends AbstractTableModel {

    private List<File> listaFicheros;
    private String[] cabecerasColumna = {"NOMBRE", "RUTA"};

    public TableModelFicheros(List<File> listaFicheros) {
        this.listaFicheros = listaFicheros;
    }

    public File getRow(int row) {
        return listaFicheros.get(row);
    }

    @Override
    public int getRowCount() {
        return listaFicheros.size();
    }

    @Override
    public int getColumnCount() {
        return cabecerasColumna.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return listaFicheros.get(row).getName();
            case 1: {
                return listaFicheros.get(row).getAbsolutePath();
            }
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int col) {
        return cabecerasColumna[col];
    }

    public void refrescar() {
        fireTableDataChanged();
    }

    public int removeRows(int[] rowsOrdenados) {
        // Los ÍNDICES tienen que estar ORDENADOS para que no de error al borrar uno posterior y luego otro anterior
        Arrays.sort(rowsOrdenados);
        int contador = 0;
        for (int row : rowsOrdenados) {
            boolean borrado = removeRow(row - contador);
            if (borrado) {
                contador++;
            }
        }
        return contador;
    }

    // Aquí lo tenía como mi ejercicio de la agenda, lo cambié, si peta lo cambio de nuevo
    public boolean removeRow(int row) {
        if (!listaFicheros.isEmpty() && row < listaFicheros.size()) {
            listaFicheros.remove(row);
            fireTableDataChanged();
            return true;
        }
        return false;
    }

    public boolean addRow(FicheroCopia fichero) {
        return listaFicheros.add(fichero);
    }
}
