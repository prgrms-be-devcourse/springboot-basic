package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.vouchertype.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.programmers.vouchermanagement.util.Constant.UPDATE_ONE_FLAG;
import static com.programmers.vouchermanagement.util.Message.*;
import static com.programmers.vouchermanagement.voucher.repository.VoucherDomainMapper.*;
import static com.programmers.vouchermanagement.voucher.repository.VoucherQuery.*;

@Profile("jdbc")
@Repository
public class VoucherJDBCRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(VoucherJDBCRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJDBCRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Voucher voucher) {
        int update = jdbcTemplate.update(INSERT, voucherToParamMap(voucher));
        if (update != UPDATE_ONE_FLAG) {
            throw new EmptyResultDataAccessException(UPDATE_ONE_FLAG);
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, Collections.singletonMap(ID_KEY, id.toString().getBytes()), voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error(EMPTY_RESULT, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAllByCreatedAt(LocalDateTime from, LocalDateTime to) {
        return jdbcTemplate.query(FIND_ALL_BY_CREATED_AT, Map.of(FROM_KEY, from.toString(), TO_KEY, to.toString()), voucherRowMapper);
    }

    @Override
    public void delete(UUID id) {
        int update = jdbcTemplate.update(DELETE_VOUCHER, Collections.singletonMap(ID_KEY, id.toString().getBytes()));
        if (update != UPDATE_ONE_FLAG) {
            throw new NoSuchElementException(NOT_DELETED);
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL, Collections.emptyMap());
    }

    @Override
    public void update(Voucher voucher) {
        int update = jdbcTemplate.update(UPDATE_VOUCHER, voucherToParamMap(voucher));
        if (update != UPDATE_ONE_FLAG) {
            throw new NoSuchElementException(NOT_UPDATED);
        }
    }

    @Override
    public List<Voucher> findAllByType(VoucherType type) {
        return jdbcTemplate.query(FIND_ALL_BY_TYPE, Map.of(TYPE_KEY, type.getName()), voucherRowMapper);
    }
}
