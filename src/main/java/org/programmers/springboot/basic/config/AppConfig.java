package org.programmers.springboot.basic.config;

import org.programmers.springboot.basic.VoucherManagementApplication;

import java.util.Objects;

public class AppConfig {

    public static boolean isRunningFromJar() {
        return Objects.requireNonNull(VoucherManagementApplication.class.getResource("VoucherManagementApplication.class")).toString().startsWith("jar:");
    }
}
