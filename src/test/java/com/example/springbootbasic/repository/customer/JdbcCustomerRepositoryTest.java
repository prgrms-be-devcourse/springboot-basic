package com.example.springbootbasic.repository.customer;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
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

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.springbootbasic.domain.customer.CustomerStatus.BLACK;
import static com.example.springbootbasic.domain.customer.CustomerStatus.NORMAL;
import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;
import static com.example.springbootbasic.domain.voucher.VoucherType.PERCENT_DISCOUNT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JdbcCustomerRepositoryTest {

    private final LocalDateTime startAt = LocalDateTime.of(2022, Month.OCTOBER, 25, 0, 0);
    private final LocalDateTime endAt = LocalDateTime.of(2022, Month.DECEMBER, 25, 0, 0);

    @Autowired
    private JdbcCustomerRepository customerRepository;

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
        customerRepository.deleteAllCustomerVoucher();
        customerRepository.deleteAllCustomers();
        voucherRepository.deleteAllVouchers();
    }

    @Test
    @DisplayName("모든 타입의 고객 검색을 성공한다.")
    void whenFindAllCustomersThenSuccessTest() {
        // given
        Customer customer1 = new Customer(NORMAL);
        Customer customer2 = new Customer(NORMAL);
        Customer customer3 = new Customer(NORMAL);
        Customer customer4 = new Customer(BLACK);

        Customer savedCustomer1 = customerRepository.saveCustomer(customer1);
        Customer savedCustomer2 = customerRepository.saveCustomer(customer2);
        Customer savedCustomer3 = customerRepository.saveCustomer(customer3);
        Customer savedCustomer4 = customerRepository.saveCustomer(customer4);

        // when
        List<Customer> allCustomers = customerRepository.findAllCustomers();
        List<Long> allCustomerIds = allCustomers.stream()
                .map(Customer::getCustomerId)
                .collect(Collectors.toList());

        // then
        assertThat(allCustomerIds).contains(savedCustomer1.getCustomerId(),
                savedCustomer2.getCustomerId(),
                savedCustomer3.getCustomerId(),
                savedCustomer4.getCustomerId());
    }

    @Test
    @DisplayName("일반 타입으로 등록된 모든 고객 검색을 성공한다.")
    void whenFindAllNormalStatusCustomersThenSuccessTest() {
        // given
        Customer customer1 = new Customer(BLACK);
        Customer customer2 = new Customer(BLACK);
        Customer customer3 = new Customer(BLACK);
        Customer customer4 = new Customer(BLACK);

        customerRepository.saveCustomer(customer1);
        customerRepository.saveCustomer(customer2);
        customerRepository.saveCustomer(customer3);
        customerRepository.saveCustomer(customer4);

        // when
        List<Customer> allCustomers = customerRepository.findCustomersByStatus(NORMAL);

        // then
        assertThat(allCustomers).isEqualTo(Collections.emptyList());
    }

    @Test
    @DisplayName("블랙리스트로 등록된 모든 고객 검색을 성공한다.")
    void whenFindAllBlackStatusCustomersThenSuccessTest() {
        // given
        Customer customer1 = new Customer(NORMAL);
        Customer customer2 = new Customer(NORMAL);
        Customer customer3 = new Customer(NORMAL);
        Customer customer4 = new Customer(BLACK);

        customerRepository.saveCustomer(customer1);
        customerRepository.saveCustomer(customer2);
        customerRepository.saveCustomer(customer3);
        customerRepository.saveCustomer(customer4);

        // when
        List<Customer> blackCustomers = customerRepository.findCustomersByStatus(BLACK);

        // then
        assertThat(blackCustomers).hasSize(1);
    }

    @Test
    @DisplayName("타입을 통한 고객 검색 중 타입에 null이 들어갔을 경우 Collection.emptyList()를 반환한다.")
    void whenFindCustomersByStatusNullThenSuccessTest() {
        // when
        List<Customer> findAllCustomers = customerRepository.findCustomersByStatus(null);

        // then
        assertThat(findAllCustomers).isEqualTo(Collections.emptyList());
    }

    @Test
    @DisplayName("고객 저장 후 저장된 고객 조회를 성공한다.")
    void whenSaveCustomerThenSuccessTest() {
        // given
        Customer customer1 = new Customer(NORMAL);
        Customer customer2 = new Customer(BLACK);

        // when
        Customer savedCustomer1 = customerRepository.saveCustomer(customer1);
        Customer savedCustomer2 = customerRepository.saveCustomer(customer2);
        List<Customer> allCustomers = customerRepository.findAllCustomers();

        // then
        List<CustomerStatus> allCustomerStatuses = allCustomers.stream()
                .map(Customer::getStatus)
                .collect(Collectors.toList());
        List<Long> allCustomerIds = allCustomers.stream()
                .map(Customer::getCustomerId)
                .collect(Collectors.toList());

        assertThat(allCustomerStatuses).contains(savedCustomer1.getStatus(), savedCustomer2.getStatus());
        assertThat(allCustomerIds).contains(savedCustomer1.getCustomerId(), savedCustomer2.getCustomerId());
    }

    @Test
    @DisplayName("고객의 바우처 목록에 바우처 저장 후 해당 고객의 바우처 아이디 목록 검색을 성공한다.")
    void whenCustomerSaveVoucherThenSuccessTest() {
        // given
        Customer customer = new Customer(0L, NORMAL);
        Voucher fixedVoucher = VoucherFactory.of(10000L, FIXED_AMOUNT, LocalDateTime.now(), startAt, endAt);
        Voucher percentVoucher = VoucherFactory.of(10L, PERCENT_DISCOUNT, LocalDateTime.now(), startAt, endAt);

        Customer savedCustomer = customerRepository.saveCustomer(customer);
        Voucher savedFixedVoucher = voucherRepository.save(fixedVoucher);
        Voucher savedPercentVoucher = voucherRepository.save(percentVoucher);
        customerRepository.saveVoucher(savedCustomer, savedFixedVoucher);
        customerRepository.saveVoucher(savedCustomer, savedPercentVoucher);

        // when
        List<Long> findVoucherIds = customerRepository.findVoucherIdsByCustomerId(savedCustomer.getCustomerId());

        // then
        assertThat(findVoucherIds).contains(savedFixedVoucher.getVoucherId(), savedPercentVoucher.getVoucherId());
    }

    @Test
    @DisplayName("주어진 바우처를 가지고 있는 모든 고객 검색을 성공한다.")
    void whenFindCustomersByVouchersThenSuccessTest() {
        // given
        Customer customer1 = new Customer(NORMAL);
        Customer customer2 = new Customer(NORMAL);
        Customer customer3 = new Customer(NORMAL);
        Customer customer4 = new Customer(NORMAL);
        Voucher fixedVoucher1 = VoucherFactory.of(1000L, FIXED_AMOUNT, LocalDateTime.now(), startAt, endAt);

        Customer savedCustomer1 = customerRepository.saveCustomer(customer1);
        Customer savedCustomer2 = customerRepository.saveCustomer(customer2);
        Customer savedCustomer3 = customerRepository.saveCustomer(customer3);
        Customer savedCustomer4 = customerRepository.saveCustomer(customer4);
        Voucher savedFixedVoucher = voucherRepository.save(fixedVoucher1);

        customerRepository.saveVoucher(savedCustomer1, savedFixedVoucher);
        customerRepository.saveVoucher(savedCustomer2, savedFixedVoucher);
        customerRepository.saveVoucher(savedCustomer3, savedFixedVoucher);
        customerRepository.saveVoucher(savedCustomer4, savedFixedVoucher);

        // when
        List<Long> findAllCustomerIds = customerRepository.findCustomerIdsByVoucherId(savedFixedVoucher.getVoucherId());
        List<Customer> findCustomers = findAllCustomerIds.stream()
                .map(customerId -> customerRepository.findCustomerById(customerId))
                .toList();
        List<Long> findCustomerIds = findCustomers.stream()
                .map(Customer::getCustomerId)
                .toList();

        // then
        assertThat(findCustomerIds).contains(savedCustomer1.getCustomerId(),
                savedCustomer2.getCustomerId(),
                savedCustomer3.getCustomerId(),
                savedCustomer4.getCustomerId());
    }

    @Test
    @DisplayName("고객 아이디를 이용하여 고객이 보유한 모든 바우처를 삭제를 성공한다.")
    void whenDeleteAllVouchersByCustomerIdThenSuccessTest() {
        // given
        Customer customer = new Customer(NORMAL);
        Voucher fixedVoucher = VoucherFactory.of(1000L, FIXED_AMOUNT, LocalDateTime.now(), startAt, endAt);

        Customer savedCustomer = customerRepository.saveCustomer(customer);
        Voucher savedFixedVoucher = voucherRepository.save(fixedVoucher);

        customerRepository.saveVoucher(savedCustomer, savedFixedVoucher);

        // when
        customerRepository.deleteAllVouchersByCustomerId(savedCustomer.getCustomerId());
        List<Long> voucherIdsByCustomer = customerRepository.findVoucherIdsByCustomerId(savedCustomer.getCustomerId());

        // then
        assertThat(voucherIdsByCustomer).isEmpty();
    }
}