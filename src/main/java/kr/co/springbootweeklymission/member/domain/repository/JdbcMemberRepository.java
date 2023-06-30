package kr.co.springbootweeklymission.member.domain.repository;

import kr.co.springbootweeklymission.member.domain.entity.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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
        return null;
    }
}
