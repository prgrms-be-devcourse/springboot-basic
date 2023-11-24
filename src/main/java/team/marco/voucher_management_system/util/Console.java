package team.marco.voucher_management_system.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Objects;

public final class Console {
    private static BufferedReader reader;

    private Console() {
        // Don't let anyone instantiate this class.
    }

    public static String readString() {
        System.out.print("> ");
        String input = readLine();
        System.out.println();

        if (Objects.isNull(input)) {
            throw new UncheckedIOException(new IOException("입력 과정에서 오류가 발생했습니다."));
        }

        return input;
    }

    public static int readInt() {
        return Integer.parseInt(readString());
    }

    private static String readLine() {
        try {
            return getInstance().readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void print(Object object) {
        System.out.println(object + System.lineSeparator()); // thanks to SH, IJ
    }

    public static void close() {
        if (reader != null) {
            closeReader();

            reader = null;
        }
    }

    private static BufferedReader getInstance() {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
        return reader;
    }

    private static void closeReader() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
