package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.service.VoucherService;
import devcourse.springbootbasic.service.WalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoucherController.class)
class VoucherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("unused") // VoucherController 모킹
    @MockBean
    private VoucherService voucherService;

    @SuppressWarnings("unused") // VoucherController 모킹
    @MockBean
    private WalletService walletService;

    @ParameterizedTest
    @CsvSource({
            "FIXED, 2023-01-01, 2023-01-05",
            "PERCENT, 2023-01-01, 2023-01-05"
    })
    @DisplayName("바우처 조회 시 검색 조건이 올바른 경우 200 OK를 반환합니다.")
    void getVoucherByValidSearchParameters(String voucherType, String startDate, String endDate) throws Exception {
        mockMvc.perform(get("/api/v1/vouchers")
                        .param("voucherType", voucherType)
                        .param("startDate", startDate)
                        .param("endDate", endDate))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @ParameterizedTest
    @CsvSource({
            "FIXED, 2023-01-01, 2023-01-32",
            "PERCENT, 2023-01-01, 2023-01-32",
            "PECENTFIX, 2023-01-01, 2023-01-31",
            "OGU, 2023-01-01, 2023-01-31",
    })
    @DisplayName("바우처 조회 시 검색 조건이 잘못된 경우 400 에러를 반환합니다.")
    void testInvalidVoucherSearchParameters(String voucherType, String startDate, String endDate) throws Exception {
        mockMvc.perform(get("/api/v1/vouchers")
                        .param("voucherType", voucherType)
                        .param("startDate", startDate)
                        .param("endDate", endDate))
                .andExpect(status().isBadRequest());
    }
}