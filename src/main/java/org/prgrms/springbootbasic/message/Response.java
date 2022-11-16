package org.prgrms.springbootbasic.message;

import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.prgrms.springbootbasic.type.MethodType;

import java.text.MessageFormat;
import java.util.List;

import static org.prgrms.springbootbasic.type.MethodType.CREATE;
import static org.prgrms.springbootbasic.type.MethodType.LOOKUP;

public class Response {
    public static final String VOUCHER_DOES_NOT_EXIST = "voucher does not exist";
    private final MethodType methodType;
    private final List<Voucher> voucherList;

    public Response(MethodType methodType, List<Voucher> voucherList) {
        this.methodType = methodType;
        this.voucherList = voucherList;
    }

    @Override
    public String toString() {
        if (methodType == CREATE) return MessageFormat.format("{0}이 생성되었습니다.", voucherList);
        if (methodType == LOOKUP) {
            if (voucherList.isEmpty()) {
                return VOUCHER_DOES_NOT_EXIST;
            }
            return MessageFormat.format("{0}이 있습니다.", voucherList);
        }
        return voucherList.toString();
    }
}
