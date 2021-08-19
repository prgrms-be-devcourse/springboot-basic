package org.prgrms.kdt.config;

import org.prgrms.kdt.KdtApplication;
import org.prgrms.kdt.io.FileIo;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@Configuration
@ComponentScan(basePackageClasses = {KdtApplication.class})
@PropertySource(value = "application.yml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {

    private static final String PATH = "db.txt";

    @Bean
    public FileIo fileIo() throws IOException {
        return FileIo.createFileIo(PATH);
    }
}

