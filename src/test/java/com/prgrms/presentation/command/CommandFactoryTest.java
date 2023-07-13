package com.prgrms.presentation.command;


import com.prgrms.presentation.Menu;
import com.prgrms.presentation.view.ViewManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CommandCreatorTest {

    @Mock
    private ViewManager viewManager;

    private CommandCreator commandCreator;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        commandFactory = new CommandFactory(voucherController, viewManager);
    }

    @Test
    @DisplayName("EXIT라는 메뉴를 받아 ExitCommad를 반환한다.")
    void createExitCommand_ExitCommand_True() {
        //given
        Menu menu = Menu.EXIT;

        //when
        Command exitCommand = commandFactory.createCommand(menu);

        //then
        assertThat(exitCommand).isInstanceOf(ExitCommand.class);
    }

    @Test
    @DisplayName("CREATE라는 메뉴를 받아 CreateCommad를 반환한다.")
    void createCreateCommand_CreateCommand_True() {
        //given
        Menu menu = Menu.CREATE;

        //when
        Command createCommand = commandFactory.createCommand(menu);

        //then
        assertThat(createCommand).isInstanceOf(CreateCommand.class);
    }

    @Test
    @DisplayName("LIST라는 메뉴를 받아 ListCommad를 반환한다.")
    void createListCommand_ListCommand_True() {
        //given
        Menu menu = Menu.LIST;

        //when
        Command listCommand = commandFactory.createCommand(menu);

        //then
        assertThat(listCommand).isInstanceOf(ListCommand.class);
    }
}
