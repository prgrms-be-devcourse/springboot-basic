package prgms.springbasic.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    @DisplayName("이름만으로 고객을 생성했을 경우 고객번호가 자동으로 주어진다.")
    void customerCreation() {
        Customer customer = new Customer("eonju");
        Assertions.assertThat(customer.getCustomerId()).isNotNull();
    }
}