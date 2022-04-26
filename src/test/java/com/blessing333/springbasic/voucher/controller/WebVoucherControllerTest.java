package com.blessing333.springbasic.voucher.controller;

import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherCreateFormPayload;
import com.blessing333.springbasic.voucher.dto.VoucherUpdateFormPayload;
import com.blessing333.springbasic.voucher.repository.VoucherRepository;
import com.blessing333.springbasic.voucher.service.VoucherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.blessing333.springbasic.voucher.controller.WebVoucherController.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
class WebVoucherControllerTest {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    VoucherService service;
    @Autowired
    VoucherRepository repository;
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("단일 바우처 조회 화면 요청시 조회 대상 바우처 정보를 모델에 담아 화면을 반환한다.")
    @Test
    void voucherViewTest() throws Exception {
        Voucher voucher = registerVoucher(Voucher.VoucherType.FIXED, 50000);
        mockMvc.perform(get("/vouchers/" + voucher.getVoucherId()))
                .andExpect(view().name(VOUCHER_VOUCHER_VIEW))
                .andExpect(model().attribute("voucher", voucher));
    }

    @DisplayName("모든 바우처 조회 화면 요청시 모든 바우처 정보를 모델에 담아 화면을 반환한다.")
    @Test
    void vouchersViewTest() throws Exception {
        registerVoucher(Voucher.VoucherType.FIXED, 50000);
        registerVoucher(Voucher.VoucherType.PERCENT, 50);
        registerVoucher(Voucher.VoucherType.PERCENT, 20);

        mockMvc.perform(get("/vouchers/"))
                .andExpect(view().name(VOUCHER_VOUCHERS_VIEW))
                .andExpect(model().attributeExists("vouchers"));
    }

    @DisplayName("바우처 등록 화면 요청시 빈 Payload 객체를 모델에 담아 바우처 등록 화면을 반환한다.")
    @Test
    void voucherRegisterViewTest() throws Exception {
        mockMvc.perform(get("/vouchers/registry-form"))
                .andExpect(view().name(VOUCHER_REGISTRY_VIEW))
                .andExpect(model().attributeExists("payload"));
    }

    @DisplayName("바우처 수정 화면 요청시 수정 대상 바우처정보를 모델에 담아 바우처 수정 화면을 반환한다.")
    @Test
    void voucherUpdateViewTest() throws Exception {
        Voucher voucher = registerVoucher(Voucher.VoucherType.FIXED, 50000);

        mockMvc.perform(get("/vouchers/update-form/" + voucher.getVoucherId()))
                .andExpect(view().name(VOUCHER_UPDATE_VIEW))
                .andExpect(model().attributeExists("payload"));
    }

    @DisplayName("바우처 등록 요청시 바우처를 생성하고 생성된 바우처 조회 화면으로 redirect 한다")
    @Test
    void voucherCreateTest() throws Exception {
        VoucherCreateFormPayload payload = new VoucherCreateFormPayload("1", "2000");

        mockMvc.perform(post("/vouchers")
                        .param("voucherType", payload.getVoucherType())
                        .param("discountAmount", payload.getDiscountAmount()))
                .andExpect(status().is3xxRedirection());
    }
    //TODO: 생성 페이로드가 유효하지 않을 경우 테스트

    @DisplayName("바우처 삭제 요청시 바우처를 삭제하고 모든 바우처 조회 화면으로 redirect 한다")
    @Test
    void voucherDeleteTest() throws Exception {
        Voucher voucher = registerVoucher(Voucher.VoucherType.FIXED, 50000);

        mockMvc.perform(delete("/vouchers/" + voucher.getVoucherId()))
                .andExpect(model().attributeExists("message"))
                .andExpect(status().is3xxRedirection());
    }

    //TODO: 삭제 대상 바우처 아이디가 존재하지 않을 경우 테스트

    @DisplayName("바우처 수정 요청시 바우처의 정보를 수정하고 해당 바우처 조회 화면으로 redirect 한다")
    @Test
    void voucherUpdateTest() throws Exception {
        Voucher voucher = registerVoucher(Voucher.VoucherType.FIXED, 50000);
        VoucherUpdateFormPayload payload = new VoucherUpdateFormPayload(voucher.getVoucherId().toString(), "2", "50");

        mockMvc.perform(patch("/vouchers/" + voucher.getVoucherId())
                        .param("voucherType", payload.getVoucherType())
                        .param("voucherId", payload.getVoucherId())
                        .param("discountAmount", payload.getDiscountAmount()))
                .andExpect(flash().attributeExists("message"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vouchers/" + voucher.getVoucherId()));
    }

    //TODO: 수정 대상 바우처 아이디가 존재하지 않을 경우 테스트

    private Voucher registerVoucher(Voucher.VoucherType type, long discountAmount) {
        VoucherCreateForm form = new VoucherCreateForm(type, discountAmount);
        return service.registerVoucher(form);
    }
}