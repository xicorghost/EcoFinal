## 1. Creación del proyecto con Spring Initializr


lol


## 2. Endpoints para pruebas (Postman o navegador)

### 2.1 Swagger

- Pasos para acceder a Swagger UI:
  - Ejecuta tu aplicación Spring Boot

   mvn spring-boot:run
   
  O desde tu IDE ejecutando la clase principal con @SpringBootApplication
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


### 2.2 EMAIL SERVICE EN LA CARPETA PROVEEDOR

- OJO DEBE DE EXISTIR UN PROVEEDOR 
 Y tambien hice un servicio de notificacion

- Nuevos endpoints disponibles:
  Para enviar notificaciones (POST):

  POST /proveedores/notificar/{idP} - Notifica al gerente

  POST /proveedores/notificar-proveedor/{idP} - Notifica al proveedor

- Para consultar historial (GET):

  GET /proveedores/notificaciones - Ver todas las notificaciones

  GET /proveedores/{idP}/notificaciones - Ver notificaciones de un proveedor específico

  GET /proveedores/notificaciones/tipo/GERENTE - Ver solo notificaciones al gerente

  GET /proveedores/notificaciones/tipo/PROVEEDOR - Ver solo notificaciones a proveedores

  GET /proveedores/notificaciones/recientes - Ver las últimas 10 notificaciones

- Ejemplo de flujo:
  - Enviar notificación:
    POST http://localhost:8080/proveedores/notificar/1
    {
        "mensaje": "Problema con entrega"
    }

  - Ver todas las notificaciones:
    GET http://localhost:8080/proveedores/notificaciones


  - Ver notificaciones de un proveedor:
    GET http://localhost:8080/proveedores/1/notificaciones

- Lo que se guarda en la base de datos:

  ID de la notificación.

  ID del proveedor relacionado.

  Tipo de notificación (GERENTE o PROVEEDOR).

  Destinatario del correo.

  Asunto del correo.

  Mensaje completo.

  Fecha y hora de envío.

  Estado (ENVIADO).

  Nombre del proveedor (para consultas rápidas).

### Productos




- Obtener todos los productos
  GET http://localhost:8080/productos



- Obtener producto por id
  GET http://localhost:8080/productos/{id}



- Crear productos
  POST http://localhost:8080/productos
  Content-Type: application/json


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


  {
    "id": 5,
    "nombre": "Teclado mecánico",
    "stock": 10,
    "precio": 89.95,
    "categoria": "Informática"
  },


  {
    "id": 6,
    "nombre": "Auriculares inalámbricos",
    "stock": 25,
    "precio": 129.00,
    "categoria": "Audio"
  }
  - Actualizar producto
    PUT http://localhost:8080/productos/{id}


  - Eliminar producto
    DELETE http://localhost:8080/productos/{id}
  ### 4. ADVERTENCIA
  -El dia jueves 26 descubri cual fue el problema, y era esque no aprete el f5,
  pero despues en los puertos por alguna razon lo deje en localhost:3306 pero no
  funcionaba las peticiones en el postman pero despues las puse con localhost:8080
  pero seguia estando en 3306 pero todo funcionaba bien asi que lo deje asi xd.



  ### 5. Cómo probar los endpoints en Postman
  -Crear una nueva petición.
  -Elegir el método HTTP (GET, POST, PUT, DELETE).
  -Escribir la URL correspondiente.
  -Para POST y PUT, ir a la pestaña Body, seleccionar raw y JSON, y pegar uno de los ejemplos JSON.
  -Hacer clic en Send y revisar la respuesta.
