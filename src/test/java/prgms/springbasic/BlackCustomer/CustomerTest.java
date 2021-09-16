package prgms.springbasic.BlackCustomer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    @DisplayName("이름만으로 고객을 생성했을 경우 고객번호가 자동으로 주어진다.")
    void customerCreation() {
        BlackCustomer customer = new BlackCustomer("eonju");
        Assertions.assertThat(customer.getCustomerId()).isNotNull();
    }
}