package org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher;

import org.prgrms.deukyun.voucherapp.app.menu.Menu;
import org.prgrms.deukyun.voucherapp.app.menu.main.MainMenuChoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 바우처 생성 메뉴<br>
 * ├정액 할인 바우처 생성 메뉴<br>
 * └정률 할인 바우처 생성 메뉴
 */
@Component
public class CreateVoucherMenu extends Menu<MainMenuChoice> {

    private final Logger log = LoggerFactory.getLogger(CreateVoucherMenu.class);

    private final EnumMap<CreateVoucherMenuChoice, Menu<CreateVoucherMenuChoice>> menuMap;
    private final Scanner scanner;

    public CreateVoucherMenu(List<Menu<CreateVoucherMenuChoice>> menus) {
        super(MainMenuChoice.CREATE);
        checkArgument(menus != null, "menus must be provided");
        checkArgument(!menus.isEmpty(), "menus must contain at least one Menu for CreateVoucherMenuChoice");

        menuMap = new EnumMap<CreateVoucherMenuChoice, Menu<CreateVoucherMenuChoice>>(menus.stream()
                .collect(Collectors.toMap(Menu::getChoice, menu -> menu)));

        this.scanner = new Scanner(System.in);
    }

    /**
     * 바우처 생성 메뉴 선택을 입력받아<br>
     * 선택에 따른 로직을 실행
     */
    @Override
    public void proc() {
        CreateVoucherMenuChoice choice = CreateVoucherMenuChoice.resolve(scanner.nextLine());
        log.info("create voucher menu choice : {}", choice);

        if (choice == null) {
            System.out.println("Invalid Choice");
            return;
        }

        System.out.print(choice.getPrintSelected());
        menuMap.get(choice).proc();
    }

}
