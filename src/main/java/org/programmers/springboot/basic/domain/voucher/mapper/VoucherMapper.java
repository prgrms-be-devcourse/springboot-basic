package org.programmers.springboot.basic.domain.voucher.mapper;

import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.util.converter.UUIDConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;

@Configuration
public class VoucherMapper {

    @Bean
    public RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherId = UUIDConverter.toUUID(rs.getBytes("voucher_id"));
            VoucherType voucherType = VoucherType.valueOfVoucherByValue(rs.getInt("voucher_type"));
            Long discount = rs.getLong("discount");
            return new Voucher(voucherId, voucherType, discount);
        };
    }
}
