package com.empresa.compras.proveedor;

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
 * Se encarga de guardar, buscar y actualizar proveedores en la base de datos.
 * En la arquitectura por capas, esta clase es la encargada de usar SQL
 * para la tabla Proveedor.
 */
public class ProveedorRepository {
    /*
     * Guarda un proveedor nuevo.
     * Recibe un objeto Proveedor con todos sus datos.
     * No devuelve nada.
     */
    public void guardar(Proveedor proveedor) {
        // Esta consulta inserta un proveedor con datos de contacto y estado activo.
        String sql = "INSERT INTO Proveedor (nombre, rfc, telefono, correo, direccion, activo) VALUES (?, ?, ?, ?, ?, ?)";

        // Abrimos conexion y preparamos el INSERT con parametros.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Colocamos el nombre en el primer signo ?.
            ps.setString(1, proveedor.getNombre());
            // Colocamos el RFC en el segundo signo ?.
            ps.setString(2, proveedor.getRfc());
            // Colocamos el telefono en el tercer signo ?.
            ps.setString(3, proveedor.getTelefono());
            // Colocamos el correo en el cuarto signo ?.
            ps.setString(4, proveedor.getCorreo());
            // Colocamos la direccion en el quinto signo ?.
            ps.setString(5, proveedor.getDireccion());
            // Colocamos si el proveedor esta activo en el sexto signo ?.
            ps.setBoolean(6, proveedor.isActivo());
            // Ejecutamos el INSERT.
            ps.executeUpdate();
        // Si SQL falla, lanzamos un error con mensaje claro.
        } catch (SQLException e) {
            // Indicamos que el problema ocurrio al guardar el proveedor.
            throw new RuntimeException("Error al guardar proveedor", e);
        }
    }

    /*
     * Obtiene todos los proveedores.
     * No recibe parametros.
     * Devuelve una lista de proveedores.
     */
    public List<Proveedor> obtenerTodos() {
        // Esta consulta trae todas las columnas importantes de todos los proveedores.
        String sql = "SELECT id_proveedor, nombre, rfc, telefono, correo, direccion, activo FROM Proveedor";
        // Creamos una lista vacia para guardar los proveedores encontrados.
        List<Proveedor> proveedores = new ArrayList<>();

        // Abrimos conexion, ejecutamos el SELECT y recibimos resultados.
        try (Connection conexion = Conexion.getConexion();
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            // Recorremos cada fila devuelta por la base de datos.
            while (rs.next()) {
                // Convertimos la fila en Proveedor y la agregamos a la lista.
                proveedores.add(mapear(rs));
            }
        // Si ocurre error en SQL, lanzamos una excepcion clara.
        } catch (SQLException e) {
            // Conservamos la causa tecnica dentro del error.
            throw new RuntimeException("Error al obtener proveedores", e);
        }

        // Regresamos la lista de proveedores.
        return proveedores;
    }

    /*
     * Busca un proveedor por id.
     * Recibe el id del proveedor.
     * Devuelve el proveedor si existe, o null si no existe.
     */
    public Proveedor buscarPorId(int idProveedor) {
        // Esta consulta busca un proveedor especifico usando su id.
        String sql = "SELECT id_proveedor, nombre, rfc, telefono, correo, direccion, activo FROM Proveedor WHERE id_proveedor = ?";

        // Abrimos conexion y preparamos la consulta.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Colocamos el id recibido en el signo ?.
            ps.setInt(1, idProveedor);
            // Ejecutamos el SELECT.
            try (ResultSet rs = ps.executeQuery()) {
                // Si hay una fila, significa que el proveedor existe.
                if (rs.next()) {
                    // Convertimos esa fila en objeto Proveedor.
                    return mapear(rs);
                }
            }
        // Si falla la busqueda, lanzamos error.
        } catch (SQLException e) {
            // Indicamos que el fallo ocurrio al buscar proveedor.
            throw new RuntimeException("Error al buscar proveedor", e);
        }

        // Si no hubo resultados, regresamos null.
        return null;
    }

    /*
     * Verifica si ya existe un RFC registrado.
     * Recibe el RFC como texto.
     * Devuelve true si ya existe, false si no.
     */
    public boolean existeRfc(String rfc) {
        // Esta consulta cuenta proveedores que tienen exactamente el RFC indicado.
        String sql = "SELECT COUNT(*) FROM Proveedor WHERE rfc = ?";

        // Abrimos conexion y preparamos la consulta.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Colocamos el RFC en el signo ?.
            ps.setString(1, rfc);
            // Ejecutamos la consulta de conteo.
            try (ResultSet rs = ps.executeQuery()) {
                // Si el conteo es mayor que 0, ese RFC ya esta registrado.
                return rs.next() && rs.getInt(1) > 0;
            }
        // Si falla la validacion del RFC, lanzamos error.
        } catch (SQLException e) {
            // El mensaje ayuda a identificar la operacion que fallo.
            throw new RuntimeException("Error al validar RFC", e);
        }
    }

    /*
     * Desactiva un proveedor.
     * Recibe el id del proveedor.
     * No devuelve nada.
     */
    public void desactivar(int idProveedor) {
        // Esta consulta cambia activo a false para el proveedor indicado.
        String sql = "UPDATE Proveedor SET activo = ? WHERE id_proveedor = ?";

        // Abrimos conexion y preparamos el UPDATE.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            // Colocamos false para marcar al proveedor como inactivo.
            ps.setBoolean(1, false);
            // Colocamos el id del proveedor que se va a desactivar.
            ps.setInt(2, idProveedor);
            // Ejecutamos la actualizacion.
            ps.executeUpdate();
        // Si falla el UPDATE, lanzamos error.
        } catch (SQLException e) {
            // Indicamos que el fallo ocurrio al desactivar proveedor.
            throw new RuntimeException("Error al desactivar proveedor", e);
        }
    }

    /*
     * Convierte una fila de la base de datos en un objeto Proveedor.
     * Recibe un ResultSet colocado en una fila valida.
     * Devuelve un objeto Proveedor.
     */
    private Proveedor mapear(ResultSet rs) throws SQLException {
        // Creamos y regresamos un proveedor usando las columnas del ResultSet.
        return new Proveedor(
                rs.getInt("id_proveedor"),
                rs.getString("nombre"),
                rs.getString("rfc"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getString("direccion"),
                rs.getBoolean("activo")
        );
    }
}
