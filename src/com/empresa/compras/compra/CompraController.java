package com.empresa.compras.compra;

import java.util.List;

/*
 * Clase Controller.
 * Es la entrada para las acciones relacionadas con compras desde la interfaz
 * o desde App. En la arquitectura por capas, el Controller recibe la peticion
 * y se la pasa al Service, sin manejar directamente la base de datos.
 */
public class CompraController {
    // Servicio que contiene las reglas de negocio de las compras.
    private final CompraService compraService;

    /*
     * Constructor vacio.
     * No recibe parametros y no devuelve nada.
     * Crea un servicio real para que el controlador pueda trabajar.
     */
    public CompraController() {
        // Llamamos al otro constructor y le mandamos un CompraService nuevo.
        this(new CompraService());
    }

    /*
     * Constructor con inyeccion de dependencia.
     * Recibe un CompraService y no devuelve nada.
     * Sirve para poder usar otro servicio, por ejemplo en pruebas.
     */
    public CompraController(CompraService compraService) {
        // Guardamos el servicio recibido para usarlo en los metodos.
        this.compraService = compraService;
    }

    /*
     * Registra una compra nueva.
     * Recibe el id del proveedor y la fecha de la compra.
     * Devuelve el id generado para la compra.
     */
    public int registrarCompra(int idProveedor, String fecha) {
        // Delegamos el registro al servicio, porque ahi estan las validaciones.
        return compraService.registrarCompra(idProveedor, fecha);
    }

    /*
     * Muestra todas las compras.
     * No recibe parametros.
     * Devuelve una lista de compras.
     */
    public List<Compra> mostrarCompras() {
        // Pedimos al servicio la lista completa de compras.
        return compraService.mostrarCompras();
    }

    /*
     * Busca una compra por id.
     * Recibe el id de la compra.
     * Devuelve la compra encontrada.
     */
    public Compra buscarCompra(int idCompra) {
        // Pedimos al servicio que busque y valide la compra.
        return compraService.buscarCompra(idCompra);
    }

    /*
     * Confirma una compra.
     * Recibe el id de la compra y no devuelve nada.
     */
    public void confirmarCompra(int idCompra) {
        // Avisamos al servicio que cambie la compra a confirmada.
        compraService.confirmarCompra(idCompra);
    }

    /*
     * Cancela una compra.
     * Recibe el id de la compra y no devuelve nada.
     */
    public void cancelarCompra(int idCompra) {
        // Avisamos al servicio que cambie la compra a cancelada.
        compraService.cancelarCompra(idCompra);
    }

    /*
     * Calcula el total de una compra.
     * Recibe el id de la compra.
     * Devuelve el total calculado.
     */
    public double calcularTotal(int idCompra) {
        // Pedimos al servicio que calcule el total usando los detalles de la compra.
        return compraService.calcularTotal(idCompra);
    }
}
