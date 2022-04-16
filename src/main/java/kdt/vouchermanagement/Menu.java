package kdt.vouchermanagement;

public enum Menu {
    EXIT_PROGRAM("exit"),
    CREATE_VOUCHER("create"),
    LIST_VOUCHERS("list"),
    BLACKLIST("black");

    private String menu;

    Menu(String menu) {
        this.menu = menu;
    }

    public static Menu from(String input) {
        return null;
    }
}
