package com.programmers.springweekly.repository.customer;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class FileCustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("블랙 리스트 명단을 정상적으로 가져온다.")
    void getBlacklistOfFileRepository() {
        // given
        UUID customer1UUID = UUID.fromString("1927f6b6-55bf-4f1f-d6e2-043198833df7");
        UUID customer2UUID = UUID.fromString("80f4ef40-1a44-4ef2-9c8d-714ff0c8353e");
        UUID customer3UUID = UUID.fromString("202e65fe-bdd2-4f0b-db43-307d5c24ad4a");

        Map<UUID, Customer> expectBlacklist = Map.of(
                customer1UUID,
                new Customer(customer1UUID, CustomerType.BLACKLIST),
                customer2UUID,
                new Customer(customer2UUID, CustomerType.BLACKLIST),
                customer3UUID,
                new Customer(customer3UUID, CustomerType.BLACKLIST)
        );

        // when
        Map<UUID, Customer> actualBlacklist = customerRepository.getBlackList();

        // then
        assertThat(actualBlacklist.get(customer1UUID))
                .usingRecursiveComparison()
                .isEqualTo(expectBlacklist.get(customer1UUID));

        assertThat(actualBlacklist.get(customer2UUID))
                .usingRecursiveComparison()
                .isEqualTo(expectBlacklist.get(customer2UUID));

        assertThat(actualBlacklist.get(customer3UUID))
                .usingRecursiveComparison()
                .isEqualTo(expectBlacklist.get(customer3UUID));
    }
}
