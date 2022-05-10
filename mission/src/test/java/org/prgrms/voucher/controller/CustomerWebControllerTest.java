package org.prgrms.voucher.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.voucher.controller.web.CustomerWebController;
import org.prgrms.voucher.models.*;
import org.prgrms.voucher.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerWebController.class)
public class CustomerWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Nested
    @DisplayName("createCustomer 메서드는")
    class DescribeCreateVoucher {

        @Nested
        @DisplayName("이름이 없는 요청이 들어오면")
        class ContextReceiveNullName {

            RequestBuilder request = MockMvcRequestBuilders
                    .post("/api/v1/customers")
                    .accept(MediaType.APPLICATION_JSON)
                    .content("{\"customerName\":}")
                    .contentType(MediaType.APPLICATION_JSON);

            @Test
            @DisplayName("잘못된 요청 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("문자열 타입의 데이터를 받으면")
        class ContextStringName {

            RequestBuilder request = MockMvcRequestBuilders
                    .post("/api/v1/customers")
                    .accept(MediaType.APPLICATION_JSON)
                    .content("{\"customerName\":\"jack\"}")
                    .contentType(MediaType.APPLICATION_JSON);

            @Test
            @DisplayName("서비스의 create() 메서드를 호출하고 Success를 반환한다.")
            void itSaveVoucherReturnSuccessResponse() throws Exception {

                mockMvc.perform(request)
                        .andExpect(status().isOk());

                verify(customerService).create(any(Customer.class));
            }
        }
    }

    @Nested
    @DisplayName("findCustomers 메서드는")
    class DescribeFindCustomers {

        @Nested
        @DisplayName("get요청을 받으면")
        class ContextGetRequest {

            @Test
            @DisplayName("고객 리스트를 json형태로 반환한다.")
            void itReturnVouchersByJsonType() throws Exception {

                LocalDateTime time = LocalDateTime.parse("2022-05-10T14:07:41.477051");

                Customer firstCustomer = new Customer(1L, "jack", time, time);
                Customer secondCustomer = new Customer(2L, "mick", time, time);

                List<Customer> customers = List.of(firstCustomer, secondCustomer);

                when(customerService.getCustomers()).thenReturn(customers);

                mockMvc.perform(get("/api/v1/customers"))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$[0].customerId").value("1"))
                        .andExpect(jsonPath("$[0].customerName").value("jack"))
                        .andExpect(jsonPath("$[1].customerId").value("2"))
                        .andExpect(jsonPath("$[1].customerName").value("mick"));
            }
        }

        @Nested
        @DisplayName("get요청과 해당하지 않는 날짜 1000/11/11 이전 1000/10/10 이후 범위를 받으면")
        class ContextGetRequestWithParamWrongDateTime {

            List<Customer> customers = List.of();

            @Test
            @DisplayName("비어있는 리스트를 반환한다.")
            void itReturnEmptyListByJsonType() throws Exception {

                when(customerService.getCustomersByTerm(any(), any())).thenReturn(customers);

                mockMvc.perform(get("/api/v1/customers?before=1000-11-11&after=1000-10-10"))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.size()").value(0));
            }
        }

        @Nested
        @DisplayName("get요청과 해당하는 날짜2022-09-15 이전 2022-03-18 이후 범위를 받으면")
        class ContextGetRequestWithParamDateTime {

            LocalDateTime time = LocalDateTime.parse("2022-05-10T14:07:41.477051");

            Customer firstCustomer = new Customer(1L, "jack", time, time);
            Customer secondCustomer = new Customer(2L, "mick", time, time);

            List<Customer> customers = List.of(firstCustomer, secondCustomer);

            @Test
            @DisplayName("해당하는 고객 리스트를 반환한다.")
            void itReturnEmptyListByJsonType() throws Exception {

                when(customerService.getCustomersByTerm(any(), any())).thenReturn(customers);

                mockMvc.perform(get("/api/v1/customers?before=2022-09-09&after=2022-03-18"))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$[0].customerId").value("1"))
                        .andExpect(jsonPath("$[0].customerName").value("jack"))
                        .andExpect(jsonPath("$[1].customerId").value("2"))
                        .andExpect(jsonPath("$[1].customerName").value("mick"));
            }
        }
    }

    @Nested
    @DisplayName("findVoucherById 메서드는")
    class DescribeFindById {

        @Nested
        @DisplayName("존재하지 않는 id가 인자로 들어왔을때")
        class ContextReceiveNullId {

            Long wrongId = -1L;

            @Test
            @DisplayName("잘못된 요청 응답을 반환한다.")
            void itReturnBadRequestResponse() throws Exception {

                when(customerService.getCustomerById(wrongId)).thenThrow(IllegalArgumentException.class);

                mockMvc.perform(get("/api/v1/customers/-1"))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("Id를 인자로 받으면")
        class ContextReceiveParamVoucherId {

            @Test
            @DisplayName("해당 id의 바우처를 json 형태로 반환한다.")
            void itReturnCustomerByJsonType() throws Exception {

                Customer firstCustomer = new Customer(1L, "jack", LocalDateTime.now(), LocalDateTime.now());
                Long paramId = 1L;

                when(customerService.getCustomerById(paramId)).thenReturn(firstCustomer);

                mockMvc.perform(get("/api/v1/customers/1"))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.customerId").value("1"))
                        .andExpect(jsonPath("$.customerName").value("jack"));
            }
        }
    }

    @Nested
    @DisplayName("deleteVoucherById 메서드는")
    class DescribeDeleteById {

        @Nested
        @DisplayName("존재하지 않는 ID를 삭제 요청 받으면")
        class ContextRequestWrongId {

            Long wrongId = -1L;

            @Test
            @DisplayName("잘못된 요청 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                doThrow(IllegalArgumentException.class).when(customerService).deleteCustomerById(wrongId);

                mockMvc.perform(delete("/api/v1/customers/-1"))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("존재하는 ID를 ")
        class ContextRequestId {

            @Test
            @DisplayName("서비스의 deleteVoucherById메서드를 호출하고 성공 응답을 반환한다.")
            void itDeleteCustomerAndReturnOk() throws Exception {

                mockMvc.perform(delete("/api/v1/customers/1"))
                        .andExpect(status().isOk());

                verify(customerService).deleteCustomerById(any());
            }
        }
    }
}