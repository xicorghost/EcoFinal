package com.example.Producto.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Producto.Model.Producto;
import com.example.Producto.Repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;


    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }


    public Producto guardarProducto(Producto p) {
        return productoRepository.save(p);
    }


    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }


    public Producto actualizarProducto(Long id, Producto producto) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el producto"));
        existente.setNombre(producto.getNombre());
        existente.setStock(producto.getStock());
        existente.setPrecio(producto.getPrecio());
        existente.setCategoria(producto.getCategoria());
        return productoRepository.save(existente);
    }


    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
