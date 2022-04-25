package com.pppp0722.vouchermanagement.member.service;

import com.pppp0722.vouchermanagement.member.model.Member;
import com.pppp0722.vouchermanagement.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Optional<Member> createMember(UUID memberId, String name) {
        return memberRepository.insert(new Member(memberId, name));
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> getMemberById(UUID memberID) {
        return memberRepository.findById(memberID);
    }

    @Override
    public Optional<Member> updateMember(UUID memberId, String name) {
        return memberRepository.update(new Member(memberId, name));
    }

    @Override
    public Optional<Member> deleteMember(UUID memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        if (member.isEmpty()) {
            return Optional.empty();
        }

        return memberRepository.delete(member.get());
    }


}