package com.programmers.kwonjoosung.springbootbasicjoosung.repository.wallet;

import com.programmers.kwonjoosung.springbootbasicjoosung.config.TestDataSourceConfig;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.CustomerRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.JdbcCustomerRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.JdbcVoucherRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@Import(TestDataSourceConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class JdbcWalletRepositoryTest {

    private JdbcWalletRepository jdbcWalletRepository;
    private VoucherRepository voucherRepository;
    private CustomerRepository customerRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        this.jdbcWalletRepository = new JdbcWalletRepository(jdbcTemplate);
        this.voucherRepository = new JdbcVoucherRepository(jdbcTemplate); // 여기서 다른 repository를 사용해도 되나요?
        this.customerRepository = new JdbcCustomerRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("[성공] 바우처id와 고객id를 통해서 지갑에 바우처를 추가할 수 있다.")
    void insertWalletTest() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        voucherRepository.insert(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "joosung");
        customerRepository.insert(customer);
        // when
        jdbcWalletRepository.insertToWallet(customer.getCustomerId(), voucher.getVoucherId());
        Optional<UUID> customerIdOptional = jdbcWalletRepository.findCustomerIdByVoucherId(voucher.getVoucherId());
        List<UUID> voucherIdOptional = jdbcWalletRepository.findVoucherIdsByCustomerId(customer.getCustomerId());
        //then
        assertTrue(customerIdOptional.isPresent());
        assertThat(voucherIdOptional).isNotEmpty();
        assertThat(customerIdOptional.get()).isEqualTo(customer.getCustomerId());
        assertThat(voucherIdOptional).contains(voucher.getVoucherId());
    }

    @Test
    @DisplayName("[실패] 고객id와 바우처id가 없는 경우에는 지갑에 바우처를 추가할 수 없다.")
    void insertWalletByNotExistCustomerIdAndNotExistVoucherIdTest() {
        //when
        boolean insertResult = jdbcWalletRepository.insertToWallet(UUID.randomUUID(), UUID.randomUUID());
        //then
        assertFalse(insertResult);
    }

    @Test
    @DisplayName("[실패] 바우처id가 없는 바우처인 경우에는 지갑에 바우처를 추가할 수 없다.")
    void insertWalletByNotExistVoucherIdTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "joosung");
        customerRepository.insert(customer);
        //when
        boolean insertResult = jdbcWalletRepository.insertToWallet(customer.getCustomerId(), UUID.randomUUID());
        //then
        assertFalse(insertResult);
    }

    @Test
    @DisplayName("[실패] 고객id가 없는 고객인 경우에는 지갑에 바우처를 추가할 수 없다.")
    void insertWalletByNotExistCustomerIdTest() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        voucherRepository.insert(voucher);
        //when
        boolean insertResult = jdbcWalletRepository.insertToWallet(UUID.randomUUID(), voucher.getVoucherId());
        //then
        assertFalse(insertResult);
    }

    @Test
    @DisplayName("[성공] 고객id를 통해 해당 고객이 가진 모든 바우처를 조회할 수 있다.")
    void findAllVouchersByCustomerIdTest() {
        //given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT, UUID.randomUUID(), 10);
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        Customer customer = new Customer(UUID.randomUUID(), "joosung");
        customerRepository.insert(customer);
        jdbcWalletRepository.insertToWallet(customer.getCustomerId(), voucher1.getVoucherId());
        jdbcWalletRepository.insertToWallet(customer.getCustomerId(), voucher2.getVoucherId());
        // when
        List<UUID> vouchersByCustomerId = jdbcWalletRepository.findVoucherIdsByCustomerId(customer.getCustomerId());
        //then
        assertThat(vouchersByCustomerId).contains(voucher1.getVoucherId(), voucher2.getVoucherId());
        assertThat(vouchersByCustomerId).hasSize(2);
    }

    @Test
    @DisplayName("[성공] 바우처id를 통해서 바우처를 소유한 고객id를 조회할 수 있다")
    void findCustomerByVoucherIdTest() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        voucherRepository.insert(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "joosung");
        customerRepository.insert(customer);
        jdbcWalletRepository.insertToWallet(customer.getCustomerId(), voucher.getVoucherId());
        //when
        Optional<UUID> customerIdOptional = jdbcWalletRepository.findCustomerIdByVoucherId(voucher.getVoucherId());
        //then
        assertTrue(customerIdOptional.isPresent());
        assertThat(customerIdOptional.get()).isEqualTo(customer.getCustomerId());
    }

    @Test
    @DisplayName("[실패] 바우처에 할당된 고객이 없는 경우 소유 고객을 조회할 수 없다.")
    void findCustomerIdByNotExistVoucherIdTest() {
        //given &when
        Optional<UUID> customerIdOptional = jdbcWalletRepository.findCustomerIdByVoucherId(UUID.randomUUID());
        //then
        assertFalse(customerIdOptional.isPresent());
    }

    @Test
    @DisplayName("[실패] 고객이 소유한 바우처가 없다면 조회할 수 없다.")
    void find_VoucherIds_By_CustomerId_If_Has_NotThing_Test() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        Customer customer = new Customer(UUID.randomUUID(), "joosung");
        customerRepository.insert(customer);
        // when
        Optional<UUID> customerIdOptional = jdbcWalletRepository.findCustomerIdByVoucherId(voucher.getVoucherId());
        List<UUID> voucherIdOptional = jdbcWalletRepository.findVoucherIdsByCustomerId(customer.getCustomerId());
        //then
        assertFalse(customerIdOptional.isPresent());
        assertThat(voucherIdOptional).isEmpty();
    }

    @Test
    @DisplayName("[성공] 고객이 가진 바우처를 삭제할 수 있다.")
    void deleteVoucherTest() {
        //given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        voucherRepository.insert(voucher1);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT, UUID.randomUUID(), 10);
        voucherRepository.insert(voucher2);
        Customer customer = new Customer(UUID.randomUUID(), "joosung");
        customerRepository.insert(customer);
        jdbcWalletRepository.insertToWallet(customer.getCustomerId(), voucher1.getVoucherId());
        jdbcWalletRepository.insertToWallet(customer.getCustomerId(), voucher2.getVoucherId());
        // when
        jdbcWalletRepository.deleteVoucher(voucher1.getVoucherId());
        List<UUID> vouchersByCustomerId = jdbcWalletRepository.findVoucherIdsByCustomerId(customer.getCustomerId());
        //then
        assertThat(vouchersByCustomerId).contains(voucher2.getVoucherId());
        assertThat(vouchersByCustomerId).hasSize(1);
        Optional<UUID> customerIdByVoucherId = jdbcWalletRepository.findCustomerIdByVoucherId(voucher1.getVoucherId());
        assertFalse(customerIdByVoucherId.isPresent());
    }

    @Test
    @DisplayName("[실패] 바우처 id가 지갑에 없는 경우 바우처를 삭제할 수 없다.")
    void deleteNotExistVoucherTest() {
        //given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        voucherRepository.insert(voucher1);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT, UUID.randomUUID(), 10);
        voucherRepository.insert(voucher2);
        Customer customer = new Customer(UUID.randomUUID(), "joosung");
        customerRepository.insert(customer);
        jdbcWalletRepository.insertToWallet(customer.getCustomerId(), voucher1.getVoucherId());
        // when
        boolean deleteResult = jdbcWalletRepository.deleteVoucher(voucher2.getVoucherId());
        //then
        assertFalse(deleteResult);
    }

    @Test
    @DisplayName("[성공] 바우처 id가 변경 되면 지갑에 있는 바우처id도 변경된다.")
    void updateVoucherIdTest() {
        //given
        Voucher oldVoucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        voucherRepository.insert(oldVoucher);
        Customer customer = new Customer(UUID.randomUUID(), "joosung");
        customerRepository.insert(customer);
        jdbcWalletRepository.insertToWallet(customer.getCustomerId(), oldVoucher.getVoucherId());
        //when
        Voucher newVoucher = VoucherFactory.createVoucher(VoucherType.PERCENT, oldVoucher.getVoucherId(), 10);
        voucherRepository.update(newVoucher);
        //then
        Optional<UUID> customerIdByVoucherId = jdbcWalletRepository.findCustomerIdByVoucherId(newVoucher.getVoucherId());
        assertTrue(customerIdByVoucherId.isPresent());
        assertThat(customerIdByVoucherId.get()).isEqualTo(customer.getCustomerId());
        List<UUID> voucherIdsByCustomerId = jdbcWalletRepository.findVoucherIdsByCustomerId(customer.getCustomerId());
        assertThat(voucherIdsByCustomerId).contains(newVoucher.getVoucherId());
        assertThat(voucherIdsByCustomerId.get(0)).isEqualTo(newVoucher.getVoucherId());
    }

    @Test
    @DisplayName("[성공] 고객 id가 변경 되면 지갑에 있는 고객id도 변경된다.")
    void updateCustomerIdTest() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        voucherRepository.insert(voucher);
        Customer oldCustomer = new Customer(UUID.randomUUID(), "joosung1");
        customerRepository.insert(oldCustomer);
        jdbcWalletRepository.insertToWallet(oldCustomer.getCustomerId(), voucher.getVoucherId());
        //when
        Customer newCustomer = new Customer(oldCustomer.getCustomerId(), "joosung2");
        customerRepository.update(newCustomer);
        //then
        Optional<UUID> customerIdByVoucherId = jdbcWalletRepository.findCustomerIdByVoucherId(voucher.getVoucherId());
        assertTrue(customerIdByVoucherId.isPresent());
        assertThat(customerIdByVoucherId.get()).isEqualTo(newCustomer.getCustomerId());
        List<UUID> voucherIdsByCustomerId = jdbcWalletRepository.findVoucherIdsByCustomerId(newCustomer.getCustomerId());
        assertThat(voucherIdsByCustomerId.get(0)).isEqualTo(voucher.getVoucherId());
    }
    @Test
    @DisplayName("[성공] 바우처 id가 삭제 되면 지갑에 있는 바우처도 삭제된다.")
    void deleteVoucherIdTest() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        voucherRepository.insert(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "joosung");
        customerRepository.insert(customer);
        jdbcWalletRepository.insertToWallet(customer.getCustomerId(), voucher.getVoucherId());
        //when
        voucherRepository.deleteById(voucher.getVoucherId());
        //then
        Optional<UUID> customerIdByVoucherId = jdbcWalletRepository.findCustomerIdByVoucherId(voucher.getVoucherId());
        assertFalse(customerIdByVoucherId.isPresent());
        List<UUID> voucherIdsByCustomerId = jdbcWalletRepository.findVoucherIdsByCustomerId(customer.getCustomerId());
        assertThat(voucherIdsByCustomerId).isEmpty();
    }

    @Test
    @DisplayName("[성공] 고객이 삭제 되면 지갑에 있는 고객도 삭제된다.")
    void deleteCustomerIdTest() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        voucherRepository.insert(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "joosung");
        customerRepository.insert(customer);
        jdbcWalletRepository.insertToWallet(customer.getCustomerId(), voucher.getVoucherId());
        //when
        customerRepository.delete(customer.getCustomerId());
        //then
        Optional<UUID> customerIdByVoucherId = jdbcWalletRepository.findCustomerIdByVoucherId(voucher.getVoucherId());
        assertFalse(customerIdByVoucherId.isPresent());
        List<UUID> voucherIdsByCustomerId = jdbcWalletRepository.findVoucherIdsByCustomerId(customer.getCustomerId());
        assertThat(voucherIdsByCustomerId).isEmpty();
    }

}
