package com.sparta.schedule.repository;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Schedule save(Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO schedule (todo, manager, password, createDate, updateDate) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, schedule.getTodo());
            ps.setString(2, schedule.getManager());
            ps.setString(3, schedule.getPassword());
            ps.setObject(4, schedule.getCreateDate());
            ps.setObject(5, schedule.getUpdateDate());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);
        return schedule;
    }
    public List<ScheduleResponseDto> schedules(String updateDay, String managerName) {
        //둘다 있는 경우
        if(updateDay !=null && managerName != null) {
            String date = updateDay.substring(0,4)+"-"+ updateDay.substring(4,6)+"-"+ updateDay.substring(6,8);
            String sql = "SELECT * FROM schedule WHERE DATE (updateDate) = ? AND manager = ? ORDER BY updateDate DESC";
            return jdbcTemplate.query(sql, getRowMapper(), updateDay, managerName);
            //수정날짜만 있는 경우
        } else if (updateDay !=null) {
            String date = updateDay.substring(0,4)+"-"+ updateDay.substring(4,6)+"-"+ updateDay.substring(6,8);
            String sql = "SELECT * FROM schedule WHERE DATE (updateDate) = ? ORDER BY updateDate DESC";
            return jdbcTemplate.query(sql, getRowMapper(), date);
            //매니저 이름만 있는 경우
        } else if (managerName !=null) {
            String sql = "SELECT * FROM schedule WHERE manager = ? ORDER BY updateDate DESC";
            return jdbcTemplate.query(sql, getRowMapper(), managerName);
        }else {
            String sql = "SELECT * FROM schedule ORDER BY updateDate DESC";
            return jdbcTemplate.query(sql, getRowMapper());
        }
    }

    public  Schedule update(Long id, ScheduleRequestDto requestDto) {
        String sql = "UPDATE schedule SET todo=?, manager=?, updateDate=? WHERE scheduleId = ?";
        jdbcTemplate.update(sql, requestDto.getTodo(), requestDto.getManager(), LocalDateTime.now(),id);
        return findById(id);
    }

    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE scheduleId = ?";
        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getLong("scheduleId"));
                schedule.setTodo(resultSet.getString("todo"));
                schedule.setManager(resultSet.getString("manager"));
                schedule.setPassword(resultSet.getString("password"));
                schedule.setCreateDate((LocalDateTime) resultSet.getObject("createDate"));
                schedule.setUpdateDate((LocalDateTime) resultSet.getObject("updateDate"));
                return schedule;
            }else {
                return null;
            }
        },id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM schedule WHERE scheduleId = ?";
        jdbcTemplate.update(sql,id);
    }

    private RowMapper<ScheduleResponseDto> getRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("scheduleId");
                String todo = rs.getString("todo");
                String manager = rs.getString("manager");
                Object createDate = rs.getObject("createDate");
                Object updateDate = rs.getObject("updateDate");
                return new ScheduleResponseDto(id, todo, manager, createDate, updateDate);
            }
        };
    }
}
