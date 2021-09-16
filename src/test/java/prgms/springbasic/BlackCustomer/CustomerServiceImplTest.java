package prgms.springbasic.BlackCustomer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
        BlackCustomerService customerService = applicationContext.getBean(BlackCustomerService.class);
        BlackCustomer customer = customerService.saveCustomer("eonju");

        assertThat(customer.getCustomerId()).isNotNull();
    }

    @Test
    void findByName() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CustomerBlacklistAppConfig.class);
        BlackCustomerService customerService = applicationContext.getBean(BlackCustomerService.class);
        BlackCustomer customer = customerService.saveCustomer("eonju");
        BlackCustomer findCustomer = customerService.findByName(customer.getName()).get();
        assertThat(customer.getCustomerId()).isEqualTo(findCustomer.getCustomerId());
        assertThatThrownBy(() -> customerService.findByName("ohoho"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("찾을");
    }

    @Test
    void getCustomerList() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CustomerBlacklistAppConfig.class);
        BlackCustomerService customerService = applicationContext.getBean(BlackCustomerService.class);
        BlackCustomer customer01 = customerService.saveCustomer("eonju");
        BlackCustomer customer02 = customerService.saveCustomer("spancer");
        BlackCustomer customer03 = customerService.saveCustomer("allen");
        BlackCustomer customer04 = new BlackCustomer("iamnotcustommer");
        List<BlackCustomer> customerList = customerService.getCustomerList();

        assertThat(customerList).containsAll(List.of(customer01, customer02, customer03));
        assertThat(customerList).doesNotContain(customer04);
    }
}