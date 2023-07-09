package prgms.spring_week1.domain.voucher.model.type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import prgms.spring_week1.exception.NoSuchVoucherTypeException;

import static org.junit.jupiter.api.Assertions.*;

class VoucherTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"FIXED", "PERCENT"})
    @DisplayName("올바른 바우처 입력값에 대해 예외를 던지지 않는 지 확인 ")
    void findVoucherType(String inputSelectText) {
        assertDoesNotThrow(() -> VoucherType.findVoucherType(inputSelectText));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIXD", "ERCENT"})
    @DisplayName("잘못된 입력 값에 대해 예외를 던지는 지 확인")
    void findVoucherType_fail(String inputSelectText) {
        Throwable exception = assertThrows(NoSuchVoucherTypeException.class, () -> VoucherType.findVoucherType(inputSelectText));
        assertEquals("해당 바우처 타입이 존재하지 않습니다.", exception.getMessage());
    }

}
