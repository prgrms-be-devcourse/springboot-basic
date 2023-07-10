package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.Assignment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JdbcAssignmentRepository implements AssignmentRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcAssignmentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Assignment assignment) {
        String sql = "INSERT INTO assignment (voucher_id, user_id) VALUES (:voucherId, :userId)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", assignment.getVoucherId().toString());
        paramMap.put("userId", assignment.getUserId().toString());
        jdbcTemplate.update(sql, paramMap);
    }

    @Override
    public void free(Assignment assignment) {
        String sql = "UPDATE assignment SET unassigned_time = CURRENT_TIMESTAMP WHERE voucher_id = :voucherId AND user_id = :userId";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", assignment.getVoucherId().toString());
        paramMap.put("userId", assignment.getUserId().toString());
        jdbcTemplate.update(sql, paramMap);
    }
}
