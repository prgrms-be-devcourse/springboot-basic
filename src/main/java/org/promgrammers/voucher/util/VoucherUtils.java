package org.promgrammers.voucher.util;

import org.promgrammers.voucher.domain.Voucher;
import org.promgrammers.voucher.domain.VoucherType;
import org.promgrammers.voucher.domain.dto.VoucherCreateRequestDto;
import org.promgrammers.voucher.service.VoucherFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.UUID;

public class VoucherUtils {

    public static SqlParameterSource createParameterSource(Voucher voucher) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", voucher.getId().toString())
                .addValue("amount", voucher.getAmount())
                .addValue("voucherType", voucher.getVoucherType().toString());
        return paramSource;
    }

    public static RowMapper<Voucher> voucherRowMapper = (((rs, rowNum) -> {
        UUID id = UUID.fromString(rs.getString("id"));
        long amount = rs.getLong("amount");
        VoucherType voucherType = VoucherType.valueOf(rs.getString("voucher_type"));

        return VoucherFactory.createVoucher(new VoucherCreateRequestDto(id, amount, voucherType));
    }));
}
