package org.prgrms.kdt.repository;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.domain.CustomerVoucherEntity;
import org.prgrms.kdt.domain.VoucherEntity;
import org.prgrms.kdt.enumType.VoucherStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.domain","org.prgrms.kdt.jdbcRepository","org.prgrms.kdt.repository"}
    )
    static class config{
        @Bean
        public DataSource dataSource(){
            var datasource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order-mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class) //쓸 구현체를 넣어준다 .
                    .build();
            datasource.setMaximumPoolSize(100);
            datasource.setMinimumIdle(100);
            return datasource;
        }
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    CustomerVoucherRepository customerVoucherRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DataSource dataSource;

    CustomerVoucherEntity testCase, testCase2, testCase3;

    VoucherEntity testVoucher, testVoucher2;

    CustomerEntity testCustomer, testCustomer2;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        testCustomer = new CustomerEntity(UUID.randomUUID(), "test-name", "test-user@naver.com", LocalDateTime.now());
        testCustomer2 = new CustomerEntity(UUID.randomUUID(), "test-name2", "test-user@naver.com2", LocalDateTime.now());
        testVoucher = new VoucherEntity(UUID.randomUUID(), VoucherStatus.FIXED, 3000L, LocalDateTime.now());
        testVoucher2 = new VoucherEntity(UUID.randomUUID(), VoucherStatus.PERCENT, 15L, LocalDateTime.now());
        testCase = new CustomerVoucherEntity(UUID.randomUUID(), testCustomer.getCustomerId(),testVoucher.getVoucherId(),LocalDateTime.now());
        testCase2 = new CustomerVoucherEntity(UUID.randomUUID(), testCustomer2.getCustomerId(),testVoucher2.getVoucherId(),LocalDateTime.now());
        testCase3 = new CustomerVoucherEntity(UUID.randomUUID(), testCustomer.getCustomerId(),testVoucher2.getVoucherId(),LocalDateTime.now());
        var mysqldConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia.Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-order-mgmt", classPathScript("schema.sql"))
                .start();
    }


    @Test
    @DisplayName("특정 고객에게 바우처를 할당해준다.")
    void testInsert() {
        customerRepository.insert(testCustomer);
        voucherRepository.insert(testVoucher);

        customerVoucherRepository.insert(testCase);
        var voucherByCustomer = customerVoucherRepository.findByVoucherId(testVoucher.getVoucherId());
        assertThat(voucherByCustomer.get(), samePropertyValuesAs(testCustomer));
    }

    @Test
    @DisplayName("고객에게 할당된 바우쳐의 정보를 조회할 수 있다.")
    void testFindAll() {
        customerRepository.insert(testCustomer);
        voucherRepository.insert(testVoucher);
        customerVoucherRepository.insert(testCase);
        customerRepository.insert(testCustomer2);
        voucherRepository.insert(testVoucher2);
        customerVoucherRepository.insert(testCase2);

        var list = customerVoucherRepository.findAll();
        assertThat(list.get(1).getCustomerVoucherId(), is(testCase.getCustomerVoucherId()));
        assertThat(list.get(1).getCustomerId(), is(testCustomer.getCustomerId()));
        assertThat(list.get(1).getVoucherId(), is(testVoucher.getVoucherId()));
        assertThat(list.get(0).getCustomerVoucherId(), is(testCase2.getCustomerVoucherId()));
        assertThat(list.get(0).getCustomerId(), is(testCustomer2.getCustomerId()));
        assertThat(list.get(0).getVoucherId(), is(testVoucher2.getVoucherId()));
    }

    @Test
    @DisplayName("모든 고객에게 할당된 바우처를 전부 삭제한다.")
    void testDeleteAll() {
        customerRepository.insert(testCustomer);
        voucherRepository.insert(testVoucher);
        customerVoucherRepository.insert(testCase);
        customerRepository.insert(testCustomer2);
        voucherRepository.insert(testVoucher2);
        customerVoucherRepository.insert(testCase2);

        customerVoucherRepository.deleteAll();
        var list = customerVoucherRepository.findAll();
        assertThat(list.isEmpty(), is(true));
        assertThat(list.size(), is(0));
    }

    @Test
    @DisplayName("특정 고객에게 할당된 바우처를 삭제한다.")
    void testDeleteById() {
        customerRepository.insert(testCustomer);
        voucherRepository.insert(testVoucher);
        customerVoucherRepository.insert(testCase);

        customerVoucherRepository.deleteById(testCustomer.getCustomerId(),testVoucher.getVoucherId());
        var findCase = customerVoucherRepository.findByVoucherId(testVoucher.getVoucherId());
        assertThat(findCase.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객이 어떤 바우처를 보유하고 있는지 조회할 수 있다.")
    void testFindByCustomerId() {
        //testCustomer가 2개의 voucher를 가지고 있다.
        customerRepository.insert(testCustomer);
        voucherRepository.insert(testVoucher);
        customerVoucherRepository.insert(testCase);

        voucherRepository.insert(testVoucher2);
        customerVoucherRepository.insert(testCase3);

        List<VoucherEntity> voucherList = customerVoucherRepository.findByCustomerId(testCustomer.getCustomerId());
        assertThat(voucherList.isEmpty(), is(false));
//        assertThat(voucherList.size(), is(2));
        for(var d : voucherList){
            System.out.println(d.toString());
        }
    }

    @Test
    @DisplayName("특정 바우처를 보유한 고객을 조회할 수 있다.")
    void findByVoucherId() {
        customerRepository.insert(testCustomer);
        voucherRepository.insert(testVoucher);
        customerVoucherRepository.insert(testCase);

        Optional<CustomerEntity> customer = customerVoucherRepository.findByVoucherId(testVoucher.getVoucherId());
        assertThat(customer.get(), samePropertyValuesAs(testCustomer));
    }
}