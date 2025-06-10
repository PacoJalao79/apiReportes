package com.example.reportes.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
public class ReporteDTO {
    
    private Long id_reporte;
    private String tipo_reporte;
    private LocalDateTime fecha_generacion;
    private String descripcion;

}
