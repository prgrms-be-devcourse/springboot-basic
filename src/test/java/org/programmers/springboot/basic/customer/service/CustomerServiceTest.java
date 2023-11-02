package org.programmers.springboot.basic.customer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.programmers.springboot.basic.DataSourceConfig;
import org.programmers.springboot.basic.DataSourceProperty;
import org.programmers.springboot.basic.aop.LoggerAspect;
import org.programmers.springboot.basic.domain.customer.dto.CustomerRequestDto;
import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.entity.CustomerType;
import org.programmers.springboot.basic.domain.customer.exception.CustomerNotFoundException;
import org.programmers.springboot.basic.domain.customer.exception.DuplicateBlackCustomerException;
import org.programmers.springboot.basic.domain.customer.exception.DuplicateEmailException;
import org.programmers.springboot.basic.domain.customer.exception.IllegalEmailException;
import org.programmers.springboot.basic.domain.customer.mapper.CustomerEntityMapper;
import org.programmers.springboot.basic.domain.customer.mapper.CustomerEntityMapperImpl;
import org.programmers.springboot.basic.domain.customer.repository.CustomerRepository;
import org.programmers.springboot.basic.domain.customer.repository.JdbcCustomerRepository;
import org.programmers.springboot.basic.domain.customer.service.CustomerService;
import org.programmers.springboot.basic.domain.customer.service.validate.EmailValidator;
import org.programmers.springboot.basic.domain.voucher.service.VoucherService;
import org.programmers.springboot.basic.util.generator.UUIDGenerator;
import org.programmers.springboot.basic.util.generator.UUIDRandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(SpringExtension.class)
@ComponentScan(
        basePackages = {"org.programmers.springboot.basic"},
        basePackageClasses = VoucherService.class
)
@ContextConfiguration(classes = {
        LoggerAspect.class,
        EmailValidator.class,
        UUIDRandomGenerator.class,
        CustomerEntityMapperImpl.class,
        JdbcCustomerRepository.class,
        CustomerService.class,
        DataSourceProperty.class,
        DataSourceConfig.class
})
@TestPropertySource(locations = "classpath:application-test.properties")
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    @Autowired
    private UUIDGenerator uuidGenerator;

    private final String name;
    private final String email;

    public CustomerServiceTest() {
        this.name = "이유노";
        this.email = "ocard9611@naver.com";
    }

    @BeforeEach
    public void init() {
        customerService.deleteAll();
    }

    @Test
    @DisplayName("create 로직 성공 검증")
    public void successCreate() {

        CustomerRequestDto requestDto = customerEntityMapper.mapToRequestDto(name, email);
        customerService.create(requestDto);

        Customer customer = customerRepository.findByEmail(requestDto.getEmail()).orElse(null);
        assertThat(customer).isNotNull();
        assertThat(customer.getName()).isEqualTo(requestDto.getName());
        assertThat(customer.getCustomerType()).isEqualTo(CustomerType.NORMAL);
    }

    @Test
    @DisplayName("Duplicate email에 대한 로직 실패 검증")
    public void failCreateByDuplicateEmail() {

        CustomerRequestDto requestDtoA = customerEntityMapper.mapToRequestDto(name, email);
        customerService.create(requestDtoA);

        CustomerRequestDto requestDtoB = customerEntityMapper.mapToRequestDto("dev", email);
        assertThatThrownBy(() -> customerService.create(requestDtoB)).isInstanceOf(DuplicateEmailException.class)
                .hasMessageContaining("Duplicate Email already exists!");
    }

    @Test
    @DisplayName("getAll 로직 성공 검증")
    public void successFindAll() {

        List<CustomerResponseDto> list = customerService.findAll();
        assertThat(list.size()).isEqualTo(0);

        CustomerRequestDto requestDtoA = customerEntityMapper.mapToRequestDto(name, email);
        customerService.create(requestDtoA);

        CustomerRequestDto requestDtoB = customerEntityMapper.mapToRequestDto("dev", "dev@gmail.com");
        customerService.create(requestDtoB);

        list = customerService.findAll();
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("blacklist 조회 로직 성공 검증")
    public void successGetBlackList() {

        List<CustomerResponseDto> list = customerService.findByCustomerIsBlack();
        assertThat(list.size()).isEqualTo(0);

        CustomerRequestDto requestDtoA = customerEntityMapper.mapToRequestDto(name, email);
        Customer customerA = customerEntityMapper.mapToEntityWithGenerator(requestDtoA, uuidGenerator);
        customerA.setBlack();
        customerService.add(customerA);

        CustomerRequestDto requestDtoB = customerEntityMapper.mapToRequestDto("dev", "dev@gmail.com");
        Customer customerB = customerEntityMapper.mapToEntityWithGenerator(requestDtoB, uuidGenerator);
        customerB.setBlack();
        customerService.add(customerB);

        list = customerService.findByCustomerIsBlack();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getCustomerType()).isEqualTo(CustomerType.BLACK);
        assertThat(list.get(1).getCustomerType()).isEqualTo(CustomerType.BLACK);
    }

    @Test
    @DisplayName("Duplicate blacklist 추가 로직 실패 검증")
    public void failAddBlackCustomerByDuplicate() {

        CustomerRequestDto requestDtoA = customerEntityMapper.mapToRequestDto(name, email);
        Customer customerA = customerEntityMapper.mapToEntityWithGenerator(requestDtoA, uuidGenerator);
        customerService.add(customerA);

        UUID customerAId = customerA.getCustomerId();
        CustomerRequestDto requestDtoB = customerEntityMapper.mapToRequestDtoWithUUID(customerAId.toString());
        customerService.addBlackCustomer(requestDtoB);

        assertThatThrownBy(() -> customerService.addBlackCustomer(requestDtoB)).isInstanceOf(DuplicateBlackCustomerException.class)
                .hasMessageContaining("Duplicate customer already exists in blacklist!");
    }

    @Test
    @DisplayName("blacklist 삭제 로직 성공 검증")
    public void successDeleteCustomerFromBlackList() {

        CustomerRequestDto requestDtoA = customerEntityMapper.mapToRequestDto(name, email);
        Customer customerA = customerEntityMapper.mapToEntityWithGenerator(requestDtoA, uuidGenerator);
        customerService.add(customerA);

        UUID customerAId = customerA.getCustomerId();
        CustomerRequestDto requestDtoB = customerEntityMapper.mapToRequestDtoWithUUID(customerAId.toString());
        customerService.addBlackCustomer(requestDtoB);

        CustomerRequestDto requestDtoC = customerEntityMapper.mapToRequestDtoWithUUID(customerAId.toString());
        customerService.deleteBlackCustomer(requestDtoC);
    }

    @Test
    @DisplayName("유효하지 않은 email 타입에 대한 find 로직 실패 검증")
    public void failFindByEmailByNonValidExist() {

        assertThatThrownBy(() -> customerService.findByEmail("asdfasdf")).isInstanceOf(IllegalEmailException.class)
                .hasMessageContaining("Illegal Email Type!");
    }

    @Test
    @DisplayName("존재하지 않는 email 값에 대한 find 로직 실패 검증")
    public void failFindByEamilByNonExist() {

        assertThatThrownBy(() -> customerService.findByEmail("ocard9611@gmail.com")).isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("No matching customers found!");
    }
}
