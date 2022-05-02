package com.hellomarket.service;

import com.hellomarket.entity.Member;
import com.hellomarket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**     회원가입     */
    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByUserId(member.getUserId());
        if(findMember != null){
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    public Member findMember(Long id) {
        return memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}