package org.prgrms.kdt.management;

import org.prgrms.kdt.io.IO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import static org.prgrms.kdt.management.Console.ConsoleCommand.*;

@Component
public class Console {

    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private final VoucherManagement voucherManagement;
    private final CustomerManagement customerManagement;
    private final WalletManagement walletManagement;
    private final IO io;
    private final Map<ConsoleCommand, Management> managementMap;

    public Console(VoucherManagement voucherManagement, CustomerManagement customerManagement, WalletManagement walletManagement, @Qualifier("consoleIo") IO io) {
        this.voucherManagement = voucherManagement;
        this.customerManagement = customerManagement;
        this.walletManagement = walletManagement;
        this.io = io;
        this.managementMap = new EnumMap<>(ConsoleCommand.class);
    }

    public void run() {
        try {
            printInitMenu();
            String input = io.readLine().trim().toUpperCase();

            ConsoleCommand.validateCommand(input);
            ConsoleCommand command = ConsoleCommand.of(input);

            if (command == EXIT)
                System.exit(1);

            Management management = managementMap.get(command);
            management.run();

        } catch (RuntimeException argumentException) {
            logger.info("User input is invalid");
            try {
                io.writeLine("Invalid input try again: " + argumentException.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException ioException) {
            logger.error("IOException : " + ioException.getMessage());
            System.exit(1);
        }

    }

    private void printInitMenu() throws IOException {
        io.writeLine("=== Management Program ===");
        io.writeLine("Type voucher to manage voucher");
        io.writeLine("Type customer to manage customer");
        io.writeLine("Type wallet to manage wallet");
        io.writeLine("Type exit to exit program");
    }

    @PostConstruct
    public void setup() {
        managementMap.put(VOUCHER, voucherManagement);
        managementMap.put(CUSTOMER, customerManagement);
        managementMap.put(WALLET, walletManagement);
    }


    enum ConsoleCommand {
        VOUCHER("VOUCHER"), CUSTOMER("CUSTOMER"), WALLET("WALLET"), EXIT("EXIT");

        private final String value;

        ConsoleCommand(String value) {
            this.value = value;
        }

        static void validateCommand(String command) {
            Arrays.stream(ConsoleCommand.values())
                    .filter(cmd -> isEqual(cmd, command))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Wrong command"));
        }

        static ConsoleCommand of (String command) {
            return Arrays.stream(ConsoleCommand.values())
                    .filter(cmd -> isEqual(cmd, command))
                    .findFirst()
                    .get();
        }

        private static boolean isEqual(ConsoleCommand cmd, String command) {
            return cmd.getValue().equals(command);
        }

        public String getValue() {
            return value;
        }
    }
}
