/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Modelo.ExtensionesFicheros;
import Controlador.ControladorBackUp;
import Modelo.FileFilters.FiltroFileExtension;
import Modelo.MyExceptions;
import Modelo.DTOs.BackUp;
import Modelo.OperacionesFicheros;
import com.seaglass.SeaGlassLookAndFeel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import static java.awt.event.KeyEvent.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author aranx
 */
public class PantallaPrincipal extends javax.swing.JFrame {

    private ControladorBackUp controlador;
    private BackUp backup;
    private File rutaRaizPendrive = new File("ubicacion.txt/../.."); // Fichero vacío alojado en el directorio de la aplicación

    // Necesitamos el getter del controlador para acceder a su información desde la pantalla de mostrar backups
    public ControladorBackUp getControlador() {
        return controlador;
    }

    // Necesitamos estos getter para acceder a su información desde la pantalla de progreso
    public BackUp getBackup() {
        return backup;
    }

    public JRadioButton getjRadioButton_siCategorias() {
        return jRadioButton_siCategorias;
    }

    /**
     * Creates new form PantallaPrincipal
     */
    public PantallaPrincipal() {
        // Como doy mi propio color de fondo, no se nota que cambia el look and feel
        // Si damos al MenuItem de "Aspecto" y le damos a cancelar, que quita el color de fondo, se nota
        //cambiarLookAndFeel(); // CASCA JAVAHELP AL APLICAR EL LOOK AND FEEL
        initComponents();
        

        // Configuramos el JavaHelp
        // Carga el fichero de ayuda
        try {
            File fichero = new File("./help/help_set.hs");
            URL hsURL = fichero.toURI().toURL();
            // Crea el HelpSet y el HelpBroker
            HelpSet helpset;
            helpset = new HelpSet(getClass().getClassLoader(), hsURL);
            HelpBroker hb = helpset.createHelpBroker();
            // Pone ayuda a item de menu al pulsarlo.
            hb.enableHelpOnButton(jMenuItem_javaHelp, "inicio", helpset);
            hb.enableHelpKey(getRootPane(),"inicio",helpset);
        } catch (HelpSetException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Colocamos la pantalla en el centro del monitor
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) ((screenSize.getWidth() - this.getWidth()) / 2), (int) ((screenSize.getHeight() - this.getHeight()) / 2));

        // Cambiamos el icono de la aplicación
        Image icono = new ImageIcon("images\\icon2.gif").getImage();
        setIconImage(icono);
        setTitle("USB-BackUp");

        // Instanciamos el atributo backup al elegir directorio, para que cada vez que elijamos una ruta nos inicialice un nuevo backup.
        // Si lo instanciamos sólo en el constructor de la pantalla de inicio habría que cerrar y volver a abrir para hacer un segundo backup!!
        // Aunque dará error al coger la fecha y hora del momento de elegir directorios y no el de dar a hacer backup...pero ahora no me paro a cambiarlo todo :(
        // Asignamos valor a la etiqueta de espacio libre
        actualizarEspacioLibre();

