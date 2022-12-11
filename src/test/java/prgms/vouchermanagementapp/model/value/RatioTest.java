package prgms.vouchermanagementapp.model.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RatioTest {

    @DisplayName("0이상 100이하의 정수에 대해 비율 객체 생성이 가능하다.")
    @ParameterizedTest(name = "{index}. number={0}")
    @ValueSource(longs = {0L, 100L, 50L, 10L})
    void should_create_ratioInstance_for_valid_range(Long number) {
        // when
        Ratio ratio = new Ratio(number);

        // then
        assertThat(ratio.getFixedDiscountLevel()).isEqualTo(number);
    }

    @DisplayName("비율은 0이상 100이하여야 한다. 그 외의 경우 IllegalArgumentException이 발생한다.")
    @ParameterizedTest(name = "{index}. number={0}")
    @ValueSource(longs = {-1L, 101L})
    void should_throw_exception_for_invalid_range(Long number) {
        // when & then
        assertThatThrownBy(() -> new Ratio(number)).isInstanceOf(IllegalArgumentException.class);
    }
}