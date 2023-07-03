package kr.co.springbootweeklymission.member.api.dto.response;

import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.model.MemberStatus;
import kr.co.springbootweeklymission.wallet.domain.entity.Wallet;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResDTO {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {
        private UUID memberId;
        private MemberStatus memberStatus;

        public static MemberResDTO.READ toMemberReadDto(Member member) {
            return MemberResDTO.READ.builder()
                    .memberId(member.getMemberId())
                    .memberStatus(member.getMemberStatus())
                    .build();
        }

        public static MemberResDTO.READ toMemberReadDto(Wallet wallet) {
            return READ.builder()
                    .memberId(wallet.getMember().getMemberId())
                    .memberStatus(wallet.getMember().getMemberStatus())
                    .build();
        }
    }
}
