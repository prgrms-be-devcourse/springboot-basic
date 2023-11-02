package devcourse.springbootbasic.commandline.function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class FunctionTest {

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5"})
    @DisplayName("유효한 기능 코드가 주어지면 기능이 반환됩니다.")
    void givenValidFunctionCode_whenFromCode_thenFunctionIsReturned(String validCode) {
        // When
        Optional<Function> function = Function.fromCode(validCode);

        // Then
        assertThat(function).isPresent();
    }

    @ParameterizedTest
    @CsvSource({"-59", "-1", "a"})
    @DisplayName("유효하지 않은 기능 코드가 주어지면 기능이 반환되지 않습니다.")
    void givenInvalidFunctionCode_whenFromCode_thenNoFunctionIsReturned(String invalidCode) {
        // When
        Optional<Function> function = Function.fromCode(invalidCode);

        // Then
        assertThat(function).isNotPresent();
    }
}
