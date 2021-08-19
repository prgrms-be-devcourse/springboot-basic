package org.prgrms.kdt;

import org.prgrms.kdt.Config.AppConfiguration;
import org.prgrms.kdt.Service.VoucherService;
import org.prgrms.kdt.IO.Input;
import org.prgrms.kdt.IO.Output;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class VoucherTester implements Runnable{

    private final String commandGuide = "사용 가능한 명령은 3가지 입니다. 한번에 하나의 명령만 가능합니다.\n바우처 생성하기 : create\nvoucher 리스트 보기 : list\n종료하기 : exit 를 입력하세요.";
    private final String voucherGuide = "fixed형 voucher를 생성하려면 f를, percent형 voucher를 생성하려면 p를 입력하세요.";
    private final String voucherInfoGuide = "fixed amount 또는 percent discount를 입력하세요.";
    private final String create = "create";
    private final String list = "list";
    private final String exit = "exit";

    private boolean exitProgram = false;


    // exception은 input이 잘못된 경우만 고려함.
    // SingleTon 객체로 해야될 것들이 있을까?
    @Override
    public void run() {
        Input input = new Console();
        Output output = new Console();

        //applicationContext interface의 구현체 생성
        //AppConfiguration.class : metadata , bean의 description이 기술되어있음
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);

        while (!exitProgram){
            try {
                String command = parseCommand(input.input(commandGuide));

                switch (command) {
                    case exit:
                        output.exit();
                        exitProgram = true;
                        break;
                    case create:
                        char voucherType = parseVoucherType(input.input(voucherGuide));
                        long voucherInfo = parseInfo(input.input(voucherInfoGuide));

                        voucherService.createVoucher(voucherType, voucherInfo);
                        output.voucherCreateSuccess();
                        break;
                    case list:
                        voucherService.showAllVoucherList();
                        break;
                }
            } catch (InputException inputException){
                //System.out.println(inputException.getMessage());
                output.inputError(inputException.getMessage());

            }
        }
    }

    private char parseVoucherType (String input) throws InputException{
        switch (input){
            case "f":
                return 'f';
            case "p":
                return 'p';
            default:
                throw new InputException();
        }
    }


    private String parseCommand (String inputString) throws InputException{
        switch(inputString) {
            case create:
            case list:
            case exit:
                break;
            default: throw new InputException();
        }
        return inputString;
    }


    private int parseInfo(String inputString) throws InputException{
        if(inputString.chars().allMatch(i-> i>47 && i<58)) return Integer.parseInt(inputString);
        throw new InputException();
        //inputString.chars().filter(i-> i>48 && i<59);
    }


}
