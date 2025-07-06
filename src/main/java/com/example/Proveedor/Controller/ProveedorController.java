package com.example.Proveedor.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Proveedor.Model.Proveedor;
import com.example.Proveedor.Service.ProveedorService;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;


    @GetMapping
    public List<Proveedor> getAll() {
        return proveedorService.listarProveedores();
    }

    @PostMapping
    public Proveedor save(@RequestBody Proveedor pr) {
        return proveedorService.guardarProveedor(pr);
    }

    @GetMapping("/{idP}")
    public ResponseEntity<Proveedor> obtenerPorId(@PathVariable Long idP) {
        return proveedorService.obtenerProveedorPorId(idP)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{idP}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long idP, @RequestBody Proveedor proveedor) {
        try {
            Proveedor actualizada = proveedorService.actualizarProveedor(idP, proveedor);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idP}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idP) {
        proveedorService.eliminarProveedor(idP);
        return ResponseEntity.noContent().build();
    }
}
