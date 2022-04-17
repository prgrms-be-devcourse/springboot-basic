package org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher.fixed;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher.CreateVoucherMenuChoice;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.repository.MemoryVoucherRepository;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.InputMismatchException;

import static org.assertj.core.api.Assertions.*;

class CreateFADVMenuTest {

    CreateFADVMenu menu;
    VoucherService voucherService;

    @BeforeEach
    void setup() {
        voucherService = new VoucherService(new MemoryVoucherRepository());
    }


    @Nested
    class constructorTest {
        @Test
        void whenCreateFADVMenu_thenChoiceIsSet(){
            //action
            menu = new CreateFADVMenu(voucherService);

            //assert
            assertThat(menu.getChoice()).isEqualTo(CreateVoucherMenuChoice.FIXED);
        }
    }


    @Nested
    class procTest {

        @Test
        void givenInput2000_whenProcMenu_thenFADVInserted() {
            //setup
            String input = "2000";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            menu = new CreateFADVMenu(voucherService);

            //action
            menu.proc();

            //assert
            assertThat(voucherService.findAll()).hasSize(1);
            assertThat(voucherService.findAll().get(0))
                    .isInstanceOf(FixedAmountDiscountVoucher.class);
            FixedAmountDiscountVoucher voucher = (FixedAmountDiscountVoucher) voucherService.findAll().get(0);
            assertThat(voucher.getAmount()).isEqualTo(2000L);
        }

        @Test
        void givenNegativeInput_whenProcMenu_thenThrowsIllegalArgumentException() {
            //setup
            String input = "-2000";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            menu = new CreateFADVMenu(voucherService);

            //assert throws
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> menu.proc());
        }

        @Test
        void givenNoNumberInput_whenProcMenu_thenThrowsIInputMismatchException() {
            //setup
            String input = "나는 숫자가 아니야";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            menu = new CreateFADVMenu(voucherService);

            //assert throws
            assertThatThrownBy(() -> menu.proc())
                    .isInstanceOf(InputMismatchException.class);
        }
    }
}