package com.program.commandLine.repository;

import com.program.commandLine.model.customer.Customer;
import com.program.commandLine.model.customer.RegularCustomer;
import com.program.commandLine.model.voucher.FixedAmountVoucher;
import com.program.commandLine.model.voucher.Voucher;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("release")
class VoucherWalletJdbcRepositoryTest {


    @Configuration
    @ComponentScan(
            basePackages = {"com.program.commandLine.model.voucher","com.program.commandLine.repository"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

    }

    @Autowired
    DataSource dataSource;

    @Autowired
    VoucherWalletJdbcRepository voucherWalletJdbcRepository;

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    Customer customer;
    Voucher voucher;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher_mgmt", classPathScript("schema.sql"))
                .start();

        customer = customerJdbcRepository.insert(new RegularCustomer(UUID.randomUUID(),"test","test@naver.com"));
        voucher = voucherJdbcRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 3000,  false));
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("Hikari 연결 확인")
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("vocher를 할당할 수 있다.")
    public void  testCreateWallet(){
        voucherWalletJdbcRepository.createWallet(voucher.getVoucherId(),customer.getCustomerId());

        var findCustomer = customerJdbcRepository.findById(customer.getCustomerId());
        assertThat(findCustomer.isPresent(),is(true));
        var findWallet = findCustomer.get().getVoucherWallets();
        assertThat(findWallet.isEmpty(),is(false));
        assertThat(findWallet.get(0).voucherId(), is(voucher.getVoucherId()));

    }


    @Test
    @Order(3)
    @DisplayName("특정 vocher를 할당받은 고객을 찾을 수 있다.")
    public void  testFindWalletByVoucher(){
        var assignedCustomerId =voucherWalletJdbcRepository.findCustomerWalletByVoucher(voucher.getVoucherId());

        assertThat(assignedCustomerId,notNullValue());
        assertThat(assignedCustomerId,is(customer.getCustomerId()));
    }

    @Test
    @Order(4)
    @DisplayName("vocher를 회수할 수 있다.")
    public void  testDeleteWallet(){
        voucherWalletJdbcRepository.deleteWallet(voucher.getVoucherId());

        var findCustomer = customerJdbcRepository.findById(customer.getCustomerId());
        assertThat(findCustomer.isPresent(),is(true));
        assertThat(findCustomer.get().getVoucherWallets().isEmpty(),is(true));
    }

    @Test
    @Order(5)
    @DisplayName("할당되지 않은 바우처를 얻을 수 있다.")
    public void testFindByEmptyAssignedCustomer() {
        var newVoucher = voucherJdbcRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 5500,  false));
        var vouchers = voucherWalletJdbcRepository.findNotIncludeWallet();

        assertThat(vouchers.isEmpty(),is(false));
        assertThat(vouchers.size(),is(2));
        assertThrows(IllegalArgumentException.class,()->voucherWalletJdbcRepository.findCustomerWalletByVoucher(vouchers.get(0).getVoucherId()));
    }


}