package org.prgrms.orderApp.presentation.commandOperator.pages;

import org.prgrms.orderApp.infrastructure.library.console.Console;
import org.prgrms.orderApp.infrastructure.library.console.script.BasicScript;
import org.prgrms.orderApp.infrastructure.library.console.script.ForUxScript;
import org.prgrms.orderApp.presentation.commandOperator.script.ApplicationScript;
import org.prgrms.orderApp.presentation.commandOperator.script.MonguDbScript;
import org.prgrms.orderApp.presentation.commandOperator.script.WarnningAndErrorScript;
import org.prgrms.orderApp.presentation.commandOperator.util.*;
import org.springframework.stereotype.Service;


@Service
public class MainPage {

    private Console console;
    private CheckInvalid checkInvalid;

    private String userSelected;

    public MainPage(Console console, CheckInvalid checkInvalid){
        this.console = console;
        this.checkInvalid = checkInvalid;
    }
    public void introduceApp(){
        console.print(ForUxScript.DIVISION_LINE_BOLDER);
        console.infoMessage(ApplicationScript.START_MESSAGE);
        console.print(ForUxScript.DIVISION_BLANK);
    }

    public String selectedMainMenu(){
        console.infoMessage(BasicScript.GUIDE_MESSAGE);
        console.infoMessage(ApplicationScript.INPUT_USER_SELECTED_MENU_NUMBER__GUIDE_MESSAGE);
        userSelected = console.input(BasicScript.INPUT_USER_SELECTED_MENU_NUMBER);
        //System.out.println(userSelected);
        console.print(ForUxScript.DIVISION_BLANK);

        // 메뉴 선택을 숫자로 했는지 확인
        if(checkInvalid.checkInteger(userSelected)){
            console.errorMessage(WarnningAndErrorScript.INVALID_VALUE__MUST_WRITE_DOWN_NUMBER);
            return "";
        }
        return MainMenuPage.getMenuName(Integer.parseInt(userSelected));
    }

    public void selectedInvalidMenu(){
        console.print(ForUxScript.DIVISION_LINE);
        console.infoMessage(WarnningAndErrorScript.SELECTED_NUMBER_LIMIT_ERROR);
        console.print(ForUxScript.DIVISION_LINE);
    }

    public void apologizeMessage(){
        console.infoMessage(WarnningAndErrorScript.APOLOGIZE);
    }

    public String exit() {
        console.print(ForUxScript.DIVISION_LINE);
        console.infoMessage(WarnningAndErrorScript.EXIT_WARNING_MESSAGE);
        userSelected = console.input(WarnningAndErrorScript.MESSAGE_BEFORE_EXIT);
        console.print(ForUxScript.DIVISION_BLANK);
        return userSelected;

    }

    public void sayBye(){
        console.infoMessage("Bye");
    }

    public void SelectedInvalidMenuNumber(){
        console.print(ForUxScript.DIVISION_LINE);
        console.errorMessage(WarnningAndErrorScript.SELECTED_NUMBER_LIMIT_ERROR);
        console.print(ForUxScript.DIVISION_BLANK);
    }




}
