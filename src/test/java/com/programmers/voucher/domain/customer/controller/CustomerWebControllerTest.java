package com.programmers.voucher.domain.customer.controller;

import com.programmers.voucher.domain.customer.dto.CustomerDto;
import com.programmers.voucher.domain.customer.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.testutil.CustomerTestUtil.createCustomerDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerWebController.class)
class CustomerWebControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @Test
    @DisplayName("성공: customer 목록 화면")
    void findCustomers() throws Exception {
        //given
        CustomerDto customerA
                = createCustomerDto(UUID.randomUUID(), "customerA@gmail.com", "customerA", false);
        CustomerDto customerB
                = createCustomerDto(UUID.randomUUID(), "customerB@gmail.com", "customerB", false);
        List<CustomerDto> customerDtos = List.of(customerA, customerB);

        given(customerService.findCustomers()).willReturn(customerDtos);

        //when
        ResultActions resultActions = mvc.perform(get("/customers"));

        //then
        resultActions
                .andExpect(view().name("customers/customer-list"))
                .andExpect(model().attribute("customers", customerDtos));
    }

    @Test
    @DisplayName("성공: customer 생성 요청")
    void createCustomer() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();

        given(customerService.createCustomer(any(), any())).willReturn(customerId);

        //when
        ResultActions resultActions = mvc.perform(post("/customers/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("email=customer@gmail.com&name=customer"));

        //then
        resultActions
                .andExpect(redirectedUrl("/customers/" + customerId));
    }

    @Test
    @DisplayName("성공: customer 상세 조회 화면")
    void findCustomer() throws Exception {
        //given
        CustomerDto customer
                = createCustomerDto(UUID.randomUUID(), "customer@gmail.com", "customer", false);

        given(customerService.findCustomer(any())).willReturn(customer);

        //when
        ResultActions resultActions = mvc.perform(get("/customers/" + customer.getCustomerId()));

        //then
        resultActions
                .andExpect(view().name("customers/customer"))
                .andExpect(model().attribute("customer", customer));
    }

    @Test
    @DisplayName("성공: customer 업데이트 요청")
    void updateCustomer() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();

        //when
        ResultActions resultActions = mvc.perform(post("/customers/" + customerId)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("customerId=&name=updateName&banned=true"));

        //then
        resultActions
                .andExpect(redirectedUrl("/customers/" + customerId));
    }

    @Test
    @DisplayName("성공: customer 삭제 요청")
    void deleteCustomer() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();

        //when
        ResultActions resultActions = mvc.perform(post("/customers/" + customerId + "/delete"));

        //then
        resultActions
                .andExpect(redirectedUrl("/customers"));
    }
}