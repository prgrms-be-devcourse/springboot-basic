package com.prgrms.voucher_manage.domain.voucher.service;

import com.prgrms.voucher_manage.domain.voucher.dto.CreateVoucherDto;
import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {

    @Mock
    private VoucherRepository repository;

    @InjectMocks
    private VoucherService service;

    @Test
    public void voucherService_createVoucher(){
        FixedAmountVoucher voucher = new FixedAmountVoucher(1000L);
        CreateVoucherDto dto = new CreateVoucherDto(voucher.getType(), voucher.getDiscountAmount());

        when(repository.save(voucher)).thenReturn(voucher);
        Voucher savedVoucher = service.createVoucher(dto);

        assertThat(savedVoucher).isNotNull();
        assertThat(savedVoucher.getDiscountAmount()).isEqualTo(1000L);
    }

    @Test
    public void voucherService_findVoucher(){
        FixedAmountVoucher voucher = new FixedAmountVoucher(1000L);

        when(repository.findById(voucher.getId())).thenReturn(Optional.of(voucher));
        Voucher savedVoucher = service.findVoucher(voucher.getId());

        assertThat(savedVoucher).isNotNull();
        assertThat(savedVoucher.getDiscountAmount()).isEqualTo(1000L);
    }


    @Test
    public void voucherService_delete(){
        UUID testVoucherId = UUID.randomUUID();

        when(repository.deleteById(testVoucherId)).thenReturn(1);
        service.deleteVoucher(testVoucherId);

        verify(repository, times(1)).deleteById(testVoucherId);
    }
}
