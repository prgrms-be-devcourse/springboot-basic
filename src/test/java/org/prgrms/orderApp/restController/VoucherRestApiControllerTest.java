package org.prgrms.orderApp.restController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;

import org.prgrms.orderApp.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yunyun on 2021/10/08.
 */


@WebMvcTest(VoucherRestApiController.class)
class VoucherRestApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    VoucherRepository voucherRepository;

    @Configuration
    @EnableWebMvc
    @ComponentScan( basePackages = {"org.prgrms.orderApp.voucher"})
    static class Config { }


    @Test
    @DisplayName("전체 목록을 조회할 수 있다.")
    @Disabled
    void getAllListTest() throws Exception {
        //Given
        String content = objectMapper.writeValueAsString(Collections.EMPTY_MAP);

        //Given
        mockMvc.perform(
                get("/rest-api/v1/voucher/list")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("바우처 아이디를 통해, 바우처 정보를 요청하여, 조회할 수 있다.")
    @Disabled
    void getDetailsTest() {
    }

    @Test
    @DisplayName("바우처 아이디를 통해, 바우처 정보 삭제를 요청하여 삭제할 수 있다.")
    @Disabled
    void deleteVoucherByIdTest() {
    }

    @Test
    @DisplayName("바우처 생성을 요청하여 저장할 수 있다.")
    @Disabled
    void createTest() {
    }

    @Test
    @DisplayName("바우처 타입을 통해, 바우처 정보를 요청하여 조회할 수 있다. ")
    @Disabled
    void getListByTypeTest() {
    }

    @Test
    @DisplayName("생성 날짜를 통해, 바우처 정보를 요청하여 조회할 수 있다. ")
    @Disabled
    void getListByCreatedAtTest() {
    }
}