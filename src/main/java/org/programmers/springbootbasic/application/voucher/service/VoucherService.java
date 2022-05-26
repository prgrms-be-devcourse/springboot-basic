package org.programmers.springbootbasic.application.voucher.service;

import javassist.NotFoundException;
import org.programmers.springbootbasic.application.voucher.controller.api.CreateVoucherRequest;
import org.programmers.springbootbasic.application.voucher.controller.api.UpdateVoucherRequest;
import org.programmers.springbootbasic.application.voucher.model.Voucher;
import org.programmers.springbootbasic.application.voucher.model.VoucherType;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    Voucher getVoucher(UUID voucherId) throws NotFoundException;

    List<Voucher> getVoucherList();

    List<Voucher> getVoucherListByVoucherType(VoucherType voucherType);

    List<Voucher> getVoucherListOrderByCreatedAt();

    Voucher createVoucher(CreateVoucherRequest voucherRequest);

    Voucher updateVoucher(UpdateVoucherRequest updateVoucherRequest) throws NotFoundException;

    void deleteVoucher(UUID voucherId);
}
