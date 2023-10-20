package com.programmers.springbootbasic.util;

import org.springframework.beans.factory.annotation.Value;

public class FileProperties {
    @Value("${file.user.path}")
    private static String UserFilePath;
    @Value("${file.voucher.path}")
    private static String VoucherFilePath;

    public static String getUserFilePath() {
        return UserFilePath;
    }

    public static String getVoucherFilePath() {
        return VoucherFilePath;
    }
}