package org.prgrms.springorder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springorder.domain.BlockCustomer;
import org.prgrms.springorder.repository.BlockCustomerRepository;

class BlockCustomerServiceTest {

    @Test
    @DisplayName("findAll() 테스트 - 비어있으면 빈 리스트가 리턴된다.")
    void findAllReturnEmptyListTest() {
        //given
        BlockCustomerRepository blockCustomerRepository = mock(BlockCustomerRepository.class);

        BlockCustomerService blockCustomerService = new BlockCustomerService(
            blockCustomerRepository);

        when(blockCustomerRepository.findAll())
            .thenReturn(new ArrayList<>());

        //when
        List<BlockCustomer> blockCustomers = blockCustomerService.findAll();

        //then
        assertNotNull(blockCustomers);
        assertTrue(blockCustomers.isEmpty());

        verify(blockCustomerRepository).findAll();
    }

    @Test
    @DisplayName("findAll() 테스트 - 비어있지 않으면, 저장된 모든 BlockCustomer 정보가 조회된다.")
    void findAllNotEmptyListTest() {

        //given
        BlockCustomerRepository blockCustomerRepository = mock(BlockCustomerRepository.class);

        BlockCustomerService blockCustomerService = new BlockCustomerService(
            blockCustomerRepository);

        int size = 5;

        when(blockCustomerRepository.findAll()).thenReturn(
            toAnyBlockCustomerList(size)
        );

        //when
        List<BlockCustomer> blockCustomers = blockCustomerService.findAll();

        //then
        assertNotNull(blockCustomers);
        assertEquals(size, blockCustomers.size());

        verify(blockCustomerRepository).findAll();
    }

    @Test
    @DisplayName("findAllConvertedToString() 테스트 - 비어있으면 빈 리스트가 리턴된다.")
    void findAllConvertedToStringReturnEmptyListTest() {
        //given
        BlockCustomerRepository blockCustomerRepository = mock(BlockCustomerRepository.class);

        BlockCustomerService blockCustomerService = new BlockCustomerService(
            blockCustomerRepository);

        when(blockCustomerRepository.findAll())
            .thenReturn(new ArrayList<>());

        //when
        List<String> blockCustomers = blockCustomerService.findAllConvertedToString();

        //then
        assertNotNull(blockCustomers);
        assertTrue(blockCustomers.isEmpty());

        verify(blockCustomerRepository).findAll();
    }

    @Test
    @DisplayName("findAllConvertedToString() 테스트 - 비어있지 않으면, 저장된 모든 BlockCustomer 정보가 조회된다.")
    void findAllConvertedToStringNotEmptyListTest() {

        //given
        BlockCustomerRepository blockCustomerRepository = mock(BlockCustomerRepository.class);

        BlockCustomerService blockCustomerService = new BlockCustomerService(
            blockCustomerRepository);

        int size = 5;

        List<BlockCustomer> blockCustomers = toAnyBlockCustomerList(size);

        List<String> convertedToStringBlockCustomers = blockCustomers.stream()
            .map(Object::toString)
            .collect(Collectors.toList());

        when(blockCustomerRepository.findAll()).thenReturn(
            blockCustomers
        );

        //when
        List<String> findBlockCustomers = blockCustomerService.findAllConvertedToString();

        //then
        assertNotNull(findBlockCustomers);
        assertEquals(size, findBlockCustomers.size());
        assertLinesMatch(convertedToStringBlockCustomers, findBlockCustomers);

        verify(blockCustomerRepository).findAll();
    }

    private List<BlockCustomer> toAnyBlockCustomerList(int size) {
        return IntStream.range(0, size)
            .mapToObj(
                i -> new BlockCustomer(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now()))
            .collect(Collectors.toList());
    }

}