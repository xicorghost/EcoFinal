## 1. Creación del proyecto con Spring Initializr


lol


## 2. Endpoints para pruebas (Postman o navegador)




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
