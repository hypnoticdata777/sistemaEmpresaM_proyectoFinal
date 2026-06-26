# Guia breve para explicar Equipo 2

Nos toco el modulo de compras y proveedores.

El flujo respeta arquitectura por capas:

```text
App
Controller
Service
Repository
MySQL
```

Proveedor valida nombre, RFC, RFC no repetido, correo con `@` y telefono.

Compra valida proveedor existente, fecha obligatoria, estado permitido y total no negativo.

DetalleCompra valida compra existente, producto existente, cantidad mayor que cero, costo mayor que cero y calcula el subtotal automaticamente.

Nuestro JSON obligatorio es `entradas_inventario.json`, que se genera con tipo `ENTRADA` para que Equipo 1 actualice inventario.
