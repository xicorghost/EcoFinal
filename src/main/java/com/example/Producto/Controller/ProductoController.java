package com.example.Producto.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Producto.Model.Producto;
import com.example.Producto.Service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/productos")
@Tag(name = "Productos", description = "API para gestión de productos ecológicos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Obtener todos los productos", 
               description = "Devuelve una lista completa de todos los productos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    public List<Producto> getAll() {
        return productoService.listarProductos();
    }

    @PostMapping
    @Operation(summary = "Crear nuevo producto", 
               description = "Guarda un nuevo producto en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos del producto inválidos")
    })
    public Producto save(@RequestBody Producto p) {
        return productoService.guardarProducto(p);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", 
               description = "Busca y devuelve un producto específico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Producto> obtenerPorId(
            @Parameter(description = "ID único del producto", example = "1") 
            @PathVariable Long id) {
        return productoService.obtenerProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", 
               description = "Actualiza los datos de un producto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Producto> actualizar(
            @Parameter(description = "ID del producto a actualizar", example = "1") 
            @PathVariable Long id, 
            @RequestBody Producto producto) {
        try {
            Producto actualizada = productoService.actualizarProducto(id, producto);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", 
               description = "Elimina un producto del sistema de forma permanente")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del producto a eliminar", example = "1") 
            @PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}

