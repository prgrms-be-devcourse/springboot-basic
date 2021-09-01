package org.prgrms.kdt.engine.io;

import org.prgrms.kdt.engine.voucher.domain.Voucher;

import java.util.Map;
import java.util.UUID;

public interface Output {
    void help();
    void showVoucherOptions();
    void createVoucher(Voucher voucher);
    void listVoucher(Map<UUID, Voucher> voucherList);
    void allocateCustomer(String[] uuids);
    void deleteCustomerVoucher(String customerUUID);
    void printIllegalInputError();
    void printVoucherListNotFoundError();
}
