package org.prgrms.kdt.voucher;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 6:40 오후
 */
public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    int count();

}
