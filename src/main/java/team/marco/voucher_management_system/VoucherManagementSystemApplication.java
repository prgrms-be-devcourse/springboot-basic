package team.marco.voucher_management_system;

import java.util.function.Consumer;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import team.marco.voucher_management_system.type_enum.ApplicationType;
import team.marco.voucher_management_system.util.Console;

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
        Consumer<String[]> mainMethod = getMainMethod();

        mainMethod.accept(args);
    }

    private static Consumer<String[]> getMainMethod() {
        try {
            int input = Console.readInt();

            return ApplicationType.getMainMethod(input);
        } catch (IllegalArgumentException e) {
            Console.print("사용할 수 없는 애플리케이션 입니다.");

            return getMainMethod();
        }
    }
}
