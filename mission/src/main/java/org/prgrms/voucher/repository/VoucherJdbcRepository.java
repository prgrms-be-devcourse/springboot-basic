package org.prgrms.voucher.repository;

import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("dataBase")
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {

        if (voucher == null) {
            throw new IllegalArgumentException("Voucher is null");
        }

        if (voucher.getVoucherId() == null) {
            int update = jdbcTemplate.update("INSERT INTO voucher(voucher_id, discount_value, voucher_type)" +
                    " VALUES(:voucherId, :discountValue, :voucherType)", toParamMap(voucher));

            if (update != 1) {
                throw new RuntimeException("Nothing was inserted");
            }

            return voucher;
        }

        throw new IllegalArgumentException("Wrong Voucher..");
    }

    @Override
    public List<Voucher> findAll() {

        return jdbcTemplate.query("select * from voucher", voucherRowMapper);
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {

        long voucherId = resultSet.getLong("voucher_id");
        long discountValue = resultSet.getLong("discount_value");
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));

        return voucherType.createVoucher(voucherId, discountValue, voucherType);
    };

    private Map<String, Object> toParamMap(Voucher voucher) {

        var paramMap = new HashMap<String, Object>();
        paramMap.put("voucherId", voucher.getVoucherId());
        paramMap.put("discountValue", voucher.getDiscountValue());
        paramMap.put("voucherType", voucher.getVoucherType().toString());

        return paramMap;
    }
}