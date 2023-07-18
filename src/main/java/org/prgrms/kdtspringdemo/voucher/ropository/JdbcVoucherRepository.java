package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.prgrms.kdtspringdemo.util.JdbcUtils.*;

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

        return voucherType.updateVoucher(voucherId, amount);
    };

    private Map<String, Object> toParamMap(UUID voucherId, VoucherType voucherType, long amount) {
        return Map.of(
                VOUCHER_ID, voucherId.toString().getBytes(),
                VOUCHER_TYPE, voucherType.name(),
                AMOUNT, amount
        );
    }

    @Override
    public Voucher save(Voucher voucher) {
        jdbcTemplate.update(SAVE_QUERY, toParamMap(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount()));

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return jdbcTemplate.query(FIND_BY_ID_QUERY, Collections.singletonMap(VOUCHER_ID, voucherId.toString().getBytes()), voucherRowMapper)
                .stream()
                .findFirst();
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, voucherRowMapper);
    }

    @Override
    public void update(UUID voucherId, VoucherType voucherType, long amount) {
        jdbcTemplate.update(UPDATE_QUERY, toParamMap(voucherId, voucherType, amount));
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update(DELETE_QUERY, Collections.singletonMap(VOUCHER_ID, voucherId.toString().getBytes()));
    }
}
