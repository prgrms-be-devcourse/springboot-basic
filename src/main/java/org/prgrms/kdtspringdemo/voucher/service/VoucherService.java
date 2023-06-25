package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.prgrms.kdtspringdemo.voucher.model.vo.VoucherVO;

import java.util.List;

public interface VoucherService {
    VoucherVO createVoucher(VoucherVO voucher);

    List<Voucher> getAllVoucher();
}
