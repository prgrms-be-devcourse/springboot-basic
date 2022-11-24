package com.programmers.commandline.domain.consumer.repository.impl;

import com.programmers.commandline.domain.consumer.entity.Consumer;
import com.programmers.commandline.domain.consumer.repository.ConsumerRepository;
import com.programmers.commandline.global.config.MyDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile("jdbc")
public class ConsumerNamedJdbcRepository implements ConsumerRepository {

    private final RowMapper<Consumer> consumerRowMapper = (resultSet, i) -> {
        UUID id = UUID.fromString(resultSet.getString("id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime createdAt = LocalDateTime.parse(resultSet.getString("created_at"));
        LocalDateTime lastLoginAt = resultSet.getString("last_login_at") != null ?
                LocalDateTime.parse(resultSet.getString("last_login_at")) : null;
        return new Consumer(id, name, email, createdAt, lastLoginAt);
    };
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    Logger logger = LoggerFactory.getLogger(ConsumerNamedJdbcRepository.class);

    public ConsumerNamedJdbcRepository(MyDataSource myDataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(myDataSource.getDataSource());
    }

    private Map<String, Object> toParamMap(Consumer consumer) {
        return new HashMap<>() {
            {
                put("id", consumer.getId());
                put("name", consumer.getName());
                put("email", consumer.getEmail());
                put("createdAt", consumer.getCreatedAt());
                put("lastLoginAt", consumer.getLastLoginAt());
            }
        };
    }

    @Override
    public Consumer insert(Consumer consumer) {
        String sql = "INSERT INTO consumer(id, name, email, created_at, last_login_at) " +
                "VALUES (:id, :name, :email, :createdAt, :lastLoginAt)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", consumer.getId())
                .addValue("name", consumer.getName())
                .addValue("email", consumer.getEmail())
                .addValue("createdAt", consumer.getCreatedAt())
                .addValue("lastLoginAt", consumer.getLastLoginAt());

        int update = namedParameterJdbcTemplate.update(sql, sqlParameterSource);

        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return consumer;
    }

    @Override
    public Consumer update(Consumer consumer) {
        String sql = "UPDATE consumer SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE id = :id";
        int update = namedParameterJdbcTemplate.update(sql, toParamMap(consumer));

        if (update != 1) {
            throw new RuntimeException("Noting was updated");
        }
        return consumer;
    }

    @Override
    public int count() {
        String sql = "select count(*) from consumer";
        return namedParameterJdbcTemplate.queryForObject(sql, Collections.emptyMap(), Integer.class);
    }

    @Override
    public List<Consumer> findAll() {
        String sql = "select * from consumer";
        return namedParameterJdbcTemplate.query(sql, consumerRowMapper);
    }

    @Override
    public Optional<Consumer> findById(String consumerId) {
        try {
            String sql = "select * from consumer WHERE id = :id";
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                    sql,
                    Collections.singletonMap("id", consumerId),
                    consumerRowMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Consumer> findByName(String name) {
        try {
            String sql = "select * from consumer WHERE name = :name";
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                    sql,
                    Collections.singletonMap("name", name),
                    consumerRowMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Consumer> findByEmail(String email) {
        try {
            String sql = "select * from consumer WHERE email = :email";
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                    sql,
                    Collections.singletonMap("email", email),
                    consumerRowMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM consumer";
        namedParameterJdbcTemplate.update(sql, Collections.emptyMap());
    }
}
