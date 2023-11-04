package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.util.DomainMapper;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.programmers.vouchermanagement.constant.Constant.UPDATE_ONE_FLAG;
import static com.programmers.vouchermanagement.constant.Message.*;
import static com.programmers.vouchermanagement.voucher.repository.VoucherQuery.*;

@Profile("jdbc")
@Repository
public class VoucherJDBCRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(VoucherJDBCRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DomainMapper domainMapper;

    public VoucherJDBCRepository(NamedParameterJdbcTemplate jdbcTemplate, DomainMapper domainMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.domainMapper = domainMapper;
    }

    @Override
    public void save(Voucher voucher) {
        int update = jdbcTemplate.update(INSERT, domainMapper.voucherToParamMap(voucher));
        if (update != UPDATE_ONE_FLAG) {
            throw new RuntimeException(NOT_INSERTED);
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL, domainMapper.voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID,
                    Collections.singletonMap(DomainMapper.ID_KEY, id.toString().getBytes()),
                    domainMapper.voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error(EMPTY_RESULT, e);
            return Optional.empty();
        }
    }
    @Override
    public List<Voucher> findAllByCreatedAt(LocalDateTime from, LocalDateTime to) {
        return jdbcTemplate.query(FIND_ALL_BY_CREATED_AT,
                Map.of(DomainMapper.FROM_KEY, from.toString(), DomainMapper.TO_KEY, to.toString()),
                domainMapper.voucherRowMapper);
    }

    @Override
    public void delete(UUID id) {
        int update = jdbcTemplate.update(DELETE_VOUCHER, Collections.singletonMap(DomainMapper.ID_KEY, id.toString().getBytes()));
        if (update != UPDATE_ONE_FLAG) {
            throw new RuntimeException(NOT_DELETED);
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL, Collections.emptyMap());
    }

    @Override
    public void update(Voucher voucher) {
        int update = jdbcTemplate.update(UPDATE_VOUCHER, domainMapper.voucherToParamMap(voucher));
        if (update != UPDATE_ONE_FLAG) {
            throw new RuntimeException(NOT_UPDATED);
        }
    }
}
