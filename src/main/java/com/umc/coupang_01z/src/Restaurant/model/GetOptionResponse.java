package com.umc.coupang_01z.src.Restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOptionResponse {
    private GetMenuRes getMenuRes;
    private List<GetOptionRes> listGetOptionRes;

    public GetOptionResponse() {
    }
}
