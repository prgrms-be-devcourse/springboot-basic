package org.prgrms.kdt.domain.customer.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;
import org.prgrms.kdt.domain.customer.service.CustomerService;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
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
import static org.prgrms.kdt.domain.customer.model.CustomerType.BLACK_LIST;
import static org.prgrms.kdt.domain.customer.model.CustomerType.NORMAL;
import static org.prgrms.kdt.domain.voucher.model.VoucherType.FIXED_AMOUNT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerService))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("고객 전체 조회 요청을 처리할 수 있다.")
    void customerList() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.now();
        Customer customerPark = new Customer(UUID.randomUUID(), "park", "a@naver.com", NORMAL, now, now);
        Customer customerKim = new Customer(UUID.randomUUID(),"kim" , "b@gmail.com", BLACK_LIST, now, now);
        List<Customer> savedCustomers = Arrays.asList(customerPark, customerKim);
        //when
        when(customerService.getAllCustomers()).thenReturn(savedCustomers);
        //then
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("customers", savedCustomers))
                .andExpect(model().attribute("voucherType", VoucherType.values()))
                .andExpect(view().name("customers/list"));
    }

    @Test
    @DisplayName("바우처 타입과 생성일자를 입력받아 특정 바우처를 소유한 고객 조회 요청을 처리할 수 있다.")
    void customerListOwnSpecificVoucher() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.now();
        Customer customerPark = new Customer(UUID.randomUUID(), "park", "a@naver.com", NORMAL, now, now);
        Customer customerKim = new Customer(UUID.randomUUID(),"kim" , "b@gmail.com", BLACK_LIST, now, now);
        List<Customer> findCustomers = Arrays.asList(customerPark, customerKim);
        //when
        when(customerService.getCustomersByVoucherTypeAndDate(any(), any())).thenReturn(findCustomers);
        //then
        mockMvc.perform(get("/customers/search")
                        .param("voucherType", FIXED_AMOUNT.toString())
                        .param("date", now.toLocalDate().toString()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("customers", findCustomers))
                .andExpect(model().attribute("voucherType", VoucherType.values()))
                .andExpect(view().name("customers/list"));
    }

    @Test
    @DisplayName("존재하는 고객 ID를 통해 해당 고객에 대한 상세 정보를 조회할 수 있다.")
    void customerDetails_customerExist() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.now();
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "park", "a@naver.com", NORMAL, now, now);
        //when
        when(customerService.getCustomerById(any())).thenReturn(Optional.of(customer));
        //then
        mockMvc.perform(get("/customers/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("customer", customer))
                .andExpect(model().attribute("customerType", CustomerType.values()))
                .andExpect(view().name("customers/detail"));
    }

    @Test
    @DisplayName("존재하지 않는 고객 ID를 통해 조회하는 요청을 처리할 수 있다.")
    void customerDetails_customerNotExist() throws Exception{
        //given
        UUID customerId = UUID.randomUUID();
        //when
        when(customerService.getCustomerById(any())).thenReturn(Optional.empty());
        //then
        mockMvc.perform(get("/customers/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("customerType", CustomerType.values()))
                .andExpect(view().name("customers/detail"));
    }

    @Test
    @DisplayName("고객 생성 페이지 요청을 처리할 수 있다.")
    void customerCreatePage() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(get("/customers/new"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("customerType", CustomerType.values()))
                .andExpect(view().name("customers/create"));
    }

    @Test
    @DisplayName("고객 생성 요청을 처리할 수 있다.")
    void customerCreate() throws Exception{
        //given
        String name = "park";
        String email = "park@naver.com";
        //when
        //then
        mockMvc.perform(post("/customers/new")
                        .param("customerType", NORMAL.toString())
                        .param("name", name)
                        .param("email", email))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));
    }

    @Test
    @DisplayName("고객 생성 요청 시 너무 긴 이름을 입력하면 모델에 에러를 담아준다.")
    void customerCreate_nameLength_fieldError() throws Exception{
        //given
        String name = "박우진박우진박우진박우진박우진박우진박우진박우진박우진박우진박우진박우진";
        String email = "par@naver.com";
        //when
        //then
        mockMvc.perform(post("/customers/new")
                        .param("customerType", NORMAL.toString())
                        .param("name", name)
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("createForm", "name"))
                .andExpect(view().name("/customers/create"));
    }

    @Test
    @DisplayName("고객 생성 요청 시 유효하지 않은 이메일을 입력하면 모델에 에러를 담아준다.")
    void customerCreate_emailNotValid_fieldError() throws Exception{
        //given
        String name = "박우진";
        String email = "gmail.com";
        //when
        //then
        mockMvc.perform(post("/customers/new")
                        .param("customerType", NORMAL.toString())
                        .param("name", name)
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("createForm", "email"))
                .andExpect(view().name("/customers/create"));
    }

    @Test
    @DisplayName("고객 생성 요청 시 이메일을 입력하지 않으면 모델에 에러를 담아준다.")
    void customerCreate_emailNull_fieldError() throws Exception{
        //given
        UUID customerId = UUID.randomUUID();
        String name = "park";
        String email = null;
        //when
        when(customerService.save(any())).thenReturn(customerId);
        //then
        mockMvc.perform(post("/customers/new")
                        .param("customerType", NORMAL.toString())
                        .param("name", name)
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("createForm", "email"))
                .andExpect(view().name("/customers/create"));
    }

    @Test
    @DisplayName("고객 생성 요청 시 이름을 입력하지 않으면 모델에 에러를 담아준다.")
    void customerCreate_nameNull_fieldError() throws Exception{
        //given
        UUID customerId = UUID.randomUUID();
        String name = null;
        String email = "park@naver.com";
        //when
        when(customerService.save(any())).thenReturn(customerId);
        //then
        mockMvc.perform(post("/customers/new")
                        .param("customerType", NORMAL.toString())
                        .param("name", name)
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("createForm", "name"))
                .andExpect(view().name("/customers/create"));
    }

    @Test
    @DisplayName("고객 수정 요청을 처리할 수 있다.")
    void customerModify() throws Exception{
        //given
        UUID customerId = UUID.randomUUID();
        CustomerType customerType = BLACK_LIST;
        String email = "park@naver.com";
        String name = "park";
        //when
        //then
        mockMvc.perform(put("/customers/{customerId}", customerId)
                        .param("customerType", customerType.toString())
                        .param("name", name)
                        .param("email", email))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));
    }

    @Test
    @DisplayName("고객 수정 요청 시 길이가 너무 길 경우 예외가 발생한다.")
    void customerModify_nameLength_exception() throws Exception{
        //given
        UUID customerId = UUID.randomUUID();
        CustomerType customerType = BLACK_LIST;
        String email = "park@naver.com";
        String name = "parkparkparkparkparkparkparkparkpark";
        //when
        //then
        mockMvc.perform(put("/customers/{customerId}", customerId)
                        .param("customerType", customerType.toString())
                        .param("name", name)
                        .param("email", email))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertThat(result.getResolvedException()
                        .getClass()
                        .isAssignableFrom(MethodArgumentNotValidException.class)).isTrue());
    }

    @Test
    @DisplayName("고객 수정 요청 시 이메일이 유효하지 않을 경우 예외가 발생한다.")
    void customerModify_emailValid_exception() throws Exception{
        //given
        UUID customerId = UUID.randomUUID();
        CustomerType customerType = BLACK_LIST;
        String email = "park";
        String name = "park";
        //when
        //then
        mockMvc.perform(put("/customers/{customerId}", customerId)
                        .param("customerType", customerType.toString())
                        .param("name", name)
                        .param("email", email))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertThat(result.getResolvedException()
                        .getClass()
                        .isAssignableFrom(MethodArgumentNotValidException.class)).isTrue());
    }

    @Test
    @DisplayName("고객 수정 요청 시 이메일에 공백만 입력될 경우 예외가 발생한다.")
    void customerModify_emailBlank_exception() throws Exception{
        //given
        UUID customerId = UUID.randomUUID();
        CustomerType customerType = BLACK_LIST;
        String email = "   ";
        String name = "park";
        //when
        //then
        mockMvc.perform(put("/customers/{customerId}", customerId)
                        .param("customerType", customerType.toString())
                        .param("name", name)
                        .param("email", email))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertThat(result.getResolvedException()
                        .getClass()
                        .isAssignableFrom(MethodArgumentNotValidException.class)).isTrue());
    }

    @Test
    @DisplayName("고객 수정 요청 시 고객 타입이 입력되지 않을 경우 예외가 발생한다.")
    void customerModify_customerTypeNull_exception() throws Exception{
        //given
        UUID customerId = UUID.randomUUID();
        CustomerType customerType = null;
        String email = "park@naver.com";
        String name = "park";
        //when
        //then
        mockMvc.perform(put("/customers/{customerId}", customerId)
                        .param("customerType", String.valueOf(customerType))
                        .param("name", name)
                        .param("email", email))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertThat(result.getResolvedException()
                        .getClass()
                        .isAssignableFrom(MethodArgumentNotValidException.class)).isTrue());
    }

    @Test
    @DisplayName("고객 삭제 요청을 처리할 수 있다.")
    void customerRemove() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();
        //when
        //then
        mockMvc.perform(delete("/customers/{customerId}", customerId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));
    }
}