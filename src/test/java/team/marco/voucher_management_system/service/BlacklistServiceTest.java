package team.marco.voucher_management_system.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team.marco.voucher_management_system.repository.custromer.BlacklistRepository;
import team.marco.voucher_management_system.repository.custromer.CsvBlacklistRepository;
import team.marco.voucher_management_system.repository.custromer.CustomerIdAndName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BlacklistServiceTest {
    private BlacklistService blacklistService;
    private BlacklistRepository blacklistRepository;

    @BeforeEach
    void VoucherService_초기화() {
        blacklistRepository = new CsvBlacklistRepository("src/test/resources/test_blacklist.csv");
        blacklistService = new BlacklistService(blacklistRepository);
    }

    @Test
    void 블랙_리스트_조회() {
        // test_blacklist 파일에 2명의 블랙리스트가 있을 때

        // 블랙리스트 조회
        List<CustomerIdAndName> blacklist =  blacklistService.getBlacklist();

        // 블랙리스트 크기가 2
        assertThat(blacklist).hasSize(2);
    }
}