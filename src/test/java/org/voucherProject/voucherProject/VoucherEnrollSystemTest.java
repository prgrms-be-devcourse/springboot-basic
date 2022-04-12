package org.voucherProject.voucherProject;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.javafaker.Number;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VoucherEnrollSystemTest {

    @Autowired
    VoucherEnrollSystem voucherEnrollSystem;

    @Test
    public void validateInputOk() throws Exception {

        // exit , list, create
        String exit = "exit";
        String list = "list";
        String create = "create";
        voucherEnrollSystem.validateInput(exit);
        voucherEnrollSystem.validateInput(list);
        voucherEnrollSystem.validateInput(create);

    }

    @Test
    public void validateInputError() throws Exception {

        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            String name = faker.name().fullName();
            Assertions.assertThrows(IllegalArgumentException.class, () -> voucherEnrollSystem.validateInput(name));
        }
    }

    @Test
    public void exitSystemOk() throws Exception {

        // exit
        String exit = "exit";
        assertThat(voucherEnrollSystem.exitSystem(exit)).isTrue();
    }
}