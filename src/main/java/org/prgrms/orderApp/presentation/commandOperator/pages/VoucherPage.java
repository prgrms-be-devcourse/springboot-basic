package org.prgrms.orderApp.presentation.commandOperator.pages;

import org.json.simple.parser.ParseException;
import org.prgrms.orderApp.application.service.VoucherApplicationService;
import org.prgrms.orderApp.infrastructure.library.console.Console;
import org.prgrms.orderApp.presentation.commandOperator.script.AllScriptForCMDApplication;
import org.prgrms.orderApp.presentation.commandOperator.util.CheckInvalid;
import org.prgrms.orderApp.presentation.commandOperator.util.ProcessingStatus;
import org.prgrms.orderApp.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.orderApp.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.orderApp.domain.voucher.model.Voucher;
import org.prgrms.orderApp.domain.voucher.model.VoucherType;
import org.prgrms.orderApp.domain.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VoucherPage implements AllScriptForCMDApplication {

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
      console.print(divisionBlank);
      console.infoMessage(inputAmount_GuideMessage);
      userinputAmount = console.input(inputAmount);
      //System.out.println(userinputAmount);

      if (checkInvalid.checkInteger(userinputAmount)) {
         console.errorMessage(invalidValue_MustWriteDownNumber);
         return ;
      }

      // 숫자인지 확인 완료 후, long 타입으로 변환
      Long inputAmount_longParse = Long.parseLong(userinputAmount);

      // voucher type 선택하기
      console.infoMessage(guideMessage);
      console.infoMessage(inputUserSelectedVoucherType_GuideMessage);
      String userSelected = console.input(inputUserSelectedVoucherType);

      // 메뉴 선택을 숫자로 했는지 확인
      if(checkInvalid.checkInteger(userSelected)){
         console.errorMessage(invalidValue_MustWriteDownNumber);
         return;
      }

      userSelectedMenu = VoucherType.getMenuName(Integer.parseInt(userSelected));

      // 자동으로 voucherId 생성
      // repository에서 자동으로 생성할지 고민했지만, 우선 밖에서 주입하는 것으로 진행.

      switch(userSelectedMenu){

         case "FIXED" :
            if(voucherApplicationService.fixedAmountCheckInvalid(inputAmount_longParse)){
               voucher = voucherApplicationService.putFixedAmountVoucher(inputAmount_longParse);
               voucherTypeFlag = 1;
            }else{
               voucherTypeFlag = -1;
               errorMessage = percent_LimitError;
            }
            break;
         case "PERCENT" :
            //percent 값은 100이 넘지 않게 처리. 고객에세 돈을 줄 수는 없으니까.
            if (voucherApplicationService.percentAmountCheckInvalid(inputAmount_longParse)){
               voucher = voucherApplicationService.putPercentDiscountVoucher(inputAmount_longParse);
               voucherTypeFlag = 1;
            }else{
               voucherTypeFlag = -1;
               errorMessage = percent_LimitError;
            }
            break;
         default:
            voucherTypeFlag = -1;
            errorMessage = selectedNumber_LimitError;
      }

      // 위의 유효성 검사 후, 저장단계 진행
      // voucherType_flag / 1 : 유효성 검사 통과, -1 : 유효성 검사 통과 실패
      if (voucherTypeFlag==1){
         saveResult = voucherApplicationService.saveVoucher(voucher);
         if (saveResult.get("status").equals(ProcessingStatus.SUCCESS)) {
            console.infoMessage(success_Amount_Save + saveResult.get("voucherId"));
         } else {
            console.errorMessage(voucherId_DuplicateError + saveResult.get("voucherId"));
         }
      }else {
         console.print(divisionLine);
         console.infoMessage(errorMessage);
         console.print(divisionBlank);
      }


   }

   public void showVouchers() throws IOException, ParseException {
      console.infoMessage(showVoucherList_GuideMessage);
      List<Voucher> voucherList = voucherApplicationService.getAllVouchers();
      if (voucherList.isEmpty()) {
         console.infoMessage(emptyData);
      } else {
         console.showList(voucherList);
      }
      console.print(divisionBlank);
   }


}
