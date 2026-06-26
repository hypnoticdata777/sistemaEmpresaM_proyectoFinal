package com.empresa.json.dto;

/*
 * Clase Modelo / DTO.
 * Representa la informacion que se va a escribir en el JSON de entradas
 * de inventario. En la arquitectura por capas funciona como Modelo de
 * transporte: solo lleva datos desde la consulta hasta el exportador.
 */
public class EntradaInventarioDto {
    // Identificador del movimiento de inventario que saldra en el JSON.
    private int idMovimiento;
    // Identificador de la compra que origino la entrada.
    private int idCompra;
    // Identificador del producto que entro al inventario.
    private int idProducto;
    // Cantidad de producto que entro.
    private int cantidad;
    // Costo de cada unidad comprada.
    private double costoUnitario;
    // Fecha de la compra relacionada.
    private String fecha;
    // Tipo de movimiento; en este caso siempre es ENTRADA.
    private String tipo;

    /*
     * Constructor completo para el DTO de entrada de inventario.
     * Recibe id del movimiento, compra, producto, cantidad, costo y fecha.
     * No devuelve nada porque es constructor.
     */
    public EntradaInventarioDto(int idMovimiento, int idCompra, int idProducto, int cantidad, double costoUnitario, String fecha) {
        // Guardamos el id del movimiento.
        this.idMovimiento = idMovimiento;
        // Guardamos el id de la compra.
        this.idCompra = idCompra;
        // Guardamos el id del producto.
        this.idProducto = idProducto;
        // Guardamos la cantidad.
        this.cantidad = cantidad;
        // Guardamos el costo unitario.
        this.costoUnitario = costoUnitario;
        // Guardamos la fecha.
        this.fecha = fecha;
        // Asignamos ENTRADA porque este DTO representa productos que entran al inventario.
        this.tipo = "ENTRADA";
    }

    /*
     * Devuelve el id del movimiento.
     * No recibe parametros.
     * Retorna un entero.
     */
    public int getIdMovimiento() {
        // Regresamos el id del movimiento.
        return idMovimiento;
    }

    /*
     * Devuelve el id de la compra.
     * No recibe parametros.
     * Retorna un entero.
     */
    public int getIdCompra() {
        // Regresamos el id de la compra.
        return idCompra;
    }

    /*
     * Devuelve el id del producto.
     * No recibe parametros.
     * Retorna un entero.
     */
    public int getIdProducto() {
        // Regresamos el id del producto.
        return idProducto;
    }

    /*
     * Devuelve la cantidad.
     * No recibe parametros.
     * Retorna un entero.
     */
    public int getCantidad() {
        // Regresamos la cantidad de productos.
        return cantidad;
    }

    /*
     * Devuelve el costo unitario.
     * No recibe parametros.
     * Retorna un numero decimal.
     */
    public double getCostoUnitario() {
        // Regresamos el costo de una unidad.
        return costoUnitario;
    }

    /*
     * Devuelve la fecha.
     * No recibe parametros.
     * Retorna un texto.
     */
    public String getFecha() {
        // Regresamos la fecha guardada.
        return fecha;
    }

    /*
     * Devuelve el tipo de movimiento.
     * No recibe parametros.
     * Retorna un texto.
     */
    public String getTipo() {
        // Regresamos el tipo, que normalmente sera ENTRADA.
        return tipo;
    }
}
