package com.prgms.vouchermanager.service.voucher;

import com.prgms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.dto.CreateVoucherDto;
import com.prgms.vouchermanager.dto.UpdateVoucherDto;
import com.prgms.vouchermanager.repository.voucher.VoucherRepository;
import com.prgms.vouchermanager.validation.InputValidation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    private VoucherRepository voucherRepository;

    @Mock
    private InputValidation inputValidation;

    @InjectMocks
    VoucherService voucherService;


    @Test
    @DisplayName("service의 create()를 통해 repository의 save()를 정상 실행하고 값을 받아올 수 있다.")
    void createVoucherSuccess() {

        //given
        CreateVoucherDto dto = new CreateVoucherDto(99, 2);
        UUID id = UUID.randomUUID();
        Voucher result = dto.getVoucherType()==1?
                new FixedAmountVoucher(id, dto.getValue()):
                new PercentDiscountVoucher(id,dto.getValue());

        when(voucherRepository.save(any(Voucher.class))).thenReturn(result);
        when(inputValidation.validVoucherPercent(dto.getValue())).thenReturn(true);
        //when
        Voucher voucher = voucherService.create(dto);

        //then
        Assertions.assertThat(voucher).isEqualTo(result);
        verify(voucherRepository,atLeastOnce()).save(any(Voucher.class));

    }
    @Test
    @DisplayName("service의 create()에서  repository의 save()를 실행에 실패한다.")
    void createVoucherFail() {
        //given
        CreateVoucherDto dto = new CreateVoucherDto(101,  2);
        when(inputValidation.validVoucherPercent(dto.getValue())).thenReturn(false);
        //when
        //then
        Assertions.assertThatThrownBy(() -> voucherService.create(dto)).
                isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("service의 update()에서 repository의 update()가 정상적으로 실행된다.")
    void updateUpdateSuccess() {
        //given
        UpdateVoucherDto dto = new UpdateVoucherDto(10, 2);

        when(inputValidation.validVoucherPercent(dto.getValue())).thenReturn(true);

        UUID id = UUID.randomUUID();
        //when
        voucherService.update(id,dto);

        //then
        verify(voucherRepository, atLeastOnce()).update(any(Voucher.class));
    }

    @Test
    @DisplayName("service의 update()를 통해 repository의 save()를 실행에 실패한다.")
    void updateVoucherFail() {
        //given
        UpdateVoucherDto dto = new UpdateVoucherDto(10000, 2);

        when(inputValidation.validVoucherPercent(dto.getValue())).thenReturn(false);

        UUID id = UUID.randomUUID();
        //when
        //then
        Assertions.assertThatThrownBy(() -> voucherService.update((id),dto)).
                isInstanceOf(RuntimeException.class);
    }


    @Test
    @DisplayName("service의 findById()에 존재하는ID라면 ,repository의 findById()를 통해 정상 반환된다.")
    void findByIdVoucherSuccess() {
        //given
        UUID id = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(id, 1000);
        when(voucherRepository.findById(id)).thenReturn(Optional.ofNullable(voucher));

        //when
        Voucher result = voucherService.findById(id);

        //then
        verify(voucherRepository, atLeastOnce()).findById(id);
        Assertions.assertThat(id).isEqualTo(result.getId());

    }

    @Test
    @DisplayName("service의 findById()에 존재하지 않는 ID를 넘겨주면 ,repository의 findById()가 반드시 한번은 실행되고 예외가 발생한다.")
    void findByIdVoucherFail() {
        //given
        UUID id = UUID.randomUUID();
        when(voucherRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Assertions.assertThatThrownBy(() -> voucherService.findById(id)).isInstanceOf(EmptyResultDataAccessException.class);
        verify(voucherRepository, atLeastOnce()).findById(any());

    }

    @Test
    @DisplayName("service의 findAll()을 통해 repository 의 findAll() 실행을 성공한다.")
    void findAllSuccess() {
        //given
        List<Voucher> vouchers = List.of(new FixedAmountVoucher(UUID.randomUUID(), 100),new PercentDiscountVoucher(UUID.randomUUID(),22));
        when(voucherRepository.findAll()).thenReturn(vouchers);

        //when
        List<Voucher> findAllVouchers = voucherService.findAll();

        //then
        Assertions.assertThat(findAllVouchers).isEqualTo(vouchers);
        verify(voucherRepository, atLeastOnce()).findAll();

    }

    @Test
    @DisplayName("service의 deleteById()의 인자와 상관없이, repository의 deleteById()는 반드시 한번은 실행된다.")
    void deleteByIdSuccess() {

        //given
        //when
        voucherService.deleteById(null);

        //then
        verify(voucherRepository, atLeastOnce()).deleteById(any());
    }

    @Test
    @DisplayName("service의 deleteAll()으로 repository 의 deleteAll() 을 반드시 한번은 실행한다.")
    void deleteAll() {
        //given
        //when
        voucherService.deleteAll();

        //then
        verify(voucherRepository, atLeastOnce()).deleteAll();
    }
}
