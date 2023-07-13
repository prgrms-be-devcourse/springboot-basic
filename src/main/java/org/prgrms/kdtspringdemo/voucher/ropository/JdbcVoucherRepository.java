package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.PercentAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.prgrms.kdtspringdemo.util.JdbcUtils.*;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository{
    private static final String VOUCHER_ID = "voucher_id";
    private static final String VOUCHER_TYPE = "voucher_type";
    private static final String AMOUNT = "amount";
    private static final String SAVE_QUERY = "INSERT INTO voucher(voucher_id, voucher_type, amount) VALUES(UUID_TO_BIN(:voucher_id), :voucher_type, :amount)";
    private static final String FAILED_VOUCHER_SAVE_QUERY_MESSAGE = "바우처 저장 쿼리를 실패 하였습니다.";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucher_id)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM voucher";
    private static final String UPDATE_QUERY = "UPDATE voucher SET voucher_type = :voucher_type, amount = :amount WHERE voucher_id = UUID_TO_BIN(:voucher_id)";
    private static final String FAILED_VOUCHER_UPDATE_QUERY_MESSAGE = "조회된 바우처 ID가 없습니다.";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes(VOUCHER_ID));
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString(VOUCHER_TYPE));
        long amount = resultSet.getLong(AMOUNT);
        return switch (voucherType) {
            case FIXED -> new FixedAmountVoucher(voucherId, amount);
            case PERCENT -> new PercentAmountVoucher(voucherId, amount);
        };
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
        int update = jdbcTemplate.update(SAVE_QUERY, toParamMap(voucher));
        if (update != SUCCESS_SAVE_QUERY) {
            throw new RuntimeException(FAILED_VOUCHER_SAVE_QUERY_MESSAGE);
        }

        return voucher;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, Collections.singletonMap(VOUCHER_ID, voucherId.toString().getBytes()), voucherRowMapper);
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, voucherRowMapper);
    }

    @Override
    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update(UPDATE_QUERY, toParamMap(voucher));
        if (update == NOT_FOUND_UPDATE_QUERY) {
            throw new RuntimeException(FAILED_VOUCHER_UPDATE_QUERY_MESSAGE);
        }

        return voucher;
    }

    @Override
    public Voucher delete(UUID voucherId) {
        return null;
    }
}
