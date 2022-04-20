package com.example.voucherproject.user.repository;

import com.example.voucherproject.user.enums.UserType;
import com.example.voucherproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserJdbcRepository implements UserRepository{
    private final JdbcTemplate jdbcTemplate;
    private final String INSERT_SQL = "INSERT INTO users(id, type, name, created_at) VALUES(UNHEX(REPLACE(?,'-','')), ?, ?, ?)";
    private final String FIND_ALL_SQL = "select * from users";

    private final String FIND_BY_TYPE_SQL = "select * from users where type = ?"; // TODO: 동작하려나
    private final String FIND_BY_ID_SQL = "select * from users where id = UNHEX(REPLACE(?,'-',''))";
    private final String COUNT_SQL = "select count(*) from users";
    private final String DELETE_SQL = "DELETE FROM users";

    @Override
    public User insert(User user) {
        var update = jdbcTemplate.update(INSERT_SQL,
                user.getId().toString().getBytes(),
                user.getType().toString(),
                user.getName(),
                Timestamp.valueOf(user.getCreatedAt()));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return user;
    }

    @Override
    public List<User> findHavingTypeAll(UserType type) { //findAll인데 타입이 왜 필요행?
        var users = jdbcTemplate.query(FIND_ALL_SQL, memberRowMapper());
        return users.stream()
                .filter(user -> user.getType()== type)
                .collect(Collectors.toList());
    }

    public int deleteAll() {
        return jdbcTemplate.update(DELETE_SQL);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL,
                    memberRowMapper(),
                    userId.toString().getBytes()));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, memberRowMapper());
    }

    @Override
    public long count() {
        var count = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class);
        return (count != null) ? count.longValue() : 0;
    }

    //TODO: updated At .. User에 Builder 사용하고 싶음 ㅜ ㅜ
    private RowMapper<User> memberRowMapper() {
        return ((rs, rowNum) -> {
            var userId = toUUID(rs.getBytes("id"));
            var type = UserType.valueOf(rs.getString("type"));
            var name = rs.getString("name");
            var createdAt = rs.getTimestamp("created_at").toLocalDateTime();

            var updatedAt = rs.getString("updated_at"); // nullable

            return new User(userId, type, name, createdAt);
        });
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}