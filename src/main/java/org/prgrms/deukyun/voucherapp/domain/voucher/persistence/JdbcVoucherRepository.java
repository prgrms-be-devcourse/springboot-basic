package org.prgrms.deukyun.voucherapp.domain.voucher.persistence;

import lombok.RequiredArgsConstructor;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.*;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Primary
@Repository
@RequiredArgsConstructor
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String insertQuery = "INSERT INTO voucher(voucher_id, voucher_type, amount, percent) VALUES (:id, :type, :amount, :percent)";
    private static final String findAllQuery = "SELECT * FROM voucher";
    private static final String findByIdQuery = "SELECT * FROM voucher WHERE voucher_id = :id";
    private static final String clearQuery = "DELETE FROM voucher";

    @Override
    public Voucher insert(Voucher voucher) {
        Map<String, Object> paramMap = resolveParamMap(voucher);

        jdbcTemplate.update(insertQuery, paramMap);
        return voucher;
    }


    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            Map<String, Object> paramMap = Collections.singletonMap("id", id);
            return Optional.ofNullable(jdbcTemplate.queryForObject(findByIdQuery, paramMap, voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(findAllQuery, voucherRowMapper);
    }

    @Override
    public void clear() {
        jdbcTemplate.update(clearQuery, Collections.emptyMap());
    }


    private Map<String, Object> resolveParamMap(Voucher voucher) {

        Map<String, Object> paramMap;
        UUID id = voucher.getId();
        if (voucher instanceof FixedAmountDiscountVoucher) {
            paramMap = Map.of("id", id,
                    "type", "fixed",
                    "amount", ((FixedAmountDiscountVoucher) voucher).getAmount(),
                    "percent", 0);
        } else if (voucher instanceof PercentDiscountVoucher) {
            paramMap = Map.of("id", id,
                    "type", "percent",
                    "amount", 0,
                    "percent", ((PercentDiscountVoucher) voucher).getPercent());
        } else {
            throw new IllegalArgumentException("Invalid voucher type given");
        }

        return paramMap;
    }

    private final RowMapper<Voucher> voucherRowMapper = (rs, i) -> {
        String type = rs.getString("voucher_type");
        UUID id = UUID.fromString(rs.getString("voucher_id"));

        long amount = rs.getLong("amount");
        long percent = rs.getLong("percent");

        return VoucherFactory.createVoucher(type, id, amount, percent);
    };
}
