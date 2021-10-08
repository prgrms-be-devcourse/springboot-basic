package org.prgrms.orderApp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.orderApp.voucher.Voucher;
import org.prgrms.orderApp.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.config.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(VoucherController.class)
class VoucherControllerTest {

    @Autowired
    private MockMvc mockMvc;


    VoucherController voucherController;

    @Autowired
    VoucherRepository voucherRepository;

    @Configuration
    @EnableWebMvc
    @ComponentScan( basePackages = {"org.prgrms.orderApp.controller","org.prgrms.orderApp.voucher"})
    static class Config  implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/")
                    .addResourceLocations("classpath:/views/")
                    .setCachePeriod(20);
        }

    }



    @AfterEach
    void deleteAll(){
        voucherRepository.findAll()
                .stream().forEach(v -> voucherRepository.deleteById(v.getVoucherId()));
    }

    @Test
    @DisplayName("모든 데이터를 조회하고, 전체 조회 목록 페이지로 이동한다.")
    void getAllListTest() throws Exception {
        //Given
        voucherRepository.insert(new Voucher("VOUCHER-1234", 20, "FIXED", LocalDateTime.now().toLocalDate()));
        voucherRepository.insert(new Voucher("VOUCHER-1223", 60, "PERCENT", LocalDateTime.now().toLocalDate()));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/voucher/list");

        //When, Then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("특정 바우처 아이디를 조회하여, 상세 페이지로 이동한다.")
    void getByIdTest() throws Exception {
        //Given
        var targetData = new Voucher("VOUCHER-1234", 20, "FIXED", LocalDateTime.now().toLocalDate());
        voucherRepository.insert(targetData);
        voucherRepository.insert(new Voucher("VOUCHER-1223", 60, "PERCENT", LocalDateTime.now().toLocalDate()));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/voucher/details/{voucher_id}",targetData.getVoucherId());

        //When, Then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("특정 바우처를 삭제하고 리다이랙션으로 전체 저회 목록 화면으로 이동한다.")
    void deleteByIdTest() throws Exception {
        //Given
        var targetData = new Voucher("VOUCHER-1234", 20, "FIXED", LocalDateTime.now().toLocalDate());
        voucherRepository.insert(targetData);
        voucherRepository.insert(new Voucher("VOUCHER-1223", 60, "PERCENT", LocalDateTime.now().toLocalDate()));
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>(){{
            put("id", Collections.singletonList(targetData.getVoucherId().toString()));
        }};
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/voucher/delete").params(requestParam);

        //When, Then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 생성 페이지로 이동한다.")
    void createFormTest() throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/voucher/create");

        //When, Then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 생성 페이지에서 바우처를 생성한다.")
    @Disabled // 실제 작동은 되지만, 테스트 코드를 작성하지 못한 상태입니다.
    void createVoucherTest() throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/voucher/create")
                .param("name", "VOUCHER-FIXED-1234")
                .param("amount", String.valueOf(200))
                .param("voucherType","FIXED")
                .param("expireAt", String.valueOf(LocalDateTime.now()))
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE);


        //When, Then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }

}