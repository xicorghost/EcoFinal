package com.example.Reporte.Service;

import com.example.Reporte.Model.Reporte;
import com.example.Reporte.Repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    public Reporte guardarReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public List<Reporte> listarReportes() {
        return reporteRepository.findAll();
    }

    public Optional<Reporte> obtenerReportePorId(Long idR) {
        return reporteRepository.findById(idR);
    }

    public Reporte actualizarReporte(Long idR, Reporte reporte) {
        Reporte existente = reporteRepository.findById(idR)
                .orElseThrow(() -> new RuntimeException("No existe el reporte"));
        existente.setTipo(reporte.getTipo());
        existente.setContenido(reporte.getContenido());
        existente.setFecha(reporte.getFecha());
        return reporteRepository.save(existente);
    }

    public void eliminarReporte(Long idR) {
        reporteRepository.deleteById(idR);
    }
}

