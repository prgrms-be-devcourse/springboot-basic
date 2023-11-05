package team.marco.voucher_management_system.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @DisplayName("필수값(이름, 이메일)만으로 사용자를 생성할 수 있다.")
    @Test
    void createCustomerByBuilder() {
        // given
        String name = "name";
        String email = "email@gmail.com";

        // when
        Customer customer = new Customer.Builder(name, email).build();

        // then
        assertThat(customer.getId()).isNotNull();
        assertThat(customer.getCreatedAt()).isNotNull();
        assertThat(customer)
                .extracting("name", "email")
                .contains(name, email);
    }

    @DisplayName("사용자 ID는 중복되지 않는다.")
    @Test
    void createUniqueIdWhenUserCreate() {
        // given // when
        Customer customer1 = new Customer.Builder("name1", "email1@gmail.com").build();
        Customer customer2 = new Customer.Builder("name2", "email2@gmail.com").build();

        // then
        assertThat(customer1.getId()).isNotEqualTo(customer2.getId());
    }

    @DisplayName("사용자 이름은 빈칸 혹은 공백일 수 없다.")
    @Test
    void createCustomerWithBlankName() {
        // given // when
        String blankName = "   ";

        // then when
        assertThatThrownBy(() -> new Customer.Builder(blankName, "email1@gmail.com").build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 문자열 일 수 없습니다.");
    }

    @DisplayName("사용자 이메일은 빈칸 혹은 공백일 수 없다.")
    @Test
    void createCustomerWithBlankEmail() {
        // given // when
        String blankEmail = "   ";

        // then when
        assertThatThrownBy(() -> new Customer.Builder("name", blankEmail).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 문자열 일 수 없습니다.");
    }
}