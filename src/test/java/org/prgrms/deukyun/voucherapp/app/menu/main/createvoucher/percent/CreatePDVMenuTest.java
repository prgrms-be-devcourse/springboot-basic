package org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher.percent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher.CreateVoucherMenuChoice;
import org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher.fixed.CreateFADVMenu;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.PercentDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.repository.MemoryVoucherRepository;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.InputMismatchException;

import static org.assertj.core.api.Assertions.*;

class CreatePDVMenuTest {

    CreatePDVMenu menu;
    VoucherService voucherService;

    @BeforeEach
    void setup() {
        voucherService = new VoucherService(new MemoryVoucherRepository());
    }

    @Nested
    class constructorTest {
        @Test
        void whenCreatePDVMenu_thenChoiceIsSet(){
            //action
            menu = new CreatePDVMenu(voucherService);

            //assert
            assertThat(menu.getChoice()).isEqualTo(CreateVoucherMenuChoice.PERCENT);
        }
    }

    @Nested
    class procTest {

        @Test
        void givenInput20_whenProcMenu_thenFADVInserted() {
            //setup
            String input = "20";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            menu = new CreatePDVMenu(voucherService);

            //action
            menu.proc();

            //assert
            assertThat(voucherService.findAll()).hasSize(1);
            assertThat(voucherService.findAll().get(0))
                    .isInstanceOf(PercentDiscountVoucher.class);
            PercentDiscountVoucher voucher = (PercentDiscountVoucher) voucherService.findAll().get(0);
            assertThat(voucher.getPercent()).isEqualTo(20L);
        }

        @Test
        void givenIllegalRangeInput_whenProcMenu_thenThrowsIllegalArgumentException() {
            //setup
            String input = "-2000";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            menu = new CreatePDVMenu(voucherService);

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
            menu = new CreatePDVMenu(voucherService);

            //assert throws
            assertThatThrownBy(() -> menu.proc())
                    .isInstanceOf(InputMismatchException.class);
        }
    }
}