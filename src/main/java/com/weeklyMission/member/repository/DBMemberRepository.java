package com.weeklyMission.member.repository;

import com.weeklyMission.member.domain.Member;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile("prod")
public class DBMemberRepository implements MemberRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DBMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final RowMapper<Member> memberRowMapper = (resultSet, i) -> {
        Long memberId = Long.parseLong(resultSet.getString("member_id"));
        String name = resultSet.getString("name");
        Integer age = Integer.parseInt(resultSet.getString("age"));
        String reason = resultSet.getString("reason");

        return new Member(memberId, name, age, reason);
    };



    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper);
    }
}
