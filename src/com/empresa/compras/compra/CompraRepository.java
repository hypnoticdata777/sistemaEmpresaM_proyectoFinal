package com.empresa.compras.compra;

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
 * Se encarga de hablar directamente con la base de datos para guardar,
 * buscar y actualizar compras. En la arquitectura por capas, el Repository
 * es la unica capa de compras que deberia conocer SQL y conexiones.
 */
public class CompraRepository {
    /*
     * Guarda una compra en la base de datos.
     * Recibe un objeto Compra con los datos a insertar.
     * Devuelve el id generado por MySQL para esa compra.
     */
    public int guardar(Compra compra) {
        // Esta consulta inserta una compra nueva con proveedor, fecha, total y estado.
        String sql = "INSERT INTO compra (id_proveedor, fecha, total, estado) VALUES (?, ?, ?, ?)";

        // Abrimos la conexion y preparamos la consulta; RETURN_GENERATED_KEYS permite leer el id creado.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Colocamos el id del proveedor en el primer signo ?.
            ps.setInt(1, compra.getIdProveedor());
            // Colocamos la fecha en el segundo signo ?.
            ps.setString(2, compra.getFecha());
            // Colocamos el total en el tercer signo ?.
            ps.setDouble(3, compra.getTotal());
            // Colocamos el estado en el cuarto signo ?.
            ps.setString(4, compra.getEstado());
            // Ejecutamos el INSERT en la base de datos.
            ps.executeUpdate();

            // Pedimos a la base de datos las llaves generadas, o sea el id nuevo.
            try (ResultSet rs = ps.getGeneratedKeys()) {
                // Si existe una llave generada, significa que la compra se guardo bien.
                if (rs.next()) {
                    // Regresamos el primer valor, que es el id creado.
                    return rs.getInt(1);
                }
            }
        // Si ocurre un error de SQL, lo convertimos en RuntimeException para mostrar un mensaje claro.
        } catch (SQLException e) {
            // Lanzamos el error con contexto para saber en que operacion fallo.
            throw new RuntimeException("Error al guardar compra", e);
        }

