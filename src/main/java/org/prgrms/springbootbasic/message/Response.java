package org.prgrms.springbootbasic.message;

import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.prgrms.springbootbasic.type.MethodType;

import java.util.ArrayList;
import java.util.List;

import static org.prgrms.springbootbasic.type.MethodType.CREATE;
import static org.prgrms.springbootbasic.type.MethodType.LOOKUP;

public class Response {
    private final MethodType methodType;
    private final List<Voucher> voucherList = new ArrayList<>();

    public Response(MethodType menuItem) {
        this.methodType = menuItem;
    }

    @Override
    public String toString() {
        if (methodType == CREATE) return voucherList.toString() + "이 생성되었습니다.";
        if (methodType == LOOKUP) return voucherList.toString() + "이 있습니다.";
        return voucherList.toString();
    }

    public List<Voucher> getVoucherList() {
        return voucherList;
    }

    public void addVoucherList(Voucher... vouchers) {
        voucherList.addAll(List.of(vouchers));
    }
}
