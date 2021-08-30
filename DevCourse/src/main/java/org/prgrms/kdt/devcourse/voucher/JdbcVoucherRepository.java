package org.prgrms.kdt.devcourse.voucher;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile({"prod"})
public class JdbcVoucherRepository implements VoucherRepository{
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    static UUID toUUID(byte[] bytes){
        var byteBuffer= ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(),byteBuffer.getLong());
    }

    RowMapper<Voucher> rowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long amount = resultSet.getLong("amount");
        String typeString = resultSet.getString("type");
        if (VoucherType.isExistType(typeString)) {
            VoucherType voucherType = VoucherType.valueOf(typeString);
            if (voucherType == VoucherType.PERCENT)
                return new PercentDiscountVoucher(voucherId, amount);
            else if (voucherType == VoucherType.FIXED)
                return new FixedAmountVoucher(voucherId, amount);
        } else {
            throw new RuntimeException("Invalid Voucher Type.");
        }
        return null;
    };

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try{
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()), rowMapper));
        }catch (EmptyResultDataAccessException exception){
            throw new RuntimeException("There is no voucher");
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        HashMap<String, Object> paraMap = new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("amount", voucher.getVoucherAmount());
            put("type", voucher.getVoucherType().toString());
        }};
        int insertedResult = namedParameterJdbcTemplate.update("insert into vouchers(voucher_id, amount, type) values(UUID_TO_BIN(:voucherId), :amount, :type)", paraMap);
        if(insertedResult!=1){
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return namedParameterJdbcTemplate.query("select * from vouchers",rowMapper);
    }

    public void deleteAll() {
        namedParameterJdbcTemplate.getJdbcTemplate().update("delete from vouchers");
    }
}