        // Si no se pudo leer ningun id generado, regresamos 0 como valor por defecto.
        return 0;
    }

    /*
     * Obtiene todas las compras guardadas.
     * No recibe parametros.
     * Devuelve una lista de objetos Compra.
     */
    public List<Compra> obtenerTodos() {
        // Esta consulta trae las columnas principales de todas las compras.
        String sql = "SELECT id_compra, id_proveedor, fecha, total, estado FROM compra";
        // Creamos una lista vacia donde vamos a guardar las compras encontradas.
        List<Compra> compras = new ArrayList<>();

        // Abrimos conexion, creamos un Statement simple y ejecutamos el SELECT.
        try (Connection conexion = Conexion.getConexion();
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            // Recorremos cada fila que regreso la base de datos.
            while (rs.next()) {
                // Convertimos la fila en un objeto Compra y lo agregamos a la lista.
                compras.add(mapear(rs));
            }
        // Si falla la consulta, mostramos un error relacionado con obtener compras.
        } catch (SQLException e) {
            // Lanzamos una excepcion para que la capa superior sepa que algo salio mal.
            throw new RuntimeException("Error al obtener compras", e);
        }

        // Regresamos la lista, aunque este vacia si no habia compras.
        return compras;
    }

    /*
     * Busca una compra por su id.
     * Recibe el id de la compra.
     * Devuelve la compra si existe, o null si no se encontro.
     */
    public Compra buscarPorId(int idCompra) {
        // Esta consulta busca una sola compra usando su id.
        String sql = "SELECT id_compra, id_proveedor, fecha, total, estado FROM compra WHERE id_compra = ?";

        // Abrimos la conexion y preparamos la consulta con parametro.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Colocamos el id recibido en el signo ?.
            ps.setInt(1, idCompra);
            // Ejecutamos la consulta y guardamos el resultado.
            try (ResultSet rs = ps.executeQuery()) {
                // Si existe una fila, significa que si encontramos la compra.
                if (rs.next()) {
                    // Convertimos esa fila en objeto Compra y lo regresamos.
                    return mapear(rs);
                }
            }
        // Si falla la busqueda, lanzamos un error con mensaje claro.
        } catch (SQLException e) {
            // Enviamos el error original como causa para no perder detalle tecnico.
            throw new RuntimeException("Error al buscar compra", e);
        }

        // Si no hubo resultados, regresamos null para indicar "no encontrada".
        return null;
    }

    /*
     * Verifica si existe un proveedor activo.
     * Recibe el id del proveedor.
     * Devuelve true si existe y esta activo, false si no.
     */
    public boolean existeProveedor(int idProveedor) {
        // Esta consulta cuenta proveedores que coinciden con el id y que siguen activos.
        String sql = "SELECT COUNT(*) FROM Proveedor WHERE id_proveedor = ? AND activo = ?";

        // Abrimos la conexion y preparamos la consulta.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Colocamos el id del proveedor en el primer signo ?.
            ps.setInt(1, idProveedor);
            // Colocamos true en el segundo signo ? para buscar solo proveedores activos.
            ps.setBoolean(2, true);
            // Ejecutamos la consulta para obtener el conteo.
            try (ResultSet rs = ps.executeQuery()) {
                // Si hay resultado y el conteo es mayor que 0, el proveedor existe y esta activo.
                return rs.next() && rs.getInt(1) > 0;
            }
        // Si falla la validacion en base de datos, lanzamos un error entendible.
        } catch (SQLException e) {
            // Conservamos el error tecnico dentro de la excepcion.
            throw new RuntimeException("Error al validar proveedor", e);
        }
    }

    /*
     * Actualiza el estado de una compra.
     * Recibe el id de la compra y el nuevo estado.
     * No devuelve nada.
     */
    public void actualizarEstado(int idCompra, String estado) {
        // Esta consulta cambia el estado de una compra especifica.
        String sql = "UPDATE compra SET estado = ? WHERE id_compra = ?";

        // Abrimos la conexion y preparamos el UPDATE.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Colocamos el nuevo estado en el primer signo ?.
            ps.setString(1, estado);
            // Colocamos el id de la compra en el segundo signo ?.
            ps.setInt(2, idCompra);
            // Ejecutamos la actualizacion en la base de datos.
            ps.executeUpdate();
        // Si falla el UPDATE, lanzamos un error con contexto.
        } catch (SQLException e) {
            // Indicamos que el problema ocurrio al actualizar el estado.
            throw new RuntimeException("Error al actualizar estado de compra", e);
        }
    }

    /*
     * Actualiza el total de una compra.
     * Recibe el id de la compra y el total nuevo.
     * No devuelve nada.
     */
    public void actualizarTotal(int idCompra, double total) {
        // Esta consulta cambia el total de una compra especifica.
        String sql = "UPDATE compra SET total = ? WHERE id_compra = ?";

        // Abrimos la conexion y preparamos el UPDATE.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Colocamos el total nuevo en el primer signo ?.
            ps.setDouble(1, total);
            // Colocamos el id de la compra en el segundo signo ?.
            ps.setInt(2, idCompra);
            // Ejecutamos la actualizacion.
            ps.executeUpdate();
        // Si ocurre un problema con SQL, lanzamos un mensaje claro.
        } catch (SQLException e) {
            // Conservamos la causa original para depurar despues.
            throw new RuntimeException("Error al actualizar total de compra", e);
        }
    }

    /*
     * Calcula el total de una compra sumando sus detalles.
     * Recibe el id de la compra.
     * Devuelve la suma de los subtotales.
     */
    public double calcularTotal(int idCompra) {
        // Esta consulta suma los subtotales de los productos de una compra; COALESCE evita regresar null si no hay detalles.
        String sql = "SELECT COALESCE(SUM(subtotal), 0) FROM detalle_compra WHERE id_compra = ?";

        // Abrimos la conexion y preparamos la consulta.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Colocamos el id de la compra en el signo ?.
            ps.setInt(1, idCompra);
            // Ejecutamos el SELECT para obtener la suma.
            try (ResultSet rs = ps.executeQuery()) {
                // Si la consulta regreso una fila, leemos el total calculado.
                if (rs.next()) {
                    // Regresamos la primera columna, que es la suma de subtotales.
                    return rs.getDouble(1);
                }
            }
        // Si falla el calculo en la base de datos, lanzamos un error.
        } catch (SQLException e) {
            // Explicamos en que parte fallo para que sea mas facil corregir.
            throw new RuntimeException("Error al calcular total de compra", e);
        }

        // Si no se obtuvo ningun resultado, regresamos 0.
        return 0;
    }

    /*
     * Convierte una fila de la base de datos en un objeto Compra.
     * Recibe un ResultSet colocado en una fila valida.
     * Devuelve un objeto Compra con los datos de esa fila.
     */
    private Compra mapear(ResultSet rs) throws SQLException {
        // Creamos y regresamos una Compra usando cada columna del ResultSet.
        return new Compra(
                rs.getInt("id_compra"),
                rs.getInt("id_proveedor"),
                rs.getString("fecha"),
                rs.getDouble("total"),
                rs.getString("estado")
        );
    }
}
