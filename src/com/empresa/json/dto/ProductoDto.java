package com.empresa.json.dto;

/*
 * Clase Modelo / DTO.
 * Representa un producto leido desde productos.json. En la arquitectura por
 * capas funciona como Modelo de transporte para traer datos externos al sistema,
 * sin guardar reglas de negocio ni consultas SQL.
 */
public class ProductoDto {
    // Identificador del producto.
    private int idProducto;
    // Nombre del producto.
    private String nombre;
    // Descripcion breve del producto.
    private String descripcion;
    // Precio del producto.
    private double precio;
    // Cantidad disponible en inventario.
    private int stock;
    // Identificador de la categoria.
    private int idCategoria;
    // Nombre de la categoria.
    private String nombreCategoria;
    // Identificador del almacen.
    private int idAlmacen;
    // Nombre del almacen.
    private String nombreAlmacen;
    // Indica si el producto esta activo y se puede usar.
    private boolean activo;

    /*
     * Devuelve el id del producto.
     * No recibe parametros.
     * Retorna un entero.
     */
    public int getIdProducto() {
        // Regresamos el identificador del producto.
        return idProducto;
    }

    /*
     * Devuelve el nombre del producto.
     * No recibe parametros.
     * Retorna un texto.
     */
    public String getNombre() {
        // Regresamos el nombre.
        return nombre;
    }

    /*
     * Devuelve la descripcion del producto.
     * No recibe parametros.
     * Retorna un texto.
     */
    public String getDescripcion() {
        // Regresamos la descripcion.
        return descripcion;
    }

    /*
     * Devuelve el precio del producto.
     * No recibe parametros.
     * Retorna un numero decimal.
     */
    public double getPrecio() {
        // Regresamos el precio.
        return precio;
    }

    /*
     * Devuelve el stock del producto.
     * No recibe parametros.
     * Retorna un entero.
     */
    public int getStock() {
        // Regresamos las unidades disponibles.
        return stock;
    }

    /*
     * Devuelve el id de la categoria.
     * No recibe parametros.
     * Retorna un entero.
     */
    public int getIdCategoria() {
        // Regresamos el identificador de categoria.
        return idCategoria;
    }

    /*
     * Devuelve el nombre de la categoria.
     * No recibe parametros.
     * Retorna un texto.
     */
    public String getNombreCategoria() {
        // Regresamos el nombre de la categoria.
        return nombreCategoria;
    }

    /*
     * Devuelve el id del almacen.
     * No recibe parametros.
     * Retorna un entero.
     */
    public int getIdAlmacen() {
        // Regresamos el identificador del almacen.
        return idAlmacen;
    }

    /*
     * Devuelve el nombre del almacen.
     * No recibe parametros.
     * Retorna un texto.
     */
    public String getNombreAlmacen() {
        // Regresamos el nombre del almacen.
        return nombreAlmacen;
    }

    /*
     * Indica si el producto esta activo.
     * No recibe parametros.
     * Devuelve true si el producto se puede usar y false si no.
     */
    public boolean isActivo() {
        // Regresamos el estado activo del producto.
        return activo;
    }
}
