package org.prgrms.vouchermanagement.wallet.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.dto.WalletCreateInfo;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.policy.FixedAmountVoucher;
import org.prgrms.vouchermanagement.wallet.repository.WalletMapperRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class WalletMapperServiceTest {


    @InjectMocks
    private WalletMapperService walletMapperService;

    @Mock
    private WalletMapperRepository walletMapperRepository;

    @Test
    @DisplayName("wallet 생성 테스트")
    void create() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "gyu", 23);
        UUID customerId = customer.getUserId();

        Voucher voucher = new Voucher(UUID.randomUUID(), new FixedAmountVoucher(3000));
        UUID voucherId = voucher.getVoucherId();

        WalletCreateInfo walletCreateInfo = new WalletCreateInfo(customerId, voucherId);

        //when
        WalletCreateInfo returnCreateInfo = walletMapperService.create(walletCreateInfo);

        //then
        assertThat(returnCreateInfo).isNotNull();
    }
//jpa x -> 실제로 작성하는 쿼리 기반으로는 테스트
    @Test
    void findVouchers() {
    }

    @Test
    void delete() {
    }

    @Test
    void findCustomer() {
    }
}
