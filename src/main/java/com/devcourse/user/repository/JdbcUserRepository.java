package com.devcourse.user.repository;

import com.devcourse.global.sql.Delete;
import com.devcourse.global.sql.Insert;
import com.devcourse.global.sql.Select;
import com.devcourse.global.sql.Update;
import com.devcourse.global.sql.Where;
import com.devcourse.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
class JdbcUserRepository implements UserRepository {
    private final RowMapper<User> userMapper = (resultSet, resultNumber) -> {
        UUID id = UUID.fromString(resultSet.getString("id"));
        String name = resultSet.getString("name");
        return new User(id, name);
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID save(String name) {
        UUID id = UUID.randomUUID();
        Insert insert = Insert.builder()
                .into(User.class)
                .values("id", "name")
                .build();

        jdbcTemplate.update(insert.getQuery(), id.toString(), name);
        return id;
    }

    @Override
    public List<User> findAll() {
        Select select = Select.builder()
                .select(User.class)
                .build();

        return jdbcTemplate.query(select.getQuery(), userMapper);
    }

    @Override
    public Optional<User> findById(UUID id) {
        Select select = Select.builder()
                .select(User.class)
                .where(
                        Where.builder()
                                .condition("id")
                                .build()
                ).build();

        return jdbcTemplate.query(select.getQuery(), userMapper, id.toString())
                .stream()
                .findFirst();
    }

    @Override
    public void deleteById(UUID id) {
        Delete delete = Delete.builder()
                .from(User.class)
                .where(
                        Where.builder()
                                .condition("id")
                                .build()
                ).build();

        jdbcTemplate.update(delete.getQuery(), id.toString());
    }

    @Override
    public void update(UUID id, String name) {
        Update update = Update.builder()
                .table(User.class)
                .values("name")
                .where(
                        Where.builder()
                                .condition("id")
                                .build()
                ).build();

        jdbcTemplate.update(update.getQuery(), name, id.toString());
    }
}
