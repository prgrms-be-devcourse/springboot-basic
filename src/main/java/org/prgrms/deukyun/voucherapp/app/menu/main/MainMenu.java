package org.prgrms.deukyun.voucherapp.app.menu.main;

import org.prgrms.deukyun.voucherapp.app.menu.Menu;
import org.prgrms.deukyun.voucherapp.app.menu.NoChoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;


/**
 * 메인 메뉴<br>
 * ├퇴장 메뉴<br>
 * ├바우처 생성 메뉴<br>
 * └바우처 목록 출력 메뉴<br>
 * <p>
 * VoucherApp -> MainMenu 클래스 사용<br>
 * 제네릭 타입을 맞춰주기 위해 더미 enum NoChoice 클래스를 넣음
 */
@Component
public class MainMenu extends Menu<NoChoice> {

    private final Logger log = LoggerFactory.getLogger(MainMenu.class);

    private final EnumMap<MainMenuChoice, Menu<MainMenuChoice>> menuMap;
    private final Scanner scanner;

    public MainMenu(List<Menu<MainMenuChoice>> menus) {
        super(null);
        checkArgument(menus != null, "menus must be provided");
        checkArgument(!menus.isEmpty(), "menus must contain at least one Menu for MainMenuChoice");

        menuMap = new EnumMap<MainMenuChoice, Menu<MainMenuChoice>>(menus.stream()
                .collect(Collectors.toMap(Menu::getChoice, menu -> menu)));

        scanner = new Scanner(System.in);
    }

    /**
     * 메인 메뉴 선택을 입력받아<br>
     * 선택에 따른 로직을 실행
     */
    @Override
    public void proc() {
        MainMenuChoice choice = MainMenuChoice.resolve(scanner.nextLine());
        log.info("main menu choice : {}", choice);

        if (choice == null) {
            System.out.println("Invalid Choice");
            return;
        }

        System.out.print(choice.getPrintSelected());
        menuMap.get(choice).proc();
    }
}
