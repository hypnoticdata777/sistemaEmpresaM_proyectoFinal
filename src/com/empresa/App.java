package com.empresa;

import com.empresa.compras.compra.CompraController;
import com.empresa.compras.detallecompra.DetalleCompraController;
import com.empresa.compras.proveedor.ProveedorController;
import com.empresa.json.exportador.EntradasInventarioExportador;

import java.nio.file.Paths;
import java.util.Scanner;

/*
 * Clase Controller / Presentacion de consola.
 * Es el punto de entrada del programa y muestra menus al usuario.
 * En la arquitectura por capas, App funciona como la capa que recibe datos
 * desde consola y llama a los Controllers correspondientes.
 */
public class App {
    // Controlador para trabajar con proveedores desde el menu.
    private static final ProveedorController proveedorController = new ProveedorController();
    // Controlador para trabajar con compras desde el menu.
    private static final CompraController compraController = new CompraController();
    // Controlador para trabajar con los productos agregados a una compra.
    private static final DetalleCompraController detalleCompraController = new DetalleCompraController();
    // Exportador que crea el archivo JSON de entradas de inventario.
    private static final EntradasInventarioExportador entradasExportador = new EntradasInventarioExportador();

    /*
     * Metodo principal del programa.
     * Recibe los argumentos de ejecucion en args, aunque aqui no se usan.
     * No devuelve nada; inicia el menu de consola.
     */
    public static void main(String[] args) {
        // Creamos Scanner para leer lo que el usuario escribe en la consola.
        Scanner scanner = new Scanner(System.in);
        // Declaramos la variable donde se guardara la opcion elegida.
        int opcion;

        // Repetimos el menu principal hasta que el usuario elija salir.
        do {
            // Mostramos el titulo del sistema.
            System.out.println("\n=== Sistema Empresa ===");
            // Mostramos la opcion del modulo de inventario.
            System.out.println("1. Inventario y catalogo");
            // Mostramos la opcion del modulo de compras.
            System.out.println("2. Compras y proveedores");
            // Mostramos la opcion del modulo de ventas.
            System.out.println("3. Ventas y clientes");
            // Mostramos la opcion del modulo de pedidos.
            System.out.println("4. Pedidos y envios");
            // Mostramos la opcion para importar o exportar JSON.
            System.out.println("5. Importacion y exportacion JSON");
            // Mostramos la opcion para terminar el programa.
            System.out.println("6. Salir");
            // Pedimos al usuario que escriba una opcion.
            System.out.print("Opcion: ");
            // Leemos un numero entero de forma segura.
            opcion = leerEntero(scanner);

            // Revisamos que opcion eligio el usuario.
            switch (opcion) {
                // Estas opciones todavia no estan implementadas en este equipo.
                case 1:
                case 3:
                case 4:
                    // Avisamos que ese modulo esta pendiente.
                    System.out.println("Modulo pendiente para el equipo correspondiente.");
                    // Salimos del switch para volver al menu.
                    break;
                // Si elige 2, entramos al menu de compras y proveedores.
                case 2:
                    // Llamamos al submenu de compras.
                    menuCompras(scanner);
                    // Salimos del switch al regresar del submenu.
                    break;
                // Si elige 5, exportamos las entradas de inventario.
                case 5:
                    // Generamos el archivo JSON en la carpeta exportaciones.
                    entradasExportador.exportarTodas(Paths.get("exportaciones", "entradas_inventario.json"));
                    // Avisamos al usuario que se genero el archivo.
                    System.out.println("Archivo exportaciones/entradas_inventario.json generado.");
                    // Salimos del switch.
                    break;
                // Si elige 6, el ciclo terminara.
                case 6:
                    // Mostramos mensaje de salida.
                    System.out.println("Saliendo...");
                    // Salimos del switch.
                    break;
                // Cualquier otro numero se considera invalido.
                default:
                    // Avisamos que la opcion no existe.
                    System.out.println("Opcion no valida.");
                    // Salimos del switch.
                    break;
            }
        // El menu se repite mientras la opcion no sea 6.
        } while (opcion != 6);

        // Cerramos el Scanner para liberar el recurso de lectura.
        scanner.close();
    }

