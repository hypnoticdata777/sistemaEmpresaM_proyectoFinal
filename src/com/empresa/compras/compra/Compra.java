package com.empresa.compras.compra;

/*
 * Clase Modelo.
 * Representa una compra dentro del sistema: guarda sus datos principales
 * como proveedor, fecha, total y estado. En la arquitectura por capas,
 * el Modelo solo describe la informacion que se va a mover entre las capas.
 */
public class Compra {
    // Estado que indica que la compra fue creada pero todavia no se confirma.
    public static final String ESTADO_PENDIENTE = "PENDIENTE";
    // Estado que indica que la compra ya fue aprobada.
    public static final String ESTADO_CONFIRMADA = "CONFIRMADA";
    // Estado que indica que la compra fue cancelada.
    public static final String ESTADO_CANCELADA = "CANCELADA";

    // Identificador unico de la compra en la base de datos.
    private int idCompra;
    // Identificador del proveedor relacionado con esta compra.
    private int idProveedor;
    // Fecha escrita como texto para saber cuando se registro la compra.
    private String fecha;
    // Suma de todos los productos agregados a la compra.
    private double total;
    // Estado actual de la compra: pendiente, confirmada o cancelada.
    private String estado;

    /*
     * Constructor vacio.
     * No recibe parametros y no devuelve nada porque los constructores no retornan valores.
     * Sirve cuando queremos crear una compra y llenar sus datos despues con setters.
     */
    public Compra() {
        // No hacemos nada aqui; Java crea el objeto vacio.
    }

    /*
     * Constructor completo.
     * Recibe todos los datos de una compra y los guarda en el objeto.
     * No devuelve nada porque es un constructor.
     */
    public Compra(int idCompra, int idProveedor, String fecha, double total, String estado) {
        // Guardamos el id recibido en el atributo del objeto.
        this.idCompra = idCompra;
        // Guardamos el id del proveedor recibido.
        this.idProveedor = idProveedor;
        // Guardamos la fecha recibida.
        this.fecha = fecha;
        // Guardamos el total recibido.
        this.total = total;
        // Guardamos el estado recibido.
        this.estado = estado;
    }

    /*
     * Metodo getter.
     * No recibe parametros y devuelve el id de la compra.
     */
    public int getIdCompra() {
        // Regresamos el valor guardado en idCompra.
        return idCompra;
    }

    /*
     * Metodo setter.
     * Recibe el nuevo id de la compra y no devuelve nada.
     */
    public void setIdCompra(int idCompra) {
        // Actualizamos el id de la compra con el valor recibido.
        this.idCompra = idCompra;
    }

    /*
     * Metodo getter.
     * No recibe parametros y devuelve el id del proveedor.
     */
    public int getIdProveedor() {
        // Regresamos el proveedor asociado a esta compra.
        return idProveedor;
    }

    /*
     * Metodo setter.
     * Recibe el id del proveedor y no devuelve nada.
     */
    public void setIdProveedor(int idProveedor) {
        // Actualizamos el proveedor asociado a esta compra.
        this.idProveedor = idProveedor;
    }

    /*
     * Metodo getter.
     * No recibe parametros y devuelve la fecha de la compra.
     */
    public String getFecha() {
        // Regresamos la fecha guardada.
        return fecha;
    }

    /*
     * Metodo setter.
     * Recibe una fecha en texto y no devuelve nada.
     */
    public void setFecha(String fecha) {
        // Actualizamos la fecha con el texto recibido.
        this.fecha = fecha;
    }

    /*
     * Metodo getter.
     * No recibe parametros y devuelve el total de la compra.
     */
    public double getTotal() {
        // Regresamos el total actual de la compra.
        return total;
    }

    /*
     * Metodo setter.
     * Recibe el total nuevo y no devuelve nada.
     */
    public void setTotal(double total) {
        // Actualizamos el total de la compra.
        this.total = total;
    }

    /*
     * Metodo getter.
     * No recibe parametros y devuelve el estado de la compra.
     */
    public String getEstado() {
        // Regresamos el estado actual de la compra.
        return estado;
    }

    /*
     * Metodo setter.
     * Recibe el nuevo estado y no devuelve nada.
     */
    public void setEstado(String estado) {
        // Actualizamos el estado de la compra.
        this.estado = estado;
    }

    /*
     * Metodo toString.
     * No recibe parametros y devuelve un texto con los datos de la compra.
     * Ayuda a imprimir el objeto de forma entendible en consola.
     */
    @Override
    public String toString() {
        // Construimos y regresamos un texto juntando todos los atributos importantes.
        return "Compra{" +
                "idCompra=" + idCompra +
                ", idProveedor=" + idProveedor +
                ", fecha='" + fecha + '\'' +
                ", total=" + total +
                ", estado='" + estado + '\'' +
                '}';
    }
}
