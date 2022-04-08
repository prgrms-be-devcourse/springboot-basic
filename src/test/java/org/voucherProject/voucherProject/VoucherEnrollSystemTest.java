package org.voucherProject.voucherProject;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.javafaker.Number;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

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
            Assertions.assertThrows(RuntimeException.class, () -> voucherEnrollSystem.validateInput(name));
        }
    }

    @Test
    public void exitSystemOk() throws Exception {

        // exit
        String exit = "exit";
        assertThat(voucherEnrollSystem.exitSystem(exit)).isTrue();
    }

    @Test
    public void checkVoucherType() throws Exception {

        // 1, 2   //VoucherType의 int value를 따른다.
        String inputOne = "1";
        assertThat(voucherEnrollSystem.checkVoucherType(inputOne).get()).isEqualTo(VoucherType.FIXED);

        String inputTwo = "2";
        assertThat(voucherEnrollSystem.checkVoucherType(inputTwo).get()).isEqualTo(VoucherType.PERCENT);


    }

    @Test
    public void checkVoucherTypeElse() throws Exception {

        Faker faker = new Faker();

        for (int i = 0; i < 100; i++) {
            int inputNum = faker.number().randomDigit();
            if (inputNum == 1 || inputNum == 2) {
                continue;
            }
            Assertions.assertThrows(RuntimeException.class,
                    () -> voucherEnrollSystem.checkVoucherType(String.valueOf(inputNum)));
        }
    }

}