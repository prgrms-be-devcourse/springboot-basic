package org.devcourse.voucher.application.voucher.controller.api;

import org.devcourse.voucher.application.voucher.controller.dto.VoucherRequest;
import org.devcourse.voucher.application.voucher.model.Voucher;

import java.util.List;

public interface VoucherController {

    Voucher postCreateVoucher(VoucherRequest voucherRequest);

    List<Voucher> getVoucherList();

}