        // Abrimos sesión con el controlador, que tiene toda la información de backups hechos con el pendrive
        try {
            controlador = new ControladorBackUp();
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        backup = new BackUp(); // Si no lo inicializo, da nullPointerException si le das a hacer backup sin seleccionar directorio
        // La pantalla inicial será de tamaño fijo, la de la tabla con los datos no
        this.setResizable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup_duplicados = new javax.swing.ButtonGroup();
        buttonGroup_categorias = new javax.swing.ButtonGroup();
        jPanel = new javax.swing.JPanel();
        jL_titulo = new javax.swing.JLabel();
        jL_seleccionado = new javax.swing.JLabel();
        jB_hacerBackup = new javax.swing.JButton();
        jCheckBox_imagen = new javax.swing.JCheckBox();
        jL_agruparCategorias = new javax.swing.JLabel();
        jCheckBox_video = new javax.swing.JCheckBox();
        jRadioButton_siCategorias = new javax.swing.JRadioButton();
        jCheckBox_textos = new javax.swing.JCheckBox();
        jRadioButton_noCategorias = new javax.swing.JRadioButton();
        jCheckBox_todo = new javax.swing.JCheckBox();
        jL_espacioDisponible = new javax.swing.JLabel();
        jCheckBox_pdf = new javax.swing.JCheckBox();
        jCheckBox_comprimidos = new javax.swing.JCheckBox();
        jB_seleccionarDirectorio = new javax.swing.JButton();
        jCheckBox_audio = new javax.swing.JCheckBox();
        textFieldLimitado_nombreBackup = new textFieldLimitado.TextFieldLimitado();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu_opciones = new javax.swing.JMenu();
        jMenuItem_verBackups = new javax.swing.JMenuItem();
        jMenuItem_aspecto = new javax.swing.JMenuItem();
        jMenuItem_javaHelp = new javax.swing.JMenuItem();
        jMenuItem_cerrar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel.setBackground(new java.awt.Color(175, 210, 228));
        jPanel.setForeground(new java.awt.Color(0, 101, 153));

        jL_titulo.setFont(new java.awt.Font("Calibri", 3, 48)); // NOI18N
        jL_titulo.setForeground(new java.awt.Color(255, 102, 0));
        jL_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jL_titulo.setText("USB-BACKUP");

        jL_seleccionado.setFont(new java.awt.Font("Calibri Light", 2, 14)); // NOI18N
        jL_seleccionado.setForeground(new java.awt.Color(0, 102, 153));
        jL_seleccionado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jL_seleccionado.setText("Ningún directorio seleccionado");

        jB_hacerBackup.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jB_hacerBackup.setForeground(new java.awt.Color(255, 102, 0));
        jB_hacerBackup.setText("Hacer BackUp");
        jB_hacerBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_hacerBackupActionPerformed(evt);
            }
        });

        jCheckBox_imagen.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jCheckBox_imagen.setForeground(new java.awt.Color(0, 102, 153));
        jCheckBox_imagen.setText("Imagen");
        jCheckBox_imagen.setToolTipText(".jpg .png .gif ...");
        jCheckBox_imagen.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox_imagen.setName(""); // NOI18N
        jCheckBox_imagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_imagenActionPerformed(evt);
            }
        });

        jL_agruparCategorias.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jL_agruparCategorias.setForeground(new java.awt.Color(0, 102, 153));
        jL_agruparCategorias.setText("¿Agrupar por categorías?");

        jCheckBox_video.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jCheckBox_video.setForeground(new java.awt.Color(0, 102, 153));
        jCheckBox_video.setText("Video");
        jCheckBox_video.setToolTipText(".mkv .wav ...");
        jCheckBox_video.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_videoActionPerformed(evt);
            }
        });

        buttonGroup_categorias.add(jRadioButton_siCategorias);
        jRadioButton_siCategorias.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jRadioButton_siCategorias.setForeground(new java.awt.Color(0, 102, 153));
        jRadioButton_siCategorias.setText("Sí");
        jRadioButton_siCategorias.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton_siCategoriasItemStateChanged(evt);
            }
        });

        jCheckBox_textos.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jCheckBox_textos.setForeground(new java.awt.Color(0, 102, 153));
        jCheckBox_textos.setText("Documentos de texto");
        jCheckBox_textos.setToolTipText(".txt .doc .docx .odt ...");
        jCheckBox_textos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_textosActionPerformed(evt);
            }
        });

        buttonGroup_categorias.add(jRadioButton_noCategorias);
        jRadioButton_noCategorias.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jRadioButton_noCategorias.setForeground(new java.awt.Color(0, 102, 153));
        jRadioButton_noCategorias.setSelected(true);
        jRadioButton_noCategorias.setText("No");

        jCheckBox_todo.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jCheckBox_todo.setForeground(new java.awt.Color(0, 102, 153));
        jCheckBox_todo.setText("Todos");
        jCheckBox_todo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_todoActionPerformed(evt);
            }
        });

        jL_espacioDisponible.setFont(new java.awt.Font("Calibri", 2, 12)); // NOI18N
        jL_espacioDisponible.setForeground(new java.awt.Color(0, 102, 153));
        jL_espacioDisponible.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jCheckBox_pdf.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jCheckBox_pdf.setForeground(new java.awt.Color(0, 102, 153));
        jCheckBox_pdf.setText("PDF");
        jCheckBox_pdf.setToolTipText(".pdf");
        jCheckBox_pdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_pdfActionPerformed(evt);
            }
        });

        jCheckBox_comprimidos.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jCheckBox_comprimidos.setForeground(new java.awt.Color(0, 102, 153));
        jCheckBox_comprimidos.setText("Comprimidos");
        jCheckBox_comprimidos.setToolTipText(".rar .zip .7z ...");
        jCheckBox_comprimidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_comprimidosActionPerformed(evt);
            }
        });

        jB_seleccionarDirectorio.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jB_seleccionarDirectorio.setForeground(new java.awt.Color(255, 102, 0));
        jB_seleccionarDirectorio.setText("Seleccionar directorio");
        jB_seleccionarDirectorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_seleccionarDirectorioActionPerformed(evt);
            }
        });

        jCheckBox_audio.setFont(new java.awt.Font("Calibri", 1, 16)); // NOI18N
        jCheckBox_audio.setForeground(new java.awt.Color(0, 102, 153));
        jCheckBox_audio.setText("Audio");
        jCheckBox_audio.setToolTipText(".mp3 .mp4 .m4a ...");
        jCheckBox_audio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_audioActionPerformed(evt);
            }
        });

        textFieldLimitado_nombreBackup.setText("Nombre / Descripción");
        textFieldLimitado_nombreBackup.setForeground(new java.awt.Color(204, 204, 204));
        textFieldLimitado_nombreBackup.setPropiedadesLimitacion(new textFieldLimitado.PropiedadesLimitacion(100,new java.awt.Color(-16777216),new java.awt.Color(-16777216)));
        textFieldLimitado_nombreBackup.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textFieldLimitado_nombreBackupFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textFieldLimitado_nombreBackupFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(jL_espacioDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanelLayout.createSequentialGroup()
                                    .addGap(5, 5, 5)
                                    .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jL_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jL_seleccionado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                                    .addGap(134, 134, 134)
                                    .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                                            .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jCheckBox_todo)
                                                .addComponent(jCheckBox_comprimidos)
                                                .addComponent(jCheckBox_video))
                                            .addGap(26, 26, 26)
                                            .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jCheckBox_audio)
                                                .addComponent(jCheckBox_textos)))
                                        .addComponent(jB_hacerBackup, javax.swing.GroupLayout.Alignment.TRAILING))))
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(jL_agruparCategorias)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton_siCategorias)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButton_noCategorias)))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox_imagen)
                            .addComponent(jCheckBox_pdf))
                        .addGap(379, 379, 379))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_seleccionarDirectorio, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldLimitado_nombreBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24))))
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jL_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jL_espacioDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addComponent(jCheckBox_audio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox_textos))
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addComponent(jCheckBox_imagen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox_pdf))
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addComponent(jCheckBox_video)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox_comprimidos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox_todo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jL_agruparCategorias)
                    .addComponent(jRadioButton_siCategorias)
                    .addComponent(jRadioButton_noCategorias))
                .addGap(22, 22, 22)
                .addComponent(textFieldLimitado_nombreBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jL_seleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_hacerBackup)
                    .addComponent(jB_seleccionarDirectorio))
                .addGap(27, 27, 27))
        );

        jMenu_opciones.setText("Opciones");

        jMenuItem_verBackups.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem_verBackups.setText("Ver BackUps...");
        jMenuItem_verBackups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_verBackupsActionPerformed(evt);
            }
        });
        jMenu_opciones.add(jMenuItem_verBackups);

        jMenuItem_aspecto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem_aspecto.setText("Aspecto...");
        jMenuItem_aspecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_aspectoActionPerformed(evt);
            }
        });
        jMenu_opciones.add(jMenuItem_aspecto);

        jMenuItem_javaHelp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem_javaHelp.setText("JavaHelp");
        jMenu_opciones.add(jMenuItem_javaHelp);

        jMenuItem_cerrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem_cerrar.setText("Cerrar");
        jMenuItem_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_cerrarActionPerformed(evt);
            }
        });
        jMenu_opciones.add(jMenuItem_cerrar);

        jMenuBar1.add(jMenu_opciones);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem_verBackupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_verBackupsActionPerformed
        MostrarBackups pantallaBackups = new MostrarBackups(this, true);
        pantallaBackups.setVisible(true);
    }//GEN-LAST:event_jMenuItem_verBackupsActionPerformed

    private void jCheckBox_audioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_audioActionPerformed
        if (jCheckBox_todo.isSelected()) {
            jCheckBox_audio.setSelected(true);
        }
    }//GEN-LAST:event_jCheckBox_audioActionPerformed

    private void jB_seleccionarDirectorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_seleccionarDirectorioActionPerformed
        // Instanciamos el backup aquí, para que cada vez que elijamos una ruta nos inicialice un nuevo backup
        // si lo instanciamos en el constructor de la pantalla de inicio habría que cerrar y volver a abrir para
        // hacer un segundo backup!!
        backup = new BackUp();
        // Creamos un elemento para elegir archivos, se abrirá como una ventana
        JFileChooser jfileChooser = new JFileChooser();
        // Le asignamos un modo de selección de los ficheros que te va a ofrecer para elegir: sólo directorios
        jfileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // Va a devolver un resultado int en función de si pulsamos aceptar o cancelar
        int resultado = jfileChooser.showSaveDialog(this);
        // Según si aceptamos o cancelamos:
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File directorioElegido = jfileChooser.getSelectedFile();
            // Si el directorio elegido está vacío, salta exception
            try {
                if (directorioElegido.list().length == 0) {
                    // throw new MyExceptions.DirectorioVacio(this); // Me muestra el JOptionPane sin ponerlo en el catch
                    throw new MyExceptions.DirectorioVacio();
                } else {
                    // Si se ha seleccionado algún directorio, y no está vacío, se asigna a la configuración del backup
                    backup.getConfiguracion().setDirectorioOrigen(directorioElegido);
                    // Lo mostramos, adaptando el tamaño
                    jL_seleccionado.setText(adaptarLengthRuta(directorioElegido.getAbsolutePath()));
                }
            } catch (MyExceptions.DirectorioVacio ex) {
                JOptionPane.showMessageDialog(this, "El directorio elegido no tiene contenido,\npor favor seleccione otro.", "ERROR", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (resultado == JFileChooser.CANCEL_OPTION) {
            // Eliminamos un posible directorio que haya quedado registrado si se ha dado a aceptar antes y luego
            // se ha vuelto a entrar a seleccionar pero se ha dado a cancelar, dejaría guardada la primera elección
            backup.getConfiguracion().setDirectorioOrigen(null);
            jL_seleccionado.setText("Ningún directorio seleccionado");
        }
    }//GEN-LAST:event_jB_seleccionarDirectorioActionPerformed

    private void jCheckBox_comprimidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_comprimidosActionPerformed
        if (jCheckBox_todo.isSelected()) {
            jCheckBox_todo.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBox_comprimidosActionPerformed

    private void jCheckBox_pdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_pdfActionPerformed
        if (jCheckBox_todo.isSelected()) {
            jCheckBox_todo.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBox_pdfActionPerformed

    private void jCheckBox_todoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_todoActionPerformed
        if (jCheckBox_todo.isSelected()) {
            jCheckBox_audio.setSelected(true);
            jCheckBox_comprimidos.setSelected(true);
            jCheckBox_imagen.setSelected(true);
            jCheckBox_pdf.setSelected(true);
            jCheckBox_textos.setSelected(true);
            jCheckBox_video.setSelected(true);
        }
    }//GEN-LAST:event_jCheckBox_todoActionPerformed

    private void jCheckBox_textosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_textosActionPerformed
        if (jCheckBox_todo.isSelected()) {
            jCheckBox_todo.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBox_textosActionPerformed

    private void jCheckBox_videoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_videoActionPerformed
        if (jCheckBox_todo.isSelected()) {
            jCheckBox_todo.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBox_videoActionPerformed

    private void jCheckBox_imagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_imagenActionPerformed
        if (jCheckBox_todo.isSelected()) {
            jCheckBox_todo.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBox_imagenActionPerformed

    private void jB_hacerBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_hacerBackupActionPerformed
        // Si no se ha seleccionado directorio para copiar se muestra un mensaje de error
        if (backup.getConfiguracion().getDirectorioOrigen() == null) {
            JOptionPane.showMessageDialog(this, "No hay ningún directorio seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (generarListaExtensiones() == null) {
                JOptionPane.showMessageDialog(this, "Selecciona al menos un tipo de archivo", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    // Si no queda suficiente espacio en disco, lanzamos una exception advirtiéndolo
                    if (OperacionesFicheros.tamanhoDirectorio(backup.getConfiguracion().getDirectorioOrigen()) > OperacionesFicheros.espacioLibre(rutaRaizPendrive)) {
                        throw new MyExceptions.EspacioInsuficiente();
                    }

                    // Preguntamos si quiere conservar o eliminar los ficheros originales
                    int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar los ficheros originales?", "", JOptionPane.YES_NO_CANCEL_OPTION);

                    if (confirmacion != JOptionPane.CANCEL_OPTION) {
                        // RELIZAMOS EL BACKUP EN LA PANTALLA DE PROGRESO
                        PantallaProgreso pantallaProgreso = new PantallaProgreso(this, true); // La hacemos modal
                        boolean eliminarOriginales = confirmacion == JOptionPane.YES_OPTION ? true : false;
                        pantallaProgreso.setEliminarOriginales(eliminarOriginales);
                        pantallaProgreso.setVisible(true);

                        // Reiniciamos los campos de configuración
                        restaurarSeleccionados();
                        // Reiniciamos también el objeto backup
                        backup = new BackUp();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MyExceptions.EspacioInsuficiente ex) {
                    JOptionPane.showMessageDialog(this, "No queda suficiente espacio en disco.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jB_hacerBackupActionPerformed

    private void jRadioButton_siCategoriasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton_siCategoriasItemStateChanged
        if (jRadioButton_siCategorias.isSelected()) {
            JOptionPane.showMessageDialog(this, "Esta opción eliminará los ficheros duplicados.");
        }
    }//GEN-LAST:event_jRadioButton_siCategoriasItemStateChanged

    private void jMenuItem_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_cerrarActionPerformed
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea abandonar la aplicación?", "", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_jMenuItem_cerrarActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // Ctrl+E -> Salir
        if (evt.getKeyCode() == VK_E) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea abandonar la aplicación?", "", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
        // Ctrl+B -> Ver BackUps
        if (evt.getKeyCode() == VK_B) {
            MostrarBackups pantallaBackups = new MostrarBackups(this, true);
            pantallaBackups.setVisible(true);
        }
        // Ctrl+L -> Opciones de aspecto
        if (evt.getKeyCode() == VK_L) {
            Color colorElegido = JColorChooser.showDialog(this, "COLOR DE FONDO", this.backgroundColor());
            jPanel.setBackground(colorElegido);
        }
        // Ctrl+H -> JavaHelp
        if (evt.getKeyCode() == VK_H) {

        }
    }//GEN-LAST:event_formKeyPressed

    private void jMenuItem_aspectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_aspectoActionPerformed
        Color colorElegido = JColorChooser.showDialog(this, "COLOR DE FONDO", this.backgroundColor());
        jPanel.setBackground(colorElegido);
    }//GEN-LAST:event_jMenuItem_aspectoActionPerformed

    private void textFieldLimitado_nombreBackupFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldLimitado_nombreBackupFocusGained
        if (textFieldLimitado_nombreBackup.getText().equalsIgnoreCase("Nombre / Descripción")) {
            textFieldLimitado_nombreBackup.setText("");
            textFieldLimitado_nombreBackup.setForeground(Color.black);
        }
    }//GEN-LAST:event_textFieldLimitado_nombreBackupFocusGained

    private void textFieldLimitado_nombreBackupFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldLimitado_nombreBackupFocusLost
        if (textFieldLimitado_nombreBackup.getText().equalsIgnoreCase("")) {
            textFieldLimitado_nombreBackup.setText("Nombre / Descripción");
            textFieldLimitado_nombreBackup.setForeground(Color.LIGHT_GRAY);
        }
    }//GEN-LAST:event_textFieldLimitado_nombreBackupFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaPrincipal().setVisible(true);
            }
        });
    }

    public List<String> generarListaExtensiones() {
        // Si devuelve null al clickar hacer backup -> avisamos al usuario de que elija alguna opción
        List<String> listaExtensionesSeleccionadas = null;
        // Si está seleccionado todos
        if (jCheckBox_todo.isSelected()) {
            listaExtensionesSeleccionadas = new ArrayList<>();
            // Si hay que copiarlo todo, se deja la lista vacía, luego si
            // la lista está vacía se hace un filtro null para que copie todo
        } else {
            // Si no está seleccionado todos, comprobamos uno por uno y añadimos las listas al filtro
            if (jCheckBox_audio.isSelected()) {
                listaExtensionesSeleccionadas = new ArrayList<>();
                listaExtensionesSeleccionadas.addAll(ExtensionesFicheros.extensionesAudio());
            }
            if (jCheckBox_comprimidos.isSelected()) {
                listaExtensionesSeleccionadas = new ArrayList<>();
                listaExtensionesSeleccionadas.addAll(ExtensionesFicheros.extensionesComprimidos());
            }
            if (jCheckBox_imagen.isSelected()) {
                listaExtensionesSeleccionadas = new ArrayList<>();
                listaExtensionesSeleccionadas.addAll(ExtensionesFicheros.extensionesImagen());
            }
            if (jCheckBox_pdf.isSelected()) {
                listaExtensionesSeleccionadas = new ArrayList<>();
                listaExtensionesSeleccionadas.addAll(ExtensionesFicheros.extensionesPDF());
            }
            if (jCheckBox_textos.isSelected()) {
                listaExtensionesSeleccionadas = new ArrayList<>();
                listaExtensionesSeleccionadas.addAll(ExtensionesFicheros.extensionesTexto());
            }
            if (jCheckBox_video.isSelected()) {
                listaExtensionesSeleccionadas = new ArrayList<>();
                listaExtensionesSeleccionadas.addAll(ExtensionesFicheros.extensionesVideo());
            }
        }
        return listaExtensionesSeleccionadas;
    }

    public List<File> agruparPorCategorias(File directorio) throws IOException {
        List<File> ficherosMovidos = new ArrayList<>();
        ficherosMovidos.addAll(OperacionesFicheros.agruparPorCategoria(directorio, ExtensionesFicheros.extensionesAudio(), "AUDIO"));
        ficherosMovidos.addAll(OperacionesFicheros.agruparPorCategoria(directorio, ExtensionesFicheros.extensionesComprimidos(), "COMPRIMIDOS"));
        ficherosMovidos.addAll(OperacionesFicheros.agruparPorCategoria(directorio, ExtensionesFicheros.extensionesImagen(), "IMAGEN"));
        ficherosMovidos.addAll(OperacionesFicheros.agruparPorCategoria(directorio, ExtensionesFicheros.extensionesPDF(), "PDF"));
        ficherosMovidos.addAll(OperacionesFicheros.agruparPorCategoria(directorio, ExtensionesFicheros.extensionesTexto(), "TEXTO"));
        ficherosMovidos.addAll(OperacionesFicheros.agruparPorCategoria(directorio, ExtensionesFicheros.extensionesVideo(), "VIDEO"));
        return ficherosMovidos;
    }

    public FileFilter generarFiltroExtensiones(List<String> listaExtensiones) {
        FiltroFileExtension filtro;
        // Si la lista está vacía, el filtro=null para que copie todo
        if (listaExtensiones.size() == 0) {
            filtro = null;
        } else {
            filtro = new FiltroFileExtension(listaExtensiones);
        }
        return filtro;
    }

    public void checkearValoresBackup() {
        // Si no se ha seleccionado directorio para copiar se muestra un mensaje de error
        if (backup.getConfiguracion().getDirectorioOrigen() == null) {
            JOptionPane.showMessageDialog(this, "No hay ningún directorio seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // ALMACENAMOS LOS PARÁMETROS DE CONFIGURACIÓN DEL BACKUP
            // Fecha (la del sistema en el momento de hacer el backup)
            backup.setFecha(GregorianCalendar.getInstance().getTime());
            // Nombre o descripción
            if (!textFieldLimitado_nombreBackup.getText().equalsIgnoreCase("Nombre / Descripción")) {
                backup.setNombre(textFieldLimitado_nombreBackup.getText());
            }
            // Lista de extensiones
            backup.getConfiguracion().getListaExtensiones().addAll(generarListaExtensiones());
        }
    }

    public void restaurarSeleccionados() {
        jCheckBox_audio.setSelected(false);
        jCheckBox_comprimidos.setSelected(false);
        jCheckBox_imagen.setSelected(false);
        jCheckBox_pdf.setSelected(false);
        jCheckBox_textos.setSelected(false);
        jCheckBox_video.setSelected(false);
        jCheckBox_todo.setSelected(false);
        jL_seleccionado.setText("Ningún directorio seleccionado");
        jRadioButton_noCategorias.setSelected(true);
        textFieldLimitado_nombreBackup.setText("Nombre / Descripción");
        textFieldLimitado_nombreBackup.setForeground(Color.lightGray);
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

    public void actualizarEspacioLibre() {
        double espacioLibre = OperacionesFicheros.espacioLibre(rutaRaizPendrive);
        double espacioTotal = OperacionesFicheros.espacioTotal(rutaRaizPendrive);
        DecimalFormat decimalFormat = new DecimalFormat("#,000.00GB");
        String espacioLibreFormateado = decimalFormat.format(espacioLibre);
        String espacioTotalFormateado = decimalFormat.format(espacioTotal);
        jL_espacioDisponible.setText("Tienes " + espacioLibreFormateado + " disponibles de " + espacioTotalFormateado);
    }

    public Color backgroundColor() {
        return jPanel.getBackground();
    }
    
    public void cambiarLookAndFeel(){
        try {
            UIManager.setLookAndFeel(SeaGlassLookAndFeel.class.getCanonicalName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup_categorias;
    private javax.swing.ButtonGroup buttonGroup_duplicados;
    private javax.swing.JButton jB_hacerBackup;
    private javax.swing.JButton jB_seleccionarDirectorio;
    private javax.swing.JCheckBox jCheckBox_audio;
    private javax.swing.JCheckBox jCheckBox_comprimidos;
    private javax.swing.JCheckBox jCheckBox_imagen;
    private javax.swing.JCheckBox jCheckBox_pdf;
    private javax.swing.JCheckBox jCheckBox_textos;
    private javax.swing.JCheckBox jCheckBox_todo;
    private javax.swing.JCheckBox jCheckBox_video;
    private javax.swing.JLabel jL_agruparCategorias;
    private javax.swing.JLabel jL_espacioDisponible;
    private javax.swing.JLabel jL_seleccionado;
    private javax.swing.JLabel jL_titulo;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem_aspecto;
    private javax.swing.JMenuItem jMenuItem_cerrar;
    private javax.swing.JMenuItem jMenuItem_javaHelp;
    private javax.swing.JMenuItem jMenuItem_verBackups;
    private javax.swing.JMenu jMenu_opciones;
    private javax.swing.JPanel jPanel;
    private javax.swing.JRadioButton jRadioButton_noCategorias;
    private javax.swing.JRadioButton jRadioButton_siCategorias;
    private textFieldLimitado.TextFieldLimitado textFieldLimitado_nombreBackup;
    // End of variables declaration//GEN-END:variables
}
