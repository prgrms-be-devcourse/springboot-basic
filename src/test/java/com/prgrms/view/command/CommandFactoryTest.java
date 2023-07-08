package com.prgrms.view.command;

import com.prgrms.controller.VoucherController;
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
    void CreateExitCommand_ExitCommand_True() {
        //when
        Command exitCommand = commandFactory.createExitCommand();

        //then
        assertThat(exitCommand).isInstanceOf(ExitCommand.class);
    }

    @Test
    void CreateCreateCommand_CreateCommand_True() {
        //when
        Command createCommand = commandFactory.createCreateCommand();

        //then
        assertThat(createCommand).isInstanceOf(CreateCommand.class);
    }

    @Test
    void CreateListCommand_ListCommand_True() {
        //when
        Command listCommand = commandFactory.createListCommand();

        //then
        assertThat(listCommand).isInstanceOf(ListCommand.class);
    }
}
