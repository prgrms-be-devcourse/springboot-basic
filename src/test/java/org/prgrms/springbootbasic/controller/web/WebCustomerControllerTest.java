package org.prgrms.springbootbasic.controller.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = WebCustomerController.class)
class WebCustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    @DisplayName("전체 손님 페이지 조회")
    void testViewAllVouchers() throws Exception {
        //given
        given(customerService.findAllCustomers())
            .willReturn(List.of(
                new Customer(UUID.randomUUID(), "test1", "test1@gmail.com"),
                new Customer(UUID.randomUUID(), "test2", "test2@gmail.com")
            ));

        //when
        //then
        mockMvc.perform(get("/customers"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"));
    }

    @Test
    @DisplayName("손님 상세 페이지 조회")
    void testViewCustomer() throws Exception {
        //given
        var customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        given(customerService.findCustomer(customer.getCustomerId()))
            .willReturn(customer);

        //when
        //then
        mockMvc.perform(get("/customers/" + customer.getCustomerId().toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"));
    }

    @Test
    @DisplayName("회원 등록 폼 페이지 조회")
    void testViewRegisterForm() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/customers/new"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"));
    }

    @Test
    @DisplayName("회원 등록 요청")
    void testRegisterCustomer() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/customers/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "test")
                .param("email", "test@gmail.com"))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().string(HttpHeaders.LOCATION, "/customers"));
    }

    @Test
    @DisplayName("회원 삭제 요청")
    void testDeleteCustomer() throws Exception {
        //given
        var customerId = UUID.randomUUID();
        given(customerService.deleteCustomer(customerId)).willReturn(customerId);

        //when
        //then
        mockMvc.perform(post("/customers/" + customerId.toString() + "/delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().string(HttpHeaders.LOCATION, "/customers"));
    }
}