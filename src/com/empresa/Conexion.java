package com.empresa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Clase de soporte para la capa Repository.
 * Su trabajo es crear conexiones con MySQL para que los repositorios puedan
 * ejecutar consultas SQL. No es un Model, Service ni Controller principal;
 * acompaña a los Repository porque ellos necesitan conectarse a la base de datos.
 */
public class Conexion {
    // URL de conexion: indica el servidor, puerto y nombre de la base de datos.
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_empresa";
    // Usuario de MySQL que se usara para conectarse.
    private static final String USUARIO = "root";
    // Contrasena de MySQL; aqui esta vacia porque asi esta configurado el entorno local.
    private static final String PASSWORD = "";

    /*
     * Crea una conexion con MySQL.
     * No recibe parametros.
     * Devuelve un objeto Connection listo para ejecutar SQL.
     */
    public static Connection getConexion() {
        // Intentamos abrir la conexion con los datos configurados arriba.
        try {
            // DriverManager usa la URL, usuario y contrasena para conectarse a MySQL.
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
        // Si MySQL rechaza la conexion o hay otro problema, se captura aqui.
        } catch (SQLException e) {
            // Lanzamos un error mas facil de entender para el resto del programa.
            throw new RuntimeException("Error al conectar con MySQL", e);
        }
    }
}
