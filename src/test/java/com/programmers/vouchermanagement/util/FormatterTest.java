package com.programmers.vouchermanagement.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;

class FormatterTest {
    @Test
    @DisplayName("바우처가 없을 때의 포매터 문구를 출력한다.")
    void testFormatNoContent_Voucher() {
        //given
        String contentType = "voucher";

        //when
        String formatted = Formatter.formatNoContent(contentType);

        //then
        assertThat(formatted, containsString("There is no voucher stored yet!"));
    }

    @Test
    @DisplayName("고객이 없을 때의 포매터 문구를 출력한다.")
    void testFormatNoContent_Customer() {
        //given
        String contentType = "customer";

        //when
        String formatted = Formatter.formatNoContent(contentType);

        //then
        assertThat(formatted, containsString("There is no customer stored yet!"));
    }

    @Test
    @DisplayName("블랙리스트가 없을 때의 포매터 문구를 출력한다.")
    void testFormatNoContent_BlackCustomer() {
        //given
        String contentType = "black customer";

        //when
        String formatted = Formatter.formatNoContent(contentType);

        //then
        assertThat(formatted, containsString("There is no black customer stored yet!"));
    }

    @Test
    @DisplayName("고객 정보를 포매팅한 문구를 출력한다.")
    void testFormatCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer");

        //when
        String formatted = Formatter.formatCustomer(CustomerResponse.from(customer));

        //then
        assertThat(formatted, containsString("Customer ID : " + customer.getCustomerId()));
        assertThat(formatted, containsString("Customer Name : " + customer.getName()));
        assertThat(formatted, containsString("This Customer is " + customer.getCustomerType().name()));
    }

    @Test
    @DisplayName("바우처 정보를 포매팅한 문구를 출력한다.")
    void formatVoucher() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);

        //when
        String formatted = Formatter.formatVoucher(VoucherResponse.from(voucher));

        //then
        assertThat(formatted, containsString("Voucher ID : " + voucher.getVoucherId()));
        assertThat(formatted, containsString("Voucher Type : " + voucher.getVoucherType().displayTypeName()));
        assertThat(formatted, containsString("Discount Amount : " + voucher.getDiscountValue()));
    }
}
