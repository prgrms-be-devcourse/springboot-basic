package com.prgms.VoucherApp.domain.wallet.model;

import com.prgms.VoucherApp.domain.customer.dto.CustomerResponse;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherResponse;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;
import com.prgms.VoucherApp.domain.wallet.dto.WalletCreateRequest;
import com.prgms.VoucherApp.domain.wallet.dto.WalletResponse;
import com.prgms.VoucherApp.domain.wallet.dto.WalletsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class WalletServiceMockTest {

    private static WalletResponse response = new WalletResponse(
            UUID.randomUUID(),
            new CustomerResponse(UUID.randomUUID(), CustomerStatus.NORMAL),
            new VoucherResponse(UUID.randomUUID(), BigDecimal.valueOf(1000), VoucherType.FIXED_VOUCHER));

    @MockBean
    private WalletService mockWalletService;

    @Test
    @DisplayName("고객이 가진 할인권을 지갑에서 관리하도록 생성한다.")
    void saveWalletTest() {
        given(mockWalletService.save(any(WalletCreateRequest.class)))
                .willReturn(response);
    }

    @Test
    @DisplayName("고객이 어떤 할인권을 가지고 있는지 조회 할 수 있다.")
    void findAllWalletsByCustomerIdTest() {
        given(mockWalletService.findAllByCustomerId(any(UUID.class)))
                .willReturn(new WalletsResponse(List.of(response)));
    }

    @Test
    @DisplayName("특정 할인권을 보유하고 있는 고객을 조회 할 수 있다.")
    void findOneWalletByVoucherIdTest() {
        given(mockWalletService.findByVoucherId(any(UUID.class)))
                .willReturn(response);
    }
}