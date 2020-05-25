package Modelo;

import Modelo.DTOs.FicheroCopia;
import Modelo.DTOs.BackUp;
import Modelo.FileFilters.FiltroFileDirectorio;
import Modelo.FileFilters.FiltroFileExtension;
import Modelo.FileFilters.FiltroFileNoDirectorio;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author aranx
 */
public class OperacionesFicheros {

    // ATRIBUTOS
    public static List<FicheroCopia> listaCopiados = new ArrayList<>();
    public static List<File> listaSubficheros = new ArrayList<>();

    // MÉTODOS

    /**
     * Copia los archivos contenidos en la ruta de origen (y todos sus subdirectorios)
     * a la ruta de destino. Se puede aplicar un filtro a los archivos que son copiados.
     * @param directorioOrigen directorio cuyo contenido se quiere copiar
     * @param directorioDestino directorio al que se desean copiar los ficheros
     * @param filtro filtro que deben pasar los ficheros copiados
     * @return lista de FicheroCopia, que extiende la clase File y tiene como atributo la ruta del fichero de origen copiado
     * @throws IOException
     */

    public static List<FicheroCopia> copiarFicheros(File directorioOrigen, File directorioDestino, FileFilter filtro) throws IOException {
        reiniciarListaSubficheros();
        List<FicheroCopia> listaFicherosCopiados = new ArrayList<>();

        // Listamos recursivamente todos los ficheros que pasen el filtro
        List<File> listaFicheros = listarFicherosRecursivoFiltrado(directorioOrigen, filtro);

        // Coopiamos a la ruta de destino los ficheros no directorio, si su directorio padre no existe lo creamos
        for (File ficheroOriginal : listaFicheros) {
            if (!ficheroOriginal.isDirectory()) {
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
                listaFicherosCopiados.add(ficheroCopia);
            }
        }

        return listaFicherosCopiados;
    }

    /**
     * Copia los FicheroCopia de un BackUp a su ruta original, sobreescribiendo el
     * fichero que pudieran existir en esa ruta
     * @param backup Objeto Backup con la lista de FicheroCopia a restaurar
     * @return Lista de File restaurados
     * @throws IOException
     */
    public static List<File> restaurarFicherosCopiados(BackUp backup) throws IOException{
        reiniciarListaSubficheros();

        List<File> listaRestaurados = new ArrayList<>();
        // Primero borramos todos los ficheros que existan en el directorio original del que se hizo el backup
        List<File> ficherosActualesDirectorioOriginal = listarFicherosRecursivo(backup.getConfiguracion().getDirectorioOrigen());
        for (File ficheroActual : ficherosActualesDirectorioOriginal) {
            if (ficheroActual.exists()) {
                ficheroActual.delete();
            }
        }
        // Luego borramos todos los directorios, que habrán quedado vacíos
        List<File> subdirectoriosVacios = listarFicherosRecursivoFiltrado(backup.getConfiguracion().getDirectorioOrigen(), new FiltroFileDirectorio());
        for (File subdirectorioVacio : subdirectoriosVacios) {
            if (subdirectorioVacio.exists() && subdirectorioVacio.list().length == 0) {
                subdirectorioVacio.delete();
            }
        }
        // Copiamos los ficheros que no sean directorios, creando sus directorios padres cuando sea necesario
        for (FicheroCopia ficheroCopiado : backup.getListaFicherosCopiados()) {
            if (ficheroCopiado.exists()) {
                if (!ficheroCopiado.isDirectory()) {
                    // Fichero con la ruta original
                    File ficheroARestaurar = new File(ficheroCopiado.getRutaOrigen());
                    // Si el directorio padre no existiera, lo creamos
                    if (!ficheroARestaurar.getParentFile().exists()) {
                        ficheroARestaurar.getParentFile().mkdirs(); // Creamos los directorios padres si fuera necesario
                    }
                    // Copiamos el fichero
                    Files.copy(Paths.get(ficheroCopiado.getCanonicalPath()), Paths.get(ficheroCopiado.getRutaOrigen()), StandardCopyOption.REPLACE_EXISTING);
                    // Añadimos el objeto a la lista de restaurados
                    listaRestaurados.add(ficheroARestaurar);
                }
            }

        }
        return listaRestaurados;
    }
    
