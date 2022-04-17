package org.prgrms.deukyun.voucherapp.app.menu.main.showallvoucher;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.app.menu.main.MainMenuChoice;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.PercentDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.repository.MemoryVoucherRepository;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class ShowAllVoucherMenuTest {

    VoucherService voucherService = new VoucherService(new MemoryVoucherRepository());
    ShowAllVoucherMenu menu;

    @Nested
    class constructorTest{

        @Test
        void whenCreatePDVMenu_thenChoiceIsSet(){
            //action
            menu = new ShowAllVoucherMenu(voucherService);

            //assert
            assertThat(menu.getChoice()).isEqualTo(MainMenuChoice.LIST);
        }
    }


    @Nested
    class procTest{

        @Test
        void givenTwoVoucherInsertion_whenCallProc_thenPrintsTwoVoucher(){
            //setup
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            menu = new ShowAllVoucherMenu(voucherService);
            voucherService.insert(new FixedAmountDiscountVoucher(2000L));
            voucherService.insert(new PercentDiscountVoucher(20L));

            //action
            menu.proc();

            //assert
            assertThat(out.toString()).contains("[Fixed Amount Discount Voucher]");
            assertThat(out.toString()).contains("amount : 2000");
            assertThat(out.toString()).contains("[Percent Discount Voucher]");
            assertThat(out.toString()).contains("percent : 20");
        }
    }
}