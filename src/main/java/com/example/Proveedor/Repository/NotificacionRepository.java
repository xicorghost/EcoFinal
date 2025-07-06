package com.example.Proveedor.Repository;

import com.example.Proveedor.Model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    
    // Buscar notificaciones por proveedor
    List<Notificacion> findByProveedorIdOrderByFechaEnvioDesc(Long proveedorId);
    
    // Buscar notificaciones por tipo
    List<Notificacion> findByTipoNotificacionOrderByFechaEnvioDesc(String tipoNotificacion);
    
    // Buscar las últimas notificaciones (las 10 más recientes)
    List<Notificacion> findTop10ByOrderByFechaEnvioDesc();
}
