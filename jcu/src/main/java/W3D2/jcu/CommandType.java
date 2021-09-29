package W3D2.jcu;

import W3D2.jcu.voucher.model.VoucherStatus;
import W3D2.jcu.voucher.service.VoucherService;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.UUID;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;

// enum은 static영역에 저장됨
@RequiredArgsConstructor
public enum CommandType {
    // ToDo : delete 커멘드 추가
    EXIT("EXIT", voucherService -> {
        voucherService.saveStorage();
        return false;
    }),
    CREATE("CREATE", voucherService -> {
        System.out.print("\n==== Choose one ====\n"
            + "Type fixed to create a fixed amount Voucher.\n"
            + "Type percent to create a percent discount Voucher.\n"
            + ">> ");
        String command = Utils.readLine();
        VoucherStatus code = VoucherStatus.from(command);
        Long amount = Long.valueOf(Utils.readLineWithMessage("===== Input Amount ====="));
        voucherService.insertVoucher(code.execute(UUID.randomUUID(), amount));
        return true;
    }),
    LIST("LIST", voucherService -> {
        System.out.println(voucherService.showVouchers());
        return true;
    });

    private final String command;
    private final Predicate<VoucherService> func;

    public static CommandType from(String value){
        return CommandType.valueOf(value.toUpperCase());
    }

    public boolean execute(VoucherService voucherService) {
        return func.test(voucherService);
    }
}