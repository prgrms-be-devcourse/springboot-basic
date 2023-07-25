package org.weekly.weekly.customer.dto;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.weekly.weekly.customer.dto.request.CustomerCreationRequest;
import org.weekly.weekly.customer.dto.request.CustomerUpdateRequest;
import org.weekly.weekly.ui.exception.InputException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CustomerDto 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CustomerDtoTest {

    @DisplayName("CustomerUpdate: 업데이트, 삭제, 조회")
    @Nested
    class Update {

        @Test
        void 고객정보_업데이트요청_성공_테스트() {
            // Given
            String email = "psy@naver.com";
            String newEamil = "newPsy@naver.com";

            // When
            CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(email, newEamil);

            // Then
            assertThat(customerUpdateRequest.email()).isEqualTo(email);
            assertThat(customerUpdateRequest.newEmail()).isEqualTo(newEamil);
        }

        @ParameterizedTest
        @CsvSource({" , ",
                "test, ",
                ", test"})
        void 고객정보_잘못된_입력으로_업데이트_요청_실패_테스트(String email, String newEmail) {
            assertThatThrownBy(() -> new CustomerUpdateRequest(email, newEmail))
                    .isInstanceOf(InputException.class);
        }
    }

    @DisplayName("CustomerCreate")
    @Nested
    class Create {

        @Test
        void 고객생성_요청_성공_테스트() {
            // Given
            String email = "email@naver.com";
            String name = "psy";

            // When
            CustomerCreationRequest customerCreationRequest = new CustomerCreationRequest(email, name);

            // Then
            assertThat(customerCreationRequest.getEmail()).isEqualTo(email);
            assertThat(customerCreationRequest.getName()).isEqualTo(name);
        }

        @ParameterizedTest
        @CsvSource({
                "email@naver.com, ",
                ", name",
                " , "
        })
        void 고객정보_잘못된_입력으로_고객생성_요청_실패_테스트(String email, String name) {
            assertThatThrownBy(() -> new CustomerUpdateRequest(email, name))
                    .isInstanceOf(InputException.class);
        }
    }
}
