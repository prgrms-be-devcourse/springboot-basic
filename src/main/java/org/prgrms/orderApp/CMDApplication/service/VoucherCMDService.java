package org.prgrms.orderApp.CMDApplication.service;

import org.prgrms.orderApp.CMDApplication.console.Console;
import org.prgrms.orderApp.CMDApplication.console.script.AllScriptForConsole;
import org.prgrms.orderApp.CMDApplication.util.CheckInvalid;
import org.prgrms.orderApp.CMDApplication.util.ProcessingStatus;
import org.prgrms.orderApp.model.voucher.FixedAmountVoucher;
import org.prgrms.orderApp.model.voucher.PercentDiscountVoucher;
import org.prgrms.orderApp.model.voucher.Voucher;
import org.prgrms.orderApp.model.voucher.VoucherType;
import org.prgrms.orderApp.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VoucherCMDService implements AllScriptForConsole {

   @Autowired
   private Console console;

   @Autowired
   private VoucherService voucherService;

   @Autowired
   private CheckInvalid checkInvalid;

   private Voucher voucher;
   private Map<String,Object> saveResult;
   private String errorMessage, userSelectedMenu;
   int voucherTypeFlag;
   String userinputAmount;

   public void createVoucher() {
      console.print(divisionBlank);
      console.infoMessage(inputAmount_GuideMessage);
      userinputAmount = console.input(inputAmount);
      //System.out.println(userSelected);

      if (checkInvalid.checkInteger(inputAmount)) {
         console.errorMessage(invalidValue_MustWriteDownNumber);
         return ;
      }

      // 숫자인지 확인 완료 후, long 타입으로 변환
      Long inputAmount_longParse = Long.parseLong(inputAmount);

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
            voucher = new FixedAmountVoucher(UUID.randomUUID(), inputAmount_longParse);
            voucherTypeFlag = 1;
            break;
         case "PERCENT" :
            //percent 값은 100이 넘지 않게 처리. 고객에세 돈을 줄 수는 없으니까.
            if (inputAmount_longParse > VoucherType.PERCENTAMOUNT.getLimit()){
               voucherTypeFlag = -1;
               errorMessage = percent_LimitError;
            }else{
               voucher = new PercentDiscountVoucher(UUID.randomUUID(), inputAmount_longParse);
               voucherTypeFlag = 1;
            }
            break;
         default:
            voucherTypeFlag = -1;
            errorMessage = selectedNumber_LimitError;
      }

      // 위의 유효성 검사 후, 저장단계 진행
      // voucherType_flag / 1 : 유효성 검사 통과, -1 : 유효성 검사 통과 실패
      if (voucherTypeFlag==1){
         saveResult = voucherService.saveVoucher(voucher);
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

   public void showVouchers() {
      console.infoMessage(showVoucherList_GuideMessage);
      List<Voucher> voucherList = voucherService.getAllVouchers();
      if (voucherList.isEmpty()) {
         console.infoMessage(emptyData);
      } else {
         console.voucherList(voucherList);
      }
      console.print(divisionBlank);
   }


}
