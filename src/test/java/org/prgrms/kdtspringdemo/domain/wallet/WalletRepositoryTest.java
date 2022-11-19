package org.prgrms.kdtspringdemo.domain.wallet;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.domain.customer.model.Customer;
import org.prgrms.kdtspringdemo.domain.customer.repository.CustomerNamedJdbcRepository;
import org.prgrms.kdtspringdemo.domain.customer.repository.CustomerRepository;
import org.prgrms.kdtspringdemo.domain.voucher.VoucherCreator;
import org.prgrms.kdtspringdemo.domain.voucher.model.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.model.VoucherType;
import org.prgrms.kdtspringdemo.domain.voucher.repository.VoucherNamedJdbcRepository;
import org.prgrms.kdtspringdemo.domain.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringJUnitConfig
class WalletRepositoryTest {

    //    @Autowired
    static VoucherCreator voucherCreator = new VoucherCreator();
    static Map<UUID, Voucher> vouchers = new HashMap<>();
    static Map<UUID, Customer> customers = new HashMap<>();
    @Container
    private static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8").withInitScript("schema.sql");
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    CustomerRepository customerRepository;

    @BeforeAll
    static void setupData() {
        for (int i = 0; i < 5; i++) {
            var voucher = voucherCreator.createVoucher(VoucherType.PERCENT, i * 10L);
            var customer = new Customer(UUID.randomUUID(), "test" + i, LocalDate.now(), "test@test" + i, LocalDateTime.now());
            vouchers.put(voucher.getVoucherId(), voucher);
            customers.put(customer.getCustomerId(), customer);

        }
    }

    @BeforeEach
    void setupDB() {
        for (Voucher voucher : vouchers.values()) {
            voucherRepository.insert(voucher);
        }
        for (Customer customer : customers.values()) {
            customerRepository.insert(customer);
        }
    }

