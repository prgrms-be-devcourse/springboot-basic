package org.programmers.VoucherManagement.member.infrastructure;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.exception.MemberException;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        String sql = "INSERT INTO member_table(member_id, name, member_status, created_at) VALUES (?,?,?,?)";
        int insertCount = jdbcTemplate.update(sql,
                member.getMemberId(),
                member.getName(),
                member.getMemberStatus().toString(),
                member.getCreatedAt()
        );

        if (insertCount != 1) {
            throw new MemberException(FAIL_TO_INSERT_MEMBER);
        }
        return member;
    }

    @Override
    public void update(Member member) {
        String sql = "UPDATE member_table SET name = ?, member_status = ? WHERE member_id = ?";
        int updateCount = jdbcTemplate.update(sql,
                member.getName(),
                member.getMemberStatus().toString(),
                member.getMemberId());
        if (updateCount != 1) {
            throw new MemberException(FAIL_TO_UPDATE_MEMBER);
        }
    }

    @Override
    public void delete(String memberId) {
        String sql = "DELETE FROM member_table WHERE member_id = ?";
        int deleteCount = jdbcTemplate.update(sql,
                memberId);
        if (deleteCount != 1) {
            throw new MemberException(FAIL_TO_DELETE_MEMBER);
        }
    }
}
