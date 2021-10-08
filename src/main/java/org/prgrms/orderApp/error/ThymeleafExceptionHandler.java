package org.prgrms.orderApp.error;

import org.prgrms.orderApp.controller.VoucherController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = {VoucherController.class})
public class ThymeleafExceptionHandler {

    @ExceptionHandler(value = {CustomRuntimeException.class})
    public String MyHandlerError(CustomRuntimeException e, Model model) {
        if (e.getErrorCode().equals(451)) {
            return String.format("redirect:/voucher/list?type={}&status=fail&message={}", "FIND", "해당 쿠폰은 존재하지 않습니다.");
        } else if (e.getErrorCode().equals(452)) {
            return String.format("redirect:/voucher/list?type={}&status=fail&message={}", "DELETE", "해당 쿠폰 삭제를 실패하였습니다.");
        } else if (e.getErrorCode().equals(453)) {
            return String.format("redirect:/voucher/list?type={}&status=fail&message={}", "SAVE", " 쿠폰 생성을 실패하였습니다. Percent 쿠폰은 100이 넘을 수 없습니다. ");
        } else if (e.getErrorCode().equals(454)) {
            return String.format("redirect:/voucher/list?type={}&status=fail&message={}", "SAVE", "쿠폰 생성을 실패하였습니다. 다시 시도해주십시오.");
        } else {
            model.addAttribute("main_message", "[NOTICE] 문제가 발생하였습니다. 담당자분에게 연락바랍니다.");
            return "errors/error";
        }
    }
}
