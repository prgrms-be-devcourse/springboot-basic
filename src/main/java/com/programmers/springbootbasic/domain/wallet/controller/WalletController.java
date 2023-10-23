package com.programmers.springbootbasic.domain.wallet.controller;

import com.programmers.springbootbasic.common.response.model.CommonResult;
import com.programmers.springbootbasic.common.response.service.ResponseFactory;
import com.programmers.springbootbasic.domain.wallet.dto.WalletRequestDto;
import com.programmers.springbootbasic.domain.wallet.entity.Wallet;
import com.programmers.springbootbasic.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.programmers.springbootbasic.domain.customer.exception.ErrorMsg.EMAIL_TYPE_NOT_MATCH;
import static com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg.UUID_FORMAT_MISMATCH;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WalletController {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final WalletService walletService;

    private static void verifyEmail(String email) throws IllegalArgumentException {
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException(EMAIL_TYPE_NOT_MATCH.getMessage());
        }
    }

    public CommonResult addWallet(String email, String voucherId) {
        try {
            verifyEmail(email);
            walletService.createWallet(WalletRequestDto.builder()
                    .email(email)
                    .voucherId(UUID.fromString(voucherId))
                    .build());
        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
            if (e.getMessage().contains("UUID")) {
                return ResponseFactory.getFailResult(UUID_FORMAT_MISMATCH.getMessage());
            }
            return ResponseFactory.getFailResult(e.getMessage());
        } catch (Exception e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(e.getMessage());
        }
        return ResponseFactory.getSuccessResult();
    }

    public CommonResult findWalletsByCustomerEmail(String email) {
        StringBuilder sb = new StringBuilder();
        try {
            verifyEmail(email);
            List<Wallet> wallets = walletService.findWalletsByCustomerEmail(WalletRequestDto.builder()
                    .email(email)
                    .build());
            sb.append(String.format("--- %s user Vouchers ---\n", email));
            for (Wallet wallet : wallets) {
                sb.append(String.format("%s\n", wallet.getVoucherId().toString()));
            }
        } catch (Exception e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(e.getMessage());
        }
        return ResponseFactory.getSuccessResult(sb.toString());
    }

    public CommonResult findWalletsByVoucherId(String voucherId) {
        StringBuilder sb = new StringBuilder();
        try {
            List<Wallet> wallets = walletService.findWalletsByVoucherId(WalletRequestDto.builder()
                    .voucherId(UUID.fromString(voucherId))
                    .build());
            sb.append(String.format("--- Contain %s Voucher User list ---\n", voucherId));
            for (Wallet wallet : wallets) {
                sb.append(String.format("%s\n", wallet.getEmail()));
            }
        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(UUID_FORMAT_MISMATCH.getMessage());
        } catch (Exception e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(e.getMessage());
        }
        return ResponseFactory.getSuccessResult(sb.toString());
    }

    public CommonResult deleteWallet(String email, String voucherId) {
        try {
            verifyEmail(email);
            walletService.deleteWallet(WalletRequestDto.builder()
                    .email(email)
                    .voucherId(UUID.fromString(voucherId))
                    .build());
        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
            if (e.getMessage().contains("UUID")) {
                return ResponseFactory.getFailResult(UUID_FORMAT_MISMATCH.getMessage());
            }
            return ResponseFactory.getFailResult(e.getMessage());
        } catch (Exception e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(e.getMessage());
        }
        return ResponseFactory.getSuccessResult();
    }

}
