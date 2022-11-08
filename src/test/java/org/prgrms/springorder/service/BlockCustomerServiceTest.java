package org.prgrms.springorder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

class BlackListServiceTest {

    @Test
    @DisplayName("findAll() 테스트 - 비어있으면 빈 리스트가 리턴된다.")
    void findAllReturnEmptyListTest() {
        //given
        BlockCustomerRepository blockCustomerRepository = mock(BlockCustomerRepository.class);

        BlackListService blackListService = new BlackListService(blockCustomerRepository);

        when(blockCustomerRepository.findAll())
            .thenReturn(new ArrayList<>());

        //when
        List<BlockCustomer> blockCustomers = blackListService.findAll();

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

        BlackListService blackListService = new BlackListService(blockCustomerRepository);

        int size = 5;

        when(blockCustomerRepository.findAll()).thenReturn(
            IntStream.range(0, size)
                .mapToObj(i -> new BlockCustomer(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now()))
                .collect(Collectors.toList())
        );

        //when
        List<BlockCustomer> blockCustomers = blackListService.findAll();

        //then
        assertNotNull(blockCustomers);
        assertEquals(size, blockCustomers.size());

        verify(blockCustomerRepository).findAll();
    }

}