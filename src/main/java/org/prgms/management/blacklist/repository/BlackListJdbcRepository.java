package org.prgms.management.blacklist.repository;

import org.prgms.management.blacklist.vo.Blacklist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

import static org.prgms.management.util.JdbcUtils.*;

@Repository
@Profile({"local-db", "dev", "test"})
public class BlackListJdbcRepository implements BlackListRepository {
    private static final Logger logger = LoggerFactory.getLogger(BlackListJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BlackListJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Blacklist insert(Blacklist blacklist) {
        var executeUpdate = jdbcTemplate.update(
                "INSERT INTO blacklists(blacklist_id, customer_id, created_at) " +
                        "VALUES (UUID_TO_BIN(:blacklistId), UUID_TO_BIN(:customerId), :createdAt)",
                toParamMap(blacklist));

        if (executeUpdate != 1) {
            return null;
        }

        return blacklist;
    }

    @Override
    public List<Blacklist> findAll() {
        return jdbcTemplate.query("SELECT * FROM blacklists", rowMapper);
    }

    @Override
    public Optional<Blacklist> findById(UUID blacklistId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM blacklists WHERE blacklist_id = UUID_TO_BIN(:blacklistId)",
                    Collections.singletonMap("blacklistId", blacklistId.toString().getBytes()),
                    rowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Blacklist> findByCustomerId(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM blacklists WHERE customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    rowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Blacklist delete(Blacklist blacklist) {
        var executeUpdate = jdbcTemplate.update(
                "DELETE FROM blacklists WHERE blacklist_id = UUID_TO_BIN(:blacklistId)",
                toParamMap(blacklist));

        if (executeUpdate != 1) {
            return null;
        }

        return blacklist;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM blacklists", Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Blacklist blacklist) {
        Map<String, Object> map = new HashMap<>();
        map.put("blacklistId", blacklist.blacklistId().toString().getBytes());
        map.put("customerId", blacklist.customerId().toString().getBytes());
        map.put("createdAt", Timestamp.valueOf(blacklist.createdAt()));
        return map;
    }

    private static final RowMapper<Blacklist> rowMapper = (resultSet, i) -> {
        var blacklistId = toUUID(resultSet.getBytes("blacklist_id"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Blacklist(blacklistId, customerId, createdAt);
    };
}
