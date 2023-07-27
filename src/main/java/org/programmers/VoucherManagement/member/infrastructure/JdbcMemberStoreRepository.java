package org.programmers.VoucherManagement.member.infrastructure;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.exception.MemberException;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static org.programmers.VoucherManagement.global.response.ErrorCode.*;

@Primary
@Repository
public class JdbcMemberStoreRepository implements MemberStoreRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberStoreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Member insert(Member member) {
        String sql = "insert into member_table(member_id, name, member_status) values (?,?,?)";
        int insertCount = jdbcTemplate.update(sql,
                member.getMemberUUID().toString(),
                member.getName(),
                member.getMemberStatus().toString());

        if (insertCount != 1) {
            throw new MemberException(FAIL_TO_INSERT_MEMBER);
        }
        return member;
    }

    @Override
    public void update(Member member) {
        String sql = "update member_table set name = ?, member_status = ? where member_id = ?";
        int updateCount = jdbcTemplate.update(sql,
                member.getName(),
                member.getMemberStatus().toString(),
                member.getMemberUUID().toString());
        if (updateCount != 1) {
            throw new MemberException(FAIL_TO_UPDATE_MEMBER);
        }
    }

    @Override
    public void delete(UUID memberId) {
        String sql = "delete from member_table where member_id = ?";
        int deleteCount = jdbcTemplate.update(sql,
                memberId.toString());
        if (deleteCount != 1) {
            throw new MemberException(FAIL_TO_DELETE_MEMBER);
        }
    }
}
