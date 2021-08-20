package org.prgrms.kdt.voucher;

import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/20 Time: 7:44 오후
 */
public record VoucherData(String voucherNumber, UUID id, Long rate) {

}
