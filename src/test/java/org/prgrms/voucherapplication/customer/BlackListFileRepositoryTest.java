package org.prgrms.voucherapplication.customer;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.voucherapplication.customer.repository.BlackListFileRepository;
import org.prgrms.voucherapplication.dto.ResponseBlacklist;
import org.prgrms.voucherapplication.voucher.service.CsvFileService;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringJUnitConfig
@DisplayName("블랙리스트 Repository 테스트")
class BlackListFileRepositoryTest {

    CsvFileService csvFileService = new CsvFileService();

    final String filePath = "src/main/resources/customer_blacklist.csv";
    BlackListFileRepository blackListFileRepository = new BlackListFileRepository(filePath, csvFileService);



    @DisplayName("블랙리스트 고객 전체를 찾으면")
    @Nested
    class whenFindAll {

        @Test
        @DisplayName("csv 파일 -> List<DTO> 알맞게 맵핑되어 반환된다.")
        void mappedDto() throws IOException {
            File file = new File(filePath);
            List<String> fileLines = Files.readAllLines(file.toPath());

            List<String> names = blackListFileRepository.findAll().stream()
                    .map(ResponseBlacklist::getName)
                    .collect(Collectors.toList());

            assertThat(names, Matchers.hasSize(fileLines.size()));

            for (String line : fileLines) {
                line = line.replaceAll("[^ㄱ-ㅎ가-힣]", "");
                Assertions.assertThat(names).contains(line);
            }
        }
    }
}