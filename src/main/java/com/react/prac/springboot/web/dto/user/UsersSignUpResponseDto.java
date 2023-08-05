package com.react.prac.springboot.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersSignUpResponseDto {
    private String token;
    private int exprTime;
}