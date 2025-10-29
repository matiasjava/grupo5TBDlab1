package com.tbd.lab1tbd.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tbd.lab1tbd.dto.SitioTuristico;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SitioTuristicoRepository {

    // 1. Inyecta JdbcTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 2. Define el RowMapper
    private final RowMapper<SitioTuristico> sitioTuristicoRowMapper = new RowMapper<SitioTuristico>() {
        @Override
        public SitioTuristico mapRow(ResultSet rs, int rowNum) throws SQLException {
            SitioTuristico sitio = new SitioTuristico();
            sitio.setId(rs.getLong("id"));
            sitio.setNombre(rs.getString("nombre"));
            sitio.setDescripcion(rs.getString("descripcion"));
            sitio.setTipo(rs.getString("tipo"));
            sitio.setCalificacionPromedio(rs.getDouble("calificacion_promedio"));
            sitio.setTotalReseñas(rs.getInt("total_reseñas"));
            return sitio;
        }
    };

    // 3. Escribe método con SQL Nativo
    public List<SitioTuristico> findAll() {
        String sql = "SELECT id, nombre, descripcion, tipo, calificacion_promedio, total_reseñas FROM sitios_turisticos";
        return jdbcTemplate.query(sql, sitioTuristicoRowMapper);
    }

    // Ejemplo para llamar a tu Función Almacenada:
    public List<SitioTuristico> findSitiosCercanos(double lon, double lat, int radio) {
        String sql = "SELECT * FROM buscar_sitios_cercanos(?, ?, ?)";
        
        // Pasamos argumentos de forma segura
        return jdbcTemplate.query(sql, new Object[]{lon, lat, radio}, sitioTuristicoRowMapper);
    }
}