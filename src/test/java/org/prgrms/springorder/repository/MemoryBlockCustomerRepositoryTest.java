package org.prgrms.springorder.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.domain.BlockCustomer;

@TestInstance(Lifecycle.PER_CLASS)
class MemoryBlockCustomerRepositoryTest {

    private MemoryBlockCustomerRepository memoryBlockCustomerRepository;

    @BeforeAll
    public void init() {
        memoryBlockCustomerRepository = new MemoryBlockCustomerRepository();
    }

    @BeforeEach
    void beforeEach() {
        memoryBlockCustomerRepository.deleteAll();
    }

    @DisplayName("조회 테스트 - id로 객체를 정상적으로 찾는다.")
    @Test
    void findByIdSuccess() {
        //given
        UUID randomUUID = UUID.randomUUID();
        long amount = 0;
        BlockCustomer blockCustomer = new BlockCustomer(randomUUID, UUID.randomUUID(), LocalDateTime.now());
        memoryBlockCustomerRepository.insert(blockCustomer);

        //when
        Optional<BlockCustomer> optionalBlockCustomer = memoryBlockCustomerRepository.findById(randomUUID);

        //then
        assertTrue(optionalBlockCustomer.isPresent());
        BlockCustomer findBlockCustomer = optionalBlockCustomer.get();
        assertEquals(blockCustomer, findBlockCustomer);
        assertEquals(blockCustomer.getBlockId(), randomUUID);
    }

    @DisplayName("조회 테스트 - id로 객체를 찾지만 없으면 빈 옵셔널을 반환한다. ")
    @Test
    void findByIdReturnEmptyOptional() {
        //given
        UUID randomUUID = UUID.randomUUID();

        //when
        Optional<BlockCustomer> optionalBlockCustomer = memoryBlockCustomerRepository.findById(randomUUID);

        //then
        assertTrue(optionalBlockCustomer.isEmpty());
    }

    @DisplayName("저장 테스트 - 객체가 성공적으로 저장된다.")
    @Test
    void insertSuccessTest() {
        //given
        UUID randomUUID = UUID.randomUUID();
        BlockCustomer blockCustomer = new BlockCustomer(randomUUID, UUID.randomUUID(),
            LocalDateTime.now());

        //when
        BlockCustomer savedBlockCustomer = assertDoesNotThrow(
            () -> memoryBlockCustomerRepository.insert(blockCustomer));

        //then
        assertNotNull(savedBlockCustomer);

        Optional<BlockCustomer> optionalBlockCustomer = memoryBlockCustomerRepository.findById(
            randomUUID);
        assertTrue(optionalBlockCustomer.isPresent());
        BlockCustomer findBlockCustomer = optionalBlockCustomer.get();
        assertEquals(savedBlockCustomer, findBlockCustomer);
    }

    @DisplayName("findAll 테스트 - 저장된 값을 전부 반환한다.")
    @Test
    void findAll() {
        //given
        int saveCount = 5;

        IntStream.range(0, saveCount).forEach(index -> {
            BlockCustomer blockCustomer = new BlockCustomer(UUID.randomUUID(), UUID.randomUUID(),
                LocalDateTime.now());

            memoryBlockCustomerRepository.insert(blockCustomer);
        });

        //when
        List<BlockCustomer> blockCustomers = memoryBlockCustomerRepository.findAll();

        //then
        assertNotNull(blockCustomers);
        assertFalse(blockCustomers.isEmpty());
        assertEquals(saveCount, blockCustomers.size());
    }

    @DisplayName("findAll 테스트 - 저장된 값이 없으면 빈 리스트를 반환한다.")
    @Test
    void findAllEmptyList() {
        //given & when
        List<BlockCustomer> blockCustomers = memoryBlockCustomerRepository.findAll();
        //then
        assertNotNull(blockCustomers);
        assertTrue(blockCustomers.isEmpty());
    }

    @DisplayName("deleteAll 테스트 - 저장된 값을 모두 삭제한다.")
    @Test
    public void deleteAll() {
        //given
        int saveCount = 5;

        IntStream.range(0, saveCount).forEach(index -> {
            BlockCustomer blockCustomer = new BlockCustomer(UUID.randomUUID(), UUID.randomUUID(),
                LocalDateTime.now());

            memoryBlockCustomerRepository.insert(blockCustomer);
        });

        //when
        memoryBlockCustomerRepository.deleteAll();
        List<BlockCustomer> blockCustomers = memoryBlockCustomerRepository.findAll();

        //then
        assertNotNull(blockCustomers);
        assertTrue(blockCustomers.isEmpty());
    }

}