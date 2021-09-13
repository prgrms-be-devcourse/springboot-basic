package org.prgrms.kdt.wallet;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.ClassLevelTestConfig;
import org.prgrms.kdt.JdbcTemplateConfig;
import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.CustomerRepository;
import org.prgrms.kdt.voucher.repository.VoucherJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
class WalletJdbcWalletRepositoryTest extends ClassLevelTestConfig {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdt.wallet", "org.prgrms.kdt.customer", "org.prgrms.kdt.voucher"})
    static class Config extends JdbcTemplateConfig {}

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    CustomerRepository customerRepository;

    Customer newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user@gamil.com", LocalDateTime.now());

    @Test
    @DisplayName("고객에게 지갑을 하나 할당 해 줄 수 있다.")
    @Order(1)
    void shouldInsertWallet(){
        customerRepository.deleteAll();
        customerRepository.insert(newCustomer);
        var rowNum= walletRepository.insert(newCustomer.getCustomerId());

        assertThat(rowNum, is(1));
    }

    @Test
    @DisplayName("customerId로 지갑 정보를 DB로 부터 조회할 수 있다.")
    @Order(2)
    void shouldFindByCustomerId() {
        var wallets = walletRepository.findByCustomerId(newCustomer.getCustomerId());

        assertThat(wallets, not(empty()));
        assertThat(wallets.get(0).getCustomerId(), is(newCustomer.getCustomerId()));
    }

    @Test
    @DisplayName("walletId가 일치하는 지갑 정보를 DB로 부터 조회할 수 있다.")
    @Order(3)
    void shouldFindByWalletId() {
        var allWallets = walletRepository.findAll();
        var wallet = allWallets.get(allWallets.size()-1);

        var wallets = walletRepository.findByWalletId(wallet.getWalletId());

        assertThat(wallets, not(empty()));
        assertThat(wallets.get(0).getCustomerId(), is(newCustomer.getCustomerId()));
    }
}