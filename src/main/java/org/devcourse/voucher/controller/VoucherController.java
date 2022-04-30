package org.devcourse.voucher.controller;

import org.devcourse.voucher.model.menu.ListMenuType;
import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.model.VoucherType;

import java.util.List;

public interface VoucherController {

    Voucher createVoucherMapper(VoucherType voucher, long discount);

    List<?> findListMapper(ListMenuType listType);
}
