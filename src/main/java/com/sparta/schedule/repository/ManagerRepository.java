package com.sparta.schedule.repository;

import com.sparta.schedule.entity.Manager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;

@Repository
public class ManagerRepository {
    private final JdbcTemplate jdbcTemplate;

    public ManagerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Manager save(Manager manager) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO manager (name, email, createDate, updateDate) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, manager.getName());
            ps.setString(2, manager.getEmail());
            ps.setObject(3, manager.getCreateDate());
            ps.setObject(4, manager.getUpdateDate());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        manager.setId(id);
        return manager;
    }

    public Long getManagerIdByName(String name) {
        String sql = "SELECT managerId FROM manager WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, name);
    }
    public String getManagerNameById(Long id) {
        String sql = "SELECT name FROM manager WHERE managerId = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public Manager findById(Long id) {
        String sql = "SELECT * FROM manager WHERE managerId = ?";
        return jdbcTemplate.query(sql, resultSet-> {
            if(resultSet.next()) {
                Manager manager = new Manager();
                manager.setId(resultSet.getLong("managerId"));
                manager.setName(resultSet.getString("name"));
                manager.setEmail(resultSet.getString("email"));
                manager.setCreateDate((LocalDateTime) resultSet.getObject("createDate"));
                manager.setUpdateDate((LocalDateTime) resultSet.getObject("updateDate"));
                return manager;
            }else {
                return null;
            }
        }, id);
    }
}
