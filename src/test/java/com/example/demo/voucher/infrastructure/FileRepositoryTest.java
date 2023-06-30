package com.example.demo.voucher.infrastructure;

import com.example.demo.voucher.domain.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileRepositoryTest {

    @Mock
    private VoucherSerializer serializer;
    private FileRepository fileRepository;

    @BeforeEach
    void setUp() {
        fileRepository = new FileRepository(serializer);
    }
    @Test
    @DisplayName("id로 바우처 검색 테스트")
    void findById() throws IOException {
        // Given
        UUID uuid = UUID.randomUUID();
        Voucher voucher = mock(Voucher.class);

        // When
        when(voucher.getVoucherId()).thenReturn(uuid);
        when(serializer.deserialize()).thenReturn(Arrays.asList(voucher));

        Optional<Voucher> result = fileRepository.findById(uuid);

        // Then
        assertEquals(voucher, result.get());
        verify(serializer, times(1)).deserialize();
    }

    @Test
    @DisplayName("id로 바우처 검색 실패 테스트: not_found")
    void findById_notFound() throws IOException {
        // Given
        UUID uuid = UUID.randomUUID();

        // When
        when(serializer.deserialize()).thenReturn(Arrays.asList());

        Optional<Voucher> result = fileRepository.findById(uuid);

        // Then
        assertEquals(Optional.empty(), result);
        verify(serializer, times(1)).deserialize();
    }

    @Test
    @DisplayName("id로 바우처 검색 실패 테스트: IOException")
    void findById_throwsException() throws IOException {
        // Given
        UUID uuid = UUID.randomUUID();

        // When
        when(serializer.deserialize()).thenThrow(new IOException());

        // Then
        assertThrows(RuntimeException.class, () -> fileRepository.findById(uuid));
        verify(serializer, times(1)).deserialize();
    }

    @Test
    @DisplayName("모든 바우처 검색 테스트")
    void findAll() throws IOException {
        // Given
        List<Voucher> vouchers = Arrays.asList(mock(Voucher.class));

        // When
        when(serializer.deserialize()).thenReturn(vouchers);

        List<Voucher> result = fileRepository.findAll();

        // Then
        assertEquals(vouchers, result);
        verify(serializer, times(1)).deserialize();
    }

    @Test
    @DisplayName("id로 바우처 검색 테스트")
    void findAll_throwsException() throws IOException {
        // Given -> BeforeEach

        // When
        when(serializer.deserialize()).thenThrow(new IOException());

        // Then
        assertThrows(RuntimeException.class, () -> fileRepository.findAll());
        verify(serializer, times(1)).deserialize();
    }

    @Test
    @DisplayName("바우처 저장 테스트")
    void insert() throws IOException {
        // Given
        Voucher voucher = mock(Voucher.class);

        // When
        when(serializer.deserialize()).thenReturn(new ArrayList<>(Arrays.asList(voucher)));

        fileRepository.insert(voucher);

        // Then
        verify(serializer, times(1)).deserialize();
        verify(serializer, times(1)).serialize(any());
    }

    @Test
    @DisplayName("바우처 저장 예외 테스트")
    void insert_throwsException() throws IOException {
        // Given
        Voucher voucher = mock(Voucher.class);

        // When
        when(serializer.deserialize()).thenThrow(new IOException());

        // Then
        assertThrows(RuntimeException.class, () -> fileRepository.insert(voucher));
        verify(serializer, times(1)).deserialize();
    }
}
