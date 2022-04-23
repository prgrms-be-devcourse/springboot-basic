package org.programmers.springbootbasic.member.repository;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.member.domain.Member;
import org.programmers.springbootbasic.member.domain.SignedMember;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class JdbcTemplateMemberRepository implements MemberRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public static final String PARAM_KEY_NAME = "name";
    public static final String PARAM_KEY_EMAIL = "email";
    public static final String PARAM_KEY_SIGNED_UP_AT = "signedUpAt";
    public static final String PARAM_KEY_MEMBER_ID = "memberId";

    private static final String INSERT_SQL =
            "INSERT into member(name, email, signed_up_at) values (:"
                    + PARAM_KEY_NAME + ", :" + PARAM_KEY_EMAIL + ", :" + PARAM_KEY_SIGNED_UP_AT + ")";
    private static final String FIND_BY_ID_SQL =
            "SELECT * from member WHERE member_id = :" + PARAM_KEY_MEMBER_ID;
    private static final String FIND_ALL_SQL = "SELECT * from member";
    private static final String REMOVE_SQL = "DELETE from member WHERE member_id = :" + PARAM_KEY_MEMBER_ID;

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Member insert(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updatedRow = jdbcTemplate.update(INSERT_SQL, toParamMap(member), keyHolder);
        if (1 != updatedRow) {
            log.error("소비자가 정상적으로 저장되지 않았습니다. updatedRow={}", updatedRow);
            throw new IncorrectResultSizeDataAccessException(updatedRow);
        }
        Long memberId = keyHolder.getKey().longValue();

        return new SignedMember(memberId, member.getName(), member.getEmail(), member.getLastLoginAt(), member.getSignedUpAt());
    }

    private SqlParameterSource toParamMap(Member member) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue(PARAM_KEY_NAME, member.getName());
        paramSource.addValue(PARAM_KEY_EMAIL, member.getEmail());
        paramSource.addValue(PARAM_KEY_SIGNED_UP_AT, member.getSignedUpAt());

        return paramSource;
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        var paramSource = new MapSqlParameterSource(PARAM_KEY_MEMBER_ID, memberId);
        List<Member> result = jdbcTemplate.query(FIND_BY_ID_SQL, paramSource, memberRowMapper());
        switch (result.size()) {
            case 0:
                return Optional.empty();
            case 1:
                return Optional.of(result.get(0));
            default:
                log.error("동일 id에 2개 이상의 결과가 조회되었습니다. 해당 memberId={}, 조회 결과 수={}", memberId, result.size());
                throw new IncorrectResultSizeDataAccessException(result.size());
        }
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            long memberId = rs.getLong("member_id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            var lastLoginAt = rs.getTimestamp("LAST_LOGIN_AT") != null ?
                    rs.getTimestamp("LAST_LOGIN_AT").toLocalDateTime() : null;
            var signedUpAt = rs.getTimestamp("SIGNED_UP_AT").toLocalDateTime();

            return new SignedMember(memberId, name, email, lastLoginAt, signedUpAt);
        };
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, memberRowMapper());
    }

    @Override
    public void remove(Long memberId) {
        var paramSource = new MapSqlParameterSource(PARAM_KEY_MEMBER_ID, memberId);
        int deletedRow = jdbcTemplate.update(REMOVE_SQL, paramSource);
        if (deletedRow != 1) {
            log.error("소비자가 정상적으로 삭제되지 않았습니다. deletedRow={}", deletedRow);
            throw new IllegalStateException("소비자가 정상적으로 삭제되지 않았습니다.");
        }
    }
}
