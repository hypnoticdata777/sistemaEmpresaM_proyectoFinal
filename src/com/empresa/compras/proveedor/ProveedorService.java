package com.empresa.compras.proveedor;

import java.util.List;

/*
 * Clase Service.
 * Contiene las reglas de negocio para proveedores: valida datos obligatorios,
 * evita RFC duplicados y controla la desactivacion. En la arquitectura por
 * capas, el Service decide si una operacion se permite antes de ir al Repository.
 *
 * Clave para exposicion:
 * Esta es la clase mas importante para defender la seccion, porque explica el
 * "por que" del modulo: no registrar proveedores incompletos, no duplicar RFC y
 * no desactivar algo que no existe.
 */
public class ProveedorService {
    // Repositorio que guarda y consulta proveedores en la base de datos.
    private final ProveedorRepository proveedorRepository;

    /*
     * Constructor vacio.
     * No recibe parametros y no devuelve nada.
     * Crea un repositorio real para que el servicio funcione.
     */
    public ProveedorService() {
        // Llamamos al constructor que recibe repositorio y le pasamos uno nuevo.
        this(new ProveedorRepository());
    }

    /*
     * Constructor con repositorio.
     * Recibe un ProveedorRepository y no devuelve nada.
     * Sirve para usar otro repositorio, por ejemplo durante pruebas.
     */
    public ProveedorService(ProveedorRepository proveedorRepository) {
        // Guardamos el repositorio recibido.
        this.proveedorRepository = proveedorRepository;
    }

    /*
     * Registra un proveedor nuevo.
     * Recibe nombre, RFC, telefono, correo y direccion.
     * No devuelve nada.
     */
    public void registrarProveedor(String nombre, String rfc, String telefono, String correo, String direccion) {
        /*
         * Flujo del registro:
         * 1. Validar campos obligatorios.
         * 2. Validar formato basico del correo.
         * 3. Consultar si el RFC ya existe.
         * 4. Crear el objeto Proveedor activo.
         * 5. Guardarlo en la base de datos por medio del Repository.
         */
        // Regla de negocio: el nombre es obligatorio para saber a quien se le compra.
        validarTexto(nombre, "El nombre es obligatorio");
        // Regla de negocio: el RFC es obligatorio porque identifica fiscalmente al proveedor.
        validarTexto(rfc, "El RFC es obligatorio");
        // Regla de negocio: el telefono es obligatorio para poder contactar al proveedor si hay problemas con la compra.
        validarTexto(telefono, "El telefono es obligatorio");

        // Regla de negocio: el correo debe tener @ para ser un contacto minimamente valido.
        if (correo == null || !correo.contains("@")) {
            // Detenemos el registro si el correo no parece correcto.
            throw new IllegalArgumentException("El correo debe contener @");
        }

        // Regla de negocio: no se permite repetir RFC porque dos proveedores con el mismo RFC causarian duplicados fiscales.
        if (proveedorRepository.existeRfc(rfc.trim())) {
            // Avisamos que ese RFC ya esta registrado.
            throw new IllegalArgumentException("El RFC ya existe");
        }

        // Creamos el proveedor limpio, quitando espacios sobrantes y marcandolo como activo.
        Proveedor proveedor = new Proveedor(0, nombre.trim(), rfc.trim(), telefono.trim(), correo.trim(), direccion, true);
        // Guardamos el proveedor usando el repositorio.
        proveedorRepository.guardar(proveedor);
    }

    /*
     * Muestra todos los proveedores.
     * No recibe parametros.
     * Devuelve una lista de proveedores.
     */
    public List<Proveedor> mostrarProveedores() {
        // Pedimos al repositorio todos los proveedores guardados.
        return proveedorRepository.obtenerTodos();
    }

    /*
     * Busca un proveedor por id.
     * Recibe el id del proveedor.
     * Devuelve el proveedor encontrado.
     */
    public Proveedor buscarProveedor(int idProveedor) {
        // Consultamos el proveedor en el repositorio.
        Proveedor proveedor = proveedorRepository.buscarPorId(idProveedor);
        // Regla de negocio: no se puede operar con un proveedor inexistente porque afectaria compras y registros.
        if (proveedor == null) {
            // Lanzamos error si el id no existe.
            throw new IllegalArgumentException("No existe el proveedor indicado");
        }
        // Regresamos el proveedor valido.
        return proveedor;
    }

    /*
     * Desactiva un proveedor.
     * Recibe el id del proveedor.
     * No devuelve nada.
     */
    public void desactivarProveedor(int idProveedor) {
        /*
         * No se borra el proveedor: solo se marca como inactivo.
         * Esto conserva historial y evita romper compras ya registradas que
         * pueden estar relacionadas con ese proveedor.
         */
        // Regla de negocio: primero verificamos que exista para no desactivar un registro inexistente.
        buscarProveedor(idProveedor);
        // Pedimos al repositorio que marque al proveedor como inactivo.
        proveedorRepository.desactivar(idProveedor);
    }

    /*
     * Valida que un texto obligatorio tenga contenido.
     * Recibe el texto y el mensaje que se usara si falla.
     * No devuelve nada; si el texto esta vacio, lanza error.
     */
    private void validarTexto(String valor, String mensaje) {
        // Regla de negocio: los campos obligatorios no pueden ir vacios porque luego el proveedor queda incompleto.
        if (valor == null || valor.trim().isEmpty()) {
            // Lanzamos el mensaje especifico de la validacion.
            throw new IllegalArgumentException(mensaje);
        }
    }
}
