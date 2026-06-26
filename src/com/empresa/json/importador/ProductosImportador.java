package com.empresa.json.importador;

import com.empresa.json.dto.ProductoDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/*
 * Clase Service / Importador.
 * Se encarga de leer productos desde un archivo JSON y convertirlos en objetos.
 * En la arquitectura por capas funciona como servicio de integracion con datos
 * externos, porque trae informacion de un archivo al sistema.
 */
public class ProductosImportador {
    /*
     * Importa productos desde un archivo JSON.
     * Recibe la ruta del archivo productos.json.
     * Devuelve una lista de ProductoDto; si no hay archivo, devuelve lista vacia.
     */
    public List<ProductoDto> importar(Path rutaProductosJson) {
        // Regla practica de negocio: si no hay ruta o archivo, regresamos lista vacia para no romper el flujo del sistema.
        if (rutaProductosJson == null || !Files.exists(rutaProductosJson)) {
            // Collections.emptyList crea una lista vacia segura.
            return Collections.emptyList();
        }

        // Definimos el tipo "lista de ProductoDto" para que Gson sepa como leer el JSON.
        Type tipoLista = new TypeToken<List<ProductoDto>>() {
        // Este bloque vacio permite que TypeToken capture el tipo generico.
        }.getType();

        // Abrimos el archivo JSON para lectura.
        try (FileReader reader = new FileReader(rutaProductosJson.toFile())) {
            // Convertimos el contenido JSON en una lista de productos.
            List<ProductoDto> productos = new Gson().fromJson(reader, tipoLista);
            // Si Gson regresa null, devolvemos lista vacia; si no, devolvemos los productos.
            return productos == null ? Collections.emptyList() : productos;
        // Si ocurre un error leyendo el archivo, lanzamos una excepcion.
        } catch (IOException e) {
            // Indicamos que el problema fue al leer productos.json.
            throw new RuntimeException("Error al leer productos.json", e);
        }
    }

    /*
     * Verifica si existe un producto activo dentro del JSON.
     * Recibe el id del producto y la ruta del archivo JSON.
     * Devuelve true si encuentra un producto con ese id y activo.
     */
    public boolean existeProductoActivo(int idProducto, Path rutaProductosJson) {
        // Importamos la lista y la recorremos como stream.
        return importar(rutaProductosJson).stream()
                // Buscamos al menos un producto con el mismo id y con activo en true.
                .anyMatch(producto -> producto.getIdProducto() == idProducto && producto.isActivo());
    }
}
