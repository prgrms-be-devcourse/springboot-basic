package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.common.exception.ExceptionMessage;
import org.prgrms.kdt.voucher.domain.*;
import org.prgrms.kdt.voucher.exception.InvalidVoucherTypeException;
import org.prgrms.kdt.voucher.exception.VoucherNotInsertedException;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
@Profile("database")
public class JdbcVoucherRepository implements VoucherRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL_SQL = "select * from vouchers";
    private static final String SELECT_BY_ID_SQL = "select * from vouchers where id = UUID_TO_BIN(:id)";
    private static final String DELETE_ALL_SQL = "delete from vouchers";
    private static final String INSERT_SQL = "insert into vouchers(id, type, discount_value) values(UUID_TO_BIN(:id), :type, :discountValue)";

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("id"));
        VoucherType voucherType = VoucherType.findByVoucherType(String.valueOf(resultSet.getInt("type")));
        long discountValue = resultSet.getLong("discount_value");

        return VoucherFactory.createVoucher(voucherType, voucherId, discountValue);
    };

    private Map<String, Object> toParamMap(Voucher voucher, VoucherType voucherType){
        switch (voucherType){
            case FIXED_AMOUNT:
                return toFixedVoucherParamMap((FixedAmountVoucher) voucher);
            case PERCENT_DISCOUNT:
                return toPercentVoucherParamMap((PercentDiscountVoucher) voucher);
            default:
                throw new InvalidVoucherTypeException(ExceptionMessage.INVALID_VOUCHER_TYPE.getMessage());
        }
    }

    private Map<String, Object> toFixedVoucherParamMap(FixedAmountVoucher voucher){
        return new HashMap<String, Object>(){{
                put("id", voucher.getVoucherId().toString().getBytes());
                put("discountValue", voucher.getAmount());
                put("type", Integer.parseInt(VoucherType.FIXED_AMOUNT.getVoucherType()));
        }};
    }

    private Map<String, Object> toPercentVoucherParamMap(PercentDiscountVoucher voucher){
        return new HashMap<String, Object>(){{
                put("id", voucher.getVoucherId().toString().getBytes());
                put("discountValue", voucher.getPercent());
                put("type", Integer.parseInt(VoucherType.PERCENT_DISCOUNT.getVoucherType()));
        }};
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                                                SELECT_BY_ID_SQL,
                                                Collections.singletonMap("id", voucherId.toString().getBytes()),
                                                voucherRowMapper)
            );
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        VoucherType voucherType = voucher.getClass().equals(FixedAmountVoucher.class) ? VoucherType.FIXED_AMOUNT : VoucherType.PERCENT_DISCOUNT;
        int insertedRow = jdbcTemplate.update(INSERT_SQL, toParamMap(voucher, voucherType));

        if(insertedRow != 1){
            throw new VoucherNotInsertedException(ExceptionMessage.VOUCHER_NOT_INSERTED.getMessage());
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return jdbcTemplate.query(SELECT_ALL_SQL, Collections.emptyMap(), voucherRowMapper);
    }

    @Override
    public void clearAllVouchers() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
