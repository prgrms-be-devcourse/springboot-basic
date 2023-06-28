package me.kimihiqq.vouchermanagement.service;

import me.kimihiqq.vouchermanagement.domain.voucher.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.domain.voucher.dto.VoucherDto;
import me.kimihiqq.vouchermanagement.domain.voucher.service.VoucherServiceImpl;
import me.kimihiqq.vouchermanagement.domain.voucher.repository.VoucherRepository;
import me.kimihiqq.vouchermanagement.option.VoucherTypeOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class VoucherServiceImplTest {

    @Mock
    private VoucherRepository voucherRepository;

    @InjectMocks
    private VoucherServiceImpl voucherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateVoucher() {
        VoucherDto voucherDto = new VoucherDto("FIXED", 10);
        when(voucherRepository.save(any(Voucher.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Voucher result = voucherService.createVoucher(voucherDto);

        assertEquals(VoucherTypeOption.FIXED.name(), result.getType());
        assertEquals(10, result.getDiscount());
    }

    @Test
    void testListVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        vouchers.add(new FixedAmountVoucher(UUID.randomUUID(), 10));
        vouchers.add(new PercentDiscountVoucher(UUID.randomUUID(), 10));

        when(voucherRepository.findAll()).thenReturn(vouchers);

        List<Voucher> result = voucherService.listVouchers();

        assertEquals(2, result.size());
    }
}