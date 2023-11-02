package devcourse.springbootbasic.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static devcourse.springbootbasic.TestDataFactory.generateBlacklistCustomer;
import static devcourse.springbootbasic.TestDataFactory.generateNotBlacklistCustomers;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    @DisplayName("고객을 생성할 수 있습니다.")
    void givenValidCustomerData_whenCreateCustomer_thenCustomerIsCreated() {
        // Given
        String name = "Platypus";

        // When
        Customer customer = generateNotBlacklistCustomers(name);

        // Then
        assertThat(customer).isNotNull();
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.isBlacklisted()).isFalse();
    }

    @Test
    @DisplayName("고객의 블랙리스트 상태를 업데이트할 수 있습니다.")
    void givenUpdatedBlacklistStatus_whenUpdateBlacklistStatus_thenBlacklistStatusIsUpdated() {
        // Given
        String name = "Platypus";
        Customer customer = generateBlacklistCustomer(name);

        // When
        customer = customer.releaseBlacklist();

        // Then
        assertThat(customer).isNotNull();
        assertThat(customer.isBlacklisted()).isFalse();
    }
}
