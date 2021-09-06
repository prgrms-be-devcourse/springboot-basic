package org.prgrms.kdt.api;

import static org.prgrms.kdt.api.Apis.CUSTOMER_ID;
import static org.prgrms.kdt.api.Apis.WALLET;
import static org.prgrms.kdt.api.Apis.PRE_FIX;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.common.BaseRepositoryTest;
import org.prgrms.kdt.common.WebConfig;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 1:42 오후
 */

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {WebConfig.class})
@EnableWebMvc
class ApisTest extends BaseRepositoryTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    WalletJdbcRepository walletJdbcRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() {
        walletJdbcRepository.deleteAll();
        customerRepository.deleteAll();
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("고객에게 바우처를 정상적으로 할당하는 테스트")
    void addVoucherByCustomer() throws Exception {
        UUID customerId = UUID.randomUUID();
        customerRepository.insert(givenCustomer(customerId));

        UUID voucherId = UUID.randomUUID();
        voucherRepository.insert(givenVoucher(voucherId));

        WalletDto dto = new WalletDto();
        dto.setCustomerId(customerId.toString());
        dto.setVoucherId(voucherId.toString());

        mockMvc.perform(post(PRE_FIX + WALLET)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("customerId").value(customerId.toString()))
                .andExpect(jsonPath("voucherId").value(voucherId.toString()));
    }

    @Test
    @DisplayName("고객에게 바우처 할당시 고객이 존재하지 않으면 not found 테스트")
    void addVoucherByCustomer_notfound_customer() throws Exception {
        UUID voucherId = UUID.randomUUID();
        voucherRepository.insert(givenVoucher(voucherId));

        WalletDto dto = new WalletDto();
        dto.setCustomerId(UUID.randomUUID().toString());
        dto.setVoucherId(voucherId.toString());

        mockMvc.perform(post(PRE_FIX + WALLET)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("고객에게 바우처 할당시 바우처가 존재하지 않으면 not found 테스트")
    void addVoucherByCustomer_notfound_voucher() throws Exception {
        UUID customerId = UUID.randomUUID();
        customerRepository.insert(givenCustomer(customerId));

        WalletDto dto = new WalletDto();
        dto.setCustomerId(customerId.toString());
        dto.setVoucherId(UUID.randomUUID().toString());

        mockMvc.perform(post(PRE_FIX + WALLET)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("고객의 아이디로 보유한 바우처 조회 테스트")
    void getVoucherByCustomerId() throws Exception {
        UUID customerId = UUID.randomUUID();
        customerRepository.insert(givenCustomer(customerId));

        UUID voucherId = UUID.randomUUID();
        voucherRepository.insert(givenVoucher(voucherId));

        walletJdbcRepository.insert(new Wallet(UUID.randomUUID(), customerId, voucherId));

        mockMvc.perform(get(PRE_FIX + WALLET + CUSTOMER_ID, customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.vouchers").exists())
                .andExpect(jsonPath("$..voucherId").exists())
                .andExpect(jsonPath("$..name").exists())
                .andExpect(jsonPath("$..discount").exists())
                .andExpect(jsonPath("$..voucherType").exists())
                .andExpect(jsonPath("$..createdAt").exists());
    }

    private Customer givenCustomer(UUID customerId) {
        return new Customer(customerId, "tester", "tester@email.com", LocalDateTime.now());
    }

    private Voucher givenVoucher(UUID voucherId) {
        return new Voucher(voucherId, "test voucher", 100L, VoucherType.FIX, LocalDateTime.now());
    }
}