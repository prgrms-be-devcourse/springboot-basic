package com.example.voucherproject.user.repository;

import com.example.voucherproject.user.dto.UserDTO;
import com.example.voucherproject.user.model.UserType;
import com.example.voucherproject.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.voucherproject.common.Utils.*;

@RequiredArgsConstructor
public class UserJdbcRepository implements UserRepository{
    private final JdbcTemplate jdbcTemplate;
    private final String INSERT_SQL = "INSERT INTO user(id, type, name, created_at, updated_at) VALUES(UNHEX(REPLACE(?,'-','')), ?, ?, ?, ?)";
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
                Timestamp.valueOf(user.getCreatedAt()),
                Timestamp.valueOf(user.getUpdatedAt()));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, rowMapper());
    }

    @Override
    public List<User> findAllByUserType(UserType type) {
        var users = jdbcTemplate.query(FIND_ALL_SQL, rowMapper());
        return users.stream()
                .filter(user -> user.getType()== type)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(UUID userId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL,
                    rowMapper(),
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
    public List<User> findByTypeAndDate(UserDTO.Query query) {
        var users = jdbcTemplate.query(FIND_ALL_SQL, rowMapper());

        if(query.getType()==UserType.ALL){
            var listUsers =  users.stream()
                    .filter(user -> user.getCreatedAt().isAfter(convertLocalDateTimeFrom(query.getFrom())))
                    .filter(user -> user.getCreatedAt().isBefore(convertLocalDateTimeTo(query.getTo())))
                    .collect(Collectors.toList());
            return listUsers;
        }

        var listUsers2 = users.stream()
                .filter(user -> user.getType() == query.getType())
                .filter(user -> user.getCreatedAt().isAfter(convertLocalDateTimeFrom(query.getFrom())))
                .filter(user -> user.getCreatedAt().isBefore(convertLocalDateTimeTo(query.getTo())))
                .collect(Collectors.toList());
        return listUsers2;
    }

    private RowMapper<User> rowMapper() {
        return ((resultSet, rowNum) -> {
            var userId = toUUID(resultSet.getBytes("id"));
            var type = UserType.valueOf(resultSet.getString("type"));
            var name = resultSet.getString("name");
            var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            var updatedAt = resultSet.getTimestamp("updated_at") != null ?
                    resultSet.getTimestamp("updated_at").toLocalDateTime() : null;
            return new User(userId, type, name, createdAt, updatedAt);
        });
    }


}