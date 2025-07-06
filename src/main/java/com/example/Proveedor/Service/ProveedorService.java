package com.example.Proveedor.Service;

import com.example.Proveedor.Model.Proveedor;
import com.example.Proveedor.Repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;


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
}
