package org.prgrms.springbootbasic.voucher;

import org.prgrms.springbootbasic.console.Input;
import org.prgrms.springbootbasic.console.Output;
import org.prgrms.springbootbasic.voucher.repository.VoucherRepository;
import org.prgrms.springbootbasic.voucher.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.voucher.voucher.PercentDiscountVoucher;
import org.prgrms.springbootbasic.voucher.voucher.Voucher;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
  private final VoucherRepository voucherRepository;
  private final Input input;
  private final Output output;

  public VoucherService(VoucherRepository voucherRepository, Input input, Output output) {
    this.voucherRepository = voucherRepository;
    this.input = input;
    this.output = output;
  }

  public void run() {
    boolean execute = true;
    output.printAvailableCommandList();
    while(execute) {
      execute = validateCommand(input.readCommand());
    }
  }

  private boolean validateCommand(String command) {
    if(command.equals("exit")) return false;
    else if(command.equals("list")) {
      output.printVoucherList(findAll());
      return true;
    }
    else if(command.equals("create")) {
      validateVouherType();
      return true;
    }
    return true;
  }

  public void validateVouherType() {
    boolean created = false;
    while(!created) {
      String voucherType = input.readVoucherType();
      created = createVoucher(voucherType);
    }
  }

  public boolean createVoucher(String voucherType) {
    voucherType = voucherType.toLowerCase(Locale.ROOT);
    if(voucherType.equals("fixed") || voucherType.equals("fixedamountvoucher")) {
      voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
      return true;
    }
    else if(voucherType.equals("percent") || voucherType.equals("percentdiscountvoucher")) {
      voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 10L));
      return true;
    }
    return false;
  }

  public Map<UUID, Voucher> findAll() {
    return voucherRepository.findAll();
  }
}
