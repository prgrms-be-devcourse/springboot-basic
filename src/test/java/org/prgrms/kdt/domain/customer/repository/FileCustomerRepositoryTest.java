package org.prgrms.kdt.domain.customer.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.customer.model.Customer;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

class FileCustomerRepositoryTest {

    private static final CustomerRepository customerRepository = new FileCustomerRepository();
    private static final String csvPath = "src\\test\\resources";
    private static final String fileName = "customer_blacklist_sample.csv";

    /**
     * 리플렉션을 통해 csvPath, fileName을 설정해준다.
     */
    @BeforeAll
    public static void setCsvFile() {
        ReflectionTestUtils.setField(customerRepository, "csvPath", csvPath);
        ReflectionTestUtils.setField(customerRepository, "fileName", fileName);
    }

    /**
     * 각 테스트 실행 후 csv에 있는 내용 전부를 지운다.
     */
    @AfterEach
    void deleteCsvContents() throws IOException {
        new FileOutputStream(csvPath+"\\"+fileName).close();
    }

    @Test
    @DisplayName("csv로 저장된 고객 목록을 불러올 수 있다.")
    public void findCustomers(){
        //given
        Customer customerPark = new Customer(UUID.randomUUID(), "park");
        Customer customerKim = new Customer(UUID.randomUUID(), "kim");
        customerRepository.save(customerPark);
        customerRepository.save(customerKim);
        //when
        List<Customer> customers = customerRepository.findAll();
        //then
        Assertions.assertThat(customers.size()).isEqualTo(2);
    }
}