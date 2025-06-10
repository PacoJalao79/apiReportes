package com.example.reportes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reportes.model.Reporte;

public interface ReporteRepository extends JpaRepository<Reporte, Integer> {
}
