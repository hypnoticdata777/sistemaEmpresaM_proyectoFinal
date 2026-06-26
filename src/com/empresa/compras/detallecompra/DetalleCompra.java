package com.empresa.compras.detallecompra;

/*
 * Clase Modelo.
 * Representa un producto dentro de una compra: que producto fue comprado,
 * cuantas unidades, a que costo y cual fue su subtotal. En la arquitectura
 * por capas, este Modelo solo guarda datos y no decide reglas de negocio.
 */
public class DetalleCompra {
    // Identificador unico del detalle de compra.
    private int idDetalleCompra;
    // Identificador de la compra a la que pertenece este detalle.
    private int idCompra;
    // Identificador del producto comprado.
    private int idProducto;
    // Cantidad de piezas o unidades compradas.
    private int cantidad;
    // Costo de una sola unidad del producto.
    private double costoUnitario;
    // Resultado de multiplicar cantidad por costo unitario.
    private double subtotal;

    /*
     * Constructor vacio.
     * No recibe parametros y no devuelve nada.
     * Permite crear el objeto y llenar sus datos despues.
     */
    public DetalleCompra() {
        // No se inicializa nada especial; el objeto queda con valores por defecto.
    }

    /*
     * Constructor completo.
     * Recibe todos los datos de un detalle de compra.
     * No devuelve nada porque los constructores no retornan valores.
     */
    public DetalleCompra(int idDetalleCompra, int idCompra, int idProducto, int cantidad, double costoUnitario, double subtotal) {
        // Guardamos el id del detalle.
        this.idDetalleCompra = idDetalleCompra;
        // Guardamos el id de la compra relacionada.
        this.idCompra = idCompra;
        // Guardamos el id del producto comprado.
        this.idProducto = idProducto;
        // Guardamos la cantidad comprada.
        this.cantidad = cantidad;
        // Guardamos el costo de cada unidad.
        this.costoUnitario = costoUnitario;
        // Guardamos el subtotal ya calculado.
        this.subtotal = subtotal;
    }

    /*
     * Devuelve el id del detalle de compra.
     * No recibe parametros.
     * Retorna un entero.
     */
    public int getIdDetalleCompra() {
        // Regresamos el identificador del detalle.
        return idDetalleCompra;
    }

    /*
     * Cambia el id del detalle de compra.
     * Recibe el nuevo id y no devuelve nada.
     */
    public void setIdDetalleCompra(int idDetalleCompra) {
        // Actualizamos el identificador del detalle.
        this.idDetalleCompra = idDetalleCompra;
    }

    /*
     * Devuelve el id de la compra.
     * No recibe parametros.
     * Retorna un entero.
     */
    public int getIdCompra() {
        // Regresamos el id de la compra relacionada.
        return idCompra;
    }

    /*
     * Cambia el id de la compra.
     * Recibe el nuevo id y no devuelve nada.
     */
    public void setIdCompra(int idCompra) {
        // Actualizamos la compra relacionada.
        this.idCompra = idCompra;
    }

    /*
     * Devuelve el id del producto.
     * No recibe parametros.
     * Retorna un entero.
     */
    public int getIdProducto() {
        // Regresamos el producto comprado.
        return idProducto;
    }

    /*
     * Cambia el id del producto.
     * Recibe el nuevo id y no devuelve nada.
     */
    public void setIdProducto(int idProducto) {
        // Actualizamos el producto comprado.
        this.idProducto = idProducto;
    }

    /*
     * Devuelve la cantidad comprada.
     * No recibe parametros.
     * Retorna un entero.
     */
    public int getCantidad() {
        // Regresamos la cantidad de unidades.
        return cantidad;
    }

    /*
     * Cambia la cantidad comprada.
     * Recibe la nueva cantidad y no devuelve nada.
     */
    public void setCantidad(int cantidad) {
        // Actualizamos la cantidad.
        this.cantidad = cantidad;
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
     * Cambia el costo unitario.
     * Recibe el nuevo costo y no devuelve nada.
     */
    public void setCostoUnitario(double costoUnitario) {
        // Actualizamos el costo por unidad.
        this.costoUnitario = costoUnitario;
    }

    /*
     * Devuelve el subtotal.
     * No recibe parametros.
     * Retorna un numero decimal.
     */
    public double getSubtotal() {
        // Regresamos el subtotal del detalle.
        return subtotal;
    }

    /*
     * Cambia el subtotal.
     * Recibe el nuevo subtotal y no devuelve nada.
     */
    public void setSubtotal(double subtotal) {
        // Actualizamos el subtotal.
        this.subtotal = subtotal;
    }

    /*
     * Convierte el detalle en texto.
     * No recibe parametros.
     * Devuelve un String facil de imprimir.
     */
    @Override
    public String toString() {
        // Construimos un texto con los datos del detalle.
        return "DetalleCompra{" +
                "idDetalleCompra=" + idDetalleCompra +
                ", idCompra=" + idCompra +
                ", idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                ", costoUnitario=" + costoUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}
