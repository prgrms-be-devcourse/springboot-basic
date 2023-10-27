package com.programmers.vouchermanagement.wallet.repository;

import com.programmers.vouchermanagement.util.DomainMapper;
import com.programmers.vouchermanagement.wallet.domain.Ownership;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class WalletJDBCRepository implements WalletRepository {
    private static final Logger logger = LoggerFactory.getLogger(WalletJDBCRepository.class);
    private static final String INSERT_QUERY = "INSERT INTO test.ownership(voucher_id, customer_id) VALUES (UUID_TO_BIN(:voucherId), UUID_TO_BIN(:customerId))";
    private static final String DELETE_OWNERSHIP_QUERY = "DELETE FROM test.ownership WHERE voucher_id = UUID_TO_BIN(:id)";
    private static final String DELETE_ALL_QUERY = "TRUNCATE TABLE test.ownership";
    private static final String FIND_ALL_VOUCHER_BY_CUSTOMER_ID_QUERY = "SELECT voucher_id as id FROM test.ownership WHERE customer_id = UUID_TO_BIN(:id)";
    private static final String FIND_CUSTOMER_BY_VOUCHER_ID_QUERY = "SELECT customer_id as id FROM test.ownership WHERE voucher_id = UUID_TO_BIN(:id)";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DomainMapper domainMapper;

    public WalletJDBCRepository(NamedParameterJdbcTemplate jdbcTemplate, DomainMapper domainMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.domainMapper = domainMapper;
    }

    @Override
    public void save(Ownership ownership) {
        int update = jdbcTemplate.update(INSERT_QUERY, domainMapper.ownershipToParamMap(ownership));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
    }

    @Override
    public List<UUID> findAllVoucherByCustomerId(UUID customerId) {
        return jdbcTemplate.query(FIND_ALL_VOUCHER_BY_CUSTOMER_ID_QUERY,
                Collections.singletonMap("id", customerId.toString().getBytes()),
                domainMapper.uuidRowMapper);
    }

    @Override
    public void delete(UUID voucherId) {
        int update = jdbcTemplate.update(DELETE_OWNERSHIP_QUERY, domainMapper.uuidToParamMap(voucherId));
        if (update != 1) {
            throw new RuntimeException("Noting was deleted");
        }
    }

    @Override
    public Optional<UUID> findCustomerByVoucherId(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_CUSTOMER_BY_VOUCHER_ID_QUERY,
                    Collections.singletonMap("id", voucherId.toString().getBytes()),
                    domainMapper.uuidRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_QUERY, Collections.emptyMap());
    }
}
