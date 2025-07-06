package com.example.Proveedor.Model;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proveedores")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idP;


    @Column(nullable = false)
    private String nombre;


    @Column(nullable = false)
    private String correo;
   
    private String telefono;


}

