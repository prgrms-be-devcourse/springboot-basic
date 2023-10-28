package org.programmers.springboot.basic.domain.wallet.mapper;

import org.programmers.springboot.basic.domain.wallet.entity.Wallet;
import org.programmers.springboot.basic.util.converter.UUIDConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;

@Configuration
public class WalletMapper {

    @Bean
    public RowMapper<Wallet> walletRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherId = UUIDConverter.toUUID(rs.getBytes("voucher_id"));
            String email = rs.getString("email");
            return new Wallet(voucherId, email);
        };
    }
}
