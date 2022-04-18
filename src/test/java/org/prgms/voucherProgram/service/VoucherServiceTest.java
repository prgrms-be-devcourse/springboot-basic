package org.prgms.voucherProgram.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.domain.voucher.FixedAmountVoucher;
import org.prgms.voucherProgram.domain.voucher.PercentDiscountVoucher;
import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.dto.VoucherDto;
import org.prgms.voucherProgram.dto.WalletRequestDto;
import org.prgms.voucherProgram.dto.WalletVoucherDto;
import org.prgms.voucherProgram.exception.CustomerIsNotExistsException;
import org.prgms.voucherProgram.exception.VoucherIsNotExistsException;
import org.prgms.voucherProgram.repository.customer.CustomerRepository;
import org.prgms.voucherProgram.repository.voucher.VoucherRepository;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    VoucherService voucherService;

    private static Stream<Arguments> provideVoucher() {
        return Stream.of(
            Arguments.of(new VoucherDto(UUID.randomUUID(), 1, 10L)),
            Arguments.of(new VoucherDto(UUID.randomUUID(), 2, 30L))
        );
    }

    @DisplayName("바우처를 저장한다.")
    @ParameterizedTest
    @MethodSource("provideVoucher")
    void should_ReturnVoucher_When_createVoucher(VoucherDto voucherDto) {
        given(voucherRepository.save(any(Voucher.class))).willReturn(voucherDto.toEntity());

        VoucherDto newVoucher = voucherService.create(voucherDto);

        assertThat(newVoucher).usingRecursiveComparison()
            .isEqualTo(voucherDto);
        then(voucherRepository).should(times(1)).save(any(Voucher.class));
    }

    @DisplayName("수정할 바우처가 존재한다면 바우처를 수정한다.")
    @ParameterizedTest
    @MethodSource("provideVoucher")
    void should_ReturnUpdateVoucher_When_VoucherIsExists(VoucherDto voucherDto) {
        // given
        given(voucherRepository.findById(voucherDto.getVoucherId())).willReturn(Optional.of(voucherDto.toEntity()));
        voucherDto.setType(2);
        voucherDto.setDiscountValue(50);
        given(voucherRepository.update(any(Voucher.class))).willReturn(voucherDto.toEntity());

        // when
        VoucherDto updateVoucher = voucherService.update(voucherDto);

        // then
        assertThat(updateVoucher).usingRecursiveComparison()
            .isEqualTo(voucherDto);
        then(voucherRepository).should(times(1)).findById(any(UUID.class));
        then(voucherRepository).should(times(1)).update(any(Voucher.class));
    }

    @DisplayName("수정할 바우처가 없다면 예외를 발생한다.")
    @ParameterizedTest
    @MethodSource("provideVoucher")
    void should_ThrowException_When_UpdateVoucherIsNotExists(VoucherDto voucherDto) {
        // given
        given(voucherRepository.findById(voucherDto.getVoucherId())).willReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(() -> voucherService.update(voucherDto))
            .isInstanceOf(VoucherIsNotExistsException.class)
            .hasMessage("[ERROR] 해당 아이디로 저장된 바우처가 없습니다.");
        then(voucherRepository).should(times(1)).findById(voucherDto.getVoucherId());
        then(voucherRepository).should(times(0)).update(any(Voucher.class));
    }

    @DisplayName("삭제할 바우처가 존재한다면 바우처를 삭제한다.")
    @ParameterizedTest
    @MethodSource("provideVoucher")
    void should_DeleteVoucher_When_VoucherIsExists(VoucherDto voucherDto) {
        // given
        given(voucherRepository.findById(voucherDto.getVoucherId())).willReturn(Optional.of(voucherDto.toEntity()));
        // when
        voucherService.delete(voucherDto.getVoucherId());
        // then
        then(voucherRepository).should(times(1)).findById(voucherDto.getVoucherId());
        then(voucherRepository).should(times(1)).deleteById(voucherDto.getVoucherId());
    }

    @DisplayName("삭제할 바우처가 없다면 예외를 발생한다.")
    @ParameterizedTest
    @MethodSource("provideVoucher")
    void should_ThrowException_When_DeleteVoucherIsNotExists(VoucherDto voucherDto) {
        // given
        given(voucherRepository.findById(voucherDto.getVoucherId())).willReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(() -> voucherService.delete(voucherDto.getVoucherId()))
            .isInstanceOf(VoucherIsNotExistsException.class)
            .hasMessage("[ERROR] 해당 아이디로 저장된 바우처가 없습니다.");
        then(voucherRepository).should(times(1)).findById(voucherDto.getVoucherId());
        then(voucherRepository).should(times(0)).deleteById(voucherDto.getVoucherId());
    }

    @DisplayName("모든 바우처를 반환한다.")
    @Test
    void findAllVoucher_ReturnAllVoucher() {
        Voucher voucherOne = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        Voucher voucherTwo = new PercentDiscountVoucher(UUID.randomUUID(), UUID.randomUUID(), 20L);
        List<Voucher> mockVouchers = List.of(voucherOne, voucherTwo);
        given(voucherRepository.findAll()).willReturn(mockVouchers);

        List<VoucherDto> vouchers = voucherService.findAllVoucher();

        assertThat(vouchers).hasSize(2)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields()
            .contains(VoucherDto.from(voucherOne), VoucherDto.from(voucherTwo));
        then(voucherRepository).should(times(1)).findAll();
    }

    @DisplayName("고객에게 바우처를 할당한다.")
    @Test
    void should_ReturnVoucher_When_AssignVoucher() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        Customer customer = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        WalletRequestDto walletRequestDto = new WalletRequestDto(customer.getEmail(), voucher.getVoucherId());
        given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));
        given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.of(voucher));
        given(voucherRepository.assignCustomer(any(Voucher.class))).willReturn(
            new FixedAmountVoucher(voucher.getVoucherId(), customer.getCustomerId(), voucher.getDiscountValue()));

        // when
        WalletVoucherDto walletVoucherDto = voucherService.assignVoucher(walletRequestDto);

        // then
        assertThat(walletVoucherDto).extracting("customerId")
            .isEqualTo(customer.getCustomerId());
        then(customerRepository).should(times(1)).findByEmail(any(String.class));
        then(voucherRepository).should(times(1)).findById(any(UUID.class));
        then(voucherRepository).should(times(1)).assignCustomer(any(Voucher.class));
    }

    @DisplayName("바우처 할당 시 고객이 존재하지 않는다면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_CustomerIsNotExists() {
        // given
        WalletRequestDto walletRequestDto = new WalletRequestDto("hwan@gmail.com", UUID.randomUUID());
        given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> voucherService.assignVoucher(walletRequestDto))
            .isInstanceOf(CustomerIsNotExistsException.class)
            .hasMessage("[ERROR] 해당 이메일로 저장된 고객이 없습니다.");
    }

    @DisplayName("바우처 할당 시 바우처가 존재하지 않는다면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_VoucherIsNotExists() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        WalletRequestDto walletRequestDto = new WalletRequestDto("hwan@gmail.com", UUID.randomUUID());
        given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));
        given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(() -> voucherService.assignVoucher(walletRequestDto))
            .isInstanceOf(VoucherIsNotExistsException.class)
            .hasMessage("[ERROR] 해당 아이디로 저장된 바우처가 없습니다.");
    }
}
