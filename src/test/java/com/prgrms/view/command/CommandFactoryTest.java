package com.prgrms.view.command;

import com.prgrms.controller.VoucherController;
import com.prgrms.view.Menu;
import com.prgrms.view.ViewManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

class CommandFactoryTest {
    @Mock
    private VoucherController voucherController;

    @Mock
    private ViewManager viewManager;

    private CommandFactory commandFactory;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        commandFactory = new CommandFactory(voucherController, viewManager);
    }

    @Test
    void createExitCommand_ExitCommand_True() {
        //given
        Menu menu = Menu.EXIT;

        //when
        Command exitCommand = commandFactory.createCommand(menu);

        //then
        assertThat(exitCommand).isInstanceOf(ExitCommand.class);
    }

    @Test
    void createCreateCommand_CreateCommand_True() {
        //given
        Menu menu = Menu.CREATE;

        //when
        Command createCommand = commandFactory.createCommand(menu);

        //then
        assertThat(createCommand).isInstanceOf(CreateCommand.class);
    }

    @Test
    void createListCommand_ListCommand_True() {
        //given
        Menu menu = Menu.LIST;

        //when
        Command listCommand = commandFactory.createCommand(menu);

        //then
        assertThat(listCommand).isInstanceOf(ListCommand.class);
    }
}
