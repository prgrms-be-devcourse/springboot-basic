package com.weeklyMission.member.repository;

import com.weeklyMission.exception.AlreadyExistsException;
import com.weeklyMission.member.domain.Member;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile("prod")
public class DBMemberRepository implements MemberRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DBMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final RowMapper<Member> memberRowMapper = (resultSet, i) -> {
        String memberId = resultSet.getString("member_id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        Integer age = Integer.parseInt(resultSet.getString("age"));

        return new Member(memberId, name, email, age);
    };

    private Map<String, Object> toParamMap(Member member) {
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", member.memberId());
        map.put("name", member.name());
        map.put("email", member.email());
        map.put("age", member.age());
        return map;
    }

    @Override
    public Member save(Member member) {
        jdbcTemplate.update(
            "INSERT INTO members (member_id, name, email, age) VALUES (:memberId, :name, :email, :age)",
            toParamMap(member));
        return member;
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from members", memberRowMapper);
    }

    @Override
    public Optional<Member> findById(String id) {
        Member member;
        try {
            member = jdbcTemplate.queryForObject(
                "select * from members where member_id = :memberId",
                Collections.singletonMap("memberId", id), memberRowMapper);
        } catch (EmptyResultDataAccessException de) {
            return Optional.empty();
        }
        return Optional.ofNullable(member);
    }

    public List<Member> findByIds(List<String> ids) {
        List<Member> members;
        try {
            members = jdbcTemplate.query(
                "select * from members where member_id in (:memberIds)",
                Collections.singletonMap("memberIds", ids), memberRowMapper);
        } catch (Exception e) {
            return new ArrayList<>();
        }

        return members;
    }

    @Override
    public void deleteById(String id) {
        jdbcTemplate.update("delete from members where member_id = :memberId",
            Collections.singletonMap("memberId", id));
    }

    @Override
    public Boolean checkJoinEmail(Member member) {
        return jdbcTemplate.queryForObject(
            "select exists(select * from members where email = :email)",
            toParamMap(member), Boolean.class);
    }
}
