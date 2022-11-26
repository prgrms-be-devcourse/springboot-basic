package com.programmers.commandline.domain.voucher.service;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherServiceUnitTest {

    private VoucherRepository voucherRepositoryMock = mock(VoucherRepository.class);
    private VoucherService voucherService = new VoucherService(voucherRepositoryMock);
    private UUID id;
    private String type;
    private long discount;
    private Voucher voucher;
    private LocalDateTime createdAt;

    @BeforeAll
    void setup() {
        this.id = UUID.randomUUID();
        this.type = VoucherType.FIXED_AMOUNT.toString();
        this.discount = 100L;
        this.createdAt = LocalDateTime.now();
        this.voucher = VoucherType.valueOf(type).createVoucher(id, discount, createdAt);
    }

    @Test
    @DisplayName("바우처를 1개 생성하는 service객체에서 voucherRepository가 1회 실행되는지 검증하라")
    void insert() {
        //given
        //when
        when(voucherRepositoryMock.insert(any())).thenReturn(voucher);
        String insert = voucherService.insert(String.valueOf(type), discount);

        //then
        verify(voucherRepositoryMock, times(1)).insert(any());
    }

    @Test
    void findAll() {
        //given
        List<Voucher> vouchers = new ArrayList<>();

        vouchers.add(voucher);
        //when
        when(voucherRepositoryMock.findAll()).thenReturn(vouchers);
        List<Voucher> findAllVouchers = voucherService.findAll();

        //then
        verify(voucherRepositoryMock, times(1)).findAll();
        assertThat(findAllVouchers, is(voucher.toString()));
    }
}