package com.example.Reporte.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Reporte.Model.Reporte;
import com.example.Reporte.Service.ReporteService;

import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public List<Reporte> getAll() {
        return reporteService.listarReportes();
    }

    @PostMapping
    public Reporte save(@RequestBody Reporte R) {
        return reporteService.guardarReporte(R);
    }

    @GetMapping("/{idR}")
    public ResponseEntity<Reporte> obtenerPorId(@PathVariable Long idR) {
        return reporteService.obtenerReportePorId(idR)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{idR}")
    public ResponseEntity<Reporte> actualizar(@PathVariable Long idR, @RequestBody Reporte reporte) {
        try {
            Reporte actualizada = reporteService.actualizarReporte(idR, reporte);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idR}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idR) {
        reporteService.eliminarReporte(idR);
        return ResponseEntity.noContent().build();
    }
}

