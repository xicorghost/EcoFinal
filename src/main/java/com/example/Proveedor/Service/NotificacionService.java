package com.example.Proveedor.Service;

import com.example.Proveedor.Model.Notificacion;
import com.example.Proveedor.Repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificacionService {
    
    @Autowired
    private NotificacionRepository notificacionRepository;
    
    /**
     * Guarda una notificación en el historial
     * @param notificacion La notificación a guardar
     * @return La notificación guardada
     */
    public Notificacion guardarNotificacion(Notificacion notificacion) {
        try {
            // Validar que la notificación no sea null
            if (notificacion == null) {
                throw new IllegalArgumentException("La notificación no puede ser null");
            }
            
            // Validar campos obligatorios
            if (notificacion.getMensaje() == null || notificacion.getMensaje().trim().isEmpty()) {
                throw new IllegalArgumentException("El mensaje no puede estar vacío");
            }
            
            if (notificacion.getTipoNotificacion() == null || notificacion.getTipoNotificacion().trim().isEmpty()) {
                throw new IllegalArgumentException("El tipo de notificación no puede estar vacío");
            }
            
            // Establecer valores por defecto si no están presentes
            if (notificacion.getFechaEnvio() == null) {
                notificacion.setFechaEnvio(LocalDateTime.now());
            }
            
            if (notificacion.getEstado() == null || notificacion.getEstado().trim().isEmpty()) {
                notificacion.setEstado("ENVIADO");
            }
            
            // Normalizar el tipo de notificación
            notificacion.setTipoNotificacion(notificacion.getTipoNotificacion().toUpperCase());
            
            return notificacionRepository.save(notificacion);
            
        } catch (Exception e) {
            // Log del error (puedes usar tu logger preferido)
            System.err.println("Error al guardar notificación: " + e.getMessage());
            throw new RuntimeException("Error al guardar la notificación", e);
        }
    }
    
    /**
     * Obtiene todas las notificaciones
     * @return Lista de todas las notificaciones
     */
    @Transactional(readOnly = true)
    public List<Notificacion> obtenerTodasLasNotificaciones() {
        try {
            return notificacionRepository.findAll();
        } catch (Exception e) {
            System.err.println("Error al obtener todas las notificaciones: " + e.getMessage());
            throw new RuntimeException("Error al obtener las notificaciones", e);
        }
    }
    
    /**
     * Obtiene notificaciones por proveedor
     * @param proveedorId ID del proveedor
     * @return Lista de notificaciones del proveedor
     */
    @Transactional(readOnly = true)
    public List<Notificacion> obtenerNotificacionesPorProveedor(Long proveedorId) {
        try {
            if (proveedorId == null || proveedorId <= 0) {
                throw new IllegalArgumentException("El ID del proveedor debe ser válido");
            }
            return notificacionRepository.findByProveedorIdOrderByFechaEnvioDesc(proveedorId);
        } catch (Exception e) {
            System.err.println("Error al obtener notificaciones por proveedor: " + e.getMessage());
            throw new RuntimeException("Error al obtener las notificaciones del proveedor", e);
        }
    }
    
    /**
     * Obtiene notificaciones por tipo (GERENTE o PROVEEDOR)
     * @param tipo Tipo de notificación
     * @return Lista de notificaciones del tipo especificado
     */
    @Transactional(readOnly = true)
    public List<Notificacion> obtenerNotificacionesPorTipo(String tipo) {
        try {
            if (tipo == null || tipo.trim().isEmpty()) {
                throw new IllegalArgumentException("El tipo de notificación no puede estar vacío");
            }
            
            // Normalizar el tipo
            String tipoNormalizado = tipo.toUpperCase().trim();
            
            // Validar que sea un tipo válido
            if (!tipoNormalizado.equals("GERENTE") && !tipoNormalizado.equals("PROVEEDOR")) {
                throw new IllegalArgumentException("Tipo de notificación inválido. Debe ser GERENTE o PROVEEDOR");
            }
            
            return notificacionRepository.findByTipoNotificacionOrderByFechaEnvioDesc(tipoNormalizado);
        } catch (Exception e) {
            System.err.println("Error al obtener notificaciones por tipo: " + e.getMessage());
            throw new RuntimeException("Error al obtener las notificaciones por tipo", e);
        }
    }
    
    /**
     * Obtiene las últimas 10 notificaciones
     * @return Lista de las últimas 10 notificaciones
     */
    @Transactional(readOnly = true)
    public List<Notificacion> obtenerUltimasNotificaciones() {
        try {
            return notificacionRepository.findTop10ByOrderByFechaEnvioDesc();
        } catch (Exception e) {
            System.err.println("Error al obtener las últimas notificaciones: " + e.getMessage());
            throw new RuntimeException("Error al obtener las últimas notificaciones", e);
        }
    }
    
    /**
     * Obtiene una notificación por su ID
     * @param id ID de la notificación
     * @return La notificación encontrada
     */
    @Transactional(readOnly = true)
    public Optional<Notificacion> obtenerNotificacionPorId(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID debe ser válido");
            }
            return notificacionRepository.findById(id);
        } catch (Exception e) {
            System.err.println("Error al obtener notificación por ID: " + e.getMessage());
            throw new RuntimeException("Error al obtener la notificación", e);
        }
    }
    
    /**
     * Actualiza el estado de una notificación
     * @param id ID de la notificación
     * @param nuevoEstado Nuevo estado
     * @return La notificación actualizada
     */
    public Notificacion actualizarEstadoNotificacion(Long id, String nuevoEstado) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID debe ser válido");
            }
            
            if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
                throw new IllegalArgumentException("El estado no puede estar vacío");
            }
            
            Optional<Notificacion> notificacionOpt = notificacionRepository.findById(id);
            if (notificacionOpt.isPresent()) {
                Notificacion notificacion = notificacionOpt.get();
                notificacion.setEstado(nuevoEstado.toUpperCase());
                return notificacionRepository.save(notificacion);
            } else {
                throw new RuntimeException("Notificación no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar estado de notificación: " + e.getMessage());
            throw new RuntimeException("Error al actualizar el estado de la notificación", e);
        }
    }
}
