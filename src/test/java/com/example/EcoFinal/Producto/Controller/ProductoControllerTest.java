package com.example.EcoFinal.Producto.Controller;

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
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
@ContextConfiguration(classes = {ProductoController.class})
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    /* Test de obtener todos los productos */
    @Test
    void testGetAll() throws Exception {
        // Usar el constructor correcto de Lombok
        Producto p1 = new Producto();
        p1.setId(1L);
        p1.setNombre("Shampoo ecológico");
        p1.setStock(50);
        p1.setPrecio(3990.0);
        p1.setCategoria("Higiene");

        Producto p2 = new Producto();
        p2.setId(2L);
        p2.setNombre("Teclado mecánico");
        p2.setStock(10);
        p2.setPrecio(8990.0);
        p2.setCategoria("Informática");

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
    void testSave() throws Exception {
        // Producto a enviar (sin ID)
        Producto nuevo = new Producto();
        nuevo.setNombre("Shampoo ecológico");
        nuevo.setStock(50);
        nuevo.setPrecio(3990.0);
        nuevo.setCategoria("Higiene");

        // Producto que devuelve el servicio (con ID)
        Producto guardado = new Producto();
        guardado.setId(1L);
        guardado.setNombre("Shampoo ecológico");
        guardado.setStock(50);
        guardado.setPrecio(3990.0);
        guardado.setCategoria("Higiene");

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
    void testObtenerPorId() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Shampoo ecológico");
        producto.setStock(50);
        producto.setPrecio(3990.0);
        producto.setCategoria("Higiene");

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
    void testActualizar() throws Exception {
        Producto actualizado = new Producto();
        actualizado.setId(1L);
        actualizado.setNombre("Shampoo nuevo");
        actualizado.setStock(60);
        actualizado.setPrecio(4590.0);
        actualizado.setCategoria("Higiene");

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
    void testEliminar() throws Exception {
        doNothing().when(productoService).eliminarProducto(1L);

        mockMvc.perform(delete("/productos/1"))
                .andExpect(status().isNoContent());

        verify(productoService).eliminarProducto(1L);
    }

    /* Test de obtener producto por ID - No encontrado */
    @Test
    void testObtenerPorIdNoEncontrado() throws Exception {
        when(productoService.obtenerProductoPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/productos/999"))
                .andExpect(status().isNotFound());
    }

    /* Test de actualizar producto - No encontrado */
    @Test
    void testActualizarNoEncontrado() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto");
        producto.setStock(10);
        producto.setPrecio(1000.0);
        producto.setCategoria("Categoria");

        when(productoService.actualizarProducto(eq(999L), any(Producto.class)))
                .thenThrow(new RuntimeException("Producto no encontrado"));

        mockMvc.perform(put("/productos/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isNotFound());
    }
}