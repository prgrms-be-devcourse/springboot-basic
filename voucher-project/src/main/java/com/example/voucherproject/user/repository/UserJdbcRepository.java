package com.example.voucherproject.user.repository;

import com.example.voucherproject.user.model.UserType;
import com.example.voucherproject.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserJdbcRepository implements UserRepository{
    private final JdbcTemplate jdbcTemplate;
    private final String INSERT_SQL = "INSERT INTO user(id, type, name, created_at) VALUES(UNHEX(REPLACE(?,'-','')), ?, ?, ?)";
    private final String FIND_ALL_SQL = "select * from user";
    private final String FIND_BY_ID_SQL = "select * from user where id = UNHEX(REPLACE(?,'-',''))";
    private final String COUNT_SQL = "select count(*) from user";
    private final String DELETE_SQL = "DELETE FROM user";
    private final String DELETE_BY_ID_SQL = "DELETE FROM user WHERE id = UNHEX(REPLACE(?,'-',''))";

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
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, memberRowMapper());
    }

    @Override
    public List<User> findAllByUserType(UserType type) {
        var users = jdbcTemplate.query(FIND_ALL_SQL, memberRowMapper());
        return users.stream()
                .filter(user -> user.getType()== type)
                .collect(Collectors.toList());
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
    public int deleteById(UUID id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id.toString().getBytes());
    }

    public int deleteAll() {
        return jdbcTemplate.update(DELETE_SQL);
    }

    @Override
    public long count() {
        var count = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class);
        return (count != null) ? count.longValue() : 0;
    }

    @Override
    public List<User> findByTypeAndDate(UserType type, String from, String to) {
        var users = jdbcTemplate.query(FIND_ALL_SQL, memberRowMapper());
        var ff = from + "T00:00:00.000";
        var tt = to + "T00:00:00.000";

        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        var start = LocalDateTime.parse(ff, formatter);
        var end = LocalDateTime.parse(tt, formatter);
        System.out.println("start : "+ start.toString());
        System.out.println("end : "+ end.toString());

        if(type==UserType.ALL){
            var listUsers =  users.stream()
                    .filter(user -> user.getCreatedAt().isAfter(start))
                    .filter(user -> user.getCreatedAt().isBefore(end))
                    .collect(Collectors.toList());
            System.out.println(listUsers);
            System.out.println("nums : "+listUsers.size());
            return listUsers;
        }

        var listUsers2 = users.stream()
                .filter(user -> user.getType() == type)
                .filter(user -> user.getCreatedAt().isAfter(start))
                .filter(user -> user.getCreatedAt().isBefore(end))
                .collect(Collectors.toList());
        System.out.println(listUsers2);
        System.out.println("nums : "+listUsers2.size());
        return listUsers2;

    }

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