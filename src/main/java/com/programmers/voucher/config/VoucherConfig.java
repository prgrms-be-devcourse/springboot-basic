package com.programmers.voucher.config;

import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class VoucherConfig {
    private final VoucherProperties properties;

    @Autowired
    public VoucherConfig(VoucherProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Wini wini() throws IOException {

        File file = new File(properties.getSavePath());
        if (!file.exists()) {
            file.createNewFile();
        }

        return new Wini(file);
    }
}
