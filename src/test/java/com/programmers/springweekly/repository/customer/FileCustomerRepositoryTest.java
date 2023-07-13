package com.programmers.springweekly.repository.customer;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = {FileCustomerRepository.class})
class FileCustomerRepositoryTest {

    @Autowired
    private FileCustomerRepository customerRepository;

    @Test
    @DisplayName("블랙리스트 파일에 저장된 블랙 리스트 명단을 가져올 수 있다.")
    void getBlacklistOfFileRepository() {
        // given
        UUID customer1UUID = UUID.fromString("1927f6b6-55bf-4f1f-d6e2-043198833df7");
        UUID customer2UUID = UUID.fromString("80f4ef40-1a44-4ef2-9c8d-714ff0c8353e");
        UUID customer3UUID = UUID.fromString("202e65fe-bdd2-4f0b-db43-307d5c24ad4a");

        List<Customer> expectBlacklist = List.of(
                Customer.builder()
                        .customerId(customer1UUID)
                        .customerName("changhyeon")
                        .customerEmail("changhyeon.h@kakao.com")
                        .customerType(CustomerType.BLACKLIST)
                        .build(),
                Customer.builder()
                        .customerId(customer2UUID)
                        .customerName("changhyeonh")
                        .customerEmail("changhyeon.h@kakao.com")
                        .customerType(CustomerType.BLACKLIST)
                        .build(),
                Customer.builder()
                        .customerId(customer3UUID)
                        .customerName("changhyeonhh")
                        .customerEmail("changhyeon.h@kakao.com")
                        .customerType(CustomerType.BLACKLIST)
                        .build()
        );

        // when
        List<Customer> actualBlacklist = customerRepository.getBlackList();

        // then
        assertThat(actualBlacklist).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectBlacklist);
    }

}
