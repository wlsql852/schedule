package com.sparta.schedule.repository;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
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
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //데이터 insert
    public Schedule save(Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO schedule (todo, managerId, password, createDate, updateDate) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, schedule.getTodo());
            ps.setLong(2, schedule.getManagerId());
            ps.setString(3, schedule.getPassword());
            ps.setObject(4, schedule.getCreateDate());
            ps.setObject(5, schedule.getUpdateDate());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);
        return schedule;
    }
    //조회된 일정 리스트 (검색조건 : 수정날짜, 매니저이름, 값이 없으면 null)
    public List<ScheduleResponseDto> schedules(String updateDay, Long managerId) {
        //둘다 있는 경우
        if(updateDay !=null && managerId != null) {
            //매니저이름으로 아이디 찾기
            String date = updateDay.substring(0,4)+"-"+ updateDay.substring(4,6)+"-"+ updateDay.substring(6,8);
            String sql = "SELECT s.scheduleId, s.todo, s.password, s.createDate, s.updateDate, m.managerId, m.name managerName FROM SCHEDULE s JOIN MANAGER m ON s.managerId= m.managerId WHERE DATE (s.updateDate) = ? AND m.managerId = ? ORDER BY s.updateDate DESC";
            return jdbcTemplate.query(sql, getRowMapper(), updateDay, managerId);
            //수정날짜만 있는 경우
        } else if (updateDay !=null) {
            String date = updateDay.substring(0,4)+"-"+ updateDay.substring(4,6)+"-"+ updateDay.substring(6,8);
            String sql = "SELECT s.scheduleId, s.todo, s.password, s.createDate, s.updateDate, m.managerId, m.name managerName FROM SCHEDULE s JOIN MANAGER m ON s.managerId= m.managerId WHERE DATE (s.updateDate) = ? ORDER BY s.updateDate DESC";
            return jdbcTemplate.query(sql, getRowMapper(), date);
            //매니저 이름만 있는 경우
        } else if (managerId !=null) {
            //매니저이름으로 아이디 찾기
            String sql = "SELECT s.scheduleId, s.todo, s.password, s.createDate, s.updateDate, m.managerId, m.name managerName FROM SCHEDULE s JOIN MANAGER m ON s.managerId= m.managerId WHERE m.managerId = ? ORDER BY s.updateDate DESC";
            return jdbcTemplate.query(sql, getRowMapper(), managerId);
        }else {
            String sql = "SELECT s.scheduleId, s.todo, s.password, s.createDate, s.updateDate, m.managerId, m.name managerName FROM SCHEDULE s JOIN MANAGER m ON s.managerId= m.managerId ORDER BY s.updateDate DESC";
            return jdbcTemplate.query(sql, getRowMapper());
        }
    }
    //일정 수정
    public  Schedule update(Long id, ScheduleRequestDto requestDto) {
        String sql = "UPDATE schedule SET todo=?, managerId=?, updateDate=? WHERE scheduleId = ?";
        jdbcTemplate.update(sql, requestDto.getTodo(), requestDto.getManagerId(), LocalDateTime.now(),id);
        return findById(id);
    }

    //아이디로 일정 찾는 함수
    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE scheduleId = ?";
        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getLong("scheduleId"));
                schedule.setTodo(resultSet.getString("todo"));
                schedule.setManagerId(resultSet.getLong("managerId"));
                schedule.setPassword(resultSet.getString("password"));
                schedule.setCreateDate((LocalDateTime) resultSet.getObject("createDate"));
                schedule.setUpdateDate((LocalDateTime) resultSet.getObject("updateDate"));
                return schedule;
            }else {
                return null;
            }
        },id);
    }
    //해당 아이디를 가진 일정 지우기
    public void delete(Long id) {
        String sql = "DELETE FROM schedule WHERE scheduleId = ?";
        jdbcTemplate.update(sql,id);
    }
    //sql로 검색한 결과를 mapRow형태로 가져오기(다건 조회처럼 여러개일때 사용)
    private RowMapper<ScheduleResponseDto> getRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("scheduleId");
                String todo = rs.getString("todo");
                String managerName = rs.getString("managerName");
                Object createDate = rs.getObject("createDate");
                Object updateDate = rs.getObject("updateDate");
                return new ScheduleResponseDto(id, todo, managerName, createDate, updateDate);
            }
        };
    }

    //해당 페이지의 일정 목록 가져오기
    public List<ScheduleResponseDto> getschedulespaging(int pagesize, int page) {
        String sql = "SELECT s.scheduleId, s.todo, s.password, s.createDate, s.updateDate, m.managerId, m.name managerName FROM SCHEDULE s JOIN MANAGER m ON s.managerId= m.managerId LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("scheduleId");
                String todo = rs.getString("todo");
                String managerName = rs.getString("managerName");
                LocalDateTime createDate = rs.getObject("createDate", LocalDateTime.class);
                LocalDateTime updateDate = rs.getObject("updateDate", LocalDateTime.class);
                return new ScheduleResponseDto(id, todo, managerName, createDate, updateDate);
            }
        }, pagesize, (page-1)*pagesize);
    }
}
