package org.prgrms.orderApp.CMDProgramForOrderApp.service;

import org.prgrms.orderApp.CMDProgramForOrderApp.console.Console;
import org.prgrms.orderApp.CMDProgramForOrderApp.console.Script;
import org.prgrms.orderApp.CMDProgramForOrderApp.util.CheckInvalid;
import org.prgrms.orderApp.CMDProgramForOrderApp.util.CreateVoucherPageMenu;
import org.prgrms.orderApp.CMDProgramForOrderApp.util.MainMenuPage;
import org.prgrms.orderApp.CMDProgramForOrderApp.util.MonguDbFuntionType;
import org.prgrms.orderApp.model.voucher.FixedAmountVoucher;
import org.prgrms.orderApp.model.voucher.PercentDiscountVoucher;
import org.prgrms.orderApp.model.voucher.Voucher;
import org.prgrms.orderApp.model.voucher.VoucherType;
import org.prgrms.orderApp.monguDb.service.DbManagement;
import org.prgrms.orderApp.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CMDProgramService implements OrderCMDProgramService, VoucherCMDProgramService, MonguDbService {

    @Autowired
    private Console console;

    @Autowired
    private CheckInvalid checkInvalid;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    DbManagement dbManagement;

    private Voucher voucherType;
    private Map<String,Object> saveResult;
    private String errorMessage, userSelectedMenu, userSelected, collectionName;
    private UUID voucherId;



    public void introduceApp(){
        console.infoMessage(Script.StartMessage.getMessage());
        console.print(Script.DivisionBlank.getMessage());
    }

    public String selectedMainMenu(){
        console.infoMessage(Script.GuideMessage.getMessage());
        console.infoMessage(Script.InputUserSelectedMenuNumber_GuideMessage.getMessage());
        userSelected = console.input(Script.InputUserSelectedMenuNumber.getMessage());
        //System.out.println(userSelected);
        console.print(Script.DivisionBlank.getMessage());

        // 메뉴 선택을 숫자로 했는지 확인
        if(checkInvalid.checkInteger(userSelected)){
            console.errorMessage(Script.InvalidValue_MustWriteDownNumber.getMessage());
            return "";
        }
        return MainMenuPage.getMenuName(Integer.parseInt(userSelected));
    }

    public void selectedInvalidMenu(){
        console.print(Script.DivisionLine.getMessage());
        console.infoMessage(Script.SelectedNumber_LimitError.getMessage());
        console.print(Script.DivisionBlank.getMessage());
    }
    public void apologizeMessage(){
        console.infoMessage(Script.Apologize.getMessage());
    }

    public String exit() {
        console.print(Script.DivisionLine.getMessage());
        console.infoMessage(Script.Exit_WarringMessage.getMessage());
        userSelected = console.input(Script.MessageBeforeExit.getMessage());
        console.print(Script.DivisionBlank.getMessage());
        return userSelected;

    }

    public void sayBye(){
        console.infoMessage("Bye");
    }
    public void SelectedInvalidMenuNumber(){
        console.print(Script.DivisionLine.getMessage());
        console.errorMessage(Script.SelectedNumber_LimitError.getMessage());
        console.print(Script.DivisionBlank.getMessage());
    }

    @Override
    public void createOrder() {

    }

    @Override
    public void showOrders() {

    }

    @Override
    public void createVoucher() {
        console.print(Script.DivisionBlank.getMessage());
        console.infoMessage(Script.InputAmount_GuideMessage.getMessage());
        String inputAmount = console.input(Script.InputAmount.getMessage());
        //System.out.println(userSelected);

        if (checkInvalid.checkInteger(inputAmount)) {
            console.errorMessage(Script.InvalidValue_MustWriteDownNumber.getMessage());
            return ;
        }

        // 숫자인지 확인 완료 후, long 타입으로 변환
        Long inputAmount_longParse = Long.parseLong(inputAmount);

        // voucher type 선택하기
        console.infoMessage(Script.GuideMessage.getMessage());
        console.infoMessage(Script.InputUserSelectedVoucherType_GuideMessage.getMessage());
        String userSelected = console.input(Script.InputUserSelectedVoucherType.getMessage());

        // 메뉴 선택을 숫자로 했는지 확인
        if(checkInvalid.checkInteger(userSelected)){
            console.errorMessage(Script.InvalidValue_MustWriteDownNumber.getMessage());
            return;
        }

        userSelectedMenu = CreateVoucherPageMenu.getMenuName(Integer.parseInt(userSelected));

        // 자동으로 voucherId 생성
        // repository에서 자동으로 생성할지 고민했지만, 우선 밖에서 주입하는 것으로 진행.
        voucherId = UUID.randomUUID();

        int voucherType_flag = -1;
        switch(userSelectedMenu){

            case "FIXED" :
                voucherType = new FixedAmountVoucher(voucherId, inputAmount_longParse);
                voucherType_flag = 1;
                break;
            case "PERCENT" :
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
        }

        // 위의 유효성 검사 후, 저장단계 진행
        // voucherType_flag / 1 : 유효성 검사 통과, -1 : 유효성 검사 통과 실패
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


    }

    @Override
    public void showVouchers() {
        console.infoMessage(Script.ShowVoucherList_GuideMessage.getMessage());
        List<Voucher> voucherList = voucherService.getAllVouchers();
        if (voucherList.isEmpty()) {
            console.infoMessage(Script.EmptyData.getMessage());
        } else {
            console.voucherList(voucherList);
        }
        console.print(Script.DivisionBlank.getMessage());
    }


    @Override
    public void createCollection() throws IOException {
        console.print(Script.DivisionLine.getMessage());
        collectionName = console.input(Script.MonguDbCollectionCreate_GuideMessage.getMessage());
        console.infoMessage(dbManagement.getConnection().DbConnection().createdCollection(collectionName));
        console.print(Script.DivisionBlank.getMessage());

    }


    @Override
    public void dropCollection() {
        console.print(Script.DivisionLine.getMessage());
        collectionName = console.input(Script.MonguDbCollectionDrop_GuideMessage.getMessage());
        console.infoMessage(dbManagement.getConnection().DbConnection().dropCollection(collectionName));
        console.print(Script.DivisionBlank.getMessage());

    }

    @Override
    public void connectCollection(String collectionName) {

    }

    @Override
    public String selectedMonguMainMenu() throws IOException {
        console.print(Script.DivisionLine.getMessage());
        console.infoMessage(Script.MonguDbMainMenu.getMessage());
        return console.input(Script.InputUserSelectedMenuNumber.getMessage());

    }
}
