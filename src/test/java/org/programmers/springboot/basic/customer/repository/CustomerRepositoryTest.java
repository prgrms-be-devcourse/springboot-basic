package org.programmers.springboot.basic.customer.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.programmers.springboot.basic.aop.LoggerAspect;
import org.programmers.springboot.basic.config.DataSourceConfig;
import org.programmers.springboot.basic.config.DataSourceProperties;
import org.programmers.springboot.basic.domain.customer.dto.CustomerRequestDto;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.entity.CustomerType;
import org.programmers.springboot.basic.domain.customer.mapper.CustomerEntityMapper;
import org.programmers.springboot.basic.domain.customer.mapper.CustomerEntityMapperImpl;
import org.programmers.springboot.basic.domain.customer.mapper.CustomerMapper;
import org.programmers.springboot.basic.domain.customer.repository.CustomerRepository;
import org.programmers.springboot.basic.domain.customer.repository.JdbcCustomerRepository;
import org.programmers.springboot.basic.domain.customer.service.validate.EmailValidator;
import org.programmers.springboot.basic.domain.voucher.repository.JdbcVoucherRepository;
import org.programmers.springboot.basic.util.generator.UUIDGenerator;
import org.programmers.springboot.basic.util.generator.UUIDRandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ComponentScan(
        basePackages = {"org.programmers.springboot.basic"},
        basePackageClasses = JdbcVoucherRepository.class
)
@ContextConfiguration(classes = {
        LoggerAspect.class,
        DataSourceConfig.class,
        DataSourceProperties.class,
        EmailValidator.class,
        UUIDRandomGenerator.class,
        CustomerEntityMapperImpl.class,
        JdbcCustomerRepository.class,
        CustomerMapper.class
})
@EnableConfigurationProperties(value = DataSourceProperties.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yaml"})
@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    @Autowired
    private UUIDGenerator uuidGenerator;

    private final String name;
    private final String email;

    public CustomerRepositoryTest() {
        this.name = "이유노";
        this.email = "ocard9611@naver.com";
    }

    @BeforeEach
    public void init() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("customer add and get (by Id And Email) 로직 성공 검증")
    public void successCreateCustomer() {

        CustomerRequestDto requestDto = customerEntityMapper.mapToRequestDto(name, email);
        Customer customer = customerEntityMapper.mapToEntityWithGenerator(requestDto, uuidGenerator);

        customerRepository.add(customer);
        UUID customerId = customer.getCustomerId();

        Customer expectedById = customerRepository.findById(customerId).orElse(null);
        assertThat(expectedById).isNotNull();
        assertThat(expectedById.getEmail()).isEqualTo(customer.getEmail());
        assertThat(expectedById.getName()).isEqualTo(customer.getName());

        Customer expectedByEmail = customerRepository.findByEmail(email).orElse(null);
        assertThat(expectedByEmail).isNotNull();
        assertThat(expectedByEmail.getName()).isEqualTo(customer.getName());
        assertThat(expectedByEmail.getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @Test
    @DisplayName("get blacklist 로직 검증 성공")
    public void successGetBlack() {

        CustomerRequestDto requestDtoA = customerEntityMapper.mapToRequestDto(name, email);
        Customer customerA = customerEntityMapper.mapToEntityWithGenerator(requestDtoA, uuidGenerator);
        customerA.setBlack();

        customerRepository.add(customerA);

        CustomerRequestDto requestDtoB = customerEntityMapper.mapToRequestDto("test", "test@gmail.com");
        Customer customerB = customerEntityMapper.mapToEntityWithGenerator(requestDtoB, uuidGenerator);
        customerB.setBlack();

        customerRepository.add(customerB);

        List<Customer> blacklist = customerRepository.getBlack();
        assertThat(blacklist.size()).isEqualTo(2);
        assertThat(blacklist.get(0).getCustomerType()).isEqualTo(CustomerType.BLACK);
        assertThat(blacklist.get(1).getCustomerType()).isEqualTo(CustomerType.BLACK);
    }

    @Test
    @DisplayName("getAll 로직 검증 성공")
    public void successGetAll() {

        List<Customer> list = customerRepository.getAll();
        assertThat(list.size()).isEqualTo(0);

        CustomerRequestDto requestDtoA = customerEntityMapper.mapToRequestDto(name, email);
        Customer customerA = customerEntityMapper.mapToEntityWithGenerator(requestDtoA, uuidGenerator);
        customerA.setBlack();

        customerRepository.add(customerA);

        CustomerRequestDto requestDtoB = customerEntityMapper.mapToRequestDto("test", "test@gmail.com");
        Customer customerB = customerEntityMapper.mapToEntityWithGenerator(requestDtoB, uuidGenerator);
        customerB.setBlack();

        customerRepository.add(customerB);

        CustomerRequestDto requestDtoC = customerEntityMapper.mapToRequestDto("dev", "dev@gmail.com");
        Customer customerC = customerEntityMapper.mapToEntityWithGenerator(requestDtoC, uuidGenerator);

        customerRepository.add(customerC);

        list = customerRepository.getAll();
        assertThat(list.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("update 로직 검증 성공")
    public void successUpdate() {

        CustomerRequestDto requestDtoA = customerEntityMapper.mapToRequestDto(name, email);
        Customer customerA = customerEntityMapper.mapToEntityWithGenerator(requestDtoA, uuidGenerator);

        customerRepository.add(customerA);
        Customer updated = customerRepository.findById(customerA.getCustomerId()).orElse(null);
        assertThat(updated).isNotNull();
        assertThat(updated.getCustomerId()).isEqualTo(customerA.getCustomerId());
        assertThat(updated.getEmail()).isEqualTo(customerA.getEmail());
        assertThat(updated.getName()).isEqualTo(customerA.getName());
        assertThat(updated.getCustomerType()).isEqualTo(CustomerType.NORMAL);

        customerA.setBlack();
        customerRepository.update(customerA);

        Customer expected = customerRepository.findById(customerA.getCustomerId()).orElse(null);
        assertThat(expected).isNotNull();
        assertThat(expected.getCustomerId()).isEqualTo(customerA.getCustomerId());
        assertThat(expected.getEmail()).isEqualTo(customerA.getEmail());
        assertThat(expected.getName()).isEqualTo(customerA.getName());
        assertThat(expected.getCustomerType()).isEqualTo(CustomerType.BLACK);
    }

    @Test
    @DisplayName("delete 로직 성공 검증")
    public void successDelete() {

        List<Customer> list = customerRepository.getAll();
        assertThat(list.size()).isEqualTo(0);

        CustomerRequestDto requestDto = customerEntityMapper.mapToRequestDto(name, email);
        Customer customer = customerEntityMapper.mapToEntityWithGenerator(requestDto, uuidGenerator);

        customerRepository.add(customer);
        list = customerRepository.getAll();
        assertThat(list.size()).isEqualTo(1);

        customerRepository.delete(customer);
        list = customerRepository.getAll();
        assertThat(list.size()).isEqualTo(0);
    }
}
