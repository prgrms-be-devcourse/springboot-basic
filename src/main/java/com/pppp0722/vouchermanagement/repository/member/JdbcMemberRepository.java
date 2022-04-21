package com.pppp0722.vouchermanagement.repository.member;

import static com.pppp0722.vouchermanagement.util.Util.toUUID;

import com.pppp0722.vouchermanagement.io.Console;
import com.pppp0722.vouchermanagement.entity.member.Member;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcMemberRepository.class);

    private final Console console = Console.getInstance();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(
        NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Member member) {
        return new HashMap<>() {{
            put("memberId", member.getMemberId().toString().getBytes());
            put("name", member.getName());
        }};
    }

    private static final RowMapper<Member> memberRowMapper = (resultSet, i) -> {
        UUID memberId = toUUID(resultSet.getBytes("member_id"));
        String name = resultSet.getString("name");

        return new Member(memberId, name);
    };

    private static final RowMapper<UUID> walletIdRowMapper = (resultSet, i) -> {
        UUID memberId = toUUID(resultSet.getBytes("wallet_id"));

        return memberId;
    };

    @Override
    public Member createMember(Member member) {
        int update = jdbcTemplate.update(
            "INSERT INTO members(member_id, name) VALUES(UNHEX(REPLACE(:memberId, '-', '')), :name)",
            toParamMap(member));

        if (update != 1) {
            logger.error("Nothing was created! (Member)");
            console.printError("Nothing was created (Member)");
        }

        return member;
    }

    @Override
    public List<Member> readMembers() {
        List<Member> members = jdbcTemplate.query("SELECT * FROM members", memberRowMapper);

        return members;
    }

    @Override
    public Optional<Member> readMember(UUID memberId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "SELECT * FROM members WHERE member_id = UNHEX(REPLACE(:memberId, '-', ''))",
                    Collections.singletonMap("memberId", memberId.toString().getBytes()),
                    memberRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result! (Member)");
            console.printError("Got empty result! (Member)");

            return Optional.empty();
        }
    }

    @Override
    public Member updateMember(Member member) {
        int update = jdbcTemplate.update(
            "UPDATE members SET name = :name WHERE member_id = UNHEX(REPLACE(:memberId, '-', ''))",
            toParamMap(member));

        if (update != 1) {
            logger.error("Nothing was updated! (Member)");
            console.printError("Nothing was updated! (Member)");
        }

        return member;
    }

    @Override
    public Member deleteMember(Member member) {
        int update = jdbcTemplate.update(
            "DELETE FROM members WHERE member_id = UNHEX(REPLACE(:memberId, '-', ''))",
            toParamMap(member));

        if (update != 1) {
            logger.error("Nothing was deleted! (Member)");
            console.printError("Nothing was deleted! (Member)");
        }

        return member;
    }

    @Override
    public void updateWallet(UUID memberId, UUID walletId) {
        int update = jdbcTemplate.update(
            "UPDATE members SET wallet_id = UNHEX(REPLACE(:walletId, '-', '')) WHERE member_id = UNHEX(REPLACE(:memberId, '-', '')) AND wallet_id is null",
            new HashMap<>() {{
                put("memberId", memberId.toString().getBytes());
                put("walletId", walletId.toString().getBytes());
            }});

        if (update != 1) {
            logger.error("Nothing was updated! (Member)");
            console.printError("Nothing was updated! (Member)");
        }
    }

    @Override
    public Optional<UUID> readWalletId(UUID memberId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "SELECT wallet_id FROM members WHERE member_id = UNHEX(REPLACE(:memberId, '-', ''))",
                    Collections.singletonMap("memberId", memberId.toString().getBytes()),
                    walletIdRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result! (Member)");
            console.printError("Got empty result! (Member)");

            return Optional.empty();
        }
    }
}
