package Modelo;

/**
 * Clase contenedora de todas las subclases Exception a emplear en operaciones
 * con ficheros
 *
 * @author aranx
 */
public class MyExceptions {

    public static class DirectorioVacio extends Exception {

        /**
         * Lanza excepción cuando la ruta introducida corresponde a un
         * directorio vacío
         *
         * @param string
         */
        public DirectorioVacio(String string) {
            super(string);
        }

        /**
         * Lanza excepción cuando la ruta introducida corresponde a un
         * directorio vacío
         */
        public DirectorioVacio() {

        }

    }

    public static class EspacioInsuficiente extends Exception {

        /**
         * Lanza excepción cuando el espacio libre en el directorio raíz donde
         * se copiará un directorio es menor que el tamaño del directorio que se
         * intenta copiar
         *
         * @param string
         */
        public EspacioInsuficiente(String string) {
            super(string);
        }

        /**
         * Lanza excepción cuando el espacio libre en el directorio raíz donde
         * se copiará un directorio es menor que el tamaño del directorio que se
         * intenta copiar
         */
        public EspacioInsuficiente() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
    
    
    public static class DirectorioYaExiste extends Exception {

        /**
         * Lanza excepción cuando un directorio a crear ya existe
         * @param string
         */
        public DirectorioYaExiste(String string) {
            super(string);
        }

        /**
         * Lanza excepción cuando un directorio a crear ya existe
         */
        public DirectorioYaExiste() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
 
}
