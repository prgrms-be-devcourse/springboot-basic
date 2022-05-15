package com.mountain.voucherapp.model.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    @Test
    @DisplayName("유요하지 않은 이메일 포맷은 IllegalArgumentException 발생.")
    public void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("acccc");
        });
    }

    @Test
    @DisplayName("올바른 이메일 형식일 경우 객체가 생성된다.")
    public void testValidEmail() {
        Email email = new Email("hello@gmail.com");
        assertTrue(email.getAddress().equals("hello@gmail.com"));
    }

    @Test
    @DisplayName("동등성 확인. 주소가 달라도 값이 같으면 두 객체는 같다.")
    public void testEqEmail() {
        Email email = new Email("hello@gmail.com");
        Email email2= new Email("hello@gmail.com");
        assertEquals(email, email2);
    }
}