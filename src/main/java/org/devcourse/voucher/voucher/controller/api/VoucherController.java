package org.devcourse.voucher.voucher.controller.api;

import org.devcourse.voucher.voucher.controller.dto.CreateVoucherRequest;
import org.devcourse.voucher.voucher.model.Voucher;

import java.util.List;

public interface VoucherController {

    Voucher postCreateVoucher(CreateVoucherRequest createVoucherRequest);

    List<Voucher> getVoucherList();
}
