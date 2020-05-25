/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Modelo.OperacionesFicheros;
import static Modelo.OperacionesFicheros.listarFicherosRecursivoFiltrado;
import Modelo.DTOs.FicheroCopia;
import com.seaglass.SeaGlassLookAndFeel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author aranx
 */
public class PantallaProgreso extends javax.swing.JDialog {

    private PantallaPrincipal parent;
    private boolean eliminarOriginales;
    private boolean cancelado;
    private List<FicheroCopia> ficherosCopiados;

    public void setEliminarOriginales(boolean eliminarOriginales) {
        this.eliminarOriginales = eliminarOriginales;
    }

    public Color backgroundColor() { // Para que las pantallas hijas saquen el color de fondo
        return jPanel.getBackground();
    }

    /**
     * Creates new form Progreso
     */
    public PantallaProgreso(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.parent = (PantallaPrincipal) parent;
        this.cancelado = false;
        this.ficherosCopiados = new ArrayList<>();

        //cambiarLookAndFeel(); // CASCA JAVAHELP AL APLICAR EL LOOK AND FEEL
        initComponents();

        setTitle("Progreso");
        // Colocamos la pantalla en el centro del monitor
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) ((screenSize.getWidth() - this.getWidth()) / 2), (int) ((screenSize.getHeight() - this.getHeight()) / 2));

        setResizable(false);

        // REALIZAMOS EL BACKUP
        hacerBackup();
    }

    public void hacerBackup() {

        try {
            // Checkeamos los parámetros elegidos
            parent.checkearValoresBackup();
            // Generamos el filtro para copiar
            FileFilter filtro = parent.generarFiltroExtensiones(parent.generarListaExtensiones());

            // El directorio destino será ruta_destino/nombre_directorio_origen_FECHA_Y_HORA para que no se pisen dos backups del mismo directorio  
            File directorioOrigen = parent.getBackup().getConfiguracion().getDirectorioOrigen();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String fechaFormateada = dateFormat.format(parent.getBackup().getFecha());
            String rutaDestino = parent.getBackup().getConfiguracion().getDirectorioDestino().getCanonicalPath() + File.separator + directorioOrigen.getName() + fechaFormateada;
            File directorioDestino = new File(rutaDestino);
            directorioDestino.mkdir(); // HAY QUE CREAR LA CARPETA PADRE QUE LOS CONTENDRÁ A TODOS
            parent.getBackup().setDirectorioPadreCreado(directorioDestino);

            // Hacemos el copiado en otro hilo
            Thread hiloCopiado = new Thread() {
                @Override
                public void run() {
                    try {
                        copiarFicheros(directorioOrigen, directorioDestino, filtro);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaProgreso.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            hiloCopiado.start();

            // AGRUPAMOS POR CATEGORÍA SI PROCEDE
            Thread hiloAgrupar = new Thread() {
                @Override
                public void run() {
                    try {
                        // Si todavía está el copiado en proceso, lo retrasamos para que no casque
                        while (hiloCopiado.isAlive()) {
                            sleep(500);
                        }
                        List<File> ficherosMovidos = parent.agruparPorCategorias(directorioDestino);
                        // Al mover los ficheros de ubicación, deberemos actualizar la lista de ficheros copiados
                        // pues cambiamos la ruta en que se encuentran y quedan registrados con la ruta original de copiado,
                        // para restaurar los archivos deben tener la ubicación real
                        for (File ficheroMovido : ficherosMovidos) {
                            // Para buscarlo en la colección debe ser de nuestra clase FicheroCopia, que implementa el @equals sobreescrito
                            FicheroCopia ficheroMovidoParaComparar = new FicheroCopia(ficheroMovido.getCanonicalPath());
                            int posicionFicheroMovido = parent.getBackup().getListaFicherosCopiados().indexOf(ficheroMovidoParaComparar);
                            FicheroCopia ficheroCopiado = parent.getBackup().getListaFicherosCopiados().get(posicionFicheroMovido);
                            // FicheroCopia con la ruta en que se encuentra actualizada, lo sustituimos por el antiguo
                            FicheroCopia ficheroCopiaActualizado = new FicheroCopia(ficheroMovido.getCanonicalPath(), ficheroCopiado.getRutaOrigen());
                            parent.getBackup().getListaFicherosCopiados().add(ficheroCopiaActualizado);
                            parent.getBackup().getListaFicherosCopiados().remove(ficheroCopiado);
                        }
                        // Borramos los directorios que hayan podido quedar vacíos
                        OperacionesFicheros.eliminarDirectoriosVacios(directorioDestino);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaProgreso.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PantallaProgreso.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            if (parent.getjRadioButton_siCategorias().isSelected()) {
                hiloAgrupar.start();
            }

            // TRAS HACER EL BACKUP, LO REGISTRAMOS EN EL CONTROLADOR Y ACTUALIZAMOS EL REGISTRO DE BACKUPS HECHOS PARA QUE NOS LO GUARDE
            Thread hiloregistrarBackup = new Thread() {
                @Override
                public void run() {
                    try {
                        // Si todavía está el copiado o el agrupado en proceso, lo retrasamos para que no casque
                        while (hiloCopiado.isAlive() || hiloAgrupar.isAlive()) {
                            sleep(500);
                        }
                        parent.getControlador().getListaBackUps().add(parent.getBackup());
                        parent.getControlador().actualizarRegistroBackups();

                        JOptionPane.showMessageDialog(parent, "¡Finalizado!");
                        // Habilitamos el botón aceptar
                        jButton_aceptar.setEnabled(true);
                        jButton_cancelar.setEnabled(false);
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaProgreso.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PantallaProgreso.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            hiloregistrarBackup.start();

        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // TENGO QUE PONERLO AQUÍ PARA CONTROLAR LA BARRA DE PROGRESO :(
    public List<FicheroCopia> copiarFicheros(File directorioOrigen, File directorioDestino, FileFilter filtro) throws IOException {
        OperacionesFicheros.reiniciarListaSubficheros();

        // Listamos todos los ficheros a copiar
        List<File> listaFicherosAGrabar = listarFicherosRecursivoFiltrado(directorioOrigen, filtro);

        // Variables para controlar el progreso del proceso
        int ficherosTotales = 0; // No es el size de la colección porque incluye directorios, y no los copiaremos
        for (File fichero : listaFicherosAGrabar) {
            if (!fichero.isDirectory()) {
                ficherosTotales++;
            }
        }
        int ficherosGrabados = 0;
        int ficherosRestantes = ficherosTotales;
        int porcentajeProgreso=0;
        if (ficherosTotales > 0) { // Da exception si el divisor es 0
            porcentajeProgreso = ficherosGrabados / ficherosTotales;
        }
        // Inicializamos los valores del progreso
        inicializarProgreso(ficherosTotales);

        for (File ficheroOriginal : listaFicherosAGrabar) {
            // MIENTRAS NO SE CANCELE EL BACKUP
            if (!this.cancelado) {
                if (!ficheroOriginal.isDirectory()) {
                    copiarYRegistrarUnFichero(directorioOrigen, directorioDestino, ficheroOriginal);
                    if (eliminarOriginales) {
                        ficheroOriginal.delete(); // Se podría mover en vez de copiar
                    }
                    // Actualizamos el progreso del proceso
                    ficherosGrabados++;
                    ficherosRestantes--;
                    // Actualizamos en un hilo 'intermediario' entre GUI y el proceso de copiado, porque este método 
                    // se ejecutará a su vez en otro hilo diferente al de la interfaz gráfica
                    actualizarGUI(ficherosGrabados, ficherosRestantes, ficherosTotales, ficheroOriginal);
                }
            } else {
                // SI SE CANCELA EL BACKUP
                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cancelar el backup?", "", JOptionPane.YES_NO_OPTION);
                // Si se confirma, devolvemos la lista de ficheros copiados hasta el momento y se acaba el proceso de copiado
                if (confirmacion == JOptionPane.YES_OPTION) {
                    jButton_aceptar.setEnabled(true);
                    return parent.getBackup().getListaFicherosCopiados();
                } else {
                    // Si no se confirma, grabamos el fichero que se acababa de leer y ponemos cancelado a false
                    this.cancelado = false;
                    if (!ficheroOriginal.isDirectory()) {
                        copiarYRegistrarUnFichero(directorioOrigen, directorioDestino, ficheroOriginal);
                        // Actualizamos el progreso del proceso
                        ficherosGrabados++;
                        ficherosRestantes--;
                        actualizarGUI(ficherosGrabados, ficherosRestantes, ficherosTotales, ficheroOriginal);
                    }
                }
            }
        }

        if (eliminarOriginales) {
            OperacionesFicheros.eliminarDirectoriosVacios(directorioOrigen);
            if (OperacionesFicheros.tamanhoDirectorio(directorioOrigen) == 0) {
                directorioOrigen.delete();
            }
        }

        return parent.getBackup().getListaFicherosCopiados();
    }

    public void copiarYRegistrarUnFichero(File directorioOrigen, File directorioDestino, File ficheroOriginal) throws IOException {
        // La estructura de ficheros a partir del directorio de origen será ../../directorioOrigen/[../../fichero]
        // La subcadena de la rutaAbsoluta-rutaDirectorioOrigen
        // No restamos -1 a la longitud de la ruta del directorioOrigen porque así nos elimina el separador '/' que quedaría al final
        String rutaAPartirDeDirectorioOrigen = ficheroOriginal.getCanonicalPath().substring(directorioOrigen.getCanonicalPath().length());
        // El fichero copiado estará, en el directorio de destino, en una estructura de ficheros igual a la original
        String rutaFicheroCopia = directorioDestino.getCanonicalPath() + File.separator + rutaAPartirDeDirectorioOrigen;
        FicheroCopia ficheroCopia = new FicheroCopia(rutaFicheroCopia, ficheroOriginal.getCanonicalPath());
        // Si el directorio padre no existiera, lo creamos
        if (!ficheroCopia.getParentFile().exists()) {
            ficheroCopia.getParentFile().mkdirs(); // Creamos los directorios padres si fuera necesario
        }
        // Copiamos el fichero
        Files.copy(Paths.get(ficheroOriginal.getCanonicalPath()), Paths.get(rutaFicheroCopia), StandardCopyOption.REPLACE_EXISTING);
        // Añadimos el objeto a la lista de copiados
        this.parent.getBackup().getListaFicherosCopiados().add(ficheroCopia);
    }

    public void inicializarProgreso(int totales) {
        jProgressBar_progreso.setMaximum(totales);
        jProgressBar_progreso.setValue(0);
        jProgressBar_progreso.setToolTipText("0%");
        // Inicializamos las etiquetas que muestran la cantidad de copiados/restantes
        jL_copiados.setText("0");
        jL_restantes.setText(String.valueOf(totales));
    }

    public String adaptarLengthRuta(String ruta) {
        String rutaAdaptada = ruta;

        if (rutaAdaptada.length() > 70) {
            String inicio = "";
            String fin = "";
            for (int i = 0; i < 2; i++) {
                inicio = inicio + rutaAdaptada.substring(0, rutaAdaptada.indexOf("\\") + 1);
                rutaAdaptada = rutaAdaptada.substring(rutaAdaptada.indexOf("\\") + 1);
            }
            for (int i = 0; i < 2; i++) {
                fin = rutaAdaptada.substring(rutaAdaptada.lastIndexOf("\\")) + fin;
                rutaAdaptada = rutaAdaptada.substring(0, rutaAdaptada.lastIndexOf("\\") - 1);
            }
            rutaAdaptada = inicio + "..." + fin;
        }
        return rutaAdaptada;
    }

    public void actualizarGUI(int grabados, int restantes, int totales, File ficheroCopiandoActual) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    jProgressBar_progreso.setValue(grabados);
                    jProgressBar_progreso.setToolTipText(String.valueOf(grabados / totales) + "%");
                    jProgressBar_progreso.repaint();
                    jL_copiados.setText(String.valueOf(grabados));
                    jL_restantes.setText(String.valueOf(restantes));
                    jL_copiandoActual.setText(adaptarLengthRuta(ficheroCopiandoActual.getCanonicalPath()));
                    actualizarEspacioLibre();
                } catch (IOException ex) {
                    Logger.getLogger(PantallaProgreso.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    private void actualizarEspacioLibre() {
        double espacioLibre = OperacionesFicheros.espacioLibre(parent.getBackup().getConfiguracion().getDirectorioDestino());
        double espacioTotal = OperacionesFicheros.espacioTotal(parent.getBackup().getConfiguracion().getDirectorioDestino());
        DecimalFormat decimalFormat = new DecimalFormat("#,000.00GB");
        String espacioLibreFormateado = decimalFormat.format(espacioLibre);
        String espacioTotalFormateado = decimalFormat.format(espacioTotal);
        jL_espacioDisponible.setText("Tienes " + espacioLibreFormateado + " disponibles de " + espacioTotalFormateado);
        jProgressBar_espacioDisponible.setMaximum((int) espacioTotal);
        jProgressBar_espacioDisponible.setValue((int) espacioLibre);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        jProgressBar_progreso = new javax.swing.JProgressBar();
        jButton_cancelar = new javax.swing.JButton();
        jL_copiados = new javax.swing.JLabel();
        jL_restantes = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton_aceptar = new javax.swing.JButton();
        jL_copiandoActual = new javax.swing.JLabel();
        jL_espacioDisponible = new javax.swing.JLabel();
        jProgressBar_espacioDisponible = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel.setBackground(this.parent.backgroundColor());

        jProgressBar_progreso.setForeground(new java.awt.Color(255, 153, 51));

        jButton_cancelar.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jButton_cancelar.setForeground(new java.awt.Color(255, 102, 0));
        jButton_cancelar.setText("Cancelar");
        jButton_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cancelarActionPerformed(evt);
            }
        });

        jL_copiados.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jL_copiados.setForeground(new java.awt.Color(0, 102, 153));
        jL_copiados.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jL_restantes.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jL_restantes.setForeground(new java.awt.Color(0, 102, 153));
        jL_restantes.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 153));
        jLabel1.setText("Copiados");

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 153));
        jLabel2.setText("Restantes");

        jButton_aceptar.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jButton_aceptar.setForeground(new java.awt.Color(255, 102, 0));
        jButton_aceptar.setText("Aceptar");
        jButton_aceptar.setEnabled(false);
        jButton_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_aceptarActionPerformed(evt);
            }
        });

        jL_copiandoActual.setFont(new java.awt.Font("Calibri", 2, 14)); // NOI18N
        jL_copiandoActual.setForeground(new java.awt.Color(0, 102, 153));

        jL_espacioDisponible.setFont(new java.awt.Font("Calibri", 2, 12)); // NOI18N
        jL_espacioDisponible.setForeground(new java.awt.Color(0, 102, 153));
        jL_espacioDisponible.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jProgressBar_espacioDisponible.setForeground(new java.awt.Color(0, 102, 153));

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar_progreso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jL_copiandoActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addComponent(jL_copiados, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addComponent(jL_restantes, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jProgressBar_espacioDisponible, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jButton_cancelar)
                        .addGap(90, 90, 90)
                        .addComponent(jButton_aceptar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jL_espacioDisponible, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jL_espacioDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar_espacioDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jL_copiados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jL_restantes, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jL_copiandoActual, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar_progreso, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_cancelar)
                    .addComponent(jButton_aceptar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cancelarActionPerformed
        this.cancelado = true;
    }//GEN-LAST:event_jButton_cancelarActionPerformed

    private void jButton_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_aceptarActionPerformed
        dispose();
    }//GEN-LAST:event_jButton_aceptarActionPerformed

    public void cambiarLookAndFeel(){
        try {
            UIManager.setLookAndFeel(SeaGlassLookAndFeel.class.getCanonicalName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PantallaProgreso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(PantallaProgreso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PantallaProgreso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PantallaProgreso.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_aceptar;
    private javax.swing.JButton jButton_cancelar;
    private javax.swing.JLabel jL_copiados;
    private javax.swing.JLabel jL_copiandoActual;
    private javax.swing.JLabel jL_espacioDisponible;
    private javax.swing.JLabel jL_restantes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel;
    private javax.swing.JProgressBar jProgressBar_espacioDisponible;
    private javax.swing.JProgressBar jProgressBar_progreso;
    // End of variables declaration//GEN-END:variables
}
