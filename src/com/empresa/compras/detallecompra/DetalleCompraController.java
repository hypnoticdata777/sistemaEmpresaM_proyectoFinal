package com.empresa.compras.detallecompra;

import java.util.List;

/*
 * Clase Controller.
 * Recibe peticiones relacionadas con los detalles de compra y las envia
 * al Service. En la arquitectura por capas, este Controller conecta la
 * interfaz con la logica de negocio sin usar SQL directamente.
 */
public class DetalleCompraController {
    // Servicio que maneja las reglas para agregar productos a una compra.
    private final DetalleCompraService detalleCompraService;

    /*
     * Constructor vacio.
     * No recibe parametros y no devuelve nada.
     * Crea un servicio real para usar el controlador de forma normal.
     */
    public DetalleCompraController() {
        // Llamamos al constructor que recibe servicio y le pasamos uno nuevo.
        this(new DetalleCompraService());
    }

    /*
     * Constructor con servicio.
     * Recibe un DetalleCompraService y no devuelve nada.
     * Sirve para reutilizar o probar el controlador con otro servicio.
     */
    public DetalleCompraController(DetalleCompraService detalleCompraService) {
        // Guardamos el servicio recibido.
        this.detalleCompraService = detalleCompraService;
    }

    /*
     * Agrega un producto a una compra.
     * Recibe id de compra, id de producto, cantidad y costo unitario.
     * No devuelve nada.
     */
    public void agregarProductoACompra(int idCompra, int idProducto, int cantidad, double costoUnitario) {
        // Delegamos al servicio porque ahi estan las validaciones y el calculo del subtotal.
        detalleCompraService.agregarProductoACompra(idCompra, idProducto, cantidad, costoUnitario);
    }

    /*
     * Muestra todos los detalles de compra.
     * No recibe parametros.
     * Devuelve una lista de detalles.
     */
    public List<DetalleCompra> mostrarDetalles() {
        // Pedimos al servicio todos los detalles registrados.
        return detalleCompraService.mostrarDetalles();
    }

    /*
     * Muestra los detalles de una compra especifica.
     * Recibe el id de la compra.
     * Devuelve una lista con sus detalles.
     */
    public List<DetalleCompra> mostrarDetallesPorCompra(int idCompra) {
        // Pedimos al servicio los detalles de esa compra.
        return detalleCompraService.mostrarDetallesPorCompra(idCompra);
    }

    /*
     * Calcula el subtotal de un producto comprado.
     * Recibe cantidad y costo unitario.
     * Devuelve cantidad multiplicada por costo unitario.
     */
    public double calcularSubtotal(int cantidad, double costoUnitario) {
        // Pedimos al servicio que valide y calcule el subtotal.
        return detalleCompraService.calcularSubtotal(cantidad, costoUnitario);
    }

    /*
     * Recalcula el total de una compra.
     * Recibe el id de la compra.
     * No devuelve nada.
     */
    public void recalcularTotal(int idCompra) {
        // Pedimos al servicio que actualice el total usando los subtotales.
        detalleCompraService.recalcularTotal(idCompra);
    }
}
