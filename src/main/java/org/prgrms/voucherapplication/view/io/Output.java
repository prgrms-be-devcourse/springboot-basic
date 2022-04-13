package org.prgrms.voucherapplication.view.io;

import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.entity.Voucher;

import java.util.List;

public interface Output {
    /**
     * 바우처 리스트 출력
     * @param vouchers: Voucher 타입의 List
     */
    void printVoucherList(List<Voucher> vouchers);

    void printBlackList(List<Customer> customers);
}
