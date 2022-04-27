package org.prgrms.vouchermanager.domain.blacklist.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.blacklist.domain.Blacklist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CsvBlacklistRepositoryTest {

    private static final String SAVE_PATH = "src/test/resources/customer_blacklist.csv";

    private CsvBlacklistRepository csvBlacklistRepository;

    @BeforeEach
    void init() {
        csvBlacklistRepository = new CsvBlacklistRepository(SAVE_PATH);
        csvBlacklistRepository.init();
    }

    @Test
    @DisplayName("블랙리스트 고객 목록 반환")
    void getBlacklist() throws IOException {
        List<String> blacklistEmails = new ArrayList<>(Files.readAllLines(Path.of(SAVE_PATH)));

        List<Blacklist> findBlacklist = csvBlacklistRepository.getAll();
        List<String> findEmails = findBlacklist.stream().map(Blacklist::getEmail).collect(Collectors.toList());

        assertThat(findBlacklist.size()).isEqualTo(blacklistEmails.size());
        assertThat(findEmails).containsAll(blacklistEmails);
    }

    @Test
    @DisplayName("블랙리스트를 Id로 찾는다")
    void findById() {
        Blacklist blacklist = csvBlacklistRepository.findByEmail("blacklist01@email.com").get();

        Blacklist findById = csvBlacklistRepository.findById(blacklist.getId()).get();

        assertThat(findById).isEqualTo(blacklist);
    }

    @Test
    @DisplayName("블랙리스트를 이메일로 찾는다")
    void findByEmail() {
        Blacklist blacklist = csvBlacklistRepository.findByEmail("blacklist01@email.com").get();

        assertThat(blacklist.getEmail()).isEqualTo("blacklist01@email.com");
    }
}