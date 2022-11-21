package org.prgrms.java.repository.voucher;

import org.prgrms.java.common.Mapper;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.VoucherException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private static final String INSERT_QUERY = "INSERT INTO vouchers(voucher_id, amount, type, expired_at, used) VALUES (UUID_TO_BIN(:voucherId), :amount, :type, :expiredAt, :used)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM vouchers";
    private static final String UPDATE_QUERY = "UPDATE vouchers SET amount = :amount, type = :type, expired_at = :expiredAt, used = :used WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String DELETE_ALL_ROWS_QUERY = "DELETE FROM vouchers";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                    FIND_BY_ID_QUERY,
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    Mapper.mapToVoucher));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Voucher> findAll() {
        return namedParameterJdbcTemplate.query(FIND_ALL_QUERY, Collections.emptyMap(), Mapper.mapToVoucher);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            int result = namedParameterJdbcTemplate.update(INSERT_QUERY, Mapper.toParamMap(voucher));
            if (result != 1) {
                throw new VoucherException("Nothing was inserted.");
            }
            return voucher;
        } catch (DuplicateKeyException e) {
            throw new VoucherException(voucher.getVoucherId() + " is already exists.");
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        int result = namedParameterJdbcTemplate.update(UPDATE_QUERY, Mapper.toParamMap(voucher));
        if (result != 1) {
            throw new VoucherException("Nothing was updated");
        }
        return voucher;
    }

    @Override
    public long deleteAll() {
        return namedParameterJdbcTemplate.update(DELETE_ALL_ROWS_QUERY, Collections.emptyMap());
    }
}
