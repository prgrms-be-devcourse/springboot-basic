package com.programmers.voucher.config;

import org.ini4j.Wini;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class VoucherConfig {

    private final String path;

    public VoucherConfig(VoucherProperties info) {
        this.path = info.getSavePath();
    }

    @Bean
    public Wini wini() throws IOException {

        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }

        return new Wini(file);
    }
}
