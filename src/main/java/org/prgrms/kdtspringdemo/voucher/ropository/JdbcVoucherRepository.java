package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.exception.VoucherSaveFailedException;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.prgrms.kdtspringdemo.util.JdbcUtils.*;
import static org.prgrms.kdtspringdemo.voucher.exception.VoucherExceptionMessage.*;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
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

    private Map<String, Object> toParamMap(UUID voucherId, VoucherType voucherType, long amount) {
        return Map.of(
                VOUCHER_ID, voucherId.toString().getBytes(),
                VOUCHER_TYPE, voucherType.name(),
                AMOUNT, amount
        );
    }

    @Override
    public Voucher save(Voucher voucher) {
        int savedVoucherRow = jdbcTemplate.update(SAVE_QUERY, toParamMap(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount()));
        if (savedVoucherRow != SUCCESS_SAVE_QUERY) {
            logger.error("원인 : {} -> 에러 메시지 : {}", savedVoucherRow, FAILED_VOUCHER_SAVE_QUERY);
            throw new VoucherSaveFailedException(FAILED_VOUCHER_SAVE_QUERY);
        }

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<Voucher> voucherList = jdbcTemplate.query(FIND_BY_ID_QUERY, Collections.singletonMap(VOUCHER_ID, voucherId.toString().getBytes()), voucherRowMapper);;
        if (voucherList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(voucherList.get(0));
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> update(UUID voucherId, VoucherType voucherType, long amount) {
        jdbcTemplate.update(UPDATE_QUERY, toParamMap(voucherId, voucherType, amount));

        return findById(voucherId);
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update(DELETE_QUERY, Collections.singletonMap(VOUCHER_ID, voucherId.toString().getBytes()));
    }
}
