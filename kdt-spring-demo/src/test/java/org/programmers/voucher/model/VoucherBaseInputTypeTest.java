package org.programmers.voucher.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherBaseInputTypeTest {

    @Test
    @DisplayName("바우처 타입에 문자열을 전달하면 해당하는 Enum을 반환할 수 있다")
    void getInputType() {
        String exit = "exit";
        String create = "create";
        String voucherlist = "voucherlist";
        String delete = "delete";
        String find = "find";
        String blacklist = "blacklist";
        String update = "update";

        Assertions.assertThat(VoucherInputType.getInputType(exit)).isEqualTo(VoucherInputType.EXIT);
        Assertions.assertThat(VoucherInputType.getInputType(create)).isEqualTo(VoucherInputType.CREATE);
        Assertions.assertThat(VoucherInputType.getInputType(voucherlist)).isEqualTo(VoucherInputType.VOUCHERLIST);
        Assertions.assertThat(VoucherInputType.getInputType(delete)).isEqualTo(VoucherInputType.DELETE);
        Assertions.assertThat(VoucherInputType.getInputType(find)).isEqualTo(VoucherInputType.FIND);
        Assertions.assertThat(VoucherInputType.getInputType(blacklist)).isEqualTo(VoucherInputType.BLACKLIST);
        Assertions.assertThat(VoucherInputType.getInputType(update)).isEqualTo(VoucherInputType.UPDATE);
    }

    @Test
    @DisplayName("바우처 타입에 적절하지 않은 문자열을 전달하면 예외처리가 발생한다.")
    void getInputTypeException() {
        String errorString = "errorString";

        assertThrows(RuntimeException.class, () -> VoucherInputType.getInputType(errorString));
    }
}