    /*
     * Muestra el submenu de compras y proveedores.
     * Recibe el Scanner para seguir leyendo datos del usuario.
     * No devuelve nada.
     */
    private static void menuCompras(Scanner scanner) {
        // Variable donde se guardara la opcion del submenu.
        int opcion;
        // Repetimos el submenu hasta que el usuario elija volver.
        do {
            // Imprimimos el titulo del submenu.
            System.out.println("\n--- Compras y proveedores ---");
            // Opcion para registrar proveedor.
            System.out.println("1. Registrar proveedor");
            // Opcion para mostrar proveedores.
            System.out.println("2. Mostrar proveedores");
            // Opcion para buscar proveedor.
            System.out.println("3. Buscar proveedor");
            // Opcion para desactivar proveedor.
            System.out.println("4. Desactivar proveedor");
            // Opcion para registrar compra.
            System.out.println("5. Registrar compra");
            // Opcion para mostrar compras.
            System.out.println("6. Mostrar compras");
            // Opcion para buscar compra.
            System.out.println("7. Buscar compra");
            // Opcion para confirmar compra.
            System.out.println("8. Confirmar compra");
            // Opcion para cancelar compra.
            System.out.println("9. Cancelar compra");
            // Opcion para agregar producto a una compra.
            System.out.println("10. Agregar producto a compra");
            // Opcion para mostrar detalles de compra.
            System.out.println("11. Mostrar detalles de compra");
            // Opcion para exportar el JSON de entradas.
            System.out.println("12. Exportar entradas_inventario.json");
            // Opcion para volver al menu principal.
            System.out.println("0. Volver");
            // Pedimos al usuario que escriba una opcion.
            System.out.print("Opcion: ");
            // Leemos la opcion como entero.
            opcion = leerEntero(scanner);

            // Intentamos ejecutar la opcion elegida.
            try {
                // Mandamos la opcion y el scanner al metodo que contiene el switch.
                ejecutarOpcionCompras(opcion, scanner);
            // Capturamos errores de validacion o de base de datos para que el programa no se cierre.
            } catch (RuntimeException e) {
                // Mostramos solamente el mensaje del error al usuario.
                System.out.println("Error: " + e.getMessage());
            }
        // Seguimos en este submenu mientras la opcion no sea 0.
        } while (opcion != 0);
    }

    /*
     * Ejecuta la opcion elegida dentro del menu de compras.
     * Recibe la opcion numerica y el Scanner para pedir datos extra.
     * No devuelve nada.
     */
    private static void ejecutarOpcionCompras(int opcion, Scanner scanner) {
        // Revisamos cual opcion del submenu se eligio.
        switch (opcion) {
            // Opcion 1: registrar proveedor.
            case 1:
                // Pedimos datos y registramos proveedor.
                registrarProveedor(scanner);
                // Terminamos este caso.
                break;
            // Opcion 2: mostrar proveedores.
            case 2:
                // Pedimos proveedores al controlador y los imprimimos uno por uno.
                proveedorController.mostrarProveedores().forEach(System.out::println);
                // Terminamos este caso.
                break;
            // Opcion 3: buscar proveedor.
            case 3:
                // Pedimos el id del proveedor.
                System.out.print("ID proveedor: ");
                // Buscamos el proveedor y lo imprimimos.
                System.out.println(proveedorController.buscarProveedor(leerEntero(scanner)));
                // Terminamos este caso.
                break;
            // Opcion 4: desactivar proveedor.
            case 4:
                // Pedimos el id del proveedor.
                System.out.print("ID proveedor: ");
                // Desactivamos el proveedor indicado.
                proveedorController.desactivarProveedor(leerEntero(scanner));
                // Avisamos que se desactivo correctamente.
                System.out.println("Proveedor desactivado.");
                // Terminamos este caso.
                break;
            // Opcion 5: registrar compra.
            case 5:
                // Pedimos datos y registramos la compra.
                registrarCompra(scanner);
                // Terminamos este caso.
                break;
            // Opcion 6: mostrar compras.
            case 6:
                // Pedimos compras al controlador y las imprimimos.
                compraController.mostrarCompras().forEach(System.out::println);
                // Terminamos este caso.
                break;
            // Opcion 7: buscar compra.
            case 7:
                // Pedimos el id de la compra.
                System.out.print("ID compra: ");
                // Buscamos la compra y la imprimimos.
                System.out.println(compraController.buscarCompra(leerEntero(scanner)));
                // Terminamos este caso.
                break;
            // Opcion 8: confirmar compra.
            case 8:
                // Pedimos el id de la compra.
                System.out.print("ID compra: ");
                // Cambiamos la compra a estado confirmada.
                compraController.confirmarCompra(leerEntero(scanner));
                // Avisamos que se confirmo.
                System.out.println("Compra confirmada.");
                // Terminamos este caso.
                break;
            // Opcion 9: cancelar compra.
            case 9:
                // Pedimos el id de la compra.
                System.out.print("ID compra: ");
                // Cambiamos la compra a estado cancelada.
                compraController.cancelarCompra(leerEntero(scanner));
                // Avisamos que se cancelo.
                System.out.println("Compra cancelada.");
                // Terminamos este caso.
                break;
            // Opcion 10: agregar producto a compra.
            case 10:
                // Pedimos datos del producto comprado y lo agregamos.
                agregarProductoACompra(scanner);
                // Terminamos este caso.
                break;
            // Opcion 11: mostrar detalles de compra.
            case 11:
                // Pedimos detalles al controlador y los imprimimos.
                detalleCompraController.mostrarDetalles().forEach(System.out::println);
                // Terminamos este caso.
                break;
            // Opcion 12: exportar archivo JSON.
            case 12:
                // Generamos el archivo JSON en la carpeta exportaciones.
                entradasExportador.exportarTodas(Paths.get("exportaciones", "entradas_inventario.json"));
                // Avisamos que el archivo fue generado.
                System.out.println("Archivo exportaciones/entradas_inventario.json generado.");
                // Terminamos este caso.
                break;
            // Opcion 0: volver al menu anterior.
            case 0:
                // No hacemos nada porque el ciclo del submenu se encargara de salir.
                break;
            // Cualquier otra opcion no existe.
            default:
                // Avisamos al usuario que la opcion no es valida.
                System.out.println("Opcion no valida.");
                // Terminamos el caso default.
                break;
        }
    }

