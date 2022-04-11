package org.prgrms.kdtspringdemo.console;


import org.prgrms.kdtspringdemo.voucher.voucherdetail.VoucherType;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Input {
    Scanner scanner = new Scanner(System.in);

    // menu 입력을 받는다
    public Menu inputMenu() {
        String inputMenu = scanner.nextLine();

        List<Menu> menus = Arrays.asList(Menu.values());
        Menu menu = compareInputValueToMenuEnum(inputMenu, menus);
        return menu;
    }

    // Voucher Type 을 입력 받는다.
    public VoucherType inputVoucherType() {
        String inputVoucherType = scanner.nextLine();

        List<VoucherType> voucherTypes = Arrays.asList(VoucherType.values());
        VoucherType voucherType = compareInputValueToVoucherTypeEnum(inputVoucherType, voucherTypes);
        return voucherType;
    }

    // Menu Enum 과 비교하여 입력값을 비교하고 ERROR 경우도 처리해준다.
    private VoucherType compareInputValueToVoucherTypeEnum(String inputVoucherType, List<VoucherType> voucherTypes) {
        for (VoucherType type : voucherTypes) {
            String typeName = type.getTypeName();
            String typeNumber = type.getTypeNumber();
            // 작성자가 error 혹은 0이라고 메뉴를 적는 경우에 대한 대처
            if (inputVoucherType.equals("0") || inputVoucherType.equalsIgnoreCase("error")) {
                System.out.println("Voucher Type 작성에 있어 error 발생");

                return VoucherType.ERROR;

            } else if (inputVoucherType.equals(typeNumber) || inputVoucherType.equalsIgnoreCase(typeName)) {

                return type;
            }
        }
        // 전체 경우에 해당하지 않는 경우 ERROR 로 처리
        return VoucherType.ERROR;
    }


    // Menu 선택을 Menu eunm 과 비교하여 입렵값을 비교하고 ERROR 경우도 처리해 준다.
    private Menu compareInputValueToMenuEnum(String inputMenu, List<Menu> menus) {
        for (Menu menu : menus) {
            String menuName = menu.getMenuName();
            // 작성자가 error 라고 메뉴를 적는 경우에 대한 대처
            if (menuName.equalsIgnoreCase("Error")) {
                System.out.println("Menu 작성에 있어 error 발생");

                return Menu.ERROR;

            } else if (inputMenu.equalsIgnoreCase(menuName)) {

                return menu;
            }
        }
        // 전체 경우에 해당하지 않는 경우 ERROR 로 처리
        return Menu.ERROR;
    }

    // Voucher 타입 선택 후 discount 금액 을 입력받습니다.
    public int inputAmount(VoucherType voucherType) {
        switch (voucherType) {
            case FIXED -> System.out.println("할인 금액을 적으시오(0보다 큰 정수값만 적으시오)");
            case PERCENT -> System.out.println("할인 퍼센트를 적으시오(0 과 100 사이의 정수)");
        }

        // discount 금액을 반환하기 위해서 ERROR 처리를 -1로 처리한다.
        int discountNumber = -1;

        try {
            discountNumber = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("정수가 아닌 값을 입력하였습니다.");

            return -1;
        }
        if (discountNumber < 0) {
            System.out.println("음수를 입력하였습니다.");

            return -1;
        }

        return discountNumber;

    }
}
