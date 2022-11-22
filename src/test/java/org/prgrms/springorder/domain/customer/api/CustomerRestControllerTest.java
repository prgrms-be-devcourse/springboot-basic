package org.prgrms.springorder.domain.customer.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.hamcrest.core.AllOf;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.customer.repository.CustomerJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class CustomerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerJdbcRepository customerJdbcRepository;

    @MockBean
    private CustomerController customerController;

    @DisplayName("findAll Customer 테스트 - 저장된 모든 BlockCustomer 가 XML 형식으로 반환된다 ")
    @Test
    void findAllCustomersXmlSuccessTest() throws Exception {
        //given
        int saveCount = 5;
        List<Customer> save = saveAll(saveCount);

        //then & when

        mockMvc.perform(get("/api/v1/customers")
                .accept(MediaType.APPLICATION_XML_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_XML_VALUE))
            .andExpect(xpath("list").exists())
            .andExpect(xpath("list").nodeCount(1))
            .andExpect(xpath("/list/org.prgrms.springorder.domain.customer.model.Customer").exists())
            .andExpect(xpath("/list/org.prgrms.springorder.domain.customer.model.Customer").nodeCount(saveCount))
            .andDo(print());
    }

    @DisplayName("findAll Customer 테스트 - 저장된 모든 BlockCustomer 가 JSON 형식으로 반환된다 ")
    @Test
    void findAllCustomersJsonSuccessTest() throws Exception {
        //given
        int saveCount = 5;
        List<Customer> save = saveAll(saveCount);

        //then & when

        mockMvc.perform(get("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                )

            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.*").exists())
            .andExpect(jsonPath("$.length()").value(saveCount))
            .andDo(print());
    }

    private List<Customer> saveAll(int saveCount) {

        String name = "name";
        String email = "email@gmail.com";
        return IntStream.range(0, saveCount)
            .mapToObj(value -> {
                Customer customer = new Customer(UUID.randomUUID(), name + value, value + email);
                return customerJdbcRepository.insert(customer);

            }).collect(Collectors.toList());
    }

//    private List<BlockCustomer> save(int saveCount) {
//        String name = "name";
//        String email = "email@gmail.com";
//        return IntStream.range(0, saveCount)
//            .mapToObj(value -> {
//                Customer customer = new Customer(UUID.randomUUID(), name + value, value + email);
//                customerJdbcRepository.insert(customer);
//                BlockCustomer blockCustomer = new BlockCustomer(UUID.randomUUID(),
//                    customer.getCustomerId(), LocalDateTime.now());
//                return blockCustomerJdbcRepository.insert(blockCustomer);
//            }).collect(Collectors.toList());
//    }
}