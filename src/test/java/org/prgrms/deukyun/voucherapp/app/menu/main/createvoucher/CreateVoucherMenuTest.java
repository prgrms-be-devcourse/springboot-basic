package org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.prgrms.deukyun.voucherapp.app.menu.Menu;
import org.prgrms.deukyun.voucherapp.app.menu.main.MainMenuChoice;
import org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher.fixed.CreateFADVMenu;
import org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher.percent.CreatePDVMenu;
import org.prgrms.deukyun.voucherapp.domain.voucher.repository.MemoryVoucherRepository;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateVoucherMenuTest {

    CreateVoucherMenu menu;
    VoucherService voucherService;
    List<Menu<CreateVoucherMenuChoice>> menus;
    CreateFADVMenu mockCreateFADVMenu;
    CreatePDVMenu mockCreatePDVMenu;

    @BeforeEach
    void setup() {
        voucherService = new VoucherService(new MemoryVoucherRepository());
        mockCreateFADVMenu = mock(CreateFADVMenu.class, withSettings().useConstructor(voucherService));
        mockCreatePDVMenu = mock(CreatePDVMenu.class, withSettings().useConstructor(voucherService));
        menus = List.of(mockCreateFADVMenu, mockCreatePDVMenu);

        when(mockCreateFADVMenu.getChoice()).thenReturn(CreateVoucherMenuChoice.FIXED);
        when(mockCreatePDVMenu.getChoice()).thenReturn(CreateVoucherMenuChoice.PERCENT);
    }

    @Nested
    class constructorTest {
        @Test
        void givenMenusOfSizeTwo_whenConstructCreateVoucherMenu_thenIsCreatedAndChoiceIsSet(){
            //action
            menu = new CreateVoucherMenu(menus);

            //assert
            assertThat(menu).isNotNull();
            assertThat(menu.getChoice()).isEqualTo(MainMenuChoice.CREATE);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void givenNullAndEmptyMenus_whenConstructCreateVoucherMenu_thenThrowsIllegalArgumentException(List<Menu<CreateVoucherMenuChoice>> menus){
            //assert throws
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new CreateVoucherMenu(menus));
        }
    }

    @Nested
    class procTest {

        @Test
        void givenInputFiXeD_whenCallProc_thenPrintsFIXEDAndCallCreateFADVMenuProc() {
            //setup
            String input = "FiXeD";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));

            menu = new CreateVoucherMenu(menus);

            //action
            menu.proc();

            //assert & verify
            assertThat(out.toString()).contains("FIXED");
            verify(mockCreateFADVMenu).proc();
        }

        @Test
        void givenInputpErCeNt_whenCallProc_thenPrintsPERCENTAndCallCreatePDVMenuProc() {
            //setup
            String input = "pErCeNt";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));

            menu = new CreateVoucherMenu(menus);

            //action
            menu.proc();

            //assert & verify
            assertThat(out.toString()).contains("PERCENT");
            verify(mockCreatePDVMenu).proc();
        }

        @Test
        void givenIllegalInput_whenCallProc_thenPrintsInvalidChoice() {
            //setup
            String input = "나는 Illegal 인풋 ";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));

            menu = new CreateVoucherMenu(menus);

            //action
            menu.proc();

            //assert
            assertThat(out.toString()).contains("Invalid Choice");
        }
    }
}