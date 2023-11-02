package org.prgrms.vouchermanagement.wallet.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.dto.WalletCreateInfo;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.policy.FixedAmountVoucher;
import org.prgrms.vouchermanagement.wallet.repository.WalletMapperRepository;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class WalletMapperServiceTest {

    WalletMapperService walletMapperService;
    WalletMapperRepository walletMapperRepository;

    @BeforeEach
    void setUp() {
        walletMapperService = new WalletMapperService(walletMapperRepository);
    }

    @Test
    @DisplayName("wallet 생성 테스트")
    void create() {
        Customer customer = new Customer(UUID.randomUUID(), "김남규", 23);
        Voucher voucher = new Voucher(UUID.randomUUID(), new FixedAmountVoucher(3000));

        UUID userId = customer.getUserId();
        UUID voucherId = voucher.getVoucherId();

        WalletCreateInfo walletCreateInfo = walletMapperService.create(new WalletCreateInfo(userId, voucherId));

        Optional<Customer> customer1 = walletMapperRepository.findCustomer(voucherId);

        assertThat(customer1.get()).isNotNull();
    }

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
