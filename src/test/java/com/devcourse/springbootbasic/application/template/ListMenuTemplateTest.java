package com.devcourse.springbootbasic.application.template;

import com.devcourse.springbootbasic.application.constant.YamlProperties;
import com.devcourse.springbootbasic.application.dto.ListMenu;
import com.devcourse.springbootbasic.application.dto.VoucherDto;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.repository.customer.CustomerRepository;
import com.devcourse.springbootbasic.application.repository.voucher.MemoryVoucherRepository;
import com.devcourse.springbootbasic.application.service.CustomerService;
import com.devcourse.springbootbasic.application.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class ListMenuTemplateTest {

    ListMenuTemplate listMenuTemplate;
    VoucherService voucherService;
    YamlProperties yamlProperties;

    static Stream<Arguments> provideVouchers() {
        return Stream.of(
                Arguments.of(new VoucherDto(VoucherType.FIXED_AMOUNT, 123)),
                Arguments.of(new VoucherDto(VoucherType.PERCENT_DISCOUNT, 23))
        );
    }

    @BeforeEach
    void init() {
        voucherService = new VoucherService(new MemoryVoucherRepository());
        yamlProperties = new YamlProperties();
    }

    @ParameterizedTest
    @DisplayName("바우처 리스트 메뉴일 때 리스트 반환 테스트")
    @MethodSource("provideVouchers")
    void 바우처리스트메뉴테스트(VoucherDto voucherDto) {
        listMenuTemplate = new ListMenuTemplate(
                new CustomerService(new CustomerRepository(yamlProperties.getVoucherRecordPath())),
                voucherService
        );
        int count = 0;
        voucherService.createVoucher(voucherDto);
        var list = listMenuTemplate.listTask(ListMenu.VOUCHER_LIST);
        assertThat(list.size(), is(equalTo(count + 1)));
    }

    @Test
    @DisplayName("블랙고객 리스트 메뉴일 때 리스트 반환 테스트")
    void 블랙고객리스트메뉴테스트() {
        listMenuTemplate = new ListMenuTemplate(
                new CustomerService(new CustomerRepository("/Users/onetuks/Documents/git/IntelliJProjects/springboot-basic/src/main/resources/customer_blacklist.csv")),
                voucherService
        );
        var list = listMenuTemplate.listTask(ListMenu.BLACK_CUSTOMER_LIST);
        int i = 0;
        assertThat(list.get(i).contains(String.valueOf(i)), is(true));
    }

}