package org.programmers.springorder.repository.voucher;

import org.programmers.springorder.constant.ErrorMessage;
import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.model.voucher.VoucherType;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

import static org.programmers.springorder.global.utils.UUIDUtil.toUUID;
import static org.programmers.springorder.global.utils.UUIDUtil.uuidToBytes;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_VOUCHER = "INSERT INTO vouchers(voucher_id, discount_value, voucher_type) VALUES(UUID_TO_BIN(:voucherId), :discountValue, :voucherType)";
    private static final String SELECT_ALL_VOUCHER = "SELECT * FROM vouchers";
    private static final String SELECT_VOUCHER_BY_ID = "SELECT * FROM vouchers WHERE voucher_Id = UUID_TO_BIN(:voucherId)";
    private static final String UPDATE_VOUCHER = "UPDATE vouchers SET discount_value = :discountValue, voucher_type = :voucherType WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String DELETE_ALL_VOUCHER = "DELETE FROM vouchers";
    private static final String DELETE_VOUCHER_BY_ID = "DELETE FROM vouchers WHERE voucher_Id = UUID_TO_BIN(:voucherId)";
    private static final String UPDATE_VOUCHER_ASSIGN_CUSTOMER = "UPDATE vouchers SET customer_id = UUID_TO_BIN(:customerId) WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String SELECT_VOUCHER_BY_CUSTOMER_ID = "SELECT * FROM vouchers WHERE customer_id = UUID_TO_BIN(:customerId)";


    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long discountValue = Long.parseLong(resultSet.getString("discount_value"));
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        return Voucher.toVoucher(voucherId, discountValue, voucherType);
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", uuidToBytes(voucher.getVoucherIdToString()));
        paramMap.put("discountValue", voucher.getDiscountValue());
        paramMap.put("voucherType", voucher.getVoucherTypeName());
        return paramMap;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int update = jdbcTemplate.update(INSERT_VOUCHER, toParamMap(voucher));
        if (update != 1) {
            throw new DataAccessException(ErrorMessage.ERROR_IN_SAVE_VOUCHER) {
            };
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_VOUCHER, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            Map<String, Object> param = Map.of("voucherId", voucherId.toString());
            Voucher voucher = jdbcTemplate.queryForObject(SELECT_VOUCHER_BY_ID, param, voucherRowMapper);
            return Optional.ofNullable(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        jdbcTemplate.update(UPDATE_VOUCHER, toParamMap(voucher));
        return voucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_VOUCHER, Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID voucherId) {
        Map<String, Object> param = Map.of("voucherId", voucherId.toString());
        jdbcTemplate.update(DELETE_VOUCHER_BY_ID, param);
    }

    @Override
    public void assignVoucherToCustomer(UUID customerId, UUID voucherId) {
        Map<String, Object> param = Map.of("customerId", customerId.toString(), "voucherId", voucherId.toString());
        jdbcTemplate.update(UPDATE_VOUCHER_ASSIGN_CUSTOMER, param);
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        Map<String, Object> param = Map.of("customerId", customerId.toString());
        return jdbcTemplate.query(SELECT_VOUCHER_BY_CUSTOMER_ID, param, voucherRowMapper);
    }
}
