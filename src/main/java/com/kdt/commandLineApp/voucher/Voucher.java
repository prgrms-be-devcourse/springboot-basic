package com.kdt.commandLineApp.voucher;

import com.kdt.commandLineApp.exception.CanNotDiscountException;
import com.kdt.commandLineApp.exception.WrongVoucherParamsException;
import lombok.Getter;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Getter
public class Voucher implements Serializable {
    private final UUID id;
    private VoucherType type;
    private Float discountAmount;
    private Map<String, VoucherType> vouchertypeHashMap;

    public Voucher(String type, Float discountAmount) throws WrongVoucherParamsException {
        this.id = UUID.randomUUID();
        this.type = VoucherType.fromString(type);
        if (this.type.isValidAmount(discountAmount)) {
            this.discountAmount = discountAmount;
        }
        else {
            throw new WrongVoucherParamsException();
        }
    }

    @Override
    public String toString() {
        return "id: " + id + "\ntype: " + type.toString() + "\namount: " + discountAmount.toString() +"\n" ;
    }

    public Float discount(Integer currentPrice) throws CanNotDiscountException {
        if (currentPrice < this.discountAmount) {
            throw new CanNotDiscountException();
        }
        return type.discount(currentPrice, discountAmount);
    }

    public String getType() {
        return type.toString();
    }
}
