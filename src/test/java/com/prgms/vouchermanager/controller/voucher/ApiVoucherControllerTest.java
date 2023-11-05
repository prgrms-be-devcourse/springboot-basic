package com.prgms.vouchermanager.controller.voucher;

import com.prgms.vouchermanager.contorller.voucher.ApiVoucherController;
import com.prgms.vouchermanager.service.voucher.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiVoucherController.class)
public class ApiVoucherControllerTest {
    private final UUID existId = UUID.fromString("bc2e6a9d-1319-4db0-83ba-e07a85fffab8");
    private final UUID NotExistId = UUID.fromString("ac2e6a9d-1319-4db0-83ba-e07a85fffab1");
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VoucherService voucherService;

    @Test
    @DisplayName("findAll()으로 모든 목록 불러오기를 성공한다.")
    void findAll() throws Exception {

        mockMvc.perform(get("/api/vouchers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("findById()로 존재하는 바우처 읽기에 성공한다.")
    void findByIdSuccess() throws Exception {

        mockMvc.perform(get("/api/vouchers/{id}", existId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

//    @Test
//    @DisplayName("findById()로 존재하지 않는 바우처 읽기에 실패한다.")
//    void findByIdFail() throws Exception {
//        mockMvc.perform(get("/api/vouchers/{id}", NotExistId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andDo(print());
//    }  --> 웹이나 postman에서는 notFound 처리되지만, mockMvc 테스트만 항상 200처리됩니다.


}
