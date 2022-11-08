package org.programmers.spbw1.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
//    private static final int MAX_AMOUNT = 100000;
//    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private final UUID Id;
    private final long amount;

    public FixedAmountVoucher(UUID id, long amount) {
//        if (amount <= 0 || amount > MAX_AMOUNT){
//            logger.error("invalid voucher");
//            // throw new IllegalArgumentException("Invalid amount \tvalid range : 1 ~ " + MAX_AMOUNT);
//        }
        this.Id = id;
        this.amount = amount;
    }


    @Override
    public UUID getVoucherID() {
        return this.Id;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString(){
        return "ID : " + this.Id + " , Type : Fixed Amount, Discount Amount : " + this.amount;
    }
}
