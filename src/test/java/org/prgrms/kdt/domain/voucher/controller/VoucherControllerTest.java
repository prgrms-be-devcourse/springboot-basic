package org.prgrms.kdt.domain.voucher.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;
import org.prgrms.kdt.domain.customer.service.CustomerService;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.request.VoucherSearchRequest;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.prgrms.kdt.domain.voucher.model.VoucherType.FIXED_AMOUNT;
import static org.prgrms.kdt.domain.voucher.model.VoucherType.PERCENT_DISCOUNT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(VoucherController.class)
class VoucherControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private VoucherService voucherService;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new VoucherController(voucherService, customerService))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("바우처 전체 조회 요청을 처리할 수 있다.")
    void voucherList() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        List<Voucher> savedVouchers = Arrays.asList(
                new Voucher(UUID.randomUUID(), FIXED_AMOUNT, 10000L, now, now),
                new Voucher(UUID.randomUUID(), PERCENT_DISCOUNT, 10L, now, now));
        //when
        when(voucherService.getAllVouchers(any())).thenReturn(savedVouchers);
        //then
        mockMvc.perform(get("/vouchers"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("vouchers", savedVouchers))
                .andExpect(view().name("vouchers/list"));
    }

    @Test
    @DisplayName("고객의 이메일을 입력받아 해당 고객이 보유한 바우처를 조회 요청을 처리할 수 있다.")
    void voucherListOwnCustomer() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        String email = "abc@naver.com";
        Customer customer = new Customer(UUID.randomUUID(), "park", email, CustomerType.NORMAL, now, now);
        List<Voucher> savedVouchers = Arrays.asList(
                new Voucher(UUID.randomUUID(), FIXED_AMOUNT, 10000L, now, now),
                new Voucher(UUID.randomUUID(), PERCENT_DISCOUNT, 10L, now, now));
        //when
        when(customerService.getCustomerByEmail(any())).thenReturn(Optional.of(customer));
        when(voucherService.getVouchersByCustomerId(any())).thenReturn(savedVouchers);
        //then
        mockMvc.perform(get("/vouchers/search").param("email", email))
                .andExpect(status().isOk())
                .andExpect(model().attribute("vouchers", savedVouchers))
                .andExpect(view().name("vouchers/list"));
    }

    @Test
    @DisplayName("바우처 ID를 통해 바우처에 대한 상세 정보 조회 요청을 처리할 수 있다.")
    void voucherDetails() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.now();
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, FIXED_AMOUNT, 10000L, now, now);
        //when
        when(voucherService.getVoucherById(any())).thenReturn(Optional.of(voucher));
        //then
        mockMvc.perform(get("/vouchers/{voucherId}", voucherId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("voucher", voucher))
                .andExpect(model().attribute("voucherType", VoucherType.values()))
                .andExpect(view().name("vouchers/detail"));
    }

    @Test
    @DisplayName("바우처 생성 페이지 요청을 처리할 수 있다.")
    void voucherCreatePage() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/vouchers/new"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("voucherType", VoucherType.values()))
                .andExpect(view().name("vouchers/create"));
    }

    @Test
    @DisplayName("바우처 타입과 할인 값을 받아 새로운 바우처 생성 요청을 처리할 수 있다.")
    void voucherCreate() throws Exception {
        //given
        VoucherType voucherType = FIXED_AMOUNT;
        long discountValue = 100L;
        //when
        //then
        mockMvc.perform(post("/vouchers/new")
                        .param("voucherType", String.valueOf(voucherType))
                        .param("discountValue", String.valueOf(discountValue)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vouchers"));
    }

    @Test
    @DisplayName("바우처 생성 요청에서 입력받은 할인값이 음수일 경우 예외가 발생한다.")
    void voucherCreate_discountValueNotPositive_exception() throws Exception {
        //given
        VoucherType voucherType = FIXED_AMOUNT;
        long discountValue = -100;
        //when
        //then
        mockMvc.perform(post("/vouchers/new")
                        .param("voucherType", String.valueOf(voucherType))
                        .param("discountValue", String.valueOf(discountValue)))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertThat(result.getResolvedException()
                        .getClass()
                        .isAssignableFrom(MethodArgumentNotValidException.class)).isTrue());
    }

    @Test
    @DisplayName("바우처 생성 요청에서 입력받은 바우처 타입이 없을경우 예외가 발생한다.")
    void voucherCreate_voucherTypeNull_exception() throws Exception {
        //given
        VoucherType voucherType = null;
        long discountValue = 100;
        //when
        //then
        mockMvc.perform(post("/vouchers/new")
                        .param("voucherType", String.valueOf(voucherType))
                        .param("discountValue", String.valueOf(discountValue)))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertThat(result.getResolvedException()
                        .getClass()
                        .isAssignableFrom(MethodArgumentNotValidException.class)).isTrue());
    }

    @Test
    @DisplayName("바우처 타입과 할인 값을 받아 바우처 수정 요청을 처리할 수 있다.")
    void voucherModify() throws Exception{
        //given
        VoucherType voucherType = FIXED_AMOUNT;
        long discountValue = 100L;
        UUID voucherId = UUID.randomUUID();
        //when
        //then
        mockMvc.perform(put("/vouchers/{voucherId}", voucherId)
                        .param("voucherType", String.valueOf(voucherType))
                        .param("discountValue", String.valueOf(discountValue)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vouchers"));
    }

    @Test
    @DisplayName("바우처 수정 요청에서 입력받은 할인값이 음수일 경우 예외가 발생한다.")
    void voucherModify_discountValueNotPositive_exception() throws Exception{
        //given
        VoucherType voucherType = FIXED_AMOUNT;
        long discountValue = -100L;
        UUID voucherId = UUID.randomUUID();
        //when
        //then
        mockMvc.perform(put("/vouchers/{voucherId}", voucherId)
                        .param("voucherType", String.valueOf(voucherType))
                        .param("discountValue", String.valueOf(discountValue)))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertThat(result.getResolvedException()
                        .getClass()
                        .isAssignableFrom(MethodArgumentNotValidException.class)).isTrue());
    }

    @Test
    @DisplayName("바우처 수정 요청에서 입력받은 바우처 타입이 null일 경우 예외가 발생한다.")
    void voucherModify_voucherTypeNull_exception() throws Exception{
        //given
        VoucherType voucherType = null;
        long discountValue = 100L;
        UUID voucherId = UUID.randomUUID();
        //when
        //then
        mockMvc.perform(put("/vouchers/{voucherId}", voucherId)
                        .param("voucherType", String.valueOf(voucherType))
                        .param("discountValue", String.valueOf(discountValue)))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertThat(result.getResolvedException()
                        .getClass()
                        .isAssignableFrom(MethodArgumentNotValidException.class)).isTrue());
    }

    @Test
    @DisplayName("바우처 ID를 통해 바우처 삭제요청을 처리할 수 있다.")
    void voucherRemove() throws Exception{
        //given
        UUID voucherId = UUID.randomUUID();
        //when
        //then
        mockMvc.perform(delete("/vouchers/{voucherId}", voucherId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vouchers"));
    }

    @Test
    @DisplayName("바우처 할당 페이지 요청을 처리할 수 있다.")
    void voucherAssignPage() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        List<Voucher> notAssignedVouchers = Arrays.asList(
                new Voucher(UUID.randomUUID(), FIXED_AMOUNT, 10000L, now, now),
                new Voucher(UUID.randomUUID(), PERCENT_DISCOUNT, 10L, now, now));
        //when
        when(voucherService.getVouchersNotAssignedToCustomer()).thenReturn(notAssignedVouchers);
        //then
        mockMvc.perform(get("/vouchers/assign"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("vouchers", notAssignedVouchers))
                .andExpect(view().name("vouchers/assign"));
    }

    @Test
    @DisplayName("바우처 할당 요청을 처리할 수 있다.")
    void voucherAssign() throws Exception{
        //given
        String email = "abc@naver.com";
        UUID voucherId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Customer customer = new Customer(UUID.randomUUID(), "park", email, CustomerType.NORMAL, now, now);
        //when
        when(customerService.getCustomerByEmail(any())).thenReturn(Optional.of(customer));
        //then
        mockMvc.perform(post("/vouchers/assign")
                        .param("email", email)
                        .param("voucherId", voucherId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}