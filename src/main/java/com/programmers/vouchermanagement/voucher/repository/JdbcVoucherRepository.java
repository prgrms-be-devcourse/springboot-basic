package com.programmers.vouchermanagement.voucher.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;

@Repository
@Profile("jdbc")
public class JdbcVoucherRepository implements VoucherRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void deleteById(UUID voucherId) {

    }

    @Override
    public void deleteAll() {

    }
}
