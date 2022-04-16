package com.example.voucherproject.common.file;

import com.example.voucherproject.user.domain.User;
import com.example.voucherproject.voucher.domain.Voucher;

public interface MyWriter {
    User writeUser(User user, String filePath);
    Voucher writeVoucher(Voucher voucher, String voucher_list_path);
}
