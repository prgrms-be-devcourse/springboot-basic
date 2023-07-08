package org.prgrms.kdt.member.dao;

import org.prgrms.kdt.exception.NotUpdateException;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberName;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(JdbcMemberRepository.class);
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Member> memberRowMapper = (resultSet, i) -> {
        UUID memberId = UUID.fromString(resultSet.getString("id"));
        String name = resultSet.getString("name");
        MemberStatus status = MemberStatus.getStatus(resultSet.getString("status"));
        return new Member(memberId, name, status);
    };

    public JdbcMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member insert(Member member) {
        String sql = "INSERT INTO member(id, name, status) VALUES (?, ?, ?)";
        int update = jdbcTemplate.update(sql, member.getMemberId().toString(),
                member.getMemberName().getName(),
                member.getStatus().getDescripton());
        if (update != 1) {
            throw new NotUpdateException("insert가 제대로 이루어지지 않았습니다.");
        }
        return member;
    }

    @Override
    public Member update(Member member) {
        String sql = "UPDATE member SET name = ?, status = ? WHERE id = ?";
        int update = jdbcTemplate.update(sql, member.getMemberName().getName(), member.getStatus().getDescripton(), member.getMemberId().toString());
        if (update != 1) {
            throw new NotUpdateException("update가 제대로 이루어지지 않았습니다.");
        }
        return member;
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        return jdbcTemplate.query(sql, memberRowMapper);
    }

    @Override
    public Optional<Member> findById(UUID memberId) {
        String sql = "select * from member WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    memberRowMapper,
                    memberId.toString()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findByName(MemberName memberName) {
        String sql = "select * from member WHERE name = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    memberRowMapper,
                    memberName.getName()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findAllBlackMember() {
        String sql = "select * from member WHERE status = 'BLACK'";
        return jdbcTemplate.query(sql, memberRowMapper);
    }
}
