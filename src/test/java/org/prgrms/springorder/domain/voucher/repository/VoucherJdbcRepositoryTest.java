package org.prgrms.springorder.domain.voucher.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.customer.repository.CustomerJdbcRepository;
import org.prgrms.springorder.domain.customer.repository.CustomerRepository;
import org.prgrms.springorder.domain.voucher.api.CustomerWithVoucher;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.jdbctest.JdbcTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


@TestInstance(Lifecycle.PER_CLASS)
public class VoucherJdbcRepositoryTest extends JdbcTestBase {

    private VoucherJdbcRepository voucherJdbcRepository;

    private CustomerRepository customerRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeAll
    void setup() {
        voucherJdbcRepository = new VoucherJdbcRepository(jdbcTemplate);
        customerRepository = new CustomerJdbcRepository(jdbcTemplate);
    }

    @DisplayName("조회 테스트 - 저장된 바우처가 없다면 빈 옵셔널을 반환한다.")
    @Test
    void findByIdReturnEmpty() {
        //given & when
        UUID randomUUID = UUID.randomUUID();
        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(randomUUID);
        //then
        assertTrue(voucherOptional.isEmpty());
    }

    @DisplayName("저장 테스트 - customerId가 null 이여도 바우차가 정상적으로 저장되고 조회된다")
    @Test
    void saveSuccessWithCustomerIdIsNullTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, 100);

        //when
        Voucher savedVoucher = assertDoesNotThrow(() -> voucherJdbcRepository.insert(voucher));
        //then
        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);

        assertTrue(voucherOptional.isPresent());
        Voucher findVoucher = voucherOptional.get();
        assertEquals(savedVoucher, findVoucher);
        assertNull(findVoucher.getCustomerId());
        assertNotNull(findVoucher.getCreatedAt());
    }

    @DisplayName("저장 참조 무결성 예외 테스트 - customerId가 존재하지 않는다면 DataIntegrityViolationException 예외가 발생하고 저장되지 않는다 ")
    @Test
    void saveSuccessWithCustomerIdThrowsExceptionTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        Voucher voucher = new FixedAmountVoucher(voucherId, 100, customerId, createdAt);

        //when & then
        assertThrows(DataIntegrityViolationException.class, () -> voucherJdbcRepository.insert(voucher));

        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);

        assertTrue(voucherOptional.isEmpty());
    }

    @DisplayName("저장 테스트 - customer 가 존재하고, customerId가 null 이아니여도 존재하면 voucher가 정상적으로 저장되고 조회된다")
    @Test
    void saveSuccessWithCustomerIdTest() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "testName" , "testEmail@gmail.com");

        customerRepository.insert(customer);

        UUID voucherId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        Voucher voucher = new FixedAmountVoucher(voucherId, 100, customerId, createdAt);

        //when
        Voucher savedVoucher = assertDoesNotThrow(() -> voucherJdbcRepository.insert(voucher));

        //then
        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);

        assertTrue(voucherOptional.isPresent());
        Voucher findVoucher = voucherOptional.get();
        assertEquals(savedVoucher, findVoucher);
        assertEquals(customerId, findVoucher.getCustomerId());
        assertNotNull(findVoucher.getCustomerId());
        assertNotNull(findVoucher.getCreatedAt());
        assertEquals(createdAt, findVoucher.getCreatedAt());
    }


    @DisplayName("저장 테스트 -  PK로 중복된 값을 삽입하면 예외가 발생한다. ")
    @Test
    void insert() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, 100);
        Voucher duplicateVoucher = new FixedAmountVoucher(voucherId, 100);

        voucherJdbcRepository.insert(voucher);
        //when & then
        assertThrows(DuplicateKeyException.class, () -> voucherJdbcRepository.insert(duplicateVoucher));
    }

    @DisplayName("findAll 테스트 - 저장된 Voucher 가 모두 리턴된다.")
    @Test
    void findAllTest() {
        //given
        int saveCount = 5;

        List<Voucher> vouchers = createVouchers(saveCount);

        vouchers.forEach(voucher -> voucherJdbcRepository.insert(voucher));

        //when
        List<Voucher> findVouchers = voucherJdbcRepository.findAll();

        //then
        assertNotNull(vouchers);
        assertEquals(saveCount, vouchers.size());
        assertThat(findVouchers).isEqualTo(vouchers);
    }

    @DisplayName("findAll 테스트 - 저장된 Voucher 가 없다면 빈 리스트가 반환된다.")
    @Test
    void findAllReturnEmptyListTest() {
        //given & when
        List<Voucher> vouchers = voucherJdbcRepository.findAll();
        //then
        assertNotNull(vouchers);
        assertTrue(vouchers.isEmpty());
    }

    @DisplayName("update 참조 무결성 실패 테스트 - customerId가 null이 아니고 존재하지 않는 ID라면 예외를 던진다 ")
    @Test
    void updateVoucherFailTest() {
        //given
        UUID customerId = UUID.randomUUID();

        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherJdbcRepository.insert(voucher);

        long updateAmount = 10L;
        VoucherType updateVoucherType = VoucherType.PERCENT;
        Voucher updateVoucher = new PercentDiscountVoucher(voucherId, updateAmount);
        updateVoucher.changeCustomerId(customerId);

        //when & then
        assertThrows(DataIntegrityViolationException.class, () -> voucherJdbcRepository.update(updateVoucher));

    }

    @DisplayName("update 테스트 - 저장된 Voucher의 Type, amount, customerId 가 바뀐다. ")
    @Test
    void updateVoucherFailThrowsExceptionTest() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "testName" , "testEmail@gmail.com");

        customerRepository.insert(customer);

        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherJdbcRepository.insert(voucher);

        long updateAmount = 10L;
        VoucherType updateVoucherType = VoucherType.PERCENT;
        Voucher updateVoucher = new PercentDiscountVoucher(voucherId, updateAmount);
        updateVoucher.changeCustomerId(customerId);

        //when
        Voucher updatedVoucher = voucherJdbcRepository.update(updateVoucher);

        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);

        //then
        assertEquals(updatedVoucher, updatedVoucher);
        assertTrue(voucherOptional.isPresent());
        Voucher findUpdatedVoucher = voucherOptional.get();
        assertEquals(updatedVoucher, findUpdatedVoucher);

        assertEquals(voucherId, findUpdatedVoucher.getVoucherId());
        assertEquals(updateAmount, findUpdatedVoucher.getAmount());
        assertEquals(updateVoucherType, findUpdatedVoucher.getVoucherType());
        assertEquals(customer.getCustomerId(), findUpdatedVoucher.getCustomerId());
    }

    @DisplayName("update 테스트 - 저장된 Voucher의 customerId 가 바뀐다. ")
    @Test
    void updateVoucherCustomerId() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "testName" , "testEmail@gmail.com");

        customerRepository.insert(customer);

        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherJdbcRepository.insert(voucher);

        long updateAmount = 10L;
        Voucher updateVoucher = new FixedAmountVoucher(voucherId, updateAmount);
        updateVoucher.changeCustomerId(customerId);

        //when
        Voucher updatedVoucher = voucherJdbcRepository.update(updateVoucher);

        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);

        //then
        assertEquals(updatedVoucher, updatedVoucher);
        assertTrue(voucherOptional.isPresent());
        Voucher findUpdatedVoucher = voucherOptional.get();
        assertEquals(updatedVoucher, findUpdatedVoucher);

        assertEquals(voucherId, findUpdatedVoucher.getVoucherId());
        assertEquals(updateAmount, findUpdatedVoucher.getAmount());
        assertEquals(customer.getCustomerId(), findUpdatedVoucher.getCustomerId());
    }

    @DisplayName("update 테스트 - 저장된 Voucher의 amount가 바뀐다. ")
    @Test
    void updateVoucherAmount() {
        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherJdbcRepository.insert(voucher);

        long updateAmount = 10L;
        Voucher updateVoucher = new FixedAmountVoucher(voucherId, updateAmount);

        //when
        Voucher updatedVoucher = voucherJdbcRepository.update(updateVoucher);

        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);

        //then
        assertEquals(updatedVoucher, updatedVoucher);
        assertTrue(voucherOptional.isPresent());
        Voucher findUpdatedVoucher = voucherOptional.get();
        assertEquals(updatedVoucher, findUpdatedVoucher);

        assertEquals(voucherId, findUpdatedVoucher.getVoucherId());
        assertEquals(updateAmount, findUpdatedVoucher.getAmount());
    }

    @DisplayName("update 테스트 - 저장된 Voucher의 Type이 바뀐다. ")
    @Test
    void updateVoucherType() {
        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherJdbcRepository.insert(voucher);

        VoucherType updateVoucherType = VoucherType.PERCENT;
        Voucher updateVoucher = new PercentDiscountVoucher(voucherId, amount);

        //when
        Voucher updatedVoucher = voucherJdbcRepository.update(updateVoucher);

        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);

        //then
        assertEquals(updatedVoucher, updatedVoucher);
        assertTrue(voucherOptional.isPresent());
        Voucher findUpdatedVoucher = voucherOptional.get();
        assertEquals(updatedVoucher, findUpdatedVoucher);

        assertEquals(voucherId, findUpdatedVoucher.getVoucherId());
        assertEquals(updateVoucherType, findUpdatedVoucher.getVoucherType());
    }

    @DisplayName("deleteById 테스트 - voucherId로 저장된 Voucher가 제거된다.")
    @Test
    void deleteByIdTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherJdbcRepository.insert(voucher);

        //when
        voucherJdbcRepository.deleteById(voucherId);

        //then
        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);

        assertTrue(voucherOptional.isEmpty());
    }

    @DisplayName("deleteAll 테스트 - 저장된 모든 voucher 가 삭제된다.")
    @Test
    void deleteAllTest() {
        //given
        createVouchers(3).forEach(voucher -> voucherJdbcRepository.insert(voucher));
        //when
        voucherJdbcRepository.deleteAll();

        List<Voucher> vouchers = voucherJdbcRepository.findAll();

        //then
        assertTrue(vouchers.isEmpty());
    }

    @DisplayName("findByIdWithCustomer Join 테스트 - voucherId로 저장된 voucher와 customer를 함께 가져온다")
    @Test
    void findByIdWithCustomerTest() {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "testName";
        String email = "testEmail@gmail.com";
        Customer customer = new Customer(customerId, name, email);
        customerRepository.insert(customer);

        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount, customerId, LocalDateTime.now());
        voucherJdbcRepository.insert(voucher);

        //when
        Optional<CustomerWithVoucher> withOptional =
            voucherJdbcRepository.findByIdWithCustomer(voucherId);

        //then
        assertTrue(withOptional.isPresent());

        CustomerWithVoucher customerWithVoucher = withOptional.get();
        assertEquals(customerId, customerWithVoucher.getCustomerId());
        assertEquals(voucherId, customerWithVoucher.getVoucherId());
        assertEquals(amount, customerWithVoucher.getAmount());
        assertEquals(name, customerWithVoucher.getName());
        assertEquals(email, customerWithVoucher.getEmail());
    }

    @DisplayName("findByIdWithCustomer Join 테스트 - 저장된 값이 없으면 빈 옵셔널을 반환한다. ")
    @Test
    void findByIdWithCustomerReturnNullTest() {
        //given
        UUID voucherId = UUID.randomUUID();

        //when
        Optional<CustomerWithVoucher> withOptional =
            voucherJdbcRepository.findByIdWithCustomer(voucherId);

        //then
        assertTrue(withOptional.isEmpty());
    }

    private List<Voucher> createVouchers(int saveCount) {
        return IntStream.range(0, saveCount).mapToObj(i -> {

            if (i % 2 == 0) {
                return new PercentDiscountVoucher(UUID.randomUUID(), i);
            }
            return new FixedAmountVoucher(UUID.randomUUID(), i);

        }).collect(Collectors.toList());
    }

}
