package com.example.Proveedor.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notificaciones")
public class Notificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long proveedorId;
    
    @Column(nullable = false)
    private String tipoNotificacion; // "GERENTE" o "PROVEEDOR"
    
    @Column(nullable = false)
    private String destinatario;
    
    @Column(nullable = false)
    private String asunto;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;
    
    @Column(nullable = false)
    private LocalDateTime fechaEnvio;
    
    @Column(nullable = false)
    private String estado; // "ENVIADO", "PENDIENTE", "ERROR"
    
    // Información adicional del proveedor para consultas rápidas
    @Column
    private String nombreProveedor;
}