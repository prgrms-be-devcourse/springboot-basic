package org.promgrammers.springbootbasic.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.model.CustomerType;
import org.promgrammers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.promgrammers.springbootbasic.domain.voucher.model.VoucherType.FIXED;

class FileConverterTest {

    @Test
    @DisplayName("Parsing 성공 - 유효한 라인")
    void parseVoucherFromLineSuccessTest() throws Exception {

        //given
        String line = "16e83bb8-f61b-4035-8fa6-f40e692955da,20,FIXED";
        UUID expectedVoucherId = UUID.fromString("16e83bb8-f61b-4035-8fa6-f40e692955da");

        //when
        Voucher voucher = FileConverter.parseVoucherFromLine(line);

        //then
        assertThat(expectedVoucherId).isEqualTo(voucher.getVoucherId());
        assertThat(20).isEqualTo(voucher.getAmount());
        assertThat(FIXED).isEqualTo(voucher.getVoucherType());
    }

    @Test
    @DisplayName("Parsing 실패 - 잘못된 라인")
    void parseVoucherFromLineFailTest() throws Exception {

        //given
        String line = "invalid_line_type";

        //when -> then
        assertThrows(IllegalArgumentException.class, () -> FileConverter.parseVoucherFromLine(line));
    }

    @Test
    @DisplayName("Voucher를 라인으로 변환")
    void voucherToLineTest() throws Exception {

        //given
        UUID voucherId = UUID.fromString("c9d41d65-68d9-4f4b-b899-094cbac14278");
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, 100);

        //when
        String line = FileConverter.voucherToLine(voucher);

        //then
        assertThat(line).isEqualTo("c9d41d65-68d9-4f4b-b899-094cbac14278,100,FIXED");
    }

    @Test
    @DisplayName("Customer 변환 성공 - 유효한 라인")
    void parseCustomerFromLineSuccessTest() throws Exception {

        //given
        String line = "381aec71-9e69-4355-8c69-8b687b0bf446,BLACK";
        UUID expectedCustomerId = UUID.fromString("381aec71-9e69-4355-8c69-8b687b0bf446");

        //when
        Customer customer = FileConverter.parseCustomerFromLine(line);

        //then
        assertThat(customer.customerId()).isEqualTo(expectedCustomerId);
        assertThat(customer.customerType()).isEqualTo(CustomerType.BLACK);
    }

    @Test
    @DisplayName("Customer 변환 실패 - 잘못된 라인")
    void parseCustomerFromLineFailTest() throws Exception {

        //given
        String line = "11111111111-aaaaaaaaaaaa-aaaa-aaaaaa";

        //when -> then
        assertThrows(IllegalArgumentException.class, () -> FileConverter.parseCustomerFromLine(line));
    }
}