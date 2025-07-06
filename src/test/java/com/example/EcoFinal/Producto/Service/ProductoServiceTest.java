package com.example.EcoFinal.Producto.Service;

import com.example.Producto.Model.Producto;
import com.example.Producto.Repository.ProductoRepository;
import com.example.Producto.Service.ProductoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /* Test para guardar producto en la capa servicio */
    @Test
    void testGuardarProducto() {
        Producto producto = new Producto(null, "Shampoo ecológico", 50, 3990.0, "Higiene");
        Producto productoGuardado = new Producto(1L, "Shampoo ecológico", 50, 3990.0, "Higiene");
        when(productoRepository.save(producto)).thenReturn(productoGuardado);

        Producto resultado = productoService.guardarProducto(producto);
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(productoRepository).save(producto);
    }
    /*test listar producto */
     @Test
    void testListarProductos() {
        Producto p1 = new Producto(1L, "Shampoo ecológico", 50, 3990.0, "Higiene");
        Producto p2 = new Producto(4L, "Bolsa reutilizable", 80, 2490.0, "Accesorios");
        when(productoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Producto> resultado = productoService.listarProductos();
        assertThat(resultado).hasSize(2).contains(p1, p2);
        verify(productoRepository).findAll();
    }

    /*Test de eliminar producto por id */
    @Test
void testEliminarProductoPorId() {
    Long idProducto = 1L;

    // No se necesita simular el retorno porque deleteById no devuelve nada
    doNothing().when(productoRepository).deleteById(idProducto);
    productoService.eliminarProducto(idProducto);
    verify(productoRepository).deleteById(idProducto);
}

/*test de actualizar producto */
@Test
void testActualizarProducto() {
    Long idProducto = 1L;
    Producto productoExistente = new Producto(idProducto, "Cepillo de bambú", 30, 1990.0, "Higiene");
    Producto productoActualizado = new Producto(idProducto, "Cepillo de bambú actualizado", 40, 2490.0, "Higiene");


    when(productoRepository.findById(idProducto)).thenReturn(Optional.of(productoExistente));
    when(productoRepository.save(productoExistente)).thenReturn(productoActualizado);


    Producto resultado = productoService.actualizarProducto(idProducto, productoActualizado);


    assertThat(resultado.getNombre()).isEqualTo("Cepillo de bambú actualizado");
    assertThat(resultado.getStock()).isEqualTo(40);
    assertThat(resultado.getPrecio()).isEqualTo(2490.0);
    verify(productoRepository).findById(idProducto);
    verify(productoRepository).save(productoExistente);
}

}
