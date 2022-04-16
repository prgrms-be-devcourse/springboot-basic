package kdt.vouchermanagement;

public enum Menu {
    EXIT_PROGRAM("exit"),
    CREATE_VOUCHER("create"),
    LIST_VOUCHERS("list"),
    LIST_BLACK_CUSTOMERS("black");

    private String menu;

    Menu(String menu) {
        this.menu = menu;
    }

    public static Menu getMenu(String input) {
        return null;
    }
}
