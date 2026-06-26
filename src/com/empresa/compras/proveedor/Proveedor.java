package com.empresa.compras.proveedor;

/*
 * Clase Modelo.
 * Representa a un proveedor de la empresa: sus datos de contacto,
 * identificacion fiscal y si esta activo. En la arquitectura por capas,
 * el Modelo solo guarda informacion para moverla entre Controller, Service
 * y Repository.
 *
 * Clave para exposicion:
 * Esta clase no valida ni guarda en base de datos. Su responsabilidad es ser
 * el "molde" del proveedor. Las reglas estan en ProveedorService y el SQL esta
 * en ProveedorRepository. Separarlo asi evita mezclar datos, reglas y base de datos.
 */
public class Proveedor {
    // Identificador unico del proveedor en la base de datos.
    private int idProveedor;
    // Nombre comercial o razon social del proveedor.
    private String nombre;
    // RFC del proveedor, usado para identificarlo fiscalmente.
    private String rfc;
    // Telefono de contacto del proveedor.
    private String telefono;
    // Correo electronico del proveedor.
    private String correo;
    // Direccion fisica del proveedor.
    private String direccion;
    // Indica si el proveedor esta disponible para operaciones.
    private boolean activo;

    /*
     * Constructor vacio.
     * No recibe parametros y no devuelve nada.
     * Permite crear un proveedor y llenar sus datos despues.
     */
    public Proveedor() {
        // No se asigna nada aqui; el objeto queda con valores por defecto.
    }

    /*
     * Constructor completo.
     * Recibe todos los datos del proveedor.
     * No devuelve nada porque los constructores no retornan valores.
     */
    public Proveedor(int idProveedor, String nombre, String rfc, String telefono, String correo, String direccion, boolean activo) {
        // Guardamos el id del proveedor.
        this.idProveedor = idProveedor;
        // Guardamos el nombre recibido.
        this.nombre = nombre;
        // Guardamos el RFC recibido.
        this.rfc = rfc;
        // Guardamos el telefono recibido.
        this.telefono = telefono;
        // Guardamos el correo recibido.
        this.correo = correo;
        // Guardamos la direccion recibida.
        this.direccion = direccion;
        // Guardamos si el proveedor esta activo o no.
        this.activo = activo;
    }

    /*
     * Devuelve el id del proveedor.
     * No recibe parametros.
     * Retorna un entero.
     */
    public int getIdProveedor() {
        // Regresamos el identificador del proveedor.
        return idProveedor;
    }

    /*
     * Cambia el id del proveedor.
     * Recibe el nuevo id y no devuelve nada.
     */
    public void setIdProveedor(int idProveedor) {
        // Actualizamos el id del proveedor.
        this.idProveedor = idProveedor;
    }

    /*
     * Devuelve el nombre del proveedor.
     * No recibe parametros.
     * Retorna un texto.
     */
    public String getNombre() {
        // Regresamos el nombre guardado.
        return nombre;
    }

    /*
     * Cambia el nombre del proveedor.
     * Recibe el nuevo nombre y no devuelve nada.
     */
    public void setNombre(String nombre) {
        // Actualizamos el nombre del proveedor.
        this.nombre = nombre;
    }

    /*
     * Devuelve el RFC del proveedor.
     * No recibe parametros.
     * Retorna un texto.
     */
    public String getRfc() {
        // Regresamos el RFC guardado.
        return rfc;
    }

    /*
     * Cambia el RFC del proveedor.
     * Recibe el nuevo RFC y no devuelve nada.
     */
    public void setRfc(String rfc) {
        // Actualizamos el RFC del proveedor.
        this.rfc = rfc;
    }

    /*
     * Devuelve el telefono del proveedor.
     * No recibe parametros.
     * Retorna un texto.
     */
    public String getTelefono() {
        // Regresamos el telefono guardado.
        return telefono;
    }

    /*
     * Cambia el telefono del proveedor.
     * Recibe el nuevo telefono y no devuelve nada.
     */
    public void setTelefono(String telefono) {
        // Actualizamos el telefono del proveedor.
        this.telefono = telefono;
    }

    /*
     * Devuelve el correo del proveedor.
     * No recibe parametros.
     * Retorna un texto.
     */
    public String getCorreo() {
        // Regresamos el correo guardado.
        return correo;
    }

    /*
     * Cambia el correo del proveedor.
     * Recibe el nuevo correo y no devuelve nada.
     */
    public void setCorreo(String correo) {
        // Actualizamos el correo del proveedor.
        this.correo = correo;
    }

    /*
     * Devuelve la direccion del proveedor.
     * No recibe parametros.
     * Retorna un texto.
     */
    public String getDireccion() {
        // Regresamos la direccion guardada.
        return direccion;
    }

    /*
     * Cambia la direccion del proveedor.
     * Recibe la nueva direccion y no devuelve nada.
     */
    public void setDireccion(String direccion) {
        // Actualizamos la direccion del proveedor.
        this.direccion = direccion;
    }

    /*
     * Indica si el proveedor esta activo.
     * No recibe parametros.
     * Devuelve true si esta activo y false si no.
     */
    public boolean isActivo() {
        // Regresamos el valor del atributo activo.
        return activo;
    }

    /*
     * Cambia el estado activo del proveedor.
     * Recibe true o false y no devuelve nada.
     */
    public void setActivo(boolean activo) {
        // Actualizamos si el proveedor esta activo.
        this.activo = activo;
    }

    /*
     * Convierte el proveedor en texto.
     * No recibe parametros.
     * Devuelve un String con sus datos principales.
     */
    @Override
    public String toString() {
        // Construimos un texto para imprimir el proveedor en consola.
        return "Proveedor{" +
                "idProveedor=" + idProveedor +
                ", nombre='" + nombre + '\'' +
                ", rfc='" + rfc + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", activo=" + activo +
                '}';
    }
}
