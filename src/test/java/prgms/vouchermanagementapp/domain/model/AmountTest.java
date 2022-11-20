package prgms.vouchermanagementapp.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AmountTest {

    private static Stream<Long> providePositiveIntegers() {
        return Stream.of(10L, 100L, 1000L, 10000L);
    }

    private static Stream<Long> provideNegativeIntegers() {
        return Stream.of(-10L, -100L, -1000L, -10000L);
    }

    @DisplayName("양의 정수에 대해 금액 객체를 생성할 수 있다.")
    @ParameterizedTest(name = ("{index} number={0}"))
    @MethodSource("providePositiveIntegers")
    void should_create_amountInstance_for_positive_integers(long number) {
        // when
        Amount amount = new Amount(number);

        // then
        assertThat(amount.getAmount()).isEqualTo(number);
    }

    @DisplayName("음의 정수에 대해 금액 객체를 생성할 수 없다. (생성 시 IllegalArgumentException 발생)")
    @ParameterizedTest(name = ("{index} number={0}"))
    @MethodSource("provideNegativeIntegers")
    void should_throw_exception_for_negative_integers(long number) {
        // when & then
        assertThatThrownBy(() -> new Amount(number)).isInstanceOf(IllegalArgumentException.class);
    }
}