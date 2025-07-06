package com.example.Reporte.Model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reportes")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idR;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String contenido;
   
    private LocalDate fecha;

}

