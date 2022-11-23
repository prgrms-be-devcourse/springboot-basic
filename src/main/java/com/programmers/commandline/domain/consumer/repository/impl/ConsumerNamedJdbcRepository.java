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

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile("jdbc")
public class ConsumerNamedJdbcRepository implements ConsumerRepository {

    Logger logger = LoggerFactory.getLogger(ConsumerNamedJdbcRepository.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ConsumerNamedJdbcRepository(MyDataSource myDataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(myDataSource.getDataSource());
    }

    private static final RowMapper<Consumer> consumerRowMapper = (resultSet, i) -> {
        var consumerId = toUUID(resultSet.getBytes("consumer_id"));
        var consumerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        LocalDateTime createdAt = LocalDateTime.parse(resultSet.getString("created_at"));
        LocalDateTime lastLoginAt = resultSet.getString("last_login_at") != null ?
                LocalDateTime.parse(resultSet.getString("last_login_at")) : null;
        return new Consumer(consumerId, consumerName, email, createdAt, lastLoginAt);
    };

    private Map<String, Object> toParamMap(Consumer consumer) {
        return new HashMap<>() {{
            put("consumerId", consumer.getConsumerId());
            put("name", consumer.getName());
            put("email", consumer.getEmail());
            put("createdAt", Timestamp.valueOf(consumer.getCreatedAt()));
            put("lastLoginAt", consumer.getLastLoginAt() != null ? Timestamp.valueOf(consumer.getLastLoginAt()) : null);
        }};
    }

    @Override
    public Consumer insert(Consumer consumer) {
        String sql = "INSERT INTO consumer(consumer_id, name, email, created_at, last_login_at) " +
                "VALUES (:consumerId, :name, :email, :createdAt, :lastLoginAt)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("consumerId", consumer.getConsumerId())
                .addValue("name", consumer.getName())
                .addValue("email", consumer.getEmail())
                .addValue("createdAt", Timestamp.valueOf(consumer.getCreatedAt()).toLocalDateTime())
                .addValue("lastLoginAt", consumer.getLastLoginAt() != null ?
                        Timestamp.valueOf(consumer.getLastLoginAt()) : null);

        var update = namedParameterJdbcTemplate.update(sql, sqlParameterSource);

        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return consumer;
    }

    @Override
    public Consumer update(Consumer consumer) {
        int update = namedParameterJdbcTemplate.update("UPDATE consumer SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE consumer_id = :consumerId",
                toParamMap(consumer)
        );

        if (update != 1) {
            throw new RuntimeException("Noting was updated");
        }
        return consumer;
    }

    @Override
    public int count() {
        return namedParameterJdbcTemplate.queryForObject("select count(*) from consumer", Collections.emptyMap(), Integer.class);
    }

    @Override
    public List<Consumer> findAll() {
        return namedParameterJdbcTemplate.query("select * from consumer", consumerRowMapper);
    }

    @Override
    public Optional<Consumer> findById(String consumerId) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("select * from consumer WHERE consumer_id = :consumerId",
                    Collections.singletonMap("consumerId", consumerId),
                    consumerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Consumer> findByName(String name) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("select * from consumer WHERE name = :name",
                    Collections.singletonMap("name", name),
                    consumerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Consumer> findByEmail(String email) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("select * from consumer WHERE email = :email",
                    Collections.singletonMap("email", email),
                    consumerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        namedParameterJdbcTemplate.update("DELETE FROM consumer", Collections.emptyMap());
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
