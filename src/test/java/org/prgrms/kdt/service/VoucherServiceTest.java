package org.prgrms.kdt.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherBuilder;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.prgrms.kdt.voucher.VoucherService;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {
    private final static String FIXED = "FixedAmountVoucher";
    @Mock
    private VoucherRepository voucherRepository;
    @Mock(answer = Answers.RETURNS_SELF)
    private VoucherBuilder voucherBuilder;
    @InjectMocks
    private VoucherService voucherService;

    @Test
    @DisplayName("바우처를 생성할 수 있다.")
    void 바우처_생성하기() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), "1000", FIXED, LocalDateTime.now());
        when(voucherBuilder.create().setDiscountAmount("1000").setVoucherType(FIXED).build()).thenReturn(voucher);
        doNothing().when(voucherRepository).insert(voucher);

        Voucher createVoucher = voucherService.create("1", "1000");

        verify(voucherBuilder.create().setDiscountAmount("1000").setVoucherType(FIXED)).build();
        verify(voucherRepository).insert(voucher);
        assertThat(voucher, samePropertyValuesAs(createVoucher));
    }

    @Test
    @DisplayName("모든 바우처를 조회할 수 있다.")
    void 모든_바우처_조회하기() {
        List<Voucher> voucherList = new ArrayList<>();

        when(voucherRepository.getAllStoredVoucher()).thenReturn(voucherList);
        voucherService.getAllVouchers();

        verify(voucherRepository).getAllStoredVoucher();
    }

    @Test
    @DisplayName("바우처 Id를 이용하여 바우처를 조회할 수 있다.")
    void 바우처_ID_조회하기() {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, "1000", FIXED, LocalDateTime.now());

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));
        Voucher voucherById = voucherService.findVoucherById(voucherId.toString());

        verify(voucherRepository).findById(voucherId);
        assertThat(voucher, samePropertyValuesAs(voucherById));
    }

    @Test
    @DisplayName("특정 손님 ID를 이용하여 해당 손님에게 바우처 할당하기")
    void 바우처_할당하기() {
        Customer customer = new Customer(UUID.randomUUID(), "test01", "test01@gmail.com", LocalDateTime.now(), false);
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), "1000", FIXED, LocalDateTime.now());

        when(voucherRepository.update(voucher)).thenReturn(voucher);
        Voucher assignVoucher = voucherService.assignVoucher(voucher, customer);

        verify(voucherRepository).update(voucher);
        assertThat(assignVoucher.getOwnedCustomerId().get(), is(customer.getCustomerId()));
    }

    @Test
    @DisplayName("특정 손님 ID를 이용하여 해당 손님이 보유한 바우처 조회하기")
    void 보유한_바우처_조회하기() {
        UUID customerId = UUID.randomUUID();
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(new FixedAmountVoucher(UUID.randomUUID(), "1000", FIXED, customerId, LocalDateTime.now()));
        voucherList.add(new FixedAmountVoucher(UUID.randomUUID(), "2000", FIXED, LocalDateTime.now()));
        voucherList.add(new FixedAmountVoucher(UUID.randomUUID(), "3000", FIXED, customerId, LocalDateTime.now()));
        voucherList.add(new FixedAmountVoucher(UUID.randomUUID(), "4000", FIXED, LocalDateTime.now()));

        when(voucherRepository.getAllStoredVoucher()).thenReturn(voucherList);
        List<Voucher> ownedVouchers = voucherService.getOwnedVouchers(customerId.toString());

        verify(voucherRepository).getAllStoredVoucher();
        Assertions.assertThat(ownedVouchers).filteredOn(voucher -> Objects.equals(voucher.getOwnedCustomerId().get(), customerId));
    }

    @Test
    @DisplayName("특정 바우처 ID의 할당된 고객을 제거할 수 있다.")
    void 바우처_할당해제() {
        UUID voucherId = UUID.randomUUID();
        Customer customer = new Customer(UUID.randomUUID(), "test01", "test01@gmail.com", LocalDateTime.now(), false);
        Voucher voucher = new FixedAmountVoucher(voucherId, "1000", FIXED, customer.getCustomerId(), LocalDateTime.now());

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));
        voucher.setOwnedCustomerId(null);
        when(voucherRepository.update(voucher)).thenReturn(voucher);
        Voucher removeVoucher = voucherService.removeAssignment(voucherId.toString());

        verify(voucherRepository).findById(voucherId);
        assertThat(removeVoucher.getOwnedCustomerId().isEmpty(), is(true));
    }
}