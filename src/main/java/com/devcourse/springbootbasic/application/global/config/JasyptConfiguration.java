package com.devcourse.springbootbasic.application.global.config;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class JasyptConfiguration {

    @Value("${jasypt.encryptor.algorithm}")
    private String algorithm;

    @Value("${jasypt.encryptor.pool-size}")
    private int poolSize;

    @Value("${jasypt.encryptor.string-output-type}")
    private String stringOutputType;

    @Value("${jasypt.encryptor.key-obtention-iterations}")
    private int keyObtentionIterations;

    @Value("${jasypt.encryptor.password")
    private String password;

    @Bean
    public StringEncryptor jasyptStringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig configuration = new SimpleStringPBEConfig();
        configuration.setAlgorithm(algorithm);
        configuration.setPoolSize(poolSize);
        configuration.setStringOutputType(stringOutputType);
        configuration.setKeyObtentionIterations(keyObtentionIterations);
        configuration.setPassword(getJasyptEncryptorPassword());
        encryptor.setConfig(configuration);
        return encryptor;
    }

    private String getJasyptEncryptorPassword() {
        try {
            ClassPathResource resource = new ClassPathResource("src/main/resources/jasypt-encryptor-password.txt");
            return String.join("", Files.readAllLines(Paths.get(resource.getPath())));
        } catch (IOException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_FILE_ACCESS.getMessageText(), e.getCause());
        }
    }
}

