package org.devcourse.voucher.customer.repository;

import org.assertj.core.api.Assertions;
import org.devcourse.voucher.customer.model.Customer;
import org.devcourse.voucher.customer.model.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CsvBlacklistRepositoryTest {

    @Autowired
    BlacklistRepository blacklistRepository;

    @Test
    @DisplayName("CSV 파일로부터 값을 잘 앍어오는지 테스트")
    void csvFindAllTest() {
        List<Customer> want = List.of(new Customer(UUID.fromString("d2fc4fcf-f9ac-42ea-a7d9-28cb0bf2848e"),
                "test",
                new Email("test@test.com")
        ));

        List<Customer> got = blacklistRepository.findAll();

        Assertions.assertThat(got).usingRecursiveComparison().isEqualTo(want);
    }
}