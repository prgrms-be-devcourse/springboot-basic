package org.programmers.devcourse.voucher.engine;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.blacklist.BlackList;
import org.programmers.devcourse.voucher.engine.blacklist.BlackListService;
import org.programmers.devcourse.voucher.engine.exception.NoSuchOptionException;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.io.Input;
import org.programmers.devcourse.voucher.engine.io.Output;
import org.programmers.devcourse.voucher.engine.voucher.VoucherService;
import org.programmers.devcourse.voucher.engine.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Controller {

  private static final Logger logger = LoggerFactory.getLogger(Controller.class);
  private final Input input;
  private final Output output;
  private final VoucherService voucherService;
  private final BlackListService blackListService;

  public Controller(Input input, Output output,
      VoucherService voucherService,
      BlackListService blackListService) {
    this.input = input;
    this.output = output;
    this.voucherService = voucherService;
    this.blackListService = blackListService;
  }


  public void start() {
    try (input; output) {

      while (true) {
        Optional<MenuSelection> optionalSelection = input.getSelection();
        try {
          switch (optionalSelection.orElseThrow(
              NoSuchOptionException::new)) {
            case EXIT:
              output.print("=====Good Bye=====");
              return;
            case CREATE:
              createVoucher();
              break;
            case LIST:
              showAllVouchers();
              break;
            case BLACKLIST:
              showBlacklist();
              break;
          }

        } catch (VoucherException exception) {
          output.print(exception.getMessage());
          logger.error("VoucherException", exception);
        }
      }

    } catch (Exception exception) {
      // VoucherException 외 치명적 오류 (파일로 보관 예정)
      logger.error(
          "critical error", exception);
    }

  }


  private void showBlacklist() {
    List<BlackList> list = blackListService.getBlackList();

    output.printBlackList(list);
  }

  private void showAllVouchers() {
    output.printAllVouchers(voucherService.getAllVouchers());


  }

  private void createVoucher() throws IOException, VoucherException {
    // 사용자로 부터 바우처 타입을 입력 받는다.
    VoucherType voucherType = null;
    while (true) {
      String typeId = input.getVoucherTypeId();
      var candidate = voucherService.mapTypeToMapper(typeId);
      if (candidate.isPresent()) {
        voucherType = candidate.get();
        break;
      }
      // input은 사용자의 입력을 받으면서 소통하는 애플리케이션
      input.printInputError("타입이 맞지 않습니다.");
    }
    long voucherDiscountData = input.getVoucherDiscountData(voucherType);

    UUID voucherId = voucherService.create(voucherType, voucherDiscountData);
    output.print(MessageFormat.format("CREATE SUCCESS! VoucherID : {0}", voucherId));


  }
}
