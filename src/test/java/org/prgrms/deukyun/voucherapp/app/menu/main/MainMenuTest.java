package org.prgrms.deukyun.voucherapp.app.menu.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.prgrms.deukyun.voucherapp.app.menu.Menu;
import org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher.CreateVoucherMenu;
import org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher.percent.CreatePDVMenu;
import org.prgrms.deukyun.voucherapp.app.menu.main.exit.ExitButton;
import org.prgrms.deukyun.voucherapp.app.menu.main.exit.ExitMenu;
import org.prgrms.deukyun.voucherapp.app.menu.main.showallvoucher.ShowAllVoucherMenu;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.*;

class MainMenuTest {

    MainMenu menu;
    List<Menu<MainMenuChoice>> menus;
    ExitMenu mockExitMenu;
    CreateVoucherMenu mockCreateVoucherMenu;
    ShowAllVoucherMenu mockShowAllVoucherMenu;
    VoucherService voucherService;

    @BeforeEach
    void setup() {
        mockExitMenu = mock(ExitMenu.class, withSettings().useConstructor(new ExitButton()));
        mockCreateVoucherMenu = mock(CreateVoucherMenu.class, withSettings().useConstructor(List.of(new CreatePDVMenu(voucherService))));
        mockShowAllVoucherMenu = mock(ShowAllVoucherMenu.class, withSettings().useConstructor(voucherService));

        doNothing().when(mockExitMenu).proc();
        doNothing().when(mockCreateVoucherMenu).proc();
        doNothing().when(mockShowAllVoucherMenu).proc();

        when(mockExitMenu.getChoice()).thenReturn(MainMenuChoice.EXIT);
        when(mockCreateVoucherMenu.getChoice()).thenReturn(MainMenuChoice.CREATE);
        when(mockShowAllVoucherMenu.getChoice()).thenReturn(MainMenuChoice.LIST);

        menus = List.of(mockExitMenu, mockCreateVoucherMenu, mockShowAllVoucherMenu);
        menu = new MainMenu(menus);
    }

    @Nested
    class constructorTest {

        @Test
        void givenMenusOfSizeThree_whenConstructMainMenu_thenIsCreatedAndChoiceIsNull() {
            //action
            menu = new MainMenu(menus);

            //assert
            assertThat(menu).isNotNull();
            assertThat(menu.getChoice()).isNull();
        }

        @ParameterizedTest
        @NullAndEmptySource
        void givenNullAndEmptyMenus_whenConstructMainMenu_thenThrowsIllegalArgumentException(List<Menu<MainMenuChoice>> menus) {
            //assert throws
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new MainMenu(menus));
        }
    }

    @Nested
    class procTest {

        @Test
        void givenInputExit_whenCallProc_thenPrintsEXIT() {
            //setup
            String input = "exit";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));

            menu = new MainMenu(menus);

            //action
            menu.proc();

            //assert & verify
            assertThat(out.toString()).contains("EXIT");
            verify(mockExitMenu).proc();
        }

        @Test
        void givenInputCreate_whenCallProc_thenPrintsCREATE() {
            //setup
            String input = "cReAtE";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));

            menu = new MainMenu(menus);

            //action
            menu.proc();

            //assert & verify
            assertThat(out.toString()).contains("CREATE");
            verify(mockCreateVoucherMenu).proc();
        }

        @Test
        void givenInputList_whenCallProc_thenPrintsLIST() {
            //setup
            String input = "list";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));

            menu = new MainMenu(menus);

            //action
            menu.proc();

            //assert & verify
            assertThat(out.toString()).contains("LIST");
            verify(mockShowAllVoucherMenu).proc();
        }

        @Test
        void givenIllegalInput_whenCallProc_thenPrintsInvalidChoice() {
            //setup
            String input = "나는 Invalid 인풋 ";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));

            menu = new MainMenu(menus);

            //action
            menu.proc();

            //assert
            assertThat(out.toString()).contains("Invalid Choice");
        }
    }

}