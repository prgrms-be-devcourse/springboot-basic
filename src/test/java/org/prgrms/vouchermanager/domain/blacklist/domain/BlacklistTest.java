package org.prgrms.vouchermanager.domain.blacklist.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class BlacklistTest {

    @Test
    void BlackList_생성_성공() {
        final Blacklist blacklist = new Blacklist("blackList@gmail.com");

        assertThat(blacklist.getId()).isNotNull();
        assertThat(blacklist.getEmail()).isEqualTo("blackList@gmail.com");
    }

    @Test
    @DisplayName("email은 공백이 될 수 없다.")
    void email은_공백이_될_수_없다() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Blacklist("  "));
    }

    @Test
    @DisplayName("email은 null이 될 수 없다.")
    void email은_null이_될_수_없다() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Blacklist(null));
    }

    @Test
    @DisplayName("email은 @가 포함되야 한다.")
    void email은_골뱅이가_포함되야_한다() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Blacklist("EmailWithoutAt.com"));
    }
}