package com.prgrms.kdt.springbootbasic.W2Test.service;

import com.prgrms.kdt.springbootbasic.entity.voucher.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.entity.voucher.PercentDiscountVoucher;
import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
import com.prgrms.kdt.springbootbasic.repository.JdbcVoucherRepository;
import com.prgrms.kdt.springbootbasic.repository.VoucherRepository;
import com.prgrms.kdt.springbootbasic.service.VoucherService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VoucherServiceTest {

    VoucherRepository voucherRepository = mock(JdbcVoucherRepository.class);

    VoucherService voucherService = new VoucherService(voucherRepository);

    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
    @Test
    void createVoucher(){
        var createdVoucher = voucherService.createVoucher(voucher.getVoucherType(),voucher.getVoucherId(),voucher.getDiscountAmount());

        assertThat(createdVoucher).as("Voucher").isEqualToIgnoringGivenFields(voucher,"createdAt");
    }

    @Test
    void checkDuplicationExist(){
        //Given
        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));

        //When
        var duplicationResult = voucherService.checkDuplication(voucher);

        //Then
        assertThat(duplicationResult).isTrue();
    }

    @Test
    void checkDuplicationNotExist(){
        //Given
        var newVoucher = new PercentDiscountVoucher(UUID.randomUUID(),10);
        when(voucherRepository.findById(newVoucher.getVoucherId())).thenReturn(Optional.empty());

        //When
        var duplicationResult = voucherService.checkDuplication(newVoucher);

        //Then
        assertThat(duplicationResult).isFalse();
    }

    @Test
    void saveVoucherDuplicated(){
        //Given
        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));
        when(voucherRepository.saveVoucher(voucher)).thenReturn(Optional.empty());

        //When
        var savedVoucher = voucherService.saveVoucher(voucher);

        //Then
        assertThat(savedVoucher.isEmpty()).isTrue();
    }

    @Test
    void saveVoucherNotDuplicated(){
        //Given
        var newVoucher = new PercentDiscountVoucher(UUID.randomUUID(),10);
        when(voucherRepository.findById(newVoucher.getVoucherId())).thenReturn(Optional.empty());
        when(voucherRepository.saveVoucher(newVoucher)).thenReturn(Optional.of(newVoucher));

        //When
        var savedVoucher = voucherService.saveVoucher(newVoucher);

        //Then
        assertThat(savedVoucher.get()).as("Voucher").isEqualToComparingFieldByFieldRecursively(newVoucher);
    }

    @Test
    void getAllVouchers(){
        //Given
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(new FixedAmountVoucher(UUID.randomUUID(),100));
        voucherList.add(new PercentDiscountVoucher(UUID.randomUUID(),10));
        when(voucherRepository.getAllVouchers()).thenReturn(voucherList);

        //When
        var returnVoucherList = voucherService.getAllVouchers();

        //Then
        assertThat(returnVoucherList)
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(voucherList);
    }
}