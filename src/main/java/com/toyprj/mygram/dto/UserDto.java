package com.toyprj.mygram.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDto {

    private Long id;

    // id
    private String username;

    private String password;
    // 사용자 이름
    private String name;

    // 별명
    private String nickname;

}
