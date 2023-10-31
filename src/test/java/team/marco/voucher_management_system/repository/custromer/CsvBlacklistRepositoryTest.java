package team.marco.voucher_management_system.repository.custromer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static team.marco.voucher_management_system.util.UUIDUtil.stringToUUID;

@ActiveProfiles("test")
class CsvBlacklistRepositoryTest {

    private CsvBlacklistRepository csvBlacklistRepository;

    public CsvBlacklistRepositoryTest() {
        csvBlacklistRepository = new CsvBlacklistRepository("src/test/resources/test_blacklist.csv");
    }

    @DisplayName("")
    @Test
    void test() {
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