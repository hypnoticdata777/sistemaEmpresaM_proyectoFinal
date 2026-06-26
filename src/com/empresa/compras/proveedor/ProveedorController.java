package com.empresa.compras.proveedor;

import java.util.List;

/*
 * Clase Controller.
 * Es la entrada para registrar, mostrar, buscar y desactivar proveedores.
 * En la arquitectura por capas, este Controller recibe la solicitud y la
 * manda al Service, sin aplicar SQL ni reglas complejas directamente.
 */
public class ProveedorController {
    // Servicio que contiene las reglas de negocio de proveedores.
    private final ProveedorService proveedorService;

    /*
     * Constructor vacio.
     * No recibe parametros y no devuelve nada.
     * Crea un servicio real para trabajar normalmente.
     */
    public ProveedorController() {
        // Llamamos al constructor que recibe servicio y le pasamos uno nuevo.
        this(new ProveedorService());
    }

    /*
     * Constructor con servicio.
     * Recibe un ProveedorService y no devuelve nada.
     * Sirve para inyectar otra version del servicio, por ejemplo en pruebas.
     */
    public ProveedorController(ProveedorService proveedorService) {
        // Guardamos el servicio recibido.
        this.proveedorService = proveedorService;
    }

    /*
     * Registra un proveedor nuevo.
     * Recibe nombre, RFC, telefono, correo y direccion.
     * No devuelve nada.
     */
    public void registrarProveedor(String nombre, String rfc, String telefono, String correo, String direccion) {
        // Delegamos el registro al servicio porque ahi estan las validaciones.
        proveedorService.registrarProveedor(nombre, rfc, telefono, correo, direccion);
    }

    /*
     * Muestra todos los proveedores.
     * No recibe parametros.
     * Devuelve una lista de proveedores.
     */
    public List<Proveedor> mostrarProveedores() {
        // Pedimos al servicio la lista de proveedores.
        return proveedorService.mostrarProveedores();
    }

    /*
     * Busca un proveedor por id.
     * Recibe el id del proveedor.
     * Devuelve el proveedor encontrado.
     */
    public Proveedor buscarProveedor(int idProveedor) {
        // Pedimos al servicio que busque y valide el proveedor.
        return proveedorService.buscarProveedor(idProveedor);
    }

    /*
     * Desactiva un proveedor.
     * Recibe el id del proveedor.
     * No devuelve nada.
     */
    public void desactivarProveedor(int idProveedor) {
        // Pedimos al servicio que valide y desactive el proveedor.
        proveedorService.desactivarProveedor(idProveedor);
    }
}
