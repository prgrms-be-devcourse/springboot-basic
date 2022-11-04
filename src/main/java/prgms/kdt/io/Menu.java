package prgms.kdt.io;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Menu {
    GUIDE("=== Voucher Program ==="),
    EXIT("Type exit to exit the program."),
    CREATE("Type create to create a new voucher."),
    LIST("Type list to list all vouchers.");

    private final String literal;

    Menu(String literal) {
        this.literal = literal;
    }

    public List<String> getMenuLiterals() {
        return Arrays.stream(Menu.values())
                .map(Menu::toString)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return this.literal;
    }
}
