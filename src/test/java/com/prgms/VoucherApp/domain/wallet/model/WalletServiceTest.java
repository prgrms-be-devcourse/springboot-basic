package com.prgms.VoucherApp.domain.wallet.model;

import com.prgms.VoucherApp.domain.customer.dto.CustomerCreateRequest;
import com.prgms.VoucherApp.domain.customer.dto.CustomerResponse;
import com.prgms.VoucherApp.domain.customer.model.CustomerService;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherCreateRequest;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherResponse;
import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;
import com.prgms.VoucherApp.domain.wallet.dto.WalletCreateRequest;
import com.prgms.VoucherApp.domain.wallet.dto.WalletResponse;
import com.prgms.VoucherApp.domain.wallet.dto.WalletsResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class WalletServiceTest {

    @Autowired
    WalletService walletService;

    @Autowired
    VoucherService voucherService;

    @Autowired
    CustomerService customerService;

    private static final VoucherCreateRequest voucherRequest = new VoucherCreateRequest(VoucherType.FIXED_VOUCHER, BigDecimal.valueOf(5000));
    private static final CustomerCreateRequest customerRequest = new CustomerCreateRequest(CustomerStatus.NORMAL);

    @Test
    @DisplayName("고객이 가진 할인권을 지갑에서 관리하도록 생성한다.")
    void saveWalletTest() {
        // given
        VoucherResponse voucherResponse = voucherService.save(voucherRequest);
        CustomerResponse customerResponse = customerService.save(customerRequest);

        // when
        WalletCreateRequest walletCreateRequest = new WalletCreateRequest(customerResponse.customerId(), voucherResponse.voucherId());
        WalletResponse walletResponse = walletService.save(walletCreateRequest);

        WalletResponse findWallet = walletService.findOne(walletResponse.id());

        // then
        Assertions.assertThat(findWallet).usingRecursiveComparison().isEqualTo(walletResponse);
    }

    @Test
    @DisplayName("고객이 어떤 할인권을 가지고 있는지 조회 할 수 있다.")
    void findAllWalletsByCustomerIdTest() {
        // given
        VoucherResponse voucherResponseA = voucherService.save(voucherRequest);
        CustomerResponse customerResponse = customerService.save(customerRequest);

        VoucherCreateRequest voucherRequestB = new VoucherCreateRequest(VoucherType.PERCENT_VOUCHER, BigDecimal.valueOf(50));
        VoucherResponse voucherResponseB = voucherService.save(voucherRequestB);

        walletService.save(new WalletCreateRequest(customerResponse.customerId(), voucherResponseA.voucherId()));
        walletService.save(new WalletCreateRequest(customerResponse.customerId(), voucherResponseB.voucherId()));

        // when
        WalletsResponse walletsResponse = walletService.findAllByCustomerId(customerResponse.customerId());

        // then
        Assertions.assertThat(walletsResponse.getSize()).isEqualTo(2);
    }

    @Test
    @DisplayName("특정 할인권을 보유하고 있는 고객을 조회 할 수 있다.")
    void findOneWalletByVoucherIdTest() {
        // given
        VoucherResponse voucherResponse = voucherService.save(voucherRequest);
        CustomerResponse customerResponse = customerService.save(customerRequest);

        walletService.save(new WalletCreateRequest(customerResponse.customerId(), voucherResponse.voucherId()));

        // when
        WalletResponse walletResponse = walletService.findByVoucherId(voucherResponse.voucherId());

        // then
        Assertions.assertThat(walletResponse.customerResponse()).usingRecursiveComparison().isEqualTo(customerResponse);
    }
}
