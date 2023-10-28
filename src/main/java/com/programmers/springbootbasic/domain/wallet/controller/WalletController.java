package com.programmers.springbootbasic.domain.wallet.controller;

import com.programmers.springbootbasic.common.response.CommonResult;
import com.programmers.springbootbasic.domain.customer.vo.Email;
import com.programmers.springbootbasic.domain.wallet.dto.WalletRequestDto;
import com.programmers.springbootbasic.domain.wallet.entity.Wallet;
import com.programmers.springbootbasic.domain.wallet.service.WalletService;
import com.programmers.springbootbasic.domain.wallet.view.WalletView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

import static com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg.UUID_FORMAT_MISMATCH;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    public CommonResult<String> addWallet(String email, String voucherId) {
        try {
            walletService.createWallet(WalletRequestDto.builder()
                    .email(Email.from(email).getValue())
                    .voucherId(UUID.fromString(voucherId))
                    .build());
        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
            if (e.getMessage().contains("UUID")) {
                return CommonResult.getFailResult(UUID_FORMAT_MISMATCH.getMessage());
            }
            return CommonResult.getFailResult(e.getMessage());
        } catch (Exception e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(e.getMessage());
        }
        return CommonResult.getSuccessResult();
    }

    public CommonResult<String> findWalletsByCustomerEmail(String email) {
        try {
            List<String> vouchers = walletService.findWalletsByCustomerEmail(WalletRequestDto.builder()
                            .email(Email.from(email).getValue())
                            .build())
                    .stream()
                    .map(Wallet::getVoucherId)
                    .map(UUID::toString)
                    .toList();
            return CommonResult.getSingleResult(WalletView.getCustomerWalletView(email, vouchers));
        } catch (Exception e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(e.getMessage());
        }

    }

    public CommonResult<String> findWalletsByVoucherId(String voucherId) {
        try {
            List<String> customers = walletService.findWalletsByVoucherId(WalletRequestDto.builder()
                            .voucherId(UUID.fromString(voucherId))
                            .build())
                    .stream()
                    .map(Wallet::getEmail)
                    .toList();
            return CommonResult.getSingleResult(WalletView.getVoucherWalletView(voucherId, customers));
        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(UUID_FORMAT_MISMATCH.getMessage());
        } catch (Exception e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(e.getMessage());
        }

    }

    public CommonResult<String> deleteWallet(String email, String voucherId) {
        try {
            walletService.deleteWallet(WalletRequestDto.builder()
                    .email(Email.from(email).getValue())
                    .voucherId(UUID.fromString(voucherId))
                    .build());
        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
            if (e.getMessage().contains("UUID")) {
                return CommonResult.getFailResult(UUID_FORMAT_MISMATCH.getMessage());
            }
            return CommonResult.getFailResult(e.getMessage());
        } catch (Exception e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(e.getMessage());
        }
        return CommonResult.getSuccessResult();
    }

}
