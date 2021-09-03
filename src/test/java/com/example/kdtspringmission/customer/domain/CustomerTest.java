package com.example.kdtspringmission.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.domain.RateAmountVoucher;
import com.example.kdtspringmission.voucher.domain.Voucher;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    @DisplayName("지갑에 바우처를 추가한다")
    void testAddVoucher() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "tester1", "tester@gmail.com",
            LocalDateTime.now());
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID());

        //when
        Voucher addVoucher = customer.addVoucher(voucher);

        //then
        assertThat(customer.getWallet()).contains(addVoucher);
    }

    @Test
    @DisplayName("고객이 어떤 바우처를 가지고 있는지 조회한다.")
    void testListVouchers() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "tester1", "tester@gmail.com",
            LocalDateTime.now());
        List<Voucher> vouchers = new ArrayList<>() {{
            add(new FixedAmountVoucher(UUID.randomUUID()));
            add(new RateAmountVoucher(UUID.randomUUID()));
            add(new FixedAmountVoucher(UUID.randomUUID()));
        }};

        for (Voucher voucher : vouchers) {
            customer.addVoucher(voucher);
        }

        //when
        List<Voucher> wallet = customer.getWallet();

        //then
        assertThat(wallet).hasSameElementsAs(vouchers);
    }



}
