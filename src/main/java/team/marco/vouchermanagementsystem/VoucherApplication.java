package team.marco.vouchermanagementsystem;

import org.springframework.stereotype.Component;

@Component
public class VoucherApplication  {
    private final Console console;

    public VoucherApplication(Console console) {
        this.console = console;
    }

    public void run() {
        console.printCommandManual();
        String input = console.readString();

        try {
            CommandType commandType = CommandType.getCommandType(input);
            runCommand(commandType);
        } catch (IllegalArgumentException e) {
            System.out.printf("'%s' is invalid command%n%n", input);
            run();
        }
    }

    public void runCommand(CommandType commandType) {
        switch (commandType) {
            case CREATE -> createVoucher();
            case LIST -> getVoucherList();
            case EXIT -> {
                close();

                return;
            }
        }

        run();
    }

    private void createVoucher() {
    }

    private void getVoucherList() {
    }

    private void close() {
        // TODO: 데이터 저장 기능
        System.out.println("exit program...");
    }
}
