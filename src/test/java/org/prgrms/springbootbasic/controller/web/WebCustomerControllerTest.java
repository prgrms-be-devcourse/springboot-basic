package org.prgrms.springbootbasic.controller.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = WebCustomerController.class)
class WebCustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    @DisplayName("손님 전체 조회 페이지")
    void viewAllVouchers() throws Exception {
        //given
        given(customerService.findAllCustomers())
            .willReturn(List.of(
                new Customer(UUID.randomUUID(), "test1", "test1@gmail.com"),
                new Customer(UUID.randomUUID(), "test2", "test2@gmail.com")
            ));

        //when
        //then
        mockMvc.perform(get("/customers"))
            .andExpect(status().isOk());
    }
}