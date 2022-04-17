package org.prgrms.deukyun.voucherapp.app.menu;

public abstract class InteractiveMenu implements Menu {

    private final Menu parent;

    protected InteractiveMenu(Menu parent) {
        this.parent = parent;
    }

    public abstract void input();
}
