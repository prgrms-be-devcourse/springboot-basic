package prgms.vouchermanagementapp.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import prgms.vouchermanagementapp.config.AppConfig;
import prgms.vouchermanagementapp.io.MenuType;

class VoucherManagementControllerTest {
    AnnotationConfigApplicationContext applicationContext
            = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    void should_throw_exception_for_invalid_menuType() {
        String invalidInput = "INVALID";

        Assertions.assertThrows(NoSuchFieldError.class, () -> {
            MenuType.of(invalidInput);
        });
    }
}