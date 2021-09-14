package org.prgrms.kdt.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
@Profile("jdbc")
public class JdbcVoucherRepository implements VoucherRepository{

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher){
        return new HashMap<>() {{
           put("voucherId", voucher.getVoucherId().toString().getBytes());
           put("amount", voucher.getVoucherAmount());
           put("type", voucher.getVoucherType().toString());
        }};
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var type = resultSet.getString("type");
        var amount  = resultSet.getLong("amount");
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        if (type.equals("FIXED")){
            return new FixedAmountVoucher(voucherId, amount);
        }
        else if(type.equals("PERCENT")){
            return new PercentDiscountVoucher(voucherId, amount);
        }
        return null;
    };

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        }catch (EmptyResultDataAccessException e){
            logger.error("Got Empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Map<UUID, Voucher> getVoucherList() {
        var voucherList = jdbcTemplate.query("select * from vouchers",
                (rs, rowNum) -> Objects.equals(rs.getString("type"), "FIXED")
                        ? new FixedAmountVoucher(toUUID(rs.getBytes("voucher_id")), rs.getLong("amount"))
                        : new PercentDiscountVoucher(toUUID(rs.getBytes("voucher_id")), rs.getLong("amount"))
        );

        var voucherMap = new HashMap<UUID, Voucher>();
        for(Voucher v : voucherList){
            voucherMap.put(v.getVoucherId(), v);
        }

        return voucherMap;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var insert = jdbcTemplate.update("insert into vouchers(voucher_id, amount, type) value(UUID_TO_BIN(:voucherId), :amount, :type)",
                toParamMap(voucher));
        if (insert != 1){
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    static UUID toUUID ( byte[] bytes){
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
