package com.pppp0722.vouchermanagement.member.repository;

import static com.pppp0722.vouchermanagement.util.JdbcUtils.toUUID;

import com.pppp0722.vouchermanagement.member.model.Member;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcMemberRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Member insert(Member member) {
        try {
            int update = jdbcTemplate.update(
                "INSERT INTO members(member_id, name) VALUES(UNHEX(REPLACE(:memberId, '-', '')), :name)",
                toParamMap(member));

            if (update != 1) {
                throw new RuntimeException("Nothing gets inserted!");
            }

            return member;
        } catch (RuntimeException e) {
            logger.error("Failed to insert member!", e);
            throw e;
        }
    }

    @Override
    public List<Member> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM members", memberRowMapper);
        } catch (RuntimeException e) {
            logger.error("Failed to find all members!", e);
            throw e;
        }
    }

    @Override
    public Optional<Member> findById(UUID memberId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "SELECT * FROM members WHERE member_id = UNHEX(REPLACE(:memberId, '-', ''))",
                    Collections.singletonMap("memberId", memberId.toString().getBytes()),
                    memberRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Member update(Member member) {
        try {
            int update = jdbcTemplate.update(
                "UPDATE members SET name = :name WHERE member_id = UNHEX(REPLACE(:memberId, '-', ''))",
                toParamMap(member));

            if (update != 1) {
                throw new RuntimeException("Nothing gets updated!");
            }

            return member;
        } catch (RuntimeException e) {
            logger.error("Failed to update member!", e);
            throw e;
        }
    }

    @Override
    public Member delete(Member member) {
        try {
            int update = jdbcTemplate.update(
                "DELETE FROM members WHERE member_id = UNHEX(REPLACE(:memberId, '-', ''))",
                toParamMap(member));

            if (update != 1) {
                throw new RuntimeException("Nothing gets deleted!");
            }

            return member;
        } catch (RuntimeException e) {
            logger.error("Failed to delete member!", e);
            throw e;
        }
    }

    @Override
    public void deleteAll() {
        try {
            jdbcTemplate.update("DELETE FROM members", Collections.emptyMap());
        } catch (RuntimeException e) {
            logger.error("Failed to delete all members!");
            throw e;
        }
    }

    private static final RowMapper<Member> memberRowMapper = (resultSet, i) -> {
        UUID memberId = toUUID(resultSet.getBytes("member_id"));
        String name = resultSet.getString("name");

        return new Member(memberId, name);
    };

    private Map<String, Object> toParamMap(Member member) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberId", member.getMemberId().toString().getBytes());
        paramMap.put("name", member.getName());

        return paramMap;
    }
}
