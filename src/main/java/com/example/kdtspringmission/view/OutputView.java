package com.example.kdtspringmission.view;

import com.example.kdtspringmission.voucher.domain.Voucher;
import java.util.List;

public interface OutputView {

    void commandList();

    void creatableVoucherList();

    void voucherList(List<Voucher> vouchers);
}
