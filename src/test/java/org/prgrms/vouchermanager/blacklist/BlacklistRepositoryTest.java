package org.prgrms.vouchermanager.blacklist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class BlacklistRepositoryTest {

    private static final String SAVE_PATH = "C:\\Users\\Hyunji\\Desktop\\etc\\repositories\\springboot-basic\\src\\main\\resources\\";

    @Test
    @DisplayName("init_csv에서_블랙리스트를_읽어온다")
    void init_csv에서_블랙리스트를_읽어온다() {
        BlacklistRepository blacklistRepository = new BlacklistRepository(SAVE_PATH);
        assertThatNoException().isThrownBy(blacklistRepository::init);
    }

    @Test
    @DisplayName("블랙리스트 고객 목록 반환")
    void getBlacklist() {
        BlacklistRepository blacklistRepository = new BlacklistRepository(SAVE_PATH);
        blacklistRepository.init();
        assertThat(blacklistRepository.getAll()).isNotEmpty();
    }


    @Test
    @DisplayName("아이디로 블랙리스트에서 조회한다.")
    void findById_아이디로_블랙리스트에서_조회한다() {
        BlacklistRepository blacklistRepository = new BlacklistRepository(SAVE_PATH);
        blacklistRepository.init();
        Optional<Blacklist> byId = blacklistRepository.findById(UUID.fromString("5153948e-bf99-11ec-9d64-0242ac120002"));
        assertThat(byId.get()).isInstanceOf(Blacklist.class);
    }
}