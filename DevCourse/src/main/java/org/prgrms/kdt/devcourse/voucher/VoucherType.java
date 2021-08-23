package org.prgrms.kdt.devcourse.voucher;

import java.util.Arrays;
import java.util.Locale;

public enum VoucherType {
    FIXED,
    PERCENT;

    public static boolean isExistType(String input){
        for(VoucherType eachType : values()){
            if(eachType.toString().equals(input))
                return true;
        }
        return false;
    }
}
