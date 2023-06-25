package org.prgrms.kdt.voucher.util;

import org.prgrms.kdt.exception.DatabaseReadException;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class Converter {
    public static String voucherToString(Voucher voucher){
        return MessageFormat.format("{0},{1},{2}", voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount());
    }

    public static String[] stringToArray(String originalString, String delimiter){
        return originalString.split(delimiter);
    }

    public static Voucher stringArrToVoucher(String[] StringArr){
        if (StringArr[1].equals("FixedAmountVoucher")){
            return new FixedAmountVoucher(UUID.fromString(StringArr[0]));
        }
        if (StringArr[1].equals("PercentDiscountVoucher")){
            return new PercentDiscountVoucher(UUID.fromString(StringArr[0]));
        }
        throw new DatabaseReadException();
    }
}
