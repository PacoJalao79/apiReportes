package com.example.reportes.dto;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.*;

@Data
public class ReporteDTO extends RepresentationModel<ReporteDTO>{
    
    private Long id_reporte;
    private String tipo_reporte;
    private LocalDateTime fecha_generacion;
    private String descripcion;

}
