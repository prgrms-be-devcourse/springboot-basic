package org.prgrms.springorder.domain.customer.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springorder.domain.customer.model.BlockCustomer;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.customer.model.CustomerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile({"dev", "test"})
public class BlockCustomerJdbcRepository implements BlockCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(BlockCustomerJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BlockCustomerJdbcRepository(
        NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<BlockCustomer> blockCustomerRowMapper = (rs, rowNum) -> {

        UUID customerId = UUID.fromString(rs.getString("customer_id"));

        UUID blockId = UUID.fromString(rs.getString("block_id"));

        LocalDateTime createdAt = rs.getTimestamp("created_at")
            != null ? rs.getTimestamp("created_at").toLocalDateTime() : null;

        return new BlockCustomer(blockId, customerId, createdAt);
    };

    @Override
    public Optional<BlockCustomer> findById(UUID blockId) {
        try {
            BlockCustomer BlockCustomer = jdbcTemplate.queryForObject(
                BlockCustomerSql.FIND_BY_ID.getSql(),
                Collections.singletonMap("blockId", blockId)
                , blockCustomerRowMapper);

            return Optional.ofNullable(BlockCustomer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public BlockCustomer insert(BlockCustomer blockCustomer) {
        try {
            jdbcTemplate.update(BlockCustomerSql.INSERT.getSql(), toParamMap(blockCustomer));

            return blockCustomer;
        } catch (DataAccessException e) {
            logger.error("voucher insert error. name {},  message {}", e.getClass().getName(),
                e.getMessage());
            throw e;
        }
    }

    @Override
    public List<BlockCustomer> findAll() {

        return jdbcTemplate.query(BlockCustomerSql.FIND_ALL.getSql(), blockCustomerRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(BlockCustomerSql.DELETE_ALL.getSql(), Collections.emptyMap());
    }


    private Map<String, Object> toParamMap(BlockCustomer blockCustomer) {
        return new HashMap<>() {{
            put("customerId", blockCustomer.getCustomerId().toString());
            put("blockId", blockCustomer.getBlockId());
            put("createdAt", blockCustomer.getCreatedAt() == null ? null
                : Timestamp.valueOf(blockCustomer.getCreatedAt()));
        }};
    }

}