    @AfterEach
    void cleanup() {
        walletRepository.deleteAllWallet();
        voucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("customer에 Voucher 추가")
    void addVoucherToCustomer() {
        //given
        var voucherIds = new ArrayList<UUID>(vouchers.keySet());
        var customerIds = new ArrayList<UUID>(customers.keySet());
        //when
        Wallet expectedWallet = new Wallet(customerIds.get(1), voucherIds.get(1));
        Wallet actualWallet = walletRepository.addWallet(expectedWallet);
        assertThat(actualWallet).isEqualTo(expectedWallet);
    }

    @Test
    @DisplayName("CustomerId로 바우처를 조회 성공")
    void finddVouchersByCustomerIdSucess() {
        //given
        var voucherIds = new ArrayList<UUID>(vouchers.keySet());
        var customerIds = new ArrayList<UUID>(customers.keySet());
        //when
        for (int i = 0; i < voucherIds.size(); i++) {
            walletRepository.addWallet(new Wallet(customerIds.get(1), voucherIds.get(i)));
        }
        var selectedVoucherIds = walletRepository.findVoucherIdsByCustomerId(customerIds.get(1));
        //then
        voucherIds.sort(Comparator.naturalOrder());
        selectedVoucherIds.sort(Comparator.naturalOrder());
        assertThat(selectedVoucherIds.size()).isEqualTo(voucherIds.size());
        for (int i = 0; i < voucherIds.size(); i++) {
            assertThat(selectedVoucherIds.get(i)).isEqualTo(voucherIds.get(i));
        }

    }

    @Test
    @DisplayName("CustomerId로 바우처를 조회 성공[바우쳐가 0개]")
    void findVouchersByCustomerIdSucessNoVoucher() {
        //given
        var voucherIds = new ArrayList<UUID>(vouchers.keySet());
        var customerIds = new ArrayList<UUID>(customers.keySet());
        //when
        for (int i = 0; i < voucherIds.size(); i++) {
            walletRepository.addWallet(new Wallet(customerIds.get(1), voucherIds.get(i)));
        }
        //when
        var voucherIdsByCustomerId = walletRepository.findVoucherIdsByCustomerId(customerIds.get(2));
        //tjen
        assertThat(voucherIdsByCustomerId.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("CustomerId로 바우처를 조회 실패[잘못된 ID]")
    void findVouchersByCustomerIdFailWrongId() {
        //given
        var voucherIds = new ArrayList<UUID>(vouchers.keySet());
        var customerIds = new ArrayList<UUID>(customers.keySet());

        var voucherIdsByCustomerId = walletRepository.findVoucherIdsByCustomerId(customerIds.get(1));
        assertThat(voucherIdsByCustomerId.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("VoucherId 로 CustomerId 조회")
    void findCustomersByVoucherId() {
        var voucherIds = new ArrayList<UUID>(vouchers.keySet());
        var customerIds = new ArrayList<UUID>(customers.keySet());
        //when
        for (int i = 0; i < voucherIds.size(); i++) {
            walletRepository.addWallet(new Wallet(customerIds.get(1), voucherIds.get(i)));
            if (i == 3) {
                walletRepository.addWallet(new Wallet(customerIds.get(3), voucherIds.get(i)));
            }
        }
        var customerIdsByVoucherId = walletRepository.findCustomerIdsByVoucherId(voucherIds.get(3));
        assertThat(customerIdsByVoucherId.size()).isEqualTo(2);
        for (UUID customerId : customerIdsByVoucherId) {
            var actualCustomer = customerRepository.findById(customerId);
            var expectedCustomer = customers.get(customerId);
            if (actualCustomer.isPresent()) {
                assertThat(actualCustomer.get()).isEqualTo(expectedCustomer);
            }
        }
    }

    @Test
    @DisplayName("customer와 voucher의 연결 제거")
    void deleteVoucherFromCustomer() {
        var voucherIds = new ArrayList<UUID>(vouchers.keySet());
        var customerIds = new ArrayList<UUID>(customers.keySet());
        //when
        for (int i = 0; i < voucherIds.size(); i++) {
            walletRepository.addWallet(new Wallet(customerIds.get(1), voucherIds.get(i)));
        }
        var beDeletedWallet = new Wallet(customerIds.get(1), voucherIds.get(2));
        walletRepository.deleteWallet(beDeletedWallet);
        assertThat(walletRepository.count()).isEqualTo(voucherIds.size() - 1);

    }

    @Test
    @DisplayName("customer가 삭제될때 관련된 wallet도 삭제도야한다.")
    void shouldBeDeletedWhenCustomerDeleted() {
        var voucherIds = new ArrayList<UUID>(vouchers.keySet());
        var customerIds = new ArrayList<UUID>(customers.keySet());
        for (int i = 0; i < voucherIds.size(); i++) {
            walletRepository.addWallet(new Wallet(customerIds.get(1), voucherIds.get(i)));
        }
        //when
        customerRepository.deleteById(customerIds.get(1));
        //then
        assertThat(walletRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("voucher가 삭제될때 관련된 wallet도 삭제되야한다.")
    void shouldBeDeleteWhenVoucherDeleted() {
        var voucherIds = new ArrayList<UUID>(vouchers.keySet());
        var customerIds = new ArrayList<UUID>(customers.keySet());
        for (int i = 0; i < voucherIds.size(); i++) {
            walletRepository.addWallet(new Wallet(customerIds.get(i), voucherIds.get(1)));
        }
        //when
        voucherRepository.deleteById(voucherIds.get(1));
        //then
        assertThat(walletRepository.count()).isEqualTo(0);
    }

    @Configuration
    @ComponentScan(basePackages = "org.prgrms.kdtspringdemo.domain.wallet")
    static class config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url(mySQLContainer.getJdbcUrl())
                    .username(mySQLContainer.getUsername())
                    .password(mySQLContainer.getPassword())
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

        @Bean
        public VoucherCreator voucherCreator() {
            return new VoucherCreator();
        }

        @Bean
        VoucherRepository voucherRepository(NamedParameterJdbcTemplate jdbcTemplate, VoucherCreator voucherCreator) {
            return new VoucherNamedJdbcRepository(jdbcTemplate, voucherCreator);
        }

        @Bean
        CustomerRepository customerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new CustomerNamedJdbcRepository(jdbcTemplate);
        }
    }


}