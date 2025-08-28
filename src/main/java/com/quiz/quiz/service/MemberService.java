package com.quiz.quiz.service;

import com.quiz.quiz.dto.MemberDto;
import com.quiz.quiz.entity.Member;
import com.quiz.quiz.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;

    public void saveMember(MemberDto dto) {
        Member entity = MemberDto.toDto(dto);  // DTO -> Entity 변환
        entity.setStatus(false);
        repository.save(entity);
        if ("root".equals(dto.getId())) {
            throw new RuntimeException("관리자 계정은 별도로 등록해야 합니다.");
        }
    }

    public List<MemberDto> getAllList() {
        List<Member> memberList = repository.findAll();
        return memberList.stream()
                .map(MemberDto::fromMemberEntity)
                .toList();
    }

    public void deleteMember(String id) {
        repository.deleteById(id);
    }

    public MemberDto findOneMember(String id) {
        Member entity = repository.findById(id).orElse(null);
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }
        return MemberDto.fromMemberEntity(entity);
    }

    @Transactional
    public void toggleStatus(String id) {
        Member member = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다: " + id));

        if (!"root".equals(member.getId())) {
            member.setStatus(!member.isStatus()); // 승인/미승인
        }
    }


    @Transactional
    public void updatePassword(String id, String newPassword) {
        Member member = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다: " + id));
        member.setPassword(newPassword);
    }

}
