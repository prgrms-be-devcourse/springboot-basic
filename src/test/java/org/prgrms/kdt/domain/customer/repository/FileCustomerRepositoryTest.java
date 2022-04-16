package org.prgrms.kdt.domain.customer.repository;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.domain.customer.model.Customer;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileCustomerRepositoryTest {

    CustomerRepository customerRepository = new FileCustomerRepository();
    String csvPath = "src\\test\\resources";
    String fileName = "customer_blacklist_sample.csv";

    /**
     * 리플렉션을 통해 csvPath, fileName을 설정해준다.
     */
    @BeforeEach
    public void setCsvFile() {
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
        Customer customerPark = new Customer.Builder(UUID.randomUUID(), "d@naver.com").name("park").build();
        Customer customerKim = new Customer.Builder(UUID.randomUUID(), "a@gmail.com").name("kim").build();
        customerRepository.save(customerPark);
        customerRepository.save(customerKim);
        //when
        List<Customer> customers = customerRepository.findAll();
        //then
        assertThat(customers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("고객 저장 시 파일을 읽지 못할 경우 예외가 발생한다.")
    public void exception_saveCustomer(){
        //given
        Customer customer = new Customer.Builder(UUID.randomUUID(), "a@naver.com").name("park").build();
        ReflectionTestUtils.setField(customerRepository, "csvPath", "");
        //when
        //then
        assertThatThrownBy(() -> customerRepository.save(customer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("파일의 경로 혹은 이름을 확인해주세요.");
    }

    @Test
    @DisplayName("고객 전체 조회시 파일을 읽지 못할 경우 예외가 발생한다.")
    public void exception_findAllCustomer(){
        //given
        ReflectionTestUtils.setField(customerRepository, "csvPath", "");
        //when
        //then
        assertThatThrownBy(() -> customerRepository.findAll())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("파일의 경로 혹은 이름을 확인해주세요.");
    }
}