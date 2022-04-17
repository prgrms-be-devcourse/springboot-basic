package org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CreateVoucherMenuChoiceTest {

    @Test
    void givenStringFixed_whenCallResolve_thenReturnEnumFixed(){
        //setup
        String in = "fIxEd";

        //action
        CreateVoucherMenuChoice choice = CreateVoucherMenuChoice.resolve(in);

        //assert
        assertThat(choice).isEqualTo(CreateVoucherMenuChoice.FIXED);
    }

    @Test
    void givenStringPercent_whenCallResolve_thenReturnEnumPercent(){
        //setup
        String in = "PERcent";

        //action
        CreateVoucherMenuChoice choice = CreateVoucherMenuChoice.resolve(in);

        //assert
        assertThat(choice).isEqualTo(CreateVoucherMenuChoice.PERCENT);
    }

    @Test
    void givenIllegalString_whenCallResolve_thenReturnNull(){
        //setup
        String in = "나를 넣으면 널이 나오지";

        //action
        CreateVoucherMenuChoice choice = CreateVoucherMenuChoice.resolve(in);

        //assert
        assertThat(choice).isNull();
    }
}