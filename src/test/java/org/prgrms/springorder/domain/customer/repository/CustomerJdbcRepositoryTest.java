package org.prgrms.springorder.domain.customer.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.jdbctest.JdbcTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


@TestInstance(Lifecycle.PER_CLASS)
public class CustomerJdbcRepositoryTest extends JdbcTestBase {

    private CustomerJdbcRepository customerJdbcRepository;

    private UUID customerId = UUID.randomUUID();

    private String name = "testName";

    private String email = "testEmail@gmail.com";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeAll
    void setUp() {
        customerJdbcRepository = new CustomerJdbcRepository(jdbcTemplate);
    }

    @DisplayName("조회 테스트 - 저장된 customer 가 없다면 빈 옵셔널을 반환한다.")
    @Test
    void findByIdReturnEmpty() {
        //given & when
        UUID randomUUID = UUID.randomUUID();
        Optional<Customer> customerOptional = customerJdbcRepository.findById(randomUUID);
        //then
        assertTrue(customerOptional.isEmpty());
    }

    @DisplayName("저장 테스트 - Customer 가 정상적으로 저장되고 조회된다")
    @Test
    void saveSuccessTest() {
        //given
        Customer customer = new Customer(customerId, name, email);

        //when
        Customer savedCustomer = assertDoesNotThrow(() -> customerJdbcRepository.insert(customer));

        //then
        Optional<Customer> customerOptional = customerJdbcRepository.findById(customerId);

        assertTrue(customerOptional.isPresent());
        Customer findCustomer = customerOptional.get();
        assertEquals(savedCustomer, findCustomer);
        assertEquals(name, findCustomer.getName());
        assertEquals(email, findCustomer.getEmail());
        assertNotNull(findCustomer.getCreatedAt());
    }

    @DisplayName("저장 테스트 - PK로 중복된 값을 삽입하면 예외가 발생한다. ")
    @Test
    void insert() {
        //given
        Customer customer = new Customer(customerId, name, email);
        Customer duplicateVoucher = new Customer(customerId, name, email);

        customerJdbcRepository.insert(customer);
        //when & then
        assertThrows(DuplicateKeyException.class,
            () -> customerJdbcRepository.insert(duplicateVoucher));
    }

    @DisplayName("findAll 테스트 - 저장된 Customer 가 모두 리턴된다.")
    @Test
    void findAllTest() {
        //given

        int saveCount = 5;

        List<Customer> customers = createCustomers(saveCount);

        customers.forEach(customer -> customerJdbcRepository.insert(customer));

        //when
        List<Customer> findCustomers = customerJdbcRepository.findAll();

        //then
        assertNotNull(customers);
        assertEquals(saveCount, customers.size());
        assertThat(findCustomers).isEqualTo(customers);
    }

    @DisplayName("findAll 테스트 - 저장된 Customer 가 없다면 빈 리스트가 반환된다.")
    @Test
    void findAllReturnEmptyListTest() {
        //given & when
        List<Customer> customers = customerJdbcRepository.findAll();
        //then
        assertNotNull(customers);
        assertTrue(customers.isEmpty());
    }

    @DisplayName("update 테스트 - 저장된 Customer 의 lastLoginAt 이 바뀐다. ")
    @Test
    void updateVoucherAmount() {
        //given
        Customer customer = new Customer(customerId, name, email);

        customerJdbcRepository.insert(customer);

        LocalDateTime newLastLoginAt = LocalDateTime.now();

        //when
        customer.updateLastLoginAt(newLastLoginAt);

        customerJdbcRepository.update(customer);

        //then
        Optional<Customer> updatedCustomerOptional = customerJdbcRepository.findById(customerId);

        assertTrue(updatedCustomerOptional.isPresent());
        Customer updatedCustomer = updatedCustomerOptional.get();

        assertEquals(customerId, updatedCustomer.getCustomerId());
        assertEquals(customer.getEmail(), updatedCustomer.getEmail());
        assertEquals(newLastLoginAt, updatedCustomer.getLastLoginAt());
    }

    @DisplayName("update 테스트 - 저장된 Customer 의 name 이 바뀐다. ")
    @Test
    void updateVoucherType() {
        //given
        Customer customer = new Customer(customerId, name, email);

        customerJdbcRepository.insert(customer);

        String newName = "newName";

        //when
        customer.changeName(newName);

        customerJdbcRepository.update(customer);

        //then
        Optional<Customer> updatedCustomerOptional = customerJdbcRepository.findById(customerId);

        assertTrue(updatedCustomerOptional.isPresent());
        Customer updatedCustomer = updatedCustomerOptional.get();

        assertEquals(customerId, updatedCustomer.getCustomerId());
        assertEquals(newName, updatedCustomer.getName());
    }

    private List<Customer> createCustomers(int saveCount) {

        return IntStream.range(0, saveCount)
            .mapToObj(i -> new Customer(UUID.randomUUID(), name + i, i + email))
            .collect(Collectors.toList());

    }
}