package org.prgrms.springbootbasic.repository.customer;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.customer.Customer;
import org.prgrms.springbootbasic.entity.customer.Email;
import org.prgrms.springbootbasic.entity.customer.Name;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentDiscountVoucher;
import org.prgrms.springbootbasic.repository.voucher.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestInstance(Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        var mysqldConfig = aMysqldConfig(v8_0_11)
            .withCharset(Charset.UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
            .addSchema("test_springboot_basic", classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    void clean() {
        embeddedMysql.stop();
    }

    @AfterEach
    void init() {
        jdbcVoucherRepository.removeAll();
        jdbcCustomerRepository.removeAll();
    }

    @DisplayName("모든 회원 조회 테스트 - 회원 존재하는 경우")
    @Test
    void findAll() {
        //given
        jdbcCustomerRepository.save(
            new Customer(UUID.randomUUID(), new Name("test00"), new Email("test00@gmail.com")));
        jdbcCustomerRepository.save(
            new Customer(UUID.randomUUID(), new Name("test01"), new Email("test01@gmail.com")));
        jdbcCustomerRepository.save(
            new Customer(UUID.randomUUID(), new Name("test02"), new Email("test02@gmail.com")));

        //when
        var customers = jdbcCustomerRepository.findAll();

        //then
        assertThat(customers.size()).isEqualTo(3);
    }

    @DisplayName("모든 회원 조회 테스트 - 회원 존재하지 않는 경우")
    @Test
    void findAllEmpty() {
        //given
        //when
        var customers = jdbcCustomerRepository.findAll();

        //then
        assertThat(customers).isEmpty();
    }

    @DisplayName("회원 저장 기능 테스트 - 성공")
    @Test
    void save() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), new Name("hyuk"),
            new Email("hyuk@gmail.com"));

        //when
        var saveCustomerId = jdbcCustomerRepository.save(customer);

        //then
        assertThat(saveCustomerId).isEqualTo(customer.getCustomerId());
    }

    @DisplayName("회원 저장 기능 테스트 - 실패(이메일 중복)")
    @Test
    void saveFail() {
        //given
        jdbcCustomerRepository.save(
            new Customer(UUID.randomUUID(), new Name("test"), new Email("test@gmail.com")));

        //when
        //then
        assertThatThrownBy(() -> jdbcCustomerRepository.save(
            new Customer(UUID.randomUUID(), new Name("test1"), new Email("test@gmail.com"))))
            .isInstanceOf(DataAccessException.class);
    }

    @DisplayName("전체 회원 삭제 테스트 - 회원이 존재하는 경우")
    @Test
    void removeAll() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), new Name("test"),
            new Email("test@gmail.com"));
        jdbcCustomerRepository.save(customer);

        //when
        jdbcCustomerRepository.removeAll();

        //then
        assertThat(jdbcCustomerRepository.findAll().size()).isEqualTo(0);
    }

    @DisplayName("전체 회원 삭제 테스트 - 회원이 존재하지 않는 경우")
    @Test
    void removeAllWhenEmpty() {
        //given
        //when
        jdbcCustomerRepository.removeAll();

        //then
        assertThat(true).isTrue();
    }

    @DisplayName("이름 변경 기능 테스트")
    @Test
    void changeName() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), new Name("test"),
            new Email("test@gmail.com"));
        jdbcCustomerRepository.save(customer);

        String newName = "newTest";
        customer.changeName(newName);

        //when
        var updatedCustomerId = jdbcCustomerRepository.updateName(customer);

        //then
        var customers = jdbcCustomerRepository.findById(customer.getCustomerId());
        assertThat(customers.get().getName().getName())
            .isEqualTo(newName);
    }

    @DisplayName("이메일로 회원 조회 테스트")
    @ParameterizedTest
    @CsvSource(value = {"test@gmail.com, true", "test2@gmail.com, false"})
    void findByEmail(String email, boolean expected) {
        //given
        var customer = new Customer(UUID.randomUUID(), new Name("test"),
            new Email("test@gmail.com"));
        jdbcCustomerRepository.save(customer);

        //when
        var findCustomer = jdbcCustomerRepository.findByEmail(email);

        //then
        assertThat(findCustomer.isPresent()).isEqualTo(expected);
    }

    @DisplayName("특정 바우처를 가진 회원 조회 - fixed, 손님이 존재하는 케이스")
    @Test
    void findByVoucherTypeFixed() {
        //given
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        jdbcVoucherRepository.save(voucher);

        var customer = new Customer(UUID.randomUUID(), new Name("test"),
            new Email("test@gmail.com"));
        jdbcCustomerRepository.save(customer);

        voucher.assignCustomer(customer);
        jdbcVoucherRepository.updateCustomerId(voucher);

        //when
        var customers = jdbcCustomerRepository.findByVoucherType(VoucherType.FIXED);

        //then
        assertThat(customers).isNotEmpty();
        assertThat(customers).containsExactlyInAnyOrder(customer);
    }

    @DisplayName("특정 바우처를 가진 회원 조회 - fixed, 손님이 존재하지 않는 케이스")
    @Test
    void findByVoucherTypeFixedEmpty() {
        //given
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        jdbcVoucherRepository.save(voucher);

        var customer = new Customer(UUID.randomUUID(), new Name("test"),
            new Email("test@gmail.com"));
        jdbcCustomerRepository.save(customer);

        voucher.assignCustomer(customer);
        jdbcVoucherRepository.updateCustomerId(voucher);

        //when
        var customers = jdbcCustomerRepository.findByVoucherType(VoucherType.FIXED);

        //then
        assertThat(customers).isEmpty();
    }

    @DisplayName("특정 바우처를 가진 회원 조회 - percent, 손님이 존재하는 케이스")
    @Test
    void findByVoucherTypePercent() {
        //given
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        jdbcVoucherRepository.save(voucher);

        var customer = new Customer(UUID.randomUUID(), new Name("test"),
            new Email("test@gmail.com"));
        jdbcCustomerRepository.save(customer);

        voucher.assignCustomer(customer);
        jdbcVoucherRepository.updateCustomerId(voucher);

        //when
        var customers = jdbcCustomerRepository.findByVoucherType(VoucherType.PERCENT);

        //then
        assertThat(customers).isNotEmpty();
        assertThat(customers).containsExactlyInAnyOrder(customer);
    }

    @DisplayName("특정 바우처를 가진 회원 조회 - percent, 손님이 존재하지 않는 케이스")
    @Test
    void findByVoucherTypePercentEmpty() {
        //given
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 2000);
        jdbcVoucherRepository.save(voucher);

        var customer = new Customer(UUID.randomUUID(), new Name("test"),
            new Email("test@gmail.com"));
        jdbcCustomerRepository.save(customer);

        voucher.assignCustomer(customer);
        jdbcVoucherRepository.updateCustomerId(voucher);

        //when
        var customers = jdbcCustomerRepository.findByVoucherType(VoucherType.PERCENT);

        //then
        assertThat(customers).isEmpty();
    }

    @Test
    @DisplayName("특정 아이디를 가진 바우처 삭제")
    void deleteById() {
        //given
        var customer = new Customer(UUID.randomUUID(), new Name("test"),
            new Email("test@gmail.com"));
        jdbcCustomerRepository.save(customer);

        //when
        jdbcCustomerRepository.deleteById(customer.getCustomerId());

        //then
        assertThat(jdbcCustomerRepository.findAll()).isEmpty();
    }


    @Configuration
    @ComponentScan(
        basePackages = {"org.prgrms.springbootbasic.repository.customer"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/test_springboot_basic")
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
        public JdbcVoucherRepository jdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
            return new JdbcVoucherRepository(jdbcTemplate);
        }
    }
}
