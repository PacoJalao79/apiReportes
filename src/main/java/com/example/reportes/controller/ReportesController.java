package com.example.reportes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.reportes.dto.ReporteDTO;
import com.example.reportes.services.ReporteServices;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/reportes")
public class ReportesController {
    
     @Autowired
    private ReporteServices service;

    @PostMapping
    public ResponseEntity<ReporteDTO> crear(@RequestBody ReporteDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ReporteDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteDTO> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteDTO> actualizar(@PathVariable Integer id, @RequestBody ReporteDTO dto) {
        return service.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return service.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

            //METODO HATEOAS para buscar por ID
    @GetMapping("/hateoas/{id}")
    public ResponseEntity<ReporteDTO> obtenerHATEOAS(@PathVariable Integer id) {
        return service.obtenerPorId(id)
            .map(dto -> {
                // Agregar los links HATEOAS
                dto.add(linkTo(methodOn(ReportesController.class).obtenerHATEOAS(id)).withSelfRel());
                dto.add(linkTo(methodOn(ReportesController.class).obtenerTodosHATEOAS()).withRel("todos"));
                dto.add(linkTo(methodOn(ReportesController.class).eliminar(id)).withRel("eliminar"));

                dto.add(Link.of("http://localhost:8888/api/proxy/productos/" + dto.getId_reporte()).withSelfRel());
                dto.add(Link.of("http://localhost:8888/api/proxy/productos/" + dto.getId_reporte()).withRel("Modificar HATEOAS").withType("PUT"));
                dto.add(Link.of("http://localhost:8888/api/proxy/productos/" + dto.getId_reporte()).withRel("Eliminar HATEOAS").withType("DELETE"));

                return ResponseEntity.ok(dto);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
}

    @GetMapping("/hateoas")
    public List<ReporteDTO> obtenerTodosHATEOAS() {
    List<ReporteDTO> lista = service.listar();
    for (ReporteDTO dto : lista) {
        // link url de la misma API
        dto.add(
            linkTo(
                methodOn(ReportesController.class)
                .obtenerHATEOAS(dto.getId_reporte().intValue())
            ).withSelfRel()
        );
        // link HATEOAS para API Gateway "A mano"
        dto.add(
            Link.of("http://localhost:8888/api/proxy/productos")
                .withRel("Get todos HATEOAS")
        );
        dto.add(
            Link.of("http://localhost:8888/api/proxy/productos/" + dto.getId_reporte())
                .withRel("Crear HATEOAS")
                .withType("POST")
        );
    }

    return lista;
}


}
