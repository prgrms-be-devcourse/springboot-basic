package org.prgrms.voucherapplication.view.io;

import org.prgrms.voucherapplication.entity.BlackListCustomer;
import org.prgrms.voucherapplication.entity.SqlVoucher;
import org.prgrms.voucherapplication.entity.Voucher;

import java.util.List;

public interface Output {
    /**
     * 바우처 리스트 출력
     *
     * @param vouchers: Voucher 타입의 List
     */
    void printVoucherList(List<Voucher> vouchers);

    /**
     * 블랙리스트의 고객 정보 출력
     *
     * @param customers
     */
    void printBlackList(List<BlackListCustomer> customers);

    /**
     * DB에 있는 바우처 리스트 출력
     *
     * @param vouchers: SqlVoucher 타입의 List
     */
    void printSqlVoucherList(List<SqlVoucher> vouchers);
}