    /**
     * Restaura un BackUp en una ruta diferente a la predefinida por defecto (la
     * del dispositivo donde se encuentra la aplicación)
     * @param backup BackUp a restaurar
     * @param rutaDestino Directorio donde se desean copiar los ficheros del BackUp
     * @return Lista con los ficheros que fueron finalmente restaurados
     * @throws MyExceptions.DirectorioYaExiste Si el directorio donde se copiarán los ficheros ya existe
     * @throws IOException
     */
    public static List<File> restaurarEnRutaElegida(BackUp backup,File rutaDestino) throws MyExceptions.DirectorioYaExiste, IOException{
        reiniciarListaSubficheros();

        List<File> listaRestaurados = new ArrayList<>();
        // El directorio donde volcaremos los ficheros restaurados será rutaDestino/nombreDirPadreBackup
        String rutaDirectorioPadreRestaurados=rutaDestino+File.separator+backup.getDirectorioPadreCreado().getName();
        File directorioPadreRestaurados=new File(rutaDirectorioPadreRestaurados);
        if (directorioPadreRestaurados.exists()) {
            throw new MyExceptions.DirectorioYaExiste("El directorio ya existe"); // Si salta, según la respuesta, borramos el reictorio y volvemos a ejecutar
        }
        
        // Copiamos los ficheros que no sean directorios, creando sus directorios padres cuando sea necesario
        for (FicheroCopia ficheroCopiado : backup.getListaFicherosCopiados()) {
            if (ficheroCopiado.exists()) {
                if (!ficheroCopiado.isDirectory()) {
                    // Ruta del fichero desde el directorio padre el backup
                    String rutaDesdeDirPadreBackup=ficheroCopiado.getCanonicalPath().substring(backup.getDirectorioPadreCreado().getCanonicalPath().length());
                    File ficheroARestaurar = new File(rutaDirectorioPadreRestaurados+File.separator+rutaDesdeDirPadreBackup);
                    // Si el directorio padre no existiera, lo creamos
                    if (!ficheroARestaurar.getParentFile().exists()) {
                        ficheroARestaurar.getParentFile().mkdirs(); // Creamos los directorios padres si fuera necesario
                    }
                    // Copiamos el fichero
                    Files.copy(Paths.get(ficheroCopiado.getCanonicalPath()), Paths.get(ficheroARestaurar.getCanonicalPath()), StandardCopyOption.REPLACE_EXISTING);
                    // Añadimos el objeto a la lista de restaurados
                    listaRestaurados.add(ficheroARestaurar);
                }
            }
        }
        return listaRestaurados;
    }

    /**
     * Lista todos los ficheros que se encuentran repetidos en un directorio (y todos sus subdirectorios)
     * @param directorio
     * @return Lista con los File duplicados contenidos en el directorio pasado por parámetro
     * @throws IOException
     */
    public static List<File> listarDuplicados(File directorio) throws IOException {
        reiniciarListaSubficheros();

        // Listamos todos los ficheros hijos del directorio origen
        List<File> listaFicheros = listarFicherosRecursivoFiltrado(directorio, new FiltroFileNoDirectorio());
        List<File> listaDuplicados = new ArrayList<>();
        Collections.sort(listaFicheros, new Comparators.comparatorFileTamanhoYNombre());

        for (int i = 1; i < listaFicheros.size(); i++) { // Empezamos en 1, no en 0, pues el 1º elemento no tiene un anterior para comparar
            // Necesitamos comparar objetos de nuestra clase, porque es la que tiene sobreescrito el equals
            FicheroCopia ficheroAnterior = new FicheroCopia(listaFicheros.get(i - 1).getCanonicalPath());
            FicheroCopia ficheroLeido = new FicheroCopia(listaFicheros.get(i).getCanonicalPath());
            if (ficheroLeido.equals(ficheroAnterior)) {
                // Añadimos ambos duplicados, si el anterior ya se había añadido no lo volvemos a añadir
                // Hay que comprobar que existe el anterior antes de añadir el leído, porque sino siempre 
                // devuelve contains=true porque el equals entre ambos es true
                if (!listaDuplicados.contains(ficheroAnterior)) {
                    listaDuplicados.add(ficheroAnterior);
                }
                listaDuplicados.add(ficheroLeido);
            }
        }
        return listaDuplicados;
    }

