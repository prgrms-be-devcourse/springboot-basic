package kdt.vouchermanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import kdt.vouchermanagement.domain.customer.controller.CustomerController;
import kdt.vouchermanagement.domain.customer.domain.Customer;
import kdt.vouchermanagement.domain.customer.dto.CustomerCreateRequest;
import kdt.vouchermanagement.domain.customer.dto.CustomerResponse;
import kdt.vouchermanagement.domain.customer.entity.CustomerEntity;
import kdt.vouchermanagement.domain.customer.service.CustomerService;
import kdt.vouchermanagement.global.advice.ControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(ControllerAdvice.class)
                .build();
        objectMapper = new ObjectMapper();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void customer_등록_요청이_들어올_때_name이_blank라면_BAD_REQUEST를_반환한다(String name) throws Exception {
        //given
        String url = "/api/v1/customers";
        String message = "공백이 입력되었습니다.";
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(name);

        //when
        ResultActions resultActions = createCustomerPerform(url, customerCreateRequest);

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.data").value(message));
    }

    @Test
    void customer_등록_요청이_들어올_때_IllegalStateException이_넘어온다면_INTERNAL_SERVER_ERROR를_반환한다() throws Exception {
        //given
        String url = "/api/v1/customers";
        doThrow(new IllegalStateException()).when(customerService).saveCustomer(any(Customer.class));
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest("test");

        //when
        ResultActions resultActions = createCustomerPerform(url, customerCreateRequest);

        //then
        resultActions.andExpect(status().isInternalServerError());
    }

    @Test
    void customer_등록_요청이_들어올_때_정상적인_요청_데이터라면_customer를_저장한다() throws Exception {
        //given
        String url = "/api/v1/customers";
        doNothing().when(customerService).saveCustomer(any(Customer.class));
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest("test");

        //when
        ResultActions resultActions = createCustomerPerform(url, customerCreateRequest);

        //then
        verify(customerService, times(1)).saveCustomer(any(Customer.class));
        resultActions.andExpect(status().isCreated());
    }

    @Test
    void customer_목록_조회_요청이_들어올_때_customer_목록을_리턴한다() throws Exception {
        //given
        String url = "/api/v1/customers";
        Customer firstCustomer = CustomerEntity.of(
                1L,
                "first test",
                LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0))
                .toDomain();
        Customer secondCustomer = CustomerEntity.of(
                        2L,
                        "second test",
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0))
                .toDomain();
        List<CustomerResponse> customerResponses = CustomerResponse.listOf(List.of(firstCustomer, secondCustomer));
        doReturn(customerResponses).when(customerService).findAllCustomers();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        verify(customerService, times(1)).findAllCustomers();
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(customerResponses.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(customerResponses.get(0).getName()))
                .andExpect(jsonPath("$[1].id").value(customerResponses.get(1).getId()))
                .andExpect(jsonPath("$[1].name").value(customerResponses.get(1).getName()));
    }

    @Test
    void customer_삭제_요청이_들어올_때_IllegalArgumentException이_넘어온다면_BAD_REQUEST를_반환한다() throws Exception {
        //given
        long id = 1L;
        String url = "/api/v1/customers/" + id;
        doThrow(new IllegalArgumentException()).when(customerService).deleteById(id);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(url));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void customer_삭제_요청이_들어올_때_IllegalStateException이_넘어온다면_INTERNAL_SERVER_ERROR를_반환한다() throws Exception {
        //given
        long id = 1L;
        String url = "/api/v1/customers/" + id;
        doThrow(new IllegalStateException()).when(customerService).deleteById(id);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(url));

        //then
        resultActions.andExpect(status().isInternalServerError());
    }

    @Test
    void customer_삭제_요청이_들어올_때_정상적인_요청_데이터라면_customer를_저장한다() throws Exception {
        //given
        long id = 1L;
        String url = "/api/v1/customers/" + id;
        doNothing().when(customerService).deleteById(id);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(url));

        //then
        verify(customerService, times(1)).deleteById(id);
        resultActions.andExpect(status().isNoContent());
    }

    private ResultActions createCustomerPerform(String url, CustomerCreateRequest customerCreateRequest) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerCreateRequest))
        );
    }
}
