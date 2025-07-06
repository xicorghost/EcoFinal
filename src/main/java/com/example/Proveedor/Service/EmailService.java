package com.example.Proveedor.Service;


import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    /*
     Simula el envío de un correo electrónico
     Solo imprime en consola ya que no hay integración real con servicio de email
     */
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        System.out.println("=======================================");
        System.out.println("         CORREO SIMULADO ENVIADO");
        System.out.println("=======================================");
        System.out.println("Para: " + destinatario);
        System.out.println("Asunto: " + asunto);
        System.out.println("---------------------------------------");
        System.out.println("Mensaje:");
        System.out.println(cuerpo);
        System.out.println("---------------------------------------");
        System.out.println("Fecha: " + java.time.LocalDateTime.now());
        System.out.println("Estado: ENVIADO EXITOSAMENTE");
        System.out.println("=======================================");
    }
}