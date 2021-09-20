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
           put("customerEmail", voucher.getCustomerEmail());
        }};
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var type = resultSet.getString("type");
        var amount  = resultSet.getLong("amount");
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var customerEmail = resultSet.getString("customer_email");
        if (type.equals("FIXED")){
            return new FixedAmountVoucher(voucherId, amount, customerEmail);
        }
        else if(type.equals("PERCENT")){
            return new PercentDiscountVoucher(voucherId, amount, customerEmail);
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
    public Map<UUID, Voucher> getVoucherAll() {
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

    @Override
    public HashMap<UUID, Voucher> getVoucherListByCustomerEmail(String inputCustomerEmail) {
        var voucherList = jdbcTemplate.query("select * from vouchers where customer_email = :customerEmail",
                Collections.singletonMap("customerEmail", inputCustomerEmail),
                voucherRowMapper);
        var voucherMap = new HashMap<UUID, Voucher>();
        for(Voucher v : voucherList){
            voucherMap.put(v.getVoucherId(), v);
        }

        return voucherMap;
    }

    @Override
    public void updateAssignVoucher(Voucher voucher) {
        jdbcTemplate.update("update vouchers set customer_email = :email where voucher_id = UUID_TO_BIN(:voucherId)",
                toParamMap(voucher));
    }

    @Override
    public void deleteVoucher(UUID voucherId) {
        jdbcTemplate.update("delete from vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
    }

    static UUID toUUID ( byte[] bytes){
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
