package org.prgrms.kdt.wallet;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.common.EmbeddedMysqlConnector;
import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.CustomerRepository;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherController;
import org.prgrms.kdt.voucher.VoucherJdbcRepository;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by yhh1056
 * Date: 2021/09/09 Time: 8:19 오후
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(WalletController.class)
class WalletControllerTest extends EmbeddedMysqlConnector {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    @BeforeEach
    void beforeEach() {
        voucherJdbcRepository.deleteAll();
        customerRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    @DisplayName("지갑 등록 페이지 (고객 선택)")
    void wallet_customer() throws Exception {
        mockMvc.perform(get("/admin/wallet"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customers"))
                .andExpect(view().name("admin/wallet"));
    }

    @Test
    @DisplayName("지갑 등록 페이지 (바우처 선택)")
    void wallet_voucher() throws Exception {
        UUID customerId = UUID.randomUUID();
        customerRepository.insert(new Customer(customerId, "tester", "tester@email.com", LocalDateTime.now()));

        mockMvc.perform(get("/admin/wallet/{customerId}", customerId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customerId"))
                .andExpect(model().attributeExists("vouchers"))
                .andExpect(model().attributeExists("walletDto"))
                .andExpect(view().name("admin/wallet/form"));
    }

    @Test
    @DisplayName("지갑 등록")
    void submitWallet() throws Exception {
        UUID customerId = UUID.randomUUID();
        customerRepository.insert(new Customer(customerId, "tester", "tester@email.com", LocalDateTime.now()));

        UUID voucherId = UUID.randomUUID();
        voucherJdbcRepository.insert(new Voucher(voucherId, "voucher", 100L, VoucherType.FIX, LocalDateTime.now()));

        mockMvc.perform(post("/admin/wallet")
                .param("customerId", customerId.toString())
                .param("voucherId", voucherId.toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));
    }

    @Test
    @DisplayName("지갑 삭제")
    void deleteWallet() throws Exception {
        UUID customerId = UUID.randomUUID();
        customerRepository.insert(new Customer(customerId, "tester", "tester@email.com", LocalDateTime.now()));

        UUID voucherId = UUID.randomUUID();
        voucherJdbcRepository.insert(new Voucher(voucherId, "voucher", 100L, VoucherType.FIX, LocalDateTime.now()));

        mockMvc.perform(post("/admin/wallet/deletion")
                .param("customerId", customerId.toString())
                .param("voucherId", voucherId.toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/voucher/" + voucherId));
    }
}