package org.prgms.voucherProgram.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
import org.prgms.voucherProgram.dto.CustomerDto;
import org.prgms.voucherProgram.dto.VoucherDto;
import org.prgms.voucherProgram.dto.WalletRequestDto;
import org.prgms.voucherProgram.dto.WalletVoucherDto;
import org.prgms.voucherProgram.exception.AlreadyAssignException;
import org.prgms.voucherProgram.exception.CustomerIsNotExistsException;
import org.prgms.voucherProgram.exception.NotFoundVoucherException;
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

    @DisplayName("바우처 할당 시 바우처가 존재하지 않는다면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_VoucherIsNotExists() {
        // given
        WalletRequestDto walletRequestDto = new WalletRequestDto("hwan@gmail.com", UUID.randomUUID());
        given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(() -> voucherService.assignVoucher(walletRequestDto))
            .isInstanceOf(VoucherIsNotExistsException.class)
            .hasMessage("[ERROR] 해당 아이디로 저장된 바우처가 없습니다.");
    }

    @DisplayName("바우처 할당 시 바우처가 이미 할당되었다면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_VoucherIsAssign() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), UUID.randomUUID(), 10L);
        WalletRequestDto walletRequestDto = new WalletRequestDto("hwan@gmail.com", voucher.getVoucherId());
        given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.of(voucher));
        // when
        // then
        assertThatThrownBy(() -> voucherService.assignVoucher(walletRequestDto))
            .isInstanceOf(AlreadyAssignException.class)
            .hasMessage("[ERROR] 해당 바우처는 이미 할당되었습니다.");
    }

    @DisplayName("바우처 할당 시 고객이 존재하지 않는다면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_CustomerIsNotExists() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        WalletRequestDto walletRequestDto = new WalletRequestDto("hwan@gmail.com", voucher.getVoucherId());
        given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.of(voucher));
        given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> voucherService.assignVoucher(walletRequestDto))
            .isInstanceOf(CustomerIsNotExistsException.class)
            .hasMessage("[ERROR] 해당 이메일로 저장된 고객이 없습니다.");
    }

    @DisplayName("고객에게 할당된 바우처를 조회한다.")
    @Test
    void should_ReturnAssignVouchers() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "hwan", "hwan@gmail.com", LocalDateTime.now());
        given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));
        given(voucherRepository.findByCustomerId(any(UUID.class))).willReturn(vouchers(customerId));

        // when
        List<WalletVoucherDto> vouchers = voucherService.findAssignVouchers(customer.getEmail());

        // then
        assertThat(vouchers).hasSize(5);
        then(customerRepository).should(times(1)).findByEmail(any(String.class));
        then(voucherRepository).should(times(1)).findByCustomerId(any(UUID.class));
    }

    @DisplayName("할당된 바우처 조회 시 존재하지 않는 고객이라면 예외를 발생한다.")
    @Test
    void should_ThrowException_WhenCustomerIsNotExists() {
        // given
        String email = "hwan@gmail.com";
        given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> voucherService.findAssignVouchers(email))
            .isInstanceOf(CustomerIsNotExistsException.class)
            .hasMessage("[ERROR] 해당 이메일로 저장된 고객이 없습니다.");
    }

    @DisplayName("고객이 보유한 바우처를 삭제한다.")
    @Test
    void should_DeleteAssign() {
        // given
        UUID customerId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), customerId, 10L);
        Customer customer = new Customer(customerId, "hwan", "hwan@gmail.com", LocalDateTime.now());
        WalletRequestDto walletRequestDto = new WalletRequestDto(customer.getEmail(), voucher.getVoucherId());
        given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));
        given(voucherRepository.findByCustomerId(any(UUID.class))).willReturn(List.of(voucher));

        // when
        voucherService.deleteAssignVoucher(walletRequestDto);

        // then
        then(customerRepository).should(times(1)).findByEmail(any(String.class));
        then(voucherRepository).should(times(1)).findByCustomerId(any(UUID.class));
        then(voucherRepository).should(times(1)).deleteById(any(UUID.class));
    }

    @DisplayName("바우처 삭제 시 고객이 존재하지 않는다면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_DeleteCustomerIsNotExists() {
        // given
        WalletRequestDto walletRequestDto = new WalletRequestDto("hwan@gmail.com", UUID.randomUUID());
        given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> voucherService.deleteAssignVoucher(walletRequestDto))
            .isInstanceOf(CustomerIsNotExistsException.class)
            .hasMessage("[ERROR] 해당 이메일로 저장된 고객이 없습니다.");
    }

    @DisplayName("바우처 삭제 시 바우처가 존재하지 않는다면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_DeleteVoucherIsNotExists() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        WalletRequestDto walletRequestDto = new WalletRequestDto("hwan@gmail.com", UUID.randomUUID());
        given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));
        given(voucherRepository.findByCustomerId(any(UUID.class))).willReturn(Collections.emptyList());
        // when
        // then
        assertThatThrownBy(() -> voucherService.deleteAssignVoucher(walletRequestDto))
            .isInstanceOf(NotFoundVoucherException.class)
            .hasMessage("[ERROR] 고객이 가진 바우처들에서 해당 아이디를 가진 바우처를 찾을 수 없습니다.");
    }

    @DisplayName("특정 바우처를 가진 고객을 조회한다.")
    @Test
    void should_ReturnCustomer_When_AssignVoucher() {
        // given
        UUID customerId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), customerId, 10L);
        Customer customer = new Customer(customerId, "hwan", "hwan@gmail.com", LocalDateTime.now());
        given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.of(voucher));
        given(customerRepository.findById(any(UUID.class))).willReturn(Optional.of(customer));

        // when
        CustomerDto customerDto = voucherService.findCustomer(voucher.getVoucherId());

        // then
        assertThat(customerDto).extracting("customerId").isEqualTo(customerId);
        then(voucherRepository).should(times(1)).findById(any(UUID.class));
        then(customerRepository).should(times(1)).findById(any(UUID.class));
    }

    @DisplayName("바우처가 존재하지 않으면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_FoundVoucherIsNotExists() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> voucherService.findCustomer(voucher.getVoucherId()))
            .isInstanceOf(VoucherIsNotExistsException.class)
            .hasMessage("[ERROR] 해당 아이디로 저장된 바우처가 없습니다.");
    }

    @DisplayName("바우처가 할당 전이라면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_NotAssignVoucher() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.of(voucher));

        // when
        // then
        assertThatThrownBy(() -> voucherService.findCustomer(voucher.getVoucherId()))
            .isInstanceOf(CustomerIsNotExistsException.class)
            .hasMessage("[ERROR] 해당 바우처는 아직 할당전 입니다.");
    }

    private List<Voucher> vouchers(UUID customerId) {
        List<Voucher> vouchers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            vouchers.add(new FixedAmountVoucher(UUID.randomUUID(), customerId, 10L));
        }
        return vouchers;
    }
}
