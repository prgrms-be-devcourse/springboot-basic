package com.prgrms.kdt.springbootbasic.W2Test.service;

import com.prgrms.kdt.springbootbasic.voucher.entity.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.voucher.entity.PercentDiscountVoucher;
import com.prgrms.kdt.springbootbasic.voucher.entity.Voucher;
import com.prgrms.kdt.springbootbasic.exception.JdbcQueryFail;
import com.prgrms.kdt.springbootbasic.exception.NoSuchResource;
import com.prgrms.kdt.springbootbasic.exception.ResourceDuplication;
import com.prgrms.kdt.springbootbasic.voucher.repository.VoucherRepository;
import com.prgrms.kdt.springbootbasic.voucher.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @InjectMocks
    VoucherService voucherService;

    @Mock
    VoucherRepository voucherRepository;

    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);

    @Test
    void checkDuplicationExist(){
        //Given
        when(voucherRepository.findByTypeAndAmount(voucher.getVoucherType(),voucher.getDiscountAmount())).thenReturn(Optional.of(voucher));

        //When
        var duplicationResult = voucherService.checkDuplication(voucher);

        //Then
        assertThat(duplicationResult).isTrue();
    }

    @Test
    void checkDuplicationNotExist(){
        //Given
        var newVoucher = new PercentDiscountVoucher(UUID.randomUUID(),10);
        when(voucherRepository.findByTypeAndAmount(newVoucher.getVoucherType(), newVoucher.getDiscountAmount())).thenReturn(Optional.empty());

        //When
        var duplicationResult = voucherService.checkDuplication(newVoucher);

        //Then
        assertThat(duplicationResult).isFalse();
    }

    @Test
    void saveVoucherDuplicated(){
        //Given
        when(voucherRepository.findByTypeAndAmount(voucher.getVoucherType(),voucher.getDiscountAmount())).thenReturn(Optional.of(voucher));

        //Then
        assertThatThrownBy(() -> {
            voucherService.saveVoucher(voucher.getVoucherType(),voucher.getDiscountAmount());
        }).isInstanceOf(ResourceDuplication.class);
    }

    @Test
    void saveVoucherNotDuplicated(){
        //Given
        var newVoucher = new PercentDiscountVoucher(UUID.randomUUID(),10);
        when(voucherRepository.findByTypeAndAmount(newVoucher.getVoucherType(), newVoucher.getDiscountAmount())).thenReturn(Optional.empty());
        when(voucherRepository.saveVoucher(any())).thenReturn(Optional.of(newVoucher));

        //When
        var savedVoucher = voucherService.saveVoucher(newVoucher.getVoucherType(), newVoucher.getDiscountAmount());

        //Then
        assertThat(savedVoucher).as("Voucher").isEqualToComparingFieldByFieldRecursively(newVoucher);
    }

    @Test
    void saveVoucherWithJdbcFail(){
        //Given
        var newVoucher = new PercentDiscountVoucher(UUID.randomUUID(),10);
        when(voucherRepository.findByTypeAndAmount(newVoucher.getVoucherType(), newVoucher.getDiscountAmount())).thenReturn(Optional.empty());
        when(voucherRepository.saveVoucher(any())).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> {
            voucherService.saveVoucher(newVoucher.getVoucherType(), newVoucher.getDiscountAmount());
        }).isInstanceOf(JdbcQueryFail.class);
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

    @Test
    void updateVoucherExist(){
        //Given
        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));
        Voucher updatedVoucher = new FixedAmountVoucher(voucher.getVoucherId(), 40);
        when(voucherRepository.updateVoucherAmount(updatedVoucher)).thenReturn(Optional.of(updatedVoucher));

        //When
        var updateResult = voucherService.updateVoucher(updatedVoucher);

        //Then
        assertThat(updateResult).as("Voucher").isEqualToComparingFieldByFieldRecursively(updatedVoucher);
    }

    @Test
    void updateVoucherNotExist(){
        //Given
        Voucher newVoucher = new PercentDiscountVoucher(UUID.randomUUID(),20);
        when(voucherRepository.findById(newVoucher.getVoucherId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            voucherService.updateVoucher(newVoucher);
        }).isInstanceOf(NoSuchResource.class);

    }

    @Test
    @DisplayName("수정할 내용이 없어서 updateVoucher이 스킵됨")
    void updateVoucherSkipped(){
        //Given
        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));


        //When
        var updateResult = voucherService.updateVoucher(voucher);

        //Then
        verify(voucherRepository, never()).updateVoucherAmount(voucher);
        assertThat(updateResult).as("Voucher").isEqualToComparingFieldByFieldRecursively(voucher);
    }

    @Test
    @DisplayName("Jdbc 저장시에 오류 발생함")
    void updateVoucherWithJdbcQueryFail(){
        //Given
        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));
        Voucher updatedVoucher = new FixedAmountVoucher(voucher.getVoucherId(), 40);
        when(voucherRepository.updateVoucherAmount(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            voucherService.updateVoucher(updatedVoucher);
        }).isInstanceOf(JdbcQueryFail.class);

    }

    @Test
    void deleteVoucherExist(){
        //Given
        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));
        when(voucherRepository.deleteVoucher(voucher.getVoucherId())).thenReturn(true);

        //When
        var deletedResult = voucherService.deleteVoucher(voucher.getVoucherId());

        //Then
        assertThat(deletedResult).isTrue();
    }

    @Test
    void deleteVoucherNotExist(){
        //Given
        Voucher newVoucher = new PercentDiscountVoucher(UUID.randomUUID(),20);
        when(voucherRepository.findById(newVoucher.getVoucherId())).thenReturn(Optional.empty());

        //When
        var deletedResult = voucherService.deleteVoucher(newVoucher.getVoucherId());

        //Then
        assertThat(deletedResult).isTrue();
    }
}