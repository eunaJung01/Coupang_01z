package com.umc.coupang_01z.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String email;
    private String pw;
    private String name;
    private String phoneNum;
}
