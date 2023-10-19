package org.programmers.springorder.console;

import org.junit.jupiter.api.*;
import org.programmers.springorder.utils.MenuType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.InputMismatchException;

import static org.assertj.core.api.Assertions.*;

class ConsoleTest {

    private static final Logger log = LoggerFactory.getLogger(ConsoleTest.class);
//    private static Console console;
//
//    @BeforeAll
//    static void init() {
//        this.console = new Console();
//    }


    @Test
    @DisplayName("유효한 메뉴 번호 입력하는 성공 테스트")
    void inputMenuSuccess(){
        String simulatedInput = "1\n2\n3";
        InputStream sysInBackup = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Console console = new Console(); // TODO: 테스트 클래스 필드로 콘솔 초기화하면 사용이 안 됨!
//        Scanner scanner = new Scanner(System.in);

//        String selectedMenu = scanner.nextLine();
//        MenuType menuType = MenuType.selectMenu(selectedMenu);
        MenuType menuType1 = console.inputMenu();
        MenuType menuType2 = console.inputMenu();
        MenuType menuType3 = console.inputMenu();
        System.setIn(sysInBackup);

        assertThat(menuType1).isEqualTo(MenuType.EXIT);
        log.info("menuType1 : {}", menuType1.getMenuNum());

        assertThat(menuType2).isEqualTo(MenuType.CREATE);
        log.info("menuType2 : {}", menuType2.getMenuNum());

        assertThat(menuType3).isEqualTo(MenuType.LIST);
        log.info("menuType3 : {}", menuType3.getMenuNum());
    }

    //TODO: 예외처리로 인해 더 이상 예외가 발생하지 않아서 생기는 test
    @Test
    @DisplayName("유효하지 않은 메뉴 번호 입력하는 실패 테스트")
    @Disabled
    void inputMenuFail(){
        String simulatedInput = "4\ncreate";
        InputStream sysInBackup = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Console console = new Console(); // TODO: 테스트 클래스 필드로 콘솔 초기화하면 사용이 안 됨!
//        Scanner scanner = new Scanner(System.in);

//        String selectedMenu = scanner.nextLine();
//        MenuType menuType = MenuType.selectMenu(selectedMenu);
        System.setIn(sysInBackup);


        assertThatThrownBy(() -> console.inputMenu())
                .isInstanceOf(InputMismatchException.class)
                .hasMessage("유효하지 않은 값입니다. 다시 입력해주세요.");

        assertThatThrownBy(() -> console.inputMenu())
                .isInstanceOf(InputMismatchException.class)
                .hasMessage("유효하지 않은 값입니다. 다시 입력해주세요.");
    }



}