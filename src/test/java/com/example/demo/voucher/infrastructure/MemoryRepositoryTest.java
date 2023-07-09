package com.example.demo.voucher.infrastructure;

import com.example.demo.voucher.domain.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemoryRepositoryTest {

    private MemoryRepository memoryRepository;
    private Voucher voucherMock;
    private UUID voucherId;

    @BeforeEach
    void setUp() {
        memoryRepository = new MemoryRepository();
        voucherMock = Mockito.mock(Voucher.class);
        voucherId = UUID.randomUUID();

        Mockito.when(voucherMock.getVoucherId()).thenReturn(voucherId);
    }

    @Test
    void insert() {
        // Given
        // When
        memoryRepository.insert(voucherMock);

        // Then
        List<Voucher> result = memoryRepository.findAll();
        assertEquals(1, result.size());
        assertSame(voucherMock, result.get(0));
    }

    @Test
    void findById() {
        // Given
        memoryRepository.insert(voucherMock);

        // When
        Optional<Voucher> result = memoryRepository.findById(voucherId);

        // Then
        assertTrue(result.isPresent());
        assertSame(voucherMock, result.get());
    }

    @Test
    void findById_not_found() {
        // Given
        // When
        Optional<Voucher> result = memoryRepository.findById(voucherId);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findAll() {
        // Given
        memoryRepository.insert(voucherMock);

        // When
        List<Voucher> result = memoryRepository.findAll();

        // Then
        assertEquals(1, result.size());
        assertSame(voucherMock, result.get(0));
    }
}
