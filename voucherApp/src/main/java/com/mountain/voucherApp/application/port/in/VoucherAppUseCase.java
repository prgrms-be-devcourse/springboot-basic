package com.mountain.voucherApp.application.port.in;


import com.mountain.voucherApp.adapter.out.persistence.voucher.VoucherEntity;
import com.mountain.voucherApp.adapter.out.file.BlackListFileFormat;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface VoucherAppUseCase {

    boolean create(VoucherCreateDto voucherCreateDto);

    List<VoucherEntity> showVoucherList();

    List<CustomerDto> showCustomerVoucherInfo();

    void addVoucher(VoucherIdUpdateDto voucherIdUpdateDto);

    void removeVoucher(UUID customerId);

    List<CustomerDto> showByVoucher(UUID voucherId);

    List<BlackListFileFormat> getBlackList() throws IOException;

}
