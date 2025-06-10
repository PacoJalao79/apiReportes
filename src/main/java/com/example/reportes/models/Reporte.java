package com.example.reportes.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reportes")
@Data
public class Reporte {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_reporte;

    private String tipo_reporte;

    private LocalDateTime fecha_generacion;
    
    private String descripcion;

}


