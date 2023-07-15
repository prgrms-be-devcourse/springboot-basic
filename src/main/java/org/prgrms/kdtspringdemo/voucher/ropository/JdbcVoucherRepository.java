package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.PercentAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.prgrms.kdtspringdemo.util.JdbcUtils.*;
import static org.prgrms.kdtspringdemo.voucher.exception.ExceptionMessage.*;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private static final String VOUCHER_ID = "voucher_id";
    private static final String VOUCHER_TYPE = "voucher_type";
    private static final String AMOUNT = "amount";
    private static final String SAVE_QUERY = "INSERT INTO voucher(voucher_id, voucher_type, amount) VALUES(UUID_TO_BIN(:voucher_id), :voucher_type, :amount)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucher_id)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM voucher";
    private static final String UPDATE_QUERY = "UPDATE voucher SET voucher_type = :voucher_type, amount = :amount WHERE voucher_id = UUID_TO_BIN(:voucher_id)";
    private static final String DELETE_QUERY = "DELETE FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucher_id)";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes(VOUCHER_ID));
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString(VOUCHER_TYPE));
        long amount = resultSet.getLong(AMOUNT);

        return Voucher.update(voucherId, voucherType, amount);
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        return Map.of(
                VOUCHER_ID, voucher.getVoucherId().toString().getBytes(),
                VOUCHER_TYPE, voucher.getVoucherType().name(),
                AMOUNT, voucher.getAmount()
        );
    }

    @Override
    public Voucher save(Voucher voucher) {
        int savedVoucherRow = jdbcTemplate.update(SAVE_QUERY, toParamMap(voucher));
        if (savedVoucherRow != SUCCESS_SAVE_QUERY) {
            throw new RuntimeException(FAILED_VOUCHER_SAVE_QUERY.getMessage());
        }

        return voucher;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        Voucher voucher;
        try {
            voucher = jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, Collections.singletonMap(VOUCHER_ID, voucherId.toString().getBytes()), voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException(VOUCHER_ID_LOOKUP_FAILED.getMessage());
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, voucherRowMapper);
    }

    @Override
    public Voucher update(Voucher voucher) {
        int updatedVoucherRow = jdbcTemplate.update(UPDATE_QUERY, toParamMap(voucher));
        if (isNotFoundVoucher(updatedVoucherRow)) {
            save(voucher);
        }

        return voucher;
    }

    private boolean isNotFoundVoucher(int voucherRow) {
        return voucherRow == NOT_FOUND_ID;
    }

    @Override
    public Voucher deleteById(UUID voucherId) {
        Voucher voucher = findById(voucherId);
        int deletedVoucherRow = jdbcTemplate.update(DELETE_QUERY, Collections.singletonMap(VOUCHER_ID, voucherId.toString().getBytes()));
        if (isNotFoundVoucher(deletedVoucherRow)) {
            throw new RuntimeException(VOUCHER_ID_LOOKUP_FAILED.getMessage());
        }

        return voucher;
    }
}
