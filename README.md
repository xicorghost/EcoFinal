## 1. Creación del proyecto con Spring Initializr


### 2. ADVERTENCIA
-si no funciona localhost:8080 al ejecutar el sever/programa en 
  aplication.properties cambia a localhost:3306 pero despues en 
  postman las peticiones deben ser localhost:8080.


## 3. Endpoints para pruebas (Postman o navegador)

### 3.1 Swagger

- Pasos para acceder a Swagger UI:
  - Ejecuta tu aplicación Spring Boot

    mvn spring-boot:run

  -O desde tu IDE ejecutando la clase principal con @SpringBootApplication
- Accede a Swagger UI en tu navegador
- Una vez que la aplicación esté corriendo, ve a:

  http://localhost:8080/swagger-ui/index.html

- URLs alternativas (por si la primera no funciona):

  http://localhost:8080/swagger-ui.html

  http://localhost:8080/swagger-ui/

- Para ver la documentación en formato JSON:

  http://localhost:8080/v3/api-docs

- ¿Qué verás?
  Una vez que accedas, verás una interfaz web similar a la de tu imagen donde podrás:
  Ver todos tus endpoints organizados por controladores.

  Probar directamente los endpoints desde la interfaz.

  Ver la documentación automática de tus APIs.

  Explorar los modelos de datos (entidades).

- Si no funciona, verifica:
  Puerto: Asegúrate de que tu app corre en el puerto 8080 (o cambia el puerto en la URL).

  Consola: Revisa que no haya errores al iniciar la aplicación.

  Dependencia: Confirma que tienes springdoc-openapi-starter-webmvc-ui en tu pom.xml (ya la tienes).


### 3.2 Servico de notificacion EN LA CARPETA PROVEEDOR

- OJO DEBE DE EXISTIR UN PROVEEDOR 
 Y tambien hice un servicio de notificacion

- Consultas Postman para API Proveedores
  CRUD PROVEEDORES

- 1.Obtener todos los proveedores

  GET http://localhost:8081/proveedores

  Método: GET

  Headers: Content-Type: application/json

  Body: Ninguno

- 2.Crear nuevo proveedor

  POST http://localhost:8081/proveedores

  Método: POST

  Headers: Content-Type: application/json

  Body (JSON):

  json
  {
      "nombre": "Proveedor ABC",
      "correo": "proveedor@abc.com",
      "telefono": "+56912345678"
  }

- 3.Obtener proveedor por ID

  GET http://localhost:8081/proveedores/1

  Método: GET

  Headers: Content-Type: application/json

  Body: Ninguno

  Nota: Cambiar 1 por el ID del proveedor que quieras consultar

4. Actualizar proveedor

  PUT http://localhost:8081/proveedores/1

  Método: PUT

  Headers: Content-Type: application/json

  Body (JSON):

  json
  {
      "nombre": "Proveedor ABC Actualizado",
      "correo": "nuevo@abc.com",
      "telefono": "+56987654321"
  }
  Nota: Cambiar 1 por el ID del proveedor que quieras actualizar

- 5.Eliminar proveedor

  DELETE http://localhost:8080/proveedores/1

  Método: DELETE

  Headers: Content-Type: application/json

  Body: Ninguno

  Nota: Cambiar 1 por el ID del proveedor que quieras eliminar

### 3.3 NOTIFICACIONES


- 1.Notificar al gerente

  POST http://localhost:8081/proveedores/notificar/1

  Método: POST

  Headers: Content-Type: application/json

  Body (JSON):

  json
  {
      "mensaje": "El proveedor necesita atención urgente. Revisar inventario."
  }

  Nota: Cambiar 1 por el ID del proveedor

- 2.Notificar al proveedor

  POST http://localhost:8081/proveedores/notificar-proveedor/1

  Método: POST

  Headers: Content-Type: application/json

  Body (JSON):

  json
  {
      "mensaje": "Su pedido ha sido procesado. Favor confirmar recepción."
  }

  Nota: Cambiar 1 por el ID del proveedor

## 4. HISTORIAL DE NOTIFICACIONES

- 1.Obtener todas las notificaciones

  GET http://localhost:8080/proveedores/notificaciones

  Método: GET

  Headers: Content-Type: application/json

  Body: Ninguno



### Proveedor 
- Obtener todos los prooveedores
  GET http://localhost:8080/proveedores

- Obtener proveedor por id
  GET http://localhost:8080/proveedores/{idP}

- Crear proveedores
  POST http://localhost:8080/proveedores

  Ejemplos para crear proveedores (POST):

{
    "idP": 1,
    "nombre": "Ana García",
    "correo": "ana.garcia@example.com",
    "telefono": "987-654-3210"
  }

  {
    "idP": 2,
    "nombre": "Juan Pérez",
    "correo": "juan.perez@example.com",
    "telefono": "123-456-7890"
  }

  {
    "idP": 3,
    "nombre": "María López",
    "correo": "maria.lopez@example.com",
    "telefono": "555-123-4567"
  }

  - Actualizar proveedor
    PUT http://localhost:8080/proveedores/{idP}


  - Eliminar proveedor
    DELETE http://localhost:8080/proveedores/{idP}

### Productos
- Obtener todos los productos
  GET http://localhost:8080/productos


- Obtener producto por id
  GET http://localhost:8080/productos/{id}


- Crear productos
  POST http://localhost:8080/productos

  Ejemplos para crear productos (POST):


{
  "id": 1,
  "nombre": "Laptop",
  "stock": 15,
  "precio": 999.99,
  "categoria": "Electrónica"
}

{
    "id": 2,
    "nombre": "Smartphone",
    "stock": 30,
    "precio": 499.99,
    "categoria": "Electrónica"
  },


  {
    "id": 3,
    "nombre": "Silla de oficina",
    "stock": 20,
    "precio": 149.50,
    "categoria": "Muebles"
  },


  {
    "id": 4,
    "nombre": "Botella térmica",
    "stock": 50,
    "precio": 19.99,
    "categoria": "Hogar"
  },

  - Actualizar producto
    PUT http://localhost:8080/productos/{id}


  - Eliminar producto
    DELETE http://localhost:8080/productos/{id}


  ### 6. Cómo probar los endpoints en Postman
  -Crear una nueva petición.

  -Elegir el método HTTP (GET, POST, PUT, DELETE).

  -Escribir la URL correspondiente.

  -Para POST y PUT, ir a la pestaña Body, seleccionar raw y JSON, y pegar uno de los ejemplos JSON.

  -Hacer clic en Send y revisar la respuesta.
