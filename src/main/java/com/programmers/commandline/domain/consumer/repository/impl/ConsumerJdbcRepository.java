package com.programmers.commandline.domain.consumer.repository.impl;

import com.programmers.commandline.domain.consumer.entity.Consumer;
import com.programmers.commandline.domain.consumer.repository.ConsumerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ConsumerJdbcRepository implements ConsumerRepository {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerJdbcRepository.class);

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;
    private static RowMapper<Consumer> consumerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("consumer_id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        new Consumer(customerId, customerName, email, lastLoginAt, createdAt);
        return new Consumer(customerId, customerName, email, lastLoginAt, createdAt);
    };

    public ConsumerJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Consumer insert(Consumer consumer) {
        int update = jdbcTemplate.update("INSERT INTO consumer(consumer_id, name, email, created_at) VALUES (UUID_TO_BIN(?), ?, ?,?)",
                consumer.getConsumerId().toString().getBytes(),
                consumer.getName(),
                consumer.getEmail(),
                Timestamp.valueOf(consumer.getCreatedAt()));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return consumer;
    }

    @Override
    public Consumer update(Consumer consumer) {
        int update = jdbcTemplate.update("UPDATE consumer SET name = ?, email = ?, last_login_at =? WHERE consumer_id = UUID_TO_BIN(?)",
                consumer.getName(),
                consumer.getEmail(),
                consumer.getLastLoginAt(),
                consumer.getConsumerId().toString().getBytes());
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return consumer;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from consumer", Integer.class);
    }

    @Override
    public List<Consumer> findAll() {
        return jdbcTemplate.query("select * from consumer", consumerRowMapper);
    }

    private void mapToCustomer(List<Consumer> allConsumer, ResultSet resultSet) throws SQLException {
        UUID customerId = toUUID(resultSet.getBytes("consumer_id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        allConsumer.add(new Consumer(customerId, customerName, email, lastLoginAt, createdAt));
    }

    @Override
    public Optional<Consumer> findById(UUID consumerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from consumer WHERE consumer_id = UUID_TO_BIN(?)"
                    , consumerRowMapper
                    , consumerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Consumer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from consumer WHERE name = ?"
                    , consumerRowMapper
                    , name));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Consumer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from consumer WHERE email = ?"
                    , consumerRowMapper
                    , email));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM consumer");
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
