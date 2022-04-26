package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.voucher.domain.FixedAmountVoucher;
import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.global.io.Output;
import kdt.vouchermanagement.global.response.Response;
import kdt.vouchermanagement.global.view.CommandMessage;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleOutputTest {

    Output consoleOutput = new ConsoleOutput();

    @Test
    void printMenu() {
        // given
        String message = CommandMessage.MENU.getMessage();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // when
        consoleOutput.printMenu(message);

        // then
        assertThat(outputStream.toString()).isEqualTo(message.toString() + "\n");
    }

    @Test
    void printVoucherResponse() {
        // given
        Voucher voucher = new FixedAmountVoucher(1L, VoucherType.FIXED_AMOUNT, 10);
        Response response = Response.of(200, voucher);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // when
        consoleOutput.printResponse(response);

        // then
        assertThat(outputStream.toString()).isEqualTo(response.toString() + "\n");
    }

    @Test
    void printVoucherListResponse() {
        // given
        Voucher voucher = new FixedAmountVoucher(1L, VoucherType.FIXED_AMOUNT, 10);
        Response response = Response.of(200, List.of(voucher));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // when
        consoleOutput.printResponse(response);

        // then
        assertThat(outputStream.toString()).isEqualTo(response.toString() + "\n");
    }
}
