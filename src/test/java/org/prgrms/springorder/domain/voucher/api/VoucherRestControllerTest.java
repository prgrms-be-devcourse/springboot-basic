package org.prgrms.springorder.domain.voucher.api;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.domain.customer.api.CustomerController;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
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

    @Autowired
    private ObjectMapper objectMapper;


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
            .andExpect(xpath(
                "/list/org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher").exists())
            .andExpect(xpath(
                "/list/org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher").nodeCount(
                saveCount))
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

    @DisplayName("Create Voucher 테스트 - 바우처가 생성되고 ID를 반환한다")
    @Test
    void createVoucherSuccessTest() throws Exception {
        //given
        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(VoucherType.PERCENT,
            100L);

        //then & when
        mockMvc.perform(post("/api/v1/vouchers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voucherCreateRequest))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.voucherId").exists())
            .andDo(print());
    }


    @DisplayName("delete Voucher 테스트 - 바우처가 존재하면 바우처를 삭제한다")
    @Test
    void deleteVoucherSuccessTest() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, 100L);

        voucherJdbcRepository.insert(voucher);

        //then & when
        mockMvc.perform(delete("/api/v1/vouchers/{voucherId}", voucherId.toString())
                .contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isOk())
            .andDo(print());

        Optional<Voucher> findVoucher = voucherJdbcRepository.findById(voucherId);
        assertTrue(findVoucher.isEmpty());

    }

    @DisplayName("delete Voucher 테스트 - 바우처가 존재하지 않으면 404 응답이 온다.")
    @Test
    void deleteVoucherFailTest() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();

        //then & when
        mockMvc.perform(delete("/api/v1/vouchers/{voucherId}", voucherId.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.errorMessage").exists())
            .andExpect(jsonPath("$.statusCode").exists())
            .andExpect(jsonPath("$.statusCode").value(404))
            .andExpect(jsonPath("$.requestUri").exists())
            .andDo(print());
    }

    @DisplayName("find Success 테스트 - 바우처가 존재하면 VoucherResponse 응답이 온다.")
    @Test
    void findVoucherSuccessTest() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = VoucherType.FIXED;
        long amount = 100L;

        Voucher voucher = new FixedAmountVoucher(voucherId, amount);

        voucherJdbcRepository.insert(voucher);

        //then & when
        mockMvc.perform(get("/api/v1/vouchers/{voucherId}", voucherId.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.voucherId").value(voucherId.toString()))
            .andExpect(jsonPath("$.amount").value(amount))
            .andExpect(jsonPath("$.voucherType").value(voucherType.name()))
            .andExpect(jsonPath("$.createdAt").exists())
            .andExpect(jsonPath("$.customerId").doesNotExist())
            .andDo(print());
    }

    @DisplayName("find Voucher 테스트 - 바우처가 존재하지 않으면 404 응답이 온다.")
    @Test
    void findVoucherFailTest() throws Exception {
        ///given
        UUID voucherId = UUID.randomUUID();

        //then & when
        mockMvc.perform(get("/api/v1/vouchers/{voucherId}", voucherId.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.errorMessage").exists())
            .andExpect(jsonPath("$.statusCode").exists())
            .andExpect(jsonPath("$.statusCode").value(404))
            .andExpect(jsonPath("$.requestUri").exists())
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