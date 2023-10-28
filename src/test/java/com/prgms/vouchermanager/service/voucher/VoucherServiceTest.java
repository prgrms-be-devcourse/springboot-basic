package com.prgms.vouchermanager.service.voucher;

import com.prgms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.dto.CreateVoucherDto;
import com.prgms.vouchermanager.dto.UpdateVoucherDto;
import com.prgms.vouchermanager.repository.voucher.VoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @InjectMocks
    VoucherService voucherService;
    @Mock
    private VoucherRepository voucherRepository;

    @Test
    @DisplayName("service의 create()를 통해 repository의 save()를 정상 실행하고 값을 받아올 수 있다.")
    void createVoucherSuccess() {

        //given
        CreateVoucherDto dto = new CreateVoucherDto(99, 2);
        UUID id = UUID.randomUUID();
        Voucher result = dto.getVoucherType() == 1 ?
                new FixedAmountVoucher(id, dto.getValue()) :
                new PercentDiscountVoucher(id, dto.getValue());

        when(voucherRepository.save(any(Voucher.class))).thenReturn(result);
        //when
        Voucher voucher = voucherService.create(dto);

        //then
        Assertions.assertThat(voucher).isEqualTo(result);

    }

    @Test
    @DisplayName("service의 update()에서 repository의 update()가 정상적으로 실행된다.")
    void updateUpdateSuccess() {
        //given
        UpdateVoucherDto dto = new UpdateVoucherDto(10, 2);
        UUID id = UUID.randomUUID();

        //when
        voucherService.update(id, dto);

        //then
        verify(voucherRepository, atLeastOnce()).update(any(Voucher.class));
    }

    @Test
    @DisplayName("service의 update()에서 바우처타입 검증 실패시 RuntimeException예외가 발생한다.")
    void updateVoucherFail() {
        //given
        UpdateVoucherDto dto = new UpdateVoucherDto(10000, 142124);
        UUID id = UUID.randomUUID();

        //when
        //then
        Assertions.assertThatThrownBy(() -> voucherService.update((id), dto)).
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
        Assertions.assertThat(id).isEqualTo(result.getId());

    }

    @Test
    @DisplayName("service의 findById()에 존재하지 않는 ID를 넘겨주면 , EmptyResultDataAccessException예외가 발생한다.")
    void findByIdVoucherFail() {
        //given
        UUID id = UUID.randomUUID();
        when(voucherRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Assertions.assertThatThrownBy(() -> voucherService.findById(id)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("service의 findAll()을 성공적으로 실행해서 필요 데이터를 가져올 수 있다.")
    void findAllSuccess() {
        //given
        List<Voucher> vouchers = List.of(new FixedAmountVoucher(UUID.randomUUID(), 100), new PercentDiscountVoucher(UUID.randomUUID(), 22));
        when(voucherRepository.findAll()).thenReturn(vouchers);

        //when
        List<Voucher> findAllVouchers = voucherService.findAll();

        //then
        Assertions.assertThat(findAllVouchers).isEqualTo(vouchers);
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
    @DisplayName("service의 deleteAll()으로 repository 의 deleteAll()을 실행한다.")
    void deleteAll() {
        //given
        //when
        voucherService.deleteAll();

        //then
        verify(voucherRepository, atLeastOnce()).deleteAll();
    }
}
