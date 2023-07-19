package org.programmers.VoucherManagement.member.infrastructure;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class JdbcMemberReaderRepository implements MemberReaderRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberReaderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Member> findAll() {
        String sql = "select member_id, member_status, name from member_table";

        return jdbcTemplate.query(sql, memberRowMapper());
    }

    @Override
    public List<Member> findAllByMemberStatus(MemberStatus memberStatus) {
        String sql = "select member_id, member_status, name from member_table where member_status = ?";

        return jdbcTemplate.query(sql, memberRowMapper(), memberStatus.toString());
    }

    @Override
    public Optional<Member> findById(UUID memberId) {
        String sql = "select member_id, member_status, name from member_table where member_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    memberRowMapper(),
                    memberId.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public static RowMapper<Member> memberRowMapper() {
        return (result, rowNum) -> new Member(
                UUID.fromString(result.getString("member_id")),
                result.getString("name"),
                MemberStatus.from(result.getString("member_status"))
        );
    }
}
