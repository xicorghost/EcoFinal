/*package com.example.EcoFinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class EcoFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcoFinalApplication.class, args);
	}

}*/
package com.example.EcoFinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example")
@EnableJpaRepositories(basePackages = {
    "com.example.Producto.Repository",
    "com.example.Proveedor.Repository",
    "com.example.Reporte.Repository",
    "com.example.Tienda.Repository"
})
@EntityScan(basePackages = {
    "com.example.Producto.Model",
    "com.example.Proveedor.Model",
    "com.example.Reporte.Model",
    "com.example.Tienda.Model"
})
public class EcoFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoFinalApplication.class, args);
    }
}
