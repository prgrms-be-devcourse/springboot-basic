package com.tangerine.voucher_system.application.global.config;

import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@ConfigurationProperties("jasypt.encryptor")
@EnableEncryptableProperties
public class JasyptConfiguration {

    private String algorithm;
    private int poolSize;
    private String stringOutputType;
    private int keyObtentionIterations;

    @Bean("jasyptStringEncryptor")
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

    public String getAlgorithm() {
        return algorithm;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public String getStringOutputType() {
        return stringOutputType;
    }

    public int getKeyObtentionIterations() {
        return keyObtentionIterations;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public void setStringOutputType(String stringOutputType) {
        this.stringOutputType = stringOutputType;
    }

    public void setKeyObtentionIterations(int keyObtentionIterations) {
        this.keyObtentionIterations = keyObtentionIterations;
    }
}

