package com.example.springbootbasic.service.customer;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.repository.voucher.JdbcVoucherRepository;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.example.springbootbasic.domain.customer.CustomerStatus.BLACK;
import static com.example.springbootbasic.domain.customer.CustomerStatus.NORMAL;
import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JdbcCustomerServiceTest {

    @Autowired
    private JdbcCustomerService customerService;

    @Autowired
    private JdbcVoucherRepository voucherRepository;

    @BeforeAll
    static void setup() {
        MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        EmbeddedMysql.anEmbeddedMysql(config)
                .addSchema("test_voucher", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterEach
    void clear() {
        customerService.deleteAllCustomerVoucher();
        customerService.deleteAllCustomers();
        voucherRepository.deleteAllVouchers();
    }

    @Test
    @DisplayName("블랙리스트로 등록된 모든 고객 검색에 성공한다.")
    void whenFindBlackCustomersThenSuccessTest() {
        // given
        List<Customer> allBlackCustomers = customerService.findCustomersByStatus(BLACK);

        // when
        boolean isAllBlackCustomers = allBlackCustomers.stream().allMatch(Customer::isBlack);

        // then
        assertThat(isAllBlackCustomers).isTrue();
    }

    @Test
    @DisplayName("일반 타입으로 등록된 모든 고객 검색에 성공한다.")
    void whenFindNormalCustomersThenSuccessTest() {
        // given
        List<Customer> allNormalCustomers = customerService.findCustomersByStatus(NORMAL);

        // when
        boolean isAllNormalCustomers = allNormalCustomers.stream().allMatch(Customer::isNormal);

        // then
        assertThat(isAllNormalCustomers).isTrue();
    }

    @Test
    @DisplayName("타입으로 검색 시 null이 들어갔을 경우 Collections.emptyList()를 반환한다.")
    void whenFindCustomersByNullThenSuccessTest() {
        // when
        List<Customer> allCustomers = customerService.findCustomersByStatus(null);

        // then
        assertThat(allCustomers).isEqualTo(EMPTY_LIST);
    }

    @Test
    @DisplayName("모든 타입 고객 검색에 성공한다.")
    void whenFindAllCustomersThenSuccessTest() {
        // when
        List<Customer> allCustomers = customerService.findAllCustomers();

        // then
        assertThat(allCustomers).isNotNull();
    }

    @Test
    @DisplayName("모든 고객을 검색했을 때 고객 테이블이 비어있을 경우 Collections.EmptyList()를 반환한다.")
    void whenEmptyCustomersThenSuccessTest() {
        // when
        List<Customer> allCustomers = customerService.findAllCustomers();

        // then
        assertThat(allCustomers).isEmpty();
    }

    @Test
    @DisplayName("해당하는 바우처를 가지고 있는 모든 고객 검색에 성공한다.")
    @Sql(value ="classpath:customers-vouchers-dummy.sql")
    void whenFindCustomersWhoHasSelectedVoucherThenSuccessTest() {
        // when
        List<Customer> findAllCustomers = customerService.findCustomersWhoHasSelectedVoucher(1L);
        Voucher findVoucher = voucherRepository.findById(1L);
        boolean checkAllCustomersHaveSelectedVoucher =
                findAllCustomers.stream().allMatch(customer -> customer.hasVoucher(findVoucher));

        // then
        assertThat(checkAllCustomersHaveSelectedVoucher).isTrue();
    }

    @Test
    @DisplayName("고객 아이디를 통해 고객이 지닌 모든 바우처 검색을 성공한다.")
    @Sql(value ="classpath:customers-vouchers-dummy.sql")
    void whenFindVoucherIdsByCustomerIdThenSuccessTest() {
        // when
        List<Voucher> vouchers = customerService.findVouchersByCustomerId(1L);

        // then
        assertThat(vouchers).hasSize(3);
    }

    @Test
    @DisplayName("저장된 고객을 고객 아이디를 이용하여 검색에 성공한다.")
    void whenFindCustomerByIdThenSuccessTest() {
        Customer customer = new Customer(NORMAL);

        // when
        Customer savedCustomer = customerService.saveCustomer(customer);
        Customer findCustomer = customerService.findCustomerById(savedCustomer.getCustomerId());

        // then
        assertThat(findCustomer.getStatus()).isEqualTo(savedCustomer.getStatus());
        assertThat(findCustomer.getCustomerId()).isEqualTo(savedCustomer.getCustomerId());
    }

    @Test
    @DisplayName("고객 아이디를 통해서 찾은 고객이 갖은 모든 바우처 삭제에 성공한다.")
    @Sql(value ="classpath:customers-vouchers-dummy.sql")
    void whenDeleteAllVouchersByCustomerIdThenSuccessTest() {
        // when
        customerService.deleteAllVouchersByCustomerId(1L);
        List<Voucher> vouchers = customerService.findVouchersByCustomerId(1L);

        // then
        assertThat(vouchers).hasSize(0);
    }
}