package org.prgrms.orderApp.presentation.commandOperator.pages;

import org.json.simple.parser.ParseException;
import org.prgrms.orderApp.infrastructure.library.console.script.BasicScript;
import org.prgrms.orderApp.infrastructure.library.console.script.ForUxScript;
import org.prgrms.orderApp.presentation.commandOperator.script.ApplicationScript;
import org.prgrms.orderApp.presentation.commandOperator.script.WarnningAndErrorScript;
import org.prgrms.orderApp.infrastructure.library.console.Console;
import org.prgrms.orderApp.presentation.commandOperator.util.CheckInvalid;
import org.prgrms.orderApp.presentation.commandOperator.util.ProcessingStatus;
import org.prgrms.orderApp.domain.voucher.model.Voucher;
import org.prgrms.orderApp.domain.voucher.model.VoucherType;
import org.prgrms.orderApp.service.VoucherApplicationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class VoucherPage {

   private Console console;
   private VoucherApplicationService voucherApplicationService;
   private CheckInvalid checkInvalid;

   public VoucherPage(Console console, VoucherApplicationService voucherApplicationService, CheckInvalid checkInvalid){
      this.console = console;
      this.voucherApplicationService = voucherApplicationService;
      this.checkInvalid = checkInvalid;
   }
   private Voucher voucher;
   private Map<String,Object> saveResult;
   private String errorMessage, userSelectedMenu;
   int voucherTypeFlag;
   String userinputAmount;

   public void createVoucher() throws IOException {
      console.print(ForUxScript.DIVISION_BLANK);
      console.infoMessage(ApplicationScript.INPUT_AMOUNT__GUIDE_MESSAGE);
      userinputAmount = console.input(ApplicationScript.INPUT_AMOUNT);
      //System.out.println(userinputAmount);

      if (checkInvalid.checkInteger(userinputAmount)) {
         console.errorMessage(WarnningAndErrorScript.INVALID_VALUE__MUST_WRITE_DOWN_NUMBER);
         return ;
      }

      // 숫자인지 확인 완료 후, long 타입으로 변환
      Long inputAmount_longParse = Long.parseLong(userinputAmount);

      // voucher type 선택하기
      console.infoMessage(BasicScript.GUIDE_MESSAGE);
      console.infoMessage(ApplicationScript.INPUT_USER_SELECTED_VOUCHER_TYPE__GUIDE_MESSAGE);
      String userSelected = console.input(ApplicationScript.INPUT_USER_SELECTED_VOUCHER_TYPE);

      // 메뉴 선택을 숫자로 했는지 확인
      if(checkInvalid.checkInteger(userSelected)){
         console.errorMessage(WarnningAndErrorScript.INVALID_VALUE__MUST_WRITE_DOWN_NUMBER);
         return;
      }

      userSelectedMenu = VoucherType.getMenuNameByMenuNumber(Integer.parseInt(userSelected));

      // 자동으로 voucherId 생성
      // repository에서 자동으로 생성할지 고민했지만, 우선 밖에서 주입하는 것으로 진행.

      switch(userSelectedMenu){

         case "FIXED" :
            if(voucherApplicationService.fixedAmountCheckInvalid(inputAmount_longParse)){
               voucher = voucherApplicationService.putFixedAmountVoucher(inputAmount_longParse);
               voucherTypeFlag = 1;
            }else{
               voucherTypeFlag = -1;
               errorMessage = WarnningAndErrorScript.PERCENT_LIMIT_ERROR;
            }
            break;
         case "PERCENT" :
            //percent 값은 100이 넘지 않게 처리. 고객에세 돈을 줄 수는 없으니까.
            if (voucherApplicationService.percentAmountCheckInvalid(inputAmount_longParse)){
               voucher = voucherApplicationService.putPercentDiscountVoucher(inputAmount_longParse);
               voucherTypeFlag = 1;
            }else{
               voucherTypeFlag = -1;
               errorMessage = WarnningAndErrorScript.PERCENT_LIMIT_ERROR;
            }
            break;
         default:
            voucherTypeFlag = -1;
            errorMessage = WarnningAndErrorScript.SELECTED_NUMBER_LIMIT_ERROR;
      }

      // 위의 유효성 검사 후, 저장단계 진행
      // voucherType_flag / 1 : 유효성 검사 통과, -1 : 유효성 검사 통과 실패
      if (voucherTypeFlag==1){
         saveResult = voucherApplicationService.saveVoucher(voucher);
         if (saveResult.get("status").equals(ProcessingStatus.SUCCESS)) {
            console.infoMessage(ApplicationScript. SUCCESS_AMOUNT_SAVE + saveResult.get("voucherId"));
         } else {
            console.errorMessage(WarnningAndErrorScript.VOUCHER_ID_DUPLICATE_ERROR + saveResult.get("voucherId"));
         }
      }else {
         console.print(ForUxScript.DIVISION_LINE);
         console.infoMessage(errorMessage);
         console.print(ForUxScript.DIVISION_BLANK);
      }


   }

   public void showVouchers() throws IOException, ParseException {
      console.infoMessage(ApplicationScript.SHOW_VOUCHER_LIST__GUIDE_MESSAGE);
      List<Voucher> voucherList = voucherApplicationService.getAllVouchers();
      if (voucherList.isEmpty()) {
         console.infoMessage(BasicScript.EMPTY_DATA);
      } else {
         console.showList(voucherList);
      }
      console.print(ForUxScript.DIVISION_BLANK);
   }


}
