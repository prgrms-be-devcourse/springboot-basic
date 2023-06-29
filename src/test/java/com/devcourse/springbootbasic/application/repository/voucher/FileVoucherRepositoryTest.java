package com.devcourse.springbootbasic.application.repository.voucher;

import com.devcourse.springbootbasic.SpringbootBasicApplication;
import com.devcourse.springbootbasic.application.Platform;
import com.devcourse.springbootbasic.application.constant.YamlProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.yaml.snakeyaml.Yaml;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
@SpringJUnitConfig(classes = {FileVoucherRepository.class, YamlProperties.class})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles({"dev"})
class FileVoucherRepositoryTest {

    @Test
    @DisplayName("파일 저장 바우처 레포지토리 생성 시 성공")
    void 빈생성테스트() {
        YamlProperties yamlProperties = new YamlProperties();
        System.out.println(yamlProperties.getVersion());
    }

}