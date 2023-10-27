package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.util.DomainMapper;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class VoucherJDBCRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(VoucherJDBCRepository.class);
    private static final String INSERT_QUERY = "INSERT INTO test.vouchers(id, type, discount_value) VALUES (UUID_TO_BIN(:id), :type, :discountValue)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM test.vouchers WHERE id = UUID_TO_BIN(:id)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM test.vouchers";
    private static final String DELETE_VOUCHER_QUERY = "DELETE FROM test.vouchers WHERE id = UUID_TO_BIN(:id)";
    private static final String DELETE_ALL_QUERY = "TRUNCATE TABLE test.vouchers";
    private static final String UPDATE_VOUCHER_QUERY = "UPDATE test.vouchers SET type = :type, discount_value = :discountValue WHERE id = UUID_TO_BIN(:id)";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DomainMapper domainMapper;

    public VoucherJDBCRepository(NamedParameterJdbcTemplate jdbcTemplate, DomainMapper domainMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.domainMapper = domainMapper;
    }


    @Override
    public void save(Voucher voucher) {
        int update = jdbcTemplate.update(INSERT_QUERY, domainMapper.voucherToParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, domainMapper.voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY,
                    Collections.singletonMap("id", id.toString().getBytes()),
                    domainMapper.voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void delete(UUID id) {
        int update = jdbcTemplate.update(DELETE_VOUCHER_QUERY, Collections.singletonMap("id", id.toString().getBytes()));
        if (update != 1) {
            throw new RuntimeException("Noting was deleted");
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_QUERY, Collections.emptyMap());
    }

    @Override
    public void update(Voucher voucher) {
        int update = jdbcTemplate.update(UPDATE_VOUCHER_QUERY, domainMapper.voucherToParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Noting was updated");
        }
    }
}
