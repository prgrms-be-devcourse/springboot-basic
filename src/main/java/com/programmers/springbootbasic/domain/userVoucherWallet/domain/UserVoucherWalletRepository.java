package com.programmers.springbootbasic.domain.userVoucherWallet.domain;

import com.programmers.springbootbasic.common.Repository;
import com.programmers.springbootbasic.domain.userVoucherWallet.domain.entity.UserVoucherWallet;
import com.programmers.springbootbasic.domain.voucher.infrastructure.dto.UserVoucherWalletWithUser;
import com.programmers.springbootbasic.domain.voucher.infrastructure.dto.UserVoucherWalletWithVoucher;
import java.util.List;
import java.util.UUID;

public interface UserVoucherWalletRepository extends Repository<Long, UserVoucherWallet> {

    List<UserVoucherWalletWithVoucher> findVoucherByUserId(Long userId);

    List<UserVoucherWalletWithUser> findUserByVoucherId(UUID voucherId);
}
