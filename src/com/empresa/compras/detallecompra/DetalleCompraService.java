package com.empresa.compras.detallecompra;

import com.empresa.json.importador.ProductosImportador;

import java.nio.file.Path;
import java.util.List;

/*
 * Clase Service.
 * Contiene las reglas de negocio para los productos agregados a una compra.
 * Valida que la compra y el producto existan, calcula subtotales y pide al
 * Repository guardar datos. En la arquitectura por capas, aqui vive la logica.
 */
public class DetalleCompraService {
    // Repositorio que consulta y guarda detalles de compra en la base de datos.
    private final DetalleCompraRepository detalleCompraRepository;
    // Importador usado para validar productos leyendo un archivo JSON cuando se necesite.
    private final ProductosImportador productosImportador;

    /*
     * Constructor vacio.
     * No recibe parametros y no devuelve nada.
     * Crea las dependencias reales para trabajar normalmente.
     */
    public DetalleCompraService() {
        // Llamamos al constructor completo con repositorio e importador nuevos.
        this(new DetalleCompraRepository(), new ProductosImportador());
    }

    /*
     * Constructor con dependencias.
     * Recibe un repositorio y un importador de productos.
     * No devuelve nada.
     */
    public DetalleCompraService(DetalleCompraRepository detalleCompraRepository, ProductosImportador productosImportador) {
        // Guardamos el repositorio recibido.
        this.detalleCompraRepository = detalleCompraRepository;
        // Guardamos el importador recibido.
        this.productosImportador = productosImportador;
    }

    /*
     * Agrega un producto a una compra.
     * Recibe id de compra, id de producto, cantidad y costo unitario.
     * No devuelve nada.
     */
    public void agregarProductoACompra(int idCompra, int idProducto, int cantidad, double costoUnitario) {
        // Regla de negocio: no se puede agregar producto a una compra inexistente porque se perderia el movimiento.
        validarCompra(idCompra);
        // Regla de negocio: solo se deben comprar productos registrados y activos para evitar inventario incorrecto.
        validarProducto(idProducto);
        // Regla de negocio: la cantidad debe ser positiva porque una compra real agrega unidades al inventario.
        validarCantidad(cantidad);
        // Regla de negocio: el costo debe ser positivo porque no se puede registrar una compra con precio cero o negativo.
        validarCosto(costoUnitario);

        // Calculamos el subtotal multiplicando cantidad por costo unitario.
        double subtotal = calcularSubtotal(cantidad, costoUnitario);
        // Creamos el objeto detalle con id 0 porque la base de datos genera el id real.
        DetalleCompra detalleCompra = new DetalleCompra(0, idCompra, idProducto, cantidad, costoUnitario, subtotal);
        // Guardamos el detalle en la base de datos.
        detalleCompraRepository.guardar(detalleCompra);
        // Recalculamos el total de la compra porque acaba de cambiar con este producto.
        recalcularTotal(idCompra);
    }

    /*
     * Muestra todos los detalles de compra.
     * No recibe parametros.
     * Devuelve una lista de detalles.
     */
    public List<DetalleCompra> mostrarDetalles() {
        // Pedimos al repositorio todos los detalles guardados.
        return detalleCompraRepository.obtenerTodos();
    }

    /*
     * Muestra detalles de una compra especifica.
     * Recibe el id de la compra.
     * Devuelve una lista de detalles de esa compra.
     */
    public List<DetalleCompra> mostrarDetallesPorCompra(int idCompra) {
        // Regla de negocio: primero comprobamos que la compra exista antes de mostrar sus detalles.
        validarCompra(idCompra);
        // Pedimos al repositorio los detalles filtrados por compra.
        return detalleCompraRepository.obtenerPorCompra(idCompra);
    }

    /*
     * Calcula el subtotal de un detalle.
     * Recibe cantidad y costo unitario.
     * Devuelve el resultado de cantidad por costo.
     */
    public double calcularSubtotal(int cantidad, double costoUnitario) {
        // Regla de negocio: sin cantidad positiva no existe una compra valida de producto.
        validarCantidad(cantidad);
        // Regla de negocio: sin costo positivo no se puede calcular correctamente el dinero de la compra.
        validarCosto(costoUnitario);
        // Multiplicamos unidades por precio de cada unidad.
        return cantidad * costoUnitario;
    }

    /*
     * Recalcula el total de una compra.
     * Recibe el id de la compra.
     * No devuelve nada.
     */
    public void recalcularTotal(int idCompra) {
        // Regla de negocio: solo tiene sentido recalcular una compra que existe.
        validarCompra(idCompra);
        // Pedimos al repositorio actualizar el total en la tabla compra.
        detalleCompraRepository.actualizarTotalCompra(idCompra);
    }

    /*
     * Valida un producto usando el archivo productos.json.
     * Recibe el id del producto y la ruta del archivo JSON.
     * No devuelve nada; lanza error si el producto no existe o esta inactivo.
     */
    public void validarProductoConJson(int idProducto, Path rutaProductosJson) {
        // Regla de negocio: si el inventario viene de JSON, solo se aceptan productos que existan ahi y esten activos.
        if (!productosImportador.existeProductoActivo(idProducto, rutaProductosJson)) {
            // Detenemos la operacion porque el producto no es valido segun el archivo.
            throw new IllegalArgumentException("El producto no existe en productos.json o esta inactivo");
        }
    }

    /*
     * Valida que una compra exista.
     * Recibe el id de la compra.
     * No devuelve nada; si no existe, lanza error.
     */
    private void validarCompra(int idCompra) {
        // Regla de negocio: un detalle siempre debe pertenecer a una compra real para mantener ordenados los registros.
        if (!detalleCompraRepository.existeCompra(idCompra)) {
            // Avisamos que no se puede continuar porque la compra no existe.
            throw new IllegalArgumentException("La compra no existe");
        }
    }

    /*
     * Valida que un producto exista y este activo.
     * Recibe el id del producto.
     * No devuelve nada; si no existe o esta inactivo, lanza error.
     */
    private void validarProducto(int idProducto) {
        // Regla de negocio: no se deben comprar productos inactivos porque podrian estar descontinuados o bloqueados.
        if (!detalleCompraRepository.existeProducto(idProducto)) {
            // Detenemos el registro del detalle.
            throw new IllegalArgumentException("El producto no existe o esta inactivo");
        }
    }

    /*
     * Valida que la cantidad sea mayor que cero.
     * Recibe la cantidad.
     * No devuelve nada; si es invalida, lanza error.
     */
    private void validarCantidad(int cantidad) {
        // Regla de negocio: comprar cero o cantidades negativas no representa una entrada real de inventario.
        if (cantidad <= 0) {
            // Avisamos que la cantidad no cumple la regla.
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
    }

    /*
     * Valida que el costo unitario sea mayor que cero.
     * Recibe el costo unitario.
     * No devuelve nada; si es invalido, lanza error.
     */
    private void validarCosto(double costoUnitario) {
        // Regla de negocio: el costo debe ser positivo para que el total de la compra tenga sentido contable.
        if (costoUnitario <= 0) {
            // Avisamos que el costo no cumple la regla.
            throw new IllegalArgumentException("El costo debe ser mayor que cero");
        }
    }
}
