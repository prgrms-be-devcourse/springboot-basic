package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.prgrms.kdt.common.util.Utility.toUUID;

@Repository
public class JdbcVoucherRepository implements VoucherRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("value", voucher.getValue());
            put("type", voucher.getVoucherType().toString());
        }};
    }

    private RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId =  toUUID(resultSet.getBytes("voucher_id"));
        var value = resultSet.getLong("value");
        var voucherType = VoucherType.convert(resultSet.getString("type"));
        if(voucherType == VoucherType.FIXED)
            return new FixedAmountVoucher(voucherId, value);
        else
            return new PercentDiscountVoucher(voucherId, value);
    };



    @Override
    public Voucher insert(Voucher voucher) {
        var update = jdbcTemplate.update(
                "insert into vouchers(voucher_id, value, type) values (UUID_TO_BIN(:voucherId), :value, :type)",
                toParamMap(voucher)
        );
        if (update != 1)
            throw new RuntimeException("insert 실패");
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }



}
