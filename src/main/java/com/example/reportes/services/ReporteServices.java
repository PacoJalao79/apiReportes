package com.example.reportes.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reportes.dto.ReporteDTO;
import com.example.reportes.models.Reporte;
import com.example.reportes.repository.ReporteRepository;

@Service
public class ReporteServices {
    // Métodos auxiliares
    private ReporteDTO toDTO(Reporte reporte) {
        ReporteDTO dto = new ReporteDTO();
        dto.setId_reporte(reporte.getId_reporte());
        dto.setTipo_reporte(reporte.getTipo_reporte());
        dto.setFecha_generacion(reporte.getFecha_generacion());
        dto.setDescripcion(reporte.getDescripcion());
        return dto;
    }

    private Reporte toEntity(ReporteDTO dto) {
        Reporte reporte = new Reporte();
        reporte.setId_reporte(dto.getId_reporte());
        reporte.setTipo_reporte(dto.getTipo_reporte());
        reporte.setFecha_generacion(dto.getFecha_generacion());
        reporte.setDescripcion(dto.getDescripcion());
        return reporte;
    }

     @Autowired
    private ReporteRepository repository;

    public ReporteDTO guardar(ReporteDTO dto) {
        Reporte reporte = toEntity(dto);
        Reporte saved = repository.save(reporte);
        return toDTO(saved);
    }

    public List<ReporteDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ReporteDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    public Optional<ReporteDTO> actualizar(Integer id, ReporteDTO dto) {
        return repository.findById(id).map(reporte -> {
            reporte.setId_reporte(dto.getId_reporte());
            reporte.setTipo_reporte(dto.getTipo_reporte());
            reporte.setFecha_generacion(dto.getFecha_generacion());
            reporte.setDescripcion(dto.getDescripcion());
            return toDTO(repository.save(reporte));
        });
    }

    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
