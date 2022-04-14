package org.prgrms.kdt.view;

import java.util.Scanner;
import org.prgrms.kdt.error.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputView {

    private static final Logger logger = LoggerFactory.getLogger(InputView.class);
    private static final Scanner SC = new Scanner(System.in);

    private static final int VALID_SIZE = 2;

    public static Menu inputTheMenu() {
        return Menu.findOf(SC.nextLine());
    }

    public static String[] inputTheVoucher() throws IllegalArgumentException {
        String[] inputs = SC.nextLine().split(" ");

        validateCount(inputs);
        return inputs;
    }

    private static void validateCount(String[] inputs) {
        if (inputs.length != VALID_SIZE) {
            logger.error("inputTheMenu Size => {}", inputs.length);
            throw new IllegalArgumentException(ErrorMessage.INPUT_COUNT_ERROR.getMessage());
        }
    }

}
