package com.devcourse.springbootbasic.application.template;

import com.devcourse.springbootbasic.application.domain.Voucher;
import com.devcourse.springbootbasic.application.dto.VoucherDto;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.repository.voucher.MemoryVoucherRepository;
import com.devcourse.springbootbasic.application.service.VoucherService;
import com.devcourse.springbootbasic.application.util.template.CreateMenuTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateMenuTemplateTest {

    CreateMenuTemplate createMenuTemplate;

    static Stream<Arguments> provideValids() {
        return Stream.of(
                Arguments.of(new VoucherDto(VoucherType.FIXED_AMOUNT, 100)),
                Arguments.of(new VoucherDto(VoucherType.PERCENT_DISCOUNT, 12)),
                Arguments.of(new VoucherDto(VoucherType.FIXED_AMOUNT, 323))
        );
    }

    static Stream<Arguments> provideInvalids() {
        return Stream.of(
                Arguments.of("My name is Batman"),
                Arguments.of(19942),
                Arguments.of(new ArrayList<>())
        );
    }

    @BeforeEach
    void init() {
        createMenuTemplate = new CreateMenuTemplate(
                new VoucherService(
                        new MemoryVoucherRepository()
                )
        );
    }

    @ParameterizedTest
    @DisplayName("정상적인 바우처 Dto 입력 시 성공")
    @MethodSource("provideValids")
    void 정상테스트(VoucherDto voucherDto) {
        var voucher = createMenuTemplate.createTask(voucherDto);
        assertThat(voucher, instanceOf(Voucher.class));
    }

    @ParameterizedTest
    @DisplayName("비정상적인 바우처 Dto 입력 시 실패")
    @MethodSource("provideInvalids")
    void 예외테스트(Object input) {
        assertThrows(Exception.class, () -> createMenuTemplate.createTask((VoucherDto) input));
    }


}