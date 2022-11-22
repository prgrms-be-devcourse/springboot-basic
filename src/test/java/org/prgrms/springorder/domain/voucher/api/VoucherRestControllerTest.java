package org.prgrms.springorder.domain.voucher.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.domain.customer.api.CustomerController;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.repository.VoucherJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class VoucherRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VoucherJdbcRepository voucherJdbcRepository;

    @MockBean
    private CustomerController customerController;


    @DisplayName("findAll Voucher 테스트 - 저장된 모든 Voucher 가 XML 형식으로 반환된다 ")
    @Test
    void findAllCustomersXmlSuccessTest() throws Exception {
        //given
        int saveCount = 5;
        List<Voucher> save = createVouchers(saveCount);

        //then & when

        mockMvc.perform(get("/api/v1/vouchers")
                .accept(MediaType.APPLICATION_XML_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_XML_VALUE))
            .andExpect(xpath("list").exists())
            .andExpect(xpath("list").nodeCount(1))
            .andExpect(xpath("/list/org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher").exists())
            .andExpect(xpath("/list/org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher").nodeCount(saveCount))
            .andDo(print());
    }

    @DisplayName("findAll Voucher 테스트 - 저장된 모든 voucher 가 JSON 형식으로 반환된다 ")
    @Test
    void findAllCustomersJsonSuccessTest() throws Exception {
        //given
        int saveCount = 5;
        List<Voucher> save = createVouchers(saveCount);

        //then & when

        mockMvc.perform(get("/api/v1/vouchers")
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )

            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.*").exists())
            .andExpect(jsonPath("$.length()").value(saveCount))
            .andExpect(jsonPath("$[*].voucherType").exists())
            .andExpect(jsonPath("$[*].voucherId").exists())
            .andExpect(jsonPath("$[*].createdAt").exists())
            .andExpect(jsonPath("$[*].amount").exists())

            .andDo(print());
    }

    private List<Voucher> createVouchers(int saveCount) {
        return IntStream.range(0, saveCount)
            .mapToObj(value -> {
                FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),
                    100L);
                return voucherJdbcRepository.insert(fixedAmountVoucher);
            }).collect(Collectors.toList());
    }
}