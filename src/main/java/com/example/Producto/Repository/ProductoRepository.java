package com.example.Producto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Producto.Model.Producto;
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {}
