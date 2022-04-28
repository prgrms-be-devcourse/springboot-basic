package com.mountain.voucherApp.application.service;

import com.mountain.voucherApp.adapter.out.persistence.voucher.VoucherEntity;
import com.mountain.voucherApp.application.port.in.CustomerDto;
import com.mountain.voucherApp.application.port.in.VoucherCreateDto;
import com.mountain.voucherApp.application.port.in.VoucherIdUpdateDto;
import com.mountain.voucherApp.application.port.out.CustomerPort;
import com.mountain.voucherApp.application.port.out.VoucherPort;
import com.mountain.voucherApp.domain.vo.CustomerName;
import com.mountain.voucherApp.shared.enums.DiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VoucherAppServiceTest {

    @InjectMocks
    private VoucherAppService voucherAppService;

    @Mock
    private CustomerPort customerPort;
    @Mock
    private VoucherPort voucherPort;

    @Test
    @DisplayName("바우처 리스트를 조회할 수 있다.")
    public void testShowVoucherList() {
        List<VoucherEntity> givenVouchers = List.of(
                new VoucherEntity(UUID.randomUUID(), DiscountPolicy.FIXED, 1L),
                new VoucherEntity(UUID.randomUUID(), DiscountPolicy.FIXED, 2L)
        );
        // mocking
        given(voucherPort.findAll()).willReturn(givenVouchers);
        // when
        List<VoucherEntity> vouchers = voucherAppService.showVoucherList();
        assertAll(
                () -> assertThat(vouchers.size(), is(2)),
                () -> assertThat(vouchers, samePropertyValuesAs(givenVouchers))
        );
    }

    @Test
    @DisplayName("고객 리스트를 조회할 수 있다.")
    public void testShowCustomerVoucherInfo() {
        List<CustomerDto> givenCustomers = List.of(
                new CustomerDto(UUID.randomUUID(), UUID.randomUUID(), new CustomerName("customer1"), "customer1@gmail.com", LocalDateTime.now(), LocalDateTime.now()),
                new CustomerDto(UUID.randomUUID(), UUID.randomUUID(), new CustomerName("customer2"), "customer2@gmail.com", LocalDateTime.now(), LocalDateTime.now())
        );
        // mocking
        given(customerPort.findAll()).willReturn(givenCustomers);
        // when
        List<CustomerDto> customers = voucherAppService.showCustomerVoucherInfo();
        assertAll(
                () -> assertThat(customers.size(), is(2)),
                () -> assertThat(customers, samePropertyValuesAs(givenCustomers))
        );
    }

    @Test
    @DisplayName("Customer의 VoucherId값을 갱신할 수 있다.")
    public void testAddVoucher() {
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        VoucherIdUpdateDto requestDto = new VoucherIdUpdateDto(customerId, voucherId);

        // mocking이 필요가 없다.
        // willDoNothing().given(customerPort).updateVoucherId(customerId, voucherId);
        voucherAppService.addVoucher(requestDto);
        assertAll(
                // 실행되었는지 ?
                () -> verify(customerPort).updateVoucherId(customerId, voucherId),
                () -> verify(customerPort, times(1)).updateVoucherId(isA(UUID.class), isA(UUID.class)),
                // 호출되지 않은 인자도 확인해보기.
                () -> verify(customerPort, times(0)).updateVoucherId(UUID.randomUUID(), voucherId)
        );
    }

    @Test
    @DisplayName("customer에 등록된 voucher정보를 null로 update 할 수 있다.")
    public void testRemoveVoucher() {
        UUID customerId = UUID.randomUUID();
        UUID voucherId = null;

        voucherAppService.removeVoucher(customerId);
        assertAll(
                () -> verify(customerPort).updateVoucherId(customerId, voucherId),
                () -> verify(customerPort, times(1)).updateVoucherId(isA(UUID.class), isNull()),
                () -> verify(customerPort, times(0)).updateVoucherId(UUID.randomUUID(), voucherId)
        );
    }

    @Test
    @DisplayName("policyId와 discoumtAmount 조건으로 등록된 데이터가 없는 경우 새로운 바우처를 생성.")
    public void testCreateSuccess() {
        VoucherCreateDto voucherCreateDto = new VoucherCreateDto(DiscountPolicy.FIXED, 1000L);
        given(voucherPort.findByDiscountPolicyAndAmount(
                voucherCreateDto.getDiscountPolicy(),
                voucherCreateDto.getDiscountAmount()
        )).willReturn(Optional.empty());

        voucherAppService.create(voucherCreateDto);
        assertAll(
                () -> verify(voucherPort).insert(isA(VoucherEntity.class))
        );
    }

    @Test
    @DisplayName("바우처가 존재하면 생성하지 않는다.")
    public void testCreateExistVoucher() {
        VoucherCreateDto voucherCreateDto = new VoucherCreateDto(DiscountPolicy.FIXED, 1000L);
        given(voucherPort.findByDiscountPolicyAndAmount(
                voucherCreateDto.getDiscountPolicy(),
                voucherCreateDto.getDiscountAmount()
        )).willReturn(
                Optional.of(new VoucherEntity(UUID.randomUUID(), DiscountPolicy.FIXED, 1000L))
        );
        voucherAppService.create(voucherCreateDto);
        assertAll(
                () -> verify(voucherPort, times(0)).insert(isA(VoucherEntity.class))
        );
    }

}