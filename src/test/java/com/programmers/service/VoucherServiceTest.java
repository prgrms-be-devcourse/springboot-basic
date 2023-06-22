package com.programmers.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.domain.FixedAmountVoucher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.UUID;

class VoucherServiceTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream printStream = System.out;

    VoucherService voucherService = new VoucherService();

    @BeforeEach
    public void before() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void after() {
        System.setOut(printStream);
    }

    @DisplayName("바우처를 저장하고 조회한다")
    @Test
    void saveAndFindAll() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "voucherName1", 10L);

        //when
        voucherService.save(fixedAmountVoucher);
        voucherService.findAll();

        //then
        assertThat(outputStream.toString()).contains("voucherName1");
    }
}