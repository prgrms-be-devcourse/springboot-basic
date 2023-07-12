package kr.co.programmers.springbootbasic.wallet.dto;

import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class WalletResponse {
    private static final String WALLET_FIND_RESPONSE_FORMAT = """
            지갑 아이디 : {0}
                        
            바우처 정보
            =================================
                        
            {1}
                    
            """;
    private final UUID walletId;
    private final List<VoucherResponse> voucherResponses;

    public WalletResponse(UUID walletId, List<VoucherResponse> voucherResponses) {
        this.walletId = walletId;
        this.voucherResponses = voucherResponses;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public List<VoucherResponse> getVoucherResponses() {
        return voucherResponses;
    }

    public String formatWalletFindResponse() {
        String messages = this.voucherResponses.stream()
                .map(VoucherResponse::formatVoucherResponseDto)
                .collect(Collectors.joining());

        return MessageFormat.format(WALLET_FIND_RESPONSE_FORMAT, this.walletId, messages);
    }
}
