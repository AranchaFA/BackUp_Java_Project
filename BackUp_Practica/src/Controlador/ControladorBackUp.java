package Controlador;

import Modelo.DTOs.BackUp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aranx
 */
public class ControladorBackUp {

    // ATRIBUTOS
    private List<BackUp> listaBackUps;

    // MÉTODOS
    // Getters + Setters
    public List<BackUp> getListaBackUps() {
        return listaBackUps;
    }

    public void setListaBackUps(List<BackUp> listaBackUps) {
        this.listaBackUps = listaBackUps;
    }

    /**
     * Carga en el nuevo objeto ControladorBackUp la lista de BackUps realizados desde fichero,
     * si no existiera crea un nuevo fichero e inicializa la lista vacía
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ControladorBackUp() throws FileNotFoundException, IOException, ClassNotFoundException {
        // Al abrir el programa, se carga la lista de listaBackUps guardada
        // COMPROBAMOS QUE SIGAN EXISTIENDO TODOS LOS DIRECTORIOS DE LOS BACKUP, SI SE HUBIERA BORRADO ALGUNO LO ELIMINAMOS DE LA LISTA
        File backupsGuardados = new File("backups.dat"); // Fichero donde se habrá grabado el map de listaBackUps antes de cerrar la aplicación
        // Si ya existe el fichero, es decir ya hay backups registrados, los cargamos
        if (backupsGuardados.exists()) {
            FileInputStream fis = new FileInputStream(backupsGuardados);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.listaBackUps = (List<BackUp>) ois.readObject();
            ois.close();
            fis.close();
        } else {
            // Si no existe el fichero, no hay backups registrados aún, inicializamos el map
            FileOutputStream fos = new FileOutputStream(backupsGuardados, false); // Grabamos sobreescribiendo, no añadiendo
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            this.listaBackUps = new ArrayList<BackUp>();
            oos.writeObject(this.listaBackUps); // No haría realmente falta...¿?
            oos.close();
            fos.close();
        }

        refrescarListaBackups();
    }


    /**
     * Graba en el fichero de registro de BackUps del estado actual de la lista de BackUps
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean actualizarRegistroBackups() throws FileNotFoundException, IOException {
        // Al cerrar el programa, se graba el map de listaBackUps 
        File backupsGuardados = new File("backups.dat");
        FileOutputStream fos = new FileOutputStream(backupsGuardados, false); // Grabamos sobreescribiendo, no añadiendo
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this.listaBackUps);
        oos.close();
        fos.close();
        return true;
    }

    /**
     * Comprueba la existencia, en el dispositivo de almacenamiento de los BackUps, de los
     * directorios correspondientes a dichos BackUps. De haber sido borrados los elimina de
     * la lista de registro de BackUps y actualiza el fichero de registro
     */
    public void refrescarListaBackups() throws IOException {
        // Comprobamos que existen todos los backups que se habían registrado
        if (listaBackUps != null && !listaBackUps.isEmpty()) {
            List<BackUp> noExistentes = new ArrayList<>();
            for (BackUp backUp : listaBackUps) {
                File directorioPadreBackup = backUp.getDirectorioPadreCreado();
                if (!directorioPadreBackup.exists()) {
                    noExistentes.add(backUp);
                }
            }
            // Necesito una lista auxiliar, si intento listaBackups.remove() dentro del bucle peta DA CONCURRENT MODIFICATION EXCEPTION
            listaBackUps.removeAll(noExistentes);
        }
        actualizarRegistroBackups();
    }
}