    /*
     * Pide datos al usuario para registrar un proveedor.
     * Recibe el Scanner para leer desde consola.
     * No devuelve nada.
     */
    private static void registrarProveedor(Scanner scanner) {
        // Pedimos el nombre.
        System.out.print("Nombre: ");
        // Leemos el nombre escrito por el usuario.
        String nombre = scanner.nextLine();
        // Pedimos el RFC.
        System.out.print("RFC: ");
        // Leemos el RFC.
        String rfc = scanner.nextLine();
        // Pedimos el telefono.
        System.out.print("Telefono: ");
        // Leemos el telefono.
        String telefono = scanner.nextLine();
        // Pedimos el correo.
        System.out.print("Correo: ");
        // Leemos el correo.
        String correo = scanner.nextLine();
        // Pedimos la direccion.
        System.out.print("Direccion: ");
        // Leemos la direccion.
        String direccion = scanner.nextLine();
        // Mandamos los datos al controlador para que registre el proveedor.
        proveedorController.registrarProveedor(nombre, rfc, telefono, correo, direccion);
        // Avisamos que el proveedor se registro.
        System.out.println("Proveedor registrado.");
    }

    /*
     * Pide datos al usuario para registrar una compra.
     * Recibe el Scanner para leer desde consola.
     * No devuelve nada.
     */
    private static void registrarCompra(Scanner scanner) {
        // Pedimos el id del proveedor que hara la venta.
        System.out.print("ID proveedor: ");
        // Leemos el id como entero.
        int idProveedor = leerEntero(scanner);
        // Pedimos la fecha de la compra.
        System.out.print("Fecha: ");
        // Leemos la fecha como texto.
        String fecha = scanner.nextLine();
        // Registramos la compra y guardamos el id generado.
        int idCompra = compraController.registrarCompra(idProveedor, fecha);
        // Mostramos al usuario el id de la compra nueva.
        System.out.println("Compra registrada con ID: " + idCompra);
    }

    /*
     * Pide datos al usuario para agregar un producto a una compra.
     * Recibe el Scanner para leer desde consola.
     * No devuelve nada.
     */
    private static void agregarProductoACompra(Scanner scanner) {
        // Pedimos el id de la compra.
        System.out.print("ID compra: ");
        // Leemos el id de la compra.
        int idCompra = leerEntero(scanner);
        // Pedimos el id del producto.
        System.out.print("ID producto: ");
        // Leemos el id del producto.
        int idProducto = leerEntero(scanner);
        // Pedimos la cantidad comprada.
        System.out.print("Cantidad: ");
        // Leemos la cantidad.
        int cantidad = leerEntero(scanner);
        // Pedimos el costo por unidad.
        System.out.print("Costo unitario: ");
        // Leemos el costo como numero decimal.
        double costoUnitario = leerDouble(scanner);
        // Mandamos los datos al controlador para agregar el producto a la compra.
        detalleCompraController.agregarProductoACompra(idCompra, idProducto, cantidad, costoUnitario);
        // Avisamos que el producto se agrego y el total se actualizo.
        System.out.println("Producto agregado y total recalculado.");
    }

    /*
     * Lee un numero entero desde consola.
     * Recibe el Scanner.
     * Devuelve el entero escrito por el usuario.
     */
    private static int leerEntero(Scanner scanner) {
        // Regla practica: mientras el usuario no escriba un entero, no dejamos avanzar para evitar errores de captura.
        while (!scanner.hasNextInt()) {
            // Pedimos un numero valido.
            System.out.print("Ingresa un numero valido: ");
            // Limpiamos la entrada incorrecta.
            scanner.nextLine();
        }
        // Leemos el entero correcto.
        int valor = scanner.nextInt();
        // Limpiamos el salto de linea que queda despues de nextInt.
        scanner.nextLine();
        // Regresamos el valor leido.
        return valor;
    }

    /*
     * Lee un numero decimal desde consola.
     * Recibe el Scanner.
     * Devuelve el double escrito por el usuario.
     */
    private static double leerDouble(Scanner scanner) {
        // Regla practica: mientras el usuario no escriba un decimal valido, no dejamos continuar.
        while (!scanner.hasNextDouble()) {
            // Pedimos un numero valido.
            System.out.print("Ingresa un numero valido: ");
            // Limpiamos la entrada incorrecta.
            scanner.nextLine();
        }
        // Leemos el numero decimal correcto.
        double valor = scanner.nextDouble();
        // Limpiamos el salto de linea que queda despues de nextDouble.
        scanner.nextLine();
        // Regresamos el valor leido.
        return valor;
    }
}
