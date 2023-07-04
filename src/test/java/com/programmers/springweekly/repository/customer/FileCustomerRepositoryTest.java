package com.programmers.springweekly.repository.customer;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = {FileCustomerRepository.class})
class FileCustomerRepositoryTest {

    @Autowired
    private FileCustomerRepository customerRepository;

    @Test
    @DisplayName("블랙 리스트 명단을 정상적으로 가져온다.")
    void getBlacklistOfFileRepository() {
        // given
        UUID customer1UUID = UUID.fromString("1927f6b6-55bf-4f1f-d6e2-043198833df7");
        UUID customer2UUID = UUID.fromString("80f4ef40-1a44-4ef2-9c8d-714ff0c8353e");
        UUID customer3UUID = UUID.fromString("202e65fe-bdd2-4f0b-db43-307d5c24ad4a");

        List<Customer> expectBlacklist = List.of(
                new Customer(customer1UUID, "changhyeon", "changhyeon.h@kakao.com", CustomerType.BLACKLIST),
                new Customer(customer2UUID, "changhyeon1", "changhyeon.h@kakao.com", CustomerType.BLACKLIST),
                new Customer(customer3UUID, "changhyeon2", "changhyeon.h@kakao.com", CustomerType.BLACKLIST)
        );

        // when
        List<Customer> actualBlacklist = customerRepository.getBlackList();

        // then
        assertThat(actualBlacklist).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectBlacklist);

    }
}
