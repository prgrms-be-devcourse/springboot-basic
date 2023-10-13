package team.marco.vouchermanagementsystem;

import org.springframework.stereotype.Component;

@Component
public class VoucherApplication  {
    private final Console console;

    public VoucherApplication(Console console) {
        this.console = console;
    }

    public void run() {
        while (true) {
            console.printCommandManual();
            String input = console.readString();

            if(input.equals("exit")) {
                System.out.println("종료 되었습니다.");

                return;
            }
        }
    }
}
