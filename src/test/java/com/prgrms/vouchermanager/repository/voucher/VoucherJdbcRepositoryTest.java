package com.prgrms.vouchermanager.repository.voucher;

import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;


@SpringJUnitConfig
class VoucherJdbcRepositoryTest {
    VoucherJdbcRepository repository;
    @Autowired JdbcTemplate template;
    @Autowired DataSource dataSource;
    private final Voucher voucher1 = new FixedAmountVoucher(20000);
    private final Voucher voucher2 = new PercentAmountVoucher(10);
    @Configuration
    static class TestConfig {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .url("jdbc:mysql://localhost:3306/voucher_manager?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8")
                    .username("root")
                    .password("suzzingV1999@")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }
    }

    @BeforeEach
    void beforeEach() {
        repository = new VoucherJdbcRepository(dataSource);
        repository.create(voucher2);
    }
    @AfterEach
    void afterEach() {
        repository.delete(UUID.fromString("626b8d5d-3940-4a0d-a3e4-fe6b297e8ad0"));
        repository.delete(UUID.fromString("70754a2f-d87d-4f69-af71-1d4bfe855e28"));
        repository.delete(UUID.fromString("8213dfa7-d577-4bb5-86d6-0159b3383f0e"));
        repository.delete(voucher1.getId());
        repository.delete(voucher2.getId());
    }
    @Test
    @DisplayName("create")
    void create() {
        Voucher createVoucher = repository.create(voucher1);
        Assertions.assertThat(createVoucher).isSameAs(voucher1);
    }

    @Test
    @DisplayName("list")
    void list() {
        List<Voucher> list = repository.list();
        Assertions.assertThat(list.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("findById")
    void findById() {
        Voucher voucher = repository.create(voucher1);
        Voucher findVoucher = repository.findById(voucher.getId());

        Assertions.assertThat(findVoucher.getDiscount()).isEqualTo(voucher.getDiscount());
        Assertions.assertThat(findVoucher).isInstanceOf(FixedAmountVoucher.class);
    }

    @Test
    @DisplayName("updateDiscount")
    void updateDiscount() {
        repository.updateDiscount(voucher2.getId(), 20);
        Voucher updateVoucher = template.queryForObject("select * from vouchers where voucher_id=UUID_TO_BIN(?)",
                voucherRowMapper(),
                voucher2.getId().toString().getBytes());
        Assertions.assertThat(updateVoucher.getDiscount()).isEqualTo(20);
    }

    @Test
    @DisplayName("delete")
    void delete() {
        UUID deleteId = repository.delete(voucher2.getId());
        Assertions.assertThat(deleteId).isEqualTo(voucher2.getId());
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            if(rs.getString("voucher_type").equals("fixed")) {
                return new FixedAmountVoucher(convertBytesToUUID(rs.getBytes("voucher_id")),
                        rs.getInt("discount"));
            } else {
                return new PercentAmountVoucher(convertBytesToUUID(rs.getBytes("voucher_id")),
                        rs.getInt("discount"));
            }
        };
    }

    private UUID convertBytesToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }
}
