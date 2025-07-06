package com.example.Tienda.Model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tiendas")
public class Tienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idT;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;
   
    private String horario;

}

