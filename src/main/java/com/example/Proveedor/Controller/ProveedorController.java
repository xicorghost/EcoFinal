package com.example.Proveedor.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@CrossOrigin(origins = "*") // Permite CORS si es necesario
public class ProveedorController {
    
    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private NotificacionService notificacionService;

    /*Listar todos los proveedores
     * GET /proveedores
     */
    @GetMapping
    public ResponseEntity<List<Proveedor>> getAll() {
        try {
            List<Proveedor> proveedores = proveedorService.listarProveedores();
            return ResponseEntity.ok(proveedores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /*Guardar un nuevo proveedor
     * POST /proveedores
     */
    @PostMapping
    public ResponseEntity<Proveedor> save(@RequestBody Proveedor pr) {
        try {
            // Validaciones básicas
            if (pr == null) {
                return ResponseEntity.badRequest().body(null);
            }
            
            if (pr.getNombre() == null || pr.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            if (pr.getCorreo() == null || pr.getCorreo().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            Proveedor proveedorGuardado = proveedorService.guardarProveedor(pr);
            return ResponseEntity.status(HttpStatus.CREATED).body(proveedorGuardado);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /*Obtener proveedor por ID
     * GET /proveedores/{idP}
     */
    @GetMapping("/{idP}")
    public ResponseEntity<Proveedor> obtenerPorId(@PathVariable Long idP) {
        try {
            if (idP == null || idP <= 0) {
                return ResponseEntity.badRequest().body(null);
            }
            
            return proveedorService.obtenerProveedorPorId(idP)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
                    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /*Actualizar un proveedor existente
     * PUT /proveedores/{idP}
     */
    @PutMapping("/{idP}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long idP, @RequestBody Proveedor proveedor) {
        try {
            if (idP == null || idP <= 0) {
                return ResponseEntity.badRequest().body(null);
            }
            
            if (proveedor == null) {
                return ResponseEntity.badRequest().body(null);
            }
            
            Proveedor actualizada = proveedorService.actualizarProveedor(idP, proveedor);
            return ResponseEntity.ok(actualizada);
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /*Eliminar un proveedor existente por ID
     * DELETE /proveedores/{idP}
     */
    @DeleteMapping("/{idP}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idP) {
        try {
            if (idP == null || idP <= 0) {
                return ResponseEntity.badRequest().build();
            }
            
            proveedorService.eliminarProveedor(idP);
            return ResponseEntity.noContent().build();
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*Notificar al gerente sobre un proveedor
     * POST /proveedores/notificar/{idP}
     * Body: { "mensaje": "Texto del mensaje" }
     */
    @PostMapping("/notificar/{idP}")
    public ResponseEntity<String> notificarGerente(
            @PathVariable Long idP, 
            @RequestBody Map<String, String> request) {
        
        try {
            // Validar ID del proveedor
            if (idP == null || idP <= 0) {
                return ResponseEntity.badRequest().body("ID del proveedor inválido");
            }
            
            // Validar que el request no sea nulo
            if (request == null) {
                return ResponseEntity.badRequest().body("El cuerpo de la petición no puede estar vacío");
            }
            
            // Validar mensaje
            String mensaje = request.get("mensaje");
            if (mensaje == null || mensaje.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El mensaje no puede estar vacío");
            }
            
            // Verificar que el proveedor existe
            if (!proveedorService.obtenerProveedorPorId(idP).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            proveedorService.notificarGerente(idP, mensaje);
            return ResponseEntity.ok("Correo simulado enviado al gerente para el proveedor con ID: " + idP);
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor");
        }
    }
    
    // ENDPOINTS PARA CONSULTAR HISTORIAL DE NOTIFICACIONES 
    
    /**
     * Obtener todas las notificaciones
     * GET /proveedores/notificaciones
     */
    @GetMapping("/notificaciones")
    public ResponseEntity<List<Notificacion>> obtenerTodasLasNotificaciones() {
        try {
            List<Notificacion> notificaciones = notificacionService.obtenerTodasLasNotificaciones();
            return ResponseEntity.ok(notificaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    /**
     * Crear una notificación directamente
     * POST /proveedores/notificaciones
     * Body: { "proveedorId": 1, "tipoNotificacion": "GERENTE", "mensaje": "Texto del mensaje", "destinatario": "email@ejemplo.com" }
     */
    @PostMapping("/notificaciones")
    public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Notificacion notificacion) {
        try {
            // Validar que la notificación no sea nula
            if (notificacion == null) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // Validar mensaje
            if (notificacion.getMensaje() == null || notificacion.getMensaje().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // Validar tipo de notificación
            if (notificacion.getTipoNotificacion() == null || notificacion.getTipoNotificacion().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // Validar que el tipo sea válido
            String tipo = notificacion.getTipoNotificacion().toUpperCase().trim();
            if (!tipo.equals("GERENTE") && !tipo.equals("PROVEEDOR")) {
                return ResponseEntity.badRequest().body(null);
            }
            
            notificacion.setTipoNotificacion(tipo);
            
            // Validar que el proveedor exista si se especifica
            if (notificacion.getProveedorId() != null && notificacion.getProveedorId() > 0) {
                if (!proveedorService.obtenerProveedorPorId(notificacion.getProveedorId()).isPresent()) {
                    return ResponseEntity.badRequest().body(null);
                }
            }
            
            // Guardar la notificación
            Notificacion notificacionGuardada = notificacionService.guardarNotificacion(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(notificacionGuardada);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    /**
     * Obtener notificaciones por proveedor
     * GET /proveedores/{idP}/notificaciones
     */
    @GetMapping("/{idP}/notificaciones")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesPorProveedor(@PathVariable Long idP) {
        try {
            if (idP == null || idP <= 0) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // Verificar que el proveedor existe
            if (!proveedorService.obtenerProveedorPorId(idP).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            List<Notificacion> notificaciones = notificacionService.obtenerNotificacionesPorProveedor(idP);
            return ResponseEntity.ok(notificaciones);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    /**
     * Obtener notificaciones por tipo (GERENTE o PROVEEDOR)
     * GET /proveedores/notificaciones/tipo/{tipo}
     */
    @GetMapping("/notificaciones/tipo/{tipo}")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesPorTipo(@PathVariable String tipo) {
        try {
            if (tipo == null || tipo.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            String tipoNormalizado = tipo.toUpperCase().trim();
            if (!tipoNormalizado.equals("GERENTE") && !tipoNormalizado.equals("PROVEEDOR")) {
                return ResponseEntity.badRequest().body(null);
            }
            
            List<Notificacion> notificaciones = notificacionService.obtenerNotificacionesPorTipo(tipoNormalizado);
            return ResponseEntity.ok(notificaciones);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    /**
     * Obtener las últimas 10 notificaciones
     * GET /proveedores/notificaciones/recientes
     */
    @GetMapping("/notificaciones/recientes")
    public ResponseEntity<List<Notificacion>> obtenerUltimasNotificaciones() {
        try {
            List<Notificacion> notificaciones = notificacionService.obtenerUltimasNotificaciones();
            return ResponseEntity.ok(notificaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    /**
     * Notificar directamente al proveedor
     * POST /proveedores/notificar-proveedor/{idP}
     * Body: { "mensaje": "Texto del mensaje" }
     */
    @PostMapping("/notificar-proveedor/{idP}")
    public ResponseEntity<String> notificarProveedor(
            @PathVariable Long idP, 
            @RequestBody Map<String, String> request) {
        
        try {
            // Validar ID del proveedor
            if (idP == null || idP <= 0) {
                return ResponseEntity.badRequest().body("ID del proveedor inválido");
            }
            
            // Validar que el request no sea nulo
            if (request == null) {
                return ResponseEntity.badRequest().body("El cuerpo de la petición no puede estar vacío");
            }
            
            // Validar mensaje
            String mensaje = request.get("mensaje");
            if (mensaje == null || mensaje.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El mensaje no puede estar vacío");
            }
            
            // Verificar que el proveedor existe
            if (!proveedorService.obtenerProveedorPorId(idP).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            proveedorService.notificarProveedor(idP, mensaje);
            return ResponseEntity.ok("Correo simulado enviado al proveedor con ID: " + idP);
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor");
        }
    }
}