    /**
     * Lista todos los ficheros contenidos en un directorio (y todos sus subdirectorios)
     * @param directorio
     * @return Lista con los File contenidos en el directorio pasado por parámetro
     * @throws IOException
     */
    public static List<File> listarFicherosRecursivo(File directorio) throws IOException {
        // Aquí no podemos reiniciar la lista de subficheros, porque la pisaría una y otra vez al ser un método recursivo
        listaSubficheros.addAll(Arrays.asList(directorio.listFiles()));
        // Recorremos los sub-directorios y a su vez listamos sus sub-ficheros
        for (File fichero : directorio.listFiles(new FiltroFileDirectorio())) {
            listarFicherosRecursivo(fichero);
        }
        return listaSubficheros;
    }

    /**
     * Lista todos los ficheros, que superen el filtro pasado por parámetro, contenidos 
     * en un directorio (y todos sus subdirectorios)
     * @param directorio
     * @param filtro
     * @return Lista con los File contenidos en el directorio pasado por parámetro que han superado el filtro pasado por parámetro
     * @throws IOException
     */
    public static List<File> listarFicherosRecursivoFiltrado(File directorio, FileFilter filtro) throws IOException {
        // Aquí no podemos reiniciar la lista de subficheros, porque la pisaría una y otra vez al ser un método recursivo
        listaSubficheros.addAll(Arrays.asList(directorio.listFiles(filtro)));
        // Recorremos los sub-directorios y a su vez listamos sus sub-ficheros filtrados
        for (File fichero : directorio.listFiles(new FiltroFileDirectorio())) {
            listarFicherosRecursivoFiltrado(fichero, filtro);
        }
        return listaSubficheros;
    }

    /**
     * Elimina todos los subdirectorios sin contenido del directorio pasado por parámetro
     * @param directorio
     * @return Lista con los directorios vacíos eliminados
     * @throws IOException
     */
    public static List<File> eliminarDirectoriosVacios(File directorio) throws IOException {
        reiniciarListaSubficheros();

        List<File> borrados = new ArrayList<>();

        for (File fichero : listarFicherosRecursivoFiltrado(directorio,new FiltroFileDirectorio())) {
            if (fichero.isDirectory() && fichero.list().length == 0) {
                if (fichero.delete()) {
                    borrados.add(fichero);
                }
            }
        }

        return borrados;
    }

    /**
     * Elimina un directorio con todo su contenido
     * @param directorioPadre
     * @return Lista con los ficheros eliminados (no incluye subdirectorios)
     * @throws IOException
     */
    public static List<File> eliminarDirectorioConContenido(File directorioPadre) throws IOException {
        reiniciarListaSubficheros();

        List<File> borrados = new ArrayList<File>();

        // Borramos todos los ficheros para dejar los directorios vacíos
        List<File> ficherosNoDirectorio = listarFicherosRecursivoFiltrado(directorioPadre, new FiltroFileNoDirectorio());
        for (File fichero : ficherosNoDirectorio) {
            if (fichero.exists()) {
                if (fichero.delete()) {
                    borrados.add(fichero);
                }
            }
        }

        // Borramos todos los directorios ya vacíos
        reiniciarListaSubficheros();
        List<File> ficherosDirectorio = listarFicherosRecursivoFiltrado(directorioPadre, new FiltroFileDirectorio());
        // Ordenamos la lista de mayor a menor ruta (orden reverso al comparar Strings) para que borre primero los directorios hijos
        // Si no borra en orden de hijos a padres, quedan directorios que contienen otros directorios vacíos y no los borra 
        ficherosDirectorio.sort(new Comparators.ComparatorFileRuta().reversed());
        for (File directorioVacio : ficherosDirectorio) {
            if (directorioVacio.exists()) {
                directorioVacio.delete();
            }
        }

        // Borramos finalmente el propio directorio padre que habrá quedado vacío
        if (directorioPadre.exists() && directorioPadre.list().length == 0) {
            directorioPadre.delete();
        }

        return borrados;
    }

    // ESTE FUNCIONA BIEN, habría que vigilar que dos ficheros con el mismo nombre se copien! Sólo copia
    // uno, habría que añadir algún sufijo a los 'repetidos' para que los grabe todos

