package org.prgrms.springorder.domain.customer.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.domain.customer.model.BlockCustomer;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.common.JdbcTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@TestInstance(Lifecycle.PER_CLASS)
class BlockCustomerJdbcRepositoryTest extends JdbcTestBase {

    private BlockCustomerJdbcRepository blockCustomerJdbcRepository;

    private CustomerRepository customerRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeAll
    void setUp() {
        blockCustomerJdbcRepository = new BlockCustomerJdbcRepository(jdbcTemplate);
        customerRepository = new CustomerJdbcRepository(jdbcTemplate);
    }

    @DisplayName("조회 테스트 - 저장된 blockCustomer 가 없다면 빈 옵셔널을 반환한다.")
    @Test
    void findByIdReturnEmpty() {
        //given & when
        UUID randomUUID = UUID.randomUUID();
        Optional<BlockCustomer> blockCustomerOptional = blockCustomerJdbcRepository.findById(randomUUID);
        //then
        assertTrue(blockCustomerOptional.isEmpty());
    }

    @DisplayName("저장 테스트 - blockCustomer 가 정상적으로 저장되고 조회된다")
    @Test
    void saveSuccessTest() {
        //given
        UUID blockId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        BlockCustomer blockCustomer = new BlockCustomer(blockId, customerId, createdAt);

        Customer customer = new Customer(customerId, "test", "testEmail@gamil.com");
        customerRepository.insert(customer);
        //when
        BlockCustomer savedCustomer = assertDoesNotThrow(() -> blockCustomerJdbcRepository.insert(blockCustomer));

        //then
        Optional<BlockCustomer> blockCustomerOptional = blockCustomerJdbcRepository.findById(blockId);

        assertTrue(blockCustomerOptional.isPresent());
        BlockCustomer findBlockCustomer = blockCustomerOptional.get();
        assertEquals(savedCustomer, findBlockCustomer);
        assertNotNull(findBlockCustomer.getCreatedAt());
        assertEquals(blockId, findBlockCustomer.getBlockId());
        assertEquals(customerId, findBlockCustomer.getCustomerId());
    }

    @DisplayName("저장 테스트 - PK로 중복된 값을 삽입하면 예외가 발생한다. ")
    @Test
    void insert() {
        //given
        UUID blockId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        BlockCustomer blockCustomer = new BlockCustomer(blockId, customerId, createdAt);
        BlockCustomer duplicateBlockCustomer = new BlockCustomer(blockId, customerId, createdAt);
        Customer customer = new Customer(customerId, "test", "testEmail@gamil.com");

        customerRepository.insert(customer);
        blockCustomerJdbcRepository.insert(blockCustomer);
        //when & then
        assertThrows(DuplicateKeyException.class,
            () -> blockCustomerJdbcRepository.insert(duplicateBlockCustomer));
    }

    @DisplayName("findAll 테스트 - 저장된 blockCustomer 가 없다면 빈 리스트가 반환된다.")
    @Test
    void findAllReturnEmptyListTest() {
        //given & when
        List<BlockCustomer> blockCustomers = blockCustomerJdbcRepository.findAll();
        //then
        assertNotNull(blockCustomers);
        assertTrue(blockCustomers.isEmpty());
    }

}