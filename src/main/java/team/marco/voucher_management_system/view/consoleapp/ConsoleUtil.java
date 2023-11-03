package team.marco.voucher_management_system.view.consoleapp;

import team.marco.voucher_management_system.controller.voucher.dto.VoucherResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;

import static java.text.MessageFormat.format;
import static team.marco.voucher_management_system.view.consoleapp.ConsoleMessage.INQUIRY_COMPLETE;
import static team.marco.voucher_management_system.view.consoleapp.ConsoleMessage.WRONG_INPUT;

public final class ConsoleUtil {
    private static final String INFO_DELIMINATOR = "\n";
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private ConsoleUtil() {
        // Don't let anyone instantiate this class.
    }

    public static void print(Object object) {
        System.out.print(object + System.lineSeparator());
    }

    public static void println(Object object) {
        print(object);
        System.out.println();
    }

    public static void println() {
        System.out.println();
    }

    public static void printStringList(List<String> list) {
        String joinedString = String.join(INFO_DELIMINATOR, list);

        if (!joinedString.isBlank()) {
            ConsoleUtil.println(joinedString);
        }

        ConsoleUtil.println(INQUIRY_COMPLETE);
    }

    public static String readString() {
        System.out.print("> ");
        String input = readLine();
        System.out.println();

        if (Objects.isNull(input)) {
            throw new RuntimeException(WRONG_INPUT);
        }

        return input;
    }

    public static int readInt() {
        return Integer.parseInt(readString());
    }

    private static String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void printVoucherList(List<VoucherResponse> vouchers) {
        vouchers.forEach(v -> {
            printVoucher(v);
            printSeparatorLine();
        });
    }

    private static void printSeparatorLine() {
        print("--------------------");
    }

    public static void printVoucher(VoucherResponse voucher) {
        print(format("id   : {0}", voucher.getId()));
        print(format("name : {0}", voucher.getName()));
        print(format("code : {0}", voucher.getCode()));
    }
}
