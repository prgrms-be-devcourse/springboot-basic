package com.programmers.config;

import com.programmers.config.properties.VoucherProperties;
import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;
import java.io.IOException;

@Configuration
@Profile("file")
public class FileConfig {
    private final VoucherProperties properties;

    @Autowired
    public FileConfig(VoucherProperties properties) {
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
