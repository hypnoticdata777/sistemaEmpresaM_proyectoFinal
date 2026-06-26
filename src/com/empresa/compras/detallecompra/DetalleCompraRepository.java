package com.empresa.compras.detallecompra;

import com.empresa.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * Clase Repository.
 * Se encarga de guardar y consultar los detalles de compra en la base de datos.
 * En la arquitectura por capas, esta clase es la que conoce la tabla
 * detalle_compra y las consultas SQL relacionadas.
 */
public class DetalleCompraRepository {
    /*
     * Guarda un detalle de compra.
     * Recibe un objeto DetalleCompra con producto, cantidad, costo y subtotal.
     * No devuelve nada.
     */
    public void guardar(DetalleCompra detalleCompra) {
        // Esta consulta inserta un producto dentro de una compra con su cantidad, costo y subtotal.
        String sql = "INSERT INTO detalle_compra (id_compra, id_producto, cantidad, costo_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        // Abrimos la conexion y preparamos el INSERT con parametros.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Colocamos el id de la compra en el primer signo ?.
            ps.setInt(1, detalleCompra.getIdCompra());
            // Colocamos el id del producto en el segundo signo ?.
            ps.setInt(2, detalleCompra.getIdProducto());
            // Colocamos la cantidad en el tercer signo ?.
            ps.setInt(3, detalleCompra.getCantidad());
            // Colocamos el costo unitario en el cuarto signo ?.
            ps.setDouble(4, detalleCompra.getCostoUnitario());
            // Colocamos el subtotal en el quinto signo ?.
            ps.setDouble(5, detalleCompra.getSubtotal());
            // Ejecutamos el INSERT en la base de datos.
            ps.executeUpdate();
        // Si ocurre un error de SQL, lo convertimos en RuntimeException.
        } catch (SQLException e) {
            // Explicamos que el problema fue al guardar el detalle.
            throw new RuntimeException("Error al guardar detalle de compra", e);
        }
    }

    /*
     * Obtiene todos los detalles de compra.
     * No recibe parametros.
     * Devuelve una lista de DetalleCompra.
     */
    public List<DetalleCompra> obtenerTodos() {
        // Esta consulta trae todos los detalles registrados en la tabla detalle_compra.
        String sql = "SELECT id_detalle_compra, id_compra, id_producto, cantidad, costo_unitario, subtotal FROM detalle_compra";
        // Creamos una lista vacia para guardar los detalles encontrados.
        List<DetalleCompra> detalles = new ArrayList<>();

        // Abrimos conexion, ejecutamos el SELECT y recibimos un ResultSet.
        try (Connection conexion = Conexion.getConexion();
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            // Recorremos cada fila encontrada.
            while (rs.next()) {
                // Convertimos la fila en objeto DetalleCompra y la agregamos a la lista.
                detalles.add(mapear(rs));
            }
        // Si falla la consulta, lanzamos un error claro.
        } catch (SQLException e) {
            // Guardamos la causa original dentro del error.
            throw new RuntimeException("Error al obtener detalles de compra", e);
        }

        // Regresamos la lista de detalles.
        return detalles;
    }

    /*
     * Obtiene los detalles que pertenecen a una compra.
     * Recibe el id de la compra.
     * Devuelve una lista de detalles de esa compra.
     */
    public List<DetalleCompra> obtenerPorCompra(int idCompra) {
        // Esta consulta trae solo los detalles que pertenecen a la compra indicada.
        String sql = "SELECT id_detalle_compra, id_compra, id_producto, cantidad, costo_unitario, subtotal FROM detalle_compra WHERE id_compra = ?";
        // Creamos una lista vacia para guardar los resultados.
        List<DetalleCompra> detalles = new ArrayList<>();

        // Abrimos conexion y preparamos el SELECT con parametro.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Colocamos el id de la compra en el signo ?.
            ps.setInt(1, idCompra);
            // Ejecutamos la consulta.
            try (ResultSet rs = ps.executeQuery()) {
                // Recorremos todos los detalles de esa compra.
                while (rs.next()) {
                    // Convertimos cada fila en objeto y lo agregamos a la lista.
                    detalles.add(mapear(rs));
                }
            }
        // Si la consulta falla, lanzamos un error.
        } catch (SQLException e) {
            // El mensaje ayuda a ubicar que fallo al filtrar por compra.
            throw new RuntimeException("Error al obtener detalles por compra", e);
        }

        // Regresamos los detalles encontrados.
        return detalles;
    }

    /*
     * Verifica si una compra existe.
     * Recibe el id de la compra.
     * Devuelve true si existe, false si no.
     */
    public boolean existeCompra(int idCompra) {
        // Esta consulta cuenta cuantas compras existen con el id indicado.
        String sql = "SELECT COUNT(*) FROM compra WHERE id_compra = ?";

        // Abrimos conexion y preparamos la consulta.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Colocamos el id de compra en el signo ?.
            ps.setInt(1, idCompra);
            // Ejecutamos el SELECT de conteo.
            try (ResultSet rs = ps.executeQuery()) {
                // Si hay resultado y el conteo es mayor que 0, la compra existe.
                return rs.next() && rs.getInt(1) > 0;
            }
        // Si falla la validacion en base de datos, lanzamos error.
        } catch (SQLException e) {
            // Indicamos que el error ocurrio al validar la compra.
            throw new RuntimeException("Error al validar compra", e);
        }
    }

    /*
     * Verifica si un producto existe y esta activo.
     * Recibe el id del producto.
     * Devuelve true si el producto puede usarse en compras.
     */
    public boolean existeProducto(int idProducto) {
        // Esta consulta cuenta productos que coinciden con el id y que estan activos.
        String sql = "SELECT COUNT(*) FROM producto WHERE id_producto = ? AND activo = ?";

        // Abrimos conexion y preparamos la consulta.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Colocamos el id del producto en el primer signo ?.
            ps.setInt(1, idProducto);
            // Colocamos true para aceptar solo productos activos.
            ps.setBoolean(2, true);
            // Ejecutamos el conteo.
            try (ResultSet rs = ps.executeQuery()) {
                // Regresamos true si el conteo fue mayor que cero.
                return rs.next() && rs.getInt(1) > 0;
            }
        // Si SQL falla, lanzamos un error claro.
        } catch (SQLException e) {
            // El mensaje indica que el fallo fue validando el producto.
            throw new RuntimeException("Error al validar producto", e);
        }
    }

    /*
     * Recalcula y actualiza el total de una compra.
     * Recibe el id de la compra.
     * No devuelve nada.
     */
    public void actualizarTotalCompra(int idCompra) {
        // Esta consulta actualiza el total de compra sumando todos sus subtotales; COALESCE usa 0 si no hay detalles.
        String sql = "UPDATE compra SET total = (SELECT COALESCE(SUM(subtotal), 0) FROM detalle_compra WHERE id_compra = ?) WHERE id_compra = ?";

        // Abrimos conexion y preparamos el UPDATE.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Primer parametro: id para buscar los detalles que se van a sumar.
            ps.setInt(1, idCompra);
            // Segundo parametro: id de la compra que se va a actualizar.
            ps.setInt(2, idCompra);
            // Ejecutamos la actualizacion del total.
            ps.executeUpdate();
        // Si falla el recalculo, lanzamos un error.
        } catch (SQLException e) {
            // Conservamos el detalle tecnico para depurar.
            throw new RuntimeException("Error al recalcular total de compra", e);
        }
    }

    /*
     * Convierte una fila de ResultSet en un objeto DetalleCompra.
     * Recibe el ResultSet ya colocado en una fila valida.
     * Devuelve un DetalleCompra con los datos de esa fila.
     */
    private DetalleCompra mapear(ResultSet rs) throws SQLException {
        // Creamos y regresamos el detalle usando las columnas de la tabla.
        return new DetalleCompra(
                rs.getInt("id_detalle_compra"),
                rs.getInt("id_compra"),
                rs.getInt("id_producto"),
                rs.getInt("cantidad"),
                rs.getDouble("costo_unitario"),
                rs.getDouble("subtotal")
        );
    }
}
