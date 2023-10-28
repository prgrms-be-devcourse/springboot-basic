package com.weeklyMission.member.repository;

import com.weeklyMission.member.domain.Member;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile("prod")
public class DBMemberRepository implements MemberRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DBMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final RowMapper<Member> memberRowMapper = (resultSet, i) -> {
        UUID memberId = toUUID(resultSet.getBytes("member_id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        Integer age = Integer.parseInt(resultSet.getString("age"));

        return new Member(memberId, name, email, age);
    };

    private Map<String, Object> toParamMap(Member member){
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", member.memberId().toString().getBytes());
        map.put("name", member.name());
        map.put("email", member.email());
        map.put("age", member.age());
        return map;
    }

    @Override
    public Member save(Member member) {
        jdbcTemplate.update(
            "INSERT INTO members (member_id, name, email, age) VALUES (UUID_TO_BIN(:memberId), :name, :email, :age)",
            toParamMap(member));
        return member;
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from members", memberRowMapper);
    }

    @Override
    public Optional<Member> findById(UUID id) {
        Member member;
        try{
            member = jdbcTemplate.queryForObject("select * from members where member_id = UUID_TO_BIN(:memberId)",
                Collections.singletonMap("memberId", id.toString().getBytes()), memberRowMapper);
        } catch (EmptyResultDataAccessException de){
            throw new NoSuchElementException("존재하지 않는 id 입니다.", de);
        }
        return Optional.ofNullable(member);
    }

    public List<Member> findByIds(List<UUID> idList){
        List<byte[]> idByteList = idList.stream()
            .map(id -> id.toString().getBytes())
            .toList();

        List<Member> members = jdbcTemplate.query(
            "select * from members where member_id in (:memberIds)",
            Collections.singletonMap("memberIds", idByteList), memberRowMapper);

        return members;
    }

    @Override
    public void deleteById(UUID id) {
        findById(id);
        jdbcTemplate.update("delete from members where member_id = UUID_TO_BIN(:memberId)",
            Collections.singletonMap("memberId", id.toString().getBytes()));
    }

    static UUID toUUID(byte[] bytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
