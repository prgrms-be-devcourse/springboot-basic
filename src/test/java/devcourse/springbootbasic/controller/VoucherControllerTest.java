package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoucherControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private VoucherController voucherController;

    @SuppressWarnings("unused") // controller 모킹 시 사용
    @Mock
    private VoucherService voucherService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(voucherController).build();
    }

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