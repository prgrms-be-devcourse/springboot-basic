package org.prgrms.vouchermanager.blacklist;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class BlacklistTest {
    @Test
    @DisplayName("email은 공백이 될 수 없다.")
    void email은_공백이_될_수_없다() {
        Assertions.assertThatThrownBy(() -> new Blacklist(UUID.randomUUID(), "  ")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("email은 @가 포함되야 한다.")
    void email은_골뱅이가_포함되야_한다() {
        Assertions.assertThatThrownBy(() -> new Blacklist(UUID.randomUUID(), "  ")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("UUID는 공백이 될 수 없다.")
    void UUID는_공백이_될_수_없다() {
        Assertions.assertThatThrownBy(() -> new Blacklist(null, "test01@email.com")).isInstanceOf(IllegalArgumentException.class);
    }
}