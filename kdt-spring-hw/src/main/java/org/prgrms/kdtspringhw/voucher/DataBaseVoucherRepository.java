package org.prgrms.kdtspringhw.voucher;

import org.prgrms.kdtspringhw.voucher.voucherObj.FixedAmountVoucher;
import org.prgrms.kdtspringhw.voucher.voucherObj.PercentDiscountVoucher;
import org.prgrms.kdtspringhw.voucher.voucherObj.Voucher;
import org.prgrms.kdtspringhw.voucher.voucherObj.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
@Profile("prod")
public class DataBaseVoucherRepository implements VoucherRepository{
    private final JdbcTemplate jdbcTemplate;

    public DataBaseVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Voucher> voucherRowMapper = (resultSet, i) ->{
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long data = resultSet.getLong("data");
        VoucherType voucherType = VoucherType.getVoucherType(resultSet.getString("type"));
        switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER:
                return new FixedAmountVoucher(voucherId, data);
            case PERCENT_DISCOUNT_VOUCHER:
                return new PercentDiscountVoucher(voucherId, data);
        }
        return null;
    };

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)",
                            voucherRowMapper, voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var insertCount = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, data, type) VALUES (UUID_TO_BIN(?), ?, ?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getData(),
                voucher.getType().getName());
        if(insertCount != 1){
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public Map<UUID, Voucher> returnAll() {
        List<Voucher> list = jdbcTemplate.query("SELECT * FROM vouchers",voucherRowMapper);
        Map<UUID, Voucher> map = new HashMap<>();
        for(Voucher voc : list){
            map.put(voc.getVoucherId(), voc);
        }
        return map;
    }

    public void deleteAll(){
        jdbcTemplate.update("DELETE FROM vouchers");
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
