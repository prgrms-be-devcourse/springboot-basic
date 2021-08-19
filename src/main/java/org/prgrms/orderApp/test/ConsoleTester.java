package org.prgrms.orderApp.test;

import org.prgrms.orderApp.AppConfiguration;
import org.prgrms.orderApp.ProcessingStatus;
import org.prgrms.orderApp.model.voucher.FixedAmountVoucher;
import org.prgrms.orderApp.io.Console;
import org.prgrms.orderApp.io.Script;
import org.prgrms.orderApp.model.voucher.PercentDiscountVoucher;
import org.prgrms.orderApp.model.voucher.Voucher;
import org.prgrms.orderApp.model.voucher.VoucherType;
import org.prgrms.orderApp.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ConsoleTester {
    public static void main(String[] args) throws InterruptedException {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);

        String userSelected;
        String errorMessage="" ;
        int exit_flag = 0;
        UUID voucherId ;
        Map<String, Object> saveResult;
        Voucher voucherType = null;
        int voucherType_flag = -1;
        String inputAmount ;
        Long inputAmount_longParse;
        Console console = new Console();

        console.infoMessage(Script.StartMessage.getMessage());
        console.print(Script.DivisionBlank.getMessage());

        try {
            while (true) {
                console.infoMessage(Script.GuideMessage.getMessage());
                console.infoMessage(Script.InputUserSelectedMenuNumber_GuideMessage.getMessage());
                userSelected = console.input(Script.InputUserSelectedMenuNumber.getMessage());
                System.out.println(userSelected);
                console.print(Script.DivisionBlank.getMessage());

                // 1: create, 2: show voucher list, 3: exit, 그 외: 사용자가 잘 못 눌렀음.
                switch (userSelected) {
                    case "1":
                        console.print(Script.DivisionBlank.getMessage());
                        console.infoMessage(Script.InputAmount_GuideMessage.getMessage());
                        inputAmount = console.input(Script.InputAmount.getMessage());
                        System.out.println(userSelected);

                        // 숫자로 기입했는지 확인
                        if(!inputAmount.matches("[+-]?\\d*(\\.\\d+)?")){
                            console.errorMessage(Script.VoucherAmount_InvalidValue.getMessage());
                            break;
                        }

                        // 숫자인지 확인 완료 후, long 타입으로 변환
                        inputAmount_longParse = Long.parseLong(inputAmount);

                        // voucher type 선택하기
                        console.infoMessage(Script.GuideMessage.getMessage());
                        console.infoMessage(Script.InputUserSelectedVoucherType_GuideMessage.getMessage());
                        userSelected = console.input(Script.InputUserSelectedVoucherType.getMessage());

                        // 자동으로 voucherId 생성
                        // repository에서 자동으로 생성할지 고민했지만, 우선 밖에서 주입하는 것으로 진행.
                        voucherId = UUID.randomUUID();

                        // 1 : FixedAmountVoucher, 2: PercentDiscountVoucher, 그 외: 사용자가 잘 못 눌렀음
                        // 숫자로 하면 가독성이 떨어지는 것 같은데...ㅠ
                        switch(userSelected){
                            case "1" :
                                voucherType = new FixedAmountVoucher(voucherId, inputAmount_longParse);
                                voucherType_flag = 1;
                                break;
                            case "2" :
                                //percent 값은 100이 넘지 않게 처리. 고객에세 돈을 줄 수는 없으니까.
                                if (inputAmount_longParse > VoucherType.PERCENTDISCOUNT.getLimitAmount()){
                                    voucherType_flag = -1;
                                    errorMessage = Script.Percent_LimitError.getMessage();
                                }else{
                                    voucherType = new PercentDiscountVoucher(voucherId, inputAmount_longParse);
                                    voucherType_flag = 1;
                                }
                                break;
                            default:
                                voucherType_flag = -1;
                                errorMessage =Script.SelectedNumber_LimitError.getMessage();
                                break;
                        }

                        // 위의 유효성 검사 후, 저장단계 진행
                        // voucherType_flag / 1 : 유효성 검사 통과, 2. 유효성 검사 통과 실패
                        if (voucherType_flag==1){
                            saveResult = voucherService.saveVoucher(voucherType);
                            if (saveResult.get("status").equals(ProcessingStatus.SUCCESS)) {
                                console.infoMessage(Script.Success_Amount_Save.getMessage() + saveResult.get("voucherId"));
                            } else {
                                console.errorMessage(Script.VoucherId_DuplicateError.getMessage() + voucherId);
                            }
                        }else {
                            console.print(Script.DivisionLine.getMessage());
                            console.infoMessage(errorMessage);
                            console.print(Script.DivisionBlank.getMessage());
                        }

                        Thread.sleep(500);
                        break;                      
                        
                    case "2":
                        console.infoMessage(Script.ShowVoucherList_GuideMessage.getMessage());
                        List<Voucher> voucherList = voucherService.getAllVouchers();
                        if (voucherList.isEmpty()) {
                            console.infoMessage(Script.EmptyData.getMessage());
                        } else {
                            console.voucherList(voucherList);
                        }
                        console.print(Script.DivisionBlank.getMessage());
                        Thread.sleep(500);
                        break;

                    case "3":
                        console.print(Script.DivisionLine.getMessage());
                        console.infoMessage(Script.Exit_WarringMessage.getMessage());
                        userSelected = console.input(Script.MessageBeforeExit.getMessage());
                        if (userSelected.equals("yes")) {
                            exit_flag = 1;
                        }
                        console.print(Script.DivisionBlank.getMessage());
                        Thread.sleep(500);
                        break;

                    default:
                        console.print(Script.DivisionLine.getMessage());
                        console.infoMessage(Script.SelectedNumber_LimitError.getMessage());
                        console.print(Script.DivisionBlank.getMessage());
                        Thread.sleep(500);
                        break;
                }

                if (exit_flag == 1) {
                    break;
                }
            }
        }catch(Exception e){
            console.infoMessage(Script.Pologize.getMessage());
            System.out.println(e);
        }finally {
            console.infoMessage("Bye");
        }

    }
}
