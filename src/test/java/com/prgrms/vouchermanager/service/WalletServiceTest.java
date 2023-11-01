package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.AppConfig;
import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.wallet.Wallet;
import com.prgrms.vouchermanager.dto.customer.CustomerResponse;
import com.prgrms.vouchermanager.dto.voucher.VoucherResponse;
import com.prgrms.vouchermanager.dto.wallet.WalletRequest;
import com.prgrms.vouchermanager.dto.wallet.WalletResponse;
import com.prgrms.vouchermanager.repository.customer.BlacklistFileRepository;
import com.prgrms.vouchermanager.repository.customer.CustomerRepository;
import com.prgrms.vouchermanager.repository.voucher.VoucherJdbcRepository;
import com.prgrms.vouchermanager.repository.wallet.WalletRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;

import static com.prgrms.vouchermanager.dto.customer.CustomerResponse.*;
import static com.prgrms.vouchermanager.dto.voucher.VoucherResponse.*;
import static com.prgrms.vouchermanager.dto.wallet.WalletRequest.*;
import static com.prgrms.vouchermanager.dto.wallet.WalletResponse.*;

@SpringJUnitConfig
class WalletServiceTest {
    @Autowired
    private WalletRepository repository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VoucherJdbcRepository voucherJdbcRepository;
    private WalletService service;
    @Autowired
    private JdbcTemplate template;

    Customer customer1 = new Customer("푸리나", 1993);
    Voucher voucher1 = new FixedAmountVoucher(20000);
    Voucher voucher2 = new PercentAmountVoucher(20);
    Wallet wallet1 = new Wallet(voucher1.getId(), customer1.getId());

    private final static String DELETE_WALLETS_QUERY = "delete from wallets;";
    private final static String DELETE_CUSTOMERS_QUERY = "delete from customers;";
    private final static String DELETE_VOUCHERS_QUERY = "delete from vouchers;";

    @Configuration
    static class TestConfig extends AppConfig {
    }

    @BeforeEach
    void beforeEach() {
        service = new WalletService(repository, customerRepository, voucherJdbcRepository);

        voucherJdbcRepository.create(voucher1);
        voucherJdbcRepository.create(voucher2);
        customerRepository.create(customer1);

        repository.create(wallet1);
    }

    @AfterEach
    void afterEach() {
        template.execute(DELETE_WALLETS_QUERY);
        template.execute(DELETE_CUSTOMERS_QUERY);
        template.execute(DELETE_VOUCHERS_QUERY);
    }

    @Test
    @DisplayName("create")
    void create() {
        WalletDetailRequest request = WalletDetailRequest.builder()
                .voucherId(voucher2.getId())
                .customerId(customer1.getId())
                .build();
        WalletDetailResponse response = service.create(request);

        Assertions.assertThat(response.voucherId()).isEqualTo(voucher2.getId());
        Assertions.assertThat(response.customerId()).isEqualTo(customer1.getId());
    }

    @Test
    @DisplayName("findByCustomerId")
    void findByCustomerId() {
        WalletDetailRequest request = WalletDetailRequest.builder()
                .voucherId(voucher2.getId())
                .customerId(customer1.getId())
                .build();
        WalletDetailResponse response = service.create(request);
        List<VoucherDetailResponse> responses = service.findByCustomerId(response.customerId());

        Assertions.assertThat(responses.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findByVoucherId")
    void findByVoucherId() {
        WalletDetailRequest request = WalletDetailRequest.builder()
                .voucherId(voucher2.getId())
                .customerId(customer1.getId())
                .build();
        WalletDetailResponse response = service.create(request);
        List<CustomerDetailResponse> responses = service.findByVoucherId(request.voucherId());

        Assertions.assertThat(responses.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("delete")
    void delete() {
        int delete = service.delete(wallet1.getWalletId());
        Assertions.assertThat(delete).isEqualTo(1);
    }
}
