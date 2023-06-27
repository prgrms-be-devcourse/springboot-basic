package com.wonu606.vouchermanager.domain;

import java.util.Collection;
import java.util.Iterator;

public class Vouchers implements Iterable<Voucher> {

    private final Collection<Voucher> vouchers;

    public Vouchers(Collection<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    @Override
    public Iterator<Voucher> iterator() {
        return vouchers.iterator();
    }

    public int size() {
        return vouchers.size();
    }
}