    /**
     * Mueve los ficheros contenidos en un directorio, que tengan unas determinadas extensiones,
     * en un directorio ubicado en el directorio pasado por parámetro con el nombre de la categoría.
     * @param directorio directorio padre de los ficheros a agrupar
     * @param listaExtensiones lista con las extensiones de los ficheros a agrupar
     * @param categoria nombre del directorio a crear
     * @return lista con los ficheros movidos
     * @throws IOException
     */
    public static List<File> agruparPorCategoria(File directorio, List<String> listaExtensiones, String categoria) throws IOException {
        reiniciarListaSubficheros();

        List<File> ficherosMovidos = new ArrayList<>();

        // Creamos el directorio donde moveremos (no copiar) los archivos (dentro del propio directorio padre pasado por parámetro)
        String nombreNuevoDirectorio = categoria.toUpperCase();
        String rutaNuevoDirectorio = directorio.getCanonicalPath() + File.separator + nombreNuevoDirectorio;
        File nuevoDirectorio = new File(rutaNuevoDirectorio);
        nuevoDirectorio.mkdir();

        // Movemos al nuevo directorio los ficheros delos tipos que se encuentren en la lista de extensiones
        List<File> ficherosFiltrados = listarFicherosRecursivoFiltrado(directorio, new FiltroFileExtension(listaExtensiones));
        for (File ficheroOriginal : ficherosFiltrados) {
            String rutaDestino = rutaNuevoDirectorio + File.separator + ficheroOriginal.getName();
            File ficheroDestino = new File(rutaDestino);
            Files.move(Paths.get(ficheroOriginal.getCanonicalPath()), Paths.get(ficheroDestino.getCanonicalPath()), StandardCopyOption.REPLACE_EXISTING);
            // Necesitamos devolver los ficheros creados, para luego poder actualizar 
            // la ruta en que se encuentran en la lista de copiados del backup
            ficherosMovidos.add(ficheroDestino);
        }
        return ficherosMovidos;
    }

    /**
     * 
     * @param directorioRaiz
     * @return Espacio disponible en un directorio raíz en GB
     */
    public static double espacioLibre(File directorioRaiz) {
        // Le daremos DecimalFormat al mostrarlo
        return directorioRaiz.getFreeSpace() / (Math.pow(1024, 3));
    }

    /**
     * 
     * @param directorioRaiz
     * @return Espacio total en un directorio raíz en GB
     */
    public static double espacioTotal(File directorioRaiz) {
        // Le daremos DecimalFormat al mostrarlo
        return directorioRaiz.getTotalSpace() / (Math.pow(1024, 3));
    }

    /**
     * 
     * @param directorio
     * @return Tamaño del directorio en GB
     * @throws IOException
     */
    public static double tamanhoDirectorio(File directorio) throws IOException{
        double tamanhoTotal=0;
        if (directorio.isDirectory()) {
            List<File> listaSubficherosNoDirectorio = listarFicherosRecursivoFiltrado(directorio,new FiltroFileNoDirectorio());
            for (File fichero : listaSubficherosNoDirectorio) {
                tamanhoTotal+=fichero.length();
            }
        }
        return tamanhoTotal/(Math.pow(1024, 3)); // En GB, para comparar con el espacio libre en el directorio raíz
    }

    /**
     * Reinicia la lista static de subficheros, empleada para guardar los ficheros al listar de manera recursiva,
     * si no se reinicia se van acumulando los ficheros listados cada vez que se ejecuta el método listar
     */
    public static void reiniciarListaSubficheros() {
        listaSubficheros = new ArrayList<>();
    }
    
    /**
     * 
     * @param fichero
     * @return La extensión del fichero (sin ".") pasado por parámetro, null si
     * se trata de un directorio y no tiene ninguna extensión
     */
    public static String getExtension(File fichero) {
        String extension = null;
        // StringTokenizer para sacar la extensión en el segundo token (después del '.')
        StringTokenizer stringTokenizer = new StringTokenizer(fichero.getName(), ".");
        stringTokenizer.nextToken();
        if (stringTokenizer.hasMoreTokens()) {
            extension = stringTokenizer.nextToken();
        }
        return extension;
    }
}
