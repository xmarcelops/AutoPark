package cl.duoc.dej.tienda.exception;

public class EstacionamientoNoEncontradoException extends Exception {

    public EstacionamientoNoEncontradoException() {
    }

    /**
     * Constructs an instance of <code>ProductoNoEncontradoException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public EstacionamientoNoEncontradoException(String msg) {
        super(msg);
    }
}
