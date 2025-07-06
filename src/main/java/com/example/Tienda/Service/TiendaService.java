package com.example.Tienda.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Tienda.Model.Tienda;
import com.example.Tienda.Repository.TiendaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TiendaService {
    @Autowired
    private TiendaRepository tiendaRepository;

    public List<Tienda> listarTiendas() {
        return tiendaRepository.findAll();
    }

    public Tienda guardarTienda(Tienda T) {
        return tiendaRepository.save(T);
    }

    public Optional<Tienda> obtenerTiendaPorId(Long idT) {
        return tiendaRepository.findById(idT);
    }

    public Tienda actualizarTienda(Long idT, Tienda tienda) {
        Tienda existente = tiendaRepository.findById(idT)
                .orElseThrow(() -> new RuntimeException("No existe la tienda"));
        existente.setNombre(tienda.getNombre());
        existente.setDireccion(tienda.getDireccion());
        existente.setHorario(tienda.getHorario());
        return tiendaRepository.save(existente);
    }

    public void eliminarTienda(Long idT) {
        tiendaRepository.deleteById(idT);
    }
}

