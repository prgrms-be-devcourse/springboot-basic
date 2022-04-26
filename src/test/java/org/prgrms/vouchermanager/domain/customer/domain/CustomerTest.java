package org.prgrms.vouchermanager.domain.customer.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CustomerTest {

    @Nested
    class Create {
        @Test
        void Customer_생성_성공() {
            final Customer customer = Customer.create("example", "example@email.com");

            assertThat(customer.getId()).isNotNull();
            assertThat(customer.getName()).isEqualTo("example");
            assertThat(customer.getEmail()).isEqualTo("example@email.com");
            assertThat(customer.getStatus()).isEqualTo(CustomerStatus.CREATED);
            assertThat(customer.getCreateAt()).isNotNull();
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 생성자에서_name이_공백이면_예외를_던진다(String name) {
            assertThatIllegalArgumentException().isThrownBy(() -> Customer.create(name, "example@email.com"));
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 생성자에서_email이_공백이면_예외를_던진다(String email) {
            assertThatIllegalArgumentException().isThrownBy(() -> Customer.create("example", email));
        }

        @Test
        void 생성자에서_email이_엣을_포함하지_않으면_예외를_던진다() {
            assertThatIllegalArgumentException().isThrownBy(() -> Customer.create("example", "exampleemail.com"));
        }
    }

    @Nested
    class BlackList {
        @Test
        void 블랙리스트등록_성공() {
            final Customer customer = Customer.create("example", "example@email.com");

            customer.changeBlackList();

            assertThat(customer.getStatus()).isEqualTo(CustomerStatus.BLOCKED);
        }
    }
}