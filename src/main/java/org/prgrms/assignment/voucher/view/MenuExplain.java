package org.prgrms.assignment.voucher.view;

public enum MenuExplain {
    EXIT("Type exit to exit the program."),
    CREATE("Type create to create a new voucher."),
    LIST("Type list to list all vouchers.");

    private final String menuExplain;

    MenuExplain(String menuExplain) {
        this.menuExplain = menuExplain;
    }

    public String getMenuExplain() {
        return menuExplain;
    }
}
