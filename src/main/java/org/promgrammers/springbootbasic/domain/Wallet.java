package org.promgrammers.springbootbasic.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Wallet {

    private List<Voucher> vouchers;

    public Wallet() {
        this.vouchers = new ArrayList<>();
    }

    public void addVoucher(Voucher voucher) {
        this.vouchers.add(voucher);
    }

    public void removeVoucher(Voucher voucher) {
        this.vouchers.remove(voucher);
    }

    public boolean isContainsVoucher(Voucher voucher) {
        return this.vouchers.contains(voucher);
    }
}