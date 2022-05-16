package org.devcourse.voucher.voucher.controller;

import org.devcourse.voucher.menu.model.ListMenuType;
import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.model.VoucherType;

import java.util.List;

public interface VoucherController {

    Voucher postCreateVoucher(VoucherType voucher, long discount);

    List<Voucher> getVoucherList();
}
