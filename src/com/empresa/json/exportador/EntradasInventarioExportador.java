package com.empresa.json.exportador;

import com.empresa.Conexion;
import com.empresa.json.dto.EntradaInventarioDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Clase Service / Exportador.
 * Se encarga de obtener las entradas de inventario desde la base de datos
 * y escribirlas en un archivo JSON. En la arquitectura por capas actua como
 * servicio de integracion, porque conecta datos internos con un archivo externo.
 */
public class EntradasInventarioExportador {
    /*
     * Exporta todas las entradas de inventario.
     * Recibe la ruta donde se va a crear el archivo JSON.
     * No devuelve nada.
     */
    public void exportarTodas(Path rutaSalida) {
        // Primero obtiene las entradas y despues las escribe en la ruta indicada.
        escribirJson(obtenerEntradas(), rutaSalida);
    }

    /*
     * Obtiene las entradas de inventario desde la base de datos.
     * No recibe parametros.
     * Devuelve una lista de EntradaInventarioDto.
     */
    private List<EntradaInventarioDto> obtenerEntradas() {
        // Esta consulta une detalle_compra con compra para obtener productos comprados y la fecha de su compra.
        String sql = "SELECT dc.id_detalle_compra, dc.id_compra, dc.id_producto, dc.cantidad, dc.costo_unitario, c.fecha " +
                "FROM detalle_compra dc INNER JOIN compra c ON dc.id_compra = c.id_compra";
        // Creamos una lista vacia donde guardaremos las entradas encontradas.
        List<EntradaInventarioDto> entradas = new ArrayList<>();

        // Abrimos conexion, preparamos la consulta y la ejecutamos.
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            // Recorremos cada fila del resultado.
            while (rs.next()) {
                // Convertimos la fila en un DTO de entrada de inventario y lo agregamos a la lista.
                entradas.add(new EntradaInventarioDto(
                        rs.getInt("id_detalle_compra"),
                        rs.getInt("id_compra"),
                        rs.getInt("id_producto"),
                        rs.getInt("cantidad"),
                        rs.getDouble("costo_unitario"),
                        rs.getString("fecha")
                ));
            }
        // Si falla la consulta a la base de datos, lanzamos un error claro.
        } catch (SQLException e) {
            // El mensaje indica que el fallo fue al obtener datos para exportar.
            throw new RuntimeException("Error al obtener entradas de inventario", e);
        }

        // Regresamos la lista de entradas.
        return entradas;
    }

    /*
     * Escribe la lista de entradas en un archivo JSON.
     * Recibe la lista de entradas y la ruta de salida.
     * No devuelve nada.
     */
    private void escribirJson(List<EntradaInventarioDto> entradas, Path rutaSalida) {
        // Intentamos crear carpetas y escribir el archivo.
        try {
            // Obtenemos la carpeta padre de la ruta, por ejemplo "exportaciones".
            Path carpeta = rutaSalida.getParent();
            // Regla practica de negocio: si la carpeta no existe, se crea para que la exportacion no falle por falta de directorio.
            if (carpeta != null) {
                // Creamos la carpeta y sus padres si hacen falta.
                Files.createDirectories(carpeta);
            }

            // Creamos Gson con formato bonito para que el JSON sea facil de leer.
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            // Abrimos un escritor de archivo hacia la ruta indicada.
            try (FileWriter writer = new FileWriter(rutaSalida.toFile())) {
                // Convertimos la lista a JSON y la escribimos en el archivo.
                gson.toJson(entradas, writer);
            }
        // Si falla la escritura del archivo, lanzamos un error.
        } catch (IOException e) {
            // Indicamos que el problema ocurrio durante la exportacion.
            throw new RuntimeException("Error al exportar entradas_inventario.json", e);
        }
    }
}
