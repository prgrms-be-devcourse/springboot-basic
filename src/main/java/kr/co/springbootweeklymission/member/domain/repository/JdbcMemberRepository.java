package kr.co.springbootweeklymission.member.domain.repository;

import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.model.MemberStatus;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class JdbcMemberRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "" +
                "insert into tbl_members (member_id, member_status) " +
                "values (?, ?)";
        jdbcTemplate.update(
                sql,
                member.getMemberId().toString(),
                member.getMemberStatus().toString()
        );
        return member;
    }

    @Override
    public Optional<Member> findById(UUID memberId) {
        String sql = "" +
                "select * " +
                "from tbl_members " +
                "where member_id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                    sql,
                    memberRowMapper(),
                    memberId.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findBlackMembers() {
        String sql = "" +
                "select * " +
                "from tbl_members " +
                "where member_status = ?";
        return jdbcTemplate.query(sql, memberRowMapper(), MemberStatus.BLACK.toString());
    }

    @Override
    public List<Member> findAll() {
        String sql = "" +
                "select * " +
                "from tbl_members";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    @Override
    public void update(Member member) {
        String sql = "" +
                "update tbl_members set " +
                "member_status = ? " +
                "where member_id = ?";
        jdbcTemplate.update(
                sql,
                member.getMemberStatus().toString(),
                member.getMemberId().toString()
        );
    }

    @Override
    public void delete(Member member) {
        String sql = "" +
                "delete from tbl_members " +
                "where member_id = ?";
        jdbcTemplate.update(sql, member.getMemberId().toString());
    }

    public static RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> Member.builder()
                .memberId(UUID.fromString(rs.getString("member_id")))
                .memberStatus(MemberStatus.valueOf(rs.getString("member_status")))
                .build();
    }
}
