package org.prgrms.kdt.voucher;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.common.EmbeddedMysqlConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by yhh1056
 * Date: 2021/09/08 Time: 4:01 오후
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(VoucherController.class)
class VoucherControllerTest extends EmbeddedMysqlConnector {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    @Test
    @DisplayName("바우처 서비스 메인페이지")
    void main() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(view().name("admin/main"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("바우처 조회")))
                .andExpect(content().string(containsString("바우처 등록")));
    }

    @Test
    @DisplayName("바우처 전체 조회 테스트")
    void vouchers() throws Exception {
        IntStream.range(1, 10)
                .forEach(i -> voucherJdbcRepository.insert(givenPercentVoucher(UUID.randomUUID())));

        mockMvc.perform(get("/admin/vouchers"))
                .andDo(print())
                .andExpect(view().name("admin/vouchers"))
                .andExpect(model().attributeExists("vouchers"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test voucher")));
    }

    private Voucher givenFixedVoucher(UUID voucherId) {
        return new Voucher(voucherId, "test voucher", 50L, VoucherType.FIX, LocalDateTime.now());
    }

    private Voucher givenPercentVoucher(UUID voucherId) {
        return new Voucher(voucherId, "test voucher", 50L, VoucherType.PERCENT, LocalDateTime.now());
    }

}