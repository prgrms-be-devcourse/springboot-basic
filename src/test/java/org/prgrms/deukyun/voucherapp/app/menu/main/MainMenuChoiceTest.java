package org.prgrms.deukyun.voucherapp.app.menu.main;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MainMenuChoiceTest {

    @Test
    void givenStringExit_whenCallResolve_thenReturnEnumExit(){
        //setup
        String in = "eXiT";

        //action
        MainMenuChoice choice = MainMenuChoice.resolve(in);

        //assert
        assertThat(choice).isEqualTo(MainMenuChoice.EXIT);
    }

    @Test
    void givenStringCreate_whenCallResolve_thenReturnEnumCreate(){
        //setup
        String in = "Create";

        //action
        MainMenuChoice choice = MainMenuChoice.resolve(in);

        //assert
        assertThat(choice).isEqualTo(MainMenuChoice.CREATE);
    }

    @Test
    void givenStringList_whenCallResolve_thenReturnEnumList(){
        //setup
        String in = "List";

        //action
        MainMenuChoice choice = MainMenuChoice.resolve(in);

        //assert
        assertThat(choice).isEqualTo(MainMenuChoice.LIST);
    }


    @Test
    void givenIllegalString_whenCallResolve_thenReturnNull(){
        //setup
        String in = "나를 넣으면 널이 나오지";

        //action
        MainMenuChoice choice = MainMenuChoice.resolve(in);

        //assert
        assertThat(choice).isNull();
    }
}