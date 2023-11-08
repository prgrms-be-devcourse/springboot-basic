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
import static com.programmers.vouchermanagement.voucher.repository.util.VoucherDomainMapper.*;

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
        int update = jdbcTemplate.update(
                "INSERT INTO vouchers(id, type, discount_value) VALUES (UUID_TO_BIN(:id), :type, :discount_value)",
                voucherToParamMap(voucher));
        if (update != UPDATE_ONE_FLAG) {
            throw new EmptyResultDataAccessException(UPDATE_ONE_FLAG);
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM vouchers",
                voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM vouchers WHERE id = UUID_TO_BIN(:id)",
                    Collections.singletonMap(ID_KEY, id.toString().getBytes()), voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error(EMPTY_RESULT, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAllByCreatedAt(LocalDateTime from, LocalDateTime to) {
        return jdbcTemplate.query(
                "SELECT * FROM vouchers WHERE created_at BETWEEN DATE(:from) AND DATE(:to)",
                Map.of(FROM_KEY, from.toString(), TO_KEY, to.toString()), voucherRowMapper);
    }

    @Override
    public void delete(UUID id) {
        int update = jdbcTemplate.update(
                "DELETE FROM vouchers WHERE id = UUID_TO_BIN(:id)",
                Collections.singletonMap(ID_KEY, id.toString().getBytes()));
        if (update != UPDATE_ONE_FLAG) {
            throw new NoSuchElementException(NOT_DELETED);
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(
                "DELETE FROM vouchers",
                Collections.emptyMap());
    }

    @Override
    public void update(Voucher voucher) {
        int update = jdbcTemplate.update(
                "UPDATE vouchers SET type = :type, discount_value = :discount_value WHERE id = UUID_TO_BIN(:id)",
                voucherToParamMap(voucher));
        if (update != UPDATE_ONE_FLAG) {
            throw new NoSuchElementException(NOT_UPDATED);
        }
    }

    @Override
    public List<Voucher> findAllByType(VoucherType type) {
        return jdbcTemplate.query(
                "SELECT * FROM vouchers WHERE type = :type",
                Map.of(TYPE_KEY, type.getName()), voucherRowMapper);
    }
}
