package com.prgrms.vouchermanager.repository.voucher;

import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;


@SpringJUnitConfig
class VoucherJdbcRepositoryTest {
    private VoucherJdbcRepository repository;
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private DataSource dataSource;
    private final Voucher voucher1 = new FixedAmountVoucher(20000);
    private final Voucher voucher2 = new PercentAmountVoucher(10);
    private final static String DELETE_VOUCHERS_QUERY = "delete from vouchers;";

//    @Autowired
//    public VoucherJdbcRepositoryTest(JdbcTemplate template, DataSource dataSource) {
//        this.template = template;
//        this.dataSource = dataSource;
//    }

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
        this.repository = new VoucherJdbcRepository(dataSource);
        repository.create(voucher2);
    }
    @AfterEach
    void afterEach() {
        template.execute(DELETE_VOUCHERS_QUERY);
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
        Voucher voucher = repository.updateDiscount(voucher2.getId(), 20);
        Assertions.assertThat(voucher.getDiscount()).isEqualTo(20);
    }

    @Test
    @DisplayName("delete")
    void delete() {
        Assertions.assertThat(repository.delete(voucher2.getId())).isEqualTo(1);
    }
}
