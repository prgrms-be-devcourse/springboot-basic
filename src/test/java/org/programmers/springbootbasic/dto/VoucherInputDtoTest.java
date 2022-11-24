package org.programmers.springbootbasic.dto;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.domain.voucher.dto.VoucherInputDto;
import org.programmers.springbootbasic.exception.WrongTypeInputException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;

class VoucherInputDtoTest {

    @Test
    @DisplayName("VoucherInputDto를 생성할 때 목록에 없는 타입의 경우 WrongTypeInputException을 반환한다.")
    void VoucherInputDto_생성_에러핸들링_테스트() throws Exception {
        //given
        String invalidVoucherType = "notInList";
        //then
        Assertions.assertThatThrownBy(() -> {
            VoucherInputDto voucherInputDto = new VoucherInputDto(invalidVoucherType, 100);
        }).isInstanceOf(WrongTypeInputException.class);
    }

    @Test
    @DisplayName("VoucherInputDto를 생성할 때 올바른 타입의 경우 생성에 성공한다.")
    void VoucherInputDto_생성_성공_테스트() throws Exception {
        //given
        String validVoucherType = "fixed";
        // when
        VoucherInputDto voucherInputDto = new VoucherInputDto(validVoucherType, 1000);
        //then
        assertThat(voucherInputDto, instanceOf(VoucherInputDto.class));
    }

}