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
    public List<Member> findMembersByBlack() {
        String sql = "" +
                "select * " +
                "from tbl_members " +
                "where member_status = ?";
        return jdbcTemplate.query(sql, memberRowMapper(), MemberStatus.BLACK.toString());
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

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            if (rs.getString("voucher_id") == null) {
                return Member.builder()
                        .memberId(UUID.fromString(rs.getString("member_id")))
                        .memberStatus(MemberStatus.valueOf(rs.getString("member_status")))
                        .build();
            }

            return Member.builder()
                    .memberId(UUID.fromString(rs.getString("member_id")))
                    .memberStatus(MemberStatus.valueOf(rs.getString("member_status")))
                    .voucherId(UUID.fromString(rs.getString("voucher_id")))
                    .build();
        };
    }
}
