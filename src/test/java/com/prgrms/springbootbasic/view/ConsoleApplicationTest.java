package com.prgrms.springbootbasic.view;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prgrms.springbootbasic.ConsoleApplication;
import com.prgrms.springbootbasic.controller.voucher.VoucherController;
import com.prgrms.springbootbasic.domain.voucher.Voucher;
import com.prgrms.springbootbasic.enums.VoucherType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ConsoleApplicationTest {

    @Mock
    private Console console;

    @Mock
    private VoucherController voucherController;

    private ConsoleApplication consoleApplication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consoleApplication = new ConsoleApplication(console, voucherController);
    }

    @Test
    @DisplayName("바우처 생성 테스트")
    void createVoucherTest() {
        // given
        String voucherTypeInput = "FIXED";
        VoucherType voucherType = VoucherType.FIXED;
        long voucherDiscount = 10000;

        Voucher dummyVoucher = mock(Voucher.class);

        when(console.inputVoucherType()).thenReturn(voucherTypeInput);
        when(console.inputVoucherDiscount()).thenReturn(voucherDiscount);
        doNothing().when(voucherController).createVoucher(voucherType, voucherDiscount);

        // when
        consoleApplication.createVoucher();

        // then
        verify(console).printMessage("바우처가 생성되었습니다!");
        verify(voucherController).createVoucher(voucherType, voucherDiscount);
    }


    @Test
    @DisplayName("바우처 목록 조회 테스트")
    void getVoucherListTest() {
        // given
        Map<UUID, Voucher> voucherMap = createDummyVoucherMap();

        when(voucherController.printVoucherList()).thenReturn(voucherMap);

        // when
        consoleApplication.getVoucherList();

        // then
        verify(console).printlnVoucherList(voucherMap);
        verify(voucherController).printVoucherList();
    }

    private Map<UUID, Voucher> createDummyVoucherMap() {
        Map<UUID, Voucher> voucherMap = new HashMap<>();
        voucherMap.put(UUID.randomUUID(), createDummyVoucher(VoucherType.FIXED, 10000));
        return voucherMap;
    }

    private Voucher createDummyVoucher(VoucherType type, long discount) {
        return new Voucher() {
            @Override
            public UUID getVoucherId() {
                return UUID.randomUUID();
            }

            @Override
            public long getDiscount() {
                return discount;
            }

            @Override
            public VoucherType getVoucherType() {
                return type;
            }
        };
    }
}


