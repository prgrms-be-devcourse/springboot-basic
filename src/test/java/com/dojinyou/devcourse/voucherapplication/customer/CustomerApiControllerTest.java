package com.dojinyou.devcourse.voucherapplication.customer;

import com.dojinyou.devcourse.voucherapplication.common.exception.NotFoundException;
import com.dojinyou.devcourse.voucherapplication.customer.domain.Customer;
import com.dojinyou.devcourse.voucherapplication.customer.dto.CustomerCreateRequest;
import com.dojinyou.devcourse.voucherapplication.customer.entity.CustomerEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerApiController.class)
@DisplayName("CustomerApiController 테스트")
public class CustomerApiControllerTest {
    private final String BASE_API_URL = "/api/v1/customers";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private final LocalDateTime testTime = LocalDateTime.of(2022, 5, 13,
                                                            10, 7, 0, 0);

    private final CustomerEntity customer1 = new CustomerEntity(1L, "testCustomer1", testTime, testTime);
    private final CustomerEntity customer2 = new CustomerEntity(2L, "testCustomer2",
                                                                testTime.plusDays(1), testTime.plusDays(1));
    private final List<CustomerEntity> allCustomers = List.of(customer1, customer2);


    @Nested
    @DisplayName("전체 고객 조회 시")
    public class 전체_고객_조회_시 {
        @Nested
        @DisplayName("정상 조회될 경우")
        public class 정상_조회될_경우 {
            @Test
            @DisplayName("고객이 담긴 리스트를 응답한다.")
            public void 고객이_담긴_리스트를_응답한다() throws Exception {
                List<Customer> returnObjects = allCustomers.stream()
                                                           .map(Customer::from)
                                                           .collect(Collectors.toList());
                when(customerService.findAll()).thenReturn(returnObjects);
                String returnJson = toJson(returnObjects.stream()
                                                        .map(Customer::toDefaultResponse)
                                                        .collect(Collectors.toList()));

                var response = mvc.perform(get(BASE_API_URL));

                response.andExpect(status().isOk())
                        .andExpect(content().json(returnJson));
                verify(customerService, atLeastOnce()).findAll();
            }

            @Test
            @DisplayName("조회될 데이터가 없을 경우 빈 리스트를 응답한다.")
            public void 조회될_데이터가_없을_경우_빈_리스트를_응답한다() throws Exception {
                when(customerService.findAll()).thenReturn(List.of());

                var response = mvc.perform(get(BASE_API_URL));

                response.andExpect(status().isOk())
                        .andExpect(content().json("[]"));
            }
        }
    }

    @Nested
    @DisplayName("고객 추가 시")
    public class 고객_추가_시 {
        @Nested
        @DisplayName("정상적인 데이터가 입력되는 경우")
        public class 정상적인_데이터가_입력되는_경우 {
            @Test
            @DisplayName("생성된 데이터와 201 Create로 응답한다.")
            public void 생성된_데이터와_201_Create로_응답한다() throws Exception {
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("name", customer1.getName());
                when(customerService.create(any(Customer.class))).thenReturn(Customer.from(customer1));
                String returnJson = toJson(Customer.from(customer1).toDefaultResponse());

                var response = mvc.perform(post(BASE_API_URL)
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(toJson(requestBody)));

                response.andExpect(status().isCreated())
                        .andExpect(content().json(returnJson));
            }
        }
    }

    @Nested
    @DisplayName("id를 이용한 고객 조회시")
    public class id를_이용한_고객_조회_시 {
        @Nested
        @DisplayName("정상 조회될 경우")
        public class 정상_조회될_경우 {
            @Test
            @DisplayName("해당 고객 데이터와 200 OK로 응답한다.")
            public void 해당_고객_데이터와_200_OK로_응답한다() throws Exception {
                long inputId = customer1.getId();
                when(customerService.findById(inputId)).thenReturn(Customer.from(customer1));
                String returnJson = toJson((Customer.from(customer1)).toDefaultResponse());

                var response = mvc.perform(get(BASE_API_URL + "/" + inputId));

                response.andExpect(status().isOk())
                        .andExpect(content().json(returnJson));
                verify(customerService, atLeastOnce()).findById(inputId);
            }
        }

        @Nested
        @DisplayName("입력된 id값을 가진 고객이 없는 경우")
        public class 입력된_id값을_가진_고객이_없는_경우 {
            @Test
            @DisplayName("Not Found로 응답한다.")
            public void Not_Found로_응답한다() throws Exception {
                long inputId = 10L;
                when(customerService.findById(inputId)).thenThrow(NotFoundException.class);

                var response = mvc.perform(get(BASE_API_URL + "/" + inputId));

                response.andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("비정상적인 id값을 입력할 경우")
        public class 비정상적인_id값을_입력할_경우 {
            @Test
            @DisplayName("Bad Request로 응답한다.")
            public void Bad_Request로_응답한다() throws Exception {
                String inputId = "invalidId";

                var response = mvc.perform(get(BASE_API_URL + "/" + inputId));

                response.andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("id를 이용한 고객 삭제 시")
    public class id를_이용한_고객_삭제_시 {
        @Nested
        @DisplayName("정상 삭제될 경우")
        public class 정상_삭제될_경우 {
            @Test
            @DisplayName("No Content로 응답한다.")
            public void No_Content로_응답한다() throws Exception {
                var response = mvc.perform(delete(BASE_API_URL + "/" + customer1.getId()));

                response.andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("비정상적인 id값을 입력할 경우")
        public class 비정상적인_id값을_입력할_경우 {
            @Test
            @DisplayName("Bad Request로 응답한다.")
            public void Bad_Request로_응답한다() throws Exception {
                String inputId = "invalidId";

                var response = mvc.perform(delete(BASE_API_URL + "/" + inputId));

                response.andExpect(status().isBadRequest());
            }
        }
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
    }
}