package org.prgrms.springbootbasic;

import org.prgrms.springbootbasic.voucher.Voucher;

import java.util.ArrayList;
import java.util.List;

import static org.prgrms.springbootbasic.Menu.CREATE;
import static org.prgrms.springbootbasic.Menu.LIST;

public class Response {
    private final Menu menuItem;
    private final List<Voucher> voucherList = new ArrayList<>();

    public Response(Menu menuItem) {
        this.menuItem = menuItem;
    }

    @Override
    public String toString() {
        if (menuItem == CREATE) return voucherList.toString() + "이 생성되었습니다.";
        if (menuItem == LIST) return voucherList.toString() + "이 있습니다.";
        return voucherList.toString();
    }

    public List<Voucher> getVoucherList() {
        return voucherList;
    }

    public void addVoucherList(Voucher... vouchers) {
        voucherList.addAll(List.of(vouchers));
    }
}
