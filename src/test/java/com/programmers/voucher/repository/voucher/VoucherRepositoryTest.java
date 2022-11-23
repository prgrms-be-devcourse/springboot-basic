package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.controller.dto.CustomerDto;
import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.model.voucher.FixedAmountVoucher;
import com.programmers.voucher.model.voucher.PercentDiscountVoucher;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.model.voucher.VoucherType;
import com.programmers.voucher.repository.customer.CustomerRepository;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherRepositoryTest {
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    DataSource dataSource;
    private static EmbeddedMysql embeddedMysql;

    @Configuration
    @ComponentScan(basePackages = {"com.programmers.voucher.repository"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test_voucher")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @BeforeAll
    static void setup() {
        MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v8_0_17)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(config)
                .addSchema("test_voucher", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    private Customer insertSingleCustomerData() {
        String email = "taehee@gmail.com";
        CustomerDto customerDto = new CustomerDto("taehee", email);
        customerRepository.save(customerDto);
        return customerRepository.findByEmail(email);
    }

    private Voucher insertSingleVoucherData() {
        Voucher voucher = getVoucher();
        voucher.setVoucherType(getVoucherType());
        return voucherRepository.save(voucher);
    }

    private void insertAllVouchersData() {
        VoucherType voucherType = getVoucherType();
        for (Voucher voucher : getVouchers()) {
            voucher.setVoucherType(voucherType);
            voucherRepository.save(voucher);
        }
    }

    private VoucherType getVoucherType() {
        return VoucherType.toVoucherType("1");
    }

    private Voucher getVoucher() {
        return new FixedAmountVoucher(UUID.randomUUID(), 5000);
    }

    private List<Voucher> getVouchers() {
        return List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 5000),
                new PercentDiscountVoucher(UUID.randomUUID(), 40)
        );
    }

    @Test
    @DisplayName("데이터베이스에 바우처를 저장한다.")
    void save() {
        //given
        VoucherType voucherType = getVoucherType();
        Voucher newVoucher = getVoucher();
        newVoucher.setVoucherType(voucherType);

        //when
        Voucher result = voucherRepository.save(newVoucher);

        //then
        assertThat(result.getVoucherId())
                .isEqualTo(newVoucher.getVoucherId());
    }

    @Test
    @DisplayName("데이터베이스에서 모든 바우처 목록을 조회한다.")
    void findAll() {
        //given
        insertAllVouchersData();

        //when
        List<Voucher> result = voucherRepository.findAll();

        //then
        assertThat(result.size())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("데이터베이스에서 해당 고객이 가진 모든 바우처 목록을 조회한다.")
    void findAllByEmail() {
        //given
        insertAllVouchersData();
        Customer customer = insertSingleCustomerData();
        List<Voucher> vouchers = voucherRepository.findAll();
        for (Voucher voucher : vouchers) {
            voucher.setCustomer(customer);
            voucherRepository.assign(voucher);
        }

        //when
        List<Voucher> result = voucherRepository.findAllByEmail("taehee@gmail.com");

        //then
        assertThat(result.size())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("데이터베이스에서 바우처 아이디를 통해 조회한다.")
    void findById() {
        //given
        Voucher voucher = insertSingleVoucherData();

        //when
        Voucher result = voucherRepository.findById(voucher.getVoucherId());

        //then
        assertThat(result.getVoucherId())
                .isEqualTo(voucher.getVoucherId());
    }

    @Test
    @DisplayName("데이터베이스에서 바우처 아이디를 통해 수정한다.")
    void update() {
        //given
        Voucher voucher = insertSingleVoucherData();
        VoucherType updatedType = VoucherType.toVoucherType("2");
        Voucher updatedVoucher = new PercentDiscountVoucher(voucher.getVoucherId(), 30);
        updatedVoucher.setVoucherType(updatedType);

        //when
        voucherRepository.update(updatedVoucher);
        Voucher result = voucherRepository.findById(voucher.getVoucherId());

        //then
        assertThat(result.getDiscountValue())
                .isEqualTo(30);
    }

    @Test
    @DisplayName("데이터베이스에서 모든 바우처 목록을 삭제한다.")
    void deleteALL() {
        //given
        insertAllVouchersData();

        //when
        voucherRepository.deleteAll();

        //then
        assertThat(voucherRepository.findAll().isEmpty())
                .isTrue();
    }

    @Test
    @DisplayName("데이터베이스에 저장된 바우처를 고객에게 할당한다.")
    void assign() {
        //given
        Voucher voucher = insertSingleVoucherData();
        Customer customer = insertSingleCustomerData();
        voucher.setCustomer(customer);

        //when
        voucherRepository.assign(voucher);

        //then
        assertThat(voucherRepository.findAllByEmail("taehee@gmail.com").size())
                .isEqualTo(1);
    }
}
