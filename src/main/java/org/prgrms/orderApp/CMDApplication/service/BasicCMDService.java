package org.prgrms.orderApp.CMDApplication.service;

import org.prgrms.orderApp.CMDApplication.console.Console;
import org.prgrms.orderApp.CMDApplication.console.script.AllScriptForConsole;
import org.prgrms.orderApp.CMDApplication.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BasicCMDService implements AllScriptForConsole{

    @Autowired
    private Console console;

    @Autowired
    private CheckInvalid checkInvalid;

    private String userSelected;

    public void introduceApp(){
        console.print(divisionLineBolder);
        console.infoMessage(startMessage);
        console.print(divisionBlank);
    }

    public String selectedMainMenu(){
        console.infoMessage(guideMessage);
        console.infoMessage(inputUserSelectedMenuNumber_GuideMessage);
        userSelected = console.input(inputUserSelectedMenuNumber);
        //System.out.println(userSelected);
        console.print(divisionBlank);

        // 메뉴 선택을 숫자로 했는지 확인
        if(checkInvalid.checkInteger(userSelected)){
            console.errorMessage(invalidValue_MustWriteDownNumber);
            return "";
        }
        return MainMenuPage.getMenuName(Integer.parseInt(userSelected));
    }

    public void selectedInvalidMenu(){
        console.print(divisionLine);
        console.infoMessage(selectedNumber_LimitError);
        console.print(divisionLine);
    }

    public void apologizeMessage(){
        console.infoMessage(apologize);
    }

    public String exit() {
        console.print(divisionLine);
        console.infoMessage(exit_WarringMessage);
        userSelected = console.input(messageBeforeExit);
        console.print(divisionBlank);
        return userSelected;

    }

    public void sayBye(){
        console.infoMessage("Bye");
    }
    public void SelectedInvalidMenuNumber(){
        console.print(divisionLine);
        console.errorMessage(selectedNumber_LimitError);
        console.print(divisionBlank);
    }




}
