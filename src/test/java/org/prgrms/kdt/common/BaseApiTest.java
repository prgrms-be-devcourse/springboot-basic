package org.prgrms.kdt.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.CustomerRepository;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.wallet.Wallet;
import org.prgrms.kdt.wallet.WalletDto;
import org.prgrms.kdt.wallet.WalletJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by yhh1056
 * Date: 2021/09/07 Time: 11:48 오후
 */
@Disabled
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {WebConfig.class})
@EnableWebMvc
public class BaseApiTest extends EmbeddedMysqlConnector {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected CustomerRepository customerRepository;

    @Autowired
    protected VoucherRepository voucherRepository;

    @Autowired
    protected WalletJdbcRepository walletJdbcRepository;

    @Autowired
    protected ObjectMapper objectMapper;

    protected UUID mockCustomerId;
    protected UUID mockVoucherId;

    @BeforeEach
    void beforeEach() {
        walletJdbcRepository.deleteAll();
        customerRepository.deleteAll();
        voucherRepository.deleteAll();
    }

    public void initCustomer() {
        mockCustomerId = UUID.randomUUID();
        customerRepository.insert(givenCustomer(mockCustomerId));
    }

    public void initVoucher() {
        mockVoucherId = UUID.randomUUID();
        voucherRepository.insert(givenVoucher(mockVoucherId));
    }

    public void initWallet() {
        walletJdbcRepository.insert(new Wallet(UUID.randomUUID(), mockCustomerId, mockVoucherId));
    }

    protected Customer givenCustomer(UUID customerId) {
        return new Customer(customerId, "tester", "tester@email.com", LocalDateTime.now());
    }

    protected Voucher givenVoucher(UUID voucherId) {
        return new Voucher(voucherId, "test voucher", 100L, VoucherType.FIX, LocalDateTime.now());
    }
}
