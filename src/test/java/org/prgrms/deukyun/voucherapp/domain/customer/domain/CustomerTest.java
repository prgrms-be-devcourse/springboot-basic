package org.prgrms.deukyun.voucherapp.domain.customer.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void 성공_생성(){
        Customer customer = new Customer("ndy", true, Collections.emptyList());

        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isNotNull();
        assertThat(customer.getName()).isEqualTo("ndy");
        assertThat(customer.getVouchers()).isEmpty();
        assertThat(customer.isBlocked()).isTrue();
    }

    @Test
    void 성공_생성_아이디_포함(){
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "ndy", true, Collections.emptyList());

        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getName()).isEqualTo("ndy");
        assertThat(customer.getVouchers()).isEmpty();
        assertThat(customer.isBlocked()).isTrue();
    }

    @Test
    void 실패_생성_아이디_null(){
        //given
        UUID id = null;

        //then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new Customer(id, "ndy", true, Collections.emptyList()));
    }


    @Test
    void 실패_생성_이름_null(){
        //given
        String name = null;

        //then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new Customer(UUID.randomUUID(), name, true, Collections.emptyList()));
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new Customer(name, true, Collections.emptyList()));
    }


    @Test
    void 실패_생성_바우처_null(){
        //given
        List<Voucher> vouchers = null;

        //then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new Customer(UUID.randomUUID(), "ndy", true, vouchers));

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new Customer( "ndy", true, vouchers));
    }
}