package com.devcourse.springbootbasic.application.repository.voucher;

import com.devcourse.springbootbasic.application.constant.YamlProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

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