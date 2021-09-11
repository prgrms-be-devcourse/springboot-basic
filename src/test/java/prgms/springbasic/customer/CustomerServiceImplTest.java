package prgms.springbasic.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import prgms.springbasic.CustomerBlacklistAppConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerServiceImplTest {
    @BeforeEach
    void removeFile() {
        Path path = Path.of("src/main/customer_blacklist.csv");
        try {
            Files.deleteIfExists(path);
        } catch (IOException exception) {
            System.err.println("파일 지우기에 실패했습니다.");
        }
    }

    @Test
    void createCustomer() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CustomerBlacklistAppConfig.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);
        Customer customer = customerService.createCustomer("eonju");

        assertThat(customer.getCustomerId()).isNotNull();
    }

    @Test
    void findByName() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CustomerBlacklistAppConfig.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);
        Customer customer = customerService.createCustomer("eonju");
        Customer findCustomer = customerService.findByName(customer.getName());
        assertThat(customer.getCustomerId()).isEqualTo(findCustomer.getCustomerId());
        assertThatThrownBy(() -> customerService.findByName("ohoho"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("찾을");
    }

    @Test
    void getCustomerList() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CustomerBlacklistAppConfig.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);
        Customer customer01 = customerService.createCustomer("eonju");
        Customer customer02 = customerService.createCustomer("spancer");
        Customer customer03 = customerService.createCustomer("allen");
        Customer customer04 = new Customer("iamnotcustommer");
        List<Customer> customerList = customerService.getCustomerList();

        assertThat(customerList).containsAll(List.of(customer01, customer02, customer03));
        assertThat(customerList).doesNotContain(customer04);
    }
}