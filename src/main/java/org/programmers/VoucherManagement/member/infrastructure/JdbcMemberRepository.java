package org.programmers.VoucherManagement.member.infrastructure;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;
import org.programmers.VoucherManagement.member.exception.MemberException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.programmers.VoucherManagement.member.exception.MemberExceptionMessage.*;

@Primary
@Repository
public class JdbcMemberRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member_table";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    @Override
    public List<Member> findAllByMemberStatus() {
        String sql = "select * from member_table where member_status = 'BLACK'";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    @Override
    public Optional<Member> findById(UUID memberId) {
        String sql = "select * from member_table where member_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    memberRowMapper(),
                    memberId.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void insert(Member member) {
        String sql = "insert into member_table(member_id, name, member_status) values (?,?,?)";
        int insertCount = jdbcTemplate.update(sql,
                member.getMemberUUID().toString(),
                member.getName(),
                member.getMemberStatus().toString());

        if (insertCount != 1) {
            throw new MemberException(FAIL_TO_INSERT);
        }
    }

    @Override
    public void update(Member member) {
        String sql = "update member_table set name = ?, member_status = ? where member_id = ?";
        int updateCount = jdbcTemplate.update(sql,
                member.getName(),
                member.getMemberStatus().toString(),
                member.getMemberUUID().toString());
        if (updateCount != 1) {
            throw new MemberException(FAIL_TO_UPDATE);
        }
    }

    @Override
    public void delete(Member member) {
        String sql = "delete from member_table where member_id = ?";
        int deleteCount = jdbcTemplate.update(sql,
                member.getMemberUUID().toString());
        if (deleteCount != 1) {
            throw new MemberException(FAIL_TO_DELETE);
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
