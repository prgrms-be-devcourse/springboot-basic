package team.marco.voucher_management_system;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import team.marco.voucher_management_system.console_app.ConsoleApplication;
import team.marco.voucher_management_system.util.Console;
import team.marco.voucher_management_system.web_app.WebApplication;

@ComponentScan
@ConfigurationPropertiesScan
public class VoucherManagementSystemApplication {
    public static void main(String[] args) {
        Console.print("""
                === 실행할 애플리케이션을 선택해주세요. ===
                0. Console Application
                1. Web Application""");

        selectApplication(args);

        Console.close();
    }

    private static void selectApplication(String[] args) {
        String input = Console.readString();

        switch (input) {
            case "0" -> ConsoleApplication.main(args);
            case "1" -> WebApplication.main(args);
            default -> reselect(args);
        }
    }

    private static void reselect(String[] args) {
        Console.print("사용할 수 없는 애플리케이션 입니다.");

        selectApplication(args);
    }
}
