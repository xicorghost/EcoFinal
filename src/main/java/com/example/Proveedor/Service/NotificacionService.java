package com.example.Proveedor.Service;

import com.example.Proveedor.Model.Notificacion;
import com.example.Proveedor.Repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionService {
    
    @Autowired
    private NotificacionRepository notificacionRepository;
    
    /* Guarda una notificación en el historial*/
    public Notificacion guardarNotificacion(Notificacion notificacion) {
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacion.setEstado("ENVIADO");
        return notificacionRepository.save(notificacion);
    }
    
    /*Obtiene todas las notificaciones*/
    public List<Notificacion> obtenerTodasLasNotificaciones() {
        return notificacionRepository.findAll();
    }
    
    /*Obtiene notificaciones por proveedor*/
    public List<Notificacion> obtenerNotificacionesPorProveedor(Long proveedorId) {
        return notificacionRepository.findByProveedorIdOrderByFechaEnvioDesc(proveedorId);
    }
    
    /*Obtiene notificaciones por tipo (GERENTE o PROVEEDOR)*/
    public List<Notificacion> obtenerNotificacionesPorTipo(String tipo) {
        return notificacionRepository.findByTipoNotificacionOrderByFechaEnvioDesc(tipo);
    }
    
    /*Obtiene las últimas 10 notificaciones*/
    public List<Notificacion> obtenerUltimasNotificaciones() {
        return notificacionRepository.findTop10ByOrderByFechaEnvioDesc();
    }
}
