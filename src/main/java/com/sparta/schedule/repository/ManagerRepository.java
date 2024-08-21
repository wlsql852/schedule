package com.sparta.schedule.repository;

import com.sparta.schedule.dto.ManagerRequestDto;
import com.sparta.schedule.dto.ManagerResponseDto;
import com.sparta.schedule.entity.Manager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

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
        if(name!=null) {
            try{
                return jdbcTemplate.queryForObject(sql, Long.class, name);
            } catch (Exception e){
                throw new IllegalArgumentException("해당 이름의 매니저가 없습니다");
            }
        }else {
            return null;
        }

    }
    public String getManagerNameById(Long id) {
        String sql = "SELECT name FROM manager WHERE managerId = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, id);
        }catch (Exception e){
            throw new IllegalArgumentException("해당 아이디의 매니저가 없습니다");
        }
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

    public List<ManagerResponseDto> getSchedules() {
        String sql = "SELECT * FROM manager";
        return jdbcTemplate.query(sql, new RowMapper<ManagerResponseDto>() {
            @Override
            public ManagerResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("managerId");
                String name = rs.getString("name");
                String email = rs.getString("email");
                LocalDateTime createDate = rs.getObject("createDate", LocalDateTime.class);
                LocalDateTime updateDate = rs.getObject("updateDate", LocalDateTime.class);
                return new ManagerResponseDto(id, name, email, createDate, updateDate);
            }
        });
    }

    public Manager update(Long id, ManagerRequestDto requestDto) {
        String sql = "UPDATE manager SET name = ?, email = ?, updateDate = ? WHERE managerId = ?";
        jdbcTemplate.update(sql, requestDto.getName(), requestDto.getEmail(), LocalDateTime.now(), id);
        return findById(id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM MANAGER WHERE managerId = ?";
        jdbcTemplate.update(sql, id);
    }
}
