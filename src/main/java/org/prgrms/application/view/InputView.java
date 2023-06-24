package org.prgrms.application.view;

import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class InputView {

    private final Scanner sc = new Scanner(System.in);

    public String selectCommandType(){
        String selection = sc.nextLine();
        return selection != null ? selection : null;
    }


}
