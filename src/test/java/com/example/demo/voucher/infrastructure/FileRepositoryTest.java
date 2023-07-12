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
    private VoucherInfo voucherInfo;

    @Mock
    private VoucherFileWriter writer;

    private FileRepository fileRepository;

    @BeforeEach
    void setUp() {
        fileRepository = new FileRepository(voucherInfo, writer);
    }

    @Test
    @DisplayName("id로 바우처 검색 테스트")
    void findById() {
        // Given
        UUID uuid = UUID.randomUUID();
        Voucher voucher = mock(Voucher.class);

        // When
        when(voucher.getVoucherId()).thenReturn(uuid);
        when(voucherInfo.getVoucherList()).thenReturn(Arrays.asList(voucher));

        Optional<Voucher> result = fileRepository.findById(uuid);

        // Then
        assertEquals(voucher, result.get());
        verify(voucherInfo, times(1)).getVoucherList();
    }

    @Test
    @DisplayName("id로 바우처 검색 실패 테스트: not_found")
    void findById_notFound() {
        // Given
        UUID uuid = UUID.randomUUID();

        // When
        when(voucherInfo.getVoucherList()).thenReturn(Arrays.asList());

        Optional<Voucher> result = fileRepository.findById(uuid);

        // Then
        assertEquals(Optional.empty(), result);
        verify(voucherInfo, times(1)).getVoucherList();
    }

    @Test
    @DisplayName("모든 바우처 검색 테스트")
    void findAll() {
        // Given
        List<Voucher> vouchers = Arrays.asList(mock(Voucher.class));

        // When
        when(voucherInfo.getVoucherList()).thenReturn(vouchers);

        List<Voucher> result = fileRepository.findAll();

        // Then
        assertEquals(vouchers, result);
        verify(voucherInfo, times(1)).getVoucherList();
    }

    @Test
    @DisplayName("바우처 저장 테스트")
    void insert() throws IOException {
        // Given
        Voucher voucher = mock(Voucher.class);

        // When
        fileRepository.insert(voucher);

        // Then
        verify(voucherInfo, times(1)).add(voucher);
    }

    @Test
    @DisplayName("바우처 저장 예외 테스트")
    void insert_throwsException() throws IOException {
        // Given
        Voucher voucher = mock(Voucher.class);

        // When
        doThrow(new IOException()).when(writer).write(any(Voucher.class), any(String.class));


        // Then
        assertThrows(RuntimeException.class, () -> fileRepository.insert(voucher));
        verify(writer, times(1)).write(any(), any());
    }
}
