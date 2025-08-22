package com.quiz.quiz.dto;

import com.quiz.quiz.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    @NotBlank(message = "아이디은 반드시 입력하셔야 합니다.")
    private String id;
    private String password;
    private boolean status;

    public static MemberDto fromMemberEntity(Member member) {
        return new MemberDto(
                member.getId(),
                member.getPassword(),
                member.isStatus()
        );
    }

    public static Member toDto(MemberDto dto) {
        Member member = new Member();
        member.setId(dto.getId());
        member.setPassword(dto.getPassword());
        member.setStatus(dto.isStatus());
        return member;
    }

}
