package com.empresa.compras.compra;

import java.util.Arrays;
import java.util.List;

/*
 * Clase Service.
 * Contiene las reglas de negocio de las compras: valida datos,
 * decide si una accion se permite y pide al Repository que guarde o consulte.
 * En la arquitectura por capas, el Service es el "cerebro" del modulo.
 */
public class CompraService {
    // Repositorio usado para leer y escribir compras en la base de datos.
    private final CompraRepository compraRepository;

    /*
     * Constructor vacio.
     * No recibe parametros y no devuelve nada.
     * Crea un repositorio real para trabajar con la base de datos.
     */
    public CompraService() {
        // Llamamos al constructor que recibe repositorio y le pasamos uno nuevo.
        this(new CompraRepository());
    }

    /*
     * Constructor con repositorio.
     * Recibe un CompraRepository y no devuelve nada.
     * Ayuda a cambiar el repositorio cuando se hacen pruebas.
     */
    public CompraService(CompraRepository compraRepository) {
        // Guardamos el repositorio recibido.
        this.compraRepository = compraRepository;
    }

    /*
     * Registra una compra nueva.
     * Recibe el id del proveedor y la fecha.
     * Devuelve el id generado para la compra.
     */
    public int registrarCompra(int idProveedor, String fecha) {
        // Regla de negocio: solo se puede comprar a proveedores existentes y activos para evitar compras con proveedores bloqueados.
        if (!compraRepository.existeProveedor(idProveedor)) {
            // Detenemos el registro porque ese proveedor no es valido para operar.
            throw new IllegalArgumentException("El proveedor no existe o esta inactivo");
        }

        // Regla de negocio: la fecha es obligatoria para saber cuando ocurrio la compra y poder auditarla despues.
        validarTexto(fecha, "La fecha es obligatoria");
        // Creamos la compra nueva con total 0 y estado PENDIENTE porque aun no tiene productos confirmados.
        Compra compra = new Compra(0, idProveedor, fecha.trim(), 0, Compra.ESTADO_PENDIENTE);
        // Guardamos la compra usando el repositorio y regresamos el id generado.
        return compraRepository.guardar(compra);
    }

    /*
     * Muestra todas las compras.
     * No recibe parametros.
     * Devuelve una lista con las compras registradas.
     */
    public List<Compra> mostrarCompras() {
        // Pedimos al repositorio todas las compras.
        return compraRepository.obtenerTodos();
    }

    /*
     * Busca una compra por id.
     * Recibe el id de la compra.
     * Devuelve la compra encontrada.
     */
    public Compra buscarCompra(int idCompra) {
        // Consultamos el repositorio para buscar la compra.
        Compra compra = compraRepository.buscarPorId(idCompra);
        // Regla de negocio: no se puede trabajar con una compra que no existe porque causaria movimientos falsos.
        if (compra == null) {
            // Avisamos que el id indicado no corresponde a ninguna compra.
            throw new IllegalArgumentException("No existe la compra indicada");
        }
        // Regresamos la compra valida.
        return compra;
    }

    /*
     * Confirma una compra.
     * Recibe el id de la compra y no devuelve nada.
     */
    public void confirmarCompra(int idCompra) {
        // Primero buscamos la compra para asegurarnos de que existe.
        Compra compra = buscarCompra(idCompra);
        // Regla de negocio: una compra cancelada no debe confirmarse porque ya fue marcada como no valida.
        if (Compra.ESTADO_CANCELADA.equals(compra.getEstado())) {
            // Detenemos la operacion para no reactivar una compra cancelada por accidente.
            throw new IllegalArgumentException("No se puede confirmar una compra cancelada");
        }
        // Si pasa la validacion, actualizamos el estado a CONFIRMADA.
        compraRepository.actualizarEstado(idCompra, Compra.ESTADO_CONFIRMADA);
    }

    /*
     * Cancela una compra.
     * Recibe el id de la compra y no devuelve nada.
     */
    public void cancelarCompra(int idCompra) {
        // Verificamos que la compra exista antes de intentar cancelarla.
        buscarCompra(idCompra);
        // Cambiamos el estado de la compra a CANCELADA.
        compraRepository.actualizarEstado(idCompra, Compra.ESTADO_CANCELADA);
    }

    /*
     * Calcula y actualiza el total de una compra.
     * Recibe el id de la compra.
     * Devuelve el total calculado.
     */
    public double calcularTotal(int idCompra) {
        // Verificamos que la compra exista antes de calcular su total.
        buscarCompra(idCompra);
        // Pedimos al repositorio que sume los subtotales de sus detalles.
        double total = compraRepository.calcularTotal(idCompra);
        // Regla de negocio: el total de una compra nunca debe ser negativo porque representa dinero a pagar.
        if (total < 0) {
            // Detenemos la operacion si se detecta un total imposible.
            throw new IllegalArgumentException("El total no puede ser negativo");
        }
        // Guardamos el total calculado en la tabla compra.
        compraRepository.actualizarTotal(idCompra, total);
        // Regresamos el total para que otras partes del programa puedan mostrarlo o usarlo.
        return total;
    }

    /*
     * Valida que un estado de compra sea permitido.
     * Recibe el estado como texto.
     * No devuelve nada; si el estado no sirve, lanza error.
     */
    public void validarEstado(String estado) {
        // Creamos una lista con los estados aceptados por el negocio.
        List<String> estados = Arrays.asList(Compra.ESTADO_PENDIENTE, Compra.ESTADO_CONFIRMADA, Compra.ESTADO_CANCELADA);
        // Regla de negocio: solo se aceptan estados conocidos para evitar compras con estados inventados o confusos.
        if (!estados.contains(estado)) {
            // Avisamos que el estado no pertenece al flujo correcto.
            throw new IllegalArgumentException("Estado no permitido");
        }
    }

    /*
     * Valida que un texto no venga vacio.
     * Recibe el texto a revisar y el mensaje que se mostrara si falla.
     * No devuelve nada; si el texto esta mal, lanza error.
     */
    private void validarTexto(String valor, String mensaje) {
        // Regla de negocio: los datos obligatorios no pueden quedar vacios porque luego no se podria identificar bien la compra.
        if (valor == null || valor.trim().isEmpty()) {
            // Lanzamos el mensaje especifico que recibio el metodo.
            throw new IllegalArgumentException(mensaje);
        }
    }
}
