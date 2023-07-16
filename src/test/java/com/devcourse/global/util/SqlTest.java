package com.devcourse.global.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.devcourse.global.util.Sql.Table.USERS;
import static org.assertj.core.api.Assertions.assertThat;

class SqlTest {
    @Test
    @DisplayName("insert 구문이 정상적으로 생성된다.")
    void insertSqlBuildTest() {
        // given
        String expected = "INSERT INTO users(id, name, email, created_at) VALUES (?, ?, ?, ?)";

        // when
        String sql = Sql.builder()
                .insertInto(USERS)
                .values("id", "name", "email", "created_at")
                .build();

        // then
        assertThat(sql).isEqualTo(expected);
    }

    @Test
    @DisplayName("update 구문이 정상적으로 생성된다.")
    void updateSqlBuildTest() {
        // given
        String expected = "UPDATE users SET name = ?, email = ?, last_login_at = ? WHERE id = ?";

        // when
        String sql = Sql.builder()
                .update(USERS)
                .set("name", "email", "last_login_at")
                .where("id")
                .build();

        // then
        assertThat(sql).isEqualTo(expected);
    }

    @Test
    @DisplayName("select 구문이 정상적으로 생성된다.")
    void selectSqlBuildTest() {
        // given
        String expected = "SELECT * FROM users";

        // when
        String sql = Sql.builder()
                .select("*")
                .from(USERS)
                .build();

        // then
        assertThat(sql).isEqualTo(expected);
    }

    @Test
    @DisplayName("select where 구문이 정상적으로 생성된다.")
    void selectWhereSqlBuildTest() {
        // given
        String expected = "SELECT * FROM users WHERE id = ?";

        // when
        String sql = Sql.builder()
                .select("*")
                .from(USERS)
                .where("id")
                .build();

        // then
        assertThat(sql).isEqualTo(expected);
    }

    @Test
    @DisplayName("delete 구문이 정상적으로 생성된다.")
    void deleteSqlBuildTest() {
        // given
        String expected = "DELETE FROM users";

        // when
        String sql = Sql.builder()
                .deleteFrom(USERS)
                .build();

        // then
        assertThat(sql).isEqualTo(expected);
    }

    @Test
    @DisplayName("select where 구문이 정상적으로 생성된다.")
    void deleteFromSqlBuildTest() {
        // given
        String expected = "DELETE FROM users WHERE id = ?";

        // when
        String sql = Sql.builder()
                .deleteFrom(USERS)
                .where("id")
                .build();

        // then
        assertThat(sql).isEqualTo(expected);
    }
}
