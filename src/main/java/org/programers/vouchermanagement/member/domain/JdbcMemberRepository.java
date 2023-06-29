package org.programers.vouchermanagement.member.domain;

import org.programers.vouchermanagement.voucher.domain.Voucher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Member save(Member member) {
        String sql = "insert into member (id, status) values (?, ?)";
        jdbcTemplate.update(sql,
                member.getId().toString(),
                member.getStatus().toString());
        return member;
    }

    @Override
    public Optional<Member> findById(UUID id) {
        String sql = "select * from member where id = ?";
        try {
            Member member = jdbcTemplate.queryForObject(sql, memberRowMapper(), id.toString());
            return Optional.of(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    @Override
    public void update(Member member) {
        String sql = "update member set status = ? where id = ?";
        jdbcTemplate.update(sql,
                member.getStatus().toString(),
                member.getId().toString());
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "delete from member where id = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> new Member(
                UUID.fromString(rs.getString("id")),
                MemberStatus.valueOf(rs.getString("status")));
    }
}
