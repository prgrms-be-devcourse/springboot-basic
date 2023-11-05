package team.marco.voucher_management_system.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import team.marco.voucher_management_system.controller.customer.dto.CustomerIdAndName;
import team.marco.voucher_management_system.repository.custromer.BlacklistRepository;
import team.marco.voucher_management_system.repository.custromer.CsvBlacklistRepository;
import team.marco.voucher_management_system.service.customer.BlacklistService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class BlacklistServiceTest {
    private BlacklistService blacklistService;
    private BlacklistRepository blacklistRepository;

    public BlacklistServiceTest(@Value("${file.path.blacklist}") String path) {
        blacklistRepository = new CsvBlacklistRepository(path);
        blacklistService = new BlacklistService(blacklistRepository);
    }

    @DisplayName("파일에 저장된 블랙리스트를 조회할 수 있습니다.")
    @Test
    void test() {
        // given - 파일에 미리 2명의 사용자를 추가해 놓았습니다.

        // when
        List<CustomerIdAndName> blacklist =  blacklistService.getBlacklist();

        // then
        assertThat(blacklist).hasSize(2);
    }
}