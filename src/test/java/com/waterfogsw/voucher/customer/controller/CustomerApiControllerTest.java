package com.waterfogsw.voucher.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.waterfogsw.voucher.customer.model.Customer;
import com.waterfogsw.voucher.customer.service.CustomerService;
import com.waterfogsw.voucher.global.ControllerAdvice;
import com.waterfogsw.voucher.voucher.exception.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerApiControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String URL = "/api/v1/customers";

    @Mock
    private CustomerService service;

    @InjectMocks
    private CustomerApiController controller;

    private MockMvc mockMvc;

    @BeforeAll
    public static void beforeAll() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(ControllerAdvice.class)
                .build();
    }

    @Nested
    @DisplayName("addCustomer 메서드는")
    class Describe_addCustomer {

        @Nested
        @DisplayName("name 이 존재하지 않으면")
        class Context_without_name {

            @Test
            @DisplayName("BadRequest 응답을 한다")
            void It_BadRequest() throws Exception {
                final var request = post(URL);
                final var resultAction = mockMvc.perform(request);
                resultAction.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("name이 존재하면")
        class Context_with_exist_name {

            @Test
            @DisplayName("ok 응답을 반환한다")
            void It_ok() throws Exception {
                final Map<String, String> postRequest = new HashMap<>();
                postRequest.put("name", "hello");

                final String content = objectMapper.writeValueAsString(postRequest);
                final var request = post(URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);

                final var resultAction = mockMvc.perform(request);
                resultAction.andExpect(status().isOk());
            }
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {

        @Nested
        @DisplayName("호출되면")
        class Context_with_call {

            @Test
            @DisplayName("저장된 모든 고객 정보를 응답한다")
            void It_response_dto() throws Exception {
                final var customer1 = new Customer("aa1");
                final var customer2 = new Customer("aa2");
                final var customer3 = new Customer("aa3");
                final List<Customer> customers = new ArrayList<>(List.of(customer1, customer2, customer3));

                when(service.findAllCustomer()).thenReturn(customers);

                final var request = get(URL);
                final var resultActions = mockMvc.perform(request);
                resultActions.andExpect(status().isOk());

                final var response = resultActions.andReturn().getResponse().getContentAsString();
                final var expectedResponse = MessageFormat.format("[{0},{1},{2}]",
                        objectMapper.writeValueAsString(customer1),
                        objectMapper.writeValueAsString(customer2),
                        objectMapper.writeValueAsString(customer3));

                assertThat(response, is(expectedResponse));
            }
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {

        @Nested
        @DisplayName("id 값이 0 이하이면")
        class Context_with_below_0 {

            @ParameterizedTest
            @ValueSource(longs = {-1, 0})
            @DisplayName("badrequest 를 응답한다")
            void It_response_BadRequest(long id) throws Exception {
                final var request = get(URL + "/" + id);
                final var response = mockMvc.perform(request);
                response.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("해당 id 값이 존재하지 않으면")
        class Context_with_over_0 {

            @ParameterizedTest
            @ValueSource(longs = {1, 100})
            @DisplayName("해당 엔티티 정보를 응답한다")
            void It_response_BadRequest(long id) throws Exception {
                final var customer = new Customer(1L, "ddd");

                when(service.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

                final var request = get(URL + "/" + id);
                final var response = mockMvc.perform(request);

                response.andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("해당 id 값이 존재하면")
        class Context_with_exist {

            @ParameterizedTest
            @ValueSource(longs = {1, 100})
            @DisplayName("해당 엔티티 정보를 응답한다")
            void It_response_BadRequest(long id) throws Exception {
                final var customer = new Customer(1L, "ddd");

                when(service.findById(anyLong())).thenReturn(customer);

                final var request = get(URL + "/" + id);
                final var response = mockMvc.perform(request);

                response.andExpect(status().isOk());
                final var expectedResponse = MessageFormat.format("{0}", objectMapper.writeValueAsString(customer));
                assertThat(response.andReturn().getResponse().getContentAsString(), is(expectedResponse));
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메서드는")
    class Describe_deleteById {

        @Nested
        @DisplayName("id 값이 0 이하이면")
        class Context_with_below_0 {

            @ParameterizedTest
            @ValueSource(longs = {-1, 0})
            @DisplayName("BadRequest 를 응답한다")
            void It_response_BadRequest(long id) throws Exception {
                final var request = delete(URL + "/" + id);
                final var response = mockMvc.perform(request);
                response.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("해당 id 값이 존재하지 않으면")
        class Context_with_over_0 {

            @ParameterizedTest
            @ValueSource(longs = {1, 100})
            @DisplayName("해당 엔티티 정보를 응답한다")
            void It_response_BadRequest(long id) throws Exception {
                final var customer = new Customer(1L, "ddd");

                doThrow(ResourceNotFoundException.class).when(service).deleteById(anyLong());

                final var request = delete(URL + "/" + id);
                final var response = mockMvc.perform(request);

                response.andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("해당 id 값이 존재하면")
        class Context_with_exist {

            @ParameterizedTest
            @ValueSource(longs = {1, 100})
            @DisplayName("해당 엔티티 를 삭제한다")
            void It_response_BadRequest(long id) throws Exception {
                final var request = delete(URL + "/" + id);
                final var response = mockMvc.perform(request);
                verify(service).deleteById(anyLong());
            }
        }
    }
}