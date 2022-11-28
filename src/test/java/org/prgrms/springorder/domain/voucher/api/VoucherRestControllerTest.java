package org.prgrms.springorder.domain.voucher.api;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
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
import java.time.LocalDateTime;
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
import org.prgrms.springorder.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.domain.voucher.repository.VoucherJdbcRepository;
import org.prgrms.springorder.domain.voucher.service.VoucherFactory;
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
    void findAllVouchersXmlSuccessTest() throws Exception {
        //given
        int saveCount = 5;
        createFixedVouchers(saveCount);

        //then & when
        mockMvc.perform(get("/api/v1/vouchers")
                .accept(MediaType.APPLICATION_XML_VALUE)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_XML_VALUE))
            .andExpect(xpath("list").exists())
            .andExpect(xpath("list").nodeCount(1))
            .andExpect(xpath(
                "/list/org.prgrms.springorder.domain.voucher.api.response.VoucherResponse").exists())
            .andExpect(xpath(
                "/list/org.prgrms.springorder.domain.voucher.api.response.VoucherResponse").nodeCount(
                saveCount))
            .andDo(print());
    }

    @DisplayName("findAll Voucher 테스트 - 저장된 모든 voucher 가 JSON 형식으로 반환된다 ")
    @Test
    void findAllVouchersJsonSuccessTest() throws Exception {
        //given
        int saveCount = 5;
        List<Voucher> save = createFixedVouchers(saveCount);

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

    @DisplayName("findAll Voucher 테스트 - Voucher Type 에 맞는 조건만 조회해온다 ")
    @Test
    void findAllVouchersByVoucherTypeSuccessTest() throws Exception {
        //given
        VoucherType voucherType = VoucherType.FIXED;

        int fixedCount = 5;
        createFixedVouchers(fixedCount);

        int percentCount = 30;
        createPercentVouchers(percentCount);

        //then & when
        mockMvc.perform(get("/api/v1/vouchers")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("voucherType", voucherType.getType())
            )

            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.*").exists())
            .andExpect(jsonPath("$.length()").value(fixedCount))
            .andExpect(jsonPath("$[*].voucherId").exists())
            .andExpect(
                jsonPath("$[*].voucherType").value(everyItem(equalTo(voucherType.getType()))))
            .andExpect(jsonPath("$[*].createdAt").exists())
            .andExpect(jsonPath("$[*].amount").exists())
            .andDo(print());
    }

    @DisplayName("findAll Voucher 테스트 - startDate 와 endDate 사이에 맞는 조건만 조회해온다 ")
    @Test
    void findAllVouchersBetweenStartDateAndEndDateSuccessTest() throws Exception {
        //given
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 0, 0);

        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 0, 0);

        int countForCondition = 50;
        int countNotMatchCondition = 50;

        saveVouchersFromLocalDate(VoucherType.FIXED, 2020, 12, 1, countForCondition);
        saveVouchersFromLocalDate(VoucherType.FIXED, 2021, 1, 1, countNotMatchCondition);

        //then & when
        mockMvc.perform(get("/api/v1/vouchers")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
            )

            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.*").exists())
            .andExpect(jsonPath("$.length()").value(countForCondition))
            .andExpect(jsonPath("$[*].createdAt")
                .value(everyItem(
                    is(both(greaterThan(startDate.toString())).and(lessThan(endDate.toString()))))))
            .andExpect(jsonPath("$[*].voucherId").exists())
            .andExpect(jsonPath("$[*].createdAt").exists())
            .andExpect(jsonPath("$[*].amount").exists())
            .andDo(print());
    }

    @DisplayName("findAll Voucher 테스트 - startDate 와 endDate 사이와 VoucherType 에 맞아야만 조회해온다 ")
    @Test
    void findAllVouchersBetweenStartDateAndEndDateAndVoucherTypeSuccessTest() throws Exception {
        //given
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 0, 0);

        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 0, 0);

        VoucherType expectVoucherType = VoucherType.PERCENT;

        int countForCondition1 = 10;
        int countForCondition2 = 10;
        int countForCondition3 = 10;

        int countNotMatchCondition = 20;

        int expectCount = countForCondition1 + countForCondition2 + countForCondition3;

        saveVouchersFromLocalDate(expectVoucherType, 2020, 12, 1, countForCondition1);
        saveVouchersFromLocalDate(expectVoucherType, 2020, 5, 5, countForCondition2);
        saveVouchersFromLocalDate(expectVoucherType, 2020, 1, 2, countForCondition3);

        saveVouchersFromLocalDate(expectVoucherType, 2021, 6, 1, countNotMatchCondition);
        saveVouchersFromLocalDate(VoucherType.FIXED, 2020, 12, 1, countNotMatchCondition);
        saveVouchersFromLocalDate(VoucherType.FIXED, 2021, 3, 1, countNotMatchCondition);
        saveVouchersFromLocalDate(VoucherType.FIXED, 2022, 1, 1, countNotMatchCondition);

        //then & when
        mockMvc.perform(get("/api/v1/vouchers")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .param("voucherType", expectVoucherType.getType())
            )

            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.*").exists())
            .andExpect(jsonPath("$.length()").value(expectCount))
            .andExpect(jsonPath("$[*].createdAt")
                .value(everyItem(
                    is(both(greaterThan(startDate.toString())).and(lessThan(endDate.toString()))))))
            .andExpect(jsonPath("$[*].voucherId").exists())
            .andExpect(
                jsonPath("$[*].voucherType").value(everyItem(equalTo(expectVoucherType.getType()))))
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


    private List<Voucher> createFixedVouchers(int saveCount) {
        return IntStream.range(0, saveCount)
            .mapToObj(value -> {
                FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),
                    100L);
                return voucherJdbcRepository.insert(fixedAmountVoucher);
            }).collect(Collectors.toList());
    }

    private List<Voucher> createPercentVouchers(int saveCount) {
        return IntStream.range(0, saveCount)
            .mapToObj(value -> {
                PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(
                    UUID.randomUUID(),
                    50L);
                return voucherJdbcRepository.insert(percentDiscountVoucher);
            }).collect(Collectors.toList());
    }


    private void saveVouchersFromLocalDate(VoucherType voucherType, int year, int month,
        int dayOfMonth, int count) {
        IntStream.range(0, count).forEach(value -> {

            Voucher voucher = VoucherFactory.toVoucher(voucherType, UUID.randomUUID(), 100L,
                LocalDateTime.of(year, month, dayOfMonth, 0, 0));

            voucherJdbcRepository.insert(voucher);
        });
    }

}