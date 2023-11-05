package team.marco.voucher_management_system.repository.custromer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import team.marco.voucher_management_system.controller.customer.dto.CustomerIdAndName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static team.marco.voucher_management_system.util.UUIDUtil.stringToUUID;

@SpringBootTest
@ActiveProfiles("test")
class CsvBlacklistRepositoryTest {
    private CsvBlacklistRepository csvBlacklistRepository;

    public CsvBlacklistRepositoryTest(@Value("${file.path.blacklist}") String path) {
        csvBlacklistRepository = new CsvBlacklistRepository(path);
    }

    @DisplayName("파일에 저장된 사용자 블랙리스트를 조회할 수 있습니다.")
    @Test
    void findAll() {
        /**
         *  test_blacklist.csv에
         *  id                                      name
         *  461b8847-578d-43c7-8472-d9374bbac41a    정의진
         *  461b8847-578d-43c7-8472-d9374bbac42b    김현우
         *  를 미리 넣어 놨습니다.
         */

        // when
        List<CustomerIdAndName> blacklist = csvBlacklistRepository.findAll();

        // then
        assertThat(blacklist).hasSize(2);
        assertThat(blacklist)
                .extracting("id", "name")
                .containsExactlyInAnyOrder(
                        tuple(stringToUUID("461b8847-578d-43c7-8472-d9374bbac41a"), "정의진"),
                        tuple(stringToUUID("461b8847-578d-43c7-8472-d9374bbac42b"), "김현우")
                );
    }
}