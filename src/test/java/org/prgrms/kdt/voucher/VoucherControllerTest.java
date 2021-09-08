package org.prgrms.kdt.voucher;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.api.ApisTest;
import org.prgrms.kdt.common.BaseApiTest;
import org.prgrms.kdt.common.EmbeddedMysqlConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

}