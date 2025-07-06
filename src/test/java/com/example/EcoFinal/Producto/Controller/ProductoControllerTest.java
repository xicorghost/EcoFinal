package com.example.EcoFinal.Producto.Controller;
//src/test/java/com/example/Producto/Controller/ProductoControllerTest.java


import com.example.Producto.Model.Producto;
import com.example.Producto.Service.ProductoService;
import com.example.Producto.Controller.ProductoController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.Optional;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    /* Test de obtener todos los productos */
    @Test
    void testObtenerTodos() throws Exception {
        Producto p1 = new Producto(1L, "Shampoo ecológico", 50, 3990.0, "Higiene");
        Producto p2 = new Producto(2L, "Teclado mecánico", 10, 8990.0, "Informática");

        when(productoService.listarProductos()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Shampoo ecológico"))
                .andExpect(jsonPath("$[0].stock").value(50))
                .andExpect(jsonPath("$[0].precio").value(3990.0))
                .andExpect(jsonPath("$[0].categoria").value("Higiene"))
                .andExpect(jsonPath("$[1].nombre").value("Teclado mecánico"));
    }

    /* Test de crear un producto */
    @Test
    void testCrearProducto() throws Exception {
        Producto nuevo = new Producto(null, "Shampoo ecológico", 50, 3990.0, "Higiene");
        Producto guardado = new Producto(1L, "Shampoo ecológico", 50, 3990.0, "Higiene");

        when(productoService.guardarProducto(any(Producto.class))).thenReturn(guardado);

        mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Shampoo ecológico"))
                .andExpect(jsonPath("$.stock").value(50))
                .andExpect(jsonPath("$.precio").value(3990.0))
                .andExpect(jsonPath("$.categoria").value("Higiene"));
    }

    /* Test de obtener un producto por ID */
    @Test
    void testObtenerProductoPorId() throws Exception {
        Producto producto = new Producto(1L, "Shampoo ecológico", 50, 3990.0, "Higiene");


        when(productoService.obtenerProductoPorId(1L)).thenReturn(Optional.of(producto));


        mockMvc.perform(get("/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Shampoo ecológico"))
                .andExpect(jsonPath("$.stock").value(50))
                .andExpect(jsonPath("$.precio").value(3990.0))
                .andExpect(jsonPath("$.categoria").value("Higiene"));
    }

    /* Test de actualizar producto por ID */
    @Test
    void testActualizarProducto() throws Exception {
        Producto actualizado = new Producto(1L, "Shampoo nuevo", 60, 4590.0, "Higiene");


        when(productoService.actualizarProducto(eq(1L), any(Producto.class))).thenReturn(actualizado);


        mockMvc.perform(put("/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Shampoo nuevo"))
                .andExpect(jsonPath("$.stock").value(60))
                .andExpect(jsonPath("$.precio").value(4590.0))
                .andExpect(jsonPath("$.categoria").value("Higiene"));
    }

    /* Test de eliminar producto por ID */
    @Test
    void testEliminarProducto() throws Exception {
        doNothing().when(productoService).eliminarProducto(1L);


        mockMvc.perform(delete("/productos/1"))
                .andExpect(status().isNoContent());


        verify(productoService).eliminarProducto(1L);
    }
}
