package org.prgms.kdtspringweek1.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.kdtspringweek1.console.ScannerInput;
import org.prgms.kdtspringweek1.controller.consoleController.ConsoleInputConverter;
import org.prgms.kdtspringweek1.voucher.service.dto.CreateVoucherRequestDto;
import org.prgms.kdtspringweek1.controller.consoleController.dto.SelectFunctionTypeDto;
import org.prgms.kdtspringweek1.voucher.service.dto.SelectVoucherTypeDto;
import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.prgms.kdtspringweek1.voucher.entity.VoucherType;

import java.util.*;

import static java.util.Comparator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ConsoleInputConverterTest {

    @InjectMocks
    private ConsoleInputConverter consoleInputConverter;

    @Mock
    private ScannerInput scannerInput;

    private static Random random = new Random();

    @Test
    @DisplayName("기능 입력 성공")
    void Success_GetFunctionType() {
        // given
        List<String> inputs = Arrays.stream(SelectFunctionTypeDto.values())
                .map(SelectFunctionTypeDto::getName)
                .toList();
        String input = inputs.get(random.nextInt(inputs.size())).chars()
                .mapToObj(c -> random.nextBoolean() ? Character.toLowerCase(c) : Character.toUpperCase(c))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        when(scannerInput.getInput()).thenReturn(input);

        // when
        SelectFunctionTypeDto selectedFunction = consoleInputConverter.getFunctionType();

        // then
        assertThat(selectedFunction, isOneOf(SelectFunctionTypeDto.values()));
    }

    @Test
    @DisplayName("기능 입력 실패 - 지원되지 않는 기능인 경우")
    void Fail_FunctionType_UnsupportedFunction() {
        // given
        String input = "abcdefg";
        when(scannerInput.getInput()).thenReturn(input);

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            consoleInputConverter.getFunctionType();
        });

        // then
        assertThat(exception.getMessage(), is(InputExceptionCode.INVALID_VOUCHER_FUNCTION_TYPE.getMessage()));
    }

    @Test
    @DisplayName("바우처 종류 입력 성공")
    void Success_GetVoucherType() {
        // given
        List<Long> inputs = Arrays.stream(SelectVoucherTypeDto.values())
                .map(SelectVoucherTypeDto::getNum)
                .toList();
        String input = inputs.get(random.nextInt(inputs.size())).toString();
        when(scannerInput.getInput()).thenReturn(input);

        // when
        VoucherType selectedVoucher = consoleInputConverter.getVoucherType();

        // then
        assertThat(selectedVoucher, isOneOf(VoucherType.values()));
    }

    @Test
    @DisplayName("바우처 종류 입력 실패 - 지원되지 않는 비우처인 경우")
    void Fail_GetVoucherType_UnsupportedVoucher() {
        // given
        long possibleMaxInput = Arrays.stream(SelectVoucherTypeDto.values())
                .max(comparingLong(SelectVoucherTypeDto::getNum))
                .get().getNum();
        String input = String.valueOf(random.nextLong(Long.MAX_VALUE - possibleMaxInput) + possibleMaxInput);
        when(scannerInput.getInput()).thenReturn(input);

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            consoleInputConverter.getVoucherType();
        });

        // then
        assertThat(exception.getMessage(), is(InputExceptionCode.INVALID_VOUCHER_TYPE.getMessage()));
    }

    @Test
    @DisplayName("바우처 할인 값 입력 성공")
    void Success_GetCreateVoucherRequestDto() {
        // given
        String discountValue = String.valueOf(random.nextLong(Long.MAX_VALUE) + 1);
        String voucherType = Arrays.stream(VoucherType.values())
                .skip(random.nextInt(VoucherType.values().length))
                .findFirst()
                .map(VoucherType::getName)
                .orElse(null);

        // when
        CreateVoucherRequestDto createVoucherRequestDto = new CreateVoucherRequestDto(Long.parseLong(discountValue), voucherType);;

        // then
        assertThat(createVoucherRequestDto.getDiscountValue(), greaterThan(0L));
    }

    @Test
    @DisplayName("바우처 할인 값 입력 실패 - 0이하의 값을 입력한 경우")
    void Fail_GetCreateVoucherRequestDto_ZeroOrLess() {
        // given
        String discountValue = String.valueOf(-1 * random.nextLong(Long.MAX_VALUE));
        String voucherType = Arrays.stream(VoucherType.values())
                .skip(random.nextInt(VoucherType.values().length))
                .findFirst()
                .map(VoucherType::getName)
                .orElse(null);

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new CreateVoucherRequestDto(Long.parseLong(discountValue), voucherType);
        });

        // then
        assertThat(exception.getMessage(), is(InputExceptionCode.INVALID_DISCOUNT_VALUE.getMessage()));
    }
}