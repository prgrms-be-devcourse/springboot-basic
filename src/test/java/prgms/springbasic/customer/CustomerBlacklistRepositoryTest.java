package prgms.springbasic.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerBlacklistRepositoryTest {

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
    @DisplayName("고객 정보를 저장할 수 있어야 한다.")
    void save() {
        try {
            CustomerRepository repository = new CustomerBlacklistRepository();
            Customer customer01 = new Customer("allen");
            int beforeSize = repository.getCustomerList().size();
            repository.save(customer01);
            assertThat(repository.getCustomerList().size()).isEqualTo(beforeSize + 1);

        } catch (IOException exception) {
            throw new RuntimeException("고객 정보를 저장하지 못했습니다.");
        }
    }

    @Test
    @DisplayName("이름을 통해 고객 정보를 받아올 수 있어야한다.")
    void findByName() {
        try {
            CustomerRepository repository = new CustomerBlacklistRepository();
            Customer customer01 = new Customer("allen");
            repository.save(customer01);

            Customer findCustomer = repository.findByName("allen");
            assertThat(customer01.getCustomerId()).isEqualTo(findCustomer.getCustomerId());

        } catch (IOException exception) {
            throw new RuntimeException("고객 정보를 읽어오지 못했습니다.");
        }
    }

    @Test
    @DisplayName("모든 고객의 정보를 들고와야한다.")
    void getCustomerList() {
        CustomerRepository repository = null;
        try {
            repository = new CustomerBlacklistRepository();
            Customer customer01 = new Customer("allen");
            Customer customer02 = new Customer("runju");
            Customer customer03 = new Customer("rabbit");

            repository.save(customer01);
            repository.save(customer02);
            repository.save(customer03);

            assertThat(repository.getCustomerList().size()).isEqualTo(3);

        } catch (IOException exception) {
            throw new RuntimeException("고객 정보를 읽어오지 못했습니다.");
        }
    }
}