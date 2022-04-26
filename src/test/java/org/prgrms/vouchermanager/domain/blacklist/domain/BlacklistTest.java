package org.prgrms.vouchermanager.domain.blacklist.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class BlacklistTest {

    @Test
    void BlackList_생성_성공() {
        final Blacklist blacklist = new Blacklist("blackList@gmail.com");

        assertThat(blacklist.getId()).isNotNull();
        assertThat(blacklist.getEmail()).isEqualTo("blackList@gmail.com");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("email이 공백이면 예외를 던진다")
    void email이_공백이면_예외를_던진다(String email) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Blacklist(email));
    }

    @Test
    @DisplayName("email은 @가 포함되야 한다.")
    void email은_골뱅이가_포함되야_한다() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Blacklist("EmailWithoutAt.com"));
    }
}