package org.prgrms.kdt.view;

import java.util.Scanner;
import org.prgrms.kdt.error.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputView {

    private static final Logger log = LoggerFactory.getLogger(InputView.class);
    private static final Scanner SC = new Scanner(System.in);


    private static final int VALID_SIZE = 2;

    public static Command getCommand() {
        return Command.of(SC.nextLine());
    }

    public static String[] inputTheVoucher() throws IllegalArgumentException {
        String[] inputs = SC.nextLine().split(" ");

        if (inputs.length != VALID_SIZE) {
            log.error("inputTheMenu Size => {}", inputs.length);
            throw new IllegalArgumentException(ErrorMessage.INPUT_COUNT_ERROR.getMessage());
        }
        try {
            Long.parseLong(inputs[1]);
        } catch (NumberFormatException e) {
            log.error(ErrorMessage.INPUT_NUMBER_ERROR.getMessage(), e);
        }

        return inputs;
    }

}
