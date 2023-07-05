package com.ray.junho.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class VoucherPeriodTest {

    @DisplayName("생성일이 만료일 보다 이후일 경우 예외를 발생한다")
    @Test
    void when_CreatedTimeIsAfterExpirationTime_Expects_ThrowException() {
        // Given
        LocalDateTime createdAt =LocalDateTime.of(2023, 8, 5, 0, 0);
        LocalDateTime expireAt = LocalDateTime.of(2023, 7, 5, 0, 0);

        // When, Then
        assertThatThrownBy(() -> new VoucherPeriod(createdAt, expireAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성일과 만료일이 동일할 경우 예외를 발생한다")
    @Test
    void when_CreatedTimeAndExpirationTimeIsSame_Expects_ThrowException() {
        // Given
        LocalDateTime createdAt =LocalDateTime.of(2023, 7, 5, 0, 0);
        LocalDateTime expireAt = LocalDateTime.of(2023, 7, 5, 0, 0);

        // When, Then
        assertThatThrownBy(() -> new VoucherPeriod(createdAt, expireAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성일이 만료일 보다 이전일 경우 예외를 발생하지 않는다")
    @Test
    void when_CreatedTimeIsBeforeExpirationTime_Expects_DoesNotThrowException() {
        // Given
        LocalDateTime createdAt = LocalDateTime.of(2023, 7, 5, 0, 0);
        LocalDateTime expireAt = LocalDateTime.of(2023, 8, 5, 0, 0);

        // When, Then
        assertThatNoException()
                .isThrownBy(() -> new VoucherPeriod(createdAt, expireAt));
    }

    @DisplayName("생성일과 만료일이 동일한 객체의 동등성을 보장한다")
    @Test
    void when_GivenSameCreatedAndExpirationTime_Expects_SameObject() {
        // Given
        LocalDateTime createdAt = LocalDateTime.of(2023, 7, 5, 0, 0);
        LocalDateTime expireAt = LocalDateTime.of(2023, 8, 5, 0, 0);

        // When
        VoucherPeriod voucherPeriod1 = new VoucherPeriod(createdAt, expireAt);
        VoucherPeriod voucherPeriod2 = new VoucherPeriod(createdAt, expireAt);

        // Then
        assertThat(voucherPeriod1).isEqualTo(voucherPeriod2);
        assertThat(voucherPeriod1).hasSameHashCodeAs(voucherPeriod2);
    }
}