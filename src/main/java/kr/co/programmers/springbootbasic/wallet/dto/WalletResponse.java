package kr.co.programmers.springbootbasic.wallet.dto;

import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record WalletResponse(UUID walletId, List<VoucherResponse> voucherResponses) {
    private static final String WALLET_FIND_RESPONSE_FORMAT = """
            지갑 아이디 : {0}
                        
            바우처 정보
            =================================
                        
            {1}
                    
            """;

    public String formatMessage() {
        String messages = this.voucherResponses.stream()
                .map(VoucherResponse::formatMessage)
                .collect(Collectors.joining());

        return MessageFormat.format(WALLET_FIND_RESPONSE_FORMAT, this.walletId, messages);
    }
}
