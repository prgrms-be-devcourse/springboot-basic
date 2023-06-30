package org.promgrammers.springbootbasic.domain.wallet.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;

import java.util.UUID;


@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Wallet {
    private final UUID walletId;
    private Voucher voucher;
    private Customer customer;

    public Wallet(Voucher voucher, Customer customer) {
        this(UUID.randomUUID(), voucher, customer);
    }
}
