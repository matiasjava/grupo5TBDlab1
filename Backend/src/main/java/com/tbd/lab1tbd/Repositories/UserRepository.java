package com.tbd.lab1tbd.Repositories;

import com.tbd.lab1tbd.Entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbc;

    private static final RowMapper<UserEntity> MAPPER = new RowMapper<>() {
        @Override public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UserEntity(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password")
            );
        }
    };

    public Long create(UserEntity u) {
        String sql = """
            INSERT INTO usuario(name, email, password)
            VALUES (:name, :email, :password)
            RETURNING id
            """;
        MapSqlParameterSource p = new MapSqlParameterSource()
                .addValue("name", u.getName())
                .addValue("email", u.getEmail())
                .addValue("password", u.getPassword());
        return jdbc.queryForObject(sql, p, Long.class);
    }

    public Optional<UserEntity> getbyid(Long id) {
        String sql = "SELECT id,name,email,password FROM usuario WHERE id=:id";
        try {
            return Optional.ofNullable(jdbc.queryForObject(sql, Map.of("id", id), MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int update(Long id, UserEntity u) {
        String sql = """
            UPDATE usuario
               SET name=:name, email=:email, password=:password
             WHERE id=:id
            """;
        MapSqlParameterSource p = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("name", u.getName())
                .addValue("email", u.getEmail())
                .addValue("password", u.getPassword());
        return jdbc.update(sql, p);
    }

    public int delete(Long id) {
        String sql = "DELETE FROM usuario WHERE id=:id";
        return jdbc.update(sql, Map.of("id", id));
    }
}
