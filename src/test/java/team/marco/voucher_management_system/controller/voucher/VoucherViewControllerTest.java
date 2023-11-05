package team.marco.voucher_management_system.controller.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;
import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.service.voucher.VoucherService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static team.marco.voucher_management_system.domain.voucher.VoucherType.FIXED;

@WebMvcTest(controllers = VoucherViewController.class)
class VoucherViewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoucherService voucherService;

    @DisplayName("전체 쿠폰 목록을 조회할 수 있다.")
    @Test
    void findAllVouchers() throws Exception {
        // given
        List<Voucher> vouchers = new ArrayList<>();

        when(voucherService.getVouchers()).thenReturn(vouchers);

        // when
        MvcResult mvcResult = mockMvc.perform(
                get("/vouchers")
        ).andExpect(status().isOk()).andReturn();

        // then
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "voucher/voucher_list");
    }

    @DisplayName("쿠폰 ID로 쿠폰 상세 정보를 조회할 수 있다.")
    @Test
    void findVoucher() throws Exception {
        // given
        Voucher voucher = new Voucher.Builder(1L, FIXED, 1_000).build();

        when(voucherService.getVoucher(voucher.getId())).thenReturn(voucher);

        // when
        MvcResult mvcResult = mockMvc.perform(
                get("/vouchers/{voucherId}", 1L)
        ).andExpect(status().isOk()).andReturn();

        // then
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "voucher/voucher_detail");
    }

    @DisplayName("쿠폰 생성 폼 뷰 html 파일을 제공할 수 있다.")
    @Test
    void findVoucherCreateForm() throws Exception {
        // when
        MvcResult mvcResult = mockMvc.perform(
                get("/vouchers/create")
        ).andExpect(status().isOk()).andReturn();

        // then
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "voucher/voucher_create_form");
    }

    @DisplayName("쿠폰을 생성할 수 있다.")
    @Test
    void createVoucher() throws Exception {
        // given
        String voucherType = FIXED.name();
        int discountValue = 1_000;

        // when
        MvcResult mvcResult = mockMvc.perform(
                post("/vouchers")
                        .param("voucherType", voucherType)
                        .param("discountValue", String.valueOf(discountValue))
        ).andExpect(status().isOk()).andReturn();

        // then
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "voucher/voucher_list");
    }

    @DisplayName("쿠폰 ID로 쿠폰을 삭제할 수 있다.")
    @Test
    void deleteVoucher() throws Exception {
        // given
        Long voucherId = 1L;

        // when
        MvcResult mvcResult = mockMvc.perform(
                delete("/vouchers/{voucherId}", voucherId)
        ).andExpect(status().isOk()).andReturn();

        // then
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "voucher/voucher_list");
    }
}