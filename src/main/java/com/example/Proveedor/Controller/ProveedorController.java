package com.example.Proveedor.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Proveedor.Model.Notificacion;
import com.example.Proveedor.Model.Proveedor;
import com.example.Proveedor.Service.NotificacionService;
import com.example.Proveedor.Service.ProveedorService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private NotificacionService notificacionService;

    /*Listar todos los prov */
    @GetMapping
    public List<Proveedor> getAll() {
        return proveedorService.listarProveedores();
    }

    /*guarda un nuevo Prov */
    @PostMapping
    public Proveedor save(@RequestBody Proveedor pr) {
        return proveedorService.guardarProveedor(pr);
    }

    /*obtener por id */
    @GetMapping("/{idP}")
    public ResponseEntity<Proveedor> obtenerPorId(@PathVariable Long idP) {
        return proveedorService.obtenerProveedorPorId(idP)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /*actualiza un prov existente */
    @PutMapping("/{idP}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long idP, @RequestBody Proveedor proveedor) {
        try {
            Proveedor actualizada = proveedorService.actualizarProveedor(idP, proveedor);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /*elimana a un prov existente por id */
    @DeleteMapping("/{idP}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idP) {
        proveedorService.eliminarProveedor(idP);
        return ResponseEntity.noContent().build();
    }

    /* Notifica al gerente sobre un proveedor
      Endpoint: POST /proveedores/notificar/{idP}
      Body: { "mensaje": "Texto del mensaje" }
     */
    @PostMapping("/notificar/{idP}")
    public ResponseEntity<String> notificarGerente(
            @PathVariable Long idP, 
            @RequestBody Map<String, String> request) {
        
        try {
            String mensaje = request.get("mensaje");
            if (mensaje == null || mensaje.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El mensaje no puede estar vacío");
            }
            
            proveedorService.notificarGerente(idP, mensaje);
            return ResponseEntity.ok("Correo simulado enviado al gerente para el proveedor con ID: " + idP);
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    // ENDPOINTS PARA CONSULTAR HISTORIAL DE NOTIFICACIONES 
    
    /*
     Obtiene todas las notificaciones
     GET /proveedores/notificaciones
     */
    @GetMapping("/notificaciones")
    public List<Notificacion> obtenerTodasLasNotificaciones() {
        return notificacionService.obtenerTodasLasNotificaciones();
    }
    
    /*
     Crear una notificación directamente
     POST /proveedores/notificaciones
     Body: { "proveedorId": 1, "tipoNotificacion": "GERENTE", "mensaje": "Texto del mensaje", "destinatario": "email@ejemplo.com" }
     */
    @PostMapping("/notificaciones")
    public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Notificacion notificacion) {
        try {
            // Validaciones básicas
            if (notificacion.getMensaje() == null || notificacion.getMensaje().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            if (notificacion.getTipoNotificacion() == null || notificacion.getTipoNotificacion().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // Validar que el tipo sea válido
            String tipo = notificacion.getTipoNotificacion().toUpperCase();
            if (!tipo.equals("GERENTE") && !tipo.equals("PROVEEDOR")) {
                return ResponseEntity.badRequest().body(null);
            }
            
            notificacion.setTipoNotificacion(tipo);
            
            // Guardar la notificación
            Notificacion notificacionGuardada = notificacionService.guardarNotificacion(notificacion);
            return ResponseEntity.ok(notificacionGuardada);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /*
     Obtiene notificaciones por proveedor
     GET /proveedores/{idP}/notificaciones
     */
    @GetMapping("/{idP}/notificaciones")
    public List<Notificacion> obtenerNotificacionesPorProveedor(@PathVariable Long idP) {
        return notificacionService.obtenerNotificacionesPorProveedor(idP);
    }
    
    /*
     Obtiene notificaciones por tipo (GERENTE o PROVEEDOR)
     GET /proveedores/notificaciones/tipo/{tipo}
     */
    @GetMapping("/notificaciones/tipo/{tipo}")
    public List<Notificacion> obtenerNotificacionesPorTipo(@PathVariable String tipo) {
        return notificacionService.obtenerNotificacionesPorTipo(tipo.toUpperCase());
    }
    
    /*
     Obtiene las últimas 10 notificaciones
     GET /proveedores/notificaciones/recientes
     */
    @GetMapping("/notificaciones/recientes")
    public List<Notificacion> obtenerUltimasNotificaciones() {
        return notificacionService.obtenerUltimasNotificaciones();
    }
    
    /*
     Notifica directamente al proveedor
     Endpoint: POST /proveedores/notificar-proveedor/{idP}
     Body: { "mensaje": "Texto del mensaje" }
     */
    @PostMapping("/notificar-proveedor/{idP}")
    public ResponseEntity<String> notificarProveedor(
            @PathVariable Long idP, 
            @RequestBody Map<String, String> request) {
        
        try {
            String mensaje = request.get("mensaje");
            if (mensaje == null || mensaje.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El mensaje no puede estar vacío");
            }
            
            proveedorService.notificarProveedor(idP, mensaje);
            return ResponseEntity.ok("Correo simulado enviado al proveedor con ID: " + idP);
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}