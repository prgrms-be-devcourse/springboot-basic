package com.pppp0722.vouchermanagement.member.repository;

import static com.pppp0722.vouchermanagement.util.Util.toUUID;

import com.pppp0722.vouchermanagement.member.model.Member;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Map<String, Object> toParamMap(Member member) {
        return new HashMap<>() {{
            put("memberId", member.getMemberId().toString().getBytes());
            put("name", member.getName());
        }};
    }

    private static final RowMapper<Member> memberRowMapper = (resultSet, i) -> {
        UUID memberId = toUUID(resultSet.getBytes("member_id"));
        String name = resultSet.getString("name");

        return new Member(memberId, name);
    };

    @Override
    public Optional<Member> createMember(Member member) {
        try {
            int update = jdbcTemplate.update(
                "INSERT INTO members(member_id, name) VALUES(UNHEX(REPLACE(:memberId, '-', '')), :name)",
                toParamMap(member));

            if (update != 1) {
                logger.error("Nothing was created! (Member)");
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            logger.error("Can not create member!", e);
            return Optional.empty();
        }

        return Optional.of(member);
    }

    @Override
    public List<Member> readMembers() {
        List<Member> members;
        try {
            members = jdbcTemplate.query("SELECT * FROM members", memberRowMapper);
        } catch (RuntimeException e) {
            logger.error("Can not read members!", e);
            members = new ArrayList<>();
        }
        return members;
    }

    @Override
    public Optional<Member> readMember(UUID memberId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "SELECT * FROM members WHERE member_id = UNHEX(REPLACE(:memberId, '-', ''))",
                    Collections.singletonMap("memberId", memberId.toString().getBytes()),
                    memberRowMapper));
        } catch (RuntimeException e) {
            logger.error("Can not read member!", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> updateMember(Member member) {
        try {
            int update = jdbcTemplate.update(
                "UPDATE members SET name = :name WHERE member_id = UNHEX(REPLACE(:memberId, '-', ''))",
                toParamMap(member));

            if (update != 1) {
                logger.error("Nothing was updated! (Member)");
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            logger.error("Can not update member!", e);
            return Optional.empty();
        }

        return Optional.of(member);
    }

    @Override
    public Optional<Member> deleteMember(Member member) {
        try {
            int update = jdbcTemplate.update(
                "DELETE FROM members WHERE member_id = UNHEX(REPLACE(:memberId, '-', ''))",
                toParamMap(member));

            if (update != 1) {
                logger.error("Nothing was deleted! (Member)");
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            logger.error("Can not delete member!", e);
            return Optional.empty();
        }

        return Optional.of(member);
    }
}
