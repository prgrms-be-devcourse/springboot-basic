package com.blessing333.springbasic.voucher.controller.api;

import com.blessing333.springbasic.voucher.api.VoucherInformation;
import com.blessing333.springbasic.voucher.converter.VoucherPayloadConverter;
import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherCreateFormPayload;
import com.blessing333.springbasic.voucher.repository.VoucherRepository;
import com.blessing333.springbasic.voucher.service.VoucherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

class RestVoucherControllerTest {
    @Autowired
    private VoucherPayloadConverter converter;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    VoucherService service;

    @Autowired
    VoucherRepository repository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void resetDB() {
        repository.deleteAll();
    }

    @DisplayName("when 바우처 정보 전체 조회 _ given 입력값 정상, json 포맷 요청 _ should return 상태코드 200, json VoucherInformation.")
    @Test
    void inquiryAllTest() throws Exception {
        registerVoucher(Voucher.VoucherType.FIXED, 2000);
        registerVoucher(Voucher.VoucherType.FIXED, 4000);
        registerVoucher(Voucher.VoucherType.FIXED, 6000);

        MvcResult result = mockMvc.perform(get("/api/v1/vouchers"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        List<VoucherInformation> lists = mapper.readValue(result.getResponse().getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, VoucherInformation.class));
        assertThat(lists).hasSize(3);
    }

    @DisplayName("when 바우처 정보 전체 조회 _ given 입력값 정상, xml 포맷 요청 _ should return 상태코드 200, xml VoucherInformation.")
    @Test
    void inquiryAllTestWithXml() throws Exception {
        registerVoucher(Voucher.VoucherType.FIXED, 2000);
        registerVoucher(Voucher.VoucherType.FIXED, 4000);
        registerVoucher(Voucher.VoucherType.FIXED, 6000);

        mockMvc.perform(get("/api/v1/vouchers")
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @DisplayName("바우처 ID로 조회 _ 정상 처리 _ 상태코드 200, 바우처 정보")
    @Test
    void testVoucherInquiryById() throws Exception {
        Voucher voucher = registerVoucher(Voucher.VoucherType.FIXED, 2000);

        MvcResult result = mockMvc.perform(get("/api/v1/vouchers/" + voucher.getVoucherId()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        VoucherInformation res = mapper.readValue(result.getResponse().getContentAsString(), VoucherInformation.class);
        assertThat(res.getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(res.getVoucherType()).isEqualTo(voucher.getVoucherType());
        assertThat(res.getDiscountAmount()).isEqualTo(voucher.getDiscountAmount());
    }

    @DisplayName("존재하지 않는 바우처 조회 _ 잘못된 아이디 _ 상태코드 400, 에러")
    @Test
    void findVoucherByIdFail() throws Exception {
        UUID invalidId = UUID.randomUUID();

        mockMvc.perform(get("/api/v1/vouchers/" + invalidId))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }


    @DisplayName("바우처 생성 _ 정상 처리 _ 상태코드 200, 생성한 바우처 정보")
    @Test
    void voucherCreateTest() throws Exception {
        VoucherCreateFormPayload payload = new VoucherCreateFormPayload("1", "5000");
        String content = mapper.writeValueAsString(payload);
        MvcResult result = mockMvc.perform(post("/api/v1/vouchers")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        VoucherInformation voucherInformation = mapper.readValue(result.getResponse().getContentAsString(), VoucherInformation.class);
        VoucherCreateForm form = converter.toCreateForm(payload);
        assertThat(voucherInformation.getDiscountAmount()).isEqualTo(form.getDiscountAmount());
        assertThat(voucherInformation.getVoucherType()).isEqualTo(form.getVoucherType());
    }

    @DisplayName("바우처 생성 _ 입력값 에러 _ 상태코드 400, 에러 정보 반환")
    @Test
    void voucherCreateFailTest() throws Exception {
        VoucherCreateFormPayload payload = new VoucherCreateFormPayload("-1", "-5000");
        String content = mapper.writeValueAsString(payload);
        mockMvc.perform(post("/api/v1/vouchers")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError())
                    .andReturn();
    }

    @DisplayName("바우처 삭제 _ 정상 처리 _ 상태코드 200")
    @Test()
    void voucherDeleteTest() throws Exception {
        Voucher voucher = registerVoucher(Voucher.VoucherType.FIXED, 2000);

        mockMvc.perform(delete("/api/v1/vouchers/" + voucher.getVoucherId()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @DisplayName("바우처 삭제 _ 존재하지 않는 바우처 _ 상태코드 400")
    @Test()
    void voucherDeleteFailTest() throws Exception {
        UUID invalidId = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/vouchers/" + invalidId))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("바우처 타입으로 조회 _ 고정 바우처 타입 정상입력 _ 상태코드 200,입력된 바우처 타입에 해당하는 모든 바우처 정보.")
    @Test
    void findFixedVoucherByVoucherTypeTest() throws Exception {
        registerVoucher(Voucher.VoucherType.FIXED, 2000);
        registerVoucher(Voucher.VoucherType.PERCENT, 40);

        String typeCode = Voucher.VoucherType.FIXED.getOptionNumber();
        MvcResult result = mockMvc.perform(get("/api/v1/vouchers")
                        .param("voucherTypeCode", typeCode))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        TypeFactory typeFactory = mapper.getTypeFactory();
        List<VoucherInformation> vouchers = mapper.readValue(result.getResponse().getContentAsString(), typeFactory.constructCollectionType(List.class, VoucherInformation.class));
        vouchers.forEach((info) -> assertThat(info.getVoucherType()).isEqualTo(Voucher.VoucherType.FIXED));
    }

    @DisplayName("바우처 타입으로 조회 _ 비율 바우처 타입 정상입력 _ 상태코드 200, 입력된 바우처 타입에 해당하는 모든 바우처 정보.")
    @Test
    void findPercentVoucherByVoucherTypeTest() throws Exception {
        registerVoucher(Voucher.VoucherType.FIXED, 2000);
        registerVoucher(Voucher.VoucherType.PERCENT, 40);

        String typeCode = Voucher.VoucherType.PERCENT.getOptionNumber();
        MvcResult result = mockMvc.perform(get("/api/v1/vouchers")
                        .param("voucherTypeCode", typeCode))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        TypeFactory typeFactory = mapper.getTypeFactory();
        List<VoucherInformation> vouchers = mapper.readValue(result.getResponse().getContentAsString(),
                                                              typeFactory.constructCollectionType(List.class, VoucherInformation.class)
                                                               );
        assertThat(vouchers).hasSize(1);
        vouchers.forEach((info) -> assertThat(info.getVoucherType()).isEqualTo(Voucher.VoucherType.PERCENT));
    }

    private Voucher registerVoucher(Voucher.VoucherType type, long discountAmount) {
        return service.registerVoucher(new VoucherCreateForm(type, discountAmount));
    }
}