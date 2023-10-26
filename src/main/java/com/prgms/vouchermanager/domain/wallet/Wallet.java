package com.prgms.vouchermanager.domain.wallet;

import com.prgms.vouchermanager.domain.voucher.Voucher;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Wallet {

    private Long id;
    private final Long customer_id;
    private final UUID voucher_id;

    public Wallet(Long id, Long customer_id, UUID voucher_id) {
        this.id = id;
        this.customer_id = customer_id;
        this.voucher_id = voucher_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", customer_id=" + customer_id +
                ", voucher_id=" + voucher_id +
                '}';
    }
}
