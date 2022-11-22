package com.programmers.commandline.domain.consumer.repository.impl;

import com.programmers.commandline.domain.consumer.entity.Consumer;
import com.programmers.commandline.domain.consumer.repository.ConsumerRepository;
import com.programmers.commandline.global.config.MyDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
@Profile("local")
public class ConsumerNamedJdbcRepository implements ConsumerRepository {

    Logger logger = LoggerFactory.getLogger(ConsumerNamedJdbcRepository.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ConsumerNamedJdbcRepository(MyDataSource myDataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(myDataSource.getDataSource());
    }

    private static final RowMapper<Consumer> consumerRowMapper = (resultSet, i) -> {
        var consumerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var consumerId = toUUID(resultSet.getBytes("consumer_id"));
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Consumer(consumerId, consumerName, email, lastLoginAt, createdAt);
    };

    private Map<String, Object> toParamMap(Consumer consumer) {
        return new HashMap<>() {{
            put("consumerId", consumer.getConsumerId().toString().getBytes());
            put("name", consumer.getName());
            put("email", consumer.getEmail());
            put("cratedAt", Timestamp.valueOf(consumer.getCreatedAt()));
            put("lastLoginAt", consumer.getLastLoginAt() != null ? Timestamp.valueOf(consumer.getLastLoginAt()) : null);
        }};
    }

    @Override
    public Consumer insert(Consumer consumer) {
        var update = namedParameterJdbcTemplate.update("INSERT INTO consumer(consumer_id, name, email, created_at) VALUES (UUID_TO_BIN(:consumerId), :name, :email, :cratedAt)",
                toParamMap(consumer));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return consumer;
    }

    @Override
    public Consumer update(Consumer consumer) {
        var update = namedParameterJdbcTemplate.update("UPDATE consumer SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)",
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
    public Optional<Consumer> findById(UUID consumerId) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("select * from consumer WHERE consumer_id = UUID_TO_BIN(:consumerId)",
                    Collections.singletonMap("consumerId", consumerId.toString().getBytes()),
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
