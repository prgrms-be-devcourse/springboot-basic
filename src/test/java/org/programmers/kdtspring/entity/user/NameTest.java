package org.programmers.kdtspring.entity.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @Test
    @DisplayName("이름 생성 성공")
    void testCreateName() {
        String nameInput = "jiwoong";

        Name name = new Name(nameInput);

        assertThat(name).isNotNull();
    }

    @Test
    @DisplayName("이름 생성 실패")
    void testCreateNameFailed() {
        String nameInput = "";

        assertThatThrownBy(() -> new Name(nameInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name should not be blank");
    }
}