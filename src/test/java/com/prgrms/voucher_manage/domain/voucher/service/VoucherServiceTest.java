package com.prgrms.voucher_manage.domain.voucher.service;

import com.prgrms.voucher_manage.domain.voucher.controller.dto.CreateVoucherReq;
import com.prgrms.voucher_manage.domain.voucher.controller.dto.UpdateVoucherReq;
import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {
    @Mock
    private VoucherRepository repository;

    @InjectMocks
    private VoucherService service;

    @Test
    @DisplayName("바우처를 생성하여 반환받을 수 있다.")
    void createVoucher(){
        Voucher voucher = new FixedAmountVoucher(1000L);
        CreateVoucherReq createDto = new CreateVoucherReq(FIXED, 1000L);

        when(repository.save(any())).thenReturn(voucher);
        Voucher fixedAmountVoucher = service.createVoucher(createDto);

        assertThat(fixedAmountVoucher).isNotNull();
        assertThat(fixedAmountVoucher.getDiscountAmount()).isEqualTo(createDto.discountAmount());
    }

    @Test
    @DisplayName("바우처를 모두 조회할 수 있다.")
    void getVouchers(){
        Voucher voucher1 = new FixedAmountVoucher(1000L);
        Voucher voucher2 = new PercentDiscountVoucher(10L);
        when(repository.getAll()).thenReturn(List.of(voucher1, voucher2));

        List<Voucher> vouchers = service.getVouchers();
        assertThat(vouchers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("바우처 아이디로 바우처를 반환받을 수 있다.")
    void findVoucherById(){
        Voucher voucher = new FixedAmountVoucher(1000L);
        CreateVoucherReq createDto = new CreateVoucherReq(FIXED, 1000L);

        when(repository.save(any(FixedAmountVoucher.class))).thenReturn(voucher);
        Voucher savedVoucher = service.createVoucher(createDto);

        when(repository.getById(any())).thenReturn(savedVoucher);

        assertThat(service.getVoucherById(savedVoucher.getId()).getId()).isEqualTo(voucher.getId());
    }


    @Test
    @DisplayName("바우처 금액을 변경할 수 있다.")
    void update(){
        Voucher voucher = new FixedAmountVoucher(1000L);
        UpdateVoucherReq updateDto= new UpdateVoucherReq(2000L);

        when(repository.getById(any())).thenReturn(new FixedAmountVoucher(voucher.getId(), updateDto.discountAmount()));
        Voucher updatedVoucher = service.getVoucherById(voucher.getId());

        assertThat(updateDto.discountAmount()).isEqualTo(updatedVoucher.getDiscountAmount());
    }

    @Test
    @DisplayName("바우처를 삭제할 수 있다.")
    void delete(){
        Voucher voucher = new FixedAmountVoucher(1000L);
        service.deleteVoucher(voucher.getId());
        verify(repository).deleteById(voucher.getId());
    }

}
