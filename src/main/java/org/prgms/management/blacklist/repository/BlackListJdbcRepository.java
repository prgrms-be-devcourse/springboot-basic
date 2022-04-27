package org.prgms.management.blacklist.repository;

import org.prgms.management.blacklist.entity.Blacklist;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
@Profile({"local-db", "dev"})
public class BlackListJdbcRepository implements BlackListRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BlackListJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Blacklist> rowMapper = (resultSet, i) -> {
        var blacklistId = toUUID(resultSet.getBytes("blacklist_id"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Blacklist(blacklistId, customerId, createdAt);
    };

    @Override
    public Optional<Blacklist> insert(Blacklist blacklist) {
        var executeUpdate = jdbcTemplate.update(
                "INSERT INTO blacklists(blacklist_id, customer_id, created_at) " +
                        "VALUES (UUID_TO_BIN(:blacklistId), UUID_TO_BIN(:customerId), :createdAt)",
                toParamMap(blacklist));

        if (executeUpdate != 1) {
            return Optional.empty();
        }

        return Optional.of(blacklist);
    }

    @Override
    public List<Blacklist> findAll() {
        return jdbcTemplate.query("SELECT * FROM blacklists", rowMapper);
    }

    @Override
    public Optional<Blacklist> findById(UUID blacklistId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM blacklists WHERE blacklist_id = :blacklistId",
                Collections.singletonMap("blacklistId", blacklistId.toString().getBytes()),
                rowMapper));
    }

    @Override
    public Optional<Blacklist> findByCustomerId(UUID customerId) {
        return Optional.ofNullable(jdbcTemplate.query(
                "SELECT * FROM blacklists WHERE customer_id = :customerId",
                Collections.singletonMap("blacklistId", customerId.toString().getBytes()),
                rowMapper).get(0));
    }

    @Override
    public Optional<Blacklist> update(Blacklist blacklist) {
        var executeUpdate = jdbcTemplate.update(
                "UPDATE blacklists SET customer_id = UUID_TO_BIN(:customerId) " +
                        "WHERE blacklist_id = UUID_TO_BIN(:blacklistId)",
                toParamMap(blacklist));

        if (executeUpdate != 1) {
            return Optional.empty();
        }

        return Optional.of(blacklist);
    }

    @Override
    public Optional<Blacklist> delete(Blacklist blacklist) {
        var executeUpdate = jdbcTemplate.update(
                "DELETE FROM blacklists WHERE blacklist_id = UUID_TO_BIN(:blacklistId)",
                toParamMap(blacklist));

        if (executeUpdate != 1) {
            return Optional.empty();
        }

        return Optional.of(blacklist);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM blacklists", Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Blacklist blacklist) {
        Map<String, Object> map = new HashMap<>();
        map.put("blacklistId", blacklist.getBlacklistId().toString().getBytes());
        map.put("customerId", blacklist.getCustomerId().toString().getBytes());
        map.put("createdAt", Timestamp.valueOf(blacklist.getCreatedAt()));
        return map;
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
