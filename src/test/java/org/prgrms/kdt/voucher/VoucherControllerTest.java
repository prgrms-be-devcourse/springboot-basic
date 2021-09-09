package org.prgrms.kdt.voucher;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        mockMvc.perform(get("/admin/voucher/vouchers"))
                .andDo(print())
                .andExpect(view().name("admin/voucher/vouchers"))
                .andExpect(model().attributeExists("vouchers"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test voucher")));
    }

    @Test
    @DisplayName("바우처 등록 페이지로 이동")
    void form() throws Exception {
        mockMvc.perform(get("/admin/voucher/form"))
                .andDo(print())
                .andExpect(view().name("admin/voucher/form"))
                .andExpect(model().attributeExists("voucherDto"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("바우처 정상적으로 등록")
    void submit() throws Exception {
        mockMvc.perform(post("/admin/voucher/form")
                .param("name", "test voucher")
                .param("discount", "100")
                .param("voucherType", "FIX"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));
    }

    @Test
    @DisplayName("이름이 공백일 경우 예외")
    void submit_fail_name_blank() throws Exception {
        mockMvc.perform(post("/admin/voucher/form")
                .param("name", "  ")
                .param("discount", "100")
                .param("voucherType", "FIX"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/voucher/form"))
                .andExpect(model().attributeHasFieldErrorCode("voucherForm", "name", "NotBlank"));
    }

    @Test
    @DisplayName("바우처 이름이 20자가 넘는 경우 예외")
    void submit_fail_name_over() throws Exception {
        mockMvc.perform(post("/admin/voucher/form")
                .param("name", "0123456789 0123456789")
                .param("discount", "100")
                .param("voucherType", "FIX"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/voucher/form"))
                .andExpect(model().attributeHasFieldErrorCode("voucherForm", "name", "Length"));
    }

    @Test
    @DisplayName("할인금액이 음수인 경우 예외")
    void submit_fail_discount_min() throws Exception {
        mockMvc.perform(post("/admin/voucher/form")
                .param("name", "test voucher")
                .param("discount", "-1")
                .param("voucherType", "FIX"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/voucher/form"))
                .andExpect(model().attributeHasFieldErrorCode("voucherForm", "discount", "Min"));
    }

    @Test
    @DisplayName("할인금액이 100만이 넘는경우 예외")
    void submit_fail_discount_max() throws Exception {
        mockMvc.perform(post("/admin/voucher/form")
                .param("name", "test voucher")
                .param("discount", "1000000000000")
                .param("voucherType", "FIX"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/voucher/form"))
                .andExpect(model().attributeHasFieldErrorCode("voucherForm", "discount", "Max"));
    }

    @Test
    @DisplayName("바우처 타입을 선택하지 않는 경우 예외")
    void submit_fail_voucherType_blank() throws Exception {
        mockMvc.perform(post("/admin/voucher/form")
                .param("name", "test voucher")
                .param("discount", "100")
                .param("voucherType", " "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/voucher/form"))
                .andExpect(model().attributeHasFieldErrorCode("voucherForm", "voucherType", "NotBlank"));
    }

    private Voucher givenFixedVoucher(UUID voucherId) {
        return new Voucher(voucherId, "test voucher", 50L, VoucherType.FIX, LocalDateTime.now());
    }

    private Voucher givenPercentVoucher(UUID voucherId) {
        return new Voucher(voucherId, "test voucher", 50L, VoucherType.PERCENT, LocalDateTime.now());
    }

}