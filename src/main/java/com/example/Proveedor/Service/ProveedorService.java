package com.example.Proveedor.Service;

import com.example.Proveedor.Model.Proveedor;
import com.example.Proveedor.Repository.ProveedorRepository;
import com.example.Proveedor.Model.Notificacion;
//import com.example.Proveedor.Service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {
    @Autowired
    private EmailService emailService;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private NotificacionService notificacionService;

    public Proveedor guardarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> obtenerProveedorPorId(Long idP) {
        return proveedorRepository.findById(idP);
    }

    public Proveedor actualizarProveedor(Long idP, Proveedor proveedor) {
        Proveedor existente = proveedorRepository.findById(idP)
                .orElseThrow(() -> new RuntimeException("No existe el proveedor"));
        existente.setNombre(proveedor.getNombre());
        existente.setCorreo(proveedor.getCorreo());
        existente.setTelefono(proveedor.getTelefono());
        return proveedorRepository.save(existente);
    }

    public void eliminarProveedor(Long idP) {
        proveedorRepository.deleteById(idP);
    }

    /*nuevos metodos */
    /*Notifica al gerente sobre un proveedor específico
     (El gerente es simulado, no existe como entidad)
     */
    public void notificarGerente(Long idP, String mensaje) {
        Proveedor proveedor = proveedorRepository.findById(idP)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + idP));
        
        String gerenteEmail = "gerente@ecomarket.cl"; // Email simulado del gerente
        String asunto = "Alerta de proveedor: " + proveedor.getNombre();
        
        String mensajeCompleto = String.format(
            "Estimado Gerente,\n\n" +
            "Se ha generado una alerta para el proveedor:\n" +
            "- ID: %d\n" +
            "- Nombre: %s\n" +
            "- Email: %s\n" +
            "- Teléfono: %s\n\n" +
            "Mensaje: %s\n\n" +
            "Saludos,\n" +
            "Sistema EcoMarket",
            proveedor.getIdP(),
            proveedor.getNombre(),
            proveedor.getCorreo(),
            proveedor.getTelefono(),
            mensaje
        );
        
        // Enviar correo (simulado)
        emailService.enviarCorreo(gerenteEmail, asunto, mensajeCompleto);
        
        // Guardar en historial
        Notificacion notificacion = new Notificacion();
        notificacion.setProveedorId(idP);
        notificacion.setTipoNotificacion("GERENTE");
        notificacion.setDestinatario(gerenteEmail);
        notificacion.setAsunto(asunto);
        notificacion.setMensaje(mensajeCompleto);
        notificacion.setNombreProveedor(proveedor.getNombre());
        
        notificacionService.guardarNotificacion(notificacion);
    }
    
    /*Notifica directamente al proveedor*/
    public void notificarProveedor(Long idP, String mensaje) {
        Proveedor proveedor = proveedorRepository.findById(idP)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + idP));
        
        String asunto = "Notificación de EcoMarket";
        String mensajeCompleto = String.format(
            "Estimado %s,\n\n%s\n\n" +
            "Saludos,\n" +
            "Equipo EcoMarket\n" +
            "Contacto: info@ecomarket.cl",
            proveedor.getNombre(),
            mensaje
        );
        
        // Enviar correo (simulado)
        emailService.enviarCorreo(proveedor.getCorreo(), asunto, mensajeCompleto);
        
        // Guardar en historial
        Notificacion notificacion = new Notificacion();
        notificacion.setProveedorId(idP);
        notificacion.setTipoNotificacion("PROVEEDOR");
        notificacion.setDestinatario(proveedor.getCorreo());
        notificacion.setAsunto(asunto);
        notificacion.setMensaje(mensajeCompleto);
        notificacion.setNombreProveedor(proveedor.getNombre());
        
        notificacionService.guardarNotificacion(notificacion);
    }
